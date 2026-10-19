package com.cadenza.bottomnavigation.Panduan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cadenza.bottomnavigation.HomeFragment;
import com.cadenza.bottomnavigation.R;

public class panduanActivity extends AppCompatActivity {
    private Button btnHukum, btnIhram, btnArafah, btnJumrah, btnTawaf, btnSai, btnTahallul,
            btnKembaliRmh, btnHkmUmroh, btnUmroh, btnStlhMemakai, btnTalbiyah, btnMekkah, btnMasjidil;
    private ImageView btnKmbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);

        btnHukum = findViewById(R.id.btnHukum);
        btnIhram = findViewById(R.id.btnIhram);
        btnArafah =  findViewById(R.id.btnArafah);
        btnJumrah = findViewById(R.id.btnJumrah);
        btnTawaf = findViewById(R.id.btnTawaf);
        btnSai = findViewById(R.id.btnSai);
        btnTahallul = findViewById(R.id.btnTahallul);
        btnKembaliRmh = findViewById(R.id.btnKembaliRmh);
        btnHkmUmroh = findViewById(R.id.btnHkmUmroh);
        btnUmroh = findViewById(R.id.btnUmroh);
        btnStlhMemakai = findViewById(R.id.btnStlhMemakai);
        btnTalbiyah = findViewById(R.id.btnTalbiayah);
        btnMekkah = findViewById(R.id.btnMekkah);
        btnMasjidil = findViewById(R.id.btnMasjidil);
        btnKmbl = findViewById(R.id.btnKmbl);

        btnHukum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HukumIbadahHajiActivity.class));
            }
        });
        btnIhram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NiatIhramActivity.class));
            }
        });
        btnArafah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoaArafahActivity.class));
            }
        });
        btnJumrah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MelemparJumrohActivity.class));
            }
        });
        btnTawaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TawafIfadahActivity.class));
            }
        });
        btnSai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoaSaiActivity.class));
            }
        });
        btnTahallul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoaTahallulActivity.class));
            }
        });
        btnKembaliRmh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoaKembaliKerumahActivity.class));
            }
        });
        btnHkmUmroh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HukumIbadahUmrohActivity.class));
            }
        });
        btnUmroh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NiatUmrohActivity.class));
            }
        });
        btnStlhMemakai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoaSetelahMemakaiActivity.class));
            }
        });
        btnTalbiyah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TalbiyahActivity.class));
            }
        });
        btnMekkah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoaMemasukiActivity.class));
            }
        });
        btnMasjidil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoaMasukMasjidilActivity.class));
            }
        });
        btnKmbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kembali ke HomeFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeFragmentContainer, new HomeFragment())
                        .commit();
            }
        });


    }
}