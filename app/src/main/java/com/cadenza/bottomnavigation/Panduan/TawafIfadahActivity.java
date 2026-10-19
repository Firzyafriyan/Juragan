package com.cadenza.bottomnavigation.Panduan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cadenza.bottomnavigation.R;

public class TawafIfadahActivity extends AppCompatActivity {
    private ImageView btnkembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tawaf_ifadah);

        btnkembali = findViewById(R.id.btnKembali);

        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), panduanActivity.class));
            }
        });

    }
}