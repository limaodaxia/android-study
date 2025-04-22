package com.example.cp04;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener{
    private int mDelay;
    private TextView tv_alarm;
    private String mDesc="";
    private CheckBox ck_repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        tv_alarm = findViewById(R.id.tv_alarm);
        ck_repeat = findViewById(R.id.ck_repeat);
        findViewById(R.id.btn_alarm).setOnClickListener(this);
        initDelaySpinner();
    }

    private void initDelaySpinner() {
        ArrayAdapter<String> delayAdapter = new ArrayAdapter<>(this,
                R.layout.item_select, delayDescArray);
        Spinner sp_delay = findViewById(R.id.sp_delay);
        sp_delay.setPrompt("请选择闹钟延迟");
        sp_delay.setAdapter(delayAdapter);
        sp_delay.setOnItemSelectedListener(new DelaySelectedListener());
        sp_delay.setSelection(0);
    }

    private final int[] delayArray = {5, 10, 15, 20, 25, 30};
    private final String[] delayDescArray = {"5秒", "10秒", "15秒", "20秒", "25秒", "30秒"};



    class DelaySelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            mDelay = delayArray[arg2];
        }

        public void onNothingSelected(AdapterView<?> arg0) {}
    }

    public String getCurrentTime(){
        // 获取当前时间
        Date currentDate = new Date();
        // 定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        // 格式化日期
        return sdf.format(currentDate);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_alarm) {
            sendAlarm();
            mDesc = " " + getCurrentTime() + " 设置闹钟";
            tv_alarm.setText(mDesc);
        }
    }

    private void sendAlarm() {
        Intent intent = new Intent(ACTION_ALARM);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long delayTime = System.currentTimeMillis() + mDelay * 1000L;
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC, delayTime, pIntent);
    }

    private final String ACTION_ALARM = "com.example.cp04.action.ALARM";

    class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent!=null) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(2000);
                mDesc = String.format("%s\n %s", mDesc, getCurrentTime() + " 闹钟响了");
                tv_alarm.setText(mDesc);
                if (ck_repeat.isChecked()) {
                    sendAlarm();
                }
            }
        }
    }

    private AlarmReceiver mAlarmReceiver;


    @Override
    protected void onStart() {
        super.onStart();
        mAlarmReceiver = new AlarmReceiver();
        IntentFilter intentFilter = new IntentFilter(ACTION_ALARM);
        // 注册广播接收器并指定 RECEIVER_NOT_EXPORTED 标志
        registerReceiver(mAlarmReceiver, intentFilter, Context.RECEIVER_EXPORTED);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAlarmReceiver != null){
            unregisterReceiver(mAlarmReceiver);
        }
    }
}
