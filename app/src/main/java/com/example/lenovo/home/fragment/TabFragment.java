package com.example.lenovo.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.example.lenovo.controller.activity.SettingDB.WantDao;
import com.example.lenovo.controller.activity.settingBean.WantBean;
import com.example.lenovo.home.adapter.TabAdapter.MyAdapterViewAdapter;
import com.example.lenovo.home.bean.EssayBean;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * User--Hu mingzhi on 2017/9/22.
 * Created by ThinKPad
 */

public class TabFragment extends TabBaseFragment {
    //    private onRecyclerItemClick mListener;
    private RecyclerView wantlist_rec;
    private List<EssayBean.ResultBean.ErshouBean> ershouBeenList;
    private MyAdapterViewAdapter adapter;
    private WantDao wantDao;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.want_pager, null);
        wantlist_rec = view.findViewById(R.id.wantlist_rec);
        wantDao = new WantDao(getActivity());
        initView();
        return view;
    }

    private void initView() {
        getDataFromNet();

//        adapter.setOnRecyclerItemClick(new MyAdapterViewAdapter.onRecyclerItemClick() {
//            @Override
//            public void onItemClick(View view, int position) {
//                mListener.onRecyclerItemClick(position);
//                Log.d("Test", "onItemClick: " + position);
//            }
//        });

    }

    private void getDataFromNet() {
        String url = Constants.ESSAY_INFO;
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

                        Log.e("info", "首页请求失败==" + e.getMessage());
                    }

                    /*当联网成功回调数据，respond请求成功的数据回调*/
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("info", "请求成功==" + response);
                        //解析数据
                        procssData(response);
                    }
                });
    }

    private void procssData(String response) {
        EssayBean resultBeanList = JSON.parseObject(response, EssayBean.class);
        ershouBeenList = resultBeanList.getResult().getErshou();
        List<WantBean> wantBeenList = wantDao.getWants("0");
        if (wantBeenList.size() != 0) {
            getDateSQL(wantBeenList);
        }
        adapter = new MyAdapterViewAdapter(getActivity(), ershouBeenList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        wantlist_rec.setAdapter(adapter);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wantlist_rec.setLayoutManager(layoutManager);
    }


    private void getDateSQL(List<WantBean> wantBeenList) {


        for (WantBean wantBeen : wantBeenList) {
            EssayBean.ResultBean.ErshouBean ershouBean = new EssayBean.ResultBean.ErshouBean();

            ershouBean.setUser(wantBeen.getUser());
            ershouBean.setTitle(wantBeen.getTitle());
            ershouBean.setTime(wantBeen.getTime());
            ershouBean.setEssay(wantBeen.getEssay());
            ershouBean.setEssay1(wantBeen.getEssay1());
            ershouBean.setEssay2(wantBeen.getEssay2());

            List<String> urls = new ArrayList<>();
            urls.add(wantBeen.getUrl());
            urls.add(wantBeen.getUrl1());
            urls.add(wantBeen.getUrl2());

            ershouBean.setUrl(urls);
            ershouBean.setFigure(wantBeen.getFigure());
            this.ershouBeenList.add(ershouBean);
        }


    }

    @Override
    public String getFragmentTitle() {
        return "二手求购";
    }

    /*public interface onRecyclerItemClick{
        public void onRecyclerItemClick(int message);
    }
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            mListener = (TabFragment.onRecyclerItemClick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement OnGridViewSelectedListener");
        }
    }*/
}
