package com.example.lenovo.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lenovo.controller.fragment.ChatFragment;
import com.example.lenovo.controller.fragment.ContactListFragment;
import com.example.lenovo.controller.fragment.SettingFragment;
import com.example.lenovo.home.fragment.HomeFragment;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.shoppingcart.fragment.CartFragment;

public class MainActivity extends FragmentActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RadioGroup rg_main;
    private ChatFragment chatFragment;
    private ContactListFragment contactListFragment;
    private SettingFragment settingFragment;
    private HomeFragment homeFragment;
    private CartFragment cartFragment;
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initData();

        initListener();
    }

    private void initListener() {
        //RadioGroup的选择事件
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,  int checkedId) {
                Fragment fragment = null;
                switch (checkedId) {
                    /*主页面*/
                    case R.id.rb_home:
                        fragment = homeFragment;
                        break;
                    /*购物车*/
                    case R.id.rb_cart:
                        fragment = cartFragment;
                        break;
                    // 会话列表页面
                    case R.id.rb_community:
                        fragment = chatFragment;
                        break;

                    // 联系人列表页面
                    case R.id.rb_type:
                        fragment = contactListFragment;
                        break;

                    // 设置页面
                    case R.id.rb_user:
                        fragment = settingFragment;
                        break;
                }

                // 实现fragment切换的方法
                switchFragment(fragment);
            }
        });

        // 默认选择会话列表页面
        rg_main.check(R.id.rb_home);
    }

    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_Layout, fragment).commit();
    }

    private void initData() {
        // 创建fragment对象
        homeFragment = new HomeFragment();
        cartFragment = new CartFragment();
        chatFragment = new ChatFragment();
        contactListFragment = new ContactListFragment();
        settingFragment = new SettingFragment();
    }


    private void initView() {

        rg_main = (RadioGroup)findViewById(R.id.rg_main);
        mSwipeLayout =  findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(android.R.color.holo_red_light); // 设定下拉圆圈的背景
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE); // 设置圆圈的大小
    }
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                // 停止刷新
                mSwipeLayout.setRefreshing(false);

            }
        }, 2000); // 2秒后发送消息，停止刷新
    }


}


/*


    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;
    *//*装载多个fragment实例集合*//*
    private ArrayList<BaseFragment> fragments;

    private int postion = 0;
    *//*上次显示的fragment*//*
    private Fragment tempFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        *//*初始化fragment*//*
        initFragment();
        *//*设置监听*//*
        initListener();

    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_home://主页
                        postion = 0;
                        break;
                    case R.id.rb_cart:
                        postion = 1;
                        break;
                    case R.id.rb_community:
                        postion = 2;
                        break;
                    case R.id.rb_type:
                        postion = 3;
                        break;
                    case R.id.rb_user:
                        postion = 4;
                        break;
                    default:
                        postion = 0;
                        break;
                }

                *//*根据位置取不同的fragment*//*
                BaseFragment basefragment = getFragment(postion);

                *//*第一个是上次显示的fragment
                * 第二个是当前正要显示的fragment*//*
                switchFragment(tempFragment,basefragment);
            }
        });
        rgMain.check(R.id.rb_home);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CartFragment());
        fragments.add(new ContactListFragment());
        fragments.add(new ChatFragment());
        fragments.add(new SettingFragment());
    }

    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else { //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }*/