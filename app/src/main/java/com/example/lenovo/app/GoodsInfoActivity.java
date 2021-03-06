package com.example.lenovo.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chenyu.library.Utils.AnimationUtils;
import com.chenyu.library.XToast;
import com.example.lenovo.app.Bean.RatingBean;
import com.example.lenovo.app.DB.RatingUserDao;
import com.example.lenovo.controller.activity.ChatActivity;
import com.example.lenovo.controller.activity.SettingDB.CollectDao;
import com.example.lenovo.home.bean.GoodsInfo;
import com.example.lenovo.home.utils.VirtualkeyboardHeight;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.shoppingcart.Activity.ShoppingCartActivity;
import com.example.lenovo.shoppingcart.utils.CartStorage;
import com.example.lenovo.shoppingcart.view.AddSubView;
import com.example.lenovo.utils.Constants;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.List;
import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;


public class GoodsInfoActivity extends Activity implements IUiListener {
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
    private TextView tv_more_talk;
    private TextView tv_more_home;
    private Button btn_more;
    private GoodsInfo goodsInfo;
    private GoodsInfo dataForView;
    private LinearLayout ll_root;
    private String enable;
    private CartStorage cartProvider;
    private String TAG;
    private CollectDao collectDao;
    private boolean showDanmaku;

    private DanmakuView danmakuView;

    private DanmakuContext danmakuContext;
    private Tencent mTencent;
    private RatingUserDao ratingUserDao;
    private String APP_ID = "1106333790";
    private int half = 1;

    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    private String position;

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
        tv_more_talk = findViewById(R.id.tv_more_search);
        tv_more_home = findViewById(R.id.tv_more_home);
        btn_more = findViewById(R.id.btn_more);

        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        initClickListener();
        intiName();

        danmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                danmakuView.start();
                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create().setScrollSpeedFactor(1.5f);
        /*DanmakuContext可以用于对弹幕的各种全局配置进行设定，如设置字体、设置最大显示行数等*/
        danmakuView.prepare(parser, danmakuContext);
    }

    private void intiName() {
        Random ra = new Random();
        int i = ra.nextInt(10000);
        if (i >= 5000) {
            tvGoodInfoStore.setText("胡*志提供发货");
            tvGoodInfoCallcenter.setText("联系学长");
            Drawable drawable = getResources().getDrawable(R.drawable.icon_callserver_unpressed);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            tvGoodInfoCallcenter.setCompoundDrawables(null, drawable, null, null);//画在top
            half = 1;
            /*public void  setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);*/
        } else {
            tvGoodInfoStore.setText("**大学-**社团提供发货");
            tvGoodInfoCallcenter.setText("联系社团");
            Drawable drawable = getResources().getDrawable(R.drawable.group);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            tvGoodInfoCallcenter.setCompoundDrawables(null, drawable, null, null);//画在top
            half = 2;

        }
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 20;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.BLACK;
        danmaku.setTime(/*danmakuView.getCurrentTime()*/4000);//显示时长 偏移时间
        Log.i("info", String.valueOf(danmakuView.getCurrentTime()));
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showDanmaku) {
                    int time = new Random().nextInt(300);
//                    String content = "" + <></>ime + time;
                    List<RatingBean> rate = ratingUserDao.getRate();
                    for (RatingBean ratingBean : rate) {
                        String content = ratingBean.getRatingWord();
                        addDanmaku(content, false);
                    }
//                    String content = "很好，商品很好！！！";
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
    }


    private void initClickListener() {
        ibGoodInfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle clicks for ibGoodInfoBack
                finish();
                overridePendingTransition(R.anim.slide_up_in,
                        R.anim.slide_down_out);
            }
        });
        ibGoodInfoMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle clicks for ibGoodInfoMore
                if (ll_root.getVisibility() == View.VISIBLE) {
                    ll_root.setVisibility(View.GONE);
                } else {
                    ll_root.setVisibility(View.VISIBLE);
                }
           /* Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();*/
            }
        });
        btnGoodInfoAddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle clicks for btnGoodInfoAddcart
//            CartStorage.getInstance().addData(goodsInfo);
//            Toast.makeText(this, "添加到成功了", Toast.LENGTH_SHORT).show();
                showPopwindow();
            }
        });

        tvGoodInfoCallcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         /*   Toast.makeText(this, "客服", Toast.LENGTH_SHORT).show();*/
                    /*设置点击事件跳转到会话页面*/
                if (half == 1) {
                    Intent intent = new Intent(GoodsInfoActivity.this, ChatActivity.class);
                    // 传递参数
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, "111");
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_up_in,
                            R.anim.push_up_out);
                } else if (half == 2) {
                    Intent intent1 = new Intent(GoodsInfoActivity.this, ChatActivity.class);
                    // 传递会话类型
                    intent1.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);

                    // 群id
                    EMGroup emGroup = EMClient.getInstance().groupManager().getAllGroups().get(1);
                    intent1.putExtra(EaseConstant.EXTRA_USER_ID, emGroup.getGroupId());

                    startActivity(intent1);
                    overridePendingTransition(R.anim.push_up_in,
                            R.anim.push_up_out);
                }
            }
        });
        tvGoodInfoCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (enable.equalsIgnoreCase("1")) {
//                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                    tvGoodInfoCollection.setText("已收藏");
                    tvGoodInfoCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                            ContextCompat.getDrawable(getBaseContext(), R.drawable.good_uncollected_selected), null, null);
                    enable = "0";
                    collectDao.addCollect(goodsInfo.getProduct_id(), goodsInfo.getName(), goodsInfo.getCover_price(), goodsInfo.getFigure(), enable);
                } else if (enable.equalsIgnoreCase("0")) {
//                Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
                    tvGoodInfoCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                            ContextCompat.getDrawable(getBaseContext(), R.drawable.good_uncollected), null, null);

                    tvGoodInfoCollection.setText("收藏");
                    enable = "1";
                    collectDao.updateById(goodsInfo.getProduct_id(), enable);

                }
            }
        });
        tvGoodInfoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//            Toast.makeText(this, "跳转购物车", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GoodsInfoActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.scale_rotate,
                        R.anim.my_alpha_action);
            }
        });

        tv_more_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(GoodsInfoActivity.this, "抱歉，功能暂未开放", Toast.LENGTH_SHORT).show();
                shareToQZone(view);

            }
        });
        tv_more_talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inteng = new Intent(GoodsInfoActivity.this, RatingActivity.class);
                inteng.putExtra("see", "no");
                inteng.putExtra("photo", goodsInfo.getFigure());
                startActivity(inteng);

            }
        });
        tv_more_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//            Toast.makeText(this, "主页", Toast.LENGTH_SHORT).show();
            /*起作用的主要是finish（）结束了当前的activity*/
