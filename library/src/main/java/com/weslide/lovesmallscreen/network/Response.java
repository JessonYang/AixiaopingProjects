package com.weslide.lovesmallscreen.network;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/4/26.
 * 数据响应载体
 */
public class Response<T> extends BaseModel {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;


    @SerializedName("data")
    private T data;




    /**
     * 数据状态
     * 包括：STATUS_OK,STATUS_ERROR 两种
     */
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 主体数据
     */
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /** 返回信息 */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
