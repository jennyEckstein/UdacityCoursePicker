package com.jennyeckstein.udacitycoursepicker.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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

    }


    public static class AlarmReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(LOG_TAG, "RECEIVED ALARM");
            Intent sendIntent = new Intent(context, CourseService.class);
            context.startService(sendIntent);
        }
    }
}
