package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by Dong on 2016/6/3.
 * 首页使用的数据
 */
public class HomePage extends BaseModel {

    @SerializedName("topImages")
    /** 头部轮播图 */
    private List<ImageText> topImages;

    @SerializedName("taokeTitle")
    private TaoKe taokeTitle;

    @SerializedName("categories")
    private List<TaoKe> categories;

    @SerializedName("sqgw")
    private SaveMoney saveMoney;

    @SerializedName("storesTotalPage")
    private String storesTotalPage;

    @SerializedName("shopping")
    private Shopping shopping;

    @SerializedName("live")
    private LiveNew live;

    @SerializedName("searchurl")
    private String searchurl;

    public String getSearchurl() {
        return searchurl;
    }

    public void setSearchurl(String searchurl) {
        this.searchurl = searchurl;
    }
    //    @SerializedName("exchange")
//    private TaoKe exchange;

    @SerializedName("activites")
    private Activites activites;

    public ExchangeAll getExchangeAll() {
        return exchangeAll;
    }

    public void setExchangeAll(ExchangeAll exchangeAll) {
        this.exchangeAll = exchangeAll;
    }

    @SerializedName("exchange")
    private ExchangeAll exchangeAll;

    public Activites getActivites() {
        return activites;
    }

    public void setActivites(Activites activites) {
        this.activites = activites;
    }

    public List<ImageText> getTopImages() {
        return topImages;
    }

    public void setTopImages(List<ImageText> topImages) {
        this.topImages = topImages;
    }

    public TaoKe getTaokeTitle() {
        return taokeTitle;
    }

    public void setTaokeTitle(TaoKe taokeTitle) {
        this.taokeTitle = taokeTitle;
    }

    public List<TaoKe> getCategories() {
        return categories;
    }

    public void setCategories(List<TaoKe> categories) {
        this.categories = categories;
    }

    public SaveMoney getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(SaveMoney saveMoney) {
        this.saveMoney = saveMoney;
    }

    public String getStoresTotalPage() {
        return storesTotalPage;
    }

    public void setStoresTotalPage(String storesTotalPage) {
        this.storesTotalPage = storesTotalPage;
    }

    public Shopping getShopping() {
        return shopping;
    }

    public void setShopping(Shopping shopping) {
        this.shopping = shopping;
    }

    public LiveNew getLive() {
        return live;
    }

    public void setLive(LiveNew live) {
        this.live = live;
    }

//    public TaoKe getExchange() {
//        return exchange;
//    }

//    public void setExchange(TaoKe exchange) {
//        this.exchange = exchange;
//    }
}
