package com.example.lenovo.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.home.bean.Test;
import com.example.lenovo.myapplication.R;

import java.util.List;

/**
 * User--Hu mingzhi on 2017/9/21.
 * Created by ThinKPad
 */

public class WantRecyleViewAdapter extends RecyclerView.Adapter<WantRecyleViewAdapter.ViewHolder> {
    private final List<Test> list;
    private final Context mContext;

    public WantRecyleViewAdapter(Context mcontext, List<Test> data) {
        this.list = data;
        this.mContext = mcontext;
    }


    @Override
    public WantRecyleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_want,null);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WantRecyleViewAdapter.ViewHolder holder, int position) {
        Test test = list.get(position);
        holder.tv_title.setText(test.getTitle());


    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        /*实例化*/

        private TextView tv_title;
        public ViewHolder(final View itemView) {
            super(itemView);
//            tv_title = itemView.findViewById(R.id.tv_title);



        }
    }

    /*监听器*/
    public interface OnWantRecyleView {
        /*当某条被点击的时候回调*/
        public void onItemClick(int postion);
    }

    private OnWantRecyleView onWantRecyleView;
    /*设置监听*/
    public void setOnWantRecyleView(OnWantRecyleView onWantRecyleView) {
        this.onWantRecyleView = onWantRecyleView;
    }
}
