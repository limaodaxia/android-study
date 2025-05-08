package com.example.cp07.testlistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cp07.R;

import java.util.List;

public class MyListAdapter<T> extends BaseAdapter {


    private final Context context;
    private final List<T> mList;

    public MyListAdapter(Context context, List<T> list) {
        this.context = context;
        this.mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv = convertView.findViewById(R.id.tv);
            viewHolder.et = convertView.findViewById(R.id.et);
            viewHolder.btn = convertView.findViewById(R.id.btn);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        T t = mList.get(position);
        viewHolder.tv.setText(position+"");
        viewHolder.tv.setOnClickListener(v->{
            Toast.makeText(context, "点击了第" + position + "个文本视图", Toast.LENGTH_SHORT).show();
        });
        viewHolder.et.setText(position+"");
        viewHolder.et.setOnClickListener(v->{
            Toast.makeText(context, "点击了第" + position + "个文本框", Toast.LENGTH_SHORT).show();
        });
        viewHolder.btn.setText(position+"");
        viewHolder.btn.setOnClickListener(v->{
            Toast.makeText(context, "点击了第" + position + "个按钮", Toast.LENGTH_SHORT).show();
        });
        return convertView;
    }

    private static final class ViewHolder {
        public TextView tv;
        public EditText et;
        public Button btn;
    }
}
