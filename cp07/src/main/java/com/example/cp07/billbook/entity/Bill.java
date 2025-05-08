package com.example.cp07.billbook.entity;

public class Bill {
    private String datetime;
    private double amount;
    private String remark;
    private int type; //BillType的两周类型，收入 INCOME 和 支出 EXPENSE

    public Bill(String datetime, double amount, String remark, int type) {
        this.datetime = datetime;
        this.amount = amount;
        this.remark = remark;
        this.type = type;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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

}
