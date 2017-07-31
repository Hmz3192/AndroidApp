package com.example.lenovo.controller.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.app.LoginActivity;
import com.example.lenovo.model.Model;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.BitmapUtils;
import com.example.lenovo.utils.Constants;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Administrator on 2016/9/23.
 */
// 设置页面
public class SettingFragment extends Fragment {
    private TextView bt_setting_out;
    private TextView tvUsercenter;
    private ScrollView scrollView;
    private ImageButton ibUserIconAvator;
    private ImageButton ib_user_setting;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);

        initView(view);
        tvUsercenter.setAlpha(0);

        return view;
    }

    private void initView(View view) {
        bt_setting_out = (TextView) view.findViewById(R.id.bt_setting_out);
        tvUsercenter = (TextView) view.findViewById(R.id.tv_usercenter);
        scrollView = (ScrollView) view.findViewById(R.id.scrollview);
        ibUserIconAvator = (ImageButton) view.findViewById(R.id.ib_user_icon_avator);
        ib_user_setting = view.findViewById(R.id.ib_user_setting);

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

        /*设置头像*/
        String profile_image_url = "/1446607168578.jpg";
        Picasso.with(getActivity()).load(Constants.BASE_URL_IMAGE+profile_image_url).transform(new Transformation() {
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
                                        Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();

                                        // 回到登录页面
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);

                                        startActivity(intent);

                                        getActivity().finish();
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
}
