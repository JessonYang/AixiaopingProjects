package com.weslide.lovesmallscreen.models.demo;

import com.google.gson.annotations.SerializedName;

import com.weslide.lovesmallscreen.network.Response;

/**
 * Created by xu on 2016/5/4.
 * demo中使用
 * ip的信息
 */
public class IpInfo extends Response {
    //    {
//        "code": 0,
//            "data": {
//        "country": "中国",
//                "country_id": "CN",
//                "area": "华南",
//                "area_id": "800000",
//                "region": "广东省",
//                "region_id": "440000",
//                "city": "广州市",
//                "city_id": "440100",
//                "county": "",
//                "county_id": "-1",
//                "isp": "联通",
//                "isp_id": "100026",
//                "ip": "58.248.15.220"
//    }
//    }

    private Long id;
    @SerializedName("ip")
    private String ip;
    @SerializedName("country")
    private String country;
    @SerializedName("country_id")
    private String country_id;
    @SerializedName("area")
    private String area;
    @SerializedName("area_id")
    private String area_id;
    @SerializedName("region")
    private String region;
    @SerializedName("region_id")
    private String region_id;
    @SerializedName("city")
    private String city;
    @SerializedName("city_id")
    private String city_id;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
