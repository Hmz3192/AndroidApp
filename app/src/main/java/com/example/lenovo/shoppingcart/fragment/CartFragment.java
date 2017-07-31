package com.example.lenovo.shoppingcart.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.app.MainActivity;
import com.example.lenovo.base.BaseFragment;
import com.example.lenovo.home.bean.GoodsInfo;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.shoppingcart.Adapter.ShoppingCartAdapter;
import com.example.lenovo.shoppingcart.utils.CartStorage;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Hmz on 2017/7/5.
 * 购物车
 */

public class CartFragment extends BaseFragment {

    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;
    private TextView textView;
    private ImageView ivEmpty;
    private TextView tvEmptyCartTobuy;
    private ShoppingCartAdapter adapter;

    private LinearLayout ll_empty_shopcart;

    /*编辑状态*/
    private static final int ACTION_EDIT = 1;
    /*完成状态*/
    private static final int ACTION_COMPLETE = 2;
    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-07-15 17:46:35 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */


    @Override
    public View initView() {
        Log.e(TAG, "购物车UI初始化");
        View view = View.inflate(mcontext, R.layout.fragment_shopping_cart, null);
        findView(view);
        return view;
    }
    @Override
    public void initData() {
        super.initData();
        initListener();
        Log.e(TAG, "购物车数据初始化");
        showData();
    }
    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    private void findView(View view) {
        tvShopcartEdit = (TextView) view.findViewById(R.id.tv_shopcart_edit);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        llCheckAll = (LinearLayout) view.findViewById(R.id.ll_check_all);
        checkboxAll = (CheckBox) view.findViewById(R.id.checkbox_all);
        tvShopcartTotal = (TextView) view.findViewById(R.id.tv_shopcart_total);
        btnCheckOut = (Button) view.findViewById(R.id.btn_check_out);
        llDelete = (LinearLayout) view.findViewById(R.id.ll_delete);
        cbAll = (CheckBox) view.findViewById(R.id.cb_all);
        btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnCollection = (Button) view.findViewById(R.id.btn_collection);
        ivEmpty = (ImageView) view.findViewById(R.id.iv_empty);
        tvEmptyCartTobuy = (TextView) view.findViewById(R.id.tv_empty_cart_tobuy);
        ll_empty_shopcart = view.findViewById(R.id.ll_empty_shopcart);
    }

    private void initListener() {
        /*设置默认的编辑状态*/
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int action = (int) view.getTag();
                if (action == ACTION_EDIT) {
                    /*切换为完成*/
                    showDelete();
                } else {
                    /*切换成编辑*/
                    hideDelete();
                }

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteData();
                adapter.showTotalPrice();
                //显示空空如也
                checkData();
                adapter.checkAll();
            }
        });
        tvEmptyCartTobuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, MainActivity.class);
                startActivity(intent);
                getActivity().finish();
//                Constants.isBackHome = true;

            }
        });

    }
    private void checkData() {
        if (adapter != null && adapter.getItemCount() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);

        } else {
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            tvShopcartEdit.setVisibility(View.GONE);

        }
    }
    private void hideDelete() {
  /*设置状态和文本-编辑*/
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        /*变成非勾选*/
        if (adapter != null) {
            adapter.checkAll_none(true);
            adapter.CheckAll();
        }
        /*删除视图显示*/
        llDelete.setVisibility(View.GONE);
        /*结算视图隐藏*/
        llCheckAll.setVisibility(View.VISIBLE);

    }

    private void showDelete() {
        /*设置状态和文本-完成*/
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");
        /*变成非勾选*/
        if (adapter != null) {
            adapter.checkAll_none(false);
            adapter.CheckAll();
        }
        /*删除视图显示*/
        llDelete.setVisibility(View.VISIBLE);
        /*结算视图隐藏*/
        llCheckAll.setVisibility(View.GONE);

    }



    /*展示数据*/
    private void showData() {
        CartStorage cartProvider = CartStorage.getInstance();
        List<GoodsInfo> goodsInfoList =cartProvider.getAllData();
        if (goodsInfoList != null && goodsInfoList.size() > 0) {
            /*有数据则隐藏无数据页面*/
            ll_empty_shopcart.setVisibility(View.GONE);
            tvShopcartEdit.setVisibility(View.VISIBLE);
            adapter = new ShoppingCartAdapter(mcontext, goodsInfoList,tvShopcartTotal,cartProvider,checkboxAll,cbAll);
            recyclerview.setAdapter(adapter);
            /*设置布局管理器*/
            recyclerview.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false));
        } else {
            /*无数据则显示空布局*/
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            tvShopcartEdit.setVisibility(View.GONE);

        }
    }

}
