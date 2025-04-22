package com.example.bestpractice.loop_scroll;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestpractice.databinding.ActivityLoopBinding;

import com.example.bestpractice.R;

import java.util.LinkedList;
import java.util.List;

public class LoopActivity extends AppCompatActivity {

    private RecyclerView list;
    List<String> strs;
    LoopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);
        list = findViewById(R.id.list);

        // 构造数据
        strs = new LinkedList<>();
        strs.add("苹果");
        strs.add("梨");
        strs.add("橘子");
        strs.add("西瓜");
        strs.add("香蕉");
        strs.add("葡萄");
        strs.add("芒果");
        strs.add("柠檬");
        strs.add("甘蔗");

        adapter = new LoopAdapter(this, strs);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        list.setLayoutManager(manager);
        list.setAdapter(adapter);


//        // 添加滚动监听
//        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//                int itemCount = adapter.getItemCount();
//
//                if (lastVisibleItemPosition == itemCount - 1) {
//                    // 当滑动到列表末尾时，平滑滚动到列表开头
//                    recyclerView.smoothScrollToPosition(0);
//                } else if (firstVisibleItemPosition == 0) {
//                    // 当滑动到列表开头时，平滑滚动到列表末尾
//                    recyclerView.smoothScrollToPosition(itemCount - 1);
//                }
//            }
//        });

    }

}