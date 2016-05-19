package com.jennyeckstein.udacitycoursepicker.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Jenny on 5/10/2016.
 */
public class CourseProvider extends ContentProvider {

    private static final String LOG_TAG = CourseProvider.class.getSimpleName();

    private static final UriMatcher uriMathcher = buildUriMatcher();

    static final int COURSE = 100;
    static final int INSTRUCTOR = 200;
    static final int RELATED_COURSES = 300;
    static final int COURSE_INSTRUCTOR = 400;

    static final int COURSE_WITH_ID = 101;

    static final int INSTRUCTOR_WITH_ID = 201;

    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CourseContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, CourseContract.PATH_COURSE, COURSE);
        matcher.addURI(authority, CourseContract.PATH_INSTRUCTOR, INSTRUCTOR);
        matcher.addURI(authority, CourseContract.PATH_RELATED_COURSES, RELATED_COURSES);
        matcher.addURI(authority, CourseContract.PATH_COURSE_INSTRUCTOR, COURSE_INSTRUCTOR);

        matcher.addURI(authority, CourseContract.PATH_COURSE + "/*", COURSE_WITH_ID);
        matcher.addURI(authority, CourseContract.PATH_INSTRUCTOR + "/#", INSTRUCTOR_WITH_ID);

        return matcher;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = uriMathcher.match(uri);

        switch(match){
            case COURSE:
                return  CourseContract.Course.CONTENT_DIR_TYPE;
            case COURSE_WITH_ID:
                return CourseContract.Course.CONTENT_ITEM_TYPE;

            case INSTRUCTOR:
                return CourseContract.Instructor.CONTENT_DIR_TYPE;
            case INSTRUCTOR_WITH_ID:
                return CourseContract.Instructor.CONTENT_iTEM_TYPE;

            case RELATED_COURSES:
                return CourseContract.Related_Courses.CONTENT_DIR_TYPE;

            case COURSE_INSTRUCTOR:
                return CourseContract.Course_Instructor.CONTENT_DIR_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }
}
