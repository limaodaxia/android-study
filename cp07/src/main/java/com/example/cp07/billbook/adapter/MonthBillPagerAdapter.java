package com.example.cp07.billbook.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cp07.billbook.ui.MonthBillFragment;

import java.util.List;

public class MonthBillPagerAdapter extends FragmentPagerAdapter {

    // 总共的月份数
    private final List<String> months;

    //构造函数传入要显示的月份
    public MonthBillPagerAdapter(@NonNull FragmentManager fm,
                                 int behavior,
                                 List<String> months) {
        super(fm, behavior);
        this.months = months;
    }

    // 实例化Fragment，传入当前月份
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MonthBillFragment.newInstance(months.get(position));
    }

    // 返回要显示的月份数量，即总共的页面数
    @Override
    public int getCount() {
        return months.size();
    }
}
