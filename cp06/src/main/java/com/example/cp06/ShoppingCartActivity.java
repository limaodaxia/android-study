package com.example.cp06;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cp06.dao.GoodsInfoDao;
import com.example.cp06.databinding.ActivityShoppingCartBinding;
import com.example.cp06.entity.GoodsInfo;
import com.example.cp06.util.SharedUtil;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private ActivityShoppingCartBinding binding;

    private String isFirst ="true";
    private void downloadGoods(){

        String isFirst = SharedUtil.getInstance(this).readString("first", "true");
        if ("true".equals(isFirst)){
            // 下载商品
            List<GoodsInfo> goodsInfoList = GoodsInfo.getDefaultList();
            GoodsInfoDao goodsInfoDao = MainApplication.getInstance().getGoodsDatabase().getGoodsInfoDao();
            String path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()+"/";
            // 保存到数据库
            goodsInfoList.forEach(goodsInfo -> {
                long id = goodsInfoDao.insertGoodsInfo(goodsInfo);

                goodsInfo.setPicPath(path + goodsInfo.getName()+".jpg");

            });
        }

        SharedUtil.getInstance(this).writeString("first", "false");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnShoppingChannel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCartActivity.this, ShoppingChannelActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        downloadGoods();
    }
}