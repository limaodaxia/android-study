package com.example.cp06.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class CartInfo {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long goodsId;

    private String updateTime;

    private int count;
}
