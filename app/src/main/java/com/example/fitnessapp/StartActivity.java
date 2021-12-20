package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity
{
    EditText name,age,height,weight;
    Button next ;
    UserDatabase db;
    String[] items = {"Female","Male"};

    ArrayAdapter<String> adapterItems;
    AutoCompleteTextView autoCompleteTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_item,items);
        autoCompleteTxt.setAdapter(adapterItems);


        next = (Button) findViewById(R.id.nextbtn);

        db = new UserDatabase(this);

        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String nameTXT = name.getText().toString().trim();
                String ageTXT = age.getText().toString().trim();
                String heightTXT = height.getText().toString().trim();
                String weightTXT = weight.getText().toString().trim();
                String sexTXT = autoCompleteTxt.getText().toString().trim();


                if ((nameTXT.matches("") ) || (ageTXT.matches("")) || (heightTXT.matches("")) || (weightTXT.matches("")))
                {
                    Toast.makeText(StartActivity.this, "No Data Added!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.delete();
                    db.addUser(nameTXT,ageTXT,heightTXT,weightTXT,sexTXT);
                    openQuestionnaire2();
                    overridePendingTransition(0,0);
                    finish();
                }
            }
        });
    }
    public void openQuestionnaire2()
    {
        Intent intent = new Intent(this, Questionnaire2.class);
        startActivity(intent);
    }
}