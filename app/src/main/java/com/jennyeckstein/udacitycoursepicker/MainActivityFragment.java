package com.jennyeckstein.udacitycoursepicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jennyeckstein.udacitycoursepicker.data.CourseContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private static final int COURSE_LOADER = 0;
    private View view;
    private CourseAdapter mCourseAdapter;
    SendBackToMainActivity sendBackToMainActivity;

    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    public interface SendBackToMainActivity{
        public void sendCourseKeyToMainActivity(String courseKey);
        public void onFirstLoad(String currentKey);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendBackToMainActivity = (SendBackToMainActivity) context;
    }

    public void sendToMain(String courseKey){
        sendBackToMainActivity.sendCourseKeyToMainActivity(courseKey);
    }
    public void firstLoad(String courseKey){
        sendBackToMainActivity.onFirstLoad(courseKey);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
      //  Log.v(LOG_TAG, "ON CREATE LOADER");
        String selection = CourseContract.Course.TABLE_NAME +
                "." + CourseContract.Course.LEVEL + " = ?";
        String [] selectionArgs = {"beginner"};
        return new android.support.v4.content.CursorLoader(
                getActivity(),
                CourseContract.Course.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mCourseAdapter.swapCursor(null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
       // Log.v(LOG_TAG, "ON LOAD FINISHED");
       // Log.v(LOG_TAG, "Size: " + String.valueOf(data.getCount()));

        if(data.moveToFirst() == true){
            firstLoad(data.getString(data.getColumnIndex(CourseContract.Course.KEY)));
        }else{
          //  Log.v(LOG_TAG, "FIRST LOAD - THERE IS NO KEY TO PASS");
        }


        mCourseAdapter.swapCursor(data);
    }

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbacks = this;
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(COURSE_LOADER, null, mCallbacks);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//Log.v(LOG_TAG, "onCreateView");
        mCourseAdapter = new CourseAdapter(getActivity(), null, 0);
        this.view = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) view.findViewById(R.id.courseView);
        listView.setAdapter(mCourseAdapter);
   //     Log.v(LOG_TAG, "adapter set");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();

                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(getActivity());
                    int courseKeyColumn = cursor.getColumnIndex(CourseContract.Course.KEY);
                    String courseKey = cursor.getString(courseKeyColumn);
                    sendToMain(courseKey);
           //         Log.v(LOG_TAG, "COURSE KEY SENT TO MAIN ACTIVITY " + courseKey);

                    /*int courseKeyColumn = cursor.getColumnIndex(CourseContract.Course.KEY);
                    String courseKey = cursor.getString(courseKeyColumn);
                    Intent intent = new Intent(getActivity(), DetailActivity.class)
                            .setData(CourseContract.Course.buildCourseWithId(courseKey));
                    getActivity().startActivity(intent, bundle);*/
                }

            }
        });
        return view;
    }
}
