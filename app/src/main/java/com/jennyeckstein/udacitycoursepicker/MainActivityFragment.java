package com.jennyeckstein.udacitycoursepicker;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private View view;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) view.findViewById(R.id.courseView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                getActivity().startActivity(intent,bundle);
            }
        });

        Log.v(LOG_TAG, "Start Adapter");
        CourseAdapter courseAdapter = new CourseAdapter(getContext(), getActivity());
        listView.setAdapter(courseAdapter);
        return view;
    }
}
