package com.example.cp06.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class CartInfo {

//    public CartInfo(){
//
//    }

    // @Ignore
    public CartInfo(long goodsId, int count, String updateTime) {
        this.goodsId = goodsId;
        this.count = count;
        this.updateTime = updateTime;
    }
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long goodsId;

    private String updateTime;

    private int count;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
