package com.example.cp07;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cp06.learningresolver.UserInfo;
import com.example.cp06.learningresolver.UserInfoContent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContentResolverActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_content_resolver);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.insert).setOnClickListener(v -> doInsert());
        findViewById(R.id.query).setOnClickListener(v -> doQuery());
        findViewById(R.id.delete).setOnClickListener(v -> doDelete());
        findViewById(R.id.update).setOnClickListener(v -> doUpdate());

        tvResult = findViewById(R.id.result_view);
    }

    public void doInsert(){
        ContentValues cv = new ContentValues();
        cv.put("name", "lxl");
        cv.put("age", 25);
        cv.put("height", 160);
        cv.put("weight", 55);
        cv.put("married", false);
        cv.put("phone", "13813286925");
        cv.put("password","123abc");
        cv.put("update_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        getContentResolver().insert(UserInfoContent.CONTENT_URI, cv);
    }

    public void doQuery(){
        StringBuilder content= new StringBuilder();
        String[] projection = new String[]{
                "rowid", "_id", "name", "age", "height", "weight", "married",
                "update_time", "phone, password"
        };
        Cursor cursor = getContentResolver().query(UserInfoContent.CONTENT_URI,
                projection, null, null,null);
        while (cursor.moveToNext()) {
            UserInfo info = new UserInfo();
            info.rowid = cursor.getLong(0);
            info.id = cursor.getInt(1);
            info.name = cursor.getString(2);
            info.age = cursor.getInt(3);
            info.height = cursor.getLong(4);
            info.weight = cursor.getFloat(5);
            info.married = cursor.getInt(6) != 0;
            info.update_time = cursor.getString(7);
            info.phone = cursor.getString(8);
            info.password = cursor.getString(9);
            content.append(info).append("\n");
        }
        showResult(content.toString());
    }

    public void doDelete(){
        getContentResolver().delete(UserInfoContent.CONTENT_URI,"name='lxl'",null);
    }

    public void doUpdate(){
        ContentValues cv = new ContentValues();
        cv.put("name", "xc");
        cv.put("age", 18);
        cv.put("height", 160);
        cv.put("weight", 55);
        cv.put("married", false);
        cv.put("update_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        getContentResolver().update(UserInfoContent.CONTENT_URI, cv, "name='lxl'", null);
    }

    public void showResult(String content){
        if (tvResult == null){
            tvResult = findViewById(R.id.result_view);
        }
        tvResult.setText(content);
    }

}