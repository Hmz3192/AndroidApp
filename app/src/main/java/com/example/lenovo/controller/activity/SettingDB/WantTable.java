package com.example.lenovo.controller.activity.SettingDB;

/**
 * User--Hu mingzhi on 2017/9/23.
 * Created by ThinKPad
 */

public class WantTable {

    public static final String TAB_NAME = "tab_want";

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_FIGURE = "figure";
    public static final String COL_ESSAY = "essay";
    public static final String COL_ESSAY1 = "essay1";
    public static final String COL_ESSAY2 = "essay2";
    public static final String COL_TIME = "time";
    public static final String COL_USER = "user";
    public static final String COL_URL = "url";
    public static final String COL_URL1 = "url1";
    public static final String COL_URL2 = "url2";
    public static final String COL_KIND = "kind";





    public static final String CREATE_TAB = "create table "
            + TAB_NAME + " ("
            + COL_ID +" integer PRIMARY KEY AUTOINCREMENT,"
            + COL_TITLE + " text,"
            + COL_FIGURE + " text,"
            + COL_ESSAY + " text,"
            + COL_ESSAY1 + " text,"
            + COL_ESSAY2 + " text,"
            + COL_TIME + " text,"
            + COL_USER + " text,"
            + COL_URL + " text,"
            + COL_URL1 + " text,"
            + COL_URL2 + " text,"
            + COL_KIND + " text);";
}
