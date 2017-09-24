package com.example.lenovo.home.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.lenovo.home.adapter.MyAdapter;
import com.example.lenovo.home.fragment.TabBaseFragment;
import com.example.lenovo.home.fragment.TabFragment;
import com.example.lenovo.home.fragment.TabFragment2;
import com.example.lenovo.home.fragment.TabFragment3;
import com.example.lenovo.home.fragment.TabFragment4;
import com.example.lenovo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class WantListActivity extends FragmentActivity {
    private TabLayout tabLayout;
    private ImageView lv_back_want;
    private ViewPager viewPager;
//    private List<Fragment> fragmentList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_want_list);
        initView();
    }


    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        lv_back_want = findViewById(R.id.lv_back_want);
        List<TabBaseFragment>  fragmentList = new ArrayList<>();
        fragmentList.add(new TabFragment());
        fragmentList.add(new TabFragment2());
        fragmentList.add(new TabFragment3());
        fragmentList.add(new TabFragment4());
//        fragmentList.add(new TabFragment());

        adapter = new MyAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

//        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new RecycleFragment().newInstance(), "全部商品");
//        adapter.addFragment(new OneRecycleFragment().newInstance(), "首页");
//        adapter.addFragment(new TwowayRecycleFragment().newInstance(), "本地超市");


//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setCurrentItem(0);
        lv_back_want.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }



    /*private TabLayout tabLayout;
    private ImageView lv_back_want;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private MyFragmentPagerAdapter adapter;
    private String[] stringList = new String[]{"二手求购", "旧物换新", "新品专卖", "海外代购"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_want_list);
        initView();
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        lv_back_want = findViewById(R.id.lv_back_want);
        fragmentList = new ArrayList<>();
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecycleFragment().newInstance(), "全部商品");
        adapter.addFragment(new OneRecycleFragment().newInstance(), "首页");
        adapter.addFragment(new TwowayRecycleFragment().newInstance(), "本地超市");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);



        lv_back_want.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments = new LinkedList<>();
        private List<String> mTitles = new LinkedList<>();

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

*/

}
