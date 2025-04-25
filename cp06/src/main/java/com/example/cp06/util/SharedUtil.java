package com.example.cp06.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences 工具类
 */
public class SharedUtil {
    private final SharedPreferences mShared;
    private static SharedUtil sharedUtil;

    private SharedUtil(Context context){
        mShared = context.getSharedPreferences("cp06", Context.MODE_PRIVATE);
    }

    public static SharedUtil getInstance(Context context){
        if(sharedUtil == null){
            sharedUtil = new SharedUtil(context);
        }
        return sharedUtil;
    }

    public void writeString(String key, String value){
        Editor edit = mShared.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public String readString(String key, String defaultValue){
        return mShared.getString(key, defaultValue);
    }

    public void writeInt(String key, int value){
        Editor edit = mShared.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public int readInt(String key, int defaultValue){
        return mShared.getInt(key, defaultValue);
    }
}
