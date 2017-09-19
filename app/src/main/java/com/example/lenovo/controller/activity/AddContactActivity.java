package com.example.lenovo.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lenovo.app.Bean.QQUser;
import com.example.lenovo.model.Model;
import com.example.lenovo.model.bean.UserInfoBean;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.QQURL;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

// 添加联系人页面
public class AddContactActivity extends Activity {
    private TextView tv_add_find;
    private EditText et_add_name;
    private RelativeLayout rl_add;
    private TextView tv_add_name;
    private Button bt_add_add;
    private ImageView iv_add_photo;
    private UserInfoBean userInfo;
    private RelativeLayout ll_not;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_contact);
        // 初始化view
        initView();

        initListener();
    }

    private void initListener() {
        // 查找按钮的点击事件处理
        tv_add_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find();
                ll_not.setVisibility(View.GONE);
                rl_add.setVisibility(View.GONE);
                InputMethodManager imm  = (InputMethodManager) getApplication().getSystemService(INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(tv_add_find.getApplicationWindowToken(), 0);
                }
            }
        });

        // 添加按钮的点击事件处理
        bt_add_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    // 查找按钮的处理
    private void find() {
        // 获取输入的用户名称
        final String hxdi = et_add_name.getText().toString();

        // 校验输入的名称
        if (TextUtils.isEmpty(hxdi)) {
            Toast.makeText(AddContactActivity.this, "输入的用户名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 去服务器判断当前用户是否存在
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 去服务器判断当前查找的用户是否存在
                userInfo = new UserInfoBean(hxdi);
                getDataFromMy(userInfo.getName(), rl_add, ll_not, tv_add_name,iv_add_photo);
                // 更新UI显示
              /*  runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rl_add.setVisibility(View.VISIBLE);
                        tv_add_name.setText(userInfo.getName());
                    }
                });*/
            }
        });


    }

    private void getDataFromMy(String hxid, final RelativeLayout rl_add, final RelativeLayout ll_not, final TextView tv_add_name, final ImageView iv_add_photo) {

        String url = QQURL.GETONEINFO;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("hxid", hxid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ll_not.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("info", "成功获取数据：" + response);
                        QQUser.UserBean userBean = JSON.parseObject(response, QQUser.UserBean.class);
                        try {
                            tv_add_name.setText(userBean.getNickname());
                            if (!userBean.getPhoto().equalsIgnoreCase("0")) {
                                Picasso.with(getApplication())
                                        .load(userBean.getPhoto())
//                                    .networkPolicy(NetworkPolicy.NO_CACHE)
//                                    .memoryPolicy(MemoryPolicy.NO_CACHE)//不加载缓存
                                        .into(iv_add_photo);
                            }
                            rl_add.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            ll_not.setVisibility(View.VISIBLE);
                            tv_add_name.setText("");
                        }


                    }

                });
    }

    // 添加按钮处理
    private void add() {

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 去环信服务器添加好友
                try {
                    EMClient.getInstance().contactManager().addContact(userInfo.getName(), "添加好友");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "发送添加好友邀请成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "发送添加好友邀请失败" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        tv_add_find = (TextView) findViewById(R.id.tv_add_find);
        et_add_name = (EditText) findViewById(R.id.et_add_name);
        rl_add = (RelativeLayout) findViewById(R.id.rl_add);
        tv_add_name = (TextView) findViewById(R.id.tv_add_name);
        ll_not = findViewById(R.id.ll_not);
        bt_add_add = (Button) findViewById(R.id.bt_add_add);
        iv_add_photo = findViewById(R.id.iv_add_photo);
    }
}
