package com.cadenza.bottomnavigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LupaNIKActivity extends AppCompatActivity {

    private Button btnLPNIK;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_nikactivity);

        btnLPNIK = findViewById(R.id.btnLPNIK);

        btnLPNIK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wpurl="https://wa.me/+6289686404940?text=Assalamualaikum ADMIN" + "%0A" +
                        "Nama :" + "%0A" +
                        "Nama Ayah :" + "%0A" +
                        "Tanggal lahir :" + "%0A" +
                        "Disini saya ingin menanyakan NIK saya dengan identitas yang tertera";

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });

}   }