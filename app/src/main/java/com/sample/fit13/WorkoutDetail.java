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

public class WorkoutDetail extends AppCompatActivity implements CreateDialog.CreateDialogListener{

    TextView wdTitle, wdDate, wdDuration, wdDescription;
    ImageView wdImage;
    ArrayList<ExampleItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.workout_detail);

        Intent intent = getIntent();

        wdTitle = findViewById(R.id.detail_title);
        wdDate = findViewById(R.id.detail_date);
        wdDuration = findViewById(R.id.detail_duration);
        wdDescription = findViewById(R.id.detail_description);
        wdImage = findViewById(R.id.imageView);


        String mTitle = intent.getStringExtra("iTitle");
        String mDuration = intent.getStringExtra("iDur");
        String mDate = intent.getStringExtra("iDate");
        String mDescription = intent.getStringExtra("iDes");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(mTitle);
        wdTitle.setText(mTitle);
        wdDuration.setText(mDuration);
        wdDate.setText(mDate);
        wdDescription.setText(mDescription);



    }

    public boolean onOptionsItemSelected(MenuItem item){


        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void openDialog() {
        CreateDialog createDialog = new CreateDialog();

        createDialog.show(getSupportFragmentManager(), "create dialog");

    }

    public void insertItem(Workout w) {

        ExampleItem m = new ExampleItem();
        m.setTitle(w.getTitle());
        m.setDuration(w.getDuration());
        m.setDate(w.getDate());
        m.setDescription(w.getDescription());

        //Image files
        m.setWeightImg(R.drawable.weight_icon);
        m.setCloseImg(R.drawable.delete_icon);
        MainActivity.exampleList.add(m);

    }

    @Override
    public void saveData(Workout w) {
        insertItem(w);
    }
}



