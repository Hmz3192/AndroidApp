package com.example.lenovo.setting.db;

/**
 * User--Hu mingzhi on 2017/8/5.
 * Created by ThinKPad
 */

public class OrderTableDB {
    public static final String TAB_NAME = "tab_order";
    public static final String COL_ID = "id";

    public static final String CREATE_TAB = "create table "
            + TAB_NAME + " ("
            + COL_ID + " text);";
}
