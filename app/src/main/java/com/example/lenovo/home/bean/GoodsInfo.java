package com.example.lenovo.home.bean;

import java.io.Serializable;

/**
 * Created by Hmz on 2017/7/15.
 */

public class GoodsInfo implements Serializable{
    //价格
    private String cover_price;
    //图片
    private String figure;
    //名称
    private String name;
    //id
    private String product_id;

    /*数量*/
    private int number = 1;

    /*是否被选中*/
    private boolean isSelected = true;

    public GoodsInfo() {
    }

    public GoodsInfo(String cover_price, String figure, String name, String product_id) {
        this.cover_price = cover_price;
        this.figure = figure;
        this.name = name;
        this.product_id = product_id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCover_price() {
        return cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {

        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", name='" + name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", number=" + number +
                ", isSelected=" + isSelected +
                '}';
    }
}
