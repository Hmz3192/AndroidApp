package com.example.lenovo.controller.activity.settingActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.lenovo.app.Bean.ResultBean;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.BitmapUtils;
import com.example.lenovo.utils.QQURL;
import com.hyphenate.chat.EMClient;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserActivity extends Activity implements View.OnClickListener {

    private ImageView la_back_user;
    private RelativeLayout title;
    private ImageView user_head_avatar;
    private ImageView user_head_headphoto_update;
    private TextView user_nickname;
    private ImageView ic_right_nick;
    private RelativeLayout rl_nickname;
    private ProgressDialog dialog;
    private static final int REQUESTCODE_PICK = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    private TextView textView3;
    private ImageView ic_right_sex;
    private RelativeLayout rl_sex;
    private TextView textView4;
    private ImageView ic_right_sig;
    private RelativeLayout rl_sig;
    private String sign;
    private String photo;
    private String nickName;
    private String hxid = EMClient.getInstance().getCurrentUser();
    private String sex;
    private TextView te_nick;
    private TextView tv_sex;
    private TextView tv_sig;
    private int res = 0;
    private String a = "0";
    private String b = "3";
    private String STORE_URL;
    private File file;
    private TextView te_hxid;
    private String hxidge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        photo = intent.getStringExtra("photo");
        nickName = intent.getStringExtra("nickName");
        sex = intent.getStringExtra("sex");
        sign = intent.getStringExtra("sign");
        hxidge = intent.getStringExtra("hxid");
        initView();

        setView(photo);
        setText(hxidge,nickName, sex, sign);
    }

    private void setText(String hxidge,String nickName, String sex, String sign) {
        te_hxid.setText(hxidge);
        if (nickName.equalsIgnoreCase(a)) {
            te_nick.setText("未设置");

        } else {
            try {
                te_nick.setText(nickName);
            } catch (Exception e) {
                te_nick.setText("未设置");
            }
        }

        if (sex.equalsIgnoreCase(b)) {
            tv_sex.setText("");
        } else if (sex.equalsIgnoreCase("1")) {
            tv_sex.setText("男");
        } else if (sex.equalsIgnoreCase("0")) {
            tv_sex.setText("女");
        } else {
            try {
                tv_sex.setText("");
            } catch (Exception e) {
                tv_sex.setText("");
            }
        }

        try {
            if (sign.equalsIgnoreCase(a)) {
                tv_sig.setText("");
            } else
            {
                try {
                    tv_sig.setText(sign);
                } catch (Exception e) {
                    tv_sig.setText("未设置");
                }
            }
        } catch (Exception e) {
            tv_sig.setText("");
        }


    }

    private void setView(String photo) {
        if (photo != a) {
            try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
                Picasso.with(UserActivity.this).load(photo).transform(new Transformation() {
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
                }).into(user_head_avatar);
            } catch (Exception e) {
                //use default avatar
//                Glide.with(context).load(username).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_appitem_del_btn_normal).into(imageView);
                Picasso.with(UserActivity.this).load(R.drawable.ease_default_avatar).transform(new Transformation() {
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
                }).into(user_head_avatar);
            }
        } else
            Picasso.with(UserActivity.this).load(R.drawable.ease_default_avatar).transform(new Transformation() {
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
            }).into(user_head_avatar);

    }

    private void initView() {
        te_hxid = findViewById(R.id.te_hxid);
        la_back_user = (ImageView) findViewById(R.id.la_back_user);
        title = (RelativeLayout) findViewById(R.id.title);
        user_head_avatar = (ImageView) findViewById(R.id.user_head_avatar);
        user_head_headphoto_update = (ImageView) findViewById(R.id.user_head_headphoto_update);
        user_nickname = (TextView) findViewById(R.id.user_nickname);
        ic_right_nick = (ImageView) findViewById(R.id.ic_right_nick);
        rl_nickname = (RelativeLayout) findViewById(R.id.rl_nickname);
        textView3 = (TextView) findViewById(R.id.textView3);
        ic_right_sex = (ImageView) findViewById(R.id.ic_right_sex);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        textView4 = (TextView) findViewById(R.id.textView4);
        ic_right_sig = (ImageView) findViewById(R.id.ic_right_sig);
        rl_sig = (RelativeLayout) findViewById(R.id.rl_sig);
        te_nick = (TextView) findViewById(R.id.te_nick);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_sig = (TextView) findViewById(R.id.tv_sig);


        user_head_avatar.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_sig.setOnClickListener(this);
        la_back_user.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_nickname:
                final EditText editText = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle(R.string.setting_nickname)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(editText)
                        .setPositiveButton(R.string.dl_ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nickString = editText.getText().toString();
                                if (TextUtils.isEmpty(nickString)) {
                                    Toast.makeText(UserActivity.this, getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                updateRemoteNick(nickString);
                            }
                        }).setNegativeButton(R.string.dl_cancel, null).show();
                break;
            case R.id.rl_sex:
                final EditText editText2 = new EditText(this);
                new AlertDialog.Builder(this).setTitle("设置性别").setIcon(android.R.drawable.ic_dialog_info).setView(editText2)
                        .setPositiveButton(R.string.dl_ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String sex = editText2.getText().toString();
                                if (TextUtils.isEmpty(sex)) {
                                    Toast.makeText(UserActivity.this, getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                updateRemoteSex(sex);
                            }
                        }).setNegativeButton(R.string.dl_cancel, null).show();
                break;
            case R.id.rl_sig:
                final EditText editText3 = new EditText(this);
                new AlertDialog.Builder(this).setTitle("设置签名").setIcon(android.R.drawable.ic_dialog_info).setView(editText3)
                        .setPositiveButton(R.string.dl_ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String sig = editText3.getText().toString();
                                if (TextUtils.isEmpty(sig)) {
                                    Toast.makeText(UserActivity.this, getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                updateRemoteSig(sig);
                            }


                        }).setNegativeButton(R.string.dl_cancel, null).show();
                break;
            case R.id.user_head_avatar:
                uploadHeadPhoto();
                break;
            case R.id.la_back_user:
                finish();
                break;

        }

    }

    private void uploadHeadPhoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dl_title_upload_photo);
        builder.setItems(new String[] { getString(R.string.dl_msg_take_photo), getString(R.string.dl_msg_local_upload) },
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                Toast.makeText(UserActivity.this, getString(R.string.toast_no_support),
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Intent pickIntent = new Intent(Intent.ACTION_PICK,null);
                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(pickIntent, REQUESTCODE_PICK);
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                Log.i("info", "------------------------------" + data + "++++++++++++++++++" + data.getData());
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Bitmap photo1 = BitmapUtils.zoom(photo, 90, 90);
            Bitmap photo2 = BitmapUtils.circleBitmap(photo1);
            photo1.recycle();

            Drawable drawable = new BitmapDrawable(getResources(), photo2);

            user_head_avatar.setImageDrawable(drawable);


            file = new File(STORE_URL);
            //保存到SharedPreferences
            Bitmap2Bytes(photo);
            photo.recycle();
//            uploadUserAvatar(Bitmap2Bytes(photo));
        }
    }

    private void uploadUserAvatar(final byte[] data) {
        dialog = ProgressDialog.show(this, getString(R.string.dl_update_photo), getString(R.string.dl_waiting));
        Log.i("info", String.valueOf(data));
//        file = new File(String.valueOf(data));
        OkHttpClient mOkHttpClent = new OkHttpClient();
        new Thread(new Runnable() {

            @Override
            public void run() {
                final String avatarUrl = null;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        if (avatarUrl != null) {
                            Toast.makeText(UserActivity.this, getString(R.string.toast_updatephoto_success),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserActivity.this, getString(R.string.toast_updatephoto_fail),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        }).start();

        dialog.show();
    }

//    保存图片到SharedPreferences
  /*  private void saveBitmapToSharedPreferences(Bitmap bitmap) {
        // Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //第三步:将String保持至SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("testSP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image", imageString);
        editor.commit();

        //上传头像
        setImgByStr(imageString,"");
    }*/

    /*转换压缩图片*/
    public void  Bitmap2Bytes(Bitmap bm){
//        将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = baos.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //第三步:将String保持至SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("testSP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image", imageString);
        editor.commit();
        //上传头像
        setImgByStr(imageString,"");
    }

    private void setImgByStr(String imageString, String imgName) {
      /*  dialog = ProgressDialog.show(this, getString(R.string.dl_update_photo), getString(R.string.dl_waiting));
        String url = "http://192.168.1.109:8080/img";
        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .url(url)
                .params(params)//
                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
        dialog.show();*/
        String save_url = QQURL.UPDATEPIC;
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/**");
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
        builder.addFormDataPart("hxid", EMClient.getInstance().getCurrentUser());

        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(save_url)//地址
                .post(requestBody)//添加请求体
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                Log.i("info", "上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("上传照片成功：response = " + response.body().string());
//                Toast.makeText(UserActivity.this, "上传成功", Toast.LENGTH_LONG).show();


            }
        });
    }



    //    ===============================/storage/emulated/0/DCIM/Camera/MTXX_20160722061809.jpg
//    ===============================/storage/emulated/0/DCIM/P60715-175053.jpg
//    ===============================/storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1471264222128-001.jpg
    private void startPhotoZoom(Uri uri) {
        Log.i("info", "------------------------------" + uri );
        STORE_URL = String.valueOf(getRealPathFromUri(this, uri));
        String temp[] = STORE_URL.replaceAll("\\\\","/").split("/");
        String url = null;
        if (temp.length > 1) {
            url = temp[temp.length - 1];
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        Log.i("info", "===============================" + url);
        // 裁剪图片意图
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
//        / 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    private void updateRemoteSig(final String sig) {
        dialog = ProgressDialog.show(this, getString(R.string.dl_update_nick), getString(R.string.dl_waiting));
        updateSig(sig);
        if (UserActivity.this.isFinishing()) {
            return;
        }
        if (res != 1) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(UserActivity.this, getString(R.string.toast_updatenick_fail), Toast.LENGTH_SHORT)
                            .show();
                    dialog.dismiss();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(UserActivity.this, getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
                            .show();
                    tv_sig.setText(sig);
                }
            });
        }
        res = 0;
    }

    private void updateSig(String sig) {
        String url = QQURL.UPDATESIG;
        OkHttpUtils
                .post()
                .url(url)
                .addParams("sig", sig)
                .addParams("hxid", hxid)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ResultBean result = JSON.parseObject(response, ResultBean.class);
                        res = result.getReul();
                    }
                });
    }

    private void updateRemoteSex(final String sex) {
        dialog = ProgressDialog.show(this, getString(R.string.dl_update_nick), getString(R.string.dl_waiting));
        updateSex(sex);
        if (UserActivity.this.isFinishing()) {
            return;
        }
        if (res != 1) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(UserActivity.this, getString(R.string.toast_updatenick_fail), Toast.LENGTH_SHORT)
                            .show();
                    dialog.dismiss();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(UserActivity.this, getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
                            .show();
                    tv_sex.setText(sex);
                }
            });
        }
        res = 0;
    }


    private void updateSex(String sex) {
        String url = QQURL.UPDATESEX;
        OkHttpUtils
                .post()
                .url(url)
                .addParams("sex", sex)
                .addParams("hxid", hxid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ResultBean result = JSON.parseObject(response, ResultBean.class);
                        res = result.getReul();
                    }
                });
    }

    private void updateRemoteNick(final String nickString) {
        dialog = ProgressDialog.show(this, getString(R.string.dl_update_nick), getString(R.string.dl_waiting));
        updateNick(nickString);
        if (UserActivity.this.isFinishing()) {
            return;
        }
        if (res != 1) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(UserActivity.this, getString(R.string.toast_updatenick_fail), Toast.LENGTH_SHORT)
                            .show();
                    dialog.dismiss();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(UserActivity.this, getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
                            .show();
                    te_nick.setText(nickString);
                }
            });
        }
        res = 0;
    }

    private void updateNick(String nickString) {
        String url = QQURL.UPDATENICK;
        OkHttpUtils
                .post()
                .url(url)
                .addParams("nickName", nickString)
                .addParams("hxid", hxid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ResultBean result = JSON.parseObject(response, ResultBean.class);
                        res = result.getReul();
                    }
                });
    }

    /*装换内存地址为路径地址*/
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
