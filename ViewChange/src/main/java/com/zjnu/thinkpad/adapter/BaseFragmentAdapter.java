package com.zjnu.thinkpad.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zjnu.thinkpad.fragment.LauncherBaseFragment;

import java.util.List;

/**
 * User--Hu mingzhi on 2017/7/31.
 * Created by ThinKPad
 */

public class BaseFragmentAdapter extends FragmentStatePagerAdapter {

    private List<LauncherBaseFragment> list;


    public BaseFragmentAdapter(FragmentManager fm,List<LauncherBaseFragment> list) {
        super(fm);
        this.list = list;

    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
