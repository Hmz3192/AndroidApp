package com.example.lenovo.model.bean;

/**
 * Created by Administrator on 2016/9/23.
 */
// 用户账号信息的bean类
public class UserInfoBean {

    private String name;// 用户名称
    private String hxid;// 环信id
    private String nick;// 用户的昵称
    private String photo;// 头像

    public UserInfoBean() {

    }

    public UserInfoBean(String name) {
        this.name = name;
        this.hxid = name;
        this.nick = name;
    }

    public UserInfoBean(String hxid, String nick, String photo) {
        this.nick = nick;
        this.hxid = name;
        this.nick = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "name='" + name + '\'' +
                ", hxid='" + hxid + '\'' +
                ", nick='" + nick + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
