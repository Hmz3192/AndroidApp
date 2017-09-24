package com.example.lenovo.home.bean;

/**
 * User--Hu mingzhi on 2017/9/23.
 * Created by ThinKPad
 */

public class Comment {

    private String userName;
    private String time;
    private String comm;
    private int number;
    private String url;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Comment(String userName, String time, String comm, int number, String url) {

        this.userName = userName;
        this.time = time;
        this.comm = comm;
        this.number = number;
        this.url = url;
    }

    public Comment() {

    }

}
