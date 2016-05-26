package com.jennyeckstein.udacitycoursepicker;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jennyeckstein.udacitycoursepicker.sync.CourseSyncAdapter;

public class MainActivity extends AppCompatActivity{

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ViewGroup viewGroup;
    private View listView;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(LOG_TAG, "SAVE INSTANCE");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(LOG_TAG, "RESTORE INSTANCE");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new MainActivityFragment(), "Begginer");
        viewPagerAdapter.addFragments(new NewCourseFragment(), "New");
        viewPagerAdapter.addFragments(new ShortCourseFragment(), "Short");
        viewPagerAdapter.addFragments(new LikedCourseFragment(), "Liked");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        getWindow().setExitTransition(new Explode());

        TextView no_internet_view = (TextView) findViewById(R.id.no_internet);
        no_internet_view.setVisibility(View.GONE);

        if(isNetworkAvailable()) {
            CourseSyncAdapter.syncImmediately(this);

        }else{

            no_internet_view.setText("No Internet Connection");
        }

   /*     Intent alarmIntent = new Intent(this, CourseService.AlarmReceiver.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5000, pendingIntent);*/


        //Now we call it from AlarmReceiver
       // Intent intent = new Intent(this, CourseService.class);
       // this.startService(intent);

    }

    private boolean isNetworkAvailable(){
        try{
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            //TODO check other valid connections
            NetworkInfo netInfo =
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED){
                return true;
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
