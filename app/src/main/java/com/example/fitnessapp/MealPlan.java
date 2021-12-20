package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MealPlan extends AppCompatActivity {

    RecyclerView recycleView;
    FloatingActionButton add;
    ImageView img_empty;
    TextView no_data;
    ImageButton delete;
    MealDatabase db;
    ArrayList<String> meal_id, meal_name, meal_calories;
    CustomAdapterMeal customAdapterMeal;
    BottomNavigationView navigation;

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
        setContentView(R.layout.activity_meal_plan);
        navigation = findViewById(R.id.bottom_nav);
        add = findViewById(R.id.addbuttonmeal);
        delete = findViewById(R.id.deleteallbtnmeal);
        recycleView = findViewById(R.id.recyclerviewmeal);
        db = new MealDatabase(MealPlan.this);
        no_data = findViewById(R.id.nodata1);
        img_empty = findViewById(R.id.imageView1);

        navigation.setSelectedItemId(R.id.nav_eating);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(),ProfileMainPage.class));
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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMealDialog();
            }
        });

        meal_id = new ArrayList<>();
        meal_name = new ArrayList<>();
        meal_calories = new ArrayList<>();

        storemealsinArrays();

        customAdapterMeal = new CustomAdapterMeal (MealPlan.this,this, meal_id, meal_name, meal_calories);
        recycleView.setAdapter(customAdapterMeal);
        recycleView.setLayoutManager(new LinearLayoutManager(MealPlan.this));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.getMeal();
                if(cursor.getCount()== 0)
                    Toast.makeText(MealPlan.this, "Nothing To Delete!", Toast.LENGTH_SHORT).show();
                else
                {
                    confirmDialog();

                }

            }
        });


    }

    void storemealsinArrays(){
        Cursor cursor = db.getMeal();
        if(cursor.getCount() == 0) {
            img_empty.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }
        else {
            while (cursor.moveToNext()) {
                meal_id.add(cursor.getString(0));
                meal_name.add(cursor.getString(1));
                meal_calories.add(cursor.getString(2));
            }
            img_empty.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    public void openMealDialog()
    {
        EditText mealname;
        AlertDialog.Builder builder = new AlertDialog.Builder(MealPlan.this);
        View view = getLayoutInflater().inflate(R.layout.meal_add,null);
        builder.setTitle("Add Meal");
        mealname = view.findViewById(R.id.meal_name);
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
                String mealnameTXT = mealname.getText().toString().trim();

                if(mealnameTXT.matches(""))
                {
                    Toast.makeText(MealPlan.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.addMeal(mealnameTXT, "0");
                    Intent intent = new Intent(MealPlan.this, MealPlan.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
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
                Toast.makeText(MealPlan.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                MealDatabase db = new MealDatabase(MealPlan.this);
                db.deleteall();
                Intent intent = new Intent(MealPlan.this, MealPlan.class);
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