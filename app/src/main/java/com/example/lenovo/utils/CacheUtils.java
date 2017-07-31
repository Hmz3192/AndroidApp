package com.example.lenovo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hmz on 2017/7/15.
 * 得到保存的数据
 * 缓存工具类
 */

public class CacheUtils {

    public static final String SP_NAME = "News";
    /*获取数据*/
    public static String getString(Context mcontext, String key) {
        SharedPreferences sp = mcontext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }
    /*保存String类型数据*/
    public static void saveString(Context mcontext, String key, String value) {
        SharedPreferences sp = mcontext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
}
