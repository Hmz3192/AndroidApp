package com.example.lenovo.shoppingcart.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.app.MainActivity;
import com.example.lenovo.home.bean.GoodsInfo;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.shoppingcart.Adapter.ShoppingCartAdapter;
import com.example.lenovo.shoppingcart.utils.CartStorage;

import java.util.List;

import static com.example.lenovo.myapplication.R.id.cb_all;
import static com.example.lenovo.myapplication.R.id.ll_check_all;
import static com.example.lenovo.myapplication.R.id.ll_delete;

public class ShoppingCartActivity extends Activity implements View.OnClickListener {
    private ImageButton ibShopcartBack;
    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private LinearLayout ll_empty_shopcart;
    private ShoppingCartAdapter adapter;
    private TextView tv_empty_cart_tobuy;
    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 1;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-07-27 19:32:53 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ibShopcartBack = (ImageButton) findViewById(R.id.ib_shopcart_back);
        tvShopcartEdit = (TextView) findViewById(R.id.tv_shopcart_edit);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        llCheckAll = (LinearLayout) findViewById(ll_check_all);
        checkboxAll = (CheckBox) findViewById(R.id.checkbox_all);
        tvShopcartTotal = (TextView) findViewById(R.id.tv_shopcart_total);
        btnCheckOut = (Button) findViewById(R.id.btn_check_out);
        llDelete = (LinearLayout) findViewById(ll_delete);
        cbAll = (CheckBox) findViewById(cb_all);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        ll_empty_shopcart = findViewById(R.id.ll_empty_shopcart);
        tv_empty_cart_tobuy = findViewById(R.id.tv_empty_cart_tobuy);

        ibShopcartBack.setOnClickListener(this);
        btnCheckOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        tv_empty_cart_tobuy.setClickable(true);
        tv_empty_cart_tobuy.setOnClickListener(this);
        tvShopcartEdit.setOnClickListener(this);

    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-07-27 19:32:53 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        /*返回*/
        if (v == ibShopcartBack) {
            finish();
            overridePendingTransition(R.anim.slide_up_in,
                    R.anim.slide_down_out);
            // Handle clicks for ibShopcartBack
        } else if (v == tvShopcartEdit) {
            //设置编辑的点击事件
            int tag = (int) tvShopcartEdit.getTag();
            if (tag == ACTION_EDIT) {
                //变成完成状态
                showDelete();
            } else {
                //变成编辑状态
                hideDelete();
            }
            /*结算*/
        } else if (v == btnCheckOut) {
            // Handle clicks for btnCheckOut
            /**/
        } else if (v == btnDelete) {
            // Handle clicks for btnDelete
            adapter.deleteData();
            adapter.showTotalPrice();
            //显示空空如也
            checkData();
            adapter.checkAll();
        } else if (v == tv_empty_cart_tobuy) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in,
                    R.anim.push_left_out);
        }
    }

    private void hideDelete() {
        tvShopcartEdit.setText("编辑");
        tvShopcartEdit.setTag(ACTION_EDIT);

        adapter.checkAll_none(true);
        llDelete.setVisibility(View.GONE);
        llCheckAll.setVisibility(View.VISIBLE);

        adapter.showTotalPrice();
    }

    private void showDelete() {
        tvShopcartEdit.setText("完成");
        tvShopcartEdit.setTag(ACTION_COMPLETE);

        adapter.checkAll_none(false);
        cbAll.setChecked(false);
        checkboxAll.setChecked(false);

        llDelete.setVisibility(View.VISIBLE);
        llCheckAll.setVisibility(View.GONE);

        adapter.showTotalPrice();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_shopping_cart);
        findViews();
        showData();
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
    }

    //-----------------------------------------
    private void checkData() {
        if (adapter != null && adapter.getItemCount() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);
            llCheckAll.setVisibility(View.GONE);
        } else {
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            tvShopcartEdit.setVisibility(View.GONE);
            llCheckAll.setVisibility(View.GONE);
            llDelete.setVisibility(View.GONE);
        }
    }
    private void showData() {
        CartStorage cartProvider = CartStorage.getInstance();

        List<GoodsInfo> datas = cartProvider.getAllData();
        if (datas != null && datas.size() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);
            adapter = new ShoppingCartAdapter(this, datas, tvShopcartTotal, cartProvider, checkboxAll, cbAll);
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            recyclerview.setAdapter(adapter);
        } else {
            //显示空的
            tvShopcartEdit.setVisibility(View.GONE);
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            llCheckAll.setVisibility(View.GONE);
            llDelete.setVisibility(View.GONE);
        }
    }


}
