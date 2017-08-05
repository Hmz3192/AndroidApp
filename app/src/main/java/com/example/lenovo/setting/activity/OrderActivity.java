package com.example.lenovo.setting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.setting.fragment.AllFragment;
import com.example.lenovo.setting.fragment.backFragment;
import com.example.lenovo.setting.fragment.buyFragment;
import com.example.lenovo.setting.fragment.finishFragment;
import com.example.lenovo.setting.fragment.getFragment;
import com.example.lenovo.setting.fragment.sendFragment;

public class OrderActivity extends FragmentActivity {
    private RadioGroup or_main;
    private AllFragment allFragment;
    private backFragment backFragment;
    private buyFragment buyFragment;
    private finishFragment finishFragment;
    private getFragment getFragment;
    private sendFragment sendFragment;
    private ImageButton ib_order_back;
    private String which;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
         which = intent.getStringExtra("which");


        initView();
        initData();
        initListener();

    }

    private void initListener() {



        ib_order_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //RadioGroup的选择事件
        or_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,  int checkedId) {
                Fragment fragment = null;
                switch (checkedId) {
                    /*全部*/
                    case R.id.rb_all:
                        fragment = allFragment;
                        break;
                    /*待付款*/
                    case R.id.rb_buy:
                        fragment = buyFragment;
                        break;
                    // 代发货
                    case R.id.rb_send:
                        fragment = sendFragment;
                        break;

                    // 待收货
                    case R.id.rb_get:
                        fragment = getFragment;
                        break;

                    // 完成
                    case R.id.rb_finish:
                        fragment = finishFragment;
                        break;
                    // 售后
                    case R.id.rb_back:
                        fragment = backFragment;
                        break;
                }

                // 实现fragment切换的方法
                switchFragment(fragment);
            }
        });

        if (which.equalsIgnoreCase("ALL")) {
            // 默认选择全部订单页面
            or_main.check(R.id.rb_all);
        } else if (which.equalsIgnoreCase("PAY")) {
            // 默认选择待付款列表页面
            or_main.check(R.id.rb_buy);
        }else if (which.equalsIgnoreCase("SEND")) {
            // 默认选择代发货列表页面
            or_main.check(R.id.rb_send);
        }else if (which.equalsIgnoreCase("GET")) {
            // 默认选择待收货列表页面
            or_main.check(R.id.rb_get);
        }else if (which.equalsIgnoreCase("FINISH")) {
            // 默认选择已完成列表页面
            or_main.check(R.id.rb_finish);
        }else if (which.equalsIgnoreCase("BACK")) {
            // 默认选择售后列表页面
            or_main.check(R.id.rb_back);
        }
    }

    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_order, fragment).commit();
    }

    private void initView() {
        or_main = (RadioGroup)findViewById(R.id.or_main);
        ib_order_back = findViewById(R.id.ib_order_back);
    }
    private void initData() {
        // 创建fragment对象
        allFragment = new AllFragment();
        backFragment = new backFragment();
        buyFragment = new buyFragment();
        finishFragment = new finishFragment();
        getFragment = new getFragment();
        sendFragment = new sendFragment();

    }
}
