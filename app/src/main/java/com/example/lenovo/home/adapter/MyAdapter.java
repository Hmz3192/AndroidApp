package com.example.lenovo.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.home.adapter.TabAdapter.MyAdapterViewAdapter;
import com.example.lenovo.home.bean.Test;
import com.example.lenovo.home.fragment.TabBaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter  extends FragmentPagerAdapter {
//    private final int ershou = 0;
//    private final int jiuwu = 1;
//    private final int newGood = 2;
//    private final int haiwai = 3;
//    private List<Fragment> fragmentList;
    private List<TabBaseFragment> mFragments;
    private MyAdapterViewAdapter adapter;
    private List<Test> tests = new ArrayList<Test>();


    private RecyclerView wantlist_rec;

    public MyAdapter(FragmentManager  fm, List<TabBaseFragment> fragmentList) {
        super(fm);
        this.mFragments = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);

    }

    @Override
    public int getCount() {
        int ret = 0;
        if(mFragments != null){
            ret = mFragments.size();
        }
        return ret;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getFragmentTitle();
    }
}
/*    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Test test0 = new Test("消息一", "内容一", 0);
        Test test1 = new Test("消息二", "内容二", 0);
        Test test2 = new Test("消息三", "内容三", 0);
        Test test3 = new Test("消息四", "内容四", 1);
        Test test4 = new Test("消息五", "内容五", 1);
        Test test5 = new Test("消息六", "内容六", 1);
        Test test6 = new Test("消息七", "内容七", 2);
        Test test7 = new Test("消息八", "内容八", 3);
        Test test8 = new Test("消息九", "内容九", 3);
        tests.add(test0);
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        tests.add(test4);
        tests.add(test5);
        tests.add(test6);
        tests.add(test7);
        tests.add(test8);


        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.want_pager, container, false);
        wantlist_rec = container.findViewById(R.id.wantlist_rec);
        if (position == ershou) {
            List<Test> testList = new ArrayList<>();
            for (Test test : tests) {
                if (test.getKind() == 0) {
                    testList.add(test);
                }
            }
            //设置适配器
            adapter = new MyAdapterViewAdapter(mcontext, testList);
            GridLayoutManager manager = new GridLayoutManager(mcontext, 1);
            wantlist_rec.setAdapter(adapter);
            *//*设置布局管理者*//*
            wantlist_rec.setLayoutManager(manager);
            container.addView(view);

            return view;
        } else if (position == jiuwu) {
            List<Test> testList = new ArrayList<>();
            for (Test test : tests) {
                if (test.getKind() == 1) {
                    testList.add(test);
                }
            }
            //设置适配器
            adapter = new MyAdapterViewAdapter(mcontext, tests);
            GridLayoutManager manager = new GridLayoutManager(mcontext, 1);
            wantlist_rec.setAdapter(adapter);
            *//*设置布局管理者*//*
            wantlist_rec.setLayoutManager(manager);
            container.addView(view);
            return view;
        }else if (position == newGood) {
            List<Test> testList = new ArrayList<>();
            for (Test test : tests) {
                if (test.getKind() == 2) {
                    testList.add(test);
                }
            }
            //设置适配器
            adapter = new MyAdapterViewAdapter(mcontext, tests);
            GridLayoutManager manager = new GridLayoutManager(mcontext, 1);
            wantlist_rec.setAdapter(adapter);
            *//*设置布局管理者*//*
            wantlist_rec.setLayoutManager(manager);
            container.addView(view);
            return view;
        }else if (position == haiwai) {
            List<Test> testList = new ArrayList<>();
            for (Test test : tests) {
                if (test.getKind() == 3) {
                    testList.add(test);
                }
            }
            //设置适配器
            adapter = new MyAdapterViewAdapter(mcontext, tests);
            GridLayoutManager manager = new GridLayoutManager(mcontext, 1);
            wantlist_rec.setAdapter(adapter);
            *//*设置布局管理者*//*
            wantlist_rec.setLayoutManager(manager);
            container.addView(view);
            return view;
        }

        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "二手求购";
        } else if (position == 1) {
            return "旧物换新";

        } else if (position == 2) {
            return "新品专卖";

        } else if (position == 3) {
            return "海外代购";
        }
        return null;

    }*/
