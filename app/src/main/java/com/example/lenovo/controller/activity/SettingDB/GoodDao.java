package com.example.lenovo.controller.activity.SettingDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lenovo.controller.activity.settingBean.Good;
import com.example.lenovo.home.bean.TypeListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * User--Hu mingzhi on 2017/9/23.
 * Created by ThinKPad
 */

public class GoodDao {
    private GoodDBHelper dbHelper;

    public GoodDao(Context context) {
        dbHelper = new GoodDBHelper(context);

    }


    // 添加good到数据库
    public void addGood(Good good) {
        // 获取数据库对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        // 执行添加操作
        ContentValues values = new ContentValues();
        values.put(GoodTable.COL_TITLE, good.getTitle());
        values.put(GoodTable.COL_DETAIL, good.getDetail());
        values.put(GoodTable.COL_URL, good.getUrl());
        values.put(GoodTable.COL_PRICE, good.getPrice());

        db.replace(GoodTable.TAB_NAME, null, values);
    }


    // 获取所有商品
    public List<TypeListBean.ResultBean.PageDataBean> getGoods() {

        // 获取数据库链接
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 执行查询语句
        String sql = "select * from " + GoodTable.TAB_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        List<TypeListBean.ResultBean.PageDataBean> goods = new ArrayList<>();

        while (cursor.moveToNext()) {
            TypeListBean.ResultBean.PageDataBean good = new TypeListBean.ResultBean.PageDataBean();
            good.setProduct_id(cursor.getString(cursor.getColumnIndex(GoodTable.COL_ID)));
            good.setName(cursor.getString(cursor.getColumnIndex(GoodTable.COL_TITLE)));
//            good.s(cursor.getString(cursor.getColumnIndex(GoodTable.COL_DETAIL)));
            good.setFigure(cursor.getString(cursor.getColumnIndex(GoodTable.COL_URL)));
            good.setCover_price(cursor.getString(cursor.getColumnIndex(GoodTable.COL_PRICE)));

            goods.add(good);
        }

        // 关闭资源
        cursor.close();

        // 返回数据
        return goods;
    }
}
