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

public class HotGridViewAdapter extends BaseAdapter {
    private final List<ResultBeanData.ResultBean.HotInfoBean> datas;
    private final Context mcontext;

    public HotGridViewAdapter(Context mcontext, List<ResultBeanData.ResultBean.HotInfoBean> data) {
        this.mcontext = mcontext;
        this.datas = data;
    }

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(mcontext, R.layout.item_hot_grid_view, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_hot = view.findViewById(R.id.iv_hot);
            viewHolder.tv_name = view.findViewById(R.id.tv_name);
            viewHolder.tv_price = view.findViewById(R.id.tv_price);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        /*根据位置得到对应数据*/
        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = datas.get(i);
        Glide.with(mcontext).load(Constants.BASE_URL_IMAGE + hotInfoBean.getFigure()).into(viewHolder.iv_hot);
        viewHolder.tv_name.setText(hotInfoBean.getName());
        viewHolder.tv_price.setText("$"+hotInfoBean.getCover_price());

        return view;
    }

    static class ViewHolder {
        ImageView iv_hot;
        TextView tv_name, tv_price;
    }
}
