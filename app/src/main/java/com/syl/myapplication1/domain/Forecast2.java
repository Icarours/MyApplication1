package com.syl.myapplication1.domain;

import java.util.List;

/**
 * Created by Bright on 2018/7/31.
 *
 * @Describe
 * @Called
 */

public class Forecast2 {

    /**
     * data : {"yesterday":{"date":"30日星期一","high":"高温 31℃","fx":"东风","low":"低温 24℃","fl":"","type":"阵雨"},"city":"宝鸡","forecast":[{"date":"31日星期二","high":"高温 30℃","fengli":"","low":"低温 22℃","fengxiang":"东北风","type":"雷阵雨"},{"date":"1日星期三","high":"高温 31℃","fengli":"","low":"低温 22℃","fengxiang":"东北风","type":"小雨"},{"date":"2日星期四","high":"高温 32℃","fengli":"","low":"低温 23℃","fengxiang":"东北风","type":"雷阵雨"},{"date":"3日星期五","high":"高温 33℃","fengli":"","low":"低温 23℃","fengxiang":"东风","type":"多云"},{"date":"4日星期六","high":"高温 33℃","fengli":"","low":"低温 24℃","fengxiang":"东风","type":"多云"}],"ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","wendu":"25"}
     * status : 1000
     * desc : OK
     */

    private DataBean data;
    private int status;
    private String desc;

    @Override
    public String toString() {
        return "Forecast2{" +
                "data=" + data +
                ", status=" + status +
                ", desc='" + desc + '\'' +
                '}';
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class DataBean {
        /**
         * yesterday : {"date":"30日星期一","high":"高温 31℃","fx":"东风","low":"低温 24℃","fl":"","type":"阵雨"}
         * city : 宝鸡
         * forecast : [{"date":"31日星期二","high":"高温 30℃","fengli":"","low":"低温 22℃","fengxiang":"东北风","type":"雷阵雨"},{"date":"1日星期三","high":"高温 31℃","fengli":"","low":"低温 22℃","fengxiang":"东北风","type":"小雨"},{"date":"2日星期四","high":"高温 32℃","fengli":"","low":"低温 23℃","fengxiang":"东北风","type":"雷阵雨"},{"date":"3日星期五","high":"高温 33℃","fengli":"","low":"低温 23℃","fengxiang":"东风","type":"多云"},{"date":"4日星期六","high":"高温 33℃","fengli":"","low":"低温 24℃","fengxiang":"东风","type":"多云"}]
         * ganmao : 各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。
         * wendu : 25
         */

        private YesterdayBean yesterday;
        private String city;
        private String ganmao;
        private String wendu;
        private List<ForecastBean> forecast;

        @Override
        public String toString() {
            return "DataBean{" +
                    "yesterday=" + yesterday +
                    ", city='" + city + '\'' +
                    ", ganmao='" + ganmao + '\'' +
                    ", wendu='" + wendu + '\'' +
                    ", forecast=" + forecast +
                    '}';
        }

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 30日星期一
             * high : 高温 31℃
             * fx : 东风
             * low : 低温 24℃
             * fl :
             * type : 阵雨
             */

            private String date;
            private String high;
            private String fx;
            private String low;
            private String fl;
            private String type;

            @Override
            public String toString() {
                return "YesterdayBean{" +
                        "date='" + date + '\'' +
                        ", high='" + high + '\'' +
                        ", fx='" + fx + '\'' +
                        ", low='" + low + '\'' +
                        ", fl='" + fl + '\'' +
                        ", type='" + type + '\'' +
                        '}';
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class ForecastBean {
            /**
             * date : 31日星期二
             * high : 高温 30℃
             * fengli :
             * low : 低温 22℃
             * fengxiang : 东北风
             * type : 雷阵雨
             */

            private String date;
            private String high;
            private String fengli;
            private String low;
            private String fengxiang;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
