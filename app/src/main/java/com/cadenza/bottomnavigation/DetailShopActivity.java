package com.cadenza.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailShopActivity extends AppCompatActivity {

    ImageView btnKembali;

    Button btnPesanSekarang;

    TextView txt_nama_paket, txt_keberangkatan, txt_harga, txt_lama_waktu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shop);

        // Inisialisasi variabel
        txt_nama_paket = findViewById(R.id.txt_nama_paket);
        txt_keberangkatan = findViewById(R.id.txt_keberangkatan);
        txt_harga = findViewById(R.id.txt_harga_paket);
        txt_lama_waktu = findViewById(R.id.txt_lama_waktu);
        btnPesanSekarang = findViewById(R.id.btn_pesan_sekarang);



        // Mendapatkan data yang diterima melalui Intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String id_paket = intent.getStringExtra("id_paket");
        String nama_paket = intent.getStringExtra("nama_paket");
        String keberangkatan = intent.getStringExtra("keberangkatan");
        String pilihan_paket = intent.getStringExtra("pilihan_paket");
        String harga = intent.getStringExtra("harga");
        String lama_waktu = intent.getStringExtra("lama_waktu");

        //mengatur nilai TextView dengan data yang diterima
        txt_nama_paket.setText(nama_paket);
        txt_keberangkatan.setText(" " +formatTanggal(keberangkatan));
        txt_harga.setText("Harga " + formatRupiah(harga));
        txt_lama_waktu.setText(" "+lama_waktu + " Hari");

        btnPesanSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailShopActivity.this, PesanActivity.class);

                // mendapatkan nilai dari TextView
                intent.putExtra("id", id);
                intent.putExtra("id_paket", id_paket);
                intent.putExtra("nama_paket", nama_paket);
                intent.putExtra("keberangkatan", keberangkatan);
                intent.putExtra("pilihan_paket", pilihan_paket);
                intent.putExtra("harga", harga);
                intent.putExtra("lama_waktu", lama_waktu);

                startActivity(intent);
            }
        });




        btnKembali = findViewById(R.id.btnKembali);
        // Menambahkan onClickListener untuk tombol kembali
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailShopActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Mengakhiri activity dan kembali ke activity sebelumnya
            }
        });
    }

    private String formatTanggal(String tanggal) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdf.parse(tanggal);

            SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            return output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return tanggal;
        }
    }
    private String formatRupiah(String harga) {
        try {
            // Mengubah string harga ke dalam bentuk double
            double hargaDouble = Double.parseDouble(harga);

            // Menggunakan NumberFormat untuk memformat mata uang Rupiah
            NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            return rupiahFormat.format(hargaDouble);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return harga;
        }
    }
}