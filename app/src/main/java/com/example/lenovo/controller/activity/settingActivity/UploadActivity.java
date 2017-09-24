package com.example.lenovo.controller.activity.settingActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.controller.activity.SettingDB.GoodDao;
import com.example.lenovo.controller.activity.settingBean.Good;
import com.example.lenovo.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;
import com.redare.imagepicker.widget.ImagePickerView;

public class UploadActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_CODE_MAP = 111;
    private ImageView iv_back_user;
    private ImagePickerView imagePicker;
    private TextView tv_location;
    private AppCompatButton bt_upload;
    private int  yourChoice;
    private Good good = new Good();
    private EditText ed_word,et_title,et_price;
    private GoodDao goodDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_upload);
        Fresco.initialize(this);
        goodDao = new GoodDao(getApplication());

        //步骤1：创建一个SharedPreferences接口对象
//        SharedPreferences read = getSharedPreferences("location", MODE_WORLD_READABLE);

        iv_back_user = (ImageView) findViewById(R.id.iv_back_user);
        imagePicker = (ImagePickerView) findViewById(R.id.imagePicker);
        bt_upload = findViewById(R.id.bt_upload);
        tv_location = findViewById(R.id.tv_location);
        ed_word = findViewById(R.id.ed_word);
        et_price = findViewById(R.id.et_price);
        et_title = findViewById(R.id.et_title);
        iv_back_user.setOnClickListener(this);
        bt_upload.setOnClickListener(this);
        imagePicker.setNoImgResource(R.layout.add_img);//自定义imagePicker  add item样式
        imagePicker.setColumnNumber(4);//设置显示5列
        tv_location.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_user:
                finish();
                break;
            case R.id.tv_location:
                Intent intent1 = new Intent(UploadActivity.this, EaseBaiduMapActivity.class);
                intent1.putExtra("sure", "确定");
                startActivityForResult(intent1, REQUEST_CODE_MAP);
                break;
            case R.id.bt_upload:
                showSingleChoiceDialog();
                break;

        }
    }

    private void showSingleChoiceDialog() {
        final String[] items = { "我以个人形式售卖","我以团体形式售卖" };
        yourChoice = 0;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(UploadActivity.this);
        singleChoiceDialog.setTitle("请选择您的售卖形式");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("下一步",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
//                            Toast.makeText(UploadActivity.this,
//                                    "你选择了" + items[yourChoice],
//                                    Toast.LENGTH_SHORT).show();
                            good.setDetail(ed_word.getText().toString().trim());
                            good.setPrice(et_price.getText().toString().trim());
                            good.setTitle(et_title.getText().toString().trim());
                            good.setUrl(imagePicker.getImageList().get(0).toString());
                            goodDao.addGood(good);

                            if (yourChoice == 0) {
                                SingleshowInputDialog();
                            } else if (yourChoice == 1) {
                                TeamshowInputDialog();
                            }


                        }
                    }


                });
        singleChoiceDialog.show();
    }


    /*群号*/
    private void TeamshowInputDialog() {
        final EditText editText = new EditText(UploadActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(UploadActivity.this);
        inputDialog.setTitle("请输入您的社团群组账号，方便买家联系群组").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(UploadActivity.this,
//                                editText.getText().toString(),
//                                Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }).show();

    }

    /*单人hxid号*/
    private void SingleshowInputDialog() {
        final EditText editText = new EditText(UploadActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(UploadActivity.this);
        inputDialog.setTitle("请输入您的校淘商城账号，方便买家联系您").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(UploadActivity.this,
//                                editText.getText().toString(),
//                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imagePicker.onActivityResult(requestCode,resultCode,data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_MAP) {
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            String locationAddress = data.getStringExtra("address");
            if (locationAddress != null && !locationAddress.equals("")) {
//                sendLocationMessage(latitude, longitude, locationAddress);
                tv_location.setText(locationAddress);

                //步骤2-1：创建一个SharedPreferences.Editor接口对象，lock表示要写入的XML文件名，MODE_WORLD_WRITEABLE写操作
//                SharedPreferences.Editor editor = getSharedPreferences("location", MODE_WORLD_WRITEABLE).edit();
                //步骤2-2：将获取过来的值放入文件
//                editor.putString("locationAddress", locationAddress);
                //步骤3：提交
//                editor.commit();

//                Log.i("info", latitude + "--------------" + "+++++++++" + longitude);
            } else {
                Toast.makeText(UploadActivity.this, R.string.unable_to_get_loaction, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
