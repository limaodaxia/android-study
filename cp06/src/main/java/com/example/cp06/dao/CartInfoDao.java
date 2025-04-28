package com.example.cp06.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cp06.entity.CartInfo;

import java.util.List;

@Dao
public interface CartInfoDao {

    @Query("select * from CartInfo")
    List<CartInfo> getAllCartInfo();

    @Query("select * from CartInfo where goodsId = :id")
    CartInfo getCartInfoByGoodsId(long id);

    @Insert
    long insertCartInfo(CartInfo cartInfo);

    @Update
    int updateCartInfo(CartInfo cartInfo);

    @Delete
    int deleteCartInfo(CartInfo cartInfo);
}
