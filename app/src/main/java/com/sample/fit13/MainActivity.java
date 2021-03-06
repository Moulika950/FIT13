package com.sample.fit13;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Hello
    Button button;
    Button button2;
    Button button3;

    TextView durationTotal;
    TextView calorieTotal;
    TextView calorieGoal;


    //start
    public  static TextView timerText;
    public  static Button start;

    public static  int timer = 30;
    //get a list of installed apps.
    public  static  int currentProfileIndex;
    public static ArrayList<AppInfo> currentProfileApps;
    final static String RECORDS_FILENAME = "ExampleItem ArrayList";
    final static String RECORDS_FILENAME_2 = "DietExItem ArrayList";
    public static ArrayList<ExampleItem> exampleList;
    public static ArrayList<DietExItem> dietExampleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        button = (Button) findViewById(R.id.button);
        durationTotal = findViewById(R.id.TotalExerciseHours);

        loadDietData();
        button = (Button) findViewById(R.id.button);
        calorieTotal = findViewById(R.id.currentCaloriesViewText);
        calorieGoal =  findViewById(R.id.caloriesText);

        getTotalHours();
        getTotalCalories();

        Intent intent = getIntent();
        String goal = intent.getStringExtra("goal");
        calorieGoal =  findViewById(R.id.caloriesText);
        calorieGoal.setText(goal);

        getSupportActionBar().hide();

        if(currentProfileApps == null)
            currentProfileApps = getInstalledApps();

        start = (Button)findViewById(R.id.Start);
        timerText = findViewById(R.id.timer);

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

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 999 && requestCode == RESULT_OK){
               calorieGoal.setText(data.getStringExtra("goal"));
        }
    }*/

    public void openNewActivity(){
        Intent intent = new Intent(this, applockActivity.class);
        startActivity(intent);
    }

    public void openWorkoutActivity(){
        Premissions();

        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }
    public void openDietActivity(){
        Premissions();

        Intent intent = new Intent(this, DietActivity.class);
        startActivityForResult(new Intent (getApplicationContext(),DietActivity.class),999);
    }

    private ArrayList<AppInfo> getInstalledApps() {
        PackageManager pm = getPackageManager();
        ArrayList<AppInfo> apps = new ArrayList<AppInfo>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!isSystemPackage(p)) && !p.packageName.equals("com.sample.fit13")) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                String packages = p.applicationInfo.packageName;
                apps.add(new AppInfo(appName, icon, packages));
            }
        }
        return apps;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public  void createService(){

        Intent intent = new Intent(this,BroadcastService.class);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            System.out.println("Starting foreground");
            startForegroundService(intent);
        }else{
            System.out.println("Starting just Intent");
            startService(intent);
        }

        startService(intent);
    }

    public  void startView(View view){
        createService();
    }

    public  void Premissions(){

        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow("android:get_usage_stats",
                android.os.Process.myUid(), getPackageName());
        boolean granted = mode == AppOpsManager.MODE_ALLOWED;

        if(granted == false){
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new  Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent, 12345);
            }
        }
    }

    //Method to get and set total hours worked out for user
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
            if(totalMinutes>=60){
                totalMinutes = totalMinutes - 60;
                totalHours++;
            }
        }

        durationTotal.setText(Integer.toString(totalHours));

    }

    void getTotalCalories(){

        int totalCals = 0;
        for(int i = 0; i < dietExampleList.size(); i++){
            String calTotal = dietExampleList.get(i).getCalories();
            int cal = Integer.parseInt(calTotal);
            totalCals += cal;
        }
        calorieTotal.setText(Integer.toString(totalCals));
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("log list", null);
        Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        exampleList = gson.fromJson(json, type);

        if(exampleList == null) {
            exampleList = new ArrayList<>();

        }

    }

    private void loadDietData() {
        SharedPreferences sharedPreferences = getSharedPreferences("diet shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("diet log list", null);
        Type type = new TypeToken<ArrayList<DietExItem>>() {}.getType();
        dietExampleList = gson.fromJson(json, type);

        if(dietExampleList == null) {
            dietExampleList = new ArrayList<>();

        }
    }



}