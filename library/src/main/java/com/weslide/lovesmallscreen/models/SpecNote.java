package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/6/4.
 * 用于记录商品不同规格的库存、价值
 */
public class SpecNote extends BaseModel {
    @SerializedName("key")
    /** 多个规格形成的库存	[String,String]  数据长度取决规格数量 */
    private String[] key;
    @SerializedName("stockNumber")
    /** 库存数量 */
    private int stockNumber;

    @SerializedName("price")
    /** 该规格商品价格 */
    private String price;
    @SerializedName("score")
    /** 该规格商品可以使用的积分 */
    private String score;
    @SerializedName("cashpoint")
    /** 该规格商品可使用的代金券 */
    private String cashpoint;
    @SerializedName("costPrice")
    /** 该规格原价 */
    private String costPrice;


    public String[] getKey() {
        return key;
    }

    public void setKey(String[] key) {
        this.key = key;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCashpoint() {
        return cashpoint;
    }

    public void setCashpoint(String cashpoint) {
        this.cashpoint = cashpoint;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }
}
