package com.example.lenovo.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chenyu.library.Utils.AnimationUtils;
import com.chenyu.library.XToast;
import com.example.lenovo.model.Model;
import com.example.lenovo.model.bean.UserInfo;
import com.example.lenovo.myapplication.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class LoginActivity extends Activity {
    private EditText et_login_name;
    private EditText et_login_pass;
    private TextView bt_regist;
    private TextView bt_login;
    private TextView tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login);
        /*初始化控件*/
        initView();
        /*初始化监听*/
        initListener();

    }


    private void initListener() {
        /*注册按钮的点击事件*/
        bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(bt_regist.getApplicationWindowToken(), 0);
                }
                regist();

            }
        });

         /*登录按钮的点击事件*/
        bt_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(bt_login.getApplicationWindowToken(), 0);
                }
                login();

            }
        });
        /*回退*/
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.scale_translate,
                        R.anim.my_alpha_action);
            }
        });
    }

    /*登录按钮的点击事件*/
    private void login() {
        /*获取用户名密码*/
        final String loginname = et_login_name.getText().toString().trim();
        final String loginpwd = et_login_pass.getText().toString().trim();

        /*校验*/
        if (TextUtils.isEmpty(loginname) || TextUtils.isEmpty(loginpwd)) {
            XToast.create(LoginActivity.this)
                    .setText("输入的用户名密码为空！")
                    .setAnimation(AnimationUtils.ANIMATION_DRAWER) //Drawer Type
                    .setDuration(XToast.XTOAST_DURATION_LONG)
                    .setOnDisappearListener(new XToast.OnDisappearListener() {
                        @Override
                        public void onDisappear(XToast xToast) {
                            Log.d("cylog", "The XToast has disappeared..");
                        }
                    }).show();
//            Toast.makeText(this, "输入的用户名密码为空", Toast.LENGTH_SHORT).show();
            /*退出注册逻辑判断*/
            return;
        }

        /*环信服务器上登陆逻辑处理*/
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                /*环信服务器登录*/
                EMClient.getInstance().login(loginname, loginpwd, new EMCallBack() {
                    @Override
                    public void onSuccess() {

                        /*对模型层处理*/
                        Model.getInstance().loginSuccess(new UserInfo(loginname));
                        /*保存用户信息到本地*/
                        Model.getInstance().getUserAccountDao().addAccount(new UserInfo(loginname));
                        /*提示登录*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*提示成功*/
//                                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                /*跳转到主页面*/
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.wave_scale,
                                        R.anim.my_alpha_action);
                            }
                        });

                    }

                    @Override
                    public void onError(int i, final String s) {
                        /*提示登陆失败*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(LoginActivity.this, "登陆失败"+ s, Toast.LENGTH_SHORT).show();
                                XToast.create(LoginActivity.this)
                                        .setText("账户或密码错误！")
                                        .setAnimation(AnimationUtils.ANIMATION_DRAWER) //Drawer Type
                                        .setDuration(XToast.XTOAST_DURATION_LONG)
                                        .setOnDisappearListener(new XToast.OnDisappearListener() {
                                            @Override
                                            public void onDisappear(XToast xToast) {
                                                Log.d("cylog", "The XToast has disappeared..");
                                            }
                                        }).show();
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

    /*注册按钮的点击事件*/
    private void regist() {
        /*1.获取输入的用户名密码*/
        final String name = et_login_name.getText().toString().trim();
        final String pwd = et_login_pass.getText().toString().trim();
        /*2.校验输入的用户名密码*/
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "↑↑↑↑↑输入的用户名密码再注册好吧！", Toast.LENGTH_SHORT).show();
            /*退出注册逻辑判断*/
            return;
        }
        /*3.服务器注册账号*/
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    /*服务器上注册*/
                    EMClient.getInstance().createAccount(name, pwd);
                    /*更新页面显示*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(LoginActivity.this, "注册失败" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void initView() {
        tv_back = findViewById(R.id.tv_back);
        et_login_name = findViewById(R.id.et_login_name);
        et_login_pass = findViewById(R.id.et_login_pass);
        bt_regist = findViewById(R.id.bt_regist);
        bt_login = findViewById(R.id.bt_login);
    }
}
