package com.example.lenovo.app.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * User--Hu mingzhi on 2017/8/7.
 * Created by ThinKPad
 */

public class RatingUserDB extends SQLiteOpenHelper {
    public RatingUserDB(Context context) {
        super(context, "rate.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(RatingUserTable.CREATE_TAB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
