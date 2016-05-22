package com.jennyeckstein.udacitycoursepicker;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jennyeckstein.udacitycoursepicker.data.CourseContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private static final int COURSE_LOADER = 0;
    private View view;
    private CourseAdapter mCourseAdapter;

    private android.app.LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "ON CREATE LOADER");
        return new CursorLoader(getActivity(), CourseContract.Course.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCourseAdapter.swapCursor(null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "ON LOAD FINISHED");
        Log.v(LOG_TAG, "Size: " + String.valueOf(data.getCount()));

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
Log.v(LOG_TAG, "onCreateView");
        mCourseAdapter = new CourseAdapter(getActivity(), null, 0);
        this.view = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) view.findViewById(R.id.courseView);
        listView.setAdapter(mCourseAdapter);
        Log.v(LOG_TAG, "adapter set");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();

                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(getActivity());
                    int courseKeyColumn = cursor.getColumnIndex(CourseContract.Course.KEY);
                    String courseKey = cursor.getString(courseKeyColumn);
                    Intent intent = new Intent(getActivity(), DetailActivity.class)
                            .setData(CourseContract.Course.buildCourseWithId(courseKey));
                    getActivity().startActivity(intent, bundle);
                }

            }
        });
        return view;
    }
}
