package com.example.lenovo.model.db;

/**
 * User--Hu mingzhi on 2017/8/5.
 * Created by ThinKPad
 */

public class DeleteBean {

    private String ID = "id";
    private String DEL = "isDele";

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDEL() {
        return DEL;
    }

    public void setDEL(String DEL) {
        this.DEL = DEL;
    }

    public DeleteBean() {

    }

    public DeleteBean(String ID, String DEL) {

        this.ID = ID;
        this.DEL = DEL;
    }
}
