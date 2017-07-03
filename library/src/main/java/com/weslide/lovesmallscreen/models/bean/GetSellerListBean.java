package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xu on 2016/6/8.
 * 获取商家列表的请求数据
 */
public class GetSellerListBean {

    @SerializedName("pageIndex")
    private int pageIndex = 0;
    @SerializedName("typeId")
    private String typeId;
    @SerializedName("areaId")
    private String areaId;
    @SerializedName("search")
    private String search;
    @SerializedName("type")
    private String type;
    @SerializedName("cityId")
    private String cityId;


    public String getShopcategoryId() {
        return shopcategoryId;
    }

    public void setShopcategoryId(String shopcategoryId) {
        this.shopcategoryId = shopcategoryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @SerializedName("shopcategoryId")
    private String shopcategoryId;
    /** 区别是首页还是列表*/
    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    /** 分页页码，从1开始 */
    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {

        this.pageIndex = pageIndex;
    }

    /** 商家分类 */
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /** 商圈Id */
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /** 搜索 */
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
