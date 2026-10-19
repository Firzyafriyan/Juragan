package com.cadenza.bottomnavigation.Panduan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cadenza.bottomnavigation.R;

public class DoaTahallulActivity extends AppCompatActivity {

    private ImageView btnkembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doa_tahallul);

        btnkembali = findViewById(R.id.btnKembali);

        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), panduanActivity.class));
            }
        });


    }
}