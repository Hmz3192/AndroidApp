package com.example.lenovo.home.bean;

/**
 * User--Hu mingzhi on 2017/9/21.
 * Created by ThinKPad
 */

public class Test {

    private String title;
    private String mes;
    private int kind;

    @Override
    public String toString() {
        return "Test{" +
                "title='" + title + '\'' +
                ", mes='" + mes + '\'' +
                ", kind=" + kind +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Test() {


    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public Test(String title, String mes, int kind) {

        this.title = title;
        this.mes = mes;
        this.kind = kind;
    }
}
