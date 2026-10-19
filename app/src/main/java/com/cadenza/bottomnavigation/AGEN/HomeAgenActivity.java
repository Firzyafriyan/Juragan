package com.cadenza.bottomnavigation.AGEN;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cadenza.bottomnavigation.HomeFragment;
import com.cadenza.bottomnavigation.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeAgenActivity extends AppCompatActivity {
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_agen);

        //hide line status bar

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.body_container , new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){

                    case R.id.nav_beranda:
                        fragment = new BerandaFragment();
                        break;
                    case R.id.nav_target:
                        fragment = new TargetJamaahFragment();
                        break;
                    case R.id.nav_namajamaah:
                        fragment = new NamaJamaahFragment();
                        break;
                    case R.id.nav_datapribadi:
                        fragment = new DataPribadiFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();


                return true;
            }
        });
    }
}
