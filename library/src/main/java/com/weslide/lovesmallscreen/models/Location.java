package com.weslide.lovesmallscreen.models;

import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/4/26.
 * 位置数据
 */
public class Location extends BaseModel{

    private Long id;
    private String coordinateType;
    private String time;
    private String latitude = "123.241632";
    private String longitude = "34.1239004";

    private String country;
    private String countryCode;
    private String province;
    private String city;
    private String cityCode;
    private String district;
    private String street;
    private String streetNumber;
    private String address;
    private String radius;

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** 坐标类型 */
    public String getCoordinateType() {
        return coordinateType;
    }

    public void setCoordinateType(String coordinateType) {
        this.coordinateType = coordinateType;
    }

    /** 定位时间 */
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /** 纬度 */
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /** 经度 */
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /** 国家 */
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /** 国家编号 */
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /** 省份 */
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    /** 城市 */
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /** 城市编号 */
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /** 地区 */
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    /** 街道 */
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    /** 街道号 */
    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /** 具体地址 */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
