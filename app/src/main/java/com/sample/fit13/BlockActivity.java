package com.sample.fit13;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BlockActivity extends AppCompatActivity {

    Button Close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_block2);

        Close = findViewById(R.id.close);

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    public  void Close(View view){

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}