package com.example.lenovo.controller.activity.settingBean;

/**
 * User--Hu mingzhi on 2017/9/23.
 * Created by ThinKPad
 */

public class Good {

    private String id;
    private String title;
    private String detail;
    private String url;
    private String price;
    private String num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Good() {
    }

    @Override
    public String toString() {
        return "Good{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", url='" + url + '\'' +
                ", price='" + price + '\'' +
                ", num='" + num + '\'' +
                '}';
    }

    public Good(String id, String title, String detail, String url, String price, String num) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.url = url;
        this.price = price;
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}
