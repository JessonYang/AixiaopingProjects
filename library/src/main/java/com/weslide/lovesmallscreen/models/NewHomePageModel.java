package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;
import com.weslide.lovesmallscreen.models.bean.PtGoodModel;

import java.util.List;

/**
 * Created by Dong on 2016/6/3.
 * 首页使用的数据
 */
public class NewHomePageModel extends BaseModel {

    @SerializedName("homeTopImages")
    /** 头部轮播图 */
    private List<ImageText> homeTopImages;

    @SerializedName("storesTotalPage")
    private String storesTotalPage;

    @SerializedName("toplocal")
    private List<TopLocalModel> toplocal;

    @SerializedName("livemodule")
    private LivemoduleModel livemodule;

    @SerializedName("featureType")
    private FeatureTypeModel featureType;

    @SerializedName("sqgw")
    private SqgwModel sqgw;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @SerializedName("pid")
    private String pid;

    public List<ImageText> getHomeTopImages() {
        return homeTopImages;
    }

    public void setHomeTopImages(List<ImageText> homeTopImages) {
        this.homeTopImages = homeTopImages;
    }

    public List<TopLocalModel> getToplocal() {
        return toplocal;
    }

    public void setToplocal(List<TopLocalModel> toplocal) {
        this.toplocal = toplocal;
    }

    public LivemoduleModel getLivemodule() {
        return livemodule;
    }

    public void setLivemodule(LivemoduleModel livemodule) {
        this.livemodule = livemodule;
    }

    public FeatureTypeModel getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureTypeModel featureType) {
        this.featureType = featureType;
    }

    public SqgwModel getSqgw() {
        return sqgw;
    }

    public void setSqgw(SqgwModel sqgw) {
        this.sqgw = sqgw;
    }

    public TopClassifyModel getTopClassify() {
        return topClassify;
    }

    public void setTopClassify(TopClassifyModel topClassify) {
        this.topClassify = topClassify;
    }

    @SerializedName("topClassify")
    private TopClassifyModel topClassify;

    @SerializedName("searchurl")
    private String searchurl;

    public NfcpModel getHomeBottomBanner() {
        return homeBottomBanner;
    }

    public void setHomeBottomBanner(NfcpModel homeBottomBanner) {
        this.homeBottomBanner = homeBottomBanner;
    }

    @SerializedName("homeBottomBanner")
    private NfcpModel homeBottomBanner;

    //拼团顶部轮播图背景image
    @SerializedName("ptBgImageUrl")
    private String ptBgImageUrl;

    //拼团顶部轮播图数组，image图片链接， typeId商品所属类别id， title 商品所属类别名称
    @SerializedName("ptImages")
    private List<NfcpModel> ptImages;

    public List<PtGoodModel> getPtGoods() {
        return ptGoods;
    }

    public void setPtGoods(List<PtGoodModel> ptGoods) {
        this.ptGoods = ptGoods;
    }

    public String getPtBgImageUrl() {
        return ptBgImageUrl;
    }

    public void setPtBgImageUrl(String ptBgImageUrl) {
        this.ptBgImageUrl = ptBgImageUrl;
    }

    public List<NfcpModel> getPtImages() {
        return ptImages;
    }

    public void setPtImages(List<NfcpModel> ptImages) {
        this.ptImages = ptImages;
    }

    //拼团商品数组，image图片链接， goodsId商品id， name 商品名称, price 商品展示价格
    @SerializedName("ptGoods")
    private List<PtGoodModel> ptGoods;

    public String getSearchurl() {
        return searchurl;
    }

    public void setSearchurl(String searchurl) {
        this.searchurl = searchurl;
    }

    public String getStoresTotalPage() {
        return storesTotalPage;
    }

    public void setStoresTotalPage(String storesTotalPage) {
        this.storesTotalPage = storesTotalPage;
    }
}
