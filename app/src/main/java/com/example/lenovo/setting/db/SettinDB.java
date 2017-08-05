package com.example.lenovo.setting.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * User--Hu mingzhi on 2017/8/5.
 * Created by ThinKPad
 */

public class SettinDB extends SQLiteOpenHelper {
    public SettinDB(Context context) {
        super(context, "order.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
