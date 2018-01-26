package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2018/1/24.
 */
public class SpecificationModel extends BaseModel {

    //规格名
    @SerializedName("specStr")
    String specStr;

    //规格库存
    @SerializedName("stock")
    int stock;

    //规格id
    @SerializedName("specId")
    int specId;

    //规格价格
    @SerializedName("price")
    String price;

    public String getSpecStr() {
        return specStr;
    }

    public void setSpecStr(String specStr) {
        this.specStr = specStr;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
