package com.example.lenovo.app.Bean;

/**
 * User--Hu mingzhi on 2017/8/7.
 * Created by ThinKPad
 */

public class QQUser {


    /**
     * time : 1
     * user : {"id":4,"pass":"3333","hxid":"3333","nickname":"一个好好人","photo":"1231321321321321321.jpg","sex":1,"signture":"111"}
     */

    private String time;
    private UserBean user;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * id : 4
         * pass : 3333
         * hxid : 3333
         * nickname : 一个好好人
         * photo : 1231321321321321321.jpg
         * sex : 1
         * signture : 111
         */

        private int id;
        private String pass;
        private String hxid;
        private String nickname;
        private String photo;
        private int sex;
        private String signture;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getHxid() {
            return hxid;
        }

        public void setHxid(String hxid) {
            this.hxid = hxid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getSignture() {
            return signture;
        }

        public void setSignture(String signture) {
            this.signture = signture;
        }
    }
}
