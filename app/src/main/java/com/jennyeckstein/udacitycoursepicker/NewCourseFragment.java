package com.jennyeckstein.udacitycoursepicker;


import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jennyeckstein.udacitycoursepicker.data.CourseContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewCourseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = NewCourseFragment.class.getSimpleName();
    private static final int NEW_COURSE_LOADER = 101;
    private View view;
    private CourseAdapter mCourseAdapter;
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "ON CREATE LOADER");
        String selection = CourseContract.Course.TABLE_NAME +
                "." + CourseContract.Course.NEW_RELEASE + " = ?";
        String [] selectionArgs = {"true"};
        return new android.support.v4.content.CursorLoader(
                getActivity(),
                CourseContract.Course.CONTENT_URI,
                null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "ON LOAD FINISHED");
        Log.v(LOG_TAG, "Size: " + String.valueOf(data.getCount()));

        mCourseAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCourseAdapter.swapCursor(null);
    }



    public NewCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbacks = this;
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEW_COURSE_LOADER, null, mCallbacks);
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
