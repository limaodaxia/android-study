package com.example.cp06.learningresolver;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserInfoContent implements BaseColumns {
    public static final String AUTHORITIES = "com.example.cp06.learningresolver.UserInfoProvider";

    // 内容提供器的外部表名
    public static final String TABLE_NAME = UserDBHelper.TABLE_NAME;

    public static final Uri CONTENT_URI  = Uri.parse("content://" +AUTHORITIES + "/user");

    public static final String USER_NAME = "name";
    public static final String USER_AGE = "age";
    public static final String USER_HEIGHT = "height";
    public static final String USER_WEIGHT = "weight";
}
