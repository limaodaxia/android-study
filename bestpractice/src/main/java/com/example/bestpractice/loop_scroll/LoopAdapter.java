package com.example.bestpractice.loop_scroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestpractice.R;

import java.util.List;

public class LoopAdapter extends RecyclerView.Adapter<LoopAdapter.LoopViewHolder> {

    private Context context;
    private List<String> dataList;

    LoopAdapter(Context context, List<String> data){
        this.context = context;
        this.dataList = data;
    }

    public static class LoopViewHolder extends RecyclerView.ViewHolder {

        private final TextView itemView;

        public LoopViewHolder(@NonNull View view) {
            super(view);
            itemView = (TextView) view.findViewById(R.id.textItem);
        }

    }

    @NonNull
    @Override
    public LoopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_view, parent, false);
        return new LoopViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull LoopViewHolder holder, int position) {
        // 对 position 取模，映射到实际数据的位置
        int actualPosition = position % dataList.size();
        holder.itemView.setText(dataList.get(actualPosition));
    }


    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
