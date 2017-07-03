package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/6/14.
 */
public class TopClassifyModel extends BaseModel {

    @SerializedName("chbm")
    NfcpModel chbm;

    @SerializedName("xsms")
    NfcpModel xsms;

    @SerializedName("jfdh")
    NfcpModel jfdh;

    public NfcpModel getChbm() {
        return chbm;
    }

    public NfcpModel getXsms() {
        return xsms;
    }

    public void setXsms(NfcpModel xsms) {
        this.xsms = xsms;
    }

    public NfcpModel getJfdh() {
        return jfdh;
    }

    public void setJfdh(NfcpModel jfdh) {
        this.jfdh = jfdh;
    }

    public NfcpModel getZbdp() {
        return zbdp;
    }

    public void setZbdp(NfcpModel zbdp) {
        this.zbdp = zbdp;
    }

    public void setChbm(NfcpModel chbm) {
        this.chbm = chbm;
    }

    @SerializedName("zbdp")
    NfcpModel zbdp;
}
