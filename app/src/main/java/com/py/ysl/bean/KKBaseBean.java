package com.py.ysl.bean;

import com.google.gson.annotations.SerializedName;

public class KKBaseBean<T> {
    @SerializedName("resCode")
    private String resCode;
    @SerializedName("resMsg")
    private String resMsg;
    @SerializedName("content")
    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }



    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }
}
