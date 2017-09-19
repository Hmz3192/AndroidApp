package com.hyphenate.easeui.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.util.QQUser;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;
    private static String TAG;
    private static String currentUser;
    private static QQUser.UserBean userBean;


    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }



    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username) {
        if (userProvider != null)
            return userProvider.getUser(username);

        return null;
    }

    /**
     * set user avatar
     *
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView) {
        ;
        new MyThread(context,imageView,username).start();

    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
        new MyThread1(textView,username).start();
//        if (textView != null) {
//            if (userBean != null && userBean.getNickname() != null) {
//                textView.setText(userBean.getNickname());
//            } else {
//                textView.setText(username);
//            }
//        }
    }
    public static class MyThread extends Thread {

        //继承Thread类，并改写其run方法
        private final static String TAG = "My Thread ===> ";
        private final Context context;
        private final ImageView imageView;
        private final String userName;

        public MyThread(Context context,ImageView imageView,String userName) {
            this.context = context;
            this.userName = userName;
            this.imageView = imageView;
        }

        public void run(){
            Log.d(TAG, "run");
            String url = "http://101.201.234.133:8111/AndroidCon/user/getPic";
            OkHttpUtils
                    .get()
                    .url(url)
                    .addParams("hxid", userName)
                    .build()
                    .execute(new StringCallback(){

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i("info", "failed");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            processData(response);
                        }

                    });
        }

        private void processData(String response) {
            userBean = JSON.parseObject(response, QQUser.UserBean.class);

            if (userBean.getPhoto() != null) {
                try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
                   /* Glide.with(context)
                            .load(userBean.getPhoto())
                            .diskCacheStrategy( DiskCacheStrategy.NONE )//禁用磁盘缓存
                            .skipMemoryCache( true )//跳过内存缓存
                            .into(imageView);*/
                    Picasso.with(context)
                            .load(userBean.getPhoto())
//                            .networkPolicy(NetworkPolicy.NO_CACHE)
//                            .memoryPolicy(MemoryPolicy.NO_CACHE)//不加载缓存
                            .into(imageView);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(context)
                            .load(currentUser)
                            .placeholder(R.drawable.ease_default_avatar)
                            .into(imageView);
                }
            } else {
                Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
            }
        }
    }
    public static class MyThread1 extends Thread {

        //继承Thread类，并改写其run方法
        private final static String TAG = "My Thread ===> ";
        private final TextView textView;
        private final String userName;
        private QQUser.UserBean userBean1;

        public MyThread1(TextView textView,String userName) {
            this.textView = textView;
            this.userName = userName;
        }

        public void run() {
            Log.d(TAG, "run");
            String url = "http://101.201.234.133:8111/AndroidCon/user/getPic";
            OkHttpUtils
                    .get()
                    .url(url)
                    .addParams("hxid", userName)
                    .build()
                    .execute(new StringCallback() {

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i("info", "failed");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            processData(response);
                        }

                    });
        }

        private void processData(String response) {
            userBean1 = JSON.parseObject(response, QQUser.UserBean.class);
            textView.setText(userBean1.getNickname());

        }
    }
}
