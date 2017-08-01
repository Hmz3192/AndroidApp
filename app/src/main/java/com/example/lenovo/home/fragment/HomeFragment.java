package com.example.lenovo.home.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lenovo.base.BaseFragment;
import com.example.lenovo.home.activity.MessageCenterActivity;
import com.example.lenovo.home.adapter.HomeFragmentAdapter;
import com.example.lenovo.home.bean.ResultBeanData;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Hmz on 2017/7/5.
 * 主页面
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;
    private HomeFragmentAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    /*返回的数据*/
    private ResultBeanData.ResultBean resultBean;

    @Override
    public View initView() {
        Log.e(TAG, "主页视图被初始化了");
        View view = View.inflate(mcontext, R.layout.fragment_home, null);
        rvHome = view.findViewById(R.id.rv_home);
        ib_top = view.findViewById(R.id.ib_top);
        tv_search_home = view.findViewById(R.id.tv_search_home);
        tv_message_home = view.findViewById(R.id.tv_message_home);
        mSwipeLayout =  view.findViewById(R.id.swipe_container);
        //设置点击事件
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "主页数据被初始化了");
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
  /*   String url = "http://www.csdn.net/";*/
  /*String url = "http://101.201.234.133:8080/index.jsp";*/
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    /*当请求失败的时候*/
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Log.e(TAG, "首页请求失败==" + e.getMessage());
                    }

                    /*当联网成功回调数据，respond请求成功的数据回调*/
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "请求成功==" + response);
                        //解析数据
                        procssData(response);
                    }
                });
    }

    private void procssData(String json) {
        ResultBeanData resultBeanData = JSON.parseObject(json, ResultBeanData.class);
        resultBean = resultBeanData.getResult();
        if (resultBean != null) {
            //有数据
            //设置适配器
            adapter = new HomeFragmentAdapter(mcontext, resultBean);
            rvHome.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(mcontext, 1);
            /*设置跨度大小的监听*/
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position <= 3) {
                        /*隐藏*/
                        ib_top.setVisibility(View.GONE);
                    } else {
                        ib_top.setVisibility(View.VISIBLE);
                        /*显示*/
                    }
                    /*只能返回1*/
                    return 1;
                }
            });

            /*设置布局管理者*/
            rvHome.setLayoutManager(manager);


        } else {
            //没有数据
            Toast.makeText(mcontext, "no data", Toast.LENGTH_LONG).show();
        }
//        Log.e(TAG, "解析对象==" + resultBean.getHot_info().get(0).getName());


    }

    private void initListener() { //置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到顶部
                rvHome.scrollToPosition(0);
            }
        }); //搜素的监听

        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "搜索功能暂未开放", Toast.LENGTH_SHORT).show();
            }
        });

        //消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mcontext, "消息中心功能暂未开放", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mcontext, MessageCenterActivity.class);
                mcontext.startActivity(intent);
            }
        });

        mSwipeLayout.setOnRefreshListener(this);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(android.R.color.holo_red_light); // 设定下拉圆圈的背景
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE); // 设置圆圈的大小
    }

    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                // 停止刷新
                mSwipeLayout.setRefreshing(false);

            }
        }, 2000); // 2秒后发送消息，停止刷新
    }
}
