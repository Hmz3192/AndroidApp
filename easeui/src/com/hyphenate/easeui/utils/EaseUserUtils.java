package com.hyphenate.easeui.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.util.QQUser;
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
        currentUser = username;
        new MyThread(context,imageView).start();

    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
        if (textView != null) {
            if (userBean != null && userBean.getNickname() != null) {
                textView.setText(userBean.getNickname());
            } else {
                textView.setText(username);
            }
        }
    }
    public static class MyThread extends Thread {

        //继承Thread类，并改写其run方法
        private final static String TAG = "My Thread ===> ";
        private final Context context;
        private final ImageView imageView;

        public MyThread(Context context,ImageView imageView) {
            this.context = context;
            this.imageView = imageView;
        }

        public void run(){
            Log.d(TAG, "run");
            String url = "http://101.201.234.133:8111/AndroidCon/user/getPic";
            OkHttpUtils
                    .get()
                    .url(url)
                    .addParams("hxid", currentUser)
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
            Log.i("info", userBean.getPhoto() + "=-------------");
            Log.e(TAG, "-----------------------------" + currentUser);

            if (userBean.getPhoto() != null) {
                try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
                    Glide.with(context).load(userBean.getPhoto()).into(imageView);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(context)
                            .load(currentUser)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ease_default_avatar)
                            .into(imageView);
                }
            } else {
                Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
            }
        }
    }
}
