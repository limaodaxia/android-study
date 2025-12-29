package com.example.cp06;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class FilePathActivity extends AppCompatActivity {

    String installPath = "";
    String publicPath = "";
    String privatePath = "";
    TextView tvFilepath;
    String desc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_file_path);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 获取安装目录
        installPath = getFilesDir().getAbsolutePath();

        // 获取共有存储目录
        publicPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).toString();
        // 获取私有存储目录
        privatePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
        tvFilepath= findViewById(R.id.tv_file_path);
        desc = "app的安装目录 installPath："+ installPath +
                "\n\n系统的公共存储路径在"+publicPath +
                "\n\n当前的App的私有存储路径位于"+privatePath +
                "\n\n安卓手机 7.0之后默认禁止访问公共存储目录";

        tvFilepath.setText(desc);

        findViewById(R.id.btn_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // writeToPrivateFile("lxl will success", installPath+"test.txt");
                // writeToPrivateFile("lxl will success", publicPath+"/test.txt");
                if (checkPermission(FilePathActivity.this, "android.permission.READ_MEDIA_AUDIO"
                        , 1)){
                    writeToPrivateFile("lxl will success", publicPath+"/test.txt");
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    writeTxtToPublicDownload("test.txt", "lxl will success");
                }
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 ){
            boolean allGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED){
                    allGranted = false;
                    break;
                }
            }
            if (allGranted){
                writeToPrivateFile("lxl will success", publicPath+"/test.txt");
            }else{
                Toast.makeText(FilePathActivity.this,"你需要授予全部权限才能读写数据到内存卡",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void writeToPrivateFile(String content, String path){

        try(FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        desc = desc+"\n\n写入成功, content:" + content+", path: " + path;
        tvFilepath.setText(desc);
    }

    // 在 Android 13+ 写入公共目录的正确姿势
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void writeTxtToPublicDownload(String fileName, String content) {
        ContentValues values = new ContentValues();
        // 1. 设置文件名和类型
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, "text/plain");
        // 2. 设置存储路径（Android 10+ 专用）
        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        Uri externalUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = getContentResolver();

        // 3. 插入数据并获取 Uri
        Uri insertUri = resolver.insert(externalUri, values);

        if (insertUri != null) {
            try (OutputStream os = resolver.openOutputStream(insertUri)) {
                os.write(content.getBytes());
                Toast.makeText(this, "文件已成功保存到 Download 目录", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkPermission(Activity act, String permission, int requestCode){
        return checkPermission(act, new String[]{permission}, requestCode);
    }

    // 检查权限是否已经全部授权，全部授权返回true， 否则返回false
    private boolean checkPermission(Activity act, String[] permissions, int requestCode){
        boolean result = true;
        int check = PackageManager.PERMISSION_GRANTED;
        for (String permission : permissions){
            check = ContextCompat.checkSelfPermission(act, permission);
            if (check != PackageManager.PERMISSION_GRANTED){
                break;
            }
        }
        if(check != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(act, permissions,1);
            result = false;
        }
        return result;
    }
}