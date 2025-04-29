package com.example.cp06.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cp06.dao.CartInfoDao;
import com.example.cp06.dao.GoodsInfoDao;
import com.example.cp06.entity.CartInfo;
import com.example.cp06.entity.GoodsInfo;

@Database(version = 1, entities = {CartInfo.class, GoodsInfo.class}, exportSchema = false)
public abstract class ShoppingDatabase extends RoomDatabase {

    public abstract CartInfoDao getCartInfoDao();

    public abstract GoodsInfoDao getGoodsInfoDao();
}
