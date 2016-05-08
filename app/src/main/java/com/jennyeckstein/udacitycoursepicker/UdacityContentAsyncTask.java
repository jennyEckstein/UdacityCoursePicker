package com.jennyeckstein.udacitycoursepicker;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
            URL url = new URL("");


        }catch(Exception e){
            e.getMessage();
        }

        return null;
    }
}
