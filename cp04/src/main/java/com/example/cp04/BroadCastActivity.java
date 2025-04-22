package com.example.cp04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BroadCastActivity extends AppCompatActivity {

    private BroadCastReceiver1 receiver1;
    private BroadCastReceiver2 receiver2;
    private BroadCastReceiver3 receiver3;

    private TextView tv_broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);
        findViewById(R.id.btn_broadcast).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.example.cp04.broadcast");
            sendOrderedBroadcast(intent, null);
        });
        tv_broadcast = findViewById(R.id.tv_broadcast);
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver1 = new BroadCastReceiver1();
        receiver2 = new BroadCastReceiver2();
        receiver3 = new BroadCastReceiver3();
        IntentFilter filter1 = new IntentFilter("com.example.cp04.broadcast");
        filter1.setPriority(50);
        IntentFilter filter2 = new IntentFilter("com.example.cp04.broadcast");
        filter2.setPriority(30);
        IntentFilter filter3 = new IntentFilter("com.example.cp04.broadcast");
        filter3.setPriority(100);
        registerReceiver(receiver1, filter1, RECEIVER_EXPORTED);
        registerReceiver(receiver2, filter2, RECEIVER_EXPORTED);
        registerReceiver(receiver3, filter3, RECEIVER_EXPORTED);

    }

    class BroadCastReceiver1 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            tv_broadcast.append("receiver1接收到了消息\n");
        }
    }

    class BroadCastReceiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            tv_broadcast.append("receiver2接收到了消息\n");
            abortBroadcast();
        }
    }

    class BroadCastReceiver3 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            tv_broadcast.append("receiver3接收到了消息\n");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (receiver1 != null) {
            unregisterReceiver(receiver1);
        }
        if (receiver2 != null) {
            unregisterReceiver(receiver2);
        }
        if (receiver3 != null) {
            unregisterReceiver(receiver3);
        }
    }
}