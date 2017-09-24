package com.example.lenovo.controller.activity.settingBean;

/**
 * User--Hu mingzhi on 2017/9/23.
 * Created by ThinKPad
 */

public class WantBean {

    private String title;
    private String figure;
    private String essay;
    private String essay1;
    private String essay2;
    private String time;
    private String user;
    private String url;
    private String url1;
    private String url2;
    private String id;
    private String kind;

    @Override
    public String toString() {
        return "WantBean{" +
                "title='" + title + '\'' +
                ", figure='" + figure + '\'' +
                ", essay='" + essay + '\'' +
                ", essay1='" + essay1 + '\'' +
                ", essay2='" + essay2 + '\'' +
                ", time='" + time + '\'' +
                ", user='" + user + '\'' +
                ", url='" + url + '\'' +
                ", url2='" + url2 + '\'' +
                ", url1='" + url1 + '\'' +
                ", id='" + id + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getEssay() {
        return essay;
    }

    public void setEssay(String essay) {
        this.essay = essay;
    }

    public String getEssay1() {
        return essay1;
    }

    public void setEssay1(String essay1) {
        this.essay1 = essay1;
    }

    public String getEssay2() {
        return essay2;
    }

    public void setEssay2(String essay2) {
        this.essay2 = essay2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public WantBean(String title, String figure, String essay, String essay1, String essay2, String time, String user, String url, String url2, String url1, String id, String kind) {

        this.title = title;
        this.figure = figure;
        this.essay = essay;
        this.essay1 = essay1;
        this.essay2 = essay2;
        this.time = time;
        this.user = user;
        this.url = url;
        this.url2 = url2;
        this.url1 = url1;
        this.id = id;
        this.kind = kind;
    }

    public WantBean() {

    }
}
