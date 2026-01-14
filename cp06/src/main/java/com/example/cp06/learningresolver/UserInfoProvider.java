package com.example.cp06.learningresolver;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Objects;

public class UserInfoProvider extends ContentProvider {

    private UserDBHelper userDBHelper;
    // 这里设置为静态是为了节省内存，避免每个对象重复创建；避免多线程问题
    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final int USER_INFO = 1;
    // 一个Provider可能对应多张表，所以需要匹配器对uri进行映射
    // 下面的代码把
    static {
        uriMatcher.addURI(UserInfoContent.AUTHORITIES,"user", USER_INFO);
    }

    public UserInfoProvider() {
    }

    @Override
    public boolean onCreate() {
        userDBHelper = UserDBHelper.getInstance(getContext(), 1);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newUri = null;
        if (uriMatcher.match(uri) == USER_INFO){
            try(SQLiteDatabase mDB = userDBHelper.openWriteLink()){
                long rowId = mDB.insert(UserInfoContent.TABLE_NAME, null, values);
                if (rowId > 0){
                    newUri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI, rowId);
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(newUri, null);
                }
            }
        }
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // 这里虽然传入了 selection 和 selectionArgs 参数，但实际上对应了数据库的whereClause 和 whereArgs
        int count = 0;
        if (uriMatcher.match(uri) == USER_INFO){
            try(SQLiteDatabase mDB = userDBHelper.openWriteLink();){
                count = mDB.delete(UserInfoContent.TABLE_NAME, selection, selectionArgs);
            }
            // 可选：如果数据变了，通知监听者（这在 ContentProvider 中很常见）
            if (count > 0 && getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        if (uriMatcher.match(uri) == USER_INFO){
            try(SQLiteDatabase mDB = userDBHelper.openWriteLink()){
                count = mDB.update(UserInfoContent.TABLE_NAME, values, selection, selectionArgs);
            }
            // 可选：如果数据变了，通知监听者（这在 ContentProvider 中很常见）
            if (count > 0 && getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
        return count;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        if (uriMatcher.match(uri) == USER_INFO){
            SQLiteDatabase mDB = userDBHelper.openReadLink();
                cursor = mDB.query(UserInfoContent.TABLE_NAME,
                        projection, selection, selectionArgs,null,null, sortOrder);
                // 设置内容解析器的监听
                cursor.setNotificationUri(getContext().getContentResolver(), uri);


        }
        return cursor;
    }

}