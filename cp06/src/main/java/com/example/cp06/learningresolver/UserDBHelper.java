package com.example.cp06.learningresolver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class UserDBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase mDB = null;
    private static final String DB_NAME = "user.db";
    private static final String TABLE_NAME = "user_info";

    private UserDBHelper(@Nullable Context context, int version) {
        // 这里已经传入了DB_NAME，后续获取数据库的时候就是根据这个名字获取
        super(context,  DB_NAME, null, version);
    }
    private static UserDBHelper mHelper;
    public static UserDBHelper  getInstance( Context context, int version){
        if (version > 0 && mHelper == null) {
            mHelper = new UserDBHelper(context, version);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_sql = "CREATE TABLE IF NOT EXISTS " +TABLE_NAME +"(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name VARCHAR NOT NULL," +
                "age INTEGER NOT NULL,"+
                "height INTEGER NOT NULL," +
                "weight FLOAT NOT NULL," +
                "married INTEGER NOT NULL," +
                "update_time VARCHAR NOT NULL," +
                "phone VARCHAR," +
                "password VARCHAR)";
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }


    // 打开读链接
    public SQLiteDatabase openReadLink(){
        if (mDB == null || !mDB.isOpen()){
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    //打开写链接
    public SQLiteDatabase openWriteLink(){
        if (mDB == null || !mDB.isOpen()){
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    public long insert(List<UserInfo> infoList){
        long result = -1;
        for (int i = 0; i < infoList.size(); i++){
            UserInfo info = infoList.get(i);
            ContentValues cv = new ContentValues();
            cv.put("name", info.name);
            cv.put("age", info.age);
            cv.put("height", info.height);
            cv.put("weight", info.weight);
            cv.put("married", info.married);
            cv.put("phone", info.phone);
            cv.put("password", info.password);
            cv.put("update_time", info.update_time);
            result = mDB.insert(TABLE_NAME, null, cv);
            if (result == -1){ // 插入失败返回-1，直接退出
                return result;
            }
        }
        return result;
    }

    public int delete(String condition){
        // 返回删除的数目
       return mDB.delete(TABLE_NAME, condition, null);
    }

    public int update(UserInfo info, String condition){
        ContentValues cv = new ContentValues();
        cv.put("name", info.name);
        cv.put("age", info.age);
        cv.put("height", info.height);
        cv.put("weight", info.weight);
        cv.put("married", info.married);
        cv.put("phone", info.phone);
        cv.put("password", info.password);
        cv.put("update_time", info.update_time);
        // 返回该语句影响的行数
        return mDB.update(TABLE_NAME, cv, condition, null);
    }

    public List<UserInfo> query(String condition){
        String sql = String.format("select rowid, _id, name, age, height, weight, married," +
                "update_time, phone, password from %s where %s", TABLE_NAME, condition);
        Cursor cursor = mDB.rawQuery(sql, null);
        List<UserInfo> result = new ArrayList<>();
        while (cursor.moveToNext()){
            UserInfo info = new UserInfo();
            info.rowid = cursor.getLong(0);
            info.id = cursor.getInt(1);
            info.name = cursor.getString(2);
            info.age = cursor.getInt(3);
            info.height = cursor.getLong(4);
            info.weight = cursor.getFloat(5);
            info.married = cursor.getInt(6) != 0;
            info.update_time = cursor.getString(7);
            info.phone = cursor.getString(8);
            info.password = cursor.getString(9);
            result.add(info);
        }
        cursor.close();
        return result;
    }


    @Override
    public synchronized void close() {
        super.close();
        mHelper = null;
    }
}
