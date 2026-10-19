package com.cadenza.bottomnavigation;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.cadenza.bottomnavigation.networking.ClientAsyncTask;
import com.cadenza.bottomnavigation.networking.DaftarKota;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JadwalSholat extends AppCompatActivity {

    private List<DaftarKota> listDaftarKota;
    private ArrayAdapter<DaftarKota> mDaftarKotaAdapter;
    TextView tvTanggal, tvSubuh, tvDzuhur, tvAshar, tvMaghrib, tvIsya;
    private ImageView greetImg;
    private TextView greetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_sholat);

        tvTanggal = findViewById(R.id.tv_tanggal);
        tvSubuh = findViewById(R.id.tv_subuh);
        tvDzuhur = findViewById(R.id.tv_dzuhur);
        tvAshar = findViewById(R.id.tv_ashar);
        tvMaghrib = findViewById(R.id.tv_maghrib);
        tvIsya = findViewById(R.id.tv_isya);

        Spinner kota = findViewById(R.id.kota);
        greetImg = findViewById(R.id.greeting_img);
        greetText = findViewById(R.id.greeting_text);

        listDaftarKota = new ArrayList<>();
        mDaftarKotaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listDaftarKota);
        mDaftarKotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kota.setAdapter(mDaftarKotaAdapter);
        kota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DaftarKota kota = mDaftarKotaAdapter.getItem(position);
                loadJadwal(kota.getId());
            }
        });

        loadKota();
        greeting();
    }

    @SuppressLint("SetTextI18n")
    private void greeting() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            greetText.setText("Selamat Pagi, sudah sholat Subuh?");
            greetImg.setImageResource(R.drawable.img_default_half_morning);
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            greetText.setText("Selamat Siang, sudah sholat Dzuhur?");
            greetImg.setImageResource(R.drawable.img_default_half_afternoon);
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            greetText.setText("Selamat Sore, sudah sholat Ashar?");
            greetImg.setImageResource(R.drawable.img_default_half_without_sun);
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            greetText.setText("Selamat Malam, sudah sholat Maghrib?\njangan tidur dulu ya, lanjut sholat Isya");
            greetText.setTextColor(Color.BLACK);
            greetImg.setImageResource(R.drawable.img_default_half_night);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void loadJadwal(Integer id) {
        try {
            String idKota = id.toString();

            SimpleDateFormat current = new SimpleDateFormat("yyyy-MM-dd");
            String tanggal = current.format(new Date());

            String url = "https://api.banghasan.com/sholat/format/json/jadwal/kota/" + idKota + "/tanggal/" + tanggal;
            ClientAsyncTask task = new ClientAsyncTask(this, new ClientAsyncTask.OnPostExecuteListener() {
                @Override
                public void onPostExecute(String result) {

                    Log.d("JadwalData", result);
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        JSONObject objJadwal = jsonObj.getJSONObject("jadwal");
                        JSONObject obData = objJadwal.getJSONObject("data");

                        tvTanggal.setText(obData.getString("tanggal"));
                        tvSubuh.setText(obData.getString("subuh"));
                        tvDzuhur.setText(obData.getString("dzuhur"));
                        tvAshar.setText(obData.getString("ashar"));
                        tvMaghrib.setText(obData.getString("maghrib"));
                        tvIsya.setText(obData.getString("isya"));

                        Log.d("dataJadwal", obData.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadKota() {
        try {
            String url = "https://api.banghasan.com/sholat/format/json/kota";
            ClientAsyncTask task = new ClientAsyncTask(this, new ClientAsyncTask.OnPostExecuteListener() {
                @Override
                public void onPostExecute(String result) {

                    Log.d("KotaData", result);
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        JSONArray jsonArray = jsonObj.getJSONArray("kota");
                        DaftarKota daftarKota;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            daftarKota = new DaftarKota();
                            daftarKota.setId(obj.getInt("id"));
                            daftarKota.setNama(obj.getString("nama"));
                            listDaftarKota.add(daftarKota);
                        }
                        mDaftarKotaAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            task.execute(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
