package com.example.cp06;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.cp06.database.GoodsDatabase;

public class MainApplication extends Application {
    private static MainApplication mApp;

    private GoodsDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mDatabase = Room.databaseBuilder(mApp, GoodsDatabase.class, "GoodsInfo")
                .addMigrations()
                .allowMainThreadQueries()
                .build();
    }

    public static MainApplication getInstance() {
        return mApp;
    }

    public GoodsDatabase getGoodsDatabase() {
        return mDatabase;
    }
}
