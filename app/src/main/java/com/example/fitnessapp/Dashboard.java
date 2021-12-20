package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView navigation;
    ImageButton addquickcalories, addwater, updatecurrentweight;
    TextView date,today,caloriestodo, literstogo, kgtolose, currentkg;
    UserDatabase db;
    MealDatabase dm;
    DecimalFormat df;
    private int progr = 0,progrwater =0 ,waterint;
    private int sumcalories = 0, sumwater=0 , calories =0;
    ArrayList<String>  meal_calories;
    String nameTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        navigation = findViewById(R.id.bottom_nav);
        date = findViewById(R.id.datetoday);
        today = findViewById(R.id.today);
        caloriestodo = findViewById(R.id.caloriestodo);
        literstogo = findViewById(R.id.watertodo);
        kgtolose = findViewById(R.id.kgtolose);
        currentkg = findViewById(R.id.currentkg);
        addquickcalories =findViewById(R.id.addquickcalories);
        addwater = findViewById(R.id.addwater);
        updatecurrentweight = findViewById(R.id.updateyourcurrentweight);

        db = new UserDatabase(this);
        dm = new MealDatabase(this);
        df = new DecimalFormat("0.00");

        navigation.setSelectedItemId(R.id.nav_home);
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

                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(),ProfileMainPage.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        Date datetxt = Calendar.getInstance().getTime();
        String formateddate = DateFormat.getDateInstance(DateFormat.FULL).format(datetxt);
        String[] splitdate = formateddate.split(",");
        String showdate = splitdate[1].trim().toUpperCase() + " " + splitdate[2].trim().toUpperCase();
        String todayTXT = splitdate[0].trim().toUpperCase();
        today.setText(todayTXT);
        date.setText(showdate);




        Cursor res = db.getdata();
        StringBuffer buffer0 = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        StringBuffer buffer3 = new StringBuffer();
        StringBuffer buffer4 = new StringBuffer();
        StringBuffer buffer5 = new StringBuffer();
        StringBuffer buffer6 = new StringBuffer();
        StringBuffer buffer7 = new StringBuffer();
        StringBuffer buffer12 = new StringBuffer();
        StringBuffer buffer13 = new StringBuffer();
        StringBuffer buffer14 = new StringBuffer();

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
            buffer12.append(res.getString(12));
            buffer13.append(res.getString(13));
            buffer14.append(res.getString(14));
        }

        nameTXT = buffer0.toString();
        String ageTXT = buffer1.toString();
        String heightTXT = buffer2.toString();
        String weightTXT = buffer3.toString();
        String sexTXT = buffer4.toString();
        String goal= buffer5.toString();
        String body_type = buffer6.toString();
        String activity = buffer7.toString();
        String goalkg = buffer12.toString();
        String liters = buffer13.toString();
        String caloriesdone = buffer14.toString();



        float age = Integer.parseInt(ageTXT);
        float height = Integer.parseInt(heightTXT);
        int weight = Integer.parseInt(weightTXT);


        height = height/100;
        float bmical = weight/(height*height);
        String bmiTXT = String.valueOf(df.format(bmical));

        String resTXT = null;
        float ideal_kg = 0;

        if (bmical < 18.6)
            resTXT = "Underweight";
        else if (bmical > 18.6 && bmical < 24.9)
            resTXT = "Normal";
        else if (bmical > 24.9 && bmical < 29.9)
            resTXT = "Overweight";
        else if (bmical > 29.9)
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
            calories = weight*37;
        else if (body_type.matches("Ectomorph") && goal.matches("Gain Weight"))
            calories = weight*39;

        if (resTXT.matches("Overweight"))
            calories = calories - 250;
        else if (resTXT.matches("Obese"))
            calories = calories - 500;
        else if (resTXT.matches("Underweight"))
            calories = calories + 500;


        height = height*100;

        if (sexTXT.matches("Male"))
            ideal_kg = (height - 100 - ((height - 150)/4)) + ((age-20)/4) ;
        else if (sexTXT.matches("Female"))
            ideal_kg = (height - 100 - ((height - 150)/2)) + ((age-20)/2) ;

        if(activity.matches("Highly Active") && goal.matches("Lose Weight"))
        {
            calories = calories + 300;
        }
        else if(activity.matches("Moderate") && goal.matches("Lose Weight"))
        {
            calories = calories +150;
        }
        else if(activity.matches("Highly Active") && goal.matches("Gain Muscle Mass"))
        {
            calories = calories + 300;
        }
        else if(activity.matches("Moderate") && goal.matches("Gain Muscle Mass"))
        {
            calories = calories + 150;
        }
        else if(activity.matches("Highly Active") && goal.matches("Gain Weight"))
        {
            calories = calories + 300;
        }
        else if(activity.matches("Moderate") && goal.matches("Gain Weight"))
        {
            calories = calories + 150;
        }


        float weightfloat = Integer.parseInt(weightTXT);
        String water = String.valueOf(Math.round(weightfloat/20));
        waterint = Integer.parseInt(water);
        String caloriesTXT = String.valueOf(calories);
        String idealTXT = String.valueOf(df.format(ideal_kg));
        db.addCalsIdealBMIRES(nameTXT,caloriesTXT,idealTXT,bmiTXT,resTXT);

        caloriestodo.setText(caloriesTXT);
        literstogo.setText(water+"L");
        currentkg.setText(weightTXT);
        kgtolose.setText(goalkg);

        Boolean fan0 = liters.equals("null");
        Boolean fan1 = caloriesdone.equals("null");

        String sumwaterTXT = String.valueOf(sumwater);
        String sumcaloriesTXT = String.valueOf(sumcalories);

        if(fan1 == true)
        {
            db.addCals(nameTXT, sumcaloriesTXT);
        }
        else
        {
            sumcalories = Integer.parseInt(caloriesdone);
            progr = (100*sumcalories)/calories;
        }


        if(fan0 == true)
        {
            db.addL(nameTXT, sumwaterTXT);
        }
        else
        {
            sumwater = Integer.parseInt(liters);
            progrwater = (100*sumwater)/waterint;
        }

        updateProgressBar();
        updateProgressBarWater();

        meal_calories = new ArrayList<>();

        storemealsinArrays();

        for (int i = 0; i< meal_calories.size();i++)
        {
            Boolean fan = String.valueOf(meal_calories.get(i)).equals("null");
            if(fan == false)
            {
                sumcalories = sumcalories + Integer.parseInt(String.valueOf(meal_calories.get(i)));
                progr = (100*sumcalories)/calories;
            }
            updateProgressBar();
        }


        addwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddWaterDialog();

            }
        });

        addquickcalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddQuickCaloriesDialog();

            }
        });


        updatecurrentweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeightDialog();
            }
        });



    }




    void updateProgressBar()
    {
        ProgressBar progressbar = findViewById(R.id.progressbar);
        TextView caloriesdone = findViewById(R.id.caloriesdone);
        progressbar.setProgress(progr);
        caloriesdone.setText(String.valueOf(sumcalories));

    }
    void updateProgressBarWater()
    {
        ProgressBar progressbar = findViewById(R.id.progressbarwater);
        TextView waterdone = findViewById(R.id.waterdone);
        progressbar.setProgress(progrwater);
        waterdone.setText(String.valueOf(sumwater));

    }




    void storemealsinArrays(){
        Cursor cursor = dm.getMeal();
        if(cursor.getCount() == 0) {
            progr = 0;

        }
        else {
            while (cursor.moveToNext()) {
                meal_calories.add(cursor.getString(2));
            }

        }
    }





    public void openWeightDialog()
    {
        EditText editweight;
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        View view = getLayoutInflater().inflate(R.layout.weight_edit,null);
        builder.setTitle("Update Weight");
        editweight = view.findViewById(R.id.edit_weight);
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
                String editweightTXT = editweight.getText().toString().trim();
                if(editweightTXT.matches(""))
                {
                    Toast.makeText(Dashboard.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Cursor resname = db.getdata();
                    StringBuffer buffername = new StringBuffer();
                    while (resname.moveToNext())
                    {
                        buffername.append(resname.getString(0));

                    }
                    String namebufferTXT = buffername.toString();
                    db.editWeight(namebufferTXT,editweightTXT);
                    Intent intent = new Intent(Dashboard.this, Dashboard.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);


                }
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }





    public void openAddWaterDialog()
    {
        EditText updatewater;
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        View view = getLayoutInflater().inflate(R.layout.add_water,null);
        builder.setTitle("Add Water");
        updatewater = view.findViewById(R.id.updatewater);
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
                String updatewaterTXT = updatewater.getText().toString().trim();
                if(updatewaterTXT.matches(""))
                {
                    Toast.makeText(Dashboard.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sumwater = sumwater + Integer.parseInt(updatewaterTXT);
                    progrwater = (100*sumwater)/waterint;
                    String sumwaterTXT = String.valueOf(sumwater);
                    updateProgressBarWater();
                    db.addL(nameTXT, sumwaterTXT);

                }
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void openAddQuickCaloriesDialog()
    {
        EditText addcalories;
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        View view = getLayoutInflater().inflate(R.layout.add_quick_calories,null);
        builder.setTitle("Quick Add Calories");
        addcalories = view.findViewById(R.id.addquickcalories);
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
                String quickAddCalsTXT = addcalories.getText().toString().trim();
                if(quickAddCalsTXT.matches(""))
                {
                    Toast.makeText(Dashboard.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sumcalories = sumcalories + Integer.parseInt(quickAddCalsTXT);
                    progr = (100*sumcalories)/calories;
                    String sumcaloriesTXT = String.valueOf(sumcalories);
                    updateProgressBar();
                    db.addCals(nameTXT,sumcaloriesTXT);

                }
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}