package com.jennyeckstein.udacitycoursepicker;

import android.os.AsyncTask;
import android.util.Log;

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
            Log.v(LOG_TAG, "EXECUTING inside");
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
        //TODO: implement parse
    }
}
