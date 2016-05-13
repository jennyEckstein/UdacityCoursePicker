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

        final String SQL_CREATE_INSTRUCTOR_TABLE = "CREATE TABLE " + CourseContract.Instructor.TABLE_NAME + " (" +
                CourseContract.Instructor.ID + " INTEGER PRIMARY KEY, " +
                CourseContract.Instructor.NAME + " TEXT NOT NULL, " +
                CourseContract.Instructor.BIO + " TEXT, " +
                CourseContract.Instructor.IMAGE + " TEXT);";

        final String SQL_CREATE_COURSE_INSTRUCTOR_TABLE = "CREATE TABLE " + CourseContract.Course_Instructor.TABLE_NAME + " (" +
                CourseContract.Course_Instructor.COURSE_ID + " INTEGER NOT NULL, " +
                CourseContract.Course_Instructor.INSTRUCTOR_ID + " INTEGER NOT NULL, " +
                " FOREIGN KEY (" + CourseContract.Course_Instructor.COURSE_ID + ") REFERENCES " +
                CourseContract.Course_Instructor.TABLE_NAME + " (" + CourseContract.Course.COURSE_ID + ")"+
                ");";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CourseContract.Course.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CourseContract.Course_Instructor.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CourseContract.Instructor.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CourseContract.Related_Courses.TABLE_NAME);
        onCreate(db);
    }
}
