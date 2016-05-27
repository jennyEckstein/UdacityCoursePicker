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
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";
    private static final int DETAIL_LOADER = 1;
    private Uri passedUri;
    Context context;
    String title, image, summary;
    OnDataPass dataPass;
    String url;

    public interface OnDataPass{
        public void onDataPass(String data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPass = (OnDataPass) context;
    }

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
            Log.v(LOG_TAG, "creating loader: " + this.passedUri.toString());
            String key = this.passedUri.getPathSegments().get(1);
            return new CursorLoader(
                    getActivity(), this.passedUri, null, null, new String[]{key}, null);

        }else{
            Log.v(LOG_TAG, "creating loader, passedUri is empty");
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(!data.moveToFirst()){
            Log.v(LOG_TAG, "NO DATA RETURNED");
            return;
        }
        int titleColumn = data.getColumnIndex(CourseContract.Course.TITLE);
        int imageColumn = data.getColumnIndex(CourseContract.Course.IMAGE);
        int summaryColumn = data.getColumnIndex(CourseContract.Course.SUMMARY);

        this.title = data.getString(titleColumn);
        this.image = data.getString(imageColumn);
        this.summary = data.getString(summaryColumn);

        Log.v(LOG_TAG, title + " | " + image + " | " + summary);

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
         Picasso.with(context).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(imageView);

        TextView summaryView = (TextView) getView().findViewById(R.id.course_description);
        summaryView.setText(summary);

        Button start_course_button = (Button) getView().findViewById(R.id.begin_course_button);
        //todo add on click listener for the button

        url = data.getString(data.getColumnIndex(CourseContract.Course.HOMEPAGE));
        start_course_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        String like = data.getString(data.getColumnIndex(CourseContract.Course.LIKED_COURSE));
        passData(like);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if ("1".equals(like)){
            fab.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.fab_on));
        }else{
            fab.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.fab_off));
        }
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
            Log.v(LOG_TAG, "Setting passed URI");
            this.passedUri = arguments.getParcelable(DetailActivityFragment.DETAIL_URI);
            Log.v(LOG_TAG, "PASSES URI: " + this.passedUri);

            //TODO: fix init loader
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        }else{
            //TODO: take care of else case
            Log.v(LOG_TAG, "NO ARGUMENTS - NO LOADER");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        context = (Context)getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View activityView = inflater.inflate(R.layout.activity_detail, container, false);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
       // ImageView imageView = (ImageView) view.findViewById(R.id.detail_course_image);
       // Picasso.with(getContext()).load(R.drawable.course_test_image).into(imageView);

        //((DetailActivity)getActivity()).getActionBar().setTitle(title);







        return view;
    }
}
