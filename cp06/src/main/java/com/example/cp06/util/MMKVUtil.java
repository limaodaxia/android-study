package com.example.cp06.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cp06.MainApplication;
import com.tencent.mmkv.MMKV;

public class MMKVUtil {

    private static MMKV mmkv;

    public static void initialize(String mmapID){
        if(mmkv == null){
            mmkv = MMKV.mmkvWithID(mmapID);
        }
    }

    public static void writeString(String key, String value){
        mmkv.encode(key, value);
    }

    public static String readString(String key, String defaultValue){
        return mmkv.decodeString(key, defaultValue);
    }

}
