package com.weslide.lovesmallscreen.view_yy.model;

import android.os.Parcel;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 自定义消息
 */

@MessageTag(value = "GoodsMsgModel", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeMessage extends MessageContent {

    private String msgImageUrl;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public String getGoodID() {
        return goodID;
    }

    private void setGoodID(String goodID) {
        this.goodID = goodID;
    }

    private String goodID;

    private String msgTitle;

    public String getLinkUrl() {
        return msgTargetUrl;
    }

    private void setLinkUrl(String linkUrl) {
        this.msgTargetUrl = linkUrl;
    }

    private String msgTargetUrl;

    /**
     * 发消息时调用，将自定义消息对象序列化为消息数据:
     * 首先将消息属性封装成json，再将json转换成byte数组
     */
    @Override
    public byte[] encode() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("goodId", getGoodID());
            jsonObject.put("msgImageUrl", getGoodPic());
            jsonObject.put("msgTitle", getContent());
            jsonObject.put("msgTargetUrl", getLinkUrl());
            jsonObject.put("user", new Gson().toJson(getUser()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            return jsonObject.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public CustomizeMessage() {

    }

    /**
     * 将收到的消息进行解析，byte -> json,再将json中的内容取出赋值给消息属性
     */
    public CustomizeMessage(byte[] data) {
        String jsonString = null;

        try {
            jsonString = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.has("goodId"))
                setGoodID(jsonObject.optString("goodId"));
            if (jsonObject.has("msgImageUrl"))
                setGoodPic(jsonObject.optString("msgImageUrl"));
            if (jsonObject.has("msgTitle"))
                setContent(jsonObject.optString("msgTitle"));
            if (jsonObject.has("msgTargetUrl"))
                setLinkUrl(jsonObject.optString("msgTargetUrl"));
            if (jsonObject.has("user"))
                setUser(new Gson().fromJson(jsonObject.optString("user"),User.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param in 通过初始化传入的Parcel，为消息属性赋值
     */
    private CustomizeMessage(Parcel in) {
        setGoodID(ParcelUtils.readFromParcel(in));
        setGoodPic(ParcelUtils.readFromParcel(in));
        setContent(ParcelUtils.readFromParcel(in));
        setLinkUrl(ParcelUtils.readFromParcel(in));
        setUser(new Gson().fromJson(ParcelUtils.readFromParcel(in),User.class));
    }

    public String getGoodPic() {
        return msgImageUrl;
    }

    private void setGoodPic(String goodPic) {
        this.msgImageUrl = goodPic;
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理
     */
    public static final Creator<CustomizeMessage> CREATOR = new Creator<CustomizeMessage>() {
        @Override
        public CustomizeMessage createFromParcel(Parcel source) {
            return new CustomizeMessage(source);
        }

        @Override
        public CustomizeMessage[] newArray(int size) {
            return new CustomizeMessage[size];
        }
    };

    /**
     * 描述了包含在Parcelable对象排列信息中的特殊对象的类型
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将类的数据写入到外部提供的Parcel中
     * @param dest 对象被写入的Parcel
     * @param flags 对象如何被写入的附加标志
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, getGoodID());
        ParcelUtils.writeToParcel(dest, getGoodPic());
        ParcelUtils.writeToParcel(dest, getContent());
        ParcelUtils.writeToParcel(dest, getLinkUrl());
        ParcelUtils.writeToParcel(dest, new Gson().toJson(getUser()));
    }

    public static CustomizeMessage obtain(String goodID,String goodPic, String content,String linkUrl,User user) {
        CustomizeMessage customizeMessage = new CustomizeMessage();
        customizeMessage.goodID = goodID;
        customizeMessage.msgImageUrl = goodPic;
        customizeMessage.msgTitle = content;
        customizeMessage.msgTargetUrl = linkUrl;
        customizeMessage.user = user;
        return customizeMessage;
    }

    public String getContent() {
        return this.msgTitle;
    }

    private void setContent(String mContent) {
        this.msgTitle = mContent;
    }

}
