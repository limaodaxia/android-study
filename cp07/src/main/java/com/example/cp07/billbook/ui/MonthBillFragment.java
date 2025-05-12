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
import com.example.cp07.billbook.dao.BillDao;
import com.example.cp07.billbook.database.BillDatabase;
import com.example.cp07.billbook.entity.Bill;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

public class MonthBillFragment extends Fragment {


    private BillDao billDao;
    private int mMonth;

    // 创建一个新的Fragment实例并传递月份参数，这里不能直接设置因为这是静态方法
    public static MonthBillFragment newInstance( int month) {
        Bundle args = new Bundle();
        args.putInt("month", month);
        MonthBillFragment fragment = new MonthBillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取月份并设置
        assert getArguments() != null; // 确保参数不为空
        mMonth = getArguments().getInt("month");
        billDao = BillDatabase.getInstance().getBillDao();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // 初始化视图
        View view = inflater.inflate(R.layout.fragment_month_bill, container, false);
        // 找到当前activity
        BillPagerActivity activity =(BillPagerActivity)requireActivity();
        // 获取当前月份的账单
        List<Bill> billsByMonth = getBillsByMonth(mMonth);
        // 创建适配器并设置到ListView
        BillListAdapter adapter = new BillListAdapter(activity, billsByMonth);
        ListView listView = view.findViewById(R.id.lv_month_bill);
        listView.setAdapter(adapter);
        if (billsByMonth==null){
            view.findViewById(R.id.table_header).setVisibility(View.GONE);
        }
        return view;
    }

    // 把年月转换为LocalDateTime进行查询
    private List<Bill> getBillsByMonth(int month) {
        // 获取当前年
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        // 年月类
        YearMonth yearMonth = YearMonth.of(year, month);

        // 获取该月的第一天 00:00:00
        LocalDateTime startTime = yearMonth.atDay(1).atStartOfDay();

        // 获取该月的最后一天 23:59:59.999999999
        LocalDateTime endTime = yearMonth.atEndOfMonth()
                .atTime(LocalTime.MAX);

        return billDao.getBillsByTimeRange(startTime, endTime);
    }

}
