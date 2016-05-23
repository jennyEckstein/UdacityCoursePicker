package com.jennyeckstein.udacitycoursepicker.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.jennyeckstein.udacitycoursepicker.data.CourseContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jenny on 5/23/2016.
 */
public class CourseService extends IntentService {

    private static final String LOG_TAG = CourseService.class.getSimpleName();
    String parseJsonString;

    public CourseService() {
        super("CourseService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String coursesJsonString = null;

        try {
            URL url = new URL("https://www.udacity.com/public-api/v0/courses");
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                Log.v(LOG_TAG, "INPUT STREAM IS EMPTY");
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                Log.v(LOG_TAG, "buffer.length() IS EMPTY");
            }
            coursesJsonString = buffer.toString();
            //Log.v(LOG_TAG, coursesJsonString);
            parseJsonString(coursesJsonString);


        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            Log.v(LOG_TAG, "IOEXCEPTION");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }

    private void parseJsonString(String jsonString) {
        final String COURSES = "courses";
        final String INSTRUCTORS = "instructors";
        final String BIO = "bio";
        //final String IMAGE = "image";
        final String NAME = "name";

        final String SUBTITLE = "subtitle";
        final String KEY = "key";
        final String IMAGE = "image";
        final String EXPECTED_LEARNING = "expected_learning";
        final String FEATURED = "featured";
        final String PROJECT_NAME = "project_name";
        final String TEASER_VIDEO = "teaser_video";
        final String YOUTUBE_URL = "youtube_url";
        final String TITLE = "title";
        final String RELATED_DEGREE_KEYS = "related_degree_keys"; //array
        final String REQUIRED_KNOWLEDGE = "required_knowledge";
        final String SYLLABUS = "syllabus";
        final String NEW_RELEASE = "new_release";
        final String HOMEPAGE = "homepage";
        final String PROJECT_DESCRIPTION = "project_description";
        final String FULL_COURSE_AVAILABLE = "full_course_available";
        final String FAQ = "faq";
        final String AFFILIATES = "affiliates"; //array
        final String TRACKS = "tracks"; //array
        final String BANNER_IMAGE = "banner_image";
        final String SHORT_SUMMARY = "short_summary";
        final String SLUG = "slug";
        final String STARTER = "starter";
        final String LEVEL = "level";
        final String EXPECTED_DURATION_UNIT = "expected_duration_unit";
        final String SUMMARY = "summary";
        final String EXPECTED_DURATION = "expected_duration";


        try {
            JSONObject courseObject = new JSONObject(jsonString);
            JSONArray jsonArray = courseObject.getJSONArray(COURSES);
            Log.v(LOG_TAG, "json object");
            Vector<ContentValues> courseVector = new Vector<>(jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {
                //TODO: Check for duplicates, and data changes in the database
                JSONObject object = jsonArray.getJSONObject(i);
                String subtitle = object.getString(SUBTITLE);
                String key = object.getString(KEY);
                String image = object.getString(IMAGE);
                String expected_learning = object.getString(EXPECTED_LEARNING);
                boolean featured = object.getBoolean(FEATURED);
                String project_name = object.getString(PROJECT_NAME);
                //String teaser_video = object.getString(TEASER_VIDEO);
                String title = object.getString(TITLE);
                String required_knowledge = object.getString(REQUIRED_KNOWLEDGE);
                String syllabus = object.getString(SYLLABUS);
                String new_release = object.getString(NEW_RELEASE);
                String homepage = object.getString(HOMEPAGE);
                String project_description = object.getString(PROJECT_DESCRIPTION);
                boolean full_course_available = object.getBoolean(FULL_COURSE_AVAILABLE);
                String faq = object.getString(FAQ);
                String banner_image = object.getString(BANNER_IMAGE);
                String short_summary = object.getString(SHORT_SUMMARY);
                String slug = object.getString(SLUG);
                boolean starter = object.getBoolean(STARTER);
                String level = object.getString(LEVEL);
                String expected_duration_unit = object.getString(EXPECTED_DURATION_UNIT);
                int expected_duration = object.getInt(EXPECTED_DURATION);
                String summary = object.getString(SUMMARY);


                ContentValues courseValues = createCourseValues(subtitle, key, image, expected_learning,
                        featured, project_name, title, required_knowledge, syllabus, new_release,
                        homepage, project_description, full_course_available, faq, banner_image,
                        short_summary, slug, starter, level, expected_duration, expected_duration_unit, summary);

                courseVector.add(courseValues);

                //Log.v(LOG_TAG, key + " - " + title);
            }
            Log.v(LOG_TAG, "BEFORE INSERT");
            Log.v(LOG_TAG, "VECTOR SIZE " + String.valueOf(courseVector.size()));

            int inserted = 0;
            if (courseVector.size() > 0) {
                ContentValues[] courseArray = new ContentValues[courseVector.size()];
                courseVector.toArray(courseArray);
                inserted = getContentResolver().bulkInsert(CourseContract.Course.CONTENT_URI, courseArray);
                Log.v(LOG_TAG, "INSERTED" + ": " + String.valueOf(inserted));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private ContentValues createCourseValues(String subtitle, String key, String image, String expected_learning,
                                             boolean featured, String project_name, String title, String required_knowledge,
                                             String syllabus, String new_release, String homepage, String project_description,
                                             boolean full_course_available, String faq, String banner_image, String short_summary,
                                             String slug, boolean starter, String level, int expected_duration,
                                             String expected_duration_unit, String summary) {
        ContentValues courseValues = new ContentValues();
        //courseValues.put(CourseContract.Course.SUBTITLE, subtitle);
        courseValues.put(CourseContract.Course.KEY, key);
        courseValues.put(CourseContract.Course.IMAGE, image);
        courseValues.put(CourseContract.Course.EXPECTED_LEARNING, expected_learning);
        courseValues.put(CourseContract.Course.FEATURED, featured);
        courseValues.put(CourseContract.Course.PROJECT_NAME, project_name);
        courseValues.put(CourseContract.Course.TITLE, title);
        courseValues.put(CourseContract.Course.REQUIRED_KNOWLEDGE, required_knowledge);
        courseValues.put(CourseContract.Course.SYLLABUS, syllabus);
        courseValues.put(CourseContract.Course.NEW_RELEASE, new_release);
        courseValues.put(CourseContract.Course.HOMEPAGE, homepage);
        courseValues.put(CourseContract.Course.PROJECT_DESCRIPTION, project_description);
        courseValues.put(CourseContract.Course.FULL_COURSE_AVAILABLE, full_course_available);
        courseValues.put(CourseContract.Course.FAQ, faq);
        courseValues.put(CourseContract.Course.BANNER_IMAGE, banner_image);
        courseValues.put(CourseContract.Course.SHORT_SUMMARY, short_summary);
        courseValues.put(CourseContract.Course.SLUG, slug);
        courseValues.put(CourseContract.Course.STARTER, starter);
        courseValues.put(CourseContract.Course.LEVEL, level);
        courseValues.put(CourseContract.Course.EXPECTED_DURATION, expected_duration);
        courseValues.put(CourseContract.Course.EXPECTED_DURATION_UNIT, expected_duration_unit);
        //This field is only used for sorting purposes
        courseValues.put(CourseContract.Course.DURATION_IN_HOURS,
                convertToHours(expected_duration, expected_duration_unit));
        courseValues.put(CourseContract.Course.SUMMARY, summary);

        return courseValues;
    }

    private int convertToHours(int numberUnit, String duration_unit) {
        if (numberUnit <= 0) {
            return 0;
        }
        if (duration_unit == null) {
            return 0;
        }
        HashMap<String, Integer> map = new HashMap<>();
        map.put("days", 24);
        map.put("weeks", 168); //24 * 7
        map.put("months", 5208); //24 * 7 * 31

        return numberUnit * map.get(duration_unit).intValue();
    }
}
