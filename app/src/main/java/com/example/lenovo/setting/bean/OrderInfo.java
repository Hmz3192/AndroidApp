package com.example.lenovo.setting.bean;

import java.util.List;

/**
 * User--Hu mingzhi on 2017/8/5.
 * Created by ThinKPad
 */

public class OrderInfo {

    /**
     * code : 200
     * msg : 请求成功
     * result : {"buy_order":[{"id":"11111111101","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未付款"},{"id":"11111111102","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未付款"},{"id":"11111111103","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未付款"}],"send_order":[{"id":"11111111104","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未发货"},{"id":"11111111105","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未发货"},{"id":"11111111106","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未发货"}],"get_order":[{"id":"11111111107","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未收到"},{"id":"11111111108","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未收到"},{"id":"11111111109","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"未收到"}],"finish_order":[{"id":"11111111110","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"已完成"},{"id":"11111111111","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"已完成"},{"id":"11111111112","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"已完成"}],"back_order":[{"id":"11111111113","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"售后"},{"id":"11111111114","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"售后"},{"id":"11111111115","url":"/1477360350123.png","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","cover_price":"143.10","num":"2","status":"售后"}]}
     */

    private int code;
    private String msg;
    private ResultBean result;

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
        private List<BuyOrderBean> buy_order;
        private List<SendOrderBean> send_order;
        private List<GetOrderBean> get_order;
        private List<FinishOrderBean> finish_order;
        private List<BackOrderBean> back_order;

        public List<BuyOrderBean> getBuy_order() {
            return buy_order;
        }

        public void setBuy_order(List<BuyOrderBean> buy_order) {
            this.buy_order = buy_order;
        }

        public List<SendOrderBean> getSend_order() {
            return send_order;
        }

        public void setSend_order(List<SendOrderBean> send_order) {
            this.send_order = send_order;
        }

        public List<GetOrderBean> getGet_order() {
            return get_order;
        }

        public void setGet_order(List<GetOrderBean> get_order) {
            this.get_order = get_order;
        }

        public List<FinishOrderBean> getFinish_order() {
            return finish_order;
        }

        public void setFinish_order(List<FinishOrderBean> finish_order) {
            this.finish_order = finish_order;
        }

        public List<BackOrderBean> getBack_order() {
            return back_order;
        }

        public void setBack_order(List<BackOrderBean> back_order) {
            this.back_order = back_order;
        }

        public static class BuyOrderBean {
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

        public static class SendOrderBean {
            /**
             * id : 11111111104
             * url : /1477360350123.png
             * name : 现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪
             * cover_price : 143.10
             * num : 2
             * status : 未发货
             */

            private String id;
            private String url;
            private String name;
            private String cover_price;
            private String num;
            private String status;

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

        public static class GetOrderBean {
            /**
             * id : 11111111107
             * url : /1477360350123.png
             * name : 现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪
             * cover_price : 143.10
             * num : 2
             * status : 未收到
             */

            private String id;
            private String url;
            private String name;
            private String cover_price;
            private String num;
            private String status;

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

        public static class FinishOrderBean {
            /**
             * id : 11111111110
             * url : /1477360350123.png
             * name : 现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪
             * cover_price : 143.10
             * num : 2
             * status : 已完成
             */

            private String id;
            private String url;
            private String name;
            private String cover_price;
            private String num;
            private String status;

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

        public static class BackOrderBean {
            /**
             * id : 11111111113
             * url : /1477360350123.png
             * name : 现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪
             * cover_price : 143.10
             * num : 2
             * status : 售后
             */

            private String id;
            private String url;
            private String name;
            private String cover_price;
            private String num;
            private String status;

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