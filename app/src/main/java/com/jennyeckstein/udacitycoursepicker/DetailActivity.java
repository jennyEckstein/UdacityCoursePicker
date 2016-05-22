package com.jennyeckstein.udacitycoursepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ActionProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    private ActionProvider mShareActionProvider;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return collapsingToolbarLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle arguments = new Bundle();
        arguments.putParcelable(DetailActivityFragment.DETAIL_URI, getIntent().getData());
        DetailActivityFragment fragment = new DetailActivityFragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction().add(R.id.detail_container, fragment).commit();

        //setupWindowAnimations();

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);



        this.collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsingToolbarLayout.setTitle("Booo");

        //getSupportActionBar().setTitle("YAY");

        //ImageView imageView1 = (ImageView) findViewById(R.id.detail_course_image_appBarLayout);
        //Picasso.with(this).load(R.drawable.course_test_image).into(imageView1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
        }else if(id == R.id.action_share){
            //TODO: in the future it should share course title and other useful info
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Awesome Course #UdacityCoursePicker");
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
