package com.sample.fit13;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Hello
    Button button;
    Button button2;
    Button button3;

    public  static TextView timerText;
    public  static Button start;

    public static  int timer = 30;
    //get a list of installed apps.
    public  static  int currentProfileIndex;
    public static ArrayList<AppInfo> currentProfileApps;

    public static ArrayList<ExampleItem> exampleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);

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

        Premissions();
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

}