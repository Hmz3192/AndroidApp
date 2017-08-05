package com.example.lenovo.home.bean;

/**
 * User--Hu mingzhi on 2017/8/3.
 * Created by ThinKPad
 */

public class MoreDetailBean {
    private String cover_price;
    private String figure;
    private String name;
    private String product_id;

    public MoreDetailBean() {
    }

    public MoreDetailBean(String cover_price, String figure, String name, String product_id) {

        this.cover_price = cover_price;
        this.figure = figure;
        this.name = name;
        this.product_id = product_id;
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
}
