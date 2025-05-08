package com.example.cp07.billbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cp07.R;

public class BillCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_create);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 隐藏跳转按钮
        findViewById(R.id.tv_goto).setVisibility(View.GONE);
        // 找到标题栏的控件，修改标题
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("创建账单");
        // 设置返回按钮监听
        findViewById(R.id.iv_back).setOnClickListener(view -> {
           finish();
        });
    }
}