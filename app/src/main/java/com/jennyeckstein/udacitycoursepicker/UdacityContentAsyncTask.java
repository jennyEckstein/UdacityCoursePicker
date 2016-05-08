package com.jennyeckstein.udacitycoursepicker;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jenny on 5/8/2016.
 */
public class UdacityContentAsyncTask extends AsyncTask {

    private static final String LOG_TAG = UdacityContentAsyncTask.class.getSimpleName();

    @Override
    protected Object doInBackground(Object[] params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String coursesJsonString = null;

        try{
            URL url = new URL("https://www.udacity.com/public-api/v0/courses");
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }
            if(buffer.length() == 0){
                return null;
            }
            coursesJsonString = buffer.toString();
            //Log.v(LOG_TAG, coursesJsonString);
            parseJsonString(coursesJsonString);


        }catch(IOException e){
            Log.e(LOG_TAG, "Error", e);
            return null;
        }
        finally {
            if(urlConnection  != null){
                urlConnection.disconnect();
            }
            if(reader  != null){
                try{
                    reader.close();
                }catch(final IOException e){
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return null;
    }

    private void parseJsonString(String jsonString){
        final String COURSES = "courses";
        final String KEY = "key";
        final String TITLE = "title";

        try{
            JSONObject courseObject  = new JSONObject(jsonString);
            JSONArray jsonArray = courseObject.getJSONArray(COURSES);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String key = object.getString(KEY);
                String title = object.getString(TITLE);
                Log.v(LOG_TAG, key + " - " + title);
            }


        }catch (JSONException e) {
        Log.e(LOG_TAG, e.getMessage(), e);
        e.printStackTrace();
    }
    }
}
