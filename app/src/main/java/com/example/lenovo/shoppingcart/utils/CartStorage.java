package com.example.lenovo.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.lenovo.app.MyApplication;
import com.example.lenovo.home.bean.GoodsInfo;
import com.example.lenovo.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hmz on 2017/7/15.
 */

public class CartStorage {
    public static final String JSON_CART = "json_cart";
    private static CartStorage instance;
    private final Context mcontext;
    private SparseArray<GoodsInfo> sparseArray;
    private List<GoodsInfo> allData;

    private CartStorage(Context context) {
        this.mcontext = context;
        /*把之前存储的数据读取*/
        sparseArray = new SparseArray<>(100);

        ListToSparseArray();


    }

    /*从本地读取加入到sparesArray中*/
    private void ListToSparseArray() {
        List<GoodsInfo> goodsInList = getAllData();
        /*把列表数据转换成sparseArray*/
        for(int i = 0; i< goodsInList.size();i++) {
            GoodsInfo goodsInfo = goodsInList.get(i);
            sparseArray.put(Integer.parseInt(goodsInfo.getProduct_id()),goodsInfo);


        }

    }

    /*得到购物车实例*/
    public static CartStorage getInstance() {
        if (instance == null) {
            instance = new CartStorage(MyApplication.getMcontext());
        }
        return instance;

    }

    /*获取本地所有数据*/
    public List<GoodsInfo> getAllData() {
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        /*从本地获取*/
        String json = CacheUtils.getString(mcontext, JSON_CART);
        /*使用GSOn转换成列表*/
        /*判断是否为空*/
        if (!TextUtils.isEmpty(json)) {
            /*把String类型换成list*/
            goodsInfoList = new Gson().fromJson(json, new TypeToken<List<GoodsInfo>>() {
            }.getType());
        }
        return goodsInfoList;
    }

    public void addData(GoodsInfo goodsInfo) {

        /*添加到内存sparseArray*/
        /*如果当前数据已存在，则修改number递增*/
        GoodsInfo tempdata = sparseArray.get(Integer.parseInt(goodsInfo.getProduct_id()));
        if (tempdata != null) {
            /*内存中存在*/
            tempdata.setNumber(tempdata.getNumber()+goodsInfo.getNumber());
        } else {
            tempdata = goodsInfo;
//            tempdata.setNumber(1);
        }
        /*同步到内存中*/
        sparseArray.put(Integer.parseInt(tempdata.getProduct_id()), tempdata);

        /*同步到本地*/
        saveLocal();
    }

    /*删除数据*/
    public void deleteData(GoodsInfo goodsInfo) {
        /*内存中的删除*/
        sparseArray.delete(Integer.parseInt(goodsInfo.getProduct_id()));

        /*把内存的保持到本地*/
        saveLocal();
    }

    /*修改*/
    public void updateData(GoodsInfo goodsInfo) {
        sparseArray.put(Integer.parseInt(goodsInfo.getProduct_id()), goodsInfo);
        saveLocal();

    }

    /*存储到本地*/
    private void saveLocal() {
        /*spareseArray转换成list集合*/
        List<GoodsInfo> goodsInfoList = sparseToList();
        /*使用gson把列表转换成string类型*/
        String json = new Gson().toJson(goodsInfoList);
        /*string类型数据保存*/
        CacheUtils.saveString(mcontext,JSON_CART,json);
    }

    private List<GoodsInfo> sparseToList() {

        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        if (sparseArray != null && sparseArray.size() > 0) {
            for (int i = 0; i < sparseArray.size(); i++) {
                GoodsInfo goodsInfo = sparseArray.valueAt(i);
                goodsInfoList.add(goodsInfo);
            }
        }
        return goodsInfoList;
    }
}
