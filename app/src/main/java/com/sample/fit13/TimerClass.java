package com.sample.fit13;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class TimerClass {
    CountDownTimer countDownTimer;
    long timeLeftMillisecond = 0;//10
    public static int minutes = 0;
    public static int seconds = 0;

    BroadcastService service;

    TimerClass(BroadcastService service){
        this.service = service;
    }


    public  void StartTimer(int minutes){

        //MainActivity.currentProfileApps = MainActivity.installedApps;

        if(minutes < 1)
            minutes = 1;

        //MainActivity.bar.setMax(minutes);

        timeLeftMillisecond = minutes * 60000;

        countDownTimer = new CountDownTimer(timeLeftMillisecond,1000) {
            @Override
            public void onTick(long l) {
                timeLeftMillisecond = l;
                upDateTimer();
            }


            @Override
            public void onFinish() {
                MainActivity.start.setVisibility(View.VISIBLE);
                MainActivity.timerText.setText("Done");

            }
        }.start();
    }


    private void upDateTimer() {
        minutes = (int) timeLeftMillisecond / 60000;
        seconds = (int) timeLeftMillisecond % 60000 / 1000;

        String secondsText = "";

        if(seconds < 10) secondsText += "0";

        secondsText += seconds;

        MainActivity.timerText.setText("Time: "+ minutes+":"+secondsText);

       MainActivity.start.setVisibility(View.INVISIBLE);


        for(AppInfo app : MainActivity.currentProfileApps)
        {
            service.checkBackgroundApps();
        }
    }




}
