package com.example.cp06.learningresolver;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cp06.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBTestActivity extends AppCompatActivity {

    UserDBHelper userDBHelper;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_db_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userDBHelper = UserDBHelper.getInstance(this,1);
        userDBHelper.openWriteLink();

        textView = findViewById(R.id.result_view);

        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<UserInfo> userInfos = new ArrayList<>();
                UserInfo info = new UserInfo();
                info.name = "lxl";
                info.age = 18;
                info.married = false;
                info.height = 179;
                info.weight = 70;
                info.phone="13813286925";
                info.password="123456";
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                info.update_time = sdf.format(now);
                userInfos.add(info);
                userDBHelper.insert(userInfos);
                Toast.makeText(DBTestActivity.this,"已添加"+info.name+"的用户信息", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDBHelper.delete("name='lxl'");
                Toast.makeText(DBTestActivity.this,"删除lxl的用户信息", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        findViewById(R.id.query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<UserInfo> userInfos = userDBHelper.query("name='lxl'");
                StringBuilder result = new StringBuilder();
                for (UserInfo userInfo : userInfos) {
                    result.append(userInfo.toString()).append("\n");
                }
                textView.setText(result);
            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo info = new UserInfo();
                info.name = "lxl";
                info.age = 18;
                info.married = false;
                info.height = 179;
                info.weight = 70;
                info.phone="13813286925";
                info.password="123456";
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                info.update_time = sdf.format(now);
                userDBHelper.update(info, "name='lxl'");
                Toast.makeText(DBTestActivity.this,"已修改"+info.name+"的用户信息", Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDBHelper.close();
        userDBHelper = null;
    }
}