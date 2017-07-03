package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Administrator on 2017/3/15.
 */
public class ExchangeAll extends BaseModel {
    @SerializedName("sign")
    Sign sign;
    @SerializedName("exchange")
    Exchange exchange;
    @SerializedName("header")
    Header header;

    public SignUp getSignUp() {
        return signUp;
    }

    public void setSignUp(SignUp signUp) {
        this.signUp = signUp;
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @SerializedName("signup")
    SignUp signUp;
}
