package com.example.cp05.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class LoginUtils {
    public static void hideSoftInputFromWindow(Activity act, View v){
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    public static String generateVerifyCode(int len) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < len; i++){
            int num = (int) (Math.random() * 10);
            sb.append(num);
        }
        return sb.toString();
    }

    public static boolean verifyNumber(String phone_number){
        if (phone_number.isEmpty() || phone_number.length()<11){
            return false;
        }
        return true;
    }

}
