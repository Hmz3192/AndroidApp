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
 * Created by Hmz on 2017/7/13.
 */

public class ChannelAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.ChannelInfoBean> datas;

    public ChannelAdapter(Context mcontext, List<ResultBeanData.ResultBean.ChannelInfoBean> data) {
        this.mContext = mcontext;
        this.datas = data;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            /*加载单个布局*/
            view = view.inflate(mContext, R.layout.item_channel, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon = (ImageView) view.findViewById(R.id.iv_channel);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_channel);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        /*根据位置得到对应的数据*/
        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = datas.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + channelInfoBean.getImage()).into(viewHolder.iv_icon);
        viewHolder.tv_title.setText(channelInfoBean.getChannel_name());
        return view;
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_title;

    }
}
