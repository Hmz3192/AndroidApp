package com.example.lenovo.setting.bean;

import java.util.List;

/**
 * User--Hu mingzhi on 2017/8/5.
 * Created by ThinKPad
 */

public class AllOrder {


    /**
     * code : 200
     * msg : 请求成功
     * result : {"all_order":[{"id":"11111111101","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未付款"},{"id":"11111111102","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未付款"},{"id":"11111111103","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未付款"},{"id":"11111111104","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未发货"},{"id":"11111111105","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未发货"},{"id":"11111111106","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未发货"},{"id":"11111111107","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未收到"},{"id":"11111111108","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未收到"},{"id":"11111111109","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未收到"},{"id":"11111111110","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"已完成"},{"id":"11111111111","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"已完成"},{"id":"11111111112","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"已完成"},{"id":"11111111113","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"售后"},{"id":"11111111114","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"售后"},{"id":"11111111115","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"售后"}]}
     */

    private int code;
    private String msg;
    private ResultBean result;
    public AllOrder() {
    }

    public AllOrder(int code, String msg, ResultBean result) {

        this.code = code;
        this.msg = msg;
        this.result = result;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        public ResultBean() {
        }

        public ResultBean(List<AllOrderBean> all_order) {

            this.all_order = all_order;
        }

        private List<AllOrderBean> all_order;

        public List<AllOrderBean> getAll_order() {
            return all_order;
        }

        public void setAll_order(List<AllOrderBean> all_order) {
            this.all_order = all_order;
        }

        public static class AllOrderBean {


            /**
             * id : 11111111101
             * url : /1477360350123.png
             * name : 现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪
             * cover_price : 143.10
             * num : 2
             * status : 未付款
             */

            private String id;
            private String url;
            private String name;
            private String cover_price;
            private String num;
            private String status;

            public AllOrderBean(String id, String url, String name, String cover_price, String num, String status) {
                this.id = id;
                this.url = url;
                this.name = name;
                this.cover_price = cover_price;
                this.num = num;
                this.status = status;
            }

            public AllOrderBean() {
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCover_price() {
                return cover_price;
            }

            public void setCover_price(String cover_price) {
                this.cover_price = cover_price;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
