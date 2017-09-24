package com.example.lenovo.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.lenovo.app.Bean.QQUser;
import com.example.lenovo.controller.activity.SettingDB.WantDao;
import com.example.lenovo.controller.activity.settingBean.WantBean;
import com.example.lenovo.home.adapter.CommentAdapter;
import com.example.lenovo.home.bean.Comment;
import com.example.lenovo.home.bean.EssayBean;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.Constants;
import com.example.lenovo.utils.QQURL;
import com.hyphenate.chat.EMClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;

public class WantDetailActivity extends Activity implements View.OnClickListener {

    private ImageButton ib_back;
    private TextView tv_page;
    private TextView tv_title;
    private ImageView iv_one;
    private TextView tv_one;
    private ImageView iv_two;
    private TextView tv_two;
    private ImageView iv_three;
    private TextView tv_three;
    // 声明一个Handler对象
    private static Handler handler = new Handler();
    private Integer kind;
    private Integer postion;
    private List<EssayBean.ResultBean.ErshouBean> ershouBeenList;
    private List<EssayBean.ResultBean.OldNewBean> oldNewBeanList;
    private List<EssayBean.ResultBean.NewSellBean> newSellBeanList;
    private List<EssayBean.ResultBean.DaiGouBean> daiGouBeanList;
    private EssayBean.ResultBean.ErshouBean ershouBean;
    private EssayBean.ResultBean.OldNewBean oldNewBean;
    private EssayBean.ResultBean.NewSellBean newSellBean;
    private EssayBean.ResultBean.DaiGouBean daiGouBean;
    private TextView tv_user;
    private TextView tv_date;
    private ImageView btn_send;
    private RecyclerView lv_comm;
    private AppCompatEditText bt_talk;
    private CommentAdapter adapter;
    private List<Comment> commentList;
    private String currentUser;
    private QQUser.UserBean userBean;
    private WantDao wantDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_want_detail);
        Intent intent = getIntent();
        kind = Integer.valueOf(intent.getStringExtra("kind"));
        postion = Integer.valueOf(intent.getStringExtra("postion"));
        handler1.sendMessageDelayed(Message.obtain(), 0);
        wantDao = new WantDao(getApplication());
        handler();

        initView();
    }

    private void handler() {
         /*如果当前activity已经退出，那么不处理handler中的消息*/
        new Thread(new Runnable() {


            @Override
            public void run() {
                // tvMessage.setText("...");
                // 以上操作会报错，无法再子线程中访问UI组件，UI组件的属性必须在UI线程中访问
                // 使用post方式修改UI组件tvMessage的Text属性
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String url = Constants.ESSAY_INFO;
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

                                        Log.e("info", "首页请求失败==" + e.getMessage());
                                    }

                                    /*当联网成功回调数据，respond请求成功的数据回调*/
                                    @Override
                                    public void onResponse(String response, int id) {
                                        Log.e("info", "请求成功==" + response);
                                        //解析数据
                                        procssData(response);
                                    }
                                });
                    }
                });
            }
        }).start();
    }

    private void procssData(String json) {
        EssayBean resultBeanList = JSON.parseObject(json, EssayBean.class);
        if (kind == 0) {
            ershouBeenList = resultBeanList.getResult().getErshou();
            if (postion < 6) {
                ershouBean = ershouBeenList.get(postion);
                tv_title.setText(ershouBean.getTitle());
                tv_date.setText(ershouBean.getTime());
                tv_user.setText("来自"+ershouBean.getUser());
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(0)).into(iv_one);
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(1)).into(iv_two);
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(2)).into(iv_three);
                tv_one.setText(ershouBean.getEssay());
                tv_two.setText(ershouBean.getEssay1());
                tv_three.setText(ershouBean.getEssay2());
            } else {
                List<WantBean> wants = wantDao.getWants("0");
                WantBean wantBeen = wants.get(postion - 6);
                tv_title.setText(wantBeen.getTitle());
                tv_date.setText(wantBeen.getTime());
                tv_user.setText("来自"+wantBeen.getUser());
                Bitmap bm = BitmapFactory.decodeFile(wantBeen.getUrl());
                iv_one.setImageBitmap(bm);
                Bitmap bm1 = BitmapFactory.decodeFile(wantBeen.getUrl1());
                iv_two.setImageBitmap(bm1);
                Bitmap bm2 = BitmapFactory.decodeFile(wantBeen.getUrl2());
                iv_three.setImageBitmap(bm2);
//                Glide.with(getApplicatio2n()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(0)).into(iv_one);
//                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(1)).into(iv_two);
//                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(2)).into(iv_three);
                tv_one.setText(wantBeen.getEssay());
                tv_two.setText(wantBeen.getEssay1());
                tv_three.setText(wantBeen.getEssay2());
            }


        } else if (kind == 1) {
            oldNewBeanList = resultBeanList.getResult().getOldNew();
            if (postion < 6) {
                oldNewBean = oldNewBeanList.get(postion);
                tv_title.setText(oldNewBean.getTitle());
                tv_date.setText(oldNewBean.getTime());
                tv_user.setText("来自"+oldNewBean.getUser());
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + oldNewBean.getUrl().get(0)).into(iv_one);
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + oldNewBean.getUrl().get(1)).into(iv_two);
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + oldNewBean.getUrl().get(2)).into(iv_three);
                tv_one.setText(oldNewBean.getEssay());
                tv_two.setText(oldNewBean.getEssay1());
                tv_three.setText(oldNewBean.getEssay2());
            } else {
                List<WantBean> wants = wantDao.getWants("1");
                WantBean wantBeen = wants.get(postion - 6);
                tv_title.setText(wantBeen.getTitle());
                tv_date.setText(wantBeen.getTime());
                tv_user.setText("来自"+wantBeen.getUser());
                Bitmap bm = BitmapFactory.decodeFile(wantBeen.getUrl());
                iv_one.setImageBitmap(bm);
                Bitmap bm1 = BitmapFactory.decodeFile(wantBeen.getUrl1());
                iv_two.setImageBitmap(bm1);
                Bitmap bm2 = BitmapFactory.decodeFile(wantBeen.getUrl2());
                iv_three.setImageBitmap(bm2);
//                Glide.with(getApplicatio2n()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(0)).into(iv_one);
//                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(1)).into(iv_two);
//                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(2)).into(iv_three);
                tv_one.setText(wantBeen.getEssay());
                tv_two.setText(wantBeen.getEssay1());
                tv_three.setText(wantBeen.getEssay2());
            }

        } else if (kind == 2) {
            newSellBeanList = resultBeanList.getResult().getNewSell();

            if (postion < 6) {
                newSellBean = newSellBeanList.get(postion);
                tv_title.setText(newSellBean.getTitle());
                tv_date.setText(newSellBean.getTime());
                tv_user.setText("来自"+newSellBean.getUser());
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + newSellBean.getUrl().get(0)).into(iv_one);
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + newSellBean.getUrl().get(1)).into(iv_two);
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + newSellBean.getUrl().get(2)).into(iv_three);
                tv_one.setText(newSellBean.getEssay());
                tv_two.setText(newSellBean.getEssay1());
                tv_three.setText(newSellBean.getEssay2());
            } else {
                List<WantBean> wants = wantDao.getWants("2");
                WantBean wantBeen = wants.get(postion - 6);
                tv_title.setText(wantBeen.getTitle());
                tv_date.setText(wantBeen.getTime());
                tv_user.setText("来自"+wantBeen.getUser());
                Bitmap bm = BitmapFactory.decodeFile(wantBeen.getUrl());
                iv_one.setImageBitmap(bm);
                Bitmap bm1 = BitmapFactory.decodeFile(wantBeen.getUrl1());
                iv_two.setImageBitmap(bm1);
                Bitmap bm2 = BitmapFactory.decodeFile(wantBeen.getUrl2());
                iv_three.setImageBitmap(bm2);
//                Glide.with(getApplicatio2n()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(0)).into(iv_one);
//                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(1)).into(iv_two);
//                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(2)).into(iv_three);
                tv_one.setText(wantBeen.getEssay());
                tv_two.setText(wantBeen.getEssay1());
                tv_three.setText(wantBeen.getEssay2());
            }

        } else if (kind == 3) {
            daiGouBeanList = resultBeanList.getResult().getDaiGou();

            if (postion < 6) {
                daiGouBean = daiGouBeanList.get(postion);
                tv_title.setText(daiGouBean.getTitle());
                tv_date.setText(daiGouBean.getTime());
                tv_user.setText("来自"+daiGouBean.getUser());
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + daiGouBean.getUrl().get(0)).into(iv_one);
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + daiGouBean.getUrl().get(1)).into(iv_two);
                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + daiGouBean.getUrl().get(2)).into(iv_three);
                tv_one.setText(daiGouBean.getEssay());
                tv_two.setText(daiGouBean.getEssay1());
                tv_three.setText(daiGouBean.getEssay2());
            } else {
                List<WantBean> wants = wantDao.getWants("3");
                WantBean wantBeen = wants.get(postion - 6);
                tv_title.setText(wantBeen.getTitle());
                tv_date.setText(wantBeen.getTime());
                tv_user.setText("来自"+wantBeen.getUser());
                Bitmap bm = BitmapFactory.decodeFile(wantBeen.getUrl());
                iv_one.setImageBitmap(bm);
                Bitmap bm1 = BitmapFactory.decodeFile(wantBeen.getUrl1());
                iv_two.setImageBitmap(bm1);
                Bitmap bm2 = BitmapFactory.decodeFile(wantBeen.getUrl2());
                iv_three.setImageBitmap(bm2);
//                Glide.with(getApplicatio2n()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(0)).into(iv_one);
//                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(1)).into(iv_two);
//                Glide.with(getApplication()).load(Constants.BASE_URL_IMAGE + ershouBean.getUrl().get(2)).into(iv_three);
                tv_one.setText(wantBeen.getEssay());
                tv_two.setText(wantBeen.getEssay1());
                tv_three.setText(wantBeen.getEssay2());
            }

        }


    }

    private void initView() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        tv_page = (TextView) findViewById(R.id.tv_page);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_one = (ImageView) findViewById(R.id.iv_one);
        tv_one = (TextView) findViewById(R.id.tv_one);
        lv_comm = findViewById(R.id.lv_comm);
        iv_two = (ImageView) findViewById(R.id.iv_two);
        tv_two = (TextView) findViewById(R.id.tv_two);
        iv_three = (ImageView) findViewById(R.id.iv_three);
        tv_three = (TextView) findViewById(R.id.tv_three);
        btn_send = findViewById(R.id.btn_send);
        ib_back.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        tv_user = (TextView) findViewById(R.id.tv_user);
        tv_date = (TextView) findViewById(R.id.tv_date);
        bt_talk = findViewById(R.id.bt_talk);

        initAdapter();


    }

    private void initAdapter() {
        commentList = new ArrayList<>();
        Comment comment = new Comment("太阳晚安","2017/04/13","这篇文章很好，十五字好评。五星好评",0,"http://q.qlogo.cn/qqapp/1106333790/AFD0ACA5BD7E87C1ED3481625CA6EACC/100");
        Comment comment1 = new Comment("胡明志的胡明志","2017/04/13","这篇文章很好，对我帮助很大",0,"http://101.201.234.133:8080/AndroidCon/src/main/webapp/img/4800.jpg");
        Comment comment3 = new Comment("小可爱","2017/05/23","十五字好评，十五字好评，十五字好评，十五字好评",0,"http://101.201.234.133:8080/AndroidCon/src/main/webapp/img/113.jpg");
        Comment comment4 = new Comment("小哥哥","2017/06/03","写的很好，赞一个",0,"http://101.201.234.133:8080/AndroidCon/src/main/webapp/img/114.jpg");

        commentList.add(comment);
        commentList.add(comment1);
        commentList.add(comment3);
        commentList.add(comment4);
        //设置适配器
        adapter = new CommentAdapter(getApplication(), commentList);
        GridLayoutManager manager = new GridLayoutManager(getApplication(), 1);
        lv_comm.setAdapter(adapter);
            /*设置布局管理者*/
        lv_comm.setLayoutManager(manager);

    }

    //获得用户名
    private Handler handler1 = new Handler() {
        public void handleMessage(Message message) {
            getDataFromMy();

        }
    };
    private void getDataFromMy() {
        currentUser = EMClient.getInstance().getCurrentUser();
        String url = QQURL.GETONEINFO;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("hxid", currentUser)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        setMes(response);
                        Log.i("info", "成功获取数据：" + response);
                    }

                });
    }

    private void setMes(String json) {
        userBean = JSON.parseObject(json, QQUser.UserBean.class);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_send:
//                Toast.makeText(this, "send" + bt_talk.getText(), Toast.LENGTH_SHORT).show();
                send();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(btn_send.getApplicationWindowToken(), 0);
                }
                break;
        }
    }


    private void send() {
        if(bt_talk.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
        }else{
            // 生成评论数据
            Comment comment = new Comment();
            comment.setUserName(userBean.getNickname()+":");
            comment.setComm(bt_talk.getText().toString());
            comment.setNumber(0);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int date = c.get(Calendar.DATE);
            comment.setTime(year+"/"+month+"/"+date);
            comment.setUrl(userBean.getPhoto());

            adapter.addComment(comment);
            // 发送完，清空输入框
            bt_talk.setText("");
//            Toast.makeText(getApplicationContext(), "评论成功！", Toast.LENGTH_SHORT).show();
        }
    }
}
