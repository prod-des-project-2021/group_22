package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class WorkoutDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "WorkoutDetails.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "exercises_table";
    private static final String COLUMN_ID = "ex_id";
    private static final String COLUMN_EX_NAME = "ex_name";
    private static final String COLUMN_SETS = "nr_sets";
    private static final String COLUMN_REPETITION = "nr_repetition";


    public WorkoutDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EX_NAME + " TEXT, " +
                COLUMN_SETS + " TEXT, " +
                COLUMN_REPETITION + " TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    public void addExercise(String name_ex, String sets, String reps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EX_NAME, name_ex);
        cv.put(COLUMN_SETS, sets);
        cv.put(COLUMN_REPETITION, reps);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1)
        {
            Toast.makeText(context, "Failed To Add Exercise", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor getExercise()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null );
        return cursor;
    }

    public void deleteall()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_NAME+"'");
    }

    void deleteOneEx(String row_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID+ "=?", new String[]{row_id});
        if( result == -1 )
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    void updatedata(String row_id, String name_ex, String sets, String reps)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EX_NAME, name_ex);
        cv.put(COLUMN_SETS, sets);
        cv.put(COLUMN_REPETITION, reps);
        long result = db.update(TABLE_NAME,cv,COLUMN_ID+"=?", new String[]{row_id});
        if( result == -1 )
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();

        
    }

}
