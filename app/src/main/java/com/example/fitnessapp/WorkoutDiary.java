package com.example.fitnessapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class WorkoutDiary extends AppCompatActivity {

    RecyclerView recycleView;
    FloatingActionButton add;
    ImageView img_empty;
    TextView no_data;
    ImageButton delete;
    WorkoutDatabase db;
    ArrayList<String> ex_id, ex_name, ex_sets, ex_reps;
    CustomAdapterExercises customAdapterExercises;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_diary);

        add = findViewById(R.id.addbutton);
        delete = findViewById(R.id.deleteallbtn);
        recycleView = findViewById(R.id.recyclerview);
        db = new WorkoutDatabase(WorkoutDiary.this);
        no_data = findViewById(R.id.nodata);
        img_empty = findViewById(R.id.imageView);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExerciseDialog();
            }
        });

        ex_id = new ArrayList<>();
        ex_name = new ArrayList<>();
        ex_sets = new ArrayList<>();
        ex_reps = new ArrayList<>();

        storeexerciseinArrays();

        customAdapterExercises = new CustomAdapterExercises (WorkoutDiary.this,this, ex_id, ex_name, ex_sets, ex_reps);
        recycleView.setAdapter(customAdapterExercises);
        recycleView.setLayoutManager(new LinearLayoutManager(WorkoutDiary.this));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.getExercise();
                if(cursor.getCount()== 0)
                    Toast.makeText(WorkoutDiary.this, "Nothing To Delete!", Toast.LENGTH_SHORT).show();
                else
                {
                    confirmDialog();

                }

            }
        });

    }







    void storeexerciseinArrays(){
        Cursor cursor = db.getExercise();
        if(cursor.getCount() == 0) {
            img_empty.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }
        else {
            while (cursor.moveToNext()) {
                ex_id.add(cursor.getString(0));
                ex_name.add(cursor.getString(1));
                ex_sets.add(cursor.getString(2));
                ex_reps.add(cursor.getString(3));
            }
            img_empty.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    public void openExerciseDialog()
    {
        EditText exname,nrsets,nrreps;
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutDiary.this);
        View view = getLayoutInflater().inflate(R.layout.exercise_add,null);
        builder.setTitle("Add Exercise");
        exname = view.findViewById(R.id.ex_name);
        nrsets = view.findViewById(R.id.sets);
        nrreps = view.findViewById(R.id.reps);
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();

            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String exnameTXT = exname.getText().toString().trim();
                String nrsetsTXT = nrsets.getText().toString().trim();
                String nrrepsTXT = nrreps.getText().toString().trim();
                if(exnameTXT.matches("") || nrrepsTXT.matches("") || nrsetsTXT.matches("") )
                {
                    Toast.makeText(WorkoutDiary.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.addExercise(exnameTXT,nrsetsTXT,nrrepsTXT);
                    Intent intent = new Intent(WorkoutDiary.this, WorkoutDiary.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure you want to delete all ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(WorkoutDiary.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                WorkoutDatabase db = new WorkoutDatabase(WorkoutDiary.this);
                db.deleteall();
                Intent intent = new Intent(WorkoutDiary.this, WorkoutDiary.class);
                startActivity(intent);
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