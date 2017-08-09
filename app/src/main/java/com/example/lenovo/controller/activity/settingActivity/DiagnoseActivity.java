package com.example.lenovo.controller.activity.settingActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.hyphenate.chat.EMClient;

public class DiagnoseActivity extends Activity {

    private ImageView la_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_diagnose);
        TextView currentVersion = (TextView) findViewById(R.id.tv_version);
        la_back = findViewById(R.id.la_back);
        la_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back1();

            }
        });
        String strVersion = "";
        try {
            strVersion = getVersionName();
        } catch (Exception e) {
        }
        if (!TextUtils.isEmpty(strVersion))
            currentVersion.setText("V" + strVersion);
        else{
            String st = getResources().getString(R.string.Not_Set);
            currentVersion.setText(st);}


    }
    private String getVersionName() throws Exception {
        return EMClient.getInstance().VERSION;
    }


    public void back1() {
        finish();
    }

}
