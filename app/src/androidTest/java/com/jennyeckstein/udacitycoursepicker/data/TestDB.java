package com.jennyeckstein.udacitycoursepicker.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

/**
 * Created by Jenny on 5/20/2016.
 */
public class TestDB extends AndroidTestCase{

    public static final String LOG_TAG = TestDB.class.getSimpleName();

    private void deleteDatabase(){
        mContext.deleteDatabase(CourseDBHelper.DATABASE_NAME);
    }

    public void setUp(){
        deleteDatabase();
    }

    public void testCreateDb() throws Throwable{

        final HashSet<String> tableNameHashSet = new HashSet<>();

        tableNameHashSet.add(CourseContract.Course.TABLE_NAME);
        tableNameHashSet.add(CourseContract.Instructor.TABLE_NAME);
        tableNameHashSet.add(CourseContract.Related_Courses.TABLE_NAME);
        tableNameHashSet.add(CourseContract.Course_Instructor.TABLE_NAME);

        mContext.deleteDatabase(CourseDBHelper.DATABASE_NAME);
        SQLiteDatabase db = new CourseDBHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("Error: This means that the database has not been created correctly", c.moveToFirst());

        tableNameHashSet.clear();
        assertTrue(tableNameHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + CourseContract.Course.TABLE_NAME + ")",null);
        assertTrue("Error: This means we were unable to query the database for table information", c.moveToFirst());

    }
}

