package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;

public class ProfileMainPage extends AppCompatActivity {
    TextView name, age,rez,cals,ideal, weight , goal, liters;
    UserDatabase db;
    Button settings;
    DecimalFormat df;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main_page);
        navigation = findViewById(R.id.bottom_nav);
        age = findViewById(R.id.age);
        weight = findViewById(R.id.kg);
        name = findViewById(R.id.name);
        rez = findViewById(R.id.bmi_result);
        cals = findViewById(R.id.calories);
        ideal = findViewById(R.id.idealkg);
        settings = (Button) findViewById(R.id.settings);
        goal = findViewById(R.id.goal);
        liters = findViewById(R.id.liters);
        db = new UserDatabase(this);
        df = new DecimalFormat("0.00");

        navigation.setSelectedItemId(R.id.nav_profile);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_eating:
                        startActivity(new Intent(getApplicationContext(),MealPlan.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_workout:
                        startActivity(new Intent(getApplicationContext(),WorkoutDiary.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        Cursor res = db.getdata();
        StringBuffer buffer0 = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        StringBuffer buffer3 = new StringBuffer();
        StringBuffer buffer5 = new StringBuffer();
        StringBuffer buffer8 = new StringBuffer();
        StringBuffer buffer9 = new StringBuffer();
        StringBuffer buffer11 = new StringBuffer();

        while (res.moveToNext())
        {
            buffer0.append(res.getString(0));
            buffer1.append(res.getString(1));
            buffer3.append(res.getString(3));
            buffer5.append(res.getString(5));
            buffer8.append(res.getString(8));
            buffer9.append(res.getString(9));
            buffer11.append(res.getString(11));

        }

        String nameTXT = buffer0.toString();
        String ageTXT = buffer1.toString();
        String weightTXT = buffer3.toString();
        String goalTXT= buffer5.toString();
        String caloriesTXT= buffer8.toString();
        String idealTXT = buffer9.toString();
        String resTXT = buffer11.toString();




        name.setText(nameTXT);
        rez.setText(resTXT);
        cals.setText(caloriesTXT);
        age.setText(ageTXT);
        weight.setText(weightTXT+"kg");
        ideal.setText(idealTXT + "kg");
        goal.setText(goalTXT);

        float weightfloat = Integer.parseInt(weightTXT);
        String water = String.valueOf(Math.round(weightfloat/20));
        liters.setText(water+"L");


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
                finish();
            }
        });

    }
    public void openSettings()
    {
        Intent  intent = new Intent(this, Settings.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}