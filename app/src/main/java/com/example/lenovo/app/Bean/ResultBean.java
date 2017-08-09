package com.example.lenovo.app.Bean;

/**
 * User--Hu mingzhi on 2017/8/7.
 * Created by ThinKPad
 */

public class ResultBean {

    String result;
    int reul;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getReul() {
        return reul;
    }

    public void setReul(int reul) {
        this.reul = reul;
    }

    public ResultBean() {

    }

    public ResultBean(String result, int reul) {

        this.result = result;
        this.reul = reul;
    }
}
