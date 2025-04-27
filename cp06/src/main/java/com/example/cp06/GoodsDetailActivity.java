package com.example.cp06;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cp06.dao.GoodsInfoDao;
import com.example.cp06.databinding.ActivityGoodsDetailBinding;
import com.example.cp06.entity.GoodsInfo;

public class GoodsDetailActivity extends AppCompatActivity {

    private ActivityGoodsDetailBinding binding;

    private GoodsInfoDao goodsInfoDao = MainApplication.getInstance().getGoodsDatabase().getGoodsInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        binding = ActivityGoodsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.header.ivBack.setOnClickListener(v -> finish());
        binding.header.ivCart.setOnClickListener(v -> {
            Intent intent = new Intent(GoodsDetailActivity.this, ShoppingCartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGoodsDetail(); //zh展示商品详情
    }

    private void loadGoodsDetail(){
        int goodsId = getIntent().getIntExtra("goodsId", -1);
        if (goodsId > -1){
            GoodsInfo goodsInfoById = goodsInfoDao.getGoodsInfoById(goodsId);
            binding.header.tvTitle.setText(goodsInfoById.getName());
            binding.ivPic.setImageURI(Uri.parse(goodsInfoById.getPicPath()));
            binding.tvDesc.setText(goodsInfoById.getDesc());
            binding.tvPrice.setText(String.valueOf(goodsInfoById.getPrice()));
        }
    }
}