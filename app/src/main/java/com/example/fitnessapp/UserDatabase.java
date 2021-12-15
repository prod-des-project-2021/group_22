package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

public class UserDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "UserDetails.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "user_table";
    private static final String COLUMN_NAME = "user_name";
    private static final String COLUMN_AGE = "user_age";
    private static final String COLUMN_HEIGHT = "user_height";
    private static final String COLUMN_WEIGHT = "user_weight";
    private static final String COLUMN_SEX = "user_sex";
    private static final String COLUMN_GOAL = "user_goal";
    private static final String COLUMN_BODY_TYPE = "user_body_type";
    private static final String COLUMN_ACTIVITY = "user_activity";


    public UserDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE " + TABLE_NAME +
                       " ("+ COLUMN_NAME + " TEXT PRIMARY KEY, " +
                       COLUMN_AGE + " TEXT, " +
                       COLUMN_HEIGHT + " TEXT, " +
                       COLUMN_WEIGHT + " TEXT, " +
                       COLUMN_SEX + " TEXT, " +
                       COLUMN_GOAL + " TEXT, " +
                       COLUMN_BODY_TYPE + " TEXT, " +
                       COLUMN_ACTIVITY + " TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void addUser(String name, String age, String height, String weight, String sex)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_HEIGHT, height);
        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_SEX, sex);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
            db.insert(TABLE_NAME, null, cv);
        }

    }

    public void addGoal(String name,String goal)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_GOAL, goal);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?", new String[]{name});
        if(cursor.getCount()>0)
        {
            db.update(TABLE_NAME, cv,COLUMN_NAME +"=?",new String[]{name});
        }
    }


    public void addBodyType(String name,String body_type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?", new String[]{name});
        cv.put(COLUMN_BODY_TYPE, body_type);
        if(cursor.getCount()>0)
        {
            db.update(TABLE_NAME, cv,COLUMN_NAME +"=?",new String[]{name});
        }
    }


    public void addActivity(String name,String activity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?", new String[]{name});
        cv.put(COLUMN_ACTIVITY, activity);
        if(cursor.getCount()>0)
        {
            db.update(TABLE_NAME, cv,COLUMN_NAME +"=?",new String[]{name});
        }
    }

    public void delete()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public Cursor getdata()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null );
        return cursor;
    }

    public void editName(String name ,String nameedit)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?", new String[]{name});
        cv.put(COLUMN_NAME, nameedit);
        if(cursor.getCount()>0)
        {
            db.update(TABLE_NAME, cv,COLUMN_NAME +"=?",new String[]{name});
        }

    }

    public void editAge(String name ,String ageedit)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?", new String[]{name});
        cv.put(COLUMN_AGE, ageedit);
        if(cursor.getCount()>0)
        {
            db.update(TABLE_NAME, cv,COLUMN_NAME +"=?",new String[]{name});
        }

    }

    public void editHeight(String name ,String heightedit)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?", new String[]{name});
        cv.put(COLUMN_HEIGHT, heightedit);
        if(cursor.getCount()>0)
        {
            db.update(TABLE_NAME, cv,COLUMN_NAME +"=?",new String[]{name});
        }

    }

    public void editWeight(String name ,String weightedit)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?", new String[]{name});
        cv.put(COLUMN_WEIGHT, weightedit);
        if(cursor.getCount()>0)
        {
            db.update(TABLE_NAME, cv,COLUMN_NAME +"=?",new String[]{name});
        }

    }

    public void editSex(String name ,String sexedit)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?", new String[]{name});
        cv.put(COLUMN_SEX, sexedit);
        if(cursor.getCount()>0)
        {
            db.update(TABLE_NAME, cv,COLUMN_NAME +"=?",new String[]{name});
        }

    }



}
