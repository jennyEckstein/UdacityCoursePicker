package com.jennyeckstein.udacitycoursepicker;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment{

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
       // ImageView imageView = (ImageView) view.findViewById(R.id.detail_course_image);
       // Picasso.with(getContext()).load(R.drawable.course_test_image).into(imageView);

        return view;
    }
}
