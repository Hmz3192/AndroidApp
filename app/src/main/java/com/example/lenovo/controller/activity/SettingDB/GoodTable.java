package com.example.lenovo.controller.activity.SettingDB;

/**
 * User--Hu mingzhi on 2017/9/23.
 * Created by ThinKPad
 */

public class GoodTable {
    public static final String TAB_NAME = "tab_good";

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_DETAIL = "detail";
    public static final String COL_URL = "url";
    public static final String COL_PRICE = "price";




    public static final String CREATE_TAB = "create table "
            + TAB_NAME + " ("
            + COL_ID +" integer PRIMARY KEY AUTOINCREMENT,"
            + COL_TITLE + " text,"
            + COL_DETAIL + " text,"
            + COL_URL + " text,"
            + COL_PRICE + " text);";

}
