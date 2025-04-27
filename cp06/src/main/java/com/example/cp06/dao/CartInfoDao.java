package com.example.cp06.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.cp06.entity.CartInfo;

import java.util.List;

@Dao
public interface CartInfoDao {

    @Query("select * from CartInfo")
    List<CartInfo> getAllCartInfo();
}
