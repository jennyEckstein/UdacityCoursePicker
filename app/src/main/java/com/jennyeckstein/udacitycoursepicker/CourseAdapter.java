package com.jennyeckstein.udacitycoursepicker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Jenny on 5/2/2016.
 */
public class CourseAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflator;

    public CourseAdapter(Context context, Activity activity){
        this.inflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        //TODO: remove this hardcoded value
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null){
            view = inflator.inflate(R.layout.course_item, null);

            ImageView courseImage = (ImageView) view.findViewById(R.id.course_image);
            TextView titleView = (TextView) view.findViewById(R.id.course_title);
            TextView durationView = (TextView) view.findViewById(R.id.course_duration);
            TextView newCourseView = (TextView) view.findViewById(R.id.course_new_release);
            TextView courseLevelView = (TextView) view.findViewById(R.id.course_level);

            Picasso.with(context).load(R.drawable.course_test_image).into(courseImage);
            titleView.setText("Analyze Data with Hadoop and MapReduce");
            durationView.setText("3 weeks");
            newCourseView.setText("new");
            courseLevelView.setText("BEGGINER");
        }
        return view;
    }
}
