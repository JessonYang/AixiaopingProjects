package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/11/10.
 */
public class OrderStatusNum extends BaseModel{

    @SerializedName("payment")
    String payment;

    @SerializedName("confirm")
    String confirm;

    @SerializedName("evaluate")
    String evaluate;

    public String getChargeback() {
        return chargeback;
    }

    public void setChargeback(String chargeback) {
        this.chargeback = chargeback;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    @SerializedName("chargeback")
    String chargeback;
}
