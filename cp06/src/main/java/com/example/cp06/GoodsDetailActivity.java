package com.example.cp06;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cp06.dao.CartInfoDao;
import com.example.cp06.dao.GoodsInfoDao;
import com.example.cp06.databinding.ActivityGoodsDetailBinding;
import com.example.cp06.entity.CartInfo;
import com.example.cp06.entity.GoodsInfo;

import java.time.LocalDateTime;

public class GoodsDetailActivity extends AppCompatActivity {

    private ActivityGoodsDetailBinding binding;

    private GoodsInfoDao goodsInfoDao;
    private CartInfoDao cartInfoDao;

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

        goodsInfoDao = MainApplication.getInstance().getGoodsDatabase().getGoodsInfoDao();
        cartInfoDao = MainApplication.getInstance().getCartDatabase().getCartInfoDao();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGoodsDetail(); //zh展示商品详情
        binding.header.tvCartCount.setText(String.valueOf(MainApplication.cartCount));
    }

    private void loadGoodsDetail(){
        int goodsId = getIntent().getIntExtra("goodsId", -1);
        if (goodsId > -1){
            GoodsInfo goodsInfoById = goodsInfoDao.getGoodsInfoById(goodsId);
            binding.header.tvTitle.setText(goodsInfoById.getName());
            binding.ivPic.setImageURI(Uri.parse(goodsInfoById.getPicPath()));
            binding.tvDesc.setText(goodsInfoById.getDesc());
            binding.tvPrice.setText(String.valueOf(goodsInfoById.getPrice()));
            binding.btnAdd.setOnClickListener(v->{
                addCart(goodsInfoById);
            });
        }
    }

    private void addCart(GoodsInfo goodsInfo){
        // 先判断购物车是否有该商品，有则数量加1，没有则添加新的商品
        CartInfo existedCartInfo = cartInfoDao.getCartInfoByGoodsId(goodsInfo.getId());
        if (existedCartInfo == null){
            CartInfo newCartInfo = new CartInfo(goodsInfo.getId(), 1, LocalDateTime.now().toString());
            cartInfoDao.insertCartInfo(newCartInfo);
            MainApplication.cartCount++;
        }else{
            existedCartInfo.setCount(existedCartInfo.getCount() + 1);
            cartInfoDao.updateCartInfo(existedCartInfo);
        }
        Toast.makeText(this, "成功添加一台 "+goodsInfo.getName(), Toast.LENGTH_SHORT).show();
        binding.header.tvCartCount.setText(String.valueOf(MainApplication.cartCount));
    }
}