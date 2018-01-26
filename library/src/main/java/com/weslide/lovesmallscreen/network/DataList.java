package com.weslide.lovesmallscreen.network;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;
import com.weslide.lovesmallscreen.models.CityType;
import com.weslide.lovesmallscreen.models.GoodsType;
import com.weslide.lovesmallscreen.models.RecomenderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2016/6/8.
 * 数据响应载体，用于适应列表数据
 */
public class DataList<T> extends BaseModel {

    @SerializedName("dataList")
    private List<T> dataList = new ArrayList<>();

    public List<T> getHotDataList() {
        return hotDataList;
    }

    public void setHotDataList(List<T> hotDataList) {
        this.hotDataList = hotDataList;
    }

    @SerializedName("hotDataList")
    private List<T> hotDataList = new ArrayList<>();

    @SerializedName("cityList")
    private List<CityType> cityList = new ArrayList<>();

    public List<GoodsType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<GoodsType> typeList) {
        this.typeList = typeList;
    }

    public List<CityType> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityType> cityList) {
        this.cityList = cityList;
    }

    @SerializedName("typeList")
    private List<GoodsType> typeList = new ArrayList<>();

    @SerializedName("pageSize")
    private int pageSize = 0;

    @SerializedName("pageIndex")
    private int pageIndex = 0;

    @SerializedName("pageItemCount")
    private int pageItemCount = 10;

    @SerializedName("score")
    private String score;

    @SerializedName("allMoney")
    private String money;

    //拼团分享标题
    @SerializedName("shareTitle")
    private String shareTitle;

    //拼团分享icon
    @SerializedName("shareIcon")
    private String shareIcon;

    //拼团分享链接
    @SerializedName("shareUrl")
    private String shareUrl;

    //拼团分享内容

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareContent() {

        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    @SerializedName("shareContent")
    private String shareContent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("title")
    private String title;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @SerializedName("pid")
    private String pid;

    public RecomenderModel getRecommender() {
        return recommender;
    }

    public void setRecommender(RecomenderModel recommender) {
        this.recommender = recommender;
    }

    //粉丝推荐人
    @SerializedName("recommender")
    private RecomenderModel recommender;

    private String recyclerViewStatus = "RECYCLER_VIEW_STATUS_SUCCESS";


    /**
     * 数据列表
     */
    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }


    /**
     * 最大页数，一般用于分页显示
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    /**
     * 当前返回数据的页索引
     */
    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 当前一页返回的数据条数
     */
    public int getPageItemCount() {
        return pageItemCount;
    }

    public void setPageItemCount(int pageItemCount) {
        this.pageItemCount = pageItemCount;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String totalMoney) {
        this.money = money;
    }

    /**
     * recyclerView的值状态 SuperRecyclerView中有定义 默认正在加载
     */
    public String getRecyclerViewStatus() {
        return recyclerViewStatus;
    }

    public void setRecyclerViewStatus(String recyclerViewStatus) {
        this.recyclerViewStatus = recyclerViewStatus;
    }
}
