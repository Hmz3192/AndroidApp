package com.example.lenovo.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.home.bean.ResultBeanData;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.Constants;

import java.util.List;

/**
 * Created by Hmz on 2017/7/14.
 */

public class RecommendGridViewAdapter extends BaseAdapter {
    private final Context mcontext;
    private final List<ResultBeanData.ResultBean.RecommendInfoBean> datas;

    public RecommendGridViewAdapter(Context mcontext, List<ResultBeanData.ResultBean.RecommendInfoBean> data) {
        this.mcontext = mcontext;
        this.datas = data;

    }

    /*总数据的大小*/
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(mcontext, R.layout.item_recommend_grid_view, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_recommend = view.findViewById(R.id.iv_recommend);
            viewHolder.tv_name = view.findViewById(R.id.tv_name);
            viewHolder.tv_price = view.findViewById(R.id.tv_price);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        /*根据位置设置数据*/
        ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = datas.get(position);
        Glide.with(mcontext).load(Constants.BASE_URL_IMAGE + recommendInfoBean.getFigure()).into(viewHolder.iv_recommend);
        viewHolder.tv_name.setText(recommendInfoBean.getName());
        viewHolder.tv_price.setText("$"+recommendInfoBean.getCover_price());

        return view;
    }

    static class ViewHolder {
        ImageView iv_recommend;
        TextView tv_name, tv_price;
    }

}
