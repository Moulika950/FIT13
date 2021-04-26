package com.sample.fit13;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.sample.fit13.ExampleItem;
import com.sample.fit13.R;

import java.util.ArrayList;

//Class to handle workout detail page
public class WorkoutDetail extends AppCompatActivity {

    TextView wdTitle, wdDate, wdDuration, wdDescription;
    ImageView wdImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.workout_detail);



        wdTitle = findViewById(R.id.detail_title);
        wdDate = findViewById(R.id.detail_date);
        wdDuration = findViewById(R.id.detail_duration);
        wdDescription = findViewById(R.id.detail_description);
        wdImage = findViewById(R.id.imageView);

        //Gets Information to fill in textViews from workout_log page
        Intent intent = getIntent();


        String mTitle = intent.getStringExtra("iTitle");
        String mDuration = intent.getStringExtra("iDur");
        String mDate = intent.getStringExtra("iDate");
        String mDescription = intent.getStringExtra("iDes");



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        //Puts information on page TextViews
        wdTitle.setText(mTitle);
        wdDuration.setText(mDuration);
        wdDate.setText(mDate);
        wdDescription.setText(mDescription);



    }

    //Overloads back button to have custom result
    public boolean onOptionsItemSelected(MenuItem item){


        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }



}



