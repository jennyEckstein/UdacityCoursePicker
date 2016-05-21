package com.jennyeckstein.udacitycoursepicker.data;

import android.content.ContentValues;
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

        db.close();

    }

    public void testCourseTable(){

        CourseDBHelper courseDBHelper = new CourseDBHelper(mContext);
        SQLiteDatabase db = courseDBHelper.getWritableDatabase();

        ContentValues courseValues = createCourseValues();
        long rowID = db.insertOrThrow(CourseContract.Course.TABLE_NAME, null, courseValues);
        assertTrue(rowID != -1);
        Cursor cursor = db.query(
                CourseContract.Course.TABLE_NAME,
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        assertTrue(cursor.getCount() > 0);
        assertTrue("Error: No records returned from course query", cursor.moveToFirst());
        assertFalse("Error: More than one record returned", cursor.moveToNext());
        cursor.close();
        db.close();
    }

    private ContentValues createCourseValues(){
        ContentValues courseValues = new ContentValues();
        courseValues.put(CourseContract.Course.SUBTITLE, "Starting Out with Web Serving Technology");
        courseValues.put(CourseContract.Course.KEY, "ud171");
        courseValues.put(CourseContract.Course.IMAGE, "avc");
        courseValues.put(CourseContract.Course.EXPECTED_LEARNING, "You want to build Web services");
        courseValues.put(CourseContract.Course.FEATURED, false);
        courseValues.put(CourseContract.Course.PROJECT_NAME, "asd");
        courseValues.put(CourseContract.Course.TITLE, "Intro to Backend");
        courseValues.put(CourseContract.Course.REQUIRED_KNOWLEDGE, "You have a basic working knowledge ");
        courseValues.put(CourseContract.Course.SYLLABUS, "### Forms and Inputs How do web sites security?");
        courseValues.put(CourseContract.Course.NEW_RELEASE, false);
        courseValues.put(CourseContract.Course.HOMEPAGE, "https://www.udacity.com/course/" );
        courseValues.put(CourseContract.Course.PROJECT_DESCRIPTION, "");
        courseValues.put(CourseContract.Course.FULL_COURSE_AVAILABLE, false);
        courseValues.put(CourseContract.Course.FAQ, "ads");
        courseValues.put(CourseContract.Course.BANNER_IMAGE, "xc bv");
        courseValues.put(CourseContract.Course.SHORT_SUMMARY, "cvxdf");
        courseValues.put(CourseContract.Course.SLUG, "intro-to-backend--ud171");
        courseValues.put(CourseContract.Course.STARTER, false);
        courseValues.put(CourseContract.Course.LEVEL, "sdf s");
        courseValues.put(CourseContract.Course.DURATION_IN_HOURS, 504);
        courseValues.put(CourseContract.Course.SUMMARY, "This course presents an overview.");


        return courseValues;
    }
}

