package com.example.cp06;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cp06.dao.CartInfoDao;
import com.example.cp06.dao.GoodsInfoDao;
import com.example.cp06.databinding.ActivityShoppingChannelBinding;
import com.example.cp06.entity.CartInfo;
import com.example.cp06.entity.GoodsInfo;
import com.example.cp06.util.ScreenUtil;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ShoppingChannelActivity extends AppCompatActivity {

    private ActivityShoppingChannelBinding binding;
    private CartInfoDao cartInfoDao = MainApplication.getInstance().getCartDatabase().getCartInfoDao();
    private GoodsInfoDao goodsInfoDao = MainApplication.getInstance().getGoodsDatabase().getGoodsInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 数据绑定
        binding = ActivityShoppingChannelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 全面屏设置
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 页面初始化
        binding.header.tvTitle.setText("商品频道");
        binding.header.ivBack.setOnClickListener(v -> finish());
        binding.header.ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingChannelActivity.this, ShoppingCartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        showGoods();
        binding.header.tvCartCount.setText(String.valueOf(MainApplication.cartCount)); // 显示购物车商品数量
    }

    /**
     * 显示商品
     * 先把当前现实的商品给隐藏掉
     * 再从数据库里查询数据，按照数据填充页面
     */

    private List<GoodsInfo> goodsInfoList = new LinkedList<>();
    private void showGoods() {
        // 获取品目参数
        int screenWidth = ScreenUtil.getScreenWidth(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                screenWidth / 2, LinearLayout.LayoutParams.WRAP_CONTENT);
        binding.glGoods.removeAllViews();
        goodsInfoList = goodsInfoDao.getAllGoodsInfo();
        // 根据购物车里的元素添加视图
        for (GoodsInfo goodsInfo : goodsInfoList) {
            // 获取控件
            View view = LayoutInflater.from(this).inflate(R.layout.item_goods, binding.glGoods, false);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_price = view.findViewById(R.id.tv_price);
            Button btn_add = view.findViewById(R.id.btn_add);

            // 初始化控件
            iv_thumb.setImageURI(Uri.parse(goodsInfo.getPicPath()));
            tv_name.setText(goodsInfo.getName());
            tv_price.setText(String.valueOf(goodsInfo.getPrice()));
            iv_thumb.setOnClickListener(v -> {
                Intent intent = new Intent(ShoppingChannelActivity.this, GoodsDetailActivity.class);
                intent.putExtra("goods_id", goodsInfo.getId());
                startActivity(intent);
            });
            // 加入购物车
            btn_add.setOnClickListener(v -> {
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
            });

            // 向网格视图添加元素
            binding.glGoods.addView(view, params);
        }
    }
}