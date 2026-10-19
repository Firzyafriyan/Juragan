package com.cadenza.bottomnavigation.AGEN;

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
import com.cadenza.bottomnavigation.LogJamaahActivity;
import com.cadenza.bottomnavigation.MainActivity;
import com.cadenza.bottomnavigation.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogAgenActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView btnLupaPW;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_agen);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLupaPW = findViewById(R.id.btnLupaPW);

        btnLupaPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LupaPwAgenActivity.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        boolean isValid = true;

        if (email.isEmpty()) {
            etEmail.setError("Email harus diisi");
            isValid = false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password harus diisi");
            isValid = false;
        }

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            // Ganti urlLogin dengan URL Anda
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlLogin + "?email=" + email + "&password=" + password,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                int code = jsonResponse.getInt("code");
                                String status = jsonResponse.getString("status");

                                if (code == 200) {
                                    // Simpan data ke SharedPreferences
                                    JSONObject userObject = jsonResponse.getJSONObject("data_agen");
                                    String id_agen = userObject.getString("id_agen");
                                    String nama = userObject.getString("nama");
                                    String email = userObject.getString("email");
                                    String password = userObject.getString("password");
                                    String no_hp = userObject.getString("no_hp");
                                    String reward = userObject.getString("reward");

                                    sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                    // Simpan data ke SharedPreferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("id_agen", id_agen);
                                    editor.putString("nama", nama);
                                    editor.putString("email", email);
                                    editor.putString("password", password);
                                    editor.putString("no_hp", no_hp);
                                    editor.putString("reward", reward);;
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.apply();

                                    etEmail.setText("");
                                    etPassword.setText("");
                                    startActivity(new Intent(getApplicationContext(), HomeAgenActivity.class));
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
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // Pengguna sudah login sebelumnya, arahkan ke halaman beranda
            Intent intent = new Intent(LogAgenActivity.this, HomeAgenActivity.class);
            startActivity(intent);
            finish();
        }
    }
    }
