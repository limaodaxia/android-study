package com.example.cp06;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cp06.dao.CartInfoDao;
import com.example.cp06.dao.GoodsInfoDao;
import com.example.cp06.databinding.ActivityShoppingCartBinding;
import com.example.cp06.entity.CartInfo;
import com.example.cp06.entity.GoodsInfo;
import com.example.cp06.util.FileUtil;
import com.example.cp06.util.SharedUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private static final String TAG = "ShoppingCartActivity";
    private CartInfoDao cartInfoDao;
    private GoodsInfoDao goodsInfoDao;

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
        binding.header.ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingCartActivity.this, ShoppingChannelActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        binding.btnShoppingChannel.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingCartActivity.this, ShoppingChannelActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        binding.btnPay.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("确认支付")
                    .setMessage("不好意思，您未开通支付权限，请联系客服")
                    .setPositiveButton("确定", null)
                    .create().show();
        });
        binding.btnClear.setOnClickListener(v -> {
            // 弹出对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("确认清空")
            .setMessage("确定清空吗？")
            .setPositiveButton("确定", (dialog, which) -> {
                // 清空购物车
                cartInfoDao.deleteAllCartInfo();
                MainApplication.cartCount = 0;
                showCount();
                Toast.makeText(this, "已全部清空", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("否",null)
            .create().show();
        });

        // 获取持久化对象
        cartInfoDao = MainApplication.getInstance().getCartDatabase().getCartInfoDao();
        goodsInfoDao = MainApplication.getInstance().getGoodsDatabase().getGoodsInfoDao();
        MainApplication.cartCount = cartInfoDao.getAllCartInfo().size();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCount();
        downloadGoods();
        showCart();
    }

    // 展示总量，并根据总量显示对应的视图
    private void showCount() {
        binding.header.tvCartCount.setText(String.valueOf(MainApplication.cartCount));
        // 根据当前购物车数量决定展示哪一个视图
        if (MainApplication.cartCount == 0){
            binding.llCart.setVisibility(View.GONE);
            binding.llNoItem.setVisibility(View.VISIBLE);
            binding.llCartItems.removeAllViews();
            goodsIdMap.clear();
        }else{
            binding.llCart.setVisibility(View.VISIBLE);
            binding.llNoItem.setVisibility(View.GONE);
        }
    }

    private String isFirst ="true";
    private void downloadGoods(){
        // 获取共享参数是否为首次打开
        isFirst = SharedUtil.getInstance(this).readString("first", "true");
        if ("true".equals(isFirst)){
            // 模拟下载商品
            List<GoodsInfo> goodsInfoList = GoodsInfo.getDefaultList();
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

    /**
     * key: id
     * value: GoodsInfo
     * 存放id和GoodsInfo的映射关系，减少数据库的查询
     */
    private HashMap<Long, GoodsInfo> goodsIdMap = new HashMap<>();
    /**
     * 暂存从数据库查询到的购物车数量
     */
    private List<CartInfo> cartInfoList = new LinkedList<>();
    private void showCart() {
        binding.llCartItems.removeAllViews(); // 注意购物车清空的对象
        // 查询当前购买的商品
        cartInfoList = cartInfoDao.getAllCartInfo();
        // 如果为空直接返回
        if (cartInfoList==null|| cartInfoList.isEmpty()){
            return ;
        }
        // 显示商品信息，这里不用判断商品是否为空
        cartInfoList.forEach(cartInfo -> {
            final GoodsInfo goodsInfoById = goodsInfoDao.getGoodsInfoById(cartInfo.getGoodsId());
            // 加载视图
            View view = LayoutInflater.from(this).inflate(R.layout.item_cart, binding.llCartItems, false);

            // 获取控件
            ImageView iv_pic = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_number = view.findViewById(R.id.tv_number);
            TextView tv_price = view.findViewById(R.id.tv_price);
            TextView tv_total_price = view.findViewById(R.id.tv_total_price);
            TextView tv_desc = view.findViewById(R.id.tv_desc);
            // 填充数据
            iv_pic.setImageURI(Uri.parse(goodsInfoById.getPicPath()));
            tv_name.setText(goodsInfoById.getName());
            tv_desc.setText(goodsInfoById.getDesc());
            tv_number.setText(String.valueOf(cartInfo.getCount())); // 这里不小心直接传入了int，方法识别成了id
            tv_price.setText(String.valueOf(goodsInfoById.getPrice()));
            tv_total_price.setText(String.valueOf(goodsInfoById.getPrice() * cartInfo.getCount()));

            // 设置子项的监听
            view.setOnClickListener(v -> {
                Intent intent = new Intent(this, GoodsDetailActivity.class);
                intent.putExtra("goodsId", cartInfo.getGoodsId());
                startActivity(intent);
            });

            view.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("是否从购物车中删除"+goodsInfoById.getName()+"?");
                builder.setPositiveButton("是",(dialog, which)->{
                    binding.llCartItems.removeView(v);
                    deleteCart(cartInfo);
                });
                builder.setNegativeButton("否", null);
                builder.create().show();
                return true;
            });
            // 添加映射关系
            goodsIdMap.put(cartInfo.getGoodsId(), goodsInfoById);

            // 添加视图
            binding.llCartItems.addView(view);
        });

        // 刷新价格
        refreshTotalPrice();
    }

    private void deleteCart(CartInfo cartInfo) {
        // 数据库里删除该商品，是否根据id删除?
        cartInfoDao.deleteCartInfo(cartInfo);
        // 从购物车list中删除对应info，是否根据id删除
        cartInfoList.remove(cartInfo);

        // 给出提示
        Toast.makeText(this, "已从购物车删除" + goodsIdMap.get(cartInfo.getGoodsId()).getName(), Toast.LENGTH_SHORT).show();

        // 从映射关系里删除当前商品
        goodsIdMap.remove(cartInfo.getGoodsId());
        // 购物总数减少
        MainApplication.cartCount --;
        showCount();
        // 刷新价格
        refreshTotalPrice();

    }

    // 刷新总价
    private void refreshTotalPrice(){
        double sum = 0;
        for (CartInfo cartInfo : cartInfoList) {
            double price = goodsIdMap.get(cartInfo.getGoodsId()).getPrice();
            int count = cartInfo.getCount();
            sum += price * count;
        }
        binding.tvTotalPrice.setText(String.valueOf(sum));
    }
}