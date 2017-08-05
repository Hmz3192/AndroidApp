package com.example.lenovo.setting.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lenovo.model.db.DeleteBean;

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
    public void addAccount(DeleteBean data) {
        // 获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();


        // 执行添加操作
        ContentValues values = new ContentValues();
        values.put(OrderTableDB.COL_ID, data.getID());
        values.put(OrderTableDB.COL_DEL, data.getDEL());

        db.replace(OrderTableDB.TAB_NAME, null, values);
    }

    // 根据环信id获取所有用户信息
    public DeleteBean getAccountById(String Id) {
        // 获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // 执行查询语句
        String sql = "select * from " + OrderTableDB.TAB_NAME + " where " + OrderTableDB.COL_ID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{Id});

        DeleteBean data = null;
        if(cursor.moveToNext()) {
            data = new DeleteBean();

            // 封装对象
            data.setID(cursor.getString(cursor.getColumnIndex(OrderTableDB.COL_ID)));
            data.setDEL(cursor.getString(cursor.getColumnIndex(OrderTableDB.COL_DEL)));
        }

        // 关闭资源
        cursor.close();

        // 返回数据
        return  data;
    }
}
