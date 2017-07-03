package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/7/15.
 * 退单审核
 */
public class BackOrderVerify extends BaseModel {

//    退单审核(backOrderVerify)：
//    backOrderItemId	订单项id
//    replyContent	回复内容
//    date	审核时间
//    该参数提交至后台时为空、由后台生成并填充
//    result	审核结果	0：拒绝退单
//    1：接受退单

    @SerializedName("money")
    /** 审核通过退款金额 */
    private String money;

    @SerializedName("backOrderItemId")
    /** 订单项id */
    private String backOrderItemId;
    @SerializedName("replyContent")
    /** 回复内容 */
    private String replyContent;
    @SerializedName("date")
    private String date;
    @SerializedName("result")
    /** 审核结果	0：拒绝退单 1：接受退单 */
    private String result;


    public String getBackOrderItemId() {
        return backOrderItemId;
    }

    public void setBackOrderItemId(String backOrderItemId) {
        this.backOrderItemId = backOrderItemId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
