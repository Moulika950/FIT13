package com.sample.fit13;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

public class DietActivity extends AppCompatActivity implements DietDialog.DietDialogListener {

    private RecyclerView mRecyclerView;
    private DietAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView createItem;
    private Diet diet;
    private String tester;
    private Button bmiBtn;
    public TextView savedCalorieGoal;
    public Button setCalGoalButton;
    private EditText calorieGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createItem = findViewById(R.id.add_new_meal);
        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        buildRecyclerView();

        bmiBtn = findViewById(R.id.setBMIButton);
        bmiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, BMIClass.class);
                startActivity(intent);
            }
        });

        calorieGoal=(EditText)findViewById(R.id.calorieGoal);
        setCalGoalButton=(Button)findViewById(R.id.setCalGoalButton);
        savedCalorieGoal=(TextView)findViewById(R.id.savedCalorieGoal);


        setCalGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String goal = calorieGoal.getText().toString();
                savedCalorieGoal.setText(goal);
                Intent intent = new Intent(DietActivity.this, MainActivity.class);
                intent.putExtra("goal", goal);
                startActivity(intent);
            }
        });

    }


    public void createLog(){
        mAdapter.notifyItemInserted(MainActivity.dietExampleList.size()-1);
    }

    public void removeItem(int position) {
        MainActivity.dietExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }


    private void buildRecyclerView() {
        mRecyclerView= findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(this);
        mAdapter = new DietAdapter(this, MainActivity.dietExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DietAdapter.OnItemClickListener() {


            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    public void openDialog() {
        DietDialog dietDialog = new DietDialog();
        dietDialog.show(getSupportFragmentManager(), "diet dialog");

    }

    public void insertItem(Diet d) {

        DietExItem m = new DietExItem();
        m.setMeal(d.getMeal());
        m.setCalories(d.getCalories());
        m.setDate(d.getDate());
        m.setDescription(d.getDescription());

        //Image files
        m.setMealImg(R.drawable.meal);
        m.setCloseImg(R.drawable.delete_icon);
        MainActivity.dietExampleList.add(m);
        buildRecyclerView();

        SharedPreferences sharedPreferences = getSharedPreferences("diet shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.dietExampleList);
        editor.putString("diet log list", json);
        editor.apply();
    }

    @Override
    public void saveData(Diet d) {
        diet = d;
        insertItem(diet);
        buildRecyclerView();
    }

}

