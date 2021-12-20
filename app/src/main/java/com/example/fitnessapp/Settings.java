package com.example.fitnessapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Settings extends AppCompatActivity {
    ImageButton back;
    TextView name,age,height,weight,sex,goal,body,activity,goalkg;
    UserDatabase db;
    CardView name_tab,age_tab,height_tab,weight_tab,sex_tab,goal_tab,body_tab,activity_tab,goalkg_tab;
    String goalseditTXT, bodyeditTXT, activityeditTXT;
    int calories;
    DecimalFormat df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        back = findViewById(R.id.backbtn);
        name = findViewById(R.id.name_edit);
        age = findViewById(R.id.age_edit);
        height = findViewById(R.id.height_edit);
        weight = findViewById(R.id.weight_edit);
        sex = findViewById(R.id.sex_edit);
        goal = findViewById(R.id.goal_edit);
        body = findViewById(R.id.bodytype_edit);
        activity = findViewById(R.id.activity_edit);
        goalkg = findViewById(R.id.kggoal_edit);

        name_tab = findViewById(R.id.name_tab);
        age_tab = findViewById(R.id.age_tab);
        height_tab = findViewById(R.id.height_tab);
        weight_tab = findViewById(R.id.weight_tab);
        sex_tab = findViewById(R.id.sex_tab);
        goal_tab = findViewById(R.id.goal_tab);
        body_tab = findViewById(R.id.body_tab);
        activity_tab = findViewById(R.id.activity_tab);
        goalkg_tab = findViewById(R.id.kggoaltab);
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
        StringBuffer buffer12 = new StringBuffer();

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
        }

        String nameTXT = buffer0.toString();
        String ageTXT = buffer1.toString();
        String heightTXT = buffer2.toString();
        String weightTXT = buffer3.toString();
        String sexTXT = buffer4.toString();
        String goalTXT= buffer5.toString();
        String body_typeTXT = buffer6.toString();
        String activityTXT = buffer7.toString();
        String goalkgTXT = buffer12.toString();

        name.setText(nameTXT);
        age.setText(ageTXT);
        height.setText(heightTXT +"cm");
        weight.setText(weightTXT +"kg");
        sex.setText(sexTXT);
        goal.setText(goalTXT);
        body.setText(body_typeTXT);
        activity.setText(activityTXT);
        goalkg.setText(goalkgTXT+"kg");

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


        if (body_typeTXT.matches("Endomorph") && goalTXT.matches("Lose Weight"))
            calories = weight*22;
        else if (body_typeTXT.matches("Mesomorph") && goalTXT.matches("Lose Weight"))
            calories = weight*24;
        else if (body_typeTXT.matches("Ectomorph") && goalTXT.matches("Lose Weight"))
            calories = weight*26;
        else if (body_typeTXT.matches("Endomorph") && goalTXT.matches("Gain Muscle Mass"))
            calories = weight*29;
        else if (body_typeTXT.matches("Mesomorph") && goalTXT.matches("Gain Muscle Mass"))
            calories = weight*31;
        else if (body_typeTXT.matches("Ectomorph") && goalTXT.matches("Gain Muscle Mass"))
            calories = weight*33;
        else if (body_typeTXT.matches("Endomorph") && goalTXT.matches("Gain Weight"))
            calories = weight*35;
        else if (body_typeTXT.matches("Mesomorph") && goalTXT.matches("Gain Weight"))
            calories = weight*37;
        else if (body_typeTXT.matches("Ectomorph") && goalTXT.matches("Gain Weight"))
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

        if(activityTXT.matches("Highly Active") && goalTXT.matches("Lose Weight"))
        {
            calories = calories + 300;
        }
        else if(activityTXT.matches("Moderate") && goalTXT.matches("Lose Weight"))
        {
            calories = calories +150;
        }
        else if(activityTXT.matches("Highly Active") && goalTXT.matches("Gain Muscle Mass"))
        {
            calories = calories + 300;
        }
        else if(activityTXT.matches("Moderate") && goalTXT.matches("Gain Muscle Mass"))
        {
            calories = calories + 150;
        }
        else if(activityTXT.matches("Highly Active") && goalTXT.matches("Gain Weight"))
        {
            calories = calories + 300;
        }
        else if(activityTXT.matches("Moderate") && goalTXT.matches("Gain Weight"))
        {
            calories = calories + 150;
        }

        String caloriesTXT = String.valueOf(calories);
        String idealTXT = String.valueOf(df.format(ideal_kg));
        db.addCalsIdealBMIRES(nameTXT,caloriesTXT,idealTXT,bmiTXT,resTXT);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(Settings.this, ProfileMainPage.class);
               startActivity(intent);
               overridePendingTransition(0,0);
               finish();
            }
        });

        name_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNameDialog();


            }
        });

        age_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAgeDialog();

            }
        });

        height_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHeightDialog();

            }
        });

        weight_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeightDialog();

            }
        });

        sex_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSexDialog();

            }
        });

        goal_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoalDialog();

            }
        });

        body_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBodyDialog();

            }
        });

        activity_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityDialog();

            }
        });

        goalkg_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoalKgDialog();
            }
        });




    }

    public void openNameDialog()
    {
        EditText editname;
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        View view = getLayoutInflater().inflate(R.layout.name_edit,null);
        builder.setTitle("Edit Name");
        editname = view.findViewById(R.id.edit_name);
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
                String editnameTXT = editname.getText().toString().trim();
                if(editnameTXT.matches(""))
                {
                    Toast.makeText(Settings.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
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
                    db.editName(namebufferTXT,editnameTXT);
                    reset();


                }
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    public void openAgeDialog()
    {
        EditText editage;
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        View view = getLayoutInflater().inflate(R.layout.age_edit,null);
        builder.setTitle("Edit Age");
        editage = view.findViewById(R.id.edit_age);
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
                String editageTXT = editage.getText().toString().trim();
                if(editageTXT.matches(""))
                {
                    Toast.makeText(Settings.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Cursor resage = db.getdata();
                    StringBuffer bufferage = new StringBuffer();
                    while (resage.moveToNext())
                    {
                        bufferage.append(resage.getString(0));

                    }
                    String namebufferTXT = bufferage.toString();
                    db.editAge(namebufferTXT,editageTXT);
                    reset();


                }

            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void openHeightDialog()
    {
        EditText editheight;
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        View view = getLayoutInflater().inflate(R.layout.height_edit,null);
        builder.setTitle("Edit Height");
        editheight = view.findViewById(R.id.edit_height);
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
                String editheightTXT = editheight.getText().toString().trim();
                if(editheightTXT.matches(""))
                {
                    Toast.makeText(Settings.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Cursor resheight = db.getdata();
                    StringBuffer bufferheight = new StringBuffer();
                    while (resheight.moveToNext())
                    {
                        bufferheight.append(resheight.getString(0));

                    }
                    String namebufferTXT = bufferheight.toString();
                    db.editHeight(namebufferTXT,editheightTXT);
                    reset();


                }
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void openWeightDialog()
    {
        EditText editweight;
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        View view = getLayoutInflater().inflate(R.layout.weight_edit,null);
        builder.setTitle("Edit Weight");
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
                    Toast.makeText(Settings.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
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
                    reset();

                }
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void openSexDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        View view = getLayoutInflater().inflate(R.layout.sex_edit,null);
        builder.setTitle("Edit Sex");
        ArrayAdapter<String> adapterItems;
        AutoCompleteTextView autoCompleteTxt;
        String[] items = {"Female","Male"};

        autoCompleteTxt = view.findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

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
                String editsexTXT = autoCompleteTxt.getText().toString().trim();
                Cursor resname = db.getdata();
                StringBuffer buffername = new StringBuffer();
                while (resname.moveToNext())
                {
                    buffername.append(resname.getString(0));

                }
                String namebufferTXT = buffername.toString();
                db.editSex(namebufferTXT,editsexTXT);
                reset();

            }
        });


        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void openGoalDialog()
    {
        String[] goals = {"Lose Weight","Gain Muscle Mass", "Gain Weight"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setTitle("Edit Goal");

        builder.setSingleChoiceItems(goals, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goalseditTXT = goals[which];

            }
        });
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
                if(goalseditTXT == goals[0] || goalseditTXT == goals[1] || goalseditTXT == goals[2]  )
                {
                    Cursor resname = db.getdata();
                    StringBuffer buffername = new StringBuffer();
                    while (resname.moveToNext())
                    {
                        buffername.append(resname.getString(0));
                    }

                    String namebufferTXT = buffername.toString();
                    db.addGoal(namebufferTXT,goalseditTXT);
                    reset();
                }
                else
                {
                    Toast.makeText(Settings.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
                }




            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();



    }

    public void openBodyDialog()
    {
        String[] bodyedit = {"Ectomorph","Mesomorph", "Endomorph"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setTitle("Edit Body Type");
        builder.setSingleChoiceItems(bodyedit, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bodyeditTXT = bodyedit[which];

            }
        });
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
                if(bodyeditTXT == bodyedit[0] || bodyeditTXT == bodyedit[1] || bodyeditTXT == bodyedit[2]  )
                {
                    Cursor resname = db.getdata();
                    StringBuffer buffername = new StringBuffer();
                    while (resname.moveToNext())
                    {
                        buffername.append(resname.getString(0));
                    }

                    String namebufferTXT = buffername.toString();
                    db.addBodyType(namebufferTXT,bodyeditTXT);
                    reset();
                }
                else
                {
                    Toast.makeText(Settings.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
                }

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void openActivityDialog()
    {
        String[] activityedit = {"Sedentary","Moderate", "Highly Active"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setTitle("Edit Daily Activity");
        builder.setSingleChoiceItems(activityedit, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activityeditTXT= activityedit[which];

            }
        });
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
                if(activityeditTXT == activityedit[0] || activityeditTXT == activityedit[1] || activityeditTXT == activityedit[2]  )
                {
                    Cursor resname = db.getdata();
                    StringBuffer buffername = new StringBuffer();
                    while (resname.moveToNext())
                    {
                        buffername.append(resname.getString(0));
                    }

                    String namebufferTXT = buffername.toString();
                    db.addActivity(namebufferTXT,activityeditTXT);
                    reset();
                }
                else
                {
                    Toast.makeText(Settings.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }


    public void openGoalKgDialog()
    {
        EditText editgoalkg;
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        View view = getLayoutInflater().inflate(R.layout.goalkg_edit,null);
        builder.setTitle("Edit Name");
        editgoalkg = view.findViewById(R.id.edit_goalkg);
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
                String editgoalkgTXT = editgoalkg.getText().toString().trim();
                if(editgoalkgTXT.matches(""))
                {
                    Toast.makeText(Settings.this, "No Data Inserted", Toast.LENGTH_SHORT).show();
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
                    db.addKgGoal(namebufferTXT,editgoalkgTXT);
                    reset();


                }
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    void reset()
    {
        Intent intent = new Intent(Settings.this, Settings.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }


}