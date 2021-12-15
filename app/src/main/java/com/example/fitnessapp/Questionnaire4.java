package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Questionnaire4 extends AppCompatActivity {

    Button back,next;
    RadioGroup rg;
    RadioButton rb;
    UserDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire4);

        back = (Button) findViewById(R.id.backbtn);
        next = (Button) findViewById(R.id.nextbtn);
        rg = (RadioGroup) findViewById(R.id.radiogroup);
        db = new UserDatabase(this);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuestionnaire3();
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg.getCheckedRadioButtonId()== -1 )
                {
                    Toast.makeText(Questionnaire4.this, "Please Select Your Goal!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Cursor res = db.getdata();
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext())
                    {
                        buffer.append(res.getString(0));
                    }
                    String name = buffer.toString();

                    int id = rg.getCheckedRadioButtonId();
                    rb = (RadioButton) findViewById(id);
                    String activity = String.valueOf(rb.getText());

                    db.addActivity(name,activity);

                    openProfileMainPage();
                    finish();


                }
            }
        });
    }
    public void openQuestionnaire3()
    {
        Intent intent = new Intent(this, Questionnaire3.class);
        startActivity(intent);
    }
    public void openProfileMainPage()
    {
        Intent intent = new Intent(this, ProfileMainPage.class);
        startActivity(intent);
    }
}