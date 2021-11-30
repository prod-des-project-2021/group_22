package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Dashboard extends AppCompatActivity {

    ImageButton dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboard = (ImageButton) findViewById(R.id.dashboard);

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileMainPage();
            }
        });
    }
    public void openProfileMainPage()
    {
        Intent intent = new Intent(this, ProfileMainPage.class);
        startActivity(intent);
    }
}