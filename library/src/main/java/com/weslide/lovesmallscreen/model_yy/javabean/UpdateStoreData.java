package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong on 2017/3/1.
 */
public class UpdateStoreData extends BaseModel{

    @SerializedName("name")//店铺名字
    private String name;
    @SerializedName("businessLicencePic")//资质证明
    private String businessLicencePic;
    @SerializedName("shopcategoryId")//店铺类型
    private String shopcategoryId;
    @SerializedName("phone")//联系电话
    private String phone;
    @SerializedName("sellerIdCard")//身份证
    private String sellerIdCard;
    @SerializedName("address")//店铺地址
    private String address;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBusinessLicencePic() { return businessLicencePic; }

    public void setBusinessLicencePic(String businessLicencePic) { this.businessLicencePic = businessLicencePic; }

    public String getShopcategoryId() { return shopcategoryId; }

    public void setShopcategoryId(String shopcategoryId) { this.shopcategoryId = shopcategoryId; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getSellerIdCard() { return sellerIdCard; }

    public void setSellerIdCard(String sellerIdCard) { this.sellerIdCard = sellerIdCard; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

}
