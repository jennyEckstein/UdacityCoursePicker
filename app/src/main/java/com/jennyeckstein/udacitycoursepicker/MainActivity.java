package com.jennyeckstein.udacitycoursepicker;

import android.content.ContentValues;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.jennyeckstein.udacitycoursepicker.data.CourseContract;
import com.jennyeckstein.udacitycoursepicker.sync.CourseSyncAdapter;

public class MainActivity extends AppCompatActivity
        implements MainActivityFragment.SendBackToMainActivity,
                    DetailActivityFragment.OnDataPass{

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ViewGroup viewGroup;
    private View listView;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    boolean mTwoPane;
    String currentKey;
    String currentVideoLike;

    @Override
    public void onDataPass(String data) {
        this.currentVideoLike = data;
        Log.v(LOG_TAG, "LIKE PASSED: " + this.currentVideoLike);
    }

    @Override
    public void sendCourseKeyToMainActivity(String courseKey) {
        this.currentKey = courseKey;
        Log.v(LOG_TAG, "Key Received by activity " + courseKey);

        if(mTwoPane){
            Log.v(LOG_TAG, "CREATING DETAIL FRAGMENT ");
            DetailActivityFragment detailActivityFragment =
                    new DetailActivityFragment();
            Bundle args = new Bundle();
            if(currentKey == null || "".equals(currentKey)){
                Log.v(LOG_TAG, "THERE IS NO KEY TO PASS");
            }else {
                final FloatingActionButton fab =
                        (FloatingActionButton) findViewById(R.id.fab);
                if (fab != null) {
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                   /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                            ContentValues courseUpdateValues = new ContentValues();

                            if("0".equals(currentVideoLike)) {
                                currentVideoLike = "1";
                                fab.setImageDrawable(getResources().getDrawable(R.mipmap.fab_on));
                            }else{
                                currentVideoLike = "0";
                                fab.setImageDrawable(getResources().getDrawable(R.mipmap.fab_off));
                            }
                            courseUpdateValues.put(CourseContract.Course.LIKED_COURSE, currentVideoLike);
                            Log.v(LOG_TAG, "LIKED: " + currentVideoLike);
                            String selection =
                                    CourseContract.Course.TABLE_NAME +
                                            "." + CourseContract.Course.KEY + " = ?";
                            String[] selectionArgs = {currentKey};

                            getContentResolver().update(
                                    CourseContract.Course.buildCourseWithId(currentKey),
                                    courseUpdateValues,
                                    selection,
                                    selectionArgs);
                        }
                    });
                }
                args.putParcelable(DetailActivityFragment.DETAIL_URI,
                        CourseContract.Course.buildCourseWithId(currentKey));
                detailActivityFragment.setArguments(args);

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail_container,
                                detailActivityFragment)
                        .commit();
            }
        }else{
            Log.v(LOG_TAG, "ONE PANE sending intent");
            Intent intent = new Intent(this, DetailActivity.class).setData(
                    CourseContract.Course.buildCourseWithId(currentKey));
            startActivity(intent);
        }
        Log.v(LOG_TAG, "WE SHOULD HAVE OUR FRAGMENT ");
    }

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

        if(findViewById(R.id.fragment_detail_container) != null){
            mTwoPane = true;
Log.v(LOG_TAG, "TWO PANE");
            if(savedInstanceState == null){
                DetailActivityFragment detailActivityFragment =
                        new DetailActivityFragment();
                Bundle args = new Bundle();
                if(currentKey == null || "".equals(currentKey)){
                    Log.v(LOG_TAG, "THERE IS NO KEY TO PASS");
                }
                args.putParcelable(DetailActivityFragment.DETAIL_URI,
                        CourseContract.Course.buildCourseWithId(currentKey));
                detailActivityFragment.setArguments(args);

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail_container,
                                 detailActivityFragment)
                .commit();
            }else{
                mTwoPane = false;
            }

        }else{
            Log.v(LOG_TAG, "ONE PANE");
            mTwoPane = false;
        }


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

       // TextView no_internet_view = (TextView) findViewById(R.id.no_internet);
       // no_internet_view.setVisibility(View.GONE);

        if(isNetworkAvailable()) {
            CourseSyncAdapter.syncImmediately(this);

        }/*else{

            no_internet_view.setText("No Internet Connection");
        }*/

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