//            Constants.isBackHome = true;
                finish();
            }
        });
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ll_root.getVisibility() == View.VISIBLE) {
                    ll_root.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-07-15 09:51:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_info);
        findViews();
        ratingUserDao = new RatingUserDao(getApplication());
        mTencent = Tencent.createInstance(APP_ID, getApplicationContext());

        collectDao = new CollectDao(getApplication());


        cartProvider = CartStorage.getInstance();
        /*接受数据*/
        goodsInfo = (GoodsInfo) getIntent().getSerializableExtra("goodsbean");
        position = getIntent().getStringExtra("position");
        setCollectPic();

        Log.e(TAG, "------" + goodsInfo);
        if (goodsInfo != null) {
//            Toast.makeText(this, "GoodsBean = " + goodsInfo.toString(),  Toast.LENGTH_SHORT).show();
            setDataForView(goodsInfo, position);
        }


    }

    private void setCollectPic() {
        enable = (collectDao.getGoodById(goodsInfo.getProduct_id())).getEnable();
        if (enable != null) {
            if (enable.equalsIgnoreCase("0")) {
//                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                tvGoodInfoCollection.setText("已收藏");
                tvGoodInfoCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                        ContextCompat.getDrawable(getBaseContext(), R.drawable.good_uncollected_selected), null, null);
//            enable = "1";
//            collectDao.addCollect(goodsInfo.getName(), goodsInfo.getCover_price(), goodsInfo.getFigure(),enable);
            } else if (enable.equalsIgnoreCase("1")) {
//                Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
                tvGoodInfoCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                        ContextCompat.getDrawable(getBaseContext(), R.drawable.good_uncollected), null, null);

                tvGoodInfoCollection.setText("收藏");
//            enable = "0";
//            collectDao.updateByName(goodsInfo.getName(),enable);

            }
        } else
            enable = "1";
        return;

    }


    /*设置数据*/
    public void setDataForView(GoodsInfo goodsInfo, String position) {
        try {
            if (position.equalsIgnoreCase("9")) {
                Bitmap bm = BitmapFactory.decodeFile(goodsInfo.getFigure());
                ivGoodInfoImage.setImageBitmap(bm);
                sc_image.setImageBitmap(bm);
            }
            Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsInfo.getFigure()).into(ivGoodInfoImage);
            Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsInfo.getFigure()).into(sc_image);
        } catch (Exception e) {
            /*设置图片*/
            Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsInfo.getFigure()).into(ivGoodInfoImage);
            Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsInfo.getFigure()).into(sc_image);
        }
        if (goodsInfo.getName() != null) {
        /*设置文本*/
            tvGoodInfoName.setText(goodsInfo.getName());
        }
        /*set price*/
        tvGoodInfoPrice.setText("$" + goodsInfo.getCover_price());

//        setWebViewData(goodsInfo.getProduct_id());

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
//                Toast.makeText(GoodsInfoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
                XToast.create(GoodsInfoActivity.this)
                        .setText("添加购物车成功！")
                        .setAnimation(AnimationUtils.ANIMATION_DRAWER) //Drawer Type
                        .setDuration(XToast.XTOAST_DURATION_LONG)
                        .setOnDisappearListener(new XToast.OnDisappearListener() {
                            @Override
                            public void onDisappear(XToast xToast) {
                                Log.d("cylog", "The XToast has disappeared..");
                            }
                        }).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        showDanmaku = false;
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }

    }

    @Override
    public void onComplete(Object o) {
//        Toast.makeText(this, o.toString(), Toast.LENGTH_LONG).show();
        XToast.create(GoodsInfoActivity.this)
                .setText("分享成功")
                .setAnimation(AnimationUtils.ANIMATION_DRAWER) //Drawer Type
                .setDuration(XToast.XTOAST_DURATION_LONG)
                .setOnDisappearListener(new XToast.OnDisappearListener() {
                    @Override
                    public void onDisappear(XToast xToast) {
                        Log.d("cylog", "The XToast has disappeared..");
                    }
                }).show();

    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(this, uiError.errorMessage + "--" + uiError.errorCode + "---" + uiError.errorDetail, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();

    }

    public void shareToQZone(View view) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "校淘商城");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, goodsInfo.getName());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, Constants.BASE_URL_IMAGE + goodsInfo.getFigure());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, Constants.BASE_URL_IMAGE + goodsInfo.getFigure());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "校  淘 APP");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(this, params, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mTencent != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /*  public void setWebViewData(String product_id) {
        if (product_id != null) {
            wbGoodInfoMore.loadUrl("#");

        }
    }*/
}
