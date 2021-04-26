package com.sample.fit13;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Hello
    Button button;
    Button button2;
    Button button3;

    EditText durationTotal;

    public static ArrayList<ExampleItem> exampleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        durationTotal = findViewById(R.id.TotalExerciseHours);

        getSupportActionBar().hide();
        getTotalHours();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWorkoutActivity();
            }
        });
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDietActivity();
            }
        });
    }
    public void openNewActivity(){
        Intent intent = new Intent(this, applockActivity.class);
        startActivity(intent);
    }
    public void openWorkoutActivity(){
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }
    public void openDietActivity(){
        Intent intent = new Intent(this, DietActivity.class);
        startActivity(intent);
    }

    void getTotalHours(){

        int totalHours = 0;
        int totalMinutes = 0;
        for(int i = 0; i < exampleList.size(); i++){
            String duration = exampleList.get(i).getDuration();
            String[] parts = duration.split(":");
            String part1 = parts[0];
            String part2 = parts[1];
            int hours = Integer.parseInt(part1);
            totalHours += hours;
            int minutes = Integer.parseInt(part2);
            totalMinutes += minutes;
            if(totalMinutes>60){
                totalMinutes = totalMinutes - 60;
                totalHours++;
            }
        }

        durationTotal.setText(Integer.toString(totalHours));

    }
}