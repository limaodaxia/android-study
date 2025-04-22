package com.example.cp05;


import static com.example.cp05.ForgetActivity.getForgetActivityIntent;

import com.example.cp05.util.LoginUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, CheckBox.OnCheckedChangeListener,
        View.OnFocusChangeListener{

    private static final String TAG = "LoginActivity";
    private Button btn_forget; // 忘记密码按钮
    private CheckBox ck_remember; // 记住密码复选框
    private RadioGroup rg_login_methods; // 登录方式单选框组
    private TextView tv_password; // 密码标签
    private EditText et_password; // 密码输入框
    private EditText et_phone; // 手机号输入框
    private String password = "123456"; // 密码初始设置为123456
    private String verify_code = ""; // 验证码

    private boolean isRemember = false; // 是否记住密码

    int login_method = 0; // 登录方式，0为密码登录，1为验证码登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置程序延伸到状态栏，并且给布局设置padding
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化控件
        // 登录按钮
        findViewById(R.id.btn_login).setOnClickListener(this);

        // 忘记密码按钮
        btn_forget = findViewById(R.id.btn_forget);
        btn_forget.setOnClickListener(this);

        // 登录方式单选框组
        rg_login_methods= findViewById(R.id.rg_login_methods);
        rg_login_methods.setOnCheckedChangeListener(this);

        // 密码标签一定要在rg_login_methods.check之前设置
        // rg.check会触发 onCheckedChanged方法，修改tv_password
        tv_password = findViewById(R.id.tv_password);

        // 密码输入框, 也要在登录之前初始化好
        et_password = findViewById(R.id.et_password);
        et_password.setOnFocusChangeListener(this);

        // 记住密码复选框, 也要在登录之前初始化好
        ck_remember = findViewById(R.id.ck_remember);
        ck_remember.setOnCheckedChangeListener(this);

        // 把登录方式设置为密码登录
        rg_login_methods.check(R.id.rb_password);

        // 手机号输入框
        et_phone = findViewById(R.id.et_phone);
        et_phone.addTextChangedListener(new PhoneTextWatcher());

        // 从SharedPreferences中读取用户信息

        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String phone = sp.getString("phone", "");
        password = sp.getString("password", "");
        et_phone.setText(phone);
        et_password.setText(password);



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        et_password.setText("");
    }

    class PhoneTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 11) {
                LoginUtils.hideSoftInputFromWindow(LoginActivity.this, et_phone);
                et_password.requestFocus();
            }
        }
    }


    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData()!=null) {
                    password = result.getData().getStringExtra("new_password");
                }

    });

    // View.OnClickListener
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login){ // 登录按钮
            String now_code = login_method == 0 ? password : verify_code;
            if(!et_password.getText().toString().isEmpty() && et_password.getText().toString().equals(now_code)){ // 密码正确
                if(isRemember){
                    SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("phone", et_phone.getText().toString());
                    editor.putString("password", password);
                    editor.commit();
                }
                String phone_number = et_phone.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("登录成功");
                builder.setMessage(String.format("您的手机号是 %s****%s, 恭喜您功过验证，点击确定按钮返回上一个页面",
                        phone_number.substring(0, 3) ,
                        phone_number.substring(7)));
                builder.setNegativeButton("我再看看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }else if (et_password.getText().toString().isEmpty()){
                String tip = login_method == 0? "请输入密码" : "请获取并验证码";
                Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
                et_password.requestFocus();
            }else{ // 密码错误
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("登录失败");
                String tip = login_method == 0? "用户名或密码错误" : "验证码错误";
                builder.setMessage(tip);
                builder.setPositiveButton("确定", null);
                builder.show();
            }

        } else if(v.getId() == R.id.btn_forget){
            if(btn_forget.getText().equals("忘记密码")){ // 忘记密码按钮
                // 发送带电话号码的意图
                String phone = et_phone.getText().toString();
                Intent forgetActivityIntent = getForgetActivityIntent(this, phone);
                launcher.launch(forgetActivityIntent);

            } else if (btn_forget.getText().equals("获取验证码")) {
                String phone_number = et_phone.getText().toString();
                if(!LoginUtils.verifyNumber(phone_number)){
                    Toast.makeText(this, "请输入11位的手机号", Toast.LENGTH_SHORT).show();
                    et_phone.requestFocus();
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
            }

        }
    }

    // RadioGroup.OnCheckedChangeListener
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // 监听登录方式的变化，修改密码标签和密码输入框的提示文字
        if(checkedId == R.id.rb_password){
            btn_forget.setText("忘记密码");
            tv_password.setText(R.string.password_label);
            et_password.setHint("请输入密码");
            login_method = 0;
            if(ck_remember.isChecked()){
                et_password.setText(password);
            }else{
                et_password.setText("");
            }
        }else if(checkedId == R.id.rb_verify_code){
            btn_forget.setText("获取验证码");
            tv_password.setText(R.string.verify_code_label);
            et_password.setHint("请输入验证码");
            login_method = 1;
            et_password.setText("");
        }
    }

    // CheckBox.OnCheckedChangeListener
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.ck_remember) {
            isRemember = isChecked;
        }
    }

    // View.OnFocusChangeListener
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v.getId()==R.id.et_password){ // 监听密码输入框的焦点变化，检验手机号的合法性
            if(hasFocus){
                String phone_number = et_phone.getText().toString();
                if(!LoginUtils.verifyNumber(phone_number)){
                    Toast.makeText(this, "请输入11位的手机号", Toast.LENGTH_SHORT).show();
                    et_phone.requestFocus();
                }
            }
        }
    }

}