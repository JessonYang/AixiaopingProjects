package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;

/**
 * Created by YY on 2017/12/25.
 */
public class HomePprmModel extends BaseModel {

    //已领券数量
    @SerializedName("quan_receive")
    String quan_receive;

    //券后价
    @SerializedName("price")
    String price;

    //领券立减
    @SerializedName("cut_price")
    double cut_price;

    //商品名称
    @SerializedName("d_title")
    String d_title;

    //优惠券剩余数量
    @SerializedName("quan_surplus")
    String quan_surplus;

    //领券链接(拼接id)
    @SerializedName("link")
    String link;

    //优惠券价格
    @SerializedName("quan_price")
    String quan_price;

    //商品id
    @SerializedName("goods_id")
    String goods_id;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getQuan_receive() {
        return quan_receive;
    }

    public void setQuan_receive(String quan_receive) {
        this.quan_receive = quan_receive;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getCut_price() {
        return cut_price;
    }

    public void setCut_price(double cut_price) {
        this.cut_price = cut_price;
    }

    public String getD_title() {
        return d_title;
    }

    public void setD_title(String d_title) {
        this.d_title = d_title;
    }

    public String getQuan_surplus() {
        return quan_surplus;
    }

    public void setQuan_surplus(String quan_surplus) {
        this.quan_surplus = quan_surplus;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getQuan_price() {
        return quan_price;
    }

    public void setQuan_price(String quan_price) {
        this.quan_price = quan_price;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getOrg_price() {
        return org_price;
    }

    public void setOrg_price(String org_price) {
        this.org_price = org_price;
    }

    //商品图片
    @SerializedName("pic")

    String pic;

    //商品原价
    @SerializedName("org_price")
    String org_price;
}
