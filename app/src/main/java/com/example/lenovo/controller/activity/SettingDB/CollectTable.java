package com.example.lenovo.controller.activity.SettingDB;

/**
 * User--Hu mingzhi on 2017/8/6.
 * Created by ThinKPad
 */

public class CollectTable {
    public static final String TAB_NAME = "tab_newcollect";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PRICE = "price";
    public static final String COL_FIGURE = "figure";
    public static final String COL_ENABLE = "enable";




    public static final String CREATE_TAB = "create table "
            + TAB_NAME + " ("
            + COL_ID + " text primary key,"
            + COL_NAME + " text,"
            + COL_PRICE + " text,"
            + COL_FIGURE + " text,"
            + COL_ENABLE + " text);";

}
