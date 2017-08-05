package com.hyphenate.easeui.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.Random;

public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;
    private static String TAG;


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
        EaseUser user = getUserInfo(username);
        String currentUser = EMClient.getInstance().getCurrentUser();

        /*这个地方是聊天的，为了显示头像，我们设置一些随机数，当用户为某某时，设定头像为这个。*/
        String[] pic = new String[]{"/1.jpg",
                "/2.jpg",
                "/3.jpg",
                "/4.jpg"
        };
        String PIC_RIL = "http://101.201.234.133:8080/Andro/pic";
        String avatar = null;
        Log.e(TAG, "-----------------------------" + currentUser);
        Random random = new Random();
        if (username.equalsIgnoreCase("112")) {
            avatar = pic[0];
        } else if (username.equalsIgnoreCase("boom")) {
            avatar = pic[1];
        } else if (username.equalsIgnoreCase("111")) {
            avatar = pic[2];
        } else
            avatar = pic[3];
        if (username != null && avatar != null) {
            try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(PIC_RIL + avatar).into(imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(username).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_appitem_del_btn_normal).into(imageView);
            }
        } else {
            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
        }
    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
        if (textView != null) {
            EaseUser user = getUserInfo(username);
            if (user != null && user.getNick() != null) {
                textView.setText(user.getNick());
            } else {
                textView.setText(username);
            }
        }
    }

}
