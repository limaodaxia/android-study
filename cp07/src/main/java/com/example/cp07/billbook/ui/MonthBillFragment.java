package com.example.cp07.billbook.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cp07.R;
import com.example.cp07.billbook.adapter.BillListAdapter;
import com.example.cp07.billbook.entity.Bill;

import java.util.HashMap;
import java.util.List;

public class MonthBillFragment extends Fragment {


    private String mMonth;

    // 创建一个新的Fragment实例并传递月份参数，这里不能直接设置因为这是静态方法
    public static MonthBillFragment newInstance(String mMonth) {
        Bundle args = new Bundle();
        args.putString("month", mMonth);
        MonthBillFragment fragment = new MonthBillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取月份并设置
        mMonth = getArguments().getString("month");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // 初始化视图
        View view = inflater.inflate(R.layout.fragment_month_bill, container, false);
        // 找到当前activity
        BillPagerActivity activity =(BillPagerActivity)requireActivity();
        // 创建适配器并设置到ListView
        BillListAdapter adapter = new BillListAdapter(activity, activity.getBillsByMonth(mMonth));
        ListView listView = view.findViewById(R.id.lv_month_bill);
        listView.setAdapter(adapter);
        return view;
    }

}
