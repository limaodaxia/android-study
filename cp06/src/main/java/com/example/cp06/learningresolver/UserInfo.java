package com.example.cp06.learningresolver;

public class UserInfo {
    long rowid; // 行号
    int id;
    String name;
    int age;
    long height;
    float weight;
    String update_time;
    String password;
    String phone;
    boolean married;

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
