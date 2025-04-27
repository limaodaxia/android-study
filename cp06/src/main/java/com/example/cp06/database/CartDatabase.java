package com.example.cp06.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cp06.dao.CartInfoDao;
import com.example.cp06.entity.CartInfo;

@Database(version = 1, entities = {CartInfo.class}, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {

    public abstract CartInfoDao getCartInfoDao();
}
