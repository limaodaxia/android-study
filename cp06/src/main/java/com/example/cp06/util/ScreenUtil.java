package com.example.cp06.util;

import android.content.Context;

public class ScreenUtil {
    // 获得屏幕的宽度
    public static int getScreenWidth(Context ctx) {
        return ctx.getResources().getDisplayMetrics().widthPixels;
    }

    // 获得屏幕的高度
    public static int getScreenHeight(Context ctx) {
        return ctx.getResources().getDisplayMetrics().heightPixels;
    }
}
