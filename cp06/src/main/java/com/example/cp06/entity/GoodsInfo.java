package com.example.cp06.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cp06.R;

import java.util.ArrayList;
import java.util.List;

@Entity
public class GoodsInfo {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String desc;
    private double price;
    private String picPath;
    private int picId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    // 声明一个手机商品的名称数组
    private static String[] mNameArray = {
            "iPhone11", "Mate30", "小米10", "OPPO Reno3", "vivo X30", "荣耀30S"
    };
    // 声明一个手机商品的描述数组
    private static String[] mDescArray = {
            "Apple iPhone11 256GB 绿色 4G全网通手机",
            "华为 HUAWEI Mate30 8GB+256GB 丹霞橙 5G全网通 全面屏手机",
            "小米 MI10 8GB+128GB 钛银黑 5G手机 游戏拍照手机",
            "OPPO Reno3 8GB+128GB 蓝色星夜 双模5G 拍照游戏智能手机",
            "vivo X30 8GB+128GB 绯云 5G全网通 美颜拍照手机",
            "荣耀30S 8GB+128GB 蝶羽红 5G芯片 自拍全面屏手机"
    };
    // 声明一个手机商品的价格数组
    private static float[] mPriceArray = {6299, 4999, 3999, 2999, 2998, 2399};
    // 声明一个手机商品的大图数组
    private static int[] mPicIdArray = {
            R.drawable.iphone, R.drawable.huawei, R.drawable.xiaomi,
            R.drawable.oppo, R.drawable.vivo, R.drawable.rongyao
    };

    // 获取默认的手机信息列表
    public static List<GoodsInfo> getDefaultList() {
        List<GoodsInfo> list = new ArrayList<GoodsInfo>();
        for(int i = 0; i < mNameArray.length;i++){
            GoodsInfo info = new GoodsInfo();
            info.setName(mNameArray[i]);
            info.setDesc(mDescArray[i]);
            info.setPrice(mPriceArray[i]);
            info.setPicId(mPicIdArray[i]);
            list.add(info);
        }

        return list;
    }

}
