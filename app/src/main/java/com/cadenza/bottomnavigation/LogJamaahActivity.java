package com.cadenza.bottomnavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class LogJamaahActivity extends AppCompatActivity {

    private EditText etNik;
    private Button btnLogin;
    private TextView btnRegister, btnLupaNik;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_jamaah);

        etNik = findViewById(R.id.etNik);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnLupaNik = findViewById(R.id.btnLupaNik);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegJamaahActivity.class));
            }
        });
        btnLupaNik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LupaNIKActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        checkLoginStatus();
    }

    private void loginUser() {
        String NIK = etNik.getText().toString();

        if (!NIK.isEmpty()) {

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            // Ganti urlLogin dengan URL Anda
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlLogjamaah + "?NIK=" + NIK,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                int code = jsonResponse.getInt("code");
                                String status = jsonResponse.getString("status");

                                if (code == 200) {

                                    // Simpan data ke SharedPreferences
                                    JSONObject userObject = jsonResponse.getJSONObject("data_jamaah");
                                    String NIK = userObject.getString("NIK");
                                    String nama_lengkap = userObject.getString("nama_lengkap");
                                    String alamat = userObject.getString("alamat");
                                    String no_hp = userObject.getString("no_hp");
                                    String tgl_lahir = userObject.getString("tgl_lahir");
                                    String jenis_kelamin = userObject.getString("jenis_kelamin");
                                    String id_agen = userObject.getString("id_agen");
                                    String nama_bapak = userObject.getString("nama_bapak");

                                    sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                    // Simpan data ke SharedPreferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("NIK", NIK);
                                    editor.putString("nama_lengkap", nama_lengkap);
                                    editor.putString("alamat", alamat);
                                    editor.putString("no_hp", no_hp);
                                    editor.putString("tgl_lahir", tgl_lahir);
                                    editor.putString("jenis_kelamin", jenis_kelamin);
                                    editor.putString("id_agen", id_agen);
                                    editor.putString("nama_bapak", nama_bapak);
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.apply();

                                    etNik.setText("");
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Login Gagal: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("NIK", NIK);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // Pengguna sudah login sebelumnya, arahkan ke halaman beranda
            Intent intent = new Intent(LogJamaahActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
