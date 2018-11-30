package com.cn.android_testtwo;

public class tokenBean {

    /**
     * code : 200
     * msg : 成功!
     * data : {"appId":"com.chat.peakchao","appkey":"00d91e8e0cca2b76f515926a36db68f5"}
     */

    private int code;
    private String msg;
    /**
     * appId : com.chat.peakchao
     * appkey : 00d91e8e0cca2b76f515926a36db68f5
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "tokenBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        private String appId;
        private String appkey;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppkey() {
            return appkey;
        }

        public void setAppkey(String appkey) {
            this.appkey = appkey;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "appId='" + appId + '\'' +
                    ", appkey='" + appkey + '\'' +
                    '}';
        }
    }
}
