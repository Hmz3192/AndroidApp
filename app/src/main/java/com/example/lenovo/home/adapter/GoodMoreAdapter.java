package com.example.lenovo.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.home.activity.GoodsMoreActivity;
import com.example.lenovo.home.bean.MoreDetailBean;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.Constants;

import java.util.List;

/**
 * User--Hu mingzhi on 2017/8/2.
 * Created by ThinKPad
 */

public class GoodMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final GoodsMoreActivity mcontext;
    private OnItemClickListener onItemClickListener;
    private List<MoreDetailBean> moreDetailBeenList;


    public GoodMoreAdapter(GoodsMoreActivity mcontext,List<MoreDetailBean>  moreDetailBeenList) {
        this.mcontext = mcontext;
        this.moreDetailBeenList = moreDetailBeenList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mcontext, R.layout.item_goods_list_adapter, null));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(moreDetailBeenList.get(position));
    }

    @Override
    public int getItemCount() {
        return moreDetailBeenList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_hot;
        private TextView tv_name;
        private TextView tv_price;
        private MoreDetailBean data;


        public ViewHolder(View itemView) {
            super(itemView);
            iv_hot = (ImageView) itemView.findViewById(R.id.iv_hot);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.setOnItemClickListener(data);
                        }
                    }
                });


        }

        public void setData(MoreDetailBean moreDetailBean) {
            this.data = moreDetailBean;
            Glide.with(mcontext).load(Constants.BASE_URL_IMAGE +data.getFigure()).into(iv_hot);
            tv_name.setText(data.getName());
            tv_price.setText("￥" + data.getCover_price());

        }
    }
    public interface OnItemClickListener {
        void setOnItemClickListener(MoreDetailBean data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
