package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/6/16.
 */
public class SaveMoneyGoodModel extends BaseModel {

    @SerializedName("link")
    String link;

    @SerializedName("d_title")
    String d_title;

    @SerializedName("quan_pice")
    String quan_pice;

    @SerializedName("price")
    String price;

    @SerializedName("org_price")
    String org_price;

    @SerializedName("quan_surplus")
    String quan_surplus;

    @SerializedName("quan_receive")
    String quan_receive;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getD_title() {
        return d_title;
    }

    public void setD_title(String d_title) {
        this.d_title = d_title;
    }

    public String getQuan_pice() {
        return quan_pice;
    }

    public void setQuan_pice(String quan_pice) {
        this.quan_pice = quan_pice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrg_price() {
        return org_price;
    }

    public void setOrg_price(String org_price) {
        this.org_price = org_price;
    }

    public String getQuan_surplus() {
        return quan_surplus;
    }

    public void setQuan_surplus(String quan_surplus) {
        this.quan_surplus = quan_surplus;
    }

    public String getQuan_receive() {
        return quan_receive;
    }

    public void setQuan_receive(String quan_receive) {
        this.quan_receive = quan_receive;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @SerializedName("pic")
    String pic;
}
