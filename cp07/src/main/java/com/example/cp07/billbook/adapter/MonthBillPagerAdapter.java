package com.example.cp07.billbook.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cp07.billbook.ui.MonthBillFragment;

import java.util.List;

public class MonthBillPagerAdapter extends FragmentPagerAdapter {

    // 总共的月份数
    private final List<Integer> months;

    //构造函数传入要显示的月份
    public MonthBillPagerAdapter(@NonNull FragmentManager fm,
                                 int behavior) {
        super(fm, behavior);
        this.months = List.of(1,2,3,4,5,6,7,8,9,10,11,12);
    }

    // 实例化Fragment，传入当前月份
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MonthBillFragment.newInstance(months.get(position));
    }

    // 给每个页面加入标题
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        return months.get(position)+"月份";
    }

    // 返回要显示的月份数量，即总共的页面数
    @Override
    public int getCount() {
        return months.size();
    }
}
