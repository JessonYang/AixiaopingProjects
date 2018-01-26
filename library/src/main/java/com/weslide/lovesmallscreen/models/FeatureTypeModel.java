package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/6/14.
 */
public class FeatureTypeModel extends BaseModel {

    @SerializedName("nfcp")
    NfcpModel nfcp;

    @SerializedName("fruit")
    NfcpModel fruit;

    @SerializedName("gift")
    NfcpModel gift;

    @SerializedName("yxypMaxPicture")
    NfcpModel yxypMaxPicture;

    @SerializedName("chbb")
    NfcpModel cate;

    @SerializedName("yxypMinPicture")
    NfcpModel yxypMinPicture;

    private String titleImg;

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public NfcpModel getTea() {
        return tea;
    }

    public void setTea(NfcpModel tea) {
        this.tea = tea;
    }

    public NfcpModel getNfcp() {
        return nfcp;
    }

    public void setNfcp(NfcpModel nfcp) {
        this.nfcp = nfcp;
    }

    public NfcpModel getFruit() {
        return fruit;
    }

    public void setFruit(NfcpModel fruit) {
        this.fruit = fruit;
    }

    public NfcpModel getGift() {
        return gift;
    }

    public void setGift(NfcpModel gift) {
        this.gift = gift;
    }

    public NfcpModel getYxypMaxPicture() {
        return yxypMaxPicture;
    }

    public void setYxypMaxPicture(NfcpModel yxypMaxPicture) {
        this.yxypMaxPicture = yxypMaxPicture;
    }

    public NfcpModel getCate() {
        return cate;
    }

    public void setCate(NfcpModel cate) {
        this.cate = cate;
    }

    public NfcpModel getYxypMinPicture() {
        return yxypMinPicture;
    }

    public void setYxypMinPicture(NfcpModel yxypMinPicture) {
        this.yxypMinPicture = yxypMinPicture;
    }

    @SerializedName("tea")
    NfcpModel tea;
}
