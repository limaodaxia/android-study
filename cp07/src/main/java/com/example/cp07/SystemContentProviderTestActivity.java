package com.example.cp07;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SystemContentProviderTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_system_content_provider_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initTestView();
    }

    private TextView tvDisplay;

    private void initTestView() {
        tvDisplay = findViewById(R.id.tv_display);

        findViewById(R.id.btn_1).setOnClickListener(v -> onBtn1Click());
        findViewById(R.id.btn_2).setOnClickListener(v -> onBtn2Click());
        findViewById(R.id.btn_3).setOnClickListener(v -> onBtn3Click());
        findViewById(R.id.btn_4).setOnClickListener(v -> onBtn4Click());
    }

    /*
    测试
     */
    private void onBtn1Click() {

    }

    private void onBtn2Click() {

    }

    private void onBtn3Click() {

    }

    private void onBtn4Click() {

    }

    private void showResult(String msg) {
        if (tvDisplay != null) {
            tvDisplay.append(msg + "\n");
        }
    }
}