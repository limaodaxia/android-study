package com.example.cp07.billbook;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    // 全局静态存储 context 变量
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext(); // 保存 Application 的 Context
    }

    public static Context getAppContext() {
        return context;
    }
}
