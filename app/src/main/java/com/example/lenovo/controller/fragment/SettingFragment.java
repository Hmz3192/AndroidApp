package com.example.lenovo.controller.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chenyu.library.Utils.AnimationUtils;
import com.chenyu.library.XToast;
import com.example.lenovo.app.LoginActivity;
import com.example.lenovo.controller.activity.settingActivity.BlacklistActivity;
import com.example.lenovo.controller.activity.settingActivity.DiagnoseActivity;
import com.example.lenovo.controller.activity.settingActivity.LocationActivity;
import com.example.lenovo.controller.activity.settingActivity.MyCodeActivity;
import com.example.lenovo.model.Model;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.setting.activity.OrderActivity;
import com.example.lenovo.utils.BitmapUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseSwitchButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Administrator on 2016/9/23.
 */
// 设置页面
public class SettingFragment extends Fragment implements View.OnClickListener{
    private TextView bt_setting_out;
    private TextView tvUsercenter;
    private ScrollView scrollView;
    private ImageButton ibUserIconAvator;
    private ImageButton ib_user_setting;
    private DrawerLayout dl_draw;
    private LinearLayout ll_dl;
    private ImageButton ib_drawer_back;

    private TextView textview1, textview2;

    private RelativeLayout rl_switch_notification;
    private RelativeLayout rl_switch_sound;
    private RelativeLayout rl_switch_vibrate;
    private RelativeLayout rl_switch_speaker;
    private RelativeLayout rl_switch_chatroom_leave;

    private EaseSwitchButton notifySwitch;
    private EaseSwitchButton soundSwitch;
    private EaseSwitchButton vibrateSwitch;
    private EaseSwitchButton speakerSwitch;
    private EaseSwitchButton ownerLeaveSwitch;

    private LinearLayout llDiagnose;
    private LinearLayout blacklistContainer;

    private TextView ib_drawer_layout_confirm,tv_user_code;
    private TextView tv_location;
    private TextView tv_user_version;
    private TextView tv_all_order;

