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

public class CustomAdapterExercises extends RecyclerView.Adapter<CustomAdapterExercises.MyViewHolder> {

    private Context context;
    private ArrayList ex_id, ex_name, ex_sets, ex_reps;
    Activity activity;
    CustomAdapterExercises(Activity activity,
                           Context context,
                           ArrayList ex_id,
                           ArrayList ex_name,
                           ArrayList ex_sets,
                           ArrayList ex_reps){
        this.activity = activity;
        this.context = context;
        this.ex_id = ex_id;
        this.ex_name = ex_name;
        this.ex_sets = ex_sets;
        this.ex_reps = ex_reps;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exercise_row, parent, false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.ex_id_txt.setText(String.valueOf(position+1));
        holder.ex_name_txt.setText(String.valueOf(ex_name.get(position)));
        holder.ex_sets_txt.setText(String.valueOf(ex_sets.get(position)));
        holder.ex_reps_txt.setText(String.valueOf(ex_reps.get(position)));
        holder.exercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateExercise.class);
                intent.putExtra("id", String.valueOf(ex_id.get(position)));
                intent.putExtra("name", String.valueOf(ex_name.get(position)));
                intent.putExtra("sets", String.valueOf(ex_sets.get(position)));
                intent.putExtra("reps", String.valueOf(ex_reps.get(position)));
                activity.startActivityForResult(intent,1);
                activity.finish();

            }
        });

    }

    @Override
    public int getItemCount() {
        return ex_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ex_id_txt, ex_name_txt, ex_sets_txt, ex_reps_txt;
        LinearLayout exercises;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ex_id_txt = itemView.findViewById(R.id.ex_id_txt);
            ex_name_txt = itemView.findViewById(R.id.ex_name_txt);
            ex_sets_txt = itemView.findViewById(R.id.ex_sets_txt);
            ex_reps_txt = itemView.findViewById(R.id.ex_reps_txt);
            exercises = itemView.findViewById(R.id.exercises);

        }
    }
}
