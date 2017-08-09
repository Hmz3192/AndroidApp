package com.example.lenovo.home.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chenyu.library.Utils.AnimationUtils;
import com.chenyu.library.XToast;
import com.example.lenovo.app.GoodsInfoActivity;
import com.example.lenovo.base.BaseFragment;
import com.example.lenovo.home.activity.MessageCenterActivity;
import com.example.lenovo.home.adapter.HomeFragmentAdapter;
import com.example.lenovo.home.bean.GoodsInfo;
import com.example.lenovo.home.bean.ResultBeanData;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Random;

import okhttp3.Call;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Hmz on 2017/7/5.
 * 主页面
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView rvHome;
    private ImageView ib_top;
    private EditText tv_search_home;
    private TextView tv_message_home;
    private HomeFragmentAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    /*返回的数据*/
    private ResultBeanData.ResultBean resultBean;


    @Override
    public View initView() {
        Log.e(TAG, "主页视图被初始化了");
        View view = View.inflate(mcontext, R.layout.fragment_home, null);
        rvHome = view.findViewById(R.id.rv_home);
        ib_top = view.findViewById(R.id.ib_top);
        tv_search_home = view.findViewById(R.id.tv_search_home);
        tv_message_home = view.findViewById(R.id.tv_message_home);
        mSwipeLayout =  view.findViewById(R.id.swipe_container);
        //设置点击事件
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "主页数据被初始化了");
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
  /*   String url = "http://www.csdn.net/";*/
  /*String url = "http://101.201.234.133:8080/index.jsp";*/
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    /*当请求失败的时候*/
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Log.e(TAG, "首页请求失败==" + e.getMessage());
                    }

                    /*当联网成功回调数据，respond请求成功的数据回调*/
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "请求成功==" + response);
                        //解析数据
                        procssData(response);
                    }
                });
    }

    private void procssData(String json) {
        ResultBeanData resultBeanData = JSON.parseObject(json, ResultBeanData.class);
        resultBean = resultBeanData.getResult();
        if (resultBean != null) {
            //有数据
            //设置适配器
            adapter = new HomeFragmentAdapter(mcontext, resultBean);
            rvHome.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(mcontext, 1);
            /*设置跨度大小的监听*/
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position <= 3) {
                        /*隐藏*/
                        ib_top.setVisibility(View.GONE);
                    } else {
                        ib_top.setVisibility(View.VISIBLE);
                        /*显示*/
                    }
                    /*只能返回1*/
                    return 1;
                }
            });

            /*设置布局管理者*/
            rvHome.setLayoutManager(manager);


        } else {
            //没有数据
            Toast.makeText(mcontext, "no data", Toast.LENGTH_LONG).show();
        }
//        Log.e(TAG, "解析对象==" + resultBean.getHot_info().get(0).getName());


    }

    private void initListener() { //置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到顶部
                rvHome.scrollToPosition(0);
            }
        });


        tv_search_home.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                InputMethodManager imm  = (InputMethodManager) mcontext.getSystemService(INPUT_METHOD_SERVICE);
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏软键盘
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(tv_search_home.getApplicationWindowToken(), 0);
                    }

//                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    if (TextUtils.isEmpty(tv_search_home.getText().toString())) {
                        XToast.create(getActivity())
                                .setText("输入为空，请重新输入！")
                                .setAnimation(AnimationUtils.ANIMATION_DRAWER) //Drawer Type
                                .setDuration(XToast.XTOAST_DURATION_LONG)
                                .setOnDisappearListener(new XToast.OnDisappearListener() {
                                    @Override
                                    public void onDisappear(XToast xToast) {
                                        Log.d("cylog", "The XToast has disappeared..");
                                    }
                                }).show();
                      /*  XToast.create(getActivity(),"你收到了 1 条新消息..",XToast.XTOAST_TYPE_BOTTOM)
                                .setButtonOnClickListener(new XToast.OnButtonClickListener() {
                                    @Override
                                    public void click(XToast xToast) {
                                        Log.d("cylog","The button has been clicked.");
                                    }
                                })
                                .setButtonText("取消")
                                .show();*/
//                        Toast.makeText(mcontext, "请重新输入", Toast.LENGTH_SHORT).show();

                        return false;
                    }
                    onSearch(tv_search_home.getText().toString());

                }
                return false;
            }
        });
       /* //搜素的监听
        tv_search_home.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    onSearch(tv_search_home.getText().toString());
                }
                return true;
            }
        });*/

        //消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mcontext, "消息中心功能暂未开放", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mcontext, MessageCenterActivity.class);
                mcontext.startActivity(intent);
            }
        });

        mSwipeLayout.setOnRefreshListener(this);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(android.R.color.holo_red_light); // 设定下拉圆圈的背景
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE); // 设置圆圈的大小
    }

    /*进行搜索操作*/
    public void onSearch(String text) {
        String[] array = new String[]{"/supplier/1471315793182.jpg",
                "/supplier/1469436115002.jpg",
                "/1477360350123.png",
                "/1438680345318.jpg",
                "/1477984931882.jpg",
                "/1477984921265.jpg",
                "/1474370572805.jpg",
                "/1475045805488.jpg"};

        String[] array_num = new String[]{"67677",
                "4545",
                "345355",
                "6768",
                "77878",
                "56565",
                "4546",
                "7557"};
        Random random = new Random();
        String price = String.valueOf(random.nextInt(300));
        int i = random.nextInt(8);

        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setName(text);
        goodsInfo.setCover_price(price);
        goodsInfo.setFigure(array[i]);
        goodsInfo.setProduct_id(array_num[i]);
        Intent intent = new Intent(mcontext, GoodsInfoActivity.class);
        intent.putExtra("goodsbean", goodsInfo);
        mcontext.startActivity(intent);
    }


    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                XToast.create(getActivity())
                        .setText("刷新成功！")
                        .setAnimation(AnimationUtils.ANIMATION_DRAWER) //Drawer Type
                        .setDuration(XToast.XTOAST_DURATION_LONG)
                        .setOnDisappearListener(new XToast.OnDisappearListener() {
                            @Override
                            public void onDisappear(XToast xToast) {
                                Log.d("cylog", "The XToast has disappeared..");
                            }
                        }).show();
                // 停止刷新
                mSwipeLayout.setRefreshing(false);

            }
        }, 2000); // 2秒后发送消息，停止刷新
    }


}
