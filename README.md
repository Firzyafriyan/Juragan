<div align="center">

<img src="assets/banner_juragan.png" alt="Juragan Banner" width="100%">

<br>

[![Android](https://img.shields.io/badge/Android-App-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![Java](https://img.shields.io/badge/Java-Language-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/Gradle-Build-02303A?style=for-the-badge&logo=gradle&logoColor=white)](https://gradle.org/)
[![Volley](https://img.shields.io/badge/Volley-HTTP-FF6F00?style=for-the-badge)](https://google.github.io/volley/)
[![minSdk](https://img.shields.io/badge/minSdk-22%20(Android%205.1)-8A2BE2?style=for-the-badge)](app/build.gradle)
[![License](https://img.shields.io/badge/License-MIT-F7DF1E?style=for-the-badge)](LICENSE)

[![Stars](https://img.shields.io/github/stars/Firzyafriyan/Juragan?style=social)](https://github.com/Firzyafriyan/Juragan/stargazers)
[![Forks](https://img.shields.io/github/forks/Firzyafriyan/Juragan?style=social)](https://github.com/Firzyafriyan/Juragan/network/members)

**Aplikasi Android layanan travel Haji & Umroh — daftar jamaah, panduan ibadah & doa, FAQ, pemesanan paket, hingga pembayaran, dalam satu genggaman.**

[🚀 Mulai Cepat](#-cara-pakai) • [🐛 Laporkan Bug](https://github.com/Firzyafriyan/Juragan/issues) • [💡 Request Fitur](https://github.com/Firzyafriyan/Juragan/issues)

</div>

---

## ✨ Kenapa Pakai Ini?

<div align="center">

| 🚫 **Travel Konvensional** | ✅ **Juragan** |
|:---:|:---:|
| Info paket hanya dari brosur/agen | Katalog paket lengkap di aplikasi, bisa dibaca kapan saja |
| Daftar harus datang ke kantor | Registrasi jamaah langsung dari HP (NIK) |
| Bingung urutan ibadah & bacaan doa | Panduan Haji & Umroh lengkap + doa per tahap |
| Ribet tanya-tanya admin | Menu Tanya/FAQ: jadwal, biaya, akomodasi, persiapan |

</div>

**Juragan** memudahkan calon jamaah dalam satu aplikasi: mulai dari **registrasi & login** (Jamaah berbasis NIK + khusus **Agen**), belanja **paket travel**, memesan, sampai **pembayaran** — dilengkapi **panduan ibadah Haji & Umroh** lengkap dengan niat, tata cara, dan doa per tahap, plus **jadwal sholat** harian. 🕋

---

## 🚀 Fitur

### 🔐 Akun & Peran
- 🧑‍🤝‍🧑 **Registrasi & Login Jamaah** berbasis **NIK** (dengan fitur *Lupa NIK*)
- 🤝 **Portal Khusus Agen** — login, register, home agen, lupa password
- 🗄️ **SQLite lokal** untuk penyimpanan data di perangkat

### 🕋 Panduan Ibadah Haji & Umroh
- 📖 **Hukum Ibadah** Haji & Umroh
- 🌙 **Niat Ihram** & **Niat Umroh**, **Talbiyah**
- 🤲 Kumpulan **doa**: Arafah, Sa'i, Tahallul, memasuki Masjidil Haram, kembali ke rumah
- 🕋 Tata cara **Tawaf Ifadah** & **Melempar Jumroh**
- 🖼️ **Galeri** panduan

### ❓ Tanya / FAQ
- 📅 Jadwal keberangkatan • 🏨 Akomodasi • 💰 Total biaya
- 🎒 Persiapan • 🔄 Pengembalian • 📞 Hubungi kami • ❔ Cara pemesanan

### 🛒 Paket, Pesan & Bayar
- 🏷️ **Shop** — katalog paket travel dari server
- 📄 **Detail paket** + **Pemesanan** (PesanActivity)
- 💳 **Pembayaran** langsung di aplikasi

### 🕌 Lainnya
- 🕌 **Jadwal Sholat** harian
- ⭐ Like/Favorit, 🔍 Search — navigasi *bottom navigation* modern
- 📡 Sinkronisasi API **PHP + MySQL** via **Volley**

---

## 📋 Persyaratan

| Kebutuhan | Keterangan |
|---|---|
| 📱 **Android** | Android 5.1 (API 22) atau lebih baru |
| 🛠️ **Android Studio** | Hedgehog/Iguana direkomendasikan |
| ☕ **JDK** | Java 8 (`VERSION_1_8`) |
| 🗄️ **Backend** | Web server PHP + MySQL (endpoint `juragandb`, cocok pakai XAMPP) |
| 📦 **Build** | Gradle wrapper sudah termasuk (`gradlew` / `gradlew.bat`) |

**Library utama:** AndroidX AppCompat, Material Components, Navigation Component, ConstraintLayout, **Volley**, **Dexter** (runtime permission).

---

## 🎮 Cara Pakai

### 1️⃣ Clone & Buka Project

```bash
git clone https://github.com/Firzyafriyan/Juragan.git
```

Buka folder `Juragan` lewat **Android Studio** dan tunggu *Gradle Sync* selesai.

### 2️⃣ Siapkan Backend PHP + MySQL

1. Letakkan folder API `juragandb` (berisi `register.php`, `login.php`, `RegJamaah.php`, `LogJamaah.php`, `getShop.php`, `getAgen.php`, `transaksi.php`, `uploadImage.php`) di web server lokal, misalnya **XAMPP** `htdocs/`
2. Buat database MySQL sesuai struktur yang dibutuhkan endpoint tersebut
3. Samakan jaringan HP/emulator dengan komputer server

### 3️⃣ Sesuaikan Alamat Server

Buka `app/src/main/java/com/cadenza/bottomnavigation/API/Db_Contract.java`, lalu sesuaikan IP dengan IP komputer server-mu:

```java
public static String ip = "10.10.176.166";  // ← ganti IP server kamu
```

### 4️⃣ Build & Jalankan ▶️

- **Android Studio:** klik tombol ▶️ *Run* (pilih emulator atau HP fisik)
- **Terminal:**

```bash
./gradlew assembleDebug        # hasil APK di app/build/outputs/apk/debug/
```

### 5️⃣ Daftar & Mulai 🕋

- Daftar sebagai **Jamaah** (pakai NIK) atau masuk sebagai **Agen**
- Jelajahi **panduan ibadah**, cek **jadwal sholat**, pilih **paket travel**, pesan, dan bayar 🎉

---

## 🗂️ Struktur Proyek

```text
Juragan/
├── app/src/main/java/com/cadenza/bottomnavigation/
│   ├── Frame/...          🔐 (splash, login utama)
│   ├── AGEN/              🤝 Login, Register, Home, Lupa Password Agen
│   ├── Panduan/           🕋 Panduan Haji & Umroh + doa per tahap + Galeri
│   ├── Tanya/             ❓ FAQ: jadwal, biaya, akomodasi, persiapan, dll
│   ├── API/               📡 Db_Contract.java — konfigurasi endpoint PHP
│   ├── adapter/ & model/  🧩 RecyclerView adapter & data model
│   ├── networking/        🌐 Helper jaringan
│   ├── RegJamaah / LogJamaah / LupaNIK   🧑‍🤝‍🧑 Autentikasi Jamaah
│   ├── Shop / DetailShop / Pesan / Pembayaran   🛒 Alur belanja
│   ├── JadwalSholat     🕌 Jadwal sholat
│   └── DatabaseHelper   🗄️ SQLite lokal
├── app/src/main/res/      🎨 Layout, menu bottom-nav, values
├── build.gradle           🔨 Konfigurasi Gradle (AGP 8.1.1, compileSdk 34)
└── settings.gradle        ⚙️ Root project
```

---

## ⚠️ Catatan

> 🎓 Proyek ini untuk **pembelajaran/pengembangan**. Beberapa hal yang perlu diperhatikan sebelum production:
> - Koneksi API masih **HTTP cleartext** (`usesCleartextTraffic="true"`) — wajib migrasi ke **HTTPS** + domain resmi
> - Alamat **IP server hardcoded** di `Db_Contract.java` — idealnya dipindah ke `buildConfigField` atau config file
> - Endpoint & struktur tabel backend perlu disesuaikan dengan kebutuhanmu

---

## 🛠️ Kustomisasi Lanjutan

- 🌐 Ganti alamat API → `API/Db_Contract.java`
- 🏷️ Ubah nama aplikasi → `res/values/strings.xml` (`app_name`)
- 🎨 Tema & warna → `res/values/themes.xml` + Material Components
- 📦 Tambah dependensi → `app/build.gradle`

---

## 🗺️ Pengembangan Selanjutnya

- [ ] 🔒 Migrasi API ke **HTTPS** + domain production
- [ ] 🔔 Push notification status pemesanan & jadwal keberangkatan
- [ ] 💳 Integrasi **payment gateway** (Midtrans/Xendit)
- [ ] 🗺️ Peta & tracking bus/lokasi rombongan
- [ ] 🌙 Mode gelap (dark mode)
- [ ] 🧑‍💼 Dashboard admin web untuk kelola paket & transaksi
- [ ] 🔄 Migrasi ke **Kotlin** + coroutines/Flow

---

<div align="center">

### 🌈 🕋 Dibuat dengan ❤️ dan ☕ untuk calon tamu Allah yang istiqomah 🕋 🌈

⭐ **Star repo ini kalau bermanfaat!** ⭐

</div>
