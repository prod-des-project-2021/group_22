package com.example.fitnessapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateExercise extends AppCompatActivity {

    EditText name_ex,set_ex,reps_ex;
    TextView title;
    Button update,delete;
    ImageButton back;

    String id, name, sets,reps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exercise);

        name_ex = findViewById(R.id.edit_ex_name);
        set_ex = findViewById(R.id.edit_ex_set);
        reps_ex = findViewById(R.id.edit_ex_rep);
        update = findViewById(R.id.updateex);
        title = findViewById(R.id.title);
        back = findViewById(R.id.backbtn);
        delete = findViewById(R.id.deletex);
        getIntentData();

        title.setText(name);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateExercise.this,WorkoutDiary.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameTXT = name_ex.getText().toString().trim();
                String setsTXT = set_ex.getText().toString().trim();
                String repsTXT = reps_ex.getText().toString().trim();
                WorkoutDatabase db = new WorkoutDatabase(UpdateExercise.this);
                db.updatedata(id,nameTXT,setsTXT,repsTXT);


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();

            }
        });


    }

    void getIntentData()
    {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("sets") && getIntent().hasExtra("reps") )
        {
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            sets = getIntent().getStringExtra("sets");
            reps = getIntent().getStringExtra("reps");

            name_ex.setText(name);
            set_ex.setText(sets);
            reps_ex.setText(reps);
        }
        else
        {
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }

    }

    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ name+ " ?");
        builder.setMessage("Are you sure you want to delete "+ name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WorkoutDatabase db = new WorkoutDatabase(UpdateExercise.this);
                db.deleteOneEx(id);
                Intent intent = new Intent(UpdateExercise.this,WorkoutDiary.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builder.create().show();
    }
}