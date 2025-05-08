package com.example.cp07.billbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cp07.R;
import com.example.cp07.billbook.entity.Bill;

import java.util.LinkedList;
import java.util.List;

public class BillListAdapter extends BaseAdapter {

    private List<Bill> bills;
    private Context context;

    public BillListAdapter(Context context, List<Bill> bills) {
        this.context = context;
        if(bills == null){
            bills = new LinkedList<>();
        }
        this.bills = bills;
    }
    // 获取总数
    @Override
    public int getCount() {
        return bills.size();
    }

    // 获取指定位置的item
    @Override
    public Object getItem(int position) {
        return bills.get(position);
    }

    // 获取id，一般用不到
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 获取view，根据view来修改数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 如果为空，实例化视图
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
        }
        // 找到内容并设置数据
        TextView tvDatetime = convertView.findViewById(R.id.tv_datetime);
        tvDatetime.setText(bills.get(position).getDatetime());
        TextView tvType = convertView.findViewById(R.id.tv_type);
        tvType.setText(bills.get(position).getType()==1?"收入":"支出");
        TextView tvAmount = convertView.findViewById(R.id.tv_amount);
        tvAmount.setText(bills.get(position).getAmount()+"");

        return convertView;
    }
}
