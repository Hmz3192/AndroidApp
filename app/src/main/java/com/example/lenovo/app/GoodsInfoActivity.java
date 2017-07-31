package com.example.lenovo.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.controller.activity.ChatActivity;
import com.example.lenovo.home.bean.GoodsInfo;
import com.example.lenovo.home.utils.VirtualkeyboardHeight;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.shoppingcart.Activity.ShoppingCartActivity;
import com.example.lenovo.shoppingcart.utils.CartStorage;
import com.example.lenovo.shoppingcart.view.AddSubView;
import com.example.lenovo.utils.Constants;
import com.hyphenate.easeui.EaseConstant;

public class GoodsInfoActivity extends FragmentActivity implements View.OnClickListener {
    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage, sc_image;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private TextView tv_more_share;
    private TextView tv_more_search;
    private TextView tv_more_home;
    private Button btn_more;
    private GoodsInfo goodsInfo;
    private GoodsInfo dataForView;
    private LinearLayout ll_root;
    private int enable = 0;
    private CartStorage cartProvider;
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-07-15 09:51:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ibGoodInfoBack = (ImageButton) findViewById(R.id.ib_good_info_back);
        ibGoodInfoMore = (ImageButton) findViewById(R.id.ib_good_info_more);
        ivGoodInfoImage = (ImageView) findViewById(R.id.iv_good_info_image);
        sc_image = (ImageView) findViewById(R.id.sc_image);
        tvGoodInfoName = (TextView) findViewById(R.id.tv_good_info_name);
        tvGoodInfoDesc = (TextView) findViewById(R.id.tv_good_info_desc);
        tvGoodInfoPrice = (TextView) findViewById(R.id.tv_good_info_price);
        tvGoodInfoStore = (TextView) findViewById(R.id.tv_good_info_store);
        tvGoodInfoStyle = (TextView) findViewById(R.id.tv_good_info_style);
        llGoodsRoot = (LinearLayout) findViewById(R.id.ll_goods_root);
        tvGoodInfoCallcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);
        tvGoodInfoCollection = (TextView) findViewById(R.id.tv_good_info_collection);
        tvGoodInfoCart = (TextView) findViewById(R.id.tv_good_info_cart);
        btnGoodInfoAddcart = (Button) findViewById(R.id.btn_good_info_addcart);
        tv_more_share = findViewById(R.id.tv_more_share);
        tv_more_search = findViewById(R.id.tv_more_search);
        tv_more_home = findViewById(R.id.tv_more_home);
        btn_more = findViewById(R.id.btn_more);

        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);

        tvGoodInfoCallcenter.setOnClickListener(this);
        tvGoodInfoCollection.setOnClickListener(this);
        tvGoodInfoCart.setOnClickListener(this);

        tv_more_share.setOnClickListener(this);
        tv_more_search.setOnClickListener(this);
        tv_more_home.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-07-15 09:51:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {

        if (v == ibGoodInfoBack) {
            // Handle clicks for ibGoodInfoBack
            finish();
        } else if (v == ibGoodInfoMore) {
            // Handle clicks for ibGoodInfoMore
            if (ll_root.getVisibility() == View.VISIBLE) {
                ll_root.setVisibility(View.GONE);
            } else {
                ll_root.setVisibility(View.VISIBLE);
            }
           /* Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();*/
        } else if (v == btnGoodInfoAddcart) {
            // Handle clicks for btnGoodInfoAddcart
//            CartStorage.getInstance().addData(goodsInfo);
//            Toast.makeText(this, "添加到成功了", Toast.LENGTH_SHORT).show();
            showPopwindow();
        } else if (v == tvGoodInfoCallcenter) {
         /*   Toast.makeText(this, "客服", Toast.LENGTH_SHORT).show();*/
            tvGoodInfoCallcenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*设置点击事件跳转到会话页面*/
                    Intent intent = new Intent(GoodsInfoActivity.this, ChatActivity.class);

                    // 传递参数
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, "admin");

                    startActivity(intent);
                }
            });
        } else if (v == tvGoodInfoCollection) {
            if (enable == 0) {
//                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                tvGoodInfoCollection.setText("已收藏");
                enable = 1;
            } else if (enable == 1) {
//                Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
                tvGoodInfoCollection.setText("收藏");
                enable = 0;
            }
        } else if (v == tvGoodInfoCart) {
//            Toast.makeText(this, "跳转购物车", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
        } else if (v == tv_more_share) {
            Toast.makeText(this, "抱歉，功能暂未开放", Toast.LENGTH_SHORT).show();
        } else if (v == tv_more_search) {
            Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
        } else if (v == tv_more_home) {
//            Toast.makeText(this, "主页", Toast.LENGTH_SHORT).show();
            /*起作用的主要是finish（）结束了当前的activity*/
//            Constants.isBackHome = true;
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        findViews();

        cartProvider = CartStorage.getInstance();
        /*接受数据*/
        goodsInfo = (GoodsInfo) getIntent().getSerializableExtra("goodsbean");
        if (goodsInfo != null) {
//            Toast.makeText(this, "GoodsBean = " + goodsInfo.toString(),  Toast.LENGTH_SHORT).show();
            setDataForView(goodsInfo);
        }


    }


    /*设置数据*/
    public void setDataForView(GoodsInfo goodsInfo) {
        /*设置图片*/
        Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsInfo.getFigure()).into(ivGoodInfoImage);
        if (goodsInfo.getName() != null) {
        /*设置文本*/
            tvGoodInfoName.setText(goodsInfo.getName());
        }
        /*set price*/
        tvGoodInfoPrice.setText("$" + goodsInfo.getCover_price());

//        setWebViewData(goodsInfo.getProduct_id());
        Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsInfo.getFigure()).into(sc_image);

    }

    /*添加购物车淡出菜单*/
    private void showPopwindow() {
        // 1 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_add_product, null);

        // 2 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 3 参数设置
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);

        // 4 控件处理
        ImageView iv_goodinfo_photo = (ImageView) view.findViewById(R.id.iv_goodinfo_photo);
        TextView tv_goodinfo_name = (TextView) view.findViewById(R.id.tv_goodinfo_name);
        TextView tv_goodinfo_price = (TextView) view.findViewById(R.id.tv_goodinfo_price);
        AddSubView nas_goodinfo_num = (AddSubView) view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = (Button) view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = (Button) view.findViewById(R.id.bt_goodinfo_confim);

        // 加载图片
        Glide.with(GoodsInfoActivity.this).load(Constants.BASE_URL_IMAGE + goodsInfo.getFigure()).into(iv_goodinfo_photo);
        // 名称
        tv_goodinfo_name.setText(goodsInfo.getName());
        // 显示价格
        tv_goodinfo_price.setText(goodsInfo.getCover_price());
        // 设置最大值和当前值
        nas_goodinfo_num.setMaxvalue(8);
        nas_goodinfo_num.setValue(1);

        nas_goodinfo_num.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {
            @Override
            public void OnNumberchanger(int value) {
                goodsInfo.setNumber(value);
            }
        });

        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                //添加购物车
                cartProvider.addData(goodsInfo);
                Log.e("TAG", "66:" + goodsInfo.toString());
                Toast.makeText(GoodsInfoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });

        // 5 在底部显示
        window.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.ll_goods_root),
                Gravity.BOTTOM, 0, VirtualkeyboardHeight.getBottomStatusHeight(GoodsInfoActivity.this));

    }

  /*  public void setWebViewData(String product_id) {
        if (product_id != null) {
            wbGoodInfoMore.loadUrl("#");

        }
    }*/
}
