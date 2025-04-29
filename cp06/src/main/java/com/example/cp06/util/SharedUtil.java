package com.example.cp06.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences 工具类
 */
public class SharedUtil {
    private static SharedPreferences mShared;

    public static void initialize(Context context){
        if(mShared == null){
            mShared = context.getSharedPreferences("cp06", Context.MODE_PRIVATE);
        }
    }

    public void writeString(String key, String value){
        Editor edit = mShared.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public String readString(String key, String defaultValue){
        return mShared.getString(key, defaultValue);
    }

}
