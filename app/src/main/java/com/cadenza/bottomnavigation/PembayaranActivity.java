package com.cadenza.bottomnavigation;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cadenza.bottomnavigation.API.Db_Contract;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PembayaranActivity extends AppCompatActivity {


    ImageView btnKembali;

    private SharedPreferences sharedPreferences;
    Uri selecteduri;
    ImageView imageView;
    Button btnSelectImage,btnTransaksi
//            btnUploadGambar
                    ;
    ProgressBar progressBar;
    Bitmap bitmap;
    String url = "";
    String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnTransaksi = findViewById(R.id.btnTransaksi);
//        btnUploadGambar = findViewById(R.id.btnUploadGambar);
        btnKembali = findViewById(R.id.btnKembali);
        imageView = findViewById(R.id.imView);

        // Mendapatkan NIK dari SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String NIK = sharedPreferences.getString("NIK", ""); // Sesuaikan dengan key yang Anda gunakan

        // Mendapatkan data yang diterima melalui Intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String id_paket = intent.getStringExtra("id_paket");
        String nama_paket = intent.getStringExtra("nama_paket");
        String keberangkatan = intent.getStringExtra("keberangkatan");
        String pilihan_paket = intent.getStringExtra("pilihan_paket");
        String harga = intent.getStringExtra("harga");
        String lama_waktu = intent.getStringExtra("lama_waktu");
        String ukuran_baju = intent.getStringExtra("ukuran_baju");

//        Toast.makeText(PembayaranActivity.this, NIK, Toast.LENGTH_SHORT).show();
//        Toast.makeText(PembayaranActivity.this, sharedPreferences.getString("NIK", ""), Toast.LENGTH_SHORT).show();
//
//        btnUploadGambar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadImage(idPemesanan);
//            }
//        });



        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PembayaranActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Mengakhiri activity dan kembali ke mainactivity
            }
        });

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(PembayaranActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                                Intent intent  = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }

        });
        // Mendapatkan tanggal saat ini
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(new Date());


        btnTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaksi();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 || requestCode == RESULT_OK || data != null || data.getData() != null) {
            selecteduri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(selecteduri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                imageStore(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imageBytes = stream.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            Toast.makeText(this, "Image is too large", Toast.LENGTH_SHORT).show();
        }
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }
    private void askpermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                chooseImage();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(PembayaranActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                Toast.makeText(PembayaranActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

            }


        };
    }


    private void transaksi() {
        // Mendapatkan data yang diterima melalui Intent
        Intent intent = getIntent();
        String id_paket = intent.getStringExtra("id_paket");
        String ukuran_baju = intent.getStringExtra("ukuran_baju");

        String url = Db_Contract.urltransaksi; // Ganti dengan URL API transaksi Anda

        // Membuat permintaan StringRequest menggunakan POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse JSON response

                            Log.d(TAG, "onResponse: "+ response);
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            if ("success".equals(status)) {
                                // Pemesanan/Transaksi berhasil
                                Toast.makeText(PembayaranActivity.this, "Pemesanan Berhasil: " + message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SearchFragment.class);
                                startActivity(intent);
                                finish();

                                // Ambil ID pemesanan untuk upload gambar
                                JSONObject data = jsonResponse.getJSONObject("data");
                                String idPemesanan = data.getString("id_pemesanan");

                                // Lanjutkan dengan uploadImage
                                uploadImage(idPemesanan);
                            } else {
                                // Pemesanan/Transaksi gagal
                                Toast.makeText(PembayaranActivity.this, "Pemesanan Gagal: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PembayaranActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onResponse: "+ e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PembayaranActivity.this, "Tambah Perkembangan Gagal", Toast.LENGTH_SHORT).show();
                        Log.d("PembayaranActivity", "Error: " + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Menyiapkan data yang akan dikirim ke server
                Map<String, String> params = new HashMap<>();

                // Ambil ID_Peternakan pengguna dari penyimpanan
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String NIK = sharedPreferences.getString("NIK", "");

                // Mendapatkan tanggal saat ini
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDate = sdf.format(new Date());

                params.put("tgl_pemesanan", currentDate);
                params.put("NIK", NIK);
                params.put("ukuran_baju", ukuran_baju);
                params.put("id_paket", id_paket);

                return params;
            }
        };

        // Menambahkan request ke antrian request Volley
        RequestQueue requestQueue = Volley.newRequestQueue(PembayaranActivity.this);
        requestQueue.add(stringRequest);
    }

    private void uploadImage(String idPemesanan) {
        String url = Db_Contract.urluploadimage;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String serverResponse) {
                        try {
                            // Parse the JSON response
                            JSONObject jsonResponse = new JSONObject(serverResponse);
                            String message = jsonResponse.getString("message");
                                // Buka halaman detail pemesanan
                                Intent intent = new Intent(PembayaranActivity.this, MainActivity.class);
                                intent.putExtra("id_pemesanan", idPemesanan);
                                startActivity(intent);
                                finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PembayaranActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + serverResponse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError serverError) {
                        Toast.makeText(PembayaranActivity.this, "Upload Gambar Gagal", Toast.LENGTH_SHORT).show();
                        Log.d("PembayaranActivity", "Error: " + serverError);

                        // Menghilangkan ProgressDialog jika terjadi kesalahan
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                // Ambil ID_Peternakan pengguna dari penyimpanan
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String NIK = sharedPreferences.getString("NIK", "");

                params.put("id_pemesanan", idPemesanan);
                params.put("NIK", NIK);
                params.put("image", encodedImage);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PembayaranActivity.this);
        requestQueue.add(request);
    }


}