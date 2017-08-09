package com.example.lenovo.controller.activity.settingActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.lenovo.controller.activity.SettingDB.CollectDao;
import com.example.lenovo.controller.activity.settingAdapter.CollectAdapter;
import com.example.lenovo.controller.activity.settingAdapter.SwipeItemLayout;
import com.example.lenovo.home.bean.GoodsInfo;
import com.example.lenovo.myapplication.R;

import java.util.List;

public class CollectActivity extends Activity {
    private RecyclerView re_collect;
    private Object dataFromDB;
    private CollectDao collectDao;
    private CollectAdapter adapter1;
    private ImageButton ib_collect_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_collect);
        collectDao = new CollectDao(getApplication());
        ib_collect_back = findViewById(R.id.ib_collect_back);
        re_collect = findViewById(R.id.re_collect);
        ib_collect_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        List<GoodsInfo> dataFromDB = getDataFromDB();


        setAdapter(dataFromDB);

    }

    private void setAdapter(List<GoodsInfo> dataFromDB) {
        GridLayoutManager manager = new GridLayoutManager(CollectActivity.this, 1);
        re_collect.setLayoutManager(manager);
        re_collect.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getApplication()));

        adapter1 = new CollectAdapter(CollectActivity.this, dataFromDB);
        re_collect.setAdapter(adapter1);

    }

    public List<GoodsInfo> getDataFromDB() {
        List<GoodsInfo> goods = collectDao.getGoods();
        return goods;
    }
}
