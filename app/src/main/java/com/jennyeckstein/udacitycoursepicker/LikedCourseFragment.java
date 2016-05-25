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
import android.support.v4.content.CursorLoader;
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
public class LikedCourseFragment extends Fragment
                implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = LikedCourseFragment.class.getSimpleName();
    private static final int LIKED_COURSE_LOADER = 202;
    private View view;
    private CourseAdapter mCourseAdapter;
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = CourseContract.Course.TABLE_NAME +
                "." + CourseContract.Course.LIKED_VIDEO + " = ?";
        String [] selectionArgs = {"1"};

        return new CursorLoader(getActivity(),
                CourseContract.Course.CONTENT_URI,
                null, selection,selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCourseAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCourseAdapter.swapCursor(null);
    }

    public LikedCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbacks = this;
        android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LIKED_COURSE_LOADER, null, mCallbacks);
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
