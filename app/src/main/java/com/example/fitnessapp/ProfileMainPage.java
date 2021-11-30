package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ProfileMainPage extends AppCompatActivity {
    TextView name, bmi,rez,cals,ideal;
    UserDatabase db;
    ImageButton dashboard;
    DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main_page);

        name = findViewById(R.id.name);
        bmi = findViewById(R.id.bmi);
        rez = findViewById(R.id.res);
        cals = findViewById(R.id.cal);
        ideal = findViewById(R.id.ideal);
        dashboard = (ImageButton) findViewById(R.id.dashboard);
        db = new UserDatabase(this);
        df = new DecimalFormat("0.00");

        Cursor res = db.getdata();
        StringBuffer buffer0 = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        StringBuffer buffer3 = new StringBuffer();
        StringBuffer buffer4 = new StringBuffer();
        StringBuffer buffer5 = new StringBuffer();
        StringBuffer buffer6 = new StringBuffer();
        StringBuffer buffer7 = new StringBuffer();

        while (res.moveToNext())
        {
            buffer0.append(res.getString(0));
            buffer1.append(res.getString(1));
            buffer2.append(res.getString(2));
            buffer3.append(res.getString(3));
            buffer4.append(res.getString(4));
            buffer5.append(res.getString(5));
            buffer6.append(res.getString(6));
            buffer7.append(res.getString(7));
        }

        String nameTXT = buffer0.toString();
        String ageTXT = buffer1.toString();
        String heightTXT = buffer2.toString();
        String weightTXT = buffer3.toString();
        String sexTXT = buffer4.toString();
        String goal= buffer5.toString();
        String body_type = buffer6.toString();
        String activity = buffer7.toString();

        float age = Integer.parseInt(ageTXT);
        float height = Integer.parseInt(heightTXT);
        int weight = Integer.parseInt(weightTXT);


        height = height/100;
        float bmical = weight/(height*height);
        String bmiTXT = String.valueOf(df.format(bmical));

        String resTXT = null;
        int calories = 0;
        float ideal_kg = 0;
        
        if (bmical < 18.5)
            resTXT = "Underweight";
        else if (bmical > 18.5 && bmical < 24.9)
            resTXT = "Normal";
        else if (bmical > 25 && bmical < 29.9)
            resTXT = "Overweight";
        else if (bmical > 30)
            resTXT = "Obese";


        if (body_type.matches("Endomorph") && goal.matches("Lose Weight"))
            calories = weight*22;
        else if (body_type.matches("Mesomorph") && goal.matches("Lose Weight"))
            calories = weight*24;
        else if (body_type.matches("Ectomorph") && goal.matches("Lose Weight"))
            calories = weight*26;
        else if (body_type.matches("Endomorph") && goal.matches("Gain Muscle Mass"))
            calories = weight*29;
        else if (body_type.matches("Mesomorph") && goal.matches("Gain Muscle Mass"))
            calories = weight*31;
        else if (body_type.matches("Ectomorph") && goal.matches("Gain Muscle Mass"))
            calories = weight*33;
        else if (body_type.matches("Endomorph") && goal.matches("Gain Weight"))
            calories = weight*35;
        else if (body_type.matches("Mesomorph") && goal.matches("Gain Weight"))
            calories = weight*43;
        else if (body_type.matches("Ectomorph") && goal.matches("Gain Weight"))
            calories = weight*70;

        height = height*100;

        if (sexTXT.matches("Male"))
            ideal_kg = (height - 100 - ((height - 150)/4)) + ((age-20)/4) ;
        else if (sexTXT.matches("Female"))
            ideal_kg = (height - 100 - ((height - 150)/2)) + ((age-20)/2) ;


        String caloriesTXT = String.valueOf(calories);
        String idealTXT = String.valueOf(df.format(ideal_kg));

        name.setText(nameTXT);
        bmi.setText(bmiTXT);
        rez.setText(resTXT);
        cals.setText(caloriesTXT);
        ideal.setText(idealTXT + " kg");


        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openDashboard();
            }
        });

    }
    public void openDashboard()
    {
        Intent  intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}