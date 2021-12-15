package com.example.wooork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
    bottomNav.setOnNavigationItemReselectedListener((BottomNavigationView.OnNavigationItemReselectedListener) navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch(item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new Home_Fragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new Profile_Fragment();
                            break;
                        case R.id.nav_eating:
                            selectedFragment = new Food_Fragment();
                            break;
                        case R.id.nav_workout:
                            selectedFragment = new Workout_Fragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;

                }

            };
}