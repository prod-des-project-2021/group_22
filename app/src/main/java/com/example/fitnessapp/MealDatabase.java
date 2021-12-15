package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MealDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MealDetails.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "meal_table";
    private static final String COLUMN_ID = "meal_id";
    private static final String COLUMN_MEAL_NAME = "meal_name";
    private static final String COLUMN_CALS = "meal_cals";


    public MealDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MEAL_NAME + " TEXT, " +
                COLUMN_CALS + " TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void addMeal(String name_meal, String cals) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MEAL_NAME, name_meal);
        cv.put(COLUMN_CALS, cals);

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

    public Cursor getMeal()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME ,null );
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

    void updatedata(String row_id, String name_meal)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MEAL_NAME, name_meal);
        long result = db.update(TABLE_NAME,cv,COLUMN_ID+"=?", new String[]{row_id});
        if( result == -1 )
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();


    }


}
