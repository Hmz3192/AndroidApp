package com.example.lenovo.app.DB;

/**
 * User--Hu mingzhi on 2017/8/7.
 * Created by ThinKPad
 */

public class RatingUserTable {
    public static final String TAB_NAME = "tab_rate";

    public static final String COL_WORD = "ratingWord";
    public static final String COL_NAME = "user";




    public static final String CREATE_TAB = "create table "
            + TAB_NAME + " ("
            + COL_WORD + " text,"
            + COL_NAME + " text);";
}
