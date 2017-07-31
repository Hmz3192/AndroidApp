package com.example.lenovo.home.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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
        setContentView(R.layout.activity_mesaage_center);
        ib_login_back = (ImageButton) findViewById(R.id.ib_login_back);

        ib_login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
