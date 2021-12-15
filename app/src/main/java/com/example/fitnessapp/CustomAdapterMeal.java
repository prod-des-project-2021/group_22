package com.example.fitnessapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterMeal extends RecyclerView.Adapter<CustomAdapterMeal.MyViewHolderMeal> {

    private Context context;
    private ArrayList meal_id, meal_name, meal_cals;

    CustomAdapterMeal(
                      Context context,
                      ArrayList meal_id,
                      ArrayList meal_name,
                      ArrayList meal_cals){
        this.context = context;
        this.meal_id = meal_id;
        this.meal_name = meal_name;
        this.meal_cals = meal_cals;
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
        holder.meal_cals_txt.setText(String.valueOf(meal_cals.get(position)));

    }

    @Override
    public int getItemCount() {
        return meal_id.size();
    }

    public class MyViewHolderMeal extends RecyclerView.ViewHolder{
        TextView meal_id_txt, meal_name_txt, meal_cals_txt;
        public MyViewHolderMeal(@NonNull View itemView){
            super(itemView);
            meal_id_txt = itemView.findViewById(R.id.meal_id_txt);
            meal_name_txt = itemView.findViewById(R.id.meal_name_txt);
            meal_cals_txt = itemView.findViewById(R.id.meal_cals_txt);

        }
    }
}
