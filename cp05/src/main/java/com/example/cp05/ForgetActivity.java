package com.example.cp05;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cp05.util.LoginUtils;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {

    public static Intent getForgetActivityIntent(Context context, String number){
        Intent intent = new Intent(context, ForgetActivity.class);
        intent.putExtra("phone_number", number);
        return intent;
    };

    private String phone_number = "";

    private String verify_code = ""; // 验证码

    private EditText et_password1;
    private EditText et_password2;

    private EditText et_verify_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化密码输入框
        et_password1 = findViewById(R.id.et_password1);
        et_password2 = findViewById(R.id.et_password2);
        et_verify_code = findViewById(R.id.et_verify_code);

        // 给按钮设置监听器
        findViewById(R.id.btn_verify_code).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);

        // 获取电话号码
        if (getIntent()!=null && getIntent().hasExtra("phone_number")){
            phone_number = getIntent().getStringExtra("phone_number");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_verify_code){
            if(!LoginUtils.verifyNumber(phone_number)){
                Toast.makeText(this, "手机号不合法", Toast.LENGTH_SHORT).show();
                return;
            }
            String tmp_code = LoginUtils.generateVerifyCode(6);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请记住验证码");
            builder.setMessage(String.format("手机号是 %s****%s, 本次验证码：%s, 请输入验证码",
                    phone_number.substring(0, 3) ,
                    phone_number.substring(7),
                    tmp_code));
            builder.setPositiveButton("确定返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    verify_code = tmp_code;
                    dialog.dismiss();
                }
            });
            builder.show();
        }else if (v.getId() == R.id.btn_confirm){
            String password1 = et_password1.getText().toString();
            String password2 = et_password2.getText().toString();
            String now_verify_code = et_verify_code.getText().toString();
            if (password1.isEmpty()){
                Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                return;
            }else if (password2.isEmpty()){
                Toast.makeText(this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                return;
            } else if (!password1.equals(password2)) {
                Toast.makeText(this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                return;
            }
            if(verify_code.isEmpty()){
                Toast.makeText(this, "请获取验证码", Toast.LENGTH_SHORT).show();
                return;
            }else if(!verify_code.equals(now_verify_code)){
                Toast.makeText(this, "验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("new_password", password1);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}