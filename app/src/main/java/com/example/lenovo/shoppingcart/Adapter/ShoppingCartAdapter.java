package com.example.lenovo.shoppingcart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.home.bean.GoodsInfo;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.shoppingcart.utils.CartStorage;
import com.example.lenovo.shoppingcart.view.AddSubView;
import com.example.lenovo.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Hmz on 2017/7/15.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private final Context mcontext;
    private final List<GoodsInfo> datas;
    private final CheckBox checkboxAll;
    private final TextView tvShopcartTotal;
    /*完成状态下的删除*/
    private final CheckBox cbAll;
    private String totalPrice;
    private CartStorage cartStorage;
    public ShoppingCartAdapter(Context mcontext, List<GoodsInfo> goodsInfoList, TextView tvShopcartTotal, CartStorage cartProvider, CheckBox checkboxAll, CheckBox cbAll) {
        this.mcontext = mcontext;
        this.datas = goodsInfoList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.cartStorage = cartProvider;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        showTotalPrice();
        /*设置点击事件*/
        setListener();
        /*检验是否全选*/
        CheckAll();
    }
    public void deleteData() {
        if (datas != null && datas.size() > 0) {
            for (Iterator iterator = datas.iterator(); iterator.hasNext(); ) {

                GoodsInfo cart = (GoodsInfo) iterator.next();

                if (cart.isSelected()) {

                    //这行代码放在前面
                    int position = datas.indexOf(cart);
                    //1.删除本地缓存的
                    cartStorage.deleteData(cart);

                    //2.删除当前内存的
                    iterator.remove();

                    //3.刷新数据
                    notifyItemRemoved(position);

                }
            }
        }
    }
    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                        /*根据位置找到对应的bean对象*/
                GoodsInfo goodsInfo = datas.get(position);
                        /*设置取反状态*/
                goodsInfo.setSelected(!goodsInfo.isSelected());
                        /*刷新状态*/
                notifyItemChanged(position);
                  /*是否全选*/
                CheckAll();
                        /*重新计算*/
                showTotalPrice();
            }
        });
        /*设置checkbox的点击事件*/
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*得到状态*/
                boolean isCheck = checkboxAll.isChecked();
                /*根据状态设置全选和非全选*/
                checkAll_none(isCheck);
                /*计算总价格*/
                showTotalPrice();

            }
        });
            /*设置cbAll的点击事件*/
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*得到状态*/
                boolean isCheck = cbAll.isChecked();
                /*根据状态设置全选和非全选*/
                checkAll_none(isCheck);
            }
        });


    }


    /*设置全选和非全选*/
    public void checkAll_none(boolean isCheck) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsInfo goodsInfo = datas.get(i);
                goodsInfo.setSelected(isCheck);
                notifyItemChanged(i);
            }


        }

    }


    public void showTotalPrice() {
        tvShopcartTotal.setText("合计：" + getTotalPrice());

    }

    /*计算总价格*/
    public Double getTotalPrice() {
        double totalPrice = 0.0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsInfo goodsInfo = datas.get(i);
                if (goodsInfo.isSelected()) {
                    totalPrice += Double.valueOf(goodsInfo.getNumber()) * Double.valueOf(goodsInfo.getCover_price());
                }
            }
        }
        return totalPrice;
    }
    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                if (!datas.get(i).isSelected()) {
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                    return;
                } else {
                    checkboxAll.setChecked(true);
                    cbAll.setChecked(true);
                }
            }
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mcontext, R.layout.item_shop_cart, null);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /*根据位置得到对应的bean对象*/
        final GoodsInfo goodsInfo = datas.get(position);
        /*设置数据*/

        holder.cb_gov.setChecked(goodsInfo.isSelected());
        Glide.with(mcontext).load(Constants.BASE_URL_IMAGE + goodsInfo.getFigure()).into(holder.iv_gov);
        holder.tv_desc_gov.setText(goodsInfo.getName());
        holder.tv_price_gov.setText("$" + goodsInfo.getCover_price());
        holder.addSubView.setValue(goodsInfo.getNumber());
        holder.addSubView.setMaxvalue(10);
        holder.addSubView.setMinvalue(1);

        /*设置商品数量的变化*/
        holder.addSubView.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {
            @Override
            public void OnNumberchanger(int value) {
                /*当前内存中更新*/
                goodsInfo.setNumber(value);
                /*本地更新*/
                CartStorage.getInstance().updateData(goodsInfo);
                /*刷新适配器*/
                notifyItemChanged(position);
                /*再次计算*/
                showTotalPrice();
            }
        });
    }

    public void CheckAll() {
        if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsInfo goodsInfo = datas.get(i);
                if (!goodsInfo.isSelected()) {
                    /*非全选*/
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                } else {
                    /*选中的*/
                    number++;
                }
            }
            if (number == datas.size()) {
                /*全选*/
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            } else {
                checkboxAll.setChecked(false);
                cbAll.setChecked(false);
            }

        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public List<GoodsInfo> collectDatas() {
        List<GoodsInfo> goodsInfos = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            /*迭代器*/
            for (Iterator iterator = datas.iterator(); iterator.hasNext(); ) {

                GoodsInfo cart = (GoodsInfo) iterator.next();

                if (cart.isSelected()) {
                    goodsInfos.add(cart);

                }
            }

        }
        return goodsInfos;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov, tv_price_gov;
        private AddSubView addSubView;

        public ViewHolder(View itemView) {
            super(itemView);
            cb_gov = itemView.findViewById(R.id.cb_gov);
            iv_gov = itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = itemView.findViewById(R.id.tv_price_gov);
            addSubView = itemView.findViewById(R.id.numberAddSubView);
            /*设置item的点击事件*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });

        }
    }

    /*点击item的监听者*/
    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
