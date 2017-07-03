package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/12/30.
 * 提现
 */
public class Withdrawals extends BaseModel{
    @SerializedName("createtime")
    private String createtime;

    @SerializedName("remark")
    private String remark;

    @SerializedName("money")
    private String money;

    @SerializedName("payState")
    private String payState;

    @SerializedName("counterFee")
    private String counterFee;

    @SerializedName("bankName")
    private String bankName;

    @SerializedName("totalMoney")
    private String totalMoney;

    @SerializedName("bankCode")
    private String bankCode;

    @SerializedName("title")
    private String title;

    @SerializedName("address")
    private String address;

    @SerializedName("name")
    private String name;

    @SerializedName("account")
    private String account;

    @SerializedName("minMoney")
    private String minMoney;

    public String getCreatetime() { return createtime; }

    public void setCreatetime(String createtime) { this.createtime = createtime; }

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }

    public String getMoney() { return money; }

    public void setMoney(String money) { this.money = money; }

    public String getPayState() { return payState; }

    public void setPayState(String payState) { this.payState = payState; }

    public String getCounterFee() { return counterFee; }

    public void setCounterFee(String counterFee) { this.counterFee = counterFee; }

    public String getBankName() { return bankName; }

    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getTotalMoney() { return totalMoney; }

    public void setTotalMoney(String totalMoney) { this.totalMoney = totalMoney; }

    public String getBankCode() { return bankCode; }

    public void setBankCode(String bankCode) { this.bankCode = bankCode; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAccount() { return account; }

    public void setAccount(String account) { this.account = account; }

    public String getMinMoney() { return minMoney; }

    public void setMinMoney(String minMoney) { this.minMoney = minMoney; }
}
