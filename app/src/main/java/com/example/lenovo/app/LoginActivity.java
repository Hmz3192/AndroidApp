package com.example.lenovo.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chenyu.library.Utils.AnimationUtils;
import com.chenyu.library.XToast;
import com.example.lenovo.app.Bean.QQUser;
import com.example.lenovo.model.Model;
import com.example.lenovo.model.bean.UserInfoBean;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.QQURL;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity {
    private EditText et_login_name;
    private EditText et_login_pass;
    private TextView bt_regist,_signupLink;
    private Button bt_login;
//    private TextView tv_back;
    private Button bt_qq;
    private UserInfo mUserInfo;
    private static final String APP_ID = "1106333790";
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    Bitmap bitmap = null;
    public static String nicknameString;
    private String picUrl;
    private String a = "1";
    private String sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        mTencent = Tencent.createInstance(APP_ID, LoginActivity.this.getApplicationContext());

        /*初始化控件*/
        initView();
        /*初始化监听*/
        initListener();

    }


    private void initListener() {
        /*注册按钮的点击事件*/
       /* bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(bt_regist.getApplicationWindowToken(), 0);
                }
                regist();

            }
        });*/

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
       /* tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.scale_translate,
                        R.anim.my_alpha_action);
            }
        });*/

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();

            }
        });

        bt_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginQQ();
            }
        });
    }

    private void LoginQQ() {
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        mTencent.login(LoginActivity.this, "all", mIUiListener);
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
                        Model.getInstance().loginSuccess(new UserInfoBean(loginname));
                        /*保存用户信息到本地*/
                        Model.getInstance().getUserAccountDao().addAccount(new UserInfoBean(loginname));
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
    /*private void regist() {
        *//*1.获取输入的用户名密码*//*
        final String name = et_login_name.getText().toString().trim();
        final String pwd = et_login_pass.getText().toString().trim();
        *//*2.校验输入的用户名密码*//*
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "↑↑↑↑↑输入的用户名密码再注册好吧！", Toast.LENGTH_SHORT).show();
            *//*退出注册逻辑判断*//*
            return;
        }
        *//*3.服务器注册账号*//*
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    *//*服务器上注册*//*
                    EMClient.getInstance().createAccount(name, pwd);
                    PostDataNor(name, pwd);
                    *//*更新页面显示*//*
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
    }*/

    private void PostDataNor(String name,String pass) {
        String url = QQURL.REGISTER_NOR;
        OkHttpUtils
                .post()
                .url(url)
                .addParams("hxid", name)
                .addParams("pass", pass)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        procssData(response);
                    }
                });
    }

    private void initView() {
//        tv_back = findViewById(tv_back);
        et_login_name = findViewById(R.id.et_login_name);
        et_login_pass = findViewById(R.id.et_login_pass);
//        bt_regist = findViewById(R.id.bt_regist);
        bt_login = findViewById(R.id.bt_login);
        bt_qq = findViewById(R.id.bt_qq);
        _signupLink = findViewById(R.id.link_signup);
    }


    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
//            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            /**/
            JSONObject obj = (JSONObject) response;
            Log.e(TAG, "response1111111111111111111111111:" + response);
            String openID = null;
            try {
                openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            /*获取qq信息*/
            QQToken qqToken = mTencent.getQQToken();
            mUserInfo = new UserInfo(getApplicationContext(), qqToken);
            mUserInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object response) {
                    JSONObject json = (JSONObject)response;
                    try {
                        Log.i("info", "----------------------------------232323232" + json.toString());
                        picUrl = json.getString("figureurl_qq_2");
                        nicknameString=json.getString("nickname");
                        sex = json.getString("gender");
                        postDataQQ();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(UiError uiError) {

                }

                @Override
                public void onCancel() {

                }
                 });


//                    userlogo.setImageBitmap(bitmap);


            /*try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(final Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                        Log.e(TAG, "---------------111111");
                        Message msg = new Message();
                        msg.obj = response;
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                        Log.e(TAG, "-----111---"+response.toString());
                        *//**由于图片需要下载所以这里使用了线程，如果是想获得其他文字信息直接
             * 在mHandler里进行操作
             *
             *//*
                        new Thread(){

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                JSONObject json = (JSONObject)response;
                                try {
                                    bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                Message msg = new Message();
                                msg.obj = bitmap;
                                msg.what = 1;
                                mHandler.sendMessage(msg);
                            }
                        }.start();
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }
      /*  Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    JSONObject response = (JSONObject) msg.obj;
                    if (response.has("nickname")) {
                        try {
                            nicknameString=response.getString("nickname");

//                            nicknameTextView.setText(nicknameString);
                            Log.e(TAG, "--"+nicknameString);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }else if(msg.what == 1){
                    Bitmap bitmap = (Bitmap)msg.obj;
//                    userlogo.setImageBitmap(bitmap);

                }
            }

        };*/

    }

    private void postDataQQ() {
       String url = QQURL.REGISTER;
        OkHttpUtils
                .post()
                .url(url)
                .addParams("nickName",nicknameString )
                .addParams("photo", picUrl)
                .addParams("sex", sex)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        procssData(response);
                    }
                });
    }

    private void procssData(String response) {
        QQUser qqUser = JSON.parseObject(response, QQUser.class);
        String time = qqUser.getTime();
        String name = qqUser.getUser().getHxid();
        String pass = qqUser.getUser().getPass();
        if (time.equalsIgnoreCase(a)) {
            /*已经注册*/
            loginQQ(name,pass);
        } else {
            /*未在服务器上注册, */
            registerQQ(name,pass);
        }


    }

    private void registerQQ(final String name, final String pass) {
         /*3.服务器注册账号*/
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    /*服务器上注册*/
                    EMClient.getInstance().createAccount(name, pass);
                    loginQQ(name, pass);
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            Toast.makeText(LoginActivity.this, "注册失败" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void loginQQ(final String name, final String pass) {
         /*环信服务器上登陆逻辑处理*/
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                /*环信服务器登录*/
                EMClient.getInstance().login(name, pass, new EMCallBack() {
                    @Override
                    public void onSuccess() {

                        /*对模型层处理*/
                        Model.getInstance().loginSuccess(new UserInfoBean(name));
                        /*保存用户信息到本地*/
                        Model.getInstance().getUserAccountDao().addAccount(new UserInfoBean(name));
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
                                        .setText("第三方数据错误！")
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

                    /**
                     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
                     *
                     * @param requestCode
                     * @param resultCode
                     * @param data
                     */
                    @Override
                    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                        if (requestCode == Constants.REQUEST_LOGIN) {
                            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
                        }
                        super.onActivityResult(requestCode, resultCode, data);
                    }

                }
