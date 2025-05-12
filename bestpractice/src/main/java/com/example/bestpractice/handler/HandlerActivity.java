package com.example.bestpractice.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bestpractice.R;

public class HandlerActivity extends AppCompatActivity {

    private Handler MainThreadHandler;

    private Handler SubThreadHandler;

    private TextView tv_result;

    private Button btn_get_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        tv_result = findViewById(R.id.tv_result);
        btn_get_data = findViewById(R.id.btn_get_data);
        btn_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 子线程给主线程的handler发消息
                Message msg = new Message();
                msg.what = 1;
                msg.obj = "123";

            }
        });

        // 初始化 Handler, 这个是绑定了主线程的looper
        MainThreadHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
                    String info= "msg from thread1 = " + msg.arg1;
                    // 处理消息
                    tv_result.setText(info);
                }else if (msg.what == 2){
                    Log.d("HandlerActivity", "get message from SubHandler");
                    String info= "msg from SubHandler = " + msg.arg1;
                    // 处理消息
                    tv_result.setText(info);
                }
                return true;
            }
        });

        // 子线程给主线程的handler发消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 发送消息
                int count = 0;
                while(true){
                    count++;
                    try {
                        Message mes = Message.obtain();
                        mes.what = 1;
                        mes.arg1 = count;
                        MainThreadHandler.sendMessage(mes);

                        if(SubThreadHandler != null ){
                            Message mes2 = Message.obtain();
                            mes2.what = 1;
                            mes2.arg1 = count;
                            SubThreadHandler.sendMessage(mes2);
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                SubThreadHandler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message msg) {
                        if (msg.what == 1) {
                            Message mes = Message.obtain();
                            mes.what = 2;
                            mes.arg1 = msg.arg1;
                            MainThreadHandler.sendMessage(mes);
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return true;
                    }
                });
                Looper.loop();
            }
        }).start();

    }
}