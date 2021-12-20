package com.example.fitnessapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterMeal extends RecyclerView.Adapter<CustomAdapterMeal.MyViewHolderMeal> {

    private Context context;
    Activity activity;
    private ArrayList meal_id, meal_name, meal_cals;

    CustomAdapterMeal(Activity activity,
                      Context context,
                      ArrayList meal_id,
                      ArrayList meal_name,
                      ArrayList meal_cals){
        this.context = context;
        this.meal_id = meal_id;
        this.meal_name = meal_name;
        this.meal_cals = meal_cals;
        this.activity = activity;
    }


    @NonNull
    @Override
    public MyViewHolderMeal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_row, parent, false );
        return new MyViewHolderMeal(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMeal holder, @SuppressLint("RecyclerView") int position) {
        holder.meal_id_txt.setText(String.valueOf(position+1));
        holder.meal_name_txt.setText(String.valueOf(meal_name.get(position)));
        holder.meal_cals_txt.setText(String.valueOf(meal_cals.get(position))+" cals");
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealid = String.valueOf(meal_id.get(position));
                PopupMenu popup = new PopupMenu(activity,holder.more);
                popup.getMenuInflater().inflate(R.menu.popup_meal,popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int i = item.getItemId();
                        if(i == R.id.deletemeal)
                        {
                            MealDatabase db = new MealDatabase(context);
                            db.deleteOneMeal(mealid);
                            Intent intent = new Intent(activity, MealPlan.class);
                            activity.startActivityForResult(intent,1);
                            activity.overridePendingTransition(0,0);


                        }
                        else if(i == R.id.editmeal)
                        {
                            EditText editmeal;
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            View view = activity.getLayoutInflater().inflate(R.layout.meal_name_edit,null);
                            builder.setTitle("Edit Name");
                            editmeal = view.findViewById(R.id.mealedit);
                            editmeal.setText(String.valueOf(meal_name.get(position)));
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
                                    MealDatabase db = new MealDatabase(activity);
                                    String editmealTXT = editmeal.getText().toString().trim();
                                    if(editmealTXT.matches(""))
                                    {
                                        Toast.makeText(activity, "No Data Inserted", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {

                                        db.updatedata(mealid,editmealTXT);
                                        Intent intent = new Intent(activity, MealPlan.class);
                                        activity.startActivityForResult(intent,1);
                                        activity.overridePendingTransition(0,0);

                                    }
                                }
                            });

                            builder.setView(view);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        holder.addfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText foodname,calories,proteins,carbs,fats,fibers;
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                View view = activity.getLayoutInflater().inflate(R.layout.add_food,null);
                builder.setTitle("Add Food");
                foodname = view.findViewById(R.id.foodname);
                calories = view.findViewById(R.id.calories);
                proteins = view.findViewById(R.id.proteins);
                carbs = view.findViewById(R.id.carbs);
                fats = view.findViewById(R.id.fats);
                fibers = view.findViewById(R.id.fibers);
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
                        MealDatabase db = new MealDatabase(activity);
                        String foodnameTXT = foodname.getText().toString().trim();
                        String caloriesTXT = calories.getText().toString().trim();
                        String proteinsTXT = proteins.getText().toString().trim();
                        String carbsTXT = carbs.getText().toString().trim();
                        String fatsTXT = fats.getText().toString().trim();
                        String fibersTXT = fibers.getText().toString().trim();

                        if(foodnameTXT.matches("") || caloriesTXT.matches("") || proteinsTXT.matches("") || carbsTXT.matches("") || fatsTXT.matches("") || fibersTXT.matches("") )
                        {
                            Toast.makeText(activity, "No Data Inserted", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            int calories = Integer.parseInt(caloriesTXT) + Integer.parseInt(String.valueOf(meal_cals.get(position)));
                            String caloriesfinal = String.valueOf(calories);
                            db.updateCalories(String.valueOf(meal_id.get(position)), caloriesfinal);
                            Intent intent = new Intent(activity, MealPlan.class);
                            activity.startActivityForResult(intent,1);
                            activity.overridePendingTransition(0,0);

                        }
                    }
                });

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return meal_id.size();
    }

    public class MyViewHolderMeal extends RecyclerView.ViewHolder{
        TextView meal_id_txt, meal_name_txt, meal_cals_txt;
        ImageButton more;
        Button addfood;
        public MyViewHolderMeal(@NonNull View itemView){
            super(itemView);
            meal_id_txt = itemView.findViewById(R.id.meal_id_txt);
            meal_name_txt = itemView.findViewById(R.id.meal_name_txt);
            meal_cals_txt = itemView.findViewById(R.id.meal_cals_txt);
            more = itemView.findViewById(R.id.more);
            addfood = itemView.findViewById(R.id.addfood);

        }
    }

}
