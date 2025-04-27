package com.example.cp06;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cp06.dao.GoodsInfoDao;
import com.example.cp06.databinding.ActivityShoppingCartBinding;
import com.example.cp06.entity.CartInfo;
import com.example.cp06.entity.GoodsInfo;
import com.example.cp06.util.FileUtil;
import com.example.cp06.util.SharedUtil;

import java.util.LinkedList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private static final String TAG = "ShoppingCartActivity";
    private ActivityShoppingCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.header.tvTitle.setText("购物车");

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
        // showGoods();
    }

    private String isFirst ="true";
    private void downloadGoods(){
        // 获取共享参数是否为首次打开
        isFirst = SharedUtil.getInstance(this).readString("first", "true");
        if ("true".equals(isFirst)){
            // 模拟下载商品
            List<GoodsInfo> goodsInfoList = GoodsInfo.getDefaultList();
            GoodsInfoDao goodsInfoDao = MainApplication.getInstance().getGoodsDatabase().getGoodsInfoDao();
            // 获取外部存储的路径
            String externalPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()+"/";
            Log.d(TAG, "首次进入，开始下载，下载路径为" + externalPath);
            // 保存到数据库
            goodsInfoList.forEach(goodsInfo -> {
                // 先要插入数据库，自动生成id
                long id = goodsInfoDao.insertGoodsInfo(goodsInfo);
                goodsInfo.setId(id); // 保存id
                // 把图片保存到本地
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), goodsInfo.getPicId());
                String pic_path = externalPath + id + ".jpg";
                FileUtil.saveImage(pic_path, bitmap);
                // 保存图片路径
                goodsInfo.setPicPath(pic_path);
                // 数据库更新
                goodsInfoDao.updateGoodsInfo(goodsInfo);
            });
        }

        SharedUtil.getInstance(this).writeString("first", "false");
    }

    private List<CartInfo> cartInfoList = new LinkedList<>();
    private void showGoods() {
        if (cartInfoList.size() == 0){
            cartInfoList.clear();
        }
        //binding.goodsLayout.addView();
    }
}