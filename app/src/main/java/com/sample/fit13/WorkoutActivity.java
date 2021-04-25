package com.sample.fit13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sample.fit13.CreateDialog;
import com.sample.fit13.ExampleAdapter;
import com.sample.fit13.ExampleItem;
import com.sample.fit13.R;
import com.sample.fit13.Workout;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity implements CreateDialog.CreateDialogListener{

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ExampleItem> exampleList = new ArrayList<>();
    private TextView createItem;
    private Workout workout;
    private String tester;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_activity);



        createItem = findViewById(R.id.create_new);
        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        buildRecyclerView();

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            exampleList = intent.getParcelableArrayListExtra("Example Item");
            createLog();

        }
    };

    public void createLog(){
        mAdapter.notifyItemInserted(exampleList.size()-1);
    }

    public void removeItem(int position) {
        exampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void buildRecyclerView() {
        mRecyclerView= findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(this, exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                createLog();
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
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
        exampleList.add(m);

    }

    @Override
    public void saveData(Workout w) {
        workout = w;
        insertItem(workout);
    }


}



