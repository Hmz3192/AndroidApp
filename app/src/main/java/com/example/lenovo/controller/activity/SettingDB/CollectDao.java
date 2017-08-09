package com.example.lenovo.controller.activity.SettingDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lenovo.home.bean.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * User--Hu mingzhi on 2017/8/6.
 * Created by ThinKPad
 */

public class CollectDao {
    private CollectDBHelper dbHelper;

    public CollectDao(Context context) {
        dbHelper = new CollectDBHelper(context);
    }

    // 添加收藏到数据库
    public void addCollect(String id,String name,String price,String figure,String enable) {
        // 获取数据库对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        // 执行添加操作
        ContentValues values = new ContentValues();
        values.put(CollectTable.COL_ID, id);
        values.put(CollectTable.COL_NAME, name);
        values.put(CollectTable.COL_PRICE, price);
        values.put(CollectTable.COL_FIGURE, figure);
        values.put(CollectTable.COL_ENABLE, enable);

        db.replace(CollectTable.TAB_NAME, null, values);
    }

    /*更新收藏*/
    public void updateById(String id,String enable){

        if(id == null) {
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 执行添加操作
        ContentValues values = new ContentValues();
        values.put(CollectTable.COL_ID, id);
        values.put(CollectTable.COL_ENABLE, enable);

        db.update(CollectTable.TAB_NAME,values,CollectTable.COL_ID+"=?",new String[]{id});
    }

    // 获取所有收藏
    public List<GoodsInfo> getGoods() {

        // 获取数据库链接
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 执行查询语句
        String sql = "select * from " + CollectTable.TAB_NAME + " where "+ CollectTable.COL_ENABLE +" = '0'";
        Cursor cursor = db.rawQuery(sql, null);

        List<GoodsInfo> goods = new ArrayList<>();

        while (cursor.moveToNext()) {
            GoodsInfo good = new GoodsInfo();
            good.setProduct_id(cursor.getString(cursor.getColumnIndex(CollectTable.COL_ID)));
            good.setName(cursor.getString(cursor.getColumnIndex(CollectTable.COL_NAME)));
            good.setCover_price(cursor.getString(cursor.getColumnIndex(CollectTable.COL_PRICE)));
            good.setFigure(cursor.getString(cursor.getColumnIndex(CollectTable.COL_FIGURE)));
            good.setEnable(cursor.getString(cursor.getColumnIndex(CollectTable.COL_ENABLE)));

            goods.add(good);
        }

        // 关闭资源
        cursor.close();

        // 返回数据
        return goods;
    }

    // 获取单个收藏
    public GoodsInfo getGoodById(String id) {

        // 获取数据库链接
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 执行查询语句
        String sql = "select * from " + CollectTable.TAB_NAME + " where " + CollectTable.COL_ID + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        GoodsInfo good = new GoodsInfo();
        if(cursor != null) {
            while (cursor.moveToNext()) {
                good.setProduct_id(cursor.getString(cursor.getColumnIndex(CollectTable.COL_ID)));
                good.setName(cursor.getString(cursor.getColumnIndex(CollectTable.COL_NAME)));
                good.setCover_price(cursor.getString(cursor.getColumnIndex(CollectTable.COL_PRICE)));
                good.setFigure(cursor.getString(cursor.getColumnIndex(CollectTable.COL_FIGURE)));
                good.setEnable(cursor.getString(cursor.getColumnIndex(CollectTable.COL_ENABLE)));
            }
        }
        // 关闭资源
        cursor.close();

        // 返回数据
        return good;
    }
}
