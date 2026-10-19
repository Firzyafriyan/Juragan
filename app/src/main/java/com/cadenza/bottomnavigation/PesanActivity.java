package com.cadenza.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cadenza.bottomnavigation.API.Db_Contract;

import org.json.JSONException;
import org.json.JSONObject;

public class PesanActivity extends AppCompatActivity {
    ImageView btnKembali;

    TextView txt_nama_paket_dan_lama_waktu, txt_tanggal_keberangkatan, txt_nama_agen, txt_email_agen, txt_no_hp_agen;
    Button btnLanjutkan;
    EditText etUkuranBaju;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        // Inisialisasi variabel
        txt_nama_paket_dan_lama_waktu = findViewById(R.id.txt_nama_paket_dan_lama_waktu);
        txt_tanggal_keberangkatan = findViewById(R.id.txt_tanggal_keberangkatan);
        txt_nama_agen = findViewById(R.id.txt_nama_agen);
        txt_email_agen = findViewById(R.id.txt_email_agen);
        txt_no_hp_agen = findViewById(R.id.txt_no_hp_agen);
        btnLanjutkan = findViewById(R.id.btnLanjutkan);
        etUkuranBaju = findViewById(R.id.et_ukuran_baju);
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

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
        txt_nama_paket_dan_lama_waktu.setText(nama_paket + " " + lama_waktu + " Hari");
        txt_tanggal_keberangkatan.setText(" " + keberangkatan);

        getAgenData();


        btnKembali = findViewById(R.id.btnKembali);
        // Menambahkan onClickListener untuk tombol kembali
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PesanActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Mengakhiri activity dan kembali ke activity sebelumnya
            }
        });

        btnLanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PesanActivity.this, PembayaranActivity.class);

                // mendapatkan nilai dari TextView
                intent.putExtra("id", id);
                intent.putExtra("id_paket", id_paket);
                intent.putExtra("nama_paket", nama_paket);
                intent.putExtra("keberangkatan", keberangkatan);
                intent.putExtra("pilihan_paket", pilihan_paket);
                intent.putExtra("harga", harga);
                intent.putExtra("lama_waktu", lama_waktu);
                intent.putExtra("ukuran_baju", etUkuranBaju.getText().toString());

                startActivity(intent);
            }
        });


    }
    private void getAgenData() {
        String agenId = String.valueOf(sharedPreferences.getString("id_agen", ""));
        // Ganti URL_API dengan URL API Anda
        String url = Db_Contract.urlgetagen + "?id_agen=" + agenId;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("JSON Response", response.toString());
                        try {
                            int code = response.getInt("code");

                            if (code == 200) {
                                JSONObject dataAgen = response.getJSONObject("data_agen");

                                String namaAgen = dataAgen.getString("nama");
                                String emailAgen = dataAgen.getString("email");
                                String noHpAgen = dataAgen.getString("no_hp");

                                // Set data agen ke TextView
                                txt_nama_agen.setText(namaAgen);
                                txt_email_agen.setText(emailAgen);
                                txt_no_hp_agen.setText(noHpAgen);
                            } else {
                                // Agen tidak ditemukan, tambahkan penanganan kesalahan di sini
                                // Misalnya, tampilkan pesan kesalahan ke pengguna
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Tambahkan penanganan kesalahan jaringan di sini
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}