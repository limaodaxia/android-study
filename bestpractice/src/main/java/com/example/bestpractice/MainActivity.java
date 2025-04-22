package com.example.bestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bestpractice.aidl.AIDLActivity;
import com.example.bestpractice.handler.HandlerActivity;
import com.example.bestpractice.loop_scroll.LoopActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start_aidl).setOnClickListener(this);
        findViewById(R.id.btn_start_handler).setOnClickListener(this);
        findViewById(R.id.btn_start_loop).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start_aidl) {
            startActivity(new Intent(this, AIDLActivity.class));
        }else if (v.getId() == R.id.btn_start_handler) {
            startActivity(new Intent(this, HandlerActivity.class));
        }else if (v.getId() == R.id.btn_start_loop) {
            startActivity(new Intent(this, LoopActivity.class));
        }
    }
}