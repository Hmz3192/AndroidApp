package com.example.lenovo.setting.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.setting.bean.OrderInfo;
import com.example.lenovo.setting.db.OrderDao;
import com.example.lenovo.utils.Constants;

import java.util.List;

/**
 * User--Hu mingzhi on 2017/8/5.
 * Created by ThinKPad
 */

public class BuyFragmentAdapter extends RecyclerView.Adapter {
    private final Context mcontext;
    private final List<OrderInfo.ResultBean.BuyOrderBean> datas;
    private OnItemClickListener onItemClickListener;
    private AlertDialog.Builder builder;
    private OrderDao orderDao;



    public BuyFragmentAdapter(Context mcontext, List<OrderInfo.ResultBean.BuyOrderBean> resultBean) {
        this.mcontext = mcontext;
        this.datas = resultBean;
        orderDao = new OrderDao(mcontext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BuyFragmentAdapter.ViewHolder(View.inflate(mcontext, R.layout.order_list, null));


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BuyFragmentAdapter.ViewHolder viewHolder = (BuyFragmentAdapter.ViewHolder) holder;
        viewHolder.setData(datas.get(position));
        viewHolder.tv_delete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Toast.makeText(mcontext, "删除"+getLayoutPosition(), Toast.LENGTH_SHORT).show();
                showSimpleDialog(view,position);

//                            del_id.setVisibility(View.GONE);
            }
        });
    }
    private void showSimpleDialog(View view, final int position) {
        builder=new AlertDialog.Builder(mcontext);
        builder.setIcon(R.mipmap.atguigu_logo_1);
        builder.setTitle("提示消息");
        builder.setMessage("确定要取消该订单吗？");

        //监听下方button点击事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                    Toast.makeText(getApplicationContext(),R.string.toast_postive,Toast.LENGTH_SHORT).show();
                String id = datas.get(position).getId();
                orderDao.addAccount(id);
                datas.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(0,datas.size());

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                    Toast.makeText(getApplicationContext(), R.string.toast_negative, Toast.LENGTH_SHORT).show();
                return;
            }

        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
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
        private OrderInfo.ResultBean.BuyOrderBean datas;


        public ViewHolder(View itemView) {
            super(itemView);
            initView(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.setOnItemClickListener(getLayoutPosition());
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
            tv_delete_order.setText("取消订单");


        }

        public void setData(OrderInfo.ResultBean.BuyOrderBean data) {
            this.datas = data;
                Glide.with(mcontext).load(Constants.BASE_URL_IMAGE + datas.getUrl()).into(order_pic);
                order_id.setText("    " + datas.getId() + "  >");
                order_status.setText(datas.getStatus());
                order_name.setText(datas.getName());
                tv_now_price.setText("$" + datas.getCover_price());
                tv_or_price.setText("$" + datas.getCover_price());
                order_num.setText("x" + datas.getNum());
                order_show_num.setText("共" + datas.getNum() + "件商品,合计:$ ");
                order_show_price.setText(String.valueOf(Double.valueOf(datas.getCover_price()) * Double.valueOf(data.getNum())));

        }

    }



    public interface OnItemClickListener {
        void setOnItemClickListener(int postion);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
