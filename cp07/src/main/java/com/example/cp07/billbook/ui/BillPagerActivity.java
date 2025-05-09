package com.example.cp07.billbook.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.cp07.R;
import com.example.cp07.billbook.adapter.MonthBillPagerAdapter;
import com.example.cp07.billbook.entity.Bill;
import com.example.cp07.billbook.entity.BillType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BillPagerActivity extends AppCompatActivity {

    // 账单信息
    private HashMap<String, List<Bill>> billMap;

    // 根据月份获取账单
    public List<Bill> getBillsByMonth(String month) {
        return billMap.get(month);
    }

    // 添加账单，根据账单的月份修改相应的value
    public void addBill(Bill bill){
        String month = bill.getDatetime().substring(5, 7);
        List<Bill> list = billMap.get(month + "月份");
        if (list == null){
            list = new LinkedList<>();
        }
        list.add(bill);
        billMap.put(month + "月份", list);
    }

    // 初始化每个月的账单数据，key为月份，value为帐单列表，key不存在相当于当前月份没有账单
    public HashMap<String, List<Bill>> getDefaultBillMap(){
        HashMap<String, List<Bill>> map = new HashMap<>();
        for(int i = 1; i<=12; i++){
            if (i==3){
                List<Bill> list = new LinkedList<>();
                list.add(new Bill("2025-03-22", 100, "吃了个饭", BillType.EXPENSE));
                list.add(new Bill("2025-03-31", 4400, "收到工资", BillType.INCOME));
                map.put(i+"月份", list);
            }
        }
        return map;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置沉浸式主题
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_pager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 创建一个viewpager的适配器
        MonthBillPagerAdapter adapter = new MonthBillPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                getDefaultMonths());

        // 找到viewpager并设置适配器
        ViewPager viewPager = findViewById(R.id.vp_bill_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        // 设置标签的字体大小和颜色
        PagerTabStrip ptsBillPager = findViewById(R.id.pts_bill_pager);
        ptsBillPager.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        ptsBillPager.setTextColor(Color.BLACK);

        // 初始化账单数据
        billMap = getDefaultBillMap();

        // 找到标题栏的控件并设置标题
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("记账本");
        // 首页隐藏返回按钮
        findViewById(R.id.iv_back).setVisibility(View.GONE);
        // 跳转到账单创建页面
        TextView tvGoto = findViewById(R.id.tv_goto);
        tvGoto.setText("创建账单");
        tvGoto.setOnClickListener(view -> {
            startActivity(new Intent(this, BillCreateActivity.class));
        });
    }


    // 获取默认月份数据
    private List<String> getDefaultMonths(){
        List<String> list = new ArrayList<>(12);
        for(int i = 1; i<=12; i++){
            list.add(i+"月份");
        }
        return list;
    }
}