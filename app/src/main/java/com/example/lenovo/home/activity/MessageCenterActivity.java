package com.example.lenovo.home.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.lenovo.myapplication.R;

/**
 * Created by Hmz on 2017/7/28.
 */

public class MessageCenterActivity extends Activity {
    private ImageButton ib_login_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_mesaage_center);
        ib_login_back = (ImageButton) findViewById(R.id.ib_login_back);

        ib_login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_up_in,
                        R.anim.slide_down_out);
            }
        });
    }
}
