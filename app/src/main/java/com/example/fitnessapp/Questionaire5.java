package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Questionaire5 extends AppCompatActivity {
    Button back,next;
    UserDatabase db;
    EditText goalkg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire5);


        back = (Button) findViewById(R.id.backbtn);
        next = (Button) findViewById(R.id.nextbtn);
        goalkg= (EditText) findViewById(R.id.kggoal);
        db = new UserDatabase(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuestionnaire4();
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String goalkgTXT = goalkg.getText().toString().trim();
                if(goalkgTXT.matches("") )
                {
                    Toast.makeText(Questionaire5.this, "Please Add Your Kg Goal", Toast.LENGTH_SHORT).show();
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

                    db.addKgGoal(name,goalkgTXT);
                    openDashboard();
                    finish();


                }
            }
        });
    }

    public void openQuestionnaire4()
    {
        Intent intent = new Intent(this, Questionnaire4.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
    public void openDashboard()
    {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}