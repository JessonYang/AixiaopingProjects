package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/6/8.
 * 获取商品列表的请求数据
 */
public class GetGoodsListBean extends BaseModel {

    @SerializedName("pageIndex")
    /** 分页页码，从1开始 */
    private int pageIndex = 0;

    @SerializedName("salesVolume")
    /** 销量排序	空或0：无
     1：高到低
     2：低到高 */
    private String salesVolume = "0";

    @SerializedName("value")
    /** 价值排序	空或0：无
     1：高到低
     2：低到高 */
    private String value = "0";

    @SerializedName("typeId")
    private String typeId;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @SerializedName("cityId")
    private String cityId;
    @SerializedName("areaId")
    private String areaId;
    @SerializedName("sellerId")
    private String sellerId;
    @SerializedName("secondKillId")
    private String secondKillId;
    @SerializedName("tag")
    private int[] tag;
    @SerializedName("search")
    private String search;
    @SerializedName("mallType")
    /** 商城类型 具体看Constants */
    private String mallType;
    @SerializedName("type")
    private String type;




    /** 商品分类 */
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

    /** 商家id */
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    /** 搜索 */
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }


    /** 商品标签 */
    public int[] getTag() {
        return tag;
    }

    public void setTag(int[] tag) {
        this.tag = tag;
    }

    /** 秒杀场次ID */
    public String getSecondKillId() {
        return secondKillId;
    }

    public void setSecondKillId(String secondKillId) {
        this.secondKillId = secondKillId;
    }

    public String getMallTyle() {
        return mallType;
    }

    public void setMallTyle(String mallTyle) {
        this.mallType = mallTyle;
    }


    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
