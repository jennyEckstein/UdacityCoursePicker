package com.jennyeckstein.udacitycoursepicker;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Jenny on 5/8/2016.
 */
public class UdacityContentAsyncTask extends AsyncTask {

    private static final String LOG_TAG = UdacityContentAsyncTask.class.getSimpleName();

    private Context mContext;

    public UdacityContentAsyncTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Object doInBackground(Object[] params) {
       /* HttpURLConnection urlConnection = null;
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
        }*/

        return null;
    }
}
