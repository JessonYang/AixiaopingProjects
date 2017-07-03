package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;
import com.weslide.lovesmallscreen.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2016/7/15.
 * 退单信息
 */
public class BackOrderInfo extends BaseModel {

//    backOrderItemId	订单项Id
//    money	退还金额	和积分两者取其一
//    score	退还积分	和金额两者取其一
//    content	退单原因
//    date	退单申请时间
//    该参数提交至后台时为空、由后台生成并填充
//    images	截图描述	[String,String]
//    drawbackWay	退款方式	0：原路返回
//    100:钱包
//    200:支付宝
//    300:微信

    @SerializedName("orderItemId")
    /** 订单项Id */
    private String orderItemId;
    @SerializedName("money")
    /** 退还金额	和积分两者取其一 */
    private String money;
    @SerializedName("score")
    /** 退还积分	和金额两者取其一 */
    private String score;
    @SerializedName("content")
    /** 退单原因 */
    private String content;
    @SerializedName("date")
    /** 退单申请时间 该参数提交至后台时为空、由后台生成并填充 */
    private String date;
    @SerializedName("images")
    /** 截图描述	[String,String] */
    private ArrayList<String> images;
    @SerializedName("drawbackWay")
    /** 退款方式	0：原路返回 100:钱包 200:支付宝 300:微信 */
    private String drawbackWay;


    public String getValue() {
        if (StringUtils.isNumberEmpty(money)) {
            return score + "积分";
        } else {
            return money + "元";
        }
    }


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getDrawbackWay() {
        return drawbackWay;
    }

    public void setDrawbackWay(String drawbackWay) {
        this.drawbackWay = drawbackWay;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }
}
