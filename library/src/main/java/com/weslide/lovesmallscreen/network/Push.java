package com.weslide.lovesmallscreen.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xu on 2016/6/2.
 * 用于接收推送
 */
public class Push<T> {
    @SerializedName("taskCode")
    private int taskCode;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private T data;


    /** 推送数据的用途标识 */
    public int getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(int taskCode) {
        this.taskCode = taskCode;
    }

    /** 对该用途的说明 */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /** 推送主要数据内容 */
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
