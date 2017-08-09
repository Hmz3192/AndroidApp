package com.example.lenovo.setting.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lenovo.setting.bean.DeleteBean;

/**
 * User--Hu mingzhi on 2017/8/5.
 * Created by ThinKPad
 */

public class OrderDao {
    private SettinDB mHelper;

    public OrderDao(Context context) {
        mHelper = new SettinDB(context);
    }


    // 删除用户到数据库
    public void addAccount(String Id) {
        // 获取数据库对象
        SQLiteDatabase db = mHelper.getWritableDatabase();


        // 执行添加操作
        ContentValues values = new ContentValues();
        values.put(OrderTableDB.COL_ID,Id);

        db.replace(OrderTableDB.TAB_NAME, null, values);
    }

    // 删除用户到数据库
    public void delAccount() {
        // 获取数据库对象
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.delete(OrderTableDB.TAB_NAME,null,null);
    }

    public Boolean getAccountById(String Id) {
        // 获取数据库对象
        SQLiteDatabase db = mHelper.getWritableDatabase();

        // 执行查询语句
        String sql = "select * from " + OrderTableDB.TAB_NAME + " where " + OrderTableDB.COL_ID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{Id});

        DeleteBean data = null;
        if(cursor.moveToNext()) {
            data = new DeleteBean();

            // 封装对象
            data.setID(cursor.getString(cursor.getColumnIndex(OrderTableDB.COL_ID)));
        }

        // 关闭资源
        cursor.close();
        if (data == null || data.getID().isEmpty()) {
            return true;
        }
        // 返回数据
        return  false;
    }
}
