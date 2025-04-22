package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CalculatorActivity";

    private TextView tv_result; // 文本视图对象
    private String firstNum="";  // 第一个操作数
    private String secondNum=""; // 第二个操作数
    private String operator=""; // 运算符
    private String result=""; // 当前的计算结果
    private String showText=""; // 显示的文本内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        // 从布局文件中获取名叫tv_result的文本视图
        tv_result = findViewById(R.id.tv_result);
        // 下面给每个按钮控件都注册了点击监听器
        findViewById(R.id.btn_cancel).setOnClickListener(this); // 取消按钮
        findViewById(R.id.btn_divide).setOnClickListener(this); // 倒数按钮
        findViewById(R.id.btn_multiply).setOnClickListener(this); // 乘法按钮
        findViewById(R.id.btn_clear).setOnClickListener(this); // 清除按钮
        findViewById(R.id.btn_seven).setOnClickListener(this); // 按钮7
        findViewById(R.id.btn_eight).setOnClickListener(this); // 按钮8
        findViewById(R.id.btn_nine).setOnClickListener(this); // 按钮9
        findViewById(R.id.btn_four).setOnClickListener(this); // 按钮4
        findViewById(R.id.btn_plus).setOnClickListener(this); // 加法按钮
        findViewById(R.id.btn_five).setOnClickListener(this); // 按钮5
        findViewById(R.id.btn_six).setOnClickListener(this); // 按钮6
        findViewById(R.id.btn_minus).setOnClickListener(this); // 减法按钮
        findViewById(R.id.btn_one).setOnClickListener(this); // 按钮1
        findViewById(R.id.btn_two).setOnClickListener(this); // 按钮2
        findViewById(R.id.btn_three).setOnClickListener(this); // 按钮3
        findViewById(R.id.ib_sqrt).setOnClickListener(this); // 根号按钮，这里是个image button
        findViewById(R.id.btn_reciprocal).setOnClickListener(this); // 倒数按钮
        findViewById(R.id.btn_zero).setOnClickListener(this); // 按钮0
        findViewById(R.id.btn_dot).setOnClickListener(this); // 小数点按钮
        findViewById(R.id.btn_equal).setOnClickListener(this); // 等于按钮

    }

    private void showInfo(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public boolean verify(View view){
        // 针对每个案件，进行合法性检测
        if(view.getId()==R.id.btn_cancel){
            // 无运算符，则表示逐位取消第一个数字，如果已经为0则不做任何处理
            if (operator.isEmpty() && (firstNum.isEmpty()||firstNum.equals("0"))){
                showInfo("没有可取消的数字");
                return false;
            }
            // 有运算符，表示逐位取消第二个数字，置为0。
            if (!operator.isEmpty() && secondNum.isEmpty()){
                showInfo("没有可取消的数字");
                return false;
            }

        }else if(view.getId() == R.id.btn_plus ||
                 view.getId() == R.id.btn_multiply ||
                 view.getId() == R.id.btn_minus ||
                view.getId() == R.id.btn_divide) {
            if (firstNum.isEmpty()) { // 缺少第一个操作数
                showInfo("请输入数字");
                return false;
            }
            if (!operator.isEmpty()) { // 已有运算符
                showInfo("请输入数字");
                return false;
            }
        }else if(view.getId() == R.id.ib_sqrt){
            if (firstNum.isEmpty()) { // 缺少底数
                showInfo("请输入数字");
                return false;
            }
            if (Double.parseDouble(firstNum) < 0) { // 不能对负数开平方
                showInfo("开根号的数值不能小于零");
                return false;
            }
        }else if(view.getId() == R.id.btn_reciprocal){
            if (firstNum.isEmpty()) { // 缺少底数
                showInfo("请输入数字");
                return false;
            }
            if (firstNum.equals("0")) { // 不能对0取倒数
                showInfo("不能对0取倒数");
                return false;
            }
        }else if(view.getId() == R.id.btn_dot){
            if (operator.isEmpty() && firstNum.contains(".")) { // 无运算符，则检查第一个操作数是否已有小数点
                showInfo("一个数字不能有两个小数点");
                return false;
            }
            if (!operator.isEmpty() && secondNum.contains(".")) { // 有运算符，则检查第二个操作数是否已有小数点
                showInfo("一个数字不能有两个小数点");
                return false;
            }
        }else if(view.getId() == R.id.btn_equal){
            if (firstNum.isEmpty()){
                showInfo("请先输入数字");
                return false;
            }
            if (secondNum.isEmpty()) {
                showInfo("请先输入数字");
                return false;
            }
            if (operator.isEmpty()){
                showInfo("请先输入运算符");
                return false;
            }
            if (operator.equals("÷") && secondNum.equals("0")){
                showInfo("除数不能为0");
                return false;
            }
        }
        
        return true;
    }

    @Override
    public void onClick(View view) {
        if(!verify(view)){ // 检测当前输入不合法，直接返回。
            return;
        }
        int vid = view.getId();
        String inputText;
        if (vid == R.id.ib_sqrt) { // 如果是开根号按钮
            inputText = "√";
        } else { // 除了开根号之外的其他按钮
            inputText = ((TextView) view).getText().toString();
        }
        Log.d(TAG, "inputText=" + inputText);
        if ( vid == R.id.btn_cancel){
            if (operator.isEmpty()) { // 无运算符，则表示逐位取消第一个操作数
                if (firstNum.length() == 1) {
                    firstNum = "0";
                } else if (firstNum.length() > 1) {
                    firstNum = firstNum.substring(0, firstNum.length() - 1);
                }
                // 当前只输入了firstNum，所以把firstNum写入即可
                refreshText(firstNum);
            } else { // 有运算符，则表示逐位取消第二个操作数
                if (secondNum.length() == 1) {
                    secondNum = "";
                } else if (secondNum.length() > 1) {
                    secondNum = secondNum.substring(0, secondNum.length() - 1);
                }
                // 这里的输入内容退格一个即可
                refreshText(showText.substring(0, showText.length() - 1));
            }
        } else if(vid == R.id.btn_divide||
                vid == R.id.btn_multiply||
                vid == R.id.btn_plus||
                vid == R.id.btn_minus){
            operator = inputText; // 运算符
            refreshText(showText + operator);
        } else if(vid == R.id.btn_clear) {
            clear();
        } else if (vid == R.id.btn_equal) {
            double calculate_result = calculateFour(); // 加减乘除四则运算
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "=" + result);
        } else if(vid == R.id.ib_sqrt){
            double calculate_result = Math.sqrt(Double.parseDouble(firstNum)); // 开平方运算
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "√=" + result);
        } else if(vid == R.id.btn_dot){
            double calculate_result = 1.0 / Double.parseDouble(firstNum); // 求倒数运算
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "/=" + result);
        } else{ //剩下的这些情况是数字和小数点
            if (!result.isEmpty() && operator.isEmpty()) { // 上次的运算结果已经出来了，再次运算是重新输入，清空
                clear();
            }
            if (operator.isEmpty()) { // 无运算符，则继续拼接第一个操作数
                firstNum = firstNum+inputText;
            } else { // 有运算符，则继续拼接第二个操作数
                secondNum = secondNum + inputText;
            }
            if (showText.equals("0") && !inputText.equals(".")) { // 整数不需要前面的0
                refreshText(inputText);
            } else {
                refreshText(showText + inputText);
            }
        }
    }

    // 清空并初始化
    private void clear() {
        refreshOperate("");
        refreshText("");
    }

    // 刷新文本显示
    private void refreshText(String text) {
        showText = text;
        tv_result.setText(showText);
    }

    // 刷新运算结果,当前只有第一个结果了，操作符合第二个运算数都没了
    private void refreshOperate(String new_result) {
        result = new_result;
        firstNum = result;
        secondNum = "";
        operator = "";
    }

    // 加减乘除四则运算，返回计算结果
    private double calculateFour() {
        double calculate_result = 0;
        switch (operator) {
            case "+":  // 当前是相加运算
                calculate_result = Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
                break;
            case "-":  // 当前是相减运算
                calculate_result = Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
                break;
            case "×":  // 当前是相乘运算
                calculate_result = Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
                break;
            case "÷":  // 当前是相除运算
                calculate_result = Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
                break;
        }
        Log.d(TAG, "calculate_result=" + calculate_result); // 把运算结果打印到日志中
        return calculate_result;
    }
}