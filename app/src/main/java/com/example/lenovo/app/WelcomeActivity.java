package com.example.lenovo.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.example.lenovo.model.Model;
import com.example.lenovo.model.bean.UserInfo;
import com.example.lenovo.myapplication.R;
import com.hyphenate.chat.EMClient;
import com.youth.banner.Banner;

public class WelcomeActivity extends Activity {

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {

            /*如果当前activity已经退出，那么不处理handler中的消息*/
            if (isFinishing()) {
                return;
            }
            //判断进入主页面还是登录页面
            toMainOrLoginIn();

        }
    };
    private Banner banner;


    //判断进入主页面还是登录页面
    private void toMainOrLoginIn() {
/*        new Thread() {
            public void run() {
            }
        }.start();*/
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                   /*判断当前账户是否登录过*/
                if (EMClient.getInstance().isLoggedInBefore()) {
                    /*获取当前登录用户的信息*/
                    UserInfo account = Model.getInstance().getUserAccountDao().getAccountByHxId(EMClient.getInstance().getCurrentUser());
                    if (account == null) {
                         /*跳转到登录页面*/
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        // 登录成功后的方法
                        Model.getInstance().loginSuccess(account);
                         /*登陆过*/
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    /*没登陆过,跳转到登录页面*/
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                /*结束当前页面*/
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        PlayView();
//        initData();
        //发送延时消息
        handler.sendMessageDelayed(Message.obtain(), 2000);

    }
    /*   //两秒进入主页面
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   //执行主线程
                   //启动主页面
                   startActivity(new ntent(WelcomeActivity.this,MainActivity.class));
                   //关闭当前页面I
                   finish();
               }
           },2000);*/
    private void PlayView() {
        ImageView ivAdam = (ImageView) findViewById(R.id.ivAdam);
        AnimationDrawable anim = (AnimationDrawable) ivAdam.getBackground();// 获取到动画资源
        anim.setOneShot(false); // 设置是否重复播放
        anim.start();// 开始动画
    }

 /*   private void initData() {
        banner = findViewById(R.id.banner);
         List<String> url = new ArrayList<>();

        String imageUrl_1 = "/1478770583834.png";
        url.add(imageUrl_1);
        String imageUrl_2 = "/1478770583835.png";
        url.add(imageUrl_2);
        String imageUrl_3 = "/1478770583836.png";
        url.add(imageUrl_3);
            *//*设置循环指示点*//*
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            *//*设置动画效果*//*
        banner.setBannerAnimation(Transformer.Accordion);
            *//*加载回调图片*//*
        banner.setImages(url, new OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                    *//*联网请求图片--Glide*//*
                Glide.with(WelcomeActivity.this).load(Constants.BASE_URL_IMAGE + url).into(view);
            }
        });
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁
        handler.removeCallbacksAndMessages(null);
    }


}
