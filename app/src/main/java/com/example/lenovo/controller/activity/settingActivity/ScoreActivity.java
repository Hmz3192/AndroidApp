package com.example.lenovo.controller.activity.settingActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.AnimationUtils;
import com.example.lenovo.utils.LeafLoadingView;
import com.github.clans.fab.FloatingActionButton;

public class ScoreActivity extends Activity {
    private static final int REFRESH_PROGRESS = 0x10;
    private int mProgress = 0;
    private LeafLoadingView mLeafLoadingView;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_PROGRESS:
                    mProgress = 10;
//                    if (mProgress < 40) {
//                        mProgress += 1;
//                        // 随机800ms以内刷新一次
//                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
//                                new Random().nextInt(800));
//                        mLeafLoadingView.setProgress(mProgress);
//                    } else {
//                        mProgress += 1;
//                        // 随机1200ms以内刷新一次
//                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
//                                new Random().nextInt(1200));
//                        mLeafLoadingView.setProgress(mProgress);
//
//                    }
                    break;

                default:
                    break;
            }
        };
    };

    private ImageView sco_back_user;
    private ImageView iv_quest,fan_pic;
    private TextView tv_score;
    private FloatingActionButton menu_add,menu_del;
    private int score = 327;
    private int scoreNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_score);
        mLeafLoadingView = findViewById(R.id.leaf_loading);
        iv_quest = findViewById(R.id.iv_quest);
        menu_add = findViewById(R.id.menu_add);
        menu_del = findViewById(R.id.menu_del);
        tv_score = findViewById(R.id.tv_score);

        tv_score.setText(String.valueOf(score));


        fan_pic = findViewById(R.id.fan_pic);
        RotateAnimation rotateAnimation = AnimationUtils.initRotateAnimation(false, 1500, true,
                Animation.INFINITE);
        fan_pic.startAnimation(rotateAnimation);


        sco_back_user = findViewById(R.id.sco_back_user);
        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 3000);
        initListener();



    }

    private void initListener() {
        sco_back_user.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });


        menu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scoreNew = score - 1;
                mProgress = mProgress + 2;
                mLeafLoadingView.setProgress(mProgress);
                tv_score.setText(String.valueOf(scoreNew));
                score = scoreNew;

            }
        });

        menu_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scoreNew = score - 10;
                mProgress = mProgress + 10;
                mLeafLoadingView.setProgress(mProgress);
                tv_score.setText(String.valueOf(score-10));

                score = scoreNew;


            }
        });


        iv_quest.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ScoreActivity.this);
                builder.setMessage("        我的鱼塘是校淘商城APP的" +
                        "一款自主研发动画引擎的轻应用" +
                        "用户可以通过如下方式获取积分" +
                        "*：通过在商城中购物，获取积分" +
                        "*：达成商城隐藏任务，获取积分" +

                        "*：完成“最后一公里”送货至寝活" +
                        "        动，获取积分                          " +
                        "" +
                        "用户可以通过积分来购买喂养的小" +
                        "鲤鱼饲料。");
                builder.setIcon(android.R.drawable.btn_star_big_on);
                builder.setTitle("‘小鱼计划’用户须知");
                builder.setPositiveButton("我明白了",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }
}
