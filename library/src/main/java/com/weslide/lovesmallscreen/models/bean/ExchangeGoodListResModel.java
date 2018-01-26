package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;
import com.weslide.lovesmallscreen.models.GoodsType;
import com.weslide.lovesmallscreen.models.ImageText;

import java.util.List;

/**
 * Created by YY on 2018/1/20.
 */
public class ExchangeGoodListResModel extends BaseModel {

    //商品列表
    @SerializedName("changeList")
    List<ExchangeGoodModel> changeList;

    //轮播图
    @SerializedName("homeTopImages")
    List<ImageText> homeTopImages;

    //帖子标题
    @SerializedName("wantImg")
    String wantImg;

    @SerializedName("pageIndex")
    int pageIndex;

    @SerializedName("pageSize")
    int pageSize;

    @SerializedName("pageItemCount")
    int pageItemCount;

    //分类
    @SerializedName("classifications")
    List<GoodsType> classifications;

    //搜索关键字
    @SerializedName("search")
    String search;

    //typeID
    @SerializedName("typeId")
    String typeId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public List<ExchangeGoodModel> getChangeList() {
        return changeList;
    }

    public void setChangeList(List<ExchangeGoodModel> changeList) {
        this.changeList = changeList;
    }

    public List<ImageText> getHomeTopImages() {
        return homeTopImages;
    }

    public void setHomeTopImages(List<ImageText> homeTopImages) {
        this.homeTopImages = homeTopImages;
    }

    public String getWantImg() {
        return wantImg;
    }

    public void setWantImg(String wantImg) {
        this.wantImg = wantImg;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageItemCount() {
        return pageItemCount;
    }

    public void setPageItemCount(int pageItemCount) {
        this.pageItemCount = pageItemCount;
    }

    public List<GoodsType> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<GoodsType> classifications) {
        this.classifications = classifications;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
