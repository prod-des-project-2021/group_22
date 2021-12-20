package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button start;
    UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start =(Button) findViewById(R.id.btnstart);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openStart();
                finish();
            }
        });


    }
    public void openStart()
    {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);

    }

     public void onStart()
    {
        super.onStart();
        db = new UserDatabase(this);
        Cursor res = db.getdata();

        StringBuffer buffer5 = new StringBuffer();
        StringBuffer buffer6 = new StringBuffer();
        StringBuffer buffer7 = new StringBuffer();
        StringBuffer buffer12 = new StringBuffer();

        while (res.moveToNext())
        {
            buffer5.append(res.getString(5));
            buffer6.append(res.getString(6));
            buffer7.append(res.getString(7));
            buffer12.append(res.getString(12));
        }

        String goal= buffer5.toString();
        String body_type = buffer6.toString();
        String activity = buffer7.toString();
        String goalkg = buffer12.toString();

        Boolean fan1 = goal.equals("null");
        Boolean fan2 = body_type.equals("null");
        Boolean fan3 = activity.equals("null");
        Boolean fan4 = goalkg.matches("");

        if( res.getCount() != 0 && fan1 == false && fan2 == false && fan3 == false && fan4 == false)
        {
            Intent i = new Intent(MainActivity.this,Dashboard.class);
            startActivity(i);
            overridePendingTransition(0,0);
            finish();
        }
    }


}