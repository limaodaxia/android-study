package com.example.cp06;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.cp06.database.CartDatabase;
import com.example.cp06.database.GoodsDatabase;

public class MainApplication extends Application {
    private static MainApplication mApp;

    private GoodsDatabase goodsDatabase;

    private CartDatabase cartDatabase;

    public static int cartCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        goodsDatabase = Room.databaseBuilder(mApp, GoodsDatabase.class, "GoodsInfo")
                .addMigrations()
                .allowMainThreadQueries()
                .build();
        cartDatabase = Room.databaseBuilder(mApp, CartDatabase.class, "CartInfo")
                .addMigrations()
                .allowMainThreadQueries()
                .build();
    }

    public static MainApplication getInstance() {
        return mApp;
    }

    public GoodsDatabase getGoodsDatabase() {
        return goodsDatabase;
    }

    public CartDatabase getCartDatabase() {
        return cartDatabase;
    }
}
