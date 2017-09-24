package com.example.lenovo.controller.activity.settingActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.common.design.CheckGroup;
import com.common.design.entity.OptionWrapper;
import com.example.lenovo.controller.activity.SettingDB.WantDao;
import com.example.lenovo.controller.activity.settingBean.WantBean;
import com.example.lenovo.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.redare.imagepicker.widget.ImagePickerView;

import java.util.Calendar;
import java.util.List;

public class WantActivity extends Activity {
    private CheckGroup mCheckGroup;
    private ImageView iv_back;
    private ImagePickerView imagePicker;

    private AppCompatButton bt_send;
    private EditText et_title,ed_word;
    private int  yourChoice;
    private WantBean wantBean = new WantBean();
    private WantDao wantDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_want);
        Fresco.initialize(this);
        wantDao = new WantDao(getApplication());
        initView();
        initListener();
    }

    private void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showSingleChoiceDialog();

            }
        });
    }

    private void showSingleChoiceDialog() {

        final String[] items = { "我以个人形式发布信息","我以团体形式发布信息" };
        yourChoice = 0;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(WantActivity.this);
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
//                            Toast.makeText(WantActivity.this,
//                                    "你选择了" + items[yourChoice],
//                                    Toast.LENGTH_SHORT).show();
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
        final EditText editText = new EditText(WantActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(WantActivity.this);
        inputDialog.setTitle("请输入您的社团群组账号，方便其他人联系群组").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(WantActivity.this,
//                                editText.getText().toString(),
//                                Toast.LENGTH_SHORT).show();
                        showInputName();


                    }
                }).show();
    }

    private void showInputName() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.dialog, null);
        final EditText editTextName = (EditText) textEntryView.findViewById(R.id.editTextName);
        final EditText editTextNumEditText = (EditText)textEntryView.findViewById(R.id.editTextNum);
        final AlertDialog.Builder ad1 = new AlertDialog.Builder(WantActivity.this);
        ad1.setTitle("请留下您的联系信息:");
        ad1.setIcon(android.R.drawable.star_big_on);
        ad1.setView(textEntryView);
        ad1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                insertSQL(editTextName.getText().toString());

                Log.i("111111", editTextName.getText().toString());
//                Toast.makeText(WantActivity.this, "++++"+editTextName.getText().toString()+editTextNumEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        ad1.show();// 显示对话框

    }

    private void insertSQL(String name) {
        wantBean.setTitle(et_title.getText().toString().trim());
        wantBean.setFigure(imagePicker.getImageList().get(0).toString());
        wantBean.setUrl(imagePicker.getImageList().get(0).toString());
        try {
            wantBean.setUrl1(imagePicker.getImageList().get(1).toString());
            wantBean.setUrl2(imagePicker.getImageList().get(2).toString());
        } catch (Exception e) {
            wantBean.setUrl1("");
            wantBean.setUrl2("");
        }


        List<Integer> checkedIndex = mCheckGroup.getCheckedIndex();
//        Toast.makeText(this, "+++" + String.valueOf(checkedIndex.get(0)), Toast.LENGTH_SHORT).show();

        wantBean.setKind(String.valueOf(checkedIndex.get(0)));

        wantBean.setUser(name);

        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        wantBean.setTime(year+"/"+month+"/"+date);


        String trim = ed_word.getText().toString().trim();
        String essay, essay1, essay2;
        int length = trim.length();
        essay = trim.substring(0, length / 2);
        essay1 = trim.substring(length / 2 + 1, length * 3 / 4);
        essay2 = trim.substring(length * 3 / 4 + 1, length - 1);
        wantBean.setEssay(essay);
        wantBean.setEssay1(essay1);
        wantBean.setEssay2(essay2);


        wantDao.addWant(wantBean);

    }

    /*单人hxid号*/
    private void SingleshowInputDialog() {
        final EditText editText = new EditText(WantActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(WantActivity.this);
        inputDialog.setTitle("请输入您的校淘商城账号，方便其他人联系您").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(WantActivity.this,
//                                editText.getText().toString(),
//                                Toast.LENGTH_SHORT).show();
                        showInputName();
                    }
                }).show();
    }
    private void initView() {
        ed_word = findViewById(R.id.ed_word);
        et_title = findViewById(R.id.et_title);
        imagePicker = findViewById(R.id.imagePicker);
        imagePicker.setNoImgResource(R.layout.add_img);//自定义imagePicker  add item样式
        imagePicker.setColumnNumber(4);//设置显示5列
        bt_send = findViewById(R.id.bt_send);
        iv_back = findViewById(R.id.iv_back);
        mCheckGroup = (CheckGroup) findViewById(R.id.checkGroup);
        OptionWrapper optionWrapper = new OptionWrapper(true);//Option的封装类
        optionWrapper.setOptions("二手求购", "旧物换新", "新品转卖", "海外代购");
        optionWrapper.setChecked(0);//默认选中position为1 2 4的选项
        mCheckGroup.setShape(CheckGroup.CIRCLE);//画方形,圆形传:CheckGroup.CIRCLE
        mCheckGroup.setOptionWrapper(optionWrapper);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imagePicker.onActivityResult(requestCode, resultCode, data);
    }
}
