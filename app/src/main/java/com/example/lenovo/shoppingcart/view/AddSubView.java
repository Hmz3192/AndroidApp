package com.example.lenovo.shoppingcart.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;

/**
 * Created by Hmz on 2017/7/15.
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {
    private final Context mcontext;
    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;
    private int value = 1;
    private int maxvalue = 10;
    private int minvalue = 1;

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mcontext = context;
        /*把布局文件实例化，加载当前类中*/
        View.inflate(context, R.layout.add_sub_view, this);

        iv_sub = findViewById(R.id.iv_sub);
        iv_add = findViewById(R.id.iv_add);
        tv_value = findViewById(R.id.tv_value);

        int value = getValue();
        /*设置点击事件*/
        iv_sub.setOnClickListener(this );
        iv_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_sub:
                subNumber();
                break;
            case R.id.iv_add:
                addNumber();
                break;
        }
    }

    private void addNumber() {
        if (value < maxvalue) {
            value++;
        }
        setValue(value);
        if (onNumberChangerListener != null) {
            onNumberChangerListener.OnNumberchanger(value);

        }
    }

    private void subNumber() {
        if (value > minvalue) {
            value--;
        }
        setValue(value);
        if (onNumberChangerListener != null) {
            onNumberChangerListener.OnNumberchanger(value);

        }
    }

    public int getValue() {
       String valueStr = tv_value.getText().toString().trim();
        if (!TextUtils.isEmpty(valueStr)) {
            value = Integer.parseInt(valueStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(this.value+"");
    }

    public int getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(int maxvalue) {
        this.maxvalue = maxvalue;
    }

    public int getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(int minvalue) {
        this.minvalue = minvalue;
    }

    /*当数量发生变化的时候回调*/
    public interface OnNumberChangerListener {
        /*数据发生变化的时候回调*/
        public void OnNumberchanger(int value);
    }

    private OnNumberChangerListener onNumberChangerListener;

    /*设置数量变化的监听*/
    public void setOnNumberChangerListener(OnNumberChangerListener onNumberChangerListener) {
        this.onNumberChangerListener = onNumberChangerListener;
    }
}
