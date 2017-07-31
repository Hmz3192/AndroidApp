package com.example.lenovo.app;

import android.app.Application;
import android.content.Context;

import com.example.lenovo.model.Model;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Hmz on 2017/7/5.
 */

public class MyApplication extends Application {
    private static Context mContext;
    public static Context getMcontext() {
        return mContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        /*初始化okhttpclient*/
        initOkhttpClient();
          /*初始化easeui*/
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);/*设置同意后才接受好友邀请*/
        options.setAutoAcceptGroupInvitation(false);/*群邀请*/
        EaseUI.getInstance().init(this, options);
        /*初始化数据模型层类*/
        Model.getInstance().init(this);
        // 初始化全局上下文对象
        mContext = this;
    }

    private void initOkhttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
    // 获取全局上下文对象
    public static Context getGlobalApplication(){
        return mContext;
    }
}
