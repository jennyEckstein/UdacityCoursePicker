package com.jennyeckstein.udacitycoursepicker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jenny on 5/10/2016.
 */
public class CourseDBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "course.db";

    public CourseDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
