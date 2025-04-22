package com.example.cp04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_alarm_act).setOnClickListener(this);
        findViewById(R.id.btn_login_act).setOnClickListener(this);
        findViewById(R.id.btn_contact1_act).setOnClickListener(this);
        findViewById(R.id.btn_broadcast_act).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_alarm_act) {
            startActivity(new Intent(this, AlarmActivity.class));
        }else if(v.getId() == R.id.btn_login_act){
            startActivity(new Intent(this, LoginActivity.class));
        }else if(v.getId() == R.id.btn_contact1_act){
            startActivity(new Intent(this, ContactActivity1.class));
        }else if(v.getId() == R.id.btn_broadcast_act){
            startActivity(new Intent(this, BroadCastActivity.class));
        }
    }
}