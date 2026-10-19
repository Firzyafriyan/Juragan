# Temuan Bug, Kerentanan Keamanan & Rekomendasi Peningkatan

Dokumen ini memuat daftar temuan teknis (bugs), celah keamanan (security vulnerabilities), dan rekomendasi perbaikan berbasis prioritas untuk aplikasi Android **Juragan**.

---

## 1. Matriks Temuan Berdasarkan Tingkat Keparahan (Severity Matrix)

| ID | Kategori | Tingkat Keparahan | Komponen Terdampak | Status |
| :--- | :--- | :---: | :--- | :--- |
| **BUG-01** | Runtime Crash | **CRITICAL** | `MainActivity.java` (`nav_like`) | 🔴 Memerlukan Perbaikan Segera |
| **BUG-02** | Keamanan Data | **HIGH** | `Db_Contract.java` & `LogAgenActivity.java` | 🔴 Kredensial & Jaringan Terbuka |
| **BUG-03** | Logika Sesi | **HIGH** | `LogAgenActivity.java` & `DataPribadiFragment.java` | 🔴 Logout Agen Gagal Bersihkan Sesi |
| **BUG-04** | UI / Event Handling | **MEDIUM** | `HomeFragment.java` | 🟡 Tombol Tanya & Testi Tidak Berfungsi |
| **BUG-05** | UI / Kelengkapan | **MEDIUM** | `AGEN/` (Beranda, Target, Nama Jamaah) | 🟡 Fragment Berisi Layout Placeholder Kosong |
| **BUG-06** | Dead Code & Bloat | **LOW** | `DatabaseHelper.java`, `strings.xml` | 🟢 Mengotori Kode & Menambah Ukuran APK |
| **BUG-07** | Deprecated APIs | **LOW** | `ClientAsyncTask.java`, `startActivityForResult` | 🟢 Perlu Modernisasi Jetpack |

---

## 2. Rincian Temuan & Solusi Kode (Bug Details & Fixes)

---

### 🔴 BUG-01: Runtime Crash Saat Menekan Tab Navigasi "Like" (Jadwal Sholat)

