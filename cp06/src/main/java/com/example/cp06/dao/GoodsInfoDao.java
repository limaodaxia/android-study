package com.example.cp06.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cp06.entity.GoodsInfo;

import java.util.List;

@Dao
public interface GoodsInfoDao {
    @Query("select * from goodsinfo")
    List<GoodsInfo> getAllGoodsInfo();

    @Query("select * from goodsinfo where id = :id")
    GoodsInfo getGoodsInfoById(long id);

    @Insert
    long insertGoodsInfo(GoodsInfo goodsInfo);

    @Update(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    int updateGoodsInfo(GoodsInfo goodsInfo);
}
