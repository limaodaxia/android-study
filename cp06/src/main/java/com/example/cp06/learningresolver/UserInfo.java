package com.example.cp06.learningresolver;

public class UserInfo {
    public long rowid; // 行号
    public int id;
    public String name;
    public int age;
    public long height;
    public float weight;
    public String update_time;
    public String password;
    public String phone;
    public boolean married;

    @Override
    public String toString() {
        return "UserInfo{" +
                "rowid=" + rowid +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", update_time='" + update_time + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", married=" + married +
                '}';
    }
}
