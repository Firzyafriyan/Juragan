# Dokumentasi Proyek Juragan Travel

Selamat datang di pusat dokumentasi teknis aplikasi **Juragan Travel** (aplikasi Android travel haji dan umrah berbasis Java).

Folder ini memuat dokumentasi terperinci mengenai arsitektur perangkat lunak, spesifikasi API, katalog modul, temuan kendala teknis (bug), serta rekomendasi peningkatan sistem.

---

## 📑 Daftar Isi Dokumentasi

| Berkas | Deskripsi Singkat |
| :--- | :--- |
| **[ANALISIS_PROYEK.md](file:///c:/Dev/Projects/Juragan/docs/ANALISIS_PROYEK.md)** | **Laporan Analisis Komprehensif**:<br>Ringkasan eksekutif, identitas proyek, arsitektur modul, user flow Jamaah & Agen, evaluasi kualitas kode, dan ringkasan penilaian teknis. |
| **[ARSITEKTUR_DAN_API.md](file:///c:/Dev/Projects/Juragan/docs/ARSITEKTUR_DAN_API.md)** | **Spesifikasi Arsitektur & API**:<br>Diagram arsitektur sistem, spesifikasi lengkap 7 endpoint backend PHP Juragan, integrasi API publik jadwal sholat Bang Hasan, diagram relasi entitas (ERD), dan skema basis data. |
| **[TEMUAN_BUG_DAN_REKOMENDASI.md](file:///c:/Dev/Projects/Juragan/docs/TEMUAN_BUG_DAN_REKOMENDASI.md)** | **Audit Kualitas, Bug & Roadmap**:<br>Daftar temuan bug fatal (termasuk crash runtime pada tab navigasi), audit keamanan data, solusi kode siap pakai (*code fixes*), dan tahapan rencana refactoring (Roadmap Fase 1-3). |

---

## 🚀 Ringkasan Singkat Proyek

- **Nama Aplikasi**: Juragan (Travel Umrah & Haji)
- **ID Paket**: `com.cadenza.bottomnavigation`
- **Teknologi**: Android Native (Java 8), Gradle 8.0, Android Gradle Plugin 8.1.1
- **Target SDK**: Min SDK 22, Target SDK 32, Compile SDK 34
- **Peran Pengguna**:
  1. **Jamaah**: Akses panduan manasik, doa harian, jadwal sholat, pemesanan paket travel, pembayaran dan upload bukti transfer.
  2. **Agen**: Portal manajemen mitra untuk pencatatan dan pembinaan jamaah umrah/haji.
- **Backend**: PHP Native + MySQL (`juragandb`) + Third-party Prayer Times API (`api.banghasan.com`).

---

> [!TIP]
> Jika Anda ingin meninjau atau memperbaiki isu kritis pada aplikasi, disarankan untuk membaca **[TEMUAN_BUG_DAN_REKOMENDASI.md](file:///c:/Dev/Projects/Juragan/docs/TEMUAN_BUG_DAN_REKOMENDASI.md)** terlebih dahulu, khususnya bagian **BUG-01** (crash fatal navigasi) dan **BUG-03** (mismatch penyimpanan sesi logout agen).
