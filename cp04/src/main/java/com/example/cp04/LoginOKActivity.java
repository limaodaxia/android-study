package com.example.cp04;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LoginOKActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ok);
        findViewById(R.id.btn_login_ok).setOnClickListener(v -> {
            finish();
        });
    }
}