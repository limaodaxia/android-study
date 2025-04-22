package com.example.bestpractice.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bestpractice.IDownloadService;
import androidx.appcompat.app.AppCompatActivity;


import com.example.bestpractice.R;

public class AIDLActivity extends AppCompatActivity {

    private String TAG = "AIDLActivity";

    private TextView tv_result;

    private IDownloadService iMyDownloadService;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyDownloadService = IDownloadService.Stub.asInterface(service);
            Log.d(TAG,service+"已连接"+name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "已断开连接");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aidl);

        tv_result = findViewById(R.id.tv_result);

        Button btn_download = findViewById(R.id.download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String res = iMyDownloadService.download("www.baidu.com");
                    tv_result.setText(res);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(AIDLActivity.this, "下载成功了", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent();
        intent.setAction("com.example.bestpractice.aidl.service.DownloadService");
        intent.setPackage("com.example.bestpractice");
        bindService(intent, conn, BIND_AUTO_CREATE);
    }
}