package com.example.cp06.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cp06.dao.GoodsInfoDao;
import com.example.cp06.entity.GoodsInfo;


@Database(version = 1 , entities = {GoodsInfo.class}, exportSchema = false)
public abstract class GoodsDatabase extends RoomDatabase {

    public abstract GoodsInfoDao getGoodsInfoDao();
}
