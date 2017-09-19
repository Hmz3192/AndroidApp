package com.example.lenovo.app.Bean;

/**
 * User--Hu mingzhi on 2017/8/12.
 * Created by ThinKPad
 */

public class RatingBean {

    private String ratingWord;
    private String user;

    public RatingBean() {
    }

    public String getRatingWord() {
        return ratingWord;
    }

    public void setRatingWord(String ratingWord) {
        this.ratingWord = ratingWord;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public RatingBean(String ratingWord, String user) {

        this.ratingWord = ratingWord;
        this.user = user;
    }
}