- **Gejala**: Aplikasi langsung tertutup paksa (force close) saat pengguna menekan tab ketiga pada bottom navigation bar.
- **Lokasi Kode**: [MainActivity.java:L50-L57](file:///c:/Dev/Projects/Juragan/app/src/main/java/com/cadenza/bottomnavigation/MainActivity.java#L50-L57)
- **Analisis**:
  ```java
  case R.id.nav_like:
      Class fragmentClass = JadwalSholat.class;
      try {
          fragment = (Fragment) fragmentClass.newInstance(); // THROW ClassCastException
      } catch (Exception e) {
          e.printStackTrace();
      }
      break;
  ```
  `JadwalSholat` mewarisi `AppCompatActivity`, bukan `Fragment`. Casting kelas Activity ke `Fragment` menyebabkan `ClassCastException`. Exception ditangkap oleh blok `catch`, sehingga variabel `fragment` bernilai `null`. Baris selanjutnya:
  ```java
  getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
  ```
  akan melempar `NullPointerException` atau `IllegalArgumentException` fatal dari Android Framework.

- **Solusi Rekomendasi**:
  1. **Opsi A (Menjalankan Activity)**:
     Buka `JadwalSholat` sebagai Activity terpisah:
     ```java
     case R.id.nav_like:
         startActivity(new Intent(MainActivity.this, JadwalSholat.class));
         return false; // Jangan ubah selected item jika berpindah activity
     ```
  2. **Opsi B (Gunakan Fragment - Direkomendasikan)**:
     Ubah `JadwalSholat` menjadi sebuah `Fragment` (misalnya `JadwalSholatFragment`) atau manfaatkan `LikeFragment` yang sudah ada:
     ```java
     case R.id.nav_like:
         fragment = new LikeFragment();
         break;
     ```

---

### 🔴 BUG-02: Kebocoran Kredensial & Kerentanan Jaringan (Security)

- **Lokasi**: [LogAgenActivity.java:L84](file:///c:/Dev/Projects/Juragan/app/src/main/java/com/cadenza/bottomnavigation/AGEN/LogAgenActivity.java#L84) dan [Db_Contract.java](file:///c:/Dev/Projects/Juragan/app/src/main/java/com/cadenza/bottomnavigation/API/Db_Contract.java)
- **Masalah**:
  1. Pengiriman password agen dilekatkan langsung pada query parameter URL:
     ```java
     StringRequest stringRequest = new StringRequest(Request.Method.POST, 
         Db_Contract.urlLogin + "?email=" + email + "&password=" + password, ...);
     ```
     Parameter query string mudah tersimpan di log server (access logs), browser history, atau tercium oleh sniffer jika jaringan tidak terenkripsi.
  2. Alamat IP backend menggunakan IP intranet lokal hardcoded `10.10.176.166`.
  3. Mengaktifkan `android:usesCleartextTraffic="true"` tanpa enkripsi HTTPS (SSL/TLS).

- **Solusi Rekomendasi**:
  1. Kirim kredensial melalui `POST Body (getParams)` murni tanpa query string:
     ```java
     StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlLogin,
         response -> { ... },
         error -> { ... }
     ) {
         @Override
         protected Map<String, String> getParams() {
             Map<String, String> params = new HashMap<>();
             params.put("email", email);
             params.put("password", password);
             return params;
         }
     };
     ```
  2. Pindahkan Base URL ke `buildConfigField` di [build.gradle](file:///c:/Dev/Projects/Juragan/app/build.gradle) agar dapat dikonfigurasi dinamis untuk mode debug/release:
     ```groovy
     buildTypes {
         debug {
             buildConfigField "String", "BASE_URL", "\"http://10.10.176.166/juragandb/\""
         }
         release {
             buildConfigField "String", "BASE_URL", "\"https://api.juragantravel.com/\""
         }
     }
     ```
  3. Migrasikan backend ke protokol HTTPS dan hapus `usesCleartextTraffic="true"` saat rilis produksi.

---

### 🔴 BUG-03: Sesi Login Agen Gagal Terhapus (SharedPreferences Name Mismatch)

- **Lokasi**:
  - Penulisan sesi: [LogAgenActivity.java:L104](file:///c:/Dev/Projects/Juragan/app/src/main/java/com/cadenza/bottomnavigation/AGEN/LogAgenActivity.java#L104) -> `getSharedPreferences("UserData", MODE_PRIVATE);`
  - Pembersihan sesi: [DataPribadiFragment.java:L76](file:///c:/Dev/Projects/Juragan/app/src/main/java/com/cadenza/bottomnavigation/AGEN/DataPribadiFragment.java#L76) -> `getSharedPreferences("UserDataAgen", Context.MODE_PRIVATE);`
- **Dampak**: Ketika agen menekan tombol Logout di `DataPribadiFragment`, yang dihapus adalah file pref `"UserDataAgen"` (yang kosong), sedangkan data login di `"UserData"` tetap tersimpan. Akibatnya, fungsi `checkLoginStatus()` akan menganggap agen tetap login.
- **Solusi Rekomendasi**:
  Standarisasi nama SharedPreferences ke satu konstanta terpusat, misalnya:
  ```java
  public class PrefConst {
      public static final String PREF_USER_JAMAAH = "UserDataJamaah";
      public static final String PREF_USER_AGEN = "UserDataAgen";
  }
  ```
  Pastikan `LogAgenActivity` dan `DataPribadiFragment` konsisten menggunakan `PrefConst.PREF_USER_AGEN`.

---

### 🟡 BUG-04: Tombol "Tanya" dan "Testi" pada Beranda Tidak Berfungsi

- **Lokasi**: [HomeFragment.java](file:///c:/Dev/Projects/Juragan/app/src/main/java/com/cadenza/bottomnavigation/HomeFragment.java) & [fragment_home.xml:L240,L278](file:///c:/Dev/Projects/Juragan/app/src/main/res/layout/fragment_home.xml#L240)
- **Masalah**: Tampilan beranda memiliki CardView dengan ID `compassBtn` (Tanya) dan `btnTestimoni` (Testi), tetapi tidak pernah di-lookup (`findViewById`) atau diberi `setOnClickListener` di `HomeFragment.java`.
- **Solusi Rekomendasi**:
  Tambahkan inisialisasi dan intent di `HomeFragment.onCreateView()`:
  ```java
  View btnTanya = view.findViewById(R.id.compassBtn);
  View btnTestimoni = view.findViewById(R.id.btnTestimoni);

  if (btnTanya != null) {
      btnTanya.setOnClickListener(v -> startActivity(new Intent(getActivity(), TanyaActivity.class)));
  }

  if (btnTestimoni != null) {
      btnTestimoni.setOnClickListener(v -> startActivity(new Intent(getActivity(), TestimoniActivity.class)));
  }
  ```

---

### 🟡 BUG-05: Modul Agen Masih Belum Selesai (Layout Placeholder)

- **Lokasi**:
  - [BerandaFragment.java](file:///c:/Dev/Projects/Juragan/app/src/main/java/com/cadenza/bottomnavigation/AGEN/BerandaFragment.java) (`fragment_beranda.xml`)
  - [TargetJamaahFragment.java](file:///c:/Dev/Projects/Juragan/app/src/main/java/com/cadenza/bottomnavigation/AGEN/TargetJamaahFragment.java) (`fragment_target_jamaah.xml`)
  - [NamaJamaahFragment.java](file:///c:/Dev/Projects/Juragan/app/src/main/java/com/cadenza/bottomnavigation/AGEN/NamaJamaahFragment.java) (`fragment_nama_jamaah.xml`)
- **Masalah**: Ketiga fragment tersebut hanya menampilkan `@string/hello_blank_fragment` tanpa UI dan fungsionalitas.
- **Rekomendasi**:
  1. **Beranda Agen**: Tampilkan rekap komisi/reward, total jamaah terdaftar, dan paket terlaris.
  2. **Target Agen**: Tampilkan progress bar target tahunan/bulanan jamaah yang perlu direkrut.
  3. **Nama Jamaah**: Implementasikan `RecyclerView` yang memuat daftar jamaah dengan filter berdasarkan `id_agen` pengguna aktif.

---

### 🟢 BUG-06: Dead Code & Resep Makanan Template di `strings.xml`

- **Lokasi**: [DatabaseHelper.java](file:///c:/Dev/Projects/Juragan/app/src/main/java/com/cadenza/bottomnavigation/DatabaseHelper.java) dan [res/values/strings.xml:L17-L102](file:///c:/Dev/Projects/Juragan/app/src/main/res/values/strings.xml#L17-L102)
- **Masalah**:
  - `DatabaseHelper.java` tidak digunakan sama sekali di seluruh aplikasi (SQLite tidak terpakai karena mengandalkan MySQL remote).
  - Di `strings.xml` terdapat teks resep masakan (`pastaIngredients`, `maggiIngredients`, `pancakeDesc`, `pizzaDesc`, `burgerIngredients`, `friesIngredients`).
- **Solusi Rekomendasi**:
  Hapus resource sisa template yang tidak terpakai untuk mempercepat waktu kompilasi dan memperkecil ukuran APK final.

---

## 3. Peta Jalan Peningkatan (Refactoring & Modernization Roadmap)

```
FASE 1: Stabilisasi Kritis (1-2 Hari)
├── Perbaiki BUG-01 (Crash klik tab Like di MainActivity)
├── Sinkronisasi SharedPreferences LogAgen & DataPribadiFragment (BUG-03)
├── Hubungkan tombol Tanya & Testi di HomeFragment (BUG-04)
└── Hapus string & class sisa template (BUG-06)

FASE 2: Penguatan Keamanan & Networking (3-5 Hari)
├── Pindahkan kredensial login agen dari Query Param ke POST Body (BUG-02)
├── Konfigurasi dynamic BASE_URL via BuildConfig Gradle
├── Pasang Network Security Config untuk enforce HTTPS
└── Migrasi AsyncTask ke Coroutines (jika Kotlin) atau ExecutorService/RxJava/Retrofit

FASE 3: Penyelesaian Modul Agen & Modernisasi Arsitektur (1-2 Minggu)
├── Implementasi UI dan endpoint backend untuk Beranda, Target, dan Nama Jamaah Agen
├── Migrasi pengelolaan izin dari Dexter (library lama) ke ActivityResultContracts modern
├── Adopsi arsitektur MVVM (ViewModel + LiveData / StateFlow)
└── Implementasi Room Database untuk offline caching panduan & doa manasik
```
