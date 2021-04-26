package com.sample.fit13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sample.fit13.CreateDialog;
import com.sample.fit13.ExampleAdapter;
import com.sample.fit13.ExampleItem;
import com.sample.fit13.R;
import com.sample.fit13.Workout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//Class to handle workout log page
public class WorkoutActivity extends AppCompatActivity implements CreateDialog.CreateDialogListener{

    //Initialize variables
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView createItem;
    private Workout workout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_activity);
        getSupportActionBar().hide();


        //Button click to open insert workout log dialog
        createItem = findViewById(R.id.create_new);
        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        buildRecyclerView();

    }

    //Method to insert log to recycler view and display it
    public void createLog(){
        mAdapter.notifyItemInserted(MainActivity.exampleList.size()-1);
    }

    //Method to remove log from recycler
    public void removeItem(int position) {
        MainActivity.exampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    //Method to build recycler view
    private void buildRecyclerView() {
        mRecyclerView= findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(this, MainActivity.exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    //Creates custom Alert Dialog
    public void openDialog() {
        CreateDialog createDialog = new CreateDialog();

        createDialog.show(getSupportFragmentManager(), "create dialog");

    }

    //Inserts new log Object to ArrayList
    private void insertItem(Workout w) {

        ExampleItem m = new ExampleItem();
        m.setTitle(w.getTitle());
        m.setDuration(w.getDuration());
        m.setDate(w.getDate());
        m.setDescription(w.getDescription());

        //Image files
        m.setWeightImg(R.drawable.weight_icon);
        m.setCloseImg(R.drawable.delete_icon);
        MainActivity.exampleList.add(m);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.exampleList);
        editor.putString("log list", json);
        editor.apply();

    }





        //CreateDialogListener interface
    @Override
    public void saveData(Workout w) {
        workout = w;
        insertItem(workout);

    }




}



