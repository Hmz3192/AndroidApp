package com.example.lenovo.controller.activity.SettingDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lenovo.controller.activity.settingBean.WantBean;

import java.util.ArrayList;
import java.util.List;

/**
 * User--Hu mingzhi on 2017/9/23.
 * Created by ThinKPad
 */

public class WantDao {
    private WantDBHelper dbHelper;

    public WantDao(Context context) {
        dbHelper = new WantDBHelper(context);
    }

    // 添加需求到数据库
    public void addWant(WantBean wantBean) {

        // 获取数据库对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        // 执行添加操作
        ContentValues values = new ContentValues();
        values.put(WantTable.COL_TITLE, wantBean.getTitle());
        values.put(WantTable.COL_FIGURE, wantBean.getFigure());
        values.put(WantTable.COL_ESSAY, wantBean.getEssay());
        values.put(WantTable.COL_ESSAY1, wantBean.getEssay1());
        values.put(WantTable.COL_ESSAY2, wantBean.getEssay2());
        values.put(WantTable.COL_TIME, wantBean.getTime());
        values.put(WantTable.COL_USER, wantBean.getUser());
        values.put(WantTable.COL_URL, wantBean.getUrl());
        values.put(WantTable.COL_URL1, wantBean.getUrl1());
        values.put(WantTable.COL_URL2, wantBean.getUrl2());
        values.put(WantTable.COL_KIND, wantBean.getKind());

        db.replace(WantTable.TAB_NAME, null, values);
    }



    // 根据kind来获取
    public List<WantBean> getWants(String kind) {

        // 获取数据库链接
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 执行查询语句
        String sql = "select * from " + WantTable.TAB_NAME + " where " + WantTable.COL_KIND + " = " + kind;
        Cursor cursor = db.rawQuery(sql, null);

        List<WantBean> wantBeenList = new ArrayList<>();

        while (cursor.moveToNext()) {
            WantBean wantBean = new WantBean();
            wantBean.setEssay(cursor.getString(cursor.getColumnIndex(WantTable.COL_ESSAY)));
            wantBean.setEssay1(cursor.getString(cursor.getColumnIndex(WantTable.COL_ESSAY1)));
            wantBean.setEssay2(cursor.getString(cursor.getColumnIndex(WantTable.COL_ESSAY2)));

            wantBean.setFigure(cursor.getString(cursor.getColumnIndex(WantTable.COL_FIGURE)));

            wantBean.setTitle(cursor.getString(cursor.getColumnIndex(WantTable.COL_TITLE)));
            wantBean.setTime(cursor.getString(cursor.getColumnIndex(WantTable.COL_TIME)));

            wantBean.setUrl(cursor.getString(cursor.getColumnIndex(WantTable.COL_URL)));
            wantBean.setUrl1(cursor.getString(cursor.getColumnIndex(WantTable.COL_URL1)));
            wantBean.setUrl2(cursor.getString(cursor.getColumnIndex(WantTable.COL_URL2)));

            wantBean.setUser(cursor.getString(cursor.getColumnIndex(WantTable.COL_USER)));


            wantBeenList.add(wantBean);
        }

        // 关闭资源
        cursor.close();

        // 返回数据
        return wantBeenList;
    }




}
