package com.jennyeckstein.udacitycoursepicker;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jennyeckstein.udacitycoursepicker.data.CourseContract;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";
    private static final int DETAIL_LOADER = 1;
    private Uri passedUri;
    Context context;
    String title, image, summary;
    OnDataPass dataPass;
    String url;
    String subtitle,durationUnit, expected_duration,level,
            required_knowledge,syllabus, faq, key;

    public TextView durationTextView;

    public interface OnDataPass{
        public void onDataPass(String data);
    }

/*
    @Override
    public void onStart() {
        super.onStart();
        this.dataPass = (OnDataPass) getActivity();
    }
*/

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPass = (OnDataPass) context;
    }*/

    public void passData(String data){
        dataPass.onDataPass(data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (this.passedUri != null) {
           // Log.v(LOG_TAG, "creating loader: " + this.passedUri.toString());
            String key = this.passedUri.getPathSegments().get(1);
            return new CursorLoader(
                    getActivity(), this.passedUri, null, null, new String[]{key}, null);

        }else{
          //  Log.v(LOG_TAG, "creating loader, passedUri is empty");
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(!data.moveToFirst()){
         //   Log.v(LOG_TAG, "NO DATA RETURNED");
            return;
        }
        int keyColumn = data.getColumnIndex(CourseContract.Course.KEY);
        int titleColumn = data.getColumnIndex(CourseContract.Course.TITLE);
        int imageColumn = data.getColumnIndex(CourseContract.Course.IMAGE);
        int summaryColumn = data.getColumnIndex(CourseContract.Course.SUMMARY);
        int subtitleColumn = data.getColumnIndex(CourseContract.Course.SUBTITLE);
        int durationUnitColumn = data.getColumnIndex(CourseContract.Course.EXPECTED_DURATION_UNIT);
        int expected_duration = data.getColumnIndex(CourseContract.Course.EXPECTED_DURATION);
        int level = data.getColumnIndex(CourseContract.Course.LEVEL);
        int required_knowledge = data.getColumnIndex(CourseContract.Course.REQUIRED_KNOWLEDGE);
        int syllabus = data.getColumnIndex(CourseContract.Course.SYLLABUS);
        int faq = data.getColumnIndex(CourseContract.Course.FAQ);
        final int homepage = data.getColumnIndex(CourseContract.Course.HOMEPAGE);



        this.key = data.getString(keyColumn);
        this.title = data.getString(titleColumn);
        this.image = data.getString(imageColumn);
        this.summary = data.getString(summaryColumn);
      //  this.subtitle = data.getString(subtitleColumn);
        this.durationUnit = data.getString(durationUnitColumn);
        this.expected_duration = data.getString(expected_duration);
        this.level = data.getString(level);
        this.required_knowledge = data.getString(required_knowledge);
        this.syllabus = data.getString(syllabus);
        this.faq = data.getString(faq);
        final String homepageUrl = data.getString(homepage);


       // Log.v(LOG_TAG, title + " | " + image + " | " + summary);

        final String dur = this.expected_duration + " " + this.durationUnit;

        ViewHolder mViewHolder = (ViewHolder) getView().getTag();
       // mViewHolder.subtitleTextView.setText(this.subtitle);
        mViewHolder.durationTextView.setText(dur);
        mViewHolder.levelTextView.setText(this.level);
        mViewHolder.requiredKnowledge.setText(this.required_knowledge);
        mViewHolder.summaryTextView.setText(this.summary);
        mViewHolder.syllabusTextView.setText(this.syllabus);
        mViewHolder.faqTextView.setText(this.faq);

        mViewHolder.beginCourseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(homepageUrl));
                startActivity(intent);
            }
        });

      /*  Log.v(LOG_TAG, "SETTING DURATION " + dur);
        if(dur != null || !"".equals(dur)) {
            if(durationTextView != null) {
                Log.v(LOG_TAG, "SETTING TEXT VIEW " + dur);
                durationTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        durationTextView.setText(dur);
                    }
                });

            }
        }else{
            Log.v(LOG_TAG, "FAIL SETTING TEXT VIEW " + dur);
        }*/

       /* TextView levelTextView = (TextView) getView().findViewById(R.id.course_level);
        levelTextView.setText(level);

        TextView requiredKnowledgeTextView = (TextView) getView().findViewById(R.id.required_knowledge);
        requiredKnowledgeTextView.setText(required_knowledge);
*/
/*
        TextView summaryTextView = (TextView) getView().findViewById(R.id.summary);

        if(summaryTextView != null) {
            Log.v(LOG_TAG, "SUMMARY IS NOT NULL!!!!!!!!!!!");
            Log.v(LOG_TAG, "SUMMARY: " + summary);
            summaryTextView.setText(summary);
        }else{
            Log.v(LOG_TAG, "SUMMARY IS NULL!!!!!!!!!!!");
        }
*//*
        TextView syllabusTextView = (TextView) getView().findViewById(R.id.syllabus);
        syllabusTextView.setText(faq);*/

       // getActivity().setTitle(title);

        CollapsingToolbarLayout collapsingToolbarLayout = ((DetailActivity) getActivity()).getCollapsingToolbarLayout();
               if(collapsingToolbarLayout != null){
                   Log.v(LOG_TAG, "SUCCESS");
                   collapsingToolbarLayout.setTitle(title);
               }else{
                   Log.v(LOG_TAG, "ITS NULLLLLLLLLL");
               }


       // ((DetailActivity)context).getSupportActionBar().setTitle(title);

        /*CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);*/
        //((AppCompatActivity)this.getActivity()).setTitle(title);

        //android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        //toolbar.setTitle(title);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        //((DetailActivity)getActivity()).getSupportActionBar().setTitle(title);
        //((DetailActivity)getActivity()).getActionBar().setTitle(title);

         ImageView imageView = (ImageView) getActivity().findViewById(R.id.detail_course_image_appBarLayout);

        if(imageView != null) {
            Picasso.with(context).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(imageView);
        }
   /*     TextView summaryView = (TextView) getView().findViewById(R.id.course_description);
        summaryView.setText(summary);*/

      //  TextView subtitleView = (TextView) getView().findViewById(R.id.course_subtitle);
      //  subtitleView.setText(subtitle);

      //  Button start_course_button = (Button) getView().findViewById(R.id.begin_course_button);
        //todo add on click listener for the button
       // start_course_button.setText(key);

      //  url = data.getString(data.getColumnIndex(CourseContract.Course.HOMEPAGE));
      //  start_course_button.setOnClickListener(new View.OnClickListener(){
      /*      @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });*/

        String like = data.getString(data.getColumnIndex(CourseContract.Course.LIKED_COURSE));
        ((OnDataPass) getActivity()).onDataPass(like);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if (fab != null) {
            if ("1".equals(like)) {
                fab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.fab_on));
            } else {
                fab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.fab_off));
            }
        }

    //    Log.v(LOG_TAG, "ON LOAD FINISHED");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public DetailActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null){
          //  Log.v(LOG_TAG, "Setting passed URI");
            this.passedUri = arguments.getParcelable(DetailActivityFragment.DETAIL_URI);
          //  Log.v(LOG_TAG, "PASSES URI: " + this.passedUri);

            //TODO: fix init loader
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        }else{
            //TODO: take care of else case
         //   Log.v(LOG_TAG, "NO ARGUMENTS - NO LOADER");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        context = (Context)getContext();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Log.v(LOG_TAG, "CREATED DETAIL FRAGMENT");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ViewHolder mViewHolder = new ViewHolder(view, 0);
        view.setTag(mViewHolder);
        return view;
    }


    public static class ViewHolder{
        public TextView subtitleTextView;
        public TextView durationTextView;
        public TextView levelTextView;
        public Button beginCourseButton;
        public TextView requiredKnowledge;
        public TextView summaryTextView;
        public TextView syllabusTextView;
        public TextView faqTextView;

        public ViewHolder(View view, int layoutId){
            this.subtitleTextView = (TextView) view.findViewById(R.id.subtitle);
            this.durationTextView = (TextView) view.findViewById(R.id.duration);
            this.levelTextView = (TextView) view.findViewById(R.id.level);
            this.beginCourseButton = (Button) view.findViewById(R.id.begin_course);
            this.requiredKnowledge = (TextView) view.findViewById(R.id.required_knowledge);
            this.summaryTextView = (TextView) view.findViewById(R.id.summary);
            this.syllabusTextView = (TextView) view.findViewById(R.id.syllabus);
            this.faqTextView = (TextView) view.findViewById(R.id.faq);
        }



    }


}
