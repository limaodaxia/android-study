package com.example.cp07.billbook.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.LocalDateTime;

@Entity(tableName = "bills")
public class Bill {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private LocalDateTime createTime;
    private double amount;
    private String remark;
    private int type; //BillType的两周类型，收入 INCOME 和 支出 EXPENSE

    public Bill(LocalDateTime createTime, double amount, String remark, int type) {
        this.createTime = createTime;
        this.amount = amount;
        this.remark = remark;
        this.type = type;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
