package com.example.lenovo.app.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lenovo.app.Bean.RatingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * User--Hu mingzhi on 2017/8/7.
 * Created by ThinKPad
 */

public class RatingUserDao {
    private RatingUserDB helper;

    public RatingUserDao(Context context) {
        helper = new RatingUserDB(context);

    }



    // 添加评论到数据库
    public void addMes(String word,String name) {
        // 获取数据库对象
        SQLiteDatabase db = helper.getWritableDatabase();


        // 执行添加操作
        ContentValues values = new ContentValues();
        values.put(RatingUserTable.COL_WORD, word);
        values.put(RatingUserTable.COL_NAME, name);

        db.insert(RatingUserTable.TAB_NAME, null, values);
    }

    // 获取所有品论
    public List<RatingBean> getRate() {

        // 获取数据库链接
        SQLiteDatabase db = helper.getWritableDatabase();

        // 执行查询语句
        String sql = "select * from " + RatingUserTable.TAB_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        List<RatingBean> rates = new ArrayList<>();

        while (cursor.moveToNext()) {
            RatingBean good = new RatingBean();
            good.setUser(cursor.getString(cursor.getColumnIndex(RatingUserTable.COL_NAME)));
            good.setRatingWord(cursor.getString(cursor.getColumnIndex(RatingUserTable.COL_WORD)));

            rates.add(good);
        }

        // 关闭资源
        cursor.close();

        // 返回数据
        return rates;
    }

}
