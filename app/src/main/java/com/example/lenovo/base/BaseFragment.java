package com.example.lenovo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hmz on 2017/7/5.
 */

public abstract class BaseFragment extends Fragment{

    protected Context mcontext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /*
    * 抽象类由孩子类实现，实现不同页面
    * */
    public  abstract View initView();

    /*当activity被创建，调用这个方法*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        
    }

    /*当子类需要联网请求数据的时候，可以重写方法，得到数据*/
    public void initData() {

    }
}
