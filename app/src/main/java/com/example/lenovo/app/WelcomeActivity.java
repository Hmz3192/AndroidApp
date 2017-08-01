package com.example.lenovo.app;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.CustomVideoView;
import com.example.lenovo.viewPage.fragment.BaseFragmentAdapter;
import com.example.lenovo.viewPage.fragment.LauncherBaseFragment;
import com.example.lenovo.viewPage.fragment.PrivateMessageLauncherFragment;
import com.example.lenovo.viewPage.fragment.RewardLauncherFragment;
import com.example.lenovo.viewPage.fragment.StereoscopicLauncherFragment;
import com.example.lenovo.viewPage.view.GuideViewPager;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends FragmentActivity implements View.OnClickListener{

    private CustomVideoView videoview;
    private TextView tv_login;
    private TextView tv_registe,tv_app,bt_login;
    private RelativeLayout rl_view;


    private GuideViewPager vPager;
    private List<LauncherBaseFragment> list = new ArrayList<LauncherBaseFragment>();
    private BaseFragmentAdapter adapter;

    private ImageView[] tips;
    private int currentSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        /*初始化点点*/
        initGroup();

        //获取自定义viewpager 然后设置背景图片
        vPager = (GuideViewPager) findViewById(R.id.viewpager_launcher1);
        vPager.setBackGroud(BitmapFactory.decodeResource(getResources(),R.drawable.bg_kaka_launcher));

        /**
         * 初始化三个fragment  并且添加到list中
         */
        initFragment();
        initView();
        playVedio();

    }
    /**
     * 初始化三个fragment  并且添加到list中
     */
    private void initFragment() {
        RewardLauncherFragment rewardFragment = new RewardLauncherFragment();
        PrivateMessageLauncherFragment privateFragment = new PrivateMessageLauncherFragment();
        StereoscopicLauncherFragment stereoscopicFragment = new StereoscopicLauncherFragment();
        list.add(rewardFragment);
        list.add(privateFragment);
        list.add(stereoscopicFragment);

        adapter = new BaseFragmentAdapter(getSupportFragmentManager(),list);
        vPager.setAdapter(adapter);
        vPager.setOffscreenPageLimit(2);
        vPager.setCurrentItem(0);
        vPager.setOnPageChangeListener(changeListener);
    }


    /**
     * 监听viewpager的移动
     */
    ViewPager.OnPageChangeListener changeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int index) {
            setImageBackground(index);//改变点点点的切换效果
            LauncherBaseFragment fragment=list.get(index);

            list.get(currentSelect).stopAnimation();//停止前一个页面的动画
            fragment.startAnimation();//开启当前页面的动画

            currentSelect=index;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}
        @Override
        public void onPageScrollStateChanged(int arg0) {}
    };



    /**
     * 改变点点点的切换效果
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }

    private void initGroup() {
        //初始化点点点控件
        ViewGroup group = (ViewGroup)findViewById(R.id.viewGroup);
        tips = new ImageView[3];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageView.setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            tips[i]=imageView;

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 20;//设置点点点view的左边距
            layoutParams.rightMargin = 20;//设置点点点view的右边距
            group.addView(imageView,layoutParams);
        }
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        playVedio();
    }

    private void initView() {
        rl_view = findViewById(R.id.rl_view);
        tv_login = findViewById(R.id.tv_login_first);
        bt_login = findViewById(R.id.bt_login_seconed);
//        tv_registe = findViewById(R.id.bt_regist);
        videoview =  findViewById(R.id.videoview);
        tv_app = findViewById(R.id.tv_app);

        bt_login.setOnClickListener(this);
        tv_login.setOnClickListener(this);
//        tv_registe.setOnClickListener(this);

    }

    private void playVedio() {
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+ R.raw.landing));
        //播放
        videoview.start();
        /*视屏播放结束后，APP设置为不可见，*/
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                tv_app.setVisibility(View.GONE);
                tv_login.setVisibility(View.GONE);
                rl_view.setVisibility(View.VISIBLE);

            }
        });
        //循环播放
        /*videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_first:
                videoview.pause();
                tv_app.setVisibility(View.GONE);
                tv_login.setVisibility(View.GONE);
                rl_view.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_login_seconed:
                Intent intent1 = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;

          /*  case R.id.bt_regist:
                Toast.makeText(this, "不要再点了，注册没有页面的，去登陆页面注册", Toast.LENGTH_SHORT).show();
                break;*/

        }
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
   /* private void PlayView() {
        ImageView ivAdam = (ImageView) findViewById(R.id.ivAdam);
        AnimationDrawable anim = (AnimationDrawable) ivAdam.getBackground();// 获取到动画资源
        anim.setOneShot(false); // 设置是否重复播放
        anim.start();// 开始动画
    }*/

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




}
