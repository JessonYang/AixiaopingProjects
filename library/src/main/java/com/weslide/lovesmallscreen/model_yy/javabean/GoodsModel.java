package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/6/16.
 */
public class GoodsModel extends BaseModel {

    @SerializedName("goods")
    List<SaveMoneyGoodModel> goods;

    public List<CaroModel> getCaro() {
        return caro;
    }

    public void setCaro(List<CaroModel> caro) {
        this.caro = caro;
    }

    @SerializedName("caro")
    List<CaroModel> caro;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @SerializedName("pageIndex")
    String page_num;

    @SerializedName("title")
    String title;

    @SerializedName("cid")
    String cid;

    @SerializedName("shareTitle")
    String shareTitle;

    @SerializedName("shareContent")
    String shareContent;

    @SerializedName("shareIconUrl")
    String shareIconUrl;

    @SerializedName("shareTargetUrl")
    String shareTargetUrl;

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareIconUrl() {
        return shareIconUrl;
    }

    public void setShareIconUrl(String shareIconUrl) {
        this.shareIconUrl = shareIconUrl;
    }

    public String getShareTargetUrl() {
        return shareTargetUrl;
    }

    public void setShareTargetUrl(String shareTargetUrl) {
        this.shareTargetUrl = shareTargetUrl;
    }

    @SerializedName("keyword")
    String keyWords;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @SerializedName("pid")
    String pid;

    public List<SaveMoneyGoodModel> getGoods() {
        return goods;
    }

    public void setGoods(List<SaveMoneyGoodModel> goods) {
        this.goods = goods;
    }

    public String getPage_num() {
        return page_num;
    }

    public void setPage_num(String page_num) {
        this.page_num = page_num;
    }
}
