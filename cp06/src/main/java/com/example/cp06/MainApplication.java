package com.example.cp06;

import android.app.Application;

import androidx.room.Room;

import com.example.cp06.database.ShoppingDatabase;
import com.example.cp06.util.MMKVUtil;
import com.tencent.mmkv.MMKV;

public class MainApplication extends Application {
    private static MainApplication mApp;


    private ShoppingDatabase shoppingDatabase;

    public static int cartCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

        shoppingDatabase = Room.databaseBuilder(mApp, ShoppingDatabase.class, "CartInfo")
                .addMigrations()
                .allowMainThreadQueries()
                .build();
        MMKV.initialize(this);
        MMKVUtil.initialize("cp06");
    }

    public static MainApplication getInstance() {
        return mApp;
    }

    public ShoppingDatabase getShoppingDatabase() {
        return shoppingDatabase;
    }
}
