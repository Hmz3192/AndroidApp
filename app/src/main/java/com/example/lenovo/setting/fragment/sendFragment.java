package com.example.lenovo.setting.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lenovo.base.BaseFragment;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.setting.adapter.SendFragmentAdapter;
import com.example.lenovo.setting.bean.OrderInfo;
import com.example.lenovo.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * User--Hu mingzhi on 2017/8/5.
 * Created by ThinKPad
 */

public class sendFragment extends BaseFragment {
    private static final String TAG = "info";
    private RecyclerView rvOrder;
    private List<OrderInfo.ResultBean.SendOrderBean> resultBean;
    private SendFragmentAdapter adapter;


    @Override
    public View initView() {
        View view = View.inflate(mcontext, R.layout.fragment_order, null);
        rvOrder = view.findViewById(R.id.recyclerview1);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }


    private void getDataFromNet() {
        String url = Constants.ORDER_INFO;
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
        OrderInfo resultBeanData = JSON.parseObject(json, OrderInfo.class);
        resultBean = resultBeanData.getResult().getSend_order();
        if (resultBean != null) {
            //有数据
            //设置适配器
            adapter = new SendFragmentAdapter(mcontext, resultBean);
            GridLayoutManager manager = new GridLayoutManager(mcontext, 1);
            rvOrder.setAdapter(adapter);
            /*设置布局管理者*/
            rvOrder.setLayoutManager(manager);


        } else

        {
            //没有数据
            Toast.makeText(mcontext, "no data", Toast.LENGTH_LONG).show();
        }
//        Log.e(TAG, "解析对象==" + resultBean.getHot_info().get(0).getName());


    }
}

