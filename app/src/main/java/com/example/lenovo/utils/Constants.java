package com.example.lenovo.utils;

/**
 * Created by Hmz on 2017/7/5.
 */

/*配置所有页面联网地址*/
public class Constants {

        public static String Base_URL = "http://101.201.234.133:8080/Andro";
//    public static String Base_URL = "http://192.168.1.108:8080/atguigu";

    public static String BASE_URL_JSON = Base_URL + "/json";


    public static String HOME_URL = BASE_URL_JSON + "/HOME_URL.json";
    public static String BASE_URL_IMAGE = Base_URL + "/img";
    /* public static String Base_URL = "http://101.201.234.133:8111/AndroidCon";
     public static String BASE_URL_IMAGE = "http://101.201.234.133:8080/img";
     public static String HOME_URL = Base_URL + "/new/andro.do";*/
    public static Boolean isBackHome = false;


    //服饰
    public static final String CLOSE_STORE = BASE_URL_JSON + "/CLOSE_STORE.json";
    //游戏
    public static final String GAME_STORE = BASE_URL_JSON + "/GAME_STORE.json";
    //动漫
    public static final String COMIC_STORE = BASE_URL_JSON + "/COMIC_STORE.json";
    //cosplay
    public static final String COSPLAY_STORE = BASE_URL_JSON + "/COSPLAY_STORE.json";
    //古风
    public static final String GUFENG_STORE = BASE_URL_JSON + "/GUFENG_STORE.json";
    //漫展
    public static final String STICK_STORE = BASE_URL_JSON + "/STICK_STORE.json";
    //文具
    public static final String WENJU_STORE = BASE_URL_JSON + "/WENJU_STORE.json";
    //零食
    public static final String FOOD_STORE = BASE_URL_JSON + "/FOOD_STORE.json";
    //首饰厂
    public static final String SHOUSHI_STORE = BASE_URL_JSON + "/SHOUSHI_STORE.json";

}
