package com.example.lenovo.controller.activity.settingActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.myapplication.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;

public class LocationActivity extends Activity {
    private static final int REQUEST_CODE_MAP = 111;
    private TextView locat_edit,tv_location_1,tv_name1;
    private ImageButton ib_location_back;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_location);
        initView();
    }

    private void initView() {
        locat_edit = findViewById(R.id.locat_edit);
        ib_location_back = findViewById(R.id.ib_location_back);
        tv_location_1 = findViewById(R.id.tv_location_1);
        tv_name1 = findViewById(R.id.tv_name1);

        String currentUser = EMClient.getInstance().getCurrentUser();
        tv_name1.setText(currentUser);


        //步骤1：创建一个SharedPreferences接口对象
        SharedPreferences read = getSharedPreferences("location", MODE_WORLD_READABLE);
        //步骤2：获取文件中的值
        String location = read.getString("locationAddress","");
        if (location != null && !location.isEmpty()) {
            tv_location_1.setText(location);
        }


        /*编辑地址*/
        locat_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LocationActivity.this, EaseBaiduMapActivity.class);
                intent1.putExtra("sure", "确定");
                startActivityForResult(intent1, REQUEST_CODE_MAP);

            }
        });


        /*返回*/
        ib_location_back.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_MAP) {
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            String locationAddress = data.getStringExtra("address");
            if (locationAddress != null && !locationAddress.equals("")) {
//                sendLocationMessage(latitude, longitude, locationAddress);
                tv_location_1.setText(locationAddress);

                //步骤2-1：创建一个SharedPreferences.Editor接口对象，lock表示要写入的XML文件名，MODE_WORLD_WRITEABLE写操作
                SharedPreferences.Editor editor = getSharedPreferences("location", MODE_WORLD_WRITEABLE).edit();
                //步骤2-2：将获取过来的值放入文件
                editor.putString("locationAddress", locationAddress);
                //步骤3：提交
                editor.commit();

//                Log.i("info", latitude + "--------------" + "+++++++++" + longitude);
            } else {
                Toast.makeText(LocationActivity.this, R.string.unable_to_get_loaction, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
