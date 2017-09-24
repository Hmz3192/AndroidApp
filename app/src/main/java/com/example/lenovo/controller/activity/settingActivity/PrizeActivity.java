package com.example.lenovo.controller.activity.settingActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.ScratchView;

public class PrizeActivity extends Activity implements View.OnClickListener {

    private ImageButton ib_location_back;
    private ScratchView mScratchView,mScratchView1,mScratchView2,mScratchView3;
    private FrameLayout fab_labe1,fab_labe2,fab_labe3,fab_labe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_prize);

        initView();
    }

    private void initView() {
        ib_location_back = findViewById(R.id.ib_location_back);
        mScratchView = (ScratchView) findViewById(R.id.scratch_view);
        mScratchView1 = (ScratchView) findViewById(R.id.scratch_view1);

        ib_location_back.setOnClickListener(this);
        mScratchView.setOnClickListener(this);
        mScratchView1.setOnClickListener(this);
        mScratchView.setEraserSize(50);
        mScratchView.setWatermark(R.mipmap.alipay);
        mScratchView.setMaxPercent(50);

        mScratchView1.setEraserSize(50);
        mScratchView1.setWatermark(R.mipmap.alipay);
        mScratchView1.setMaxPercent(50);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scratch_view:
                mScratchView.setEraseStatusListener(new ScratchView.EraseStatusListener() {
                    @Override
                    public void onProgress(int percent) {
//                        String text = String.format(mPercentFormat, percent);
//                        mPercentView.setText(text);

                    }

                    @Override
                    public void onCompleted(View view) {
//                        Toast.makeText(ScratchDemoActivity.this, "completed !", Toast.LENGTH_SHORT).show();

                    }
                });
                break;
            case R.id.scratch_view1:
                mScratchView1.setEraseStatusListener(new ScratchView.EraseStatusListener() {
                    @Override
                    public void onProgress(int percent) {
//                        String text = String.format(mPercentFormat, percent);
//                        mPercentView.setText(text);

                    }

                    @Override
                    public void onCompleted(View view) {
//                        Toast.makeText(ScratchDemoActivity.this, "completed !", Toast.LENGTH_SHORT).show();

                    }
                });
                break;
            case R.id.ib_location_back:
                finish();
                break;

        }
    }
}
