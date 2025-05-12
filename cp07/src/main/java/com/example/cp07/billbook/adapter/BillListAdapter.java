package com.example.cp07.billbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cp07.R;
import com.example.cp07.billbook.entity.Bill;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class BillListAdapter extends BaseAdapter {

    private final List<Bill> bills;
    private final Context context;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        BillViewHolder holder;
        // 如果视图为空，实例化视图
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
            // 创建缓存类
            holder = new BillViewHolder();
            holder.tvDatetime = convertView.findViewById(R.id.tv_datetime);
            holder.tvType = convertView.findViewById(R.id.tv_type);
            holder.tvAmount = convertView.findViewById(R.id.tv_amount);
            convertView.setTag(holder);
        }else{
            holder = (BillViewHolder)convertView.getTag();
        }
        // 根据元素位置设置数据
        holder.tvDatetime.setText(bills.get(position).getCreateTime().format(dateFormatter));
        holder.tvType.setText(bills.get(position).getType()==1?"收入":"支出");
        holder.tvAmount.setText(String.valueOf(bills.get(position).getAmount()));

        return convertView;
    }

    // 用来缓存视图，不用每次都去findViewById减少时间消耗
    private static class BillViewHolder{
        TextView tvDatetime;
        TextView tvType;
        TextView tvAmount;
    }
}
