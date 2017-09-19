package com.example.lenovo.controller.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.lenovo.app.Bean.QQUser;
import com.example.lenovo.model.bean.PickContactInfo;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.QQURL;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/9/25.
 */
// 选择联系人的页面适配器
public class PickContactAdapter extends BaseAdapter {
    private Context mContext;
    private List<PickContactInfo> mPicks = new ArrayList<>();
    private List<String> mExistMembers = new ArrayList<>();// 保存群中已经存在的成员集合

    public PickContactAdapter(Context context, List<PickContactInfo> picks, List<String> existMembers) {
        mContext = context;

        if (picks != null && picks.size() >= 0) {
            mPicks.clear();
            mPicks.addAll(picks);
        }

        // 加载已经存在的成员集合
        mExistMembers.clear();
        mExistMembers.addAll(existMembers);
    }

    @Override
    public int getCount() {
        return mPicks == null ? 0 : mPicks.size();
    }

    @Override
    public Object getItem(int position) {
        return mPicks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 创建或获取viewHolder
        ViewHolder holder  = null;

        if(convertView == null) {
            holder = new ViewHolder();

            convertView = View.inflate(mContext, R.layout.item_pick, null);

            holder.cb = (CheckBox) convertView.findViewById(R.id.cb_pick);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_pick_name);
            holder.tv_photo = convertView.findViewById(R.id.tv_photo);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 获取当前item数据
        PickContactInfo pickContactInfo = mPicks.get(position);

        getDataFromMy(pickContactInfo.getUser().getName(),holder.tv_name,holder.tv_photo);
        // 显示数据
//        holder.tv_name.setText(dataFromMy.getNickname());
        holder.cb.setChecked(pickContactInfo.isChecked());
//        if (!dataFromMy.getPhoto().equalsIgnoreCase("0")) {
//            Picasso.with(mContext)
//                    .load(dataFromMy.getPhoto())
//                    .networkPolicy(NetworkPolicy.NO_CACHE)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE)//不加载缓存
//                    .into( holder.tv_photo);
//        }
        // 判断
        if(mExistMembers.contains(pickContactInfo.getUser().getHxid())) {
            holder.cb.setChecked(true);
            pickContactInfo.setIsChecked(true);
        }

        return convertView;
    }

    // 获取选择的联系人
    public List<String> getPickContacts() {

        List<String> picks = new ArrayList<>();

        for (PickContactInfo pick: mPicks){

            // 判断是否选中
            if(pick.isChecked()) {
                picks.add(pick.getUser().getName());
            }
        }

        return picks;
    }

    private class  ViewHolder{
        private CheckBox cb;
        private TextView tv_name;
        private ImageView tv_photo;
    }


    private void getDataFromMy(String hxid, final TextView tv_name, final ImageView tv_photo) {
        String url = QQURL.GETONEINFO;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("hxid", hxid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("info", "成功获取数据：" + response);
                        QQUser.UserBean userBean = JSON.parseObject(response, QQUser.UserBean.class);
                        tv_name.setText(userBean.getNickname());
                        if (!userBean.getPhoto().equalsIgnoreCase("0")) {
                            Picasso.with(mContext)
                                    .load(userBean.getPhoto())
//                                    .networkPolicy(NetworkPolicy.NO_CACHE)
//                                    .memoryPolicy(MemoryPolicy.NO_CACHE)//不加载缓存
                                    .into(tv_photo);
                        }

                    }

                });
    }

}
