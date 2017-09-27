package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2017/3/1.
 */
public class UpdateStoreInfo extends BaseModel{

    @SerializedName("message")
    private String message;
    @SerializedName("userId")
    private int userId;
    @SerializedName("tips")
    private String tips;
    @SerializedName("remind")
    private String remind;
    @SerializedName("verifyStatus")
    private int verifyStatus;
    @SerializedName("sellerAccount")
    private String sellerAccount;

    public String getSellerPassword() {
        return sellerPassword;
    }

    public void setSellerPassword(String sellerPassword) {
        this.sellerPassword = sellerPassword;
    }

    public String getSellerAccount() {
        return sellerAccount;
    }

    public void setSellerAccount(String sellerAccount) {
        this.sellerAccount = sellerAccount;
    }

    @SerializedName("sellerPassword")
    private String sellerPassword;

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getTips() { return tips; }

    public void setTips(String tips) { this.tips = tips; }

    public String getRemind() { return remind; }

    public void setRemind(String remind) { this.remind = remind; }

    public int getVerifyStatus() { return verifyStatus; }

    public void setVerifyStatus(int verifyStatus) { this.verifyStatus = verifyStatus; }
}
