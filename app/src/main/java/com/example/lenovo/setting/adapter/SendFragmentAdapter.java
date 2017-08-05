package com.example.lenovo.setting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.setting.bean.OrderInfo;
import com.example.lenovo.utils.Constants;

import java.util.List;

/**
 * User--Hu mingzhi on 2017/8/5.
 * Created by ThinKPad
 */

public class SendFragmentAdapter extends  RecyclerView.Adapter{
    private final Context mcontext;
    private final List<OrderInfo.ResultBean.SendOrderBean> datas;
    private OnItemClickListener onItemClickListener;

    public SendFragmentAdapter(Context mcontext, List<OrderInfo.ResultBean.SendOrderBean> resultBean) {
        this.mcontext = mcontext;
        this.datas = resultBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SendFragmentAdapter.ViewHolder(View.inflate(mcontext, R.layout.order_list, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SendFragmentAdapter.ViewHolder viewHolder = (SendFragmentAdapter.ViewHolder) holder;
        viewHolder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView order_id;
        private TextView order_status;
        private ImageView order_pic;
        private TextView order_name;
        private TextView tv_now_price;
        private TextView tv_or_price;
        private TextView order_num;
        private TextView order_show_num;
        private TextView order_show_price;
        private TextView tv_delete_order;
        private OrderInfo.ResultBean.SendOrderBean data;


        public ViewHolder(View itemView) {
            super(itemView);
            initView(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.setOnItemClickListener(data);
                    }
                }
            });
        }

        private void initView(View itemView) {
            order_id = itemView.findViewById(R.id.order_id);
            order_status = itemView.findViewById(R.id.order_status);
            order_pic = itemView.findViewById(R.id.order_pic);
            order_name = itemView.findViewById(R.id.order_name);
            tv_now_price = itemView.findViewById(R.id.tv_now_price);
            tv_or_price = itemView.findViewById(R.id.tv_or_price);
            order_num = itemView.findViewById(R.id.order_num);
            order_show_num = itemView.findViewById(R.id.order_show_num);
            order_show_price = itemView.findViewById(R.id.order_show_price);
            tv_delete_order = itemView.findViewById(R.id.tv_delete_order);



        }

        public void setData(OrderInfo.ResultBean.SendOrderBean data) {
            this.data = data;
            Glide.with(mcontext).load(Constants.BASE_URL_IMAGE + data.getUrl()).into(order_pic);
            order_id.setText("    "+data.getId()+"  >");
            order_status.setText(data.getStatus());
            order_name.setText(data.getName());
            tv_now_price.setText("$"+ data.getCover_price());
            tv_or_price.setText("$" + data.getCover_price());
            order_num.setText("x" +  data.getNum());
            order_show_num.setText("共"+data.getNum()+"件商品,合计:$ ");
            order_show_price.setText(String.valueOf(Double.valueOf(data.getCover_price())*Double.valueOf(data.getNum())));

        }

    }
    public interface OnItemClickListener {
        void setOnItemClickListener(OrderInfo.ResultBean.SendOrderBean data);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
