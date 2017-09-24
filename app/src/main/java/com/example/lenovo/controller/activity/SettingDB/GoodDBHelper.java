package com.example.lenovo.controller.activity.SettingDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * User--Hu mingzhi on 2017/9/23.
 * Created by ThinKPad
 */

public class GoodDBHelper  extends SQLiteOpenHelper {

    public GoodDBHelper(Context context) {
        super(context, "good.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(GoodTable.CREATE_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
