package com.cadenza.bottomnavigation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cadenza.bottomnavigation.API.Db_Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegJamaahActivity extends AppCompatActivity {

    private EditText etNamalengkap, etNik, etNo_hp, etTgllahir, etAlamat, etNmaBapak;
    private Spinner etGender, etIdAgen;
    private Button btnRegJamaah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_jamaah);

        etNamalengkap = findViewById(R.id.etNamalengkap);
        etNik = findViewById(R.id.etNik);
        etNo_hp = findViewById(R.id.etNo_hp);
        etTgllahir = findViewById(R.id.etTgllahir);
        etGender = findViewById(R.id.etGender);
        etIdAgen = findViewById(R.id.etIdAgen);
        etAlamat = findViewById(R.id.etAlamat);
        etNmaBapak = findViewById(R.id.etNmaBapak);
        btnRegJamaah = findViewById(R.id.btnRegJamaah);

        // Inisialisasi Spinner Gender
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etGender.setAdapter(genderAdapter);

        ArrayAdapter<CharSequence> IdAgenAdapter = ArrayAdapter.createFromResource(this,
                R.array.IdAgen_options, android.R.layout.simple_spinner_item);
        IdAgenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etIdAgen.setAdapter(IdAgenAdapter);

        // Tambahkan listener untuk menampilkan DatePickerDialog saat etTgllahir diklik
        etTgllahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnRegJamaah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama_lengkap = etNamalengkap.getText().toString();
                String NIK = etNik.getText().toString();
                String no_hp = etNo_hp.getText().toString();
                String tgl_lahir = etTgllahir.getText().toString();
                String jenis_kelamin = etGender.getSelectedItem().toString();
                String id_agen = etIdAgen.getSelectedItem().toString();
                String alamat = etAlamat.getText().toString();
                String nama_bapak = etNmaBapak.getText().toString();

                boolean isValid = true;

                if (TextUtils.isEmpty(nama_lengkap)) {
                    etNamalengkap.setError("Nama lengkap harus diisi");
                    isValid = false;
                }

                if (TextUtils.isEmpty(NIK)) {
                    etNik.setError("NIK harus diisi");
                    isValid = false;
                } else if (NIK.length() != 16) {
                    etNik.setError("NIK harus terdiri dari 16 angka");
                    isValid = false;
                }

                if (TextUtils.isEmpty(no_hp)) {
                    etNo_hp.setError("Nomor HP harus diisi");
                    isValid = false;
                }

                if (TextUtils.isEmpty(tgl_lahir)) {
                    etTgllahir.setError("Tanggal Lahir harus diisi");
                    isValid = false;
                }
                if (TextUtils.isEmpty(alamat)) {
                    etAlamat.setError("Alamat harus diisi");
                    isValid = false;
                }

                if (TextUtils.isEmpty(nama_bapak)) {
                    etNmaBapak.setError("Nama Ayah harus diisi");
                    isValid = false;
                }

                // Jika data valid, panggil metode registerUser
                if (isValid) {
                    registerUser(nama_lengkap, NIK, no_hp, tgl_lahir, jenis_kelamin, id_agen, alamat, nama_bapak);
                }
            }
        });
    }


    // Method untuk menampilkan DatePickerDialog
    private void showDatePickerDialog() {
        // Ambil tanggal sekarang sebagai default
        int year, month, day;
        if (etTgllahir.getText().toString().isEmpty()) {
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH);
            day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        } else {
            String[] dateParts = etTgllahir.getText().toString().split("-");
            year = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]) - 1;
            day = Integer.parseInt(dateParts[2]);
        }

        // Buat DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update teks etTgllahir saat tanggal dipilih
                        etTgllahir.setText(String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth));
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void registerUser(String nama_lengkap, String NIK, String no_hp, String tgl_lahir, String jenis_kelamin, String id_agen, String alamat, String nama_bapak) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlRegjamaah,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return createRequestParams(nama_lengkap, NIK, no_hp, tgl_lahir, jenis_kelamin, id_agen, alamat, nama_bapak);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            int kode = jsonResponse.getInt("kode");

            if (kode == 201) {
                Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LogJamaahActivity.class);
                startActivity(intent);
                finish();
            } else if (kode == 406) {
                Toast.makeText(getApplicationContext(), "NIK sudah terdaftar", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Registrasi Gagal", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleError(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
    }

    private Map<String, String> createRequestParams(String nama_lengkap, String NIK, String no_hp, String tgl_lahir, String jenis_kelamin, String id_agen, String alamat, String nama_bapak) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("nama_lengkap", nama_lengkap);
        requestParams.put("NIK", NIK);
        requestParams.put("no_hp", no_hp);
        requestParams.put("tgl_lahir", tgl_lahir);
        requestParams.put("jenis_kelamin", jenis_kelamin);
        requestParams.put("id_agen", id_agen);
        requestParams.put("alamat", alamat);
        requestParams.put("nama_bapak", nama_bapak);
        return requestParams;
    }
}
