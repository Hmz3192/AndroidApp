package com.example.lenovo.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.home.activity.GoodsListActivity;
import com.example.lenovo.home.bean.TypeListBean;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.Constants;

import java.util.List;

/**
 * Created by Hmz on 2017/7/28.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<TypeListBean.ResultBean.PageDataBean> datas;
    private final GoodsListActivity mcontext;
    private OnItemClickListener onItemClickListener;

    public GoodsListAdapter(GoodsListActivity mContext, List<TypeListBean.ResultBean.PageDataBean> page_data) {
        this.mcontext = mContext;
        this.datas = page_data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mcontext, R.layout.item_goods_list_adapter, null));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_hot;
        private TextView tv_name;
        private TextView tv_price;
        private TypeListBean.ResultBean.PageDataBean data;

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

        public void setData(TypeListBean.ResultBean.PageDataBean data) {
            this.data = data;
            Glide.with(mcontext).load(Constants.BASE_URL_IMAGE +data.getFigure()).into(iv_hot);
            tv_name.setText(data.getName());
            tv_price.setText("￥" + data.getCover_price());

        }
    }
    public interface OnItemClickListener {
        void setOnItemClickListener(TypeListBean.ResultBean.PageDataBean data);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