    private TextView tv_user_pay;
    private TextView tv_user_send;
    private TextView tv_user_receive;
    private TextView tv_user_finish;
    private TextView tv_user_drawback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);
        initView(view);
        tvUsercenter.setAlpha(0);
        ll_dl.setVisibility(View.VISIBLE);
        ib_drawer_back.setVisibility(View.VISIBLE);

        return view;
    }

    private void initView(View view) {
        rl_switch_notification = (RelativeLayout) view.findViewById(R.id.rl_switch_notification);
        rl_switch_sound = (RelativeLayout) view.findViewById(R.id.rl_switch_sound);
        rl_switch_vibrate = (RelativeLayout) view.findViewById(R.id.rl_switch_vibrate);
        rl_switch_speaker = (RelativeLayout) view.findViewById(R.id.rl_switch_speaker);
        rl_switch_chatroom_leave = (RelativeLayout) view.findViewById(R.id.rl_switch_chatroom_owner_leave);
        ib_drawer_layout_confirm = view.findViewById(R.id.ib_drawer_layout_confirm);

        notifySwitch = (EaseSwitchButton) view.findViewById(R.id.switch_notification);
        soundSwitch = (EaseSwitchButton) view.findViewById(R.id.switch_sound);
        vibrateSwitch = (EaseSwitchButton) view.findViewById(R.id.switch_vibrate);
        speakerSwitch = (EaseSwitchButton) view.findViewById(R.id.switch_speaker);
        ownerLeaveSwitch = (EaseSwitchButton) view.findViewById(R.id.switch_owner_leave);

        blacklistContainer = (LinearLayout) view.findViewById(R.id.ll_black_list);
        llDiagnose=(LinearLayout) view.findViewById(R.id.ll_diagnose);

        textview1 = (TextView) view.findViewById(R.id.textview1);
        textview2 = (TextView) view.findViewById(R.id.textview2);
        tv_location = view.findViewById(R.id.tv_location);

        tv_user_version = view.findViewById(R.id.tv_user_version);
        tv_user_code = view.findViewById(R.id.tv_user_code);
        bt_setting_out = (TextView) view.findViewById(R.id.bt_setting_out);
        tvUsercenter = (TextView) view.findViewById(R.id.tv_usercenter);
        scrollView = (ScrollView) view.findViewById(R.id.scrollview);
        ibUserIconAvator = (ImageButton) view.findViewById(R.id.ib_user_icon_avator);
        ib_user_setting = view.findViewById(R.id.ib_user_setting);
        dl_draw = view.findViewById(R.id.dl_draw);
        ll_dl = view.findViewById(R.id.ll_dl);
        ib_drawer_back = view.findViewById(R.id.ib_drawer_back);

        tv_all_order = view.findViewById(R.id.tv_all_order);

        tv_user_drawback = view.findViewById(R.id.tv_user_drawback);
        tv_user_finish = view.findViewById(R.id.tv_user_finish);
        tv_user_receive = view.findViewById(R.id.tv_user_receive);
        tv_user_send = view.findViewById(R.id.tv_user_send);
        tv_user_pay = view.findViewById(R.id.tv_user_pay);


        tv_user_pay.setOnClickListener(this);
        tv_user_send.setOnClickListener(this);
        tv_user_receive.setOnClickListener(this);
        tv_user_finish.setOnClickListener(this);
        tv_user_drawback.setOnClickListener(this);

        tv_user_code.setOnClickListener(this);

        tv_all_order.setOnClickListener(this);

        tv_user_version.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        ib_drawer_layout_confirm.setOnClickListener(this);
        rl_switch_notification.setOnClickListener(this);
        rl_switch_sound.setOnClickListener(this);
        rl_switch_vibrate.setOnClickListener(this);
        rl_switch_speaker.setOnClickListener(this);
        rl_switch_chatroom_leave.setOnClickListener(this);
        llDiagnose.setOnClickListener(this);
        blacklistContainer.setOnClickListener(this);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] location = new int[2];
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE://下滑是正，上滑是负
                        ibUserIconAvator.getLocationOnScreen(location);//初始状态为125,即最大值是125，全部显示不透明是（40？）
                        /*获取x=location【0】 ， y = location【1】*/
                        float i = (location[1] - 40) / 85f;
                        tvUsercenter.setAlpha(1 - i);
                        break;
                }
                return false;
            }
        });

        // 在button上显示当前用户名称
        bt_setting_out.setText("退出登录（" + EMClient.getInstance().getCurrentUser() + ")");


        setUserPic();


        /*打开画布*/
        ib_user_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl_draw.openDrawer(Gravity.LEFT);
                dl_draw.setScrimColor(Color.TRANSPARENT);
            }
        });

        ib_drawer_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl_draw.closeDrawers();

            }
        });
        // 退出登录的逻辑处理
        bt_setting_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        // 登录环信服务器退出登录
                        EMClient.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {

                                // 关闭DBHelper
                                Model.getInstance().getDbManager().close();

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 更新ui显示
//                                        Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                                        XToast.create(getActivity())
                                                .setText("退出成功！")
                                                .setAnimation(AnimationUtils.ANIMATION_DRAWER) //Drawer Type
                                                .setDuration(XToast.XTOAST_DURATION_LONG)
                                                .setOnDisappearListener(new XToast.OnDisappearListener() {
                                                    @Override
                                                    public void onDisappear(XToast xToast) {
                                                        Log.d("cylog", "The XToast has disappeared..");
                                                    }
                                                }).show();

                                        // 回到登录页面
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);

                                        startActivity(intent);

                                        getActivity().finish();
                                        getActivity(). overridePendingTransition(R.anim.hyperspace,
                                                R.anim.hyperspace_out);

                                    }
                                });

                            }

                            @Override
                            public void onError(int i, final String s) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "退出失败" + s, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
                    }
                });
            }
        });
    }

    private void setUserPic() {
        String currentUser = EMClient.getInstance().getCurrentUser();

        String[] pic = new String[]{"/1.jpg",
                "/2.jpg",
                "/3.jpg",
                "/4.jpg"
        };
        String PIC_RIL = "http://101.201.234.133:8080/Andro/pic";
        String avatar = null;
        if (currentUser.equalsIgnoreCase("112")) {
            avatar = pic[0];
        } else if (currentUser.equalsIgnoreCase("boom")) {
            avatar = pic[1];
        } else if (currentUser.equalsIgnoreCase("111")) {
            avatar = pic[2];
        } else
            avatar = pic[3];
        /*设置头像*/
        Picasso.with(getActivity()).load(PIC_RIL+avatar).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap bitmap) {
                //先对图片进行压缩
//                Bitmap zoom = BitmapUtils.zoom(bitmap, DensityUtil.dip2px(mContext, 62), DensityUtil.dip2px(mContext, 62));
                Bitmap zoom = BitmapUtils.zoom(bitmap, 90, 90);
                //对请求回来的Bitmap进行圆形处理
                Bitmap ciceBitMap = BitmapUtils.circleBitmap(zoom);
                bitmap.recycle();//必须队更改之前的进行回收
                return ciceBitMap;
            }

            @Override
            public String key() {
                return "";
            }
        }).into(ibUserIconAvator);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_switch_notification:
                if (notifySwitch.isSwitchOpen()) {
                    notifySwitch.closeSwitch();
                    rl_switch_sound.setVisibility(View.GONE);
                    rl_switch_vibrate.setVisibility(View.GONE);
                    textview1.setVisibility(View.GONE);
                    textview2.setVisibility(View.GONE);
//                    settingsModel.setSettingMsgNotification(false);
                } else {
                    notifySwitch.openSwitch();
                    rl_switch_sound.setVisibility(View.VISIBLE);
                    rl_switch_vibrate.setVisibility(View.VISIBLE);
                    textview1.setVisibility(View.VISIBLE);
                    textview2.setVisibility(View.VISIBLE);
//                    settingsModel.setSettingMsgNotification(true);
                }
                break;
            case R.id.rl_switch_sound:
                if (soundSwitch.isSwitchOpen()) {
                    soundSwitch.closeSwitch();
//                    settingsModel.setSettingMsgSound(false);
                } else {
                    soundSwitch.openSwitch();
//                    settingsModel.setSettingMsgSound(true);
                }
                break;
            case R.id.rl_switch_vibrate:
                if (vibrateSwitch.isSwitchOpen()) {
                    vibrateSwitch.closeSwitch();
//                    settingsModel.setSettingMsgVibrate(false);
                } else {
                    vibrateSwitch.openSwitch();
//                    settingsModel.setSettingMsgVibrate(true);
                }
                break;
            case R.id.rl_switch_speaker:
                if (speakerSwitch.isSwitchOpen()) {
                    speakerSwitch.closeSwitch();
//                    settingsModel.setSettingMsgSpeaker(false);
                } else {
                    speakerSwitch.openSwitch();
//                    settingsModel.setSettingMsgVibrate(true);
                }
                break;
            case R.id.rl_switch_chatroom_owner_leave:
                if(ownerLeaveSwitch.isSwitchOpen()){
                    ownerLeaveSwitch.closeSwitch();
//                    settingsModel.allowChatroomOwnerLeave(false);
//                    chatOptions.allowChatroomOwnerLeave(false);
                }else{
                    ownerLeaveSwitch.openSwitch();
//                    settingsModel.allowChatroomOwnerLeave(true);
//                    chatOptions.allowChatroomOwnerLeave(true);
                }
                break;
            case R.id.ll_black_list:
                startActivity(new Intent(getActivity(), BlacklistActivity.class));
                break;
            case R.id.ll_diagnose:
                startActivity(new Intent(getActivity(), DiagnoseActivity.class));
                break;
            case R.id.ib_drawer_layout_confirm:
                dl_draw.closeDrawers();
                break;
            case R.id.tv_user_code:
                //二维码
                Intent intent = new Intent(getActivity(), MyCodeActivity.class);
                intent.putExtra("context",EMClient.getInstance().getCurrentUser());
                startActivity(intent);

                break;

            case R.id.tv_location:
                /*收获地址*/
                Intent intent1 = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_user_version:
                XToast.create(getActivity())
                        .setText("Copyright ©2017 校淘 Powered By AndroidApp Version 5.0.0")
                        .setAnimation(AnimationUtils.ANIMATION_DRAWER) //Drawer Type
                        .setDuration(XToast.XTOAST_DURATION_LONG)
                        .setOnDisappearListener(new XToast.OnDisappearListener() {
                            @Override
                            public void onDisappear(XToast xToast) {
                                Log.d("cylog", "The XToast has disappeared..");
                            }
                        }).show();
                break;
            case R.id.tv_all_order:
                /*查看全部订单*/
                Intent intent2 = new Intent(getActivity(), OrderActivity.class);
                intent2.putExtra("which", "ALL");
                startActivity(intent2);
                break;
            case R.id.tv_user_pay:
                /*待付款*/
                Intent intent3 = new Intent(getActivity(), OrderActivity.class);
                intent3.putExtra("which", "PAY");
                startActivity(intent3);
                break;
            case R.id.tv_user_send:
                /*代发货*/
                Intent intent4 = new Intent(getActivity(), OrderActivity.class);
                intent4.putExtra("which", "SEND");
                startActivity(intent4);
                break;
            case R.id.tv_user_receive:
                /*收货*/
                Intent intent5 = new Intent(getActivity(), OrderActivity.class);
                intent5.putExtra("which", "GET");
                startActivity(intent5);
                break;
            case R.id.tv_user_finish:
                /*完成*/
                Intent intent6 = new Intent(getActivity(), OrderActivity.class);
                intent6.putExtra("which", "FINISH");
                startActivity(intent6);
                break;
            case R.id.tv_user_drawback:
                /*售后*/
                Intent intent7 = new Intent(getActivity(), OrderActivity.class);
                intent7.putExtra("which", "BACK");
                startActivity(intent7);
                break;

        }
    }
}
