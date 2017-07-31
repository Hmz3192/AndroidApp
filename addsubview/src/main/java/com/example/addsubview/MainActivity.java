package com.example.addsubview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private AddSubView addSubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addSubView = (AddSubView) findViewById(R.id.add_sub_view);
        addSubView.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {
            @Override
            public void OnNumberchanger(int value) {
//                Toast.makeText(MainActivity.this, "当前产品数量"+ value, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
