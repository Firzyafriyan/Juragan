package com.cadenza.bottomnavigation.AGEN;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cadenza.bottomnavigation.R;

public class LupaPwAgenActivity extends AppCompatActivity {

    private Button btnLpPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_pw_agen);

        btnLpPw = findViewById(R.id.btnLpPw);

        btnLpPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wpurl="https://wa.me/+6289686404940?text=Assalamualaikum ADMIN" + "%0A" +
                        "Nama   :" + "%0A" +
                        "Email  :" + "%0A" +
                        "Disini saya ingin menanyakan password dengan identitas yang tertera";

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });


}}