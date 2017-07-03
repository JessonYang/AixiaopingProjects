package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/6/14.
 */
public class Address extends BaseModel {
    @SerializedName("addressId")
    private String addressId;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
    @SerializedName("district")
    private String district;
    @SerializedName("detailedAddress")
    private String detailedAddress;
    @SerializedName("address")
    private String address;
    @SerializedName("defaultAddress")
    private Boolean defaultAddress;

    /**
     *地址id标识
     */
    public String getAddressId() { return addressId; }

    public void setAddressId(String addressId) { this.addressId = addressId; }

    /**
     * 收货人
     */
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    /**
     * 收货人电话
     */
    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    /**
     * 省
     */
    public String getProvince() { return province; }

    public void setProvince(String province) { this.province = province; }

    /**
     * 城市
     */
    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    /**
     * 区/县
     */
    public String getDistrict() { return district; }

    public void setDistrict(String district) { this.district = district; }

    /**
     * 详细地址
     */
    public String getDetailedAddress() { return detailedAddress; }

    public void setDetailedAddress(String detailedAddress) { this.detailedAddress = detailedAddress; }

    /**
     * 地址全部内容
     */
    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    /**
     * 是否是默认收货地址
     */
    public Boolean getDefaultAddress() { return defaultAddress; }

    public void setDefaultAddress(Boolean defaultAddress) { this.defaultAddress = defaultAddress; }

}
