package com.example.cp04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactActivity1 extends AppCompatActivity {

    private TextView tv_result;

    // 这里传入的是Intent, 返回的也是intent
    // 有些时候，我们ActivityResultContracts可以创建不同的类，比如getContent()，这个传入的是Uri，返回的也是Uri
    // 会根据我们传入的uri，自动构造出Intent，简化了一定的操作
    private final ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
                if (result.getResultCode() == RESULT_OK && result.getData()!=null){
                    tv_result.setText(result.getData().getStringExtra("result"));
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_contact2_act).setOnClickListener(v -> {

            Intent intent = new Intent(this, ContactActivity2.class);
            intent.putExtra("info", "界面1发来的数据");
            // 旧式通信方法
            // startActivityForResult(intent, 0 );
            // 新式通信方法
            launcher.launch(intent);
        });
        tv_result = findViewById(R.id.tv_result);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==0 && resultCode == RESULT_OK){
//            tv_result.setText(data.getStringExtra("result"));
//        }
//    }
}