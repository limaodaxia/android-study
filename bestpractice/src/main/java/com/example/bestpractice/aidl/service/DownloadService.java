package com.example.bestpractice.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.bestpractice.IDownloadService;

public class DownloadService extends Service {

    private final String TAG ="MyDownloadService";

    private IDownloadService.Stub mBinder = new IDownloadService.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String download(String url) throws RemoteException {
            Log.d(TAG,"客户端请求来了，我要返回内容");
            return "2025-网页内容";
        }
    };

    public DownloadService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
       return mBinder;
    }
}