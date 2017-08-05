package com.example.lenovo.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lenovo.app.GoodsInfoActivity;
import com.example.lenovo.home.adapter.GoodMoreAdapter;
import com.example.lenovo.home.bean.GoodsInfo;
import com.example.lenovo.home.bean.MoreDetailBean;
import com.example.lenovo.home.bean.ResultBeanData;
import com.example.lenovo.home.bean.TypeListBean;
import com.example.lenovo.home.utils.SpaceItemDecoration;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class GoodsMoreActivity extends Activity implements View.OnClickListener {
    private ImageButton ib_back, ib_home;
    private TextView tv_search, tv_sort, tv_price;
    private ImageView iv_arrow;
    private RecyclerView recyclerview;
    private ResultBeanData.ResultBean resultBean;
    private Context mcontext;
    private GoodMoreAdapter adapter;
    private int click_count = 0;
    private List<MoreDetailBean> moreDetailBeenList = new ArrayList<>();

    /*沉浸式布局*/
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_more);
        initView();
       /* XToast.create(GoodsMoreActivity.this)
                .setText("kind"+kind)
                .setAnimation(AnimationUtils.ANIMATION_DRAWER) //Drawer Type
                .setDuration(XToast.XTOAST_DURATION_LONG)
                .setOnDisappearListener(new XToast.OnDisappearListener() {
                    @Override
                    public void onDisappear(XToast xToast) {
                        Log.d("cylog", "The XToast has disappeared..");
                    }
                }).show();*/
        getDataFromKind();
    }

    private void initView() {
        ib_back = findViewById(R.id.ib_back);
        ib_home = findViewById(R.id.ib_home);
        tv_search = findViewById(R.id.tv_search);
        tv_sort = findViewById(R.id.tv_sort);
        tv_price = findViewById(R.id.tv_price);
        recyclerview = findViewById(R.id.recyclerview);
        iv_arrow = findViewById(R.id.iv_arrow);

        ib_back.setOnClickListener(this);
        ib_home.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_sort.setOnClickListener(this);
        tv_price.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                overridePendingTransition(R.anim.slide_up_in,
                        R.anim.slide_down_out);
                break;
            case R.id.ib_home:
                Constants.isBackHome = true;
                finish();
                break;
            case R.id.tv_search:
                Toast.makeText(GoodsMoreActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_sort:
                break;
            case R.id.tv_price:
                //价格点击事件
                click_count++;
                //综合排序变为默认
                tv_sort.setTextColor(Color.parseColor("#333538"));
                //价格红
                tv_price.setTextColor(Color.parseColor("#ed4141"));
                if (click_count % 2 == 1) {
                    // 箭头向下红
                    iv_arrow.setBackgroundResource(R.drawable.new_price_sort_desc);
                    /*价格由高到低,倒序*/
                    TypeListBean.SortList<MoreDetailBean> sortList = new TypeListBean.SortList<>();
                    sortList.Sort(moreDetailBeenList, "getCover_price", "desc");
                    setAdapter();
                } else {
                    // 箭头向上红
                    iv_arrow.setBackgroundResource(R.drawable.new_price_sort_asc);
                    /*价格由低到高,*/
                    TypeListBean.SortList<MoreDetailBean> sortList = new TypeListBean.SortList<>();
                    sortList.Sort(moreDetailBeenList, "getCover_price", null);
                    setAdapter();
                }
                break;


        }

    }

    public void getDataFromKind() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 100:
                    Log.e("TAG", "请求成功：" + response);
//                  //解析数据
                    procssData(response);
                    break;
                case 101:
                    Toast.makeText(GoodsMoreActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    private void procssData(String json) {
        ResultBeanData resultBeanData = JSON.parseObject(json, ResultBeanData.class);
        resultBean = resultBeanData.getResult();
        if (resultBean != null) {
            //有数据
            //设置适配器
//            adapter = new GoodMoreAdapter(mcontext, resultBean, kind);
            GridLayoutManager manager = new GridLayoutManager(GoodsMoreActivity.this, 2);
//            recyclerview.setAdapter(adapter);
            /*设置布局管理者*/
            recyclerview.setLayoutManager(manager);
            /*之间间距*/
            recyclerview.addItemDecoration(new SpaceItemDecoration(10));
            setBeanList();
            setAdapter();

        }
    }

    private void setAdapter() {

        //设置适配器
        adapter = new GoodMoreAdapter(GoodsMoreActivity.this, moreDetailBeenList);
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new GoodMoreAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(MoreDetailBean data) {
                String name = data.getName();
                String cover_price = data.getCover_price();
                String figure = data.getFigure();
                String product_id = data.getProduct_id();
                GoodsInfo goodsBean = new GoodsInfo(cover_price, figure, name, product_id);
                Intent intent = new Intent(GoodsMoreActivity.this, GoodsInfoActivity.class);
                intent.putExtra("goodsbean", goodsBean);
                startActivity(intent);
            }
        });
    }

    private void setBeanList() {
        for (ResultBeanData.ResultBean.HotInfoBean hotInfoBean : resultBean.getHot_info()) {
            MoreDetailBean moreDetailBean = new MoreDetailBean(hotInfoBean.getCover_price(), hotInfoBean.getFigure(), hotInfoBean.getName(), hotInfoBean.getProduct_id());
//            moreDetailBean.setCover_price(hotInfoBean.getCover_price());
//            moreDetailBean.setName(hotInfoBean.getName());
//            moreDetailBean.setFigure(hotInfoBean.getFigure());
//            moreDetailBean.setProduct_id(hotInfoBean.getProduct_id());
            moreDetailBeenList.add(moreDetailBean);
        }
        for (ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean : resultBean.getRecommend_info()) {
            MoreDetailBean moreDetailBean1 = new MoreDetailBean(recommendInfoBean.getCover_price(), recommendInfoBean.getFigure(), recommendInfoBean.getName(), recommendInfoBean.getProduct_id());
//            moreDetailBean1.setCover_price(recommendInfoBean.getCover_price());
//            moreDetailBean1.setName(recommendInfoBean.getName());
//            moreDetailBean1.setFigure(recommendInfoBean.getFigure());
//            moreDetailBean1.setProduct_id(recommendInfoBean.getProduct_id());
            moreDetailBeenList.add(moreDetailBean1);
        }
        for (ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean : resultBean.getSeckill_info().getList()) {
            MoreDetailBean moreDetailBean3 = new MoreDetailBean(listBean.getCover_price(), listBean.getFigure(), listBean.getName(), listBean.getProduct_id());
//            moreDetailBean3.setCover_price(listBean.getCover_price());
//            moreDetailBean3.setName(listBean.getName());
//            moreDetailBean3.setFigure(listBean.getFigure());
//            moreDetailBean3.setProduct_id(listBean.getProduct_id());
            moreDetailBeenList.add(moreDetailBean3);
        }
    }


}
