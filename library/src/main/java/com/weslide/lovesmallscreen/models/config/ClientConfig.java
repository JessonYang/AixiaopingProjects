package com.weslide.lovesmallscreen.models.config;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/5/25.
 * 客户端配置参数
 */
public class ClientConfig extends BaseModel {
//    maxscore	滑屏、解锁、品牌官网随机生成的最大积分
//    minscore	滑屏、解锁、品牌官网随机生成的最小积分
//    toadyscore	每天可获得的最大积分
//    screenAdvertLoadTime	滑屏广告加载间隔时间，单位：毫秒
//    vipLevels	会员的可选级别	[{vipLevel},{ vipLevel }]

    @SerializedName("newVersion")
    private int newVersion;
    @SerializedName("newVersionMessage")
    private String newVersionMessage;
    @SerializedName("newVersionDownload")
    /** 新版本下载链接 */
    private String newVersionDownload;

    @SerializedName("redPaperProbability")
    /** 红包概率 */
    private int redPaperProbability;

    @SerializedName("maxscore")
    private int maxscore = 30;
    @SerializedName("minscore")
    private int minscore = 20;
    @SerializedName("toadyscore")
    private int toadyscore = 4000;
    @SerializedName("screenAdvertLoadTime")
    private long screenAdvertLoadTime = 30 * (1000 * 60); //默认30分钟
    @SerializedName("vipLevels")
    /** 会员的可选级别 */
    private List<VipLevel> vipLevels;

//    homeShareContent	首页分享内容	{shareContent}
//    shoppingCarShareContent	购物车分享内容	{shareContent}
//    goodsShareContent	商品分享	{shareContent}
//    只需要返回targetUrl，其他内容由前台读取商品详情，targetUrl需再拼goodsId

    @SerializedName("homeShareContent")
    private ShareContent homeShareContent;

    public ShareContent getPersonalCenterShareContent() {
        return personalCenterShareContent;
    }

    public void setPersonalCenterShareContent(ShareContent personalCenterShareContent) {
        this.personalCenterShareContent = personalCenterShareContent;
    }

    @SerializedName("personalCenterShareContent")
    private ShareContent personalCenterShareContent;
    @SerializedName("shoppingCarShareContent")
    private ShareContent shoppingCarShareContent;
    @SerializedName("goodsShareContent")
    private ShareContent goodsShareContent;
    @SerializedName("sellerShareContent")
    private ShareContent sellerShareContent;

    @SerializedName("download")
    private String download;
    //商家分享链接
    public ShareContent getSellerShareContent() { return sellerShareContent; }

    public void setSellerShareContent(ShareContent sellerShareContent) { this.sellerShareContent = sellerShareContent; }

    /**下载链接*/
    public String getDownload() { return download; }

    public void setDownload(String download) { this.download = download; }

    /** 滑屏获得最大积分 */
    public int getMaxscore() {
        return maxscore;
    }

    public void setMaxscore(int maxscore) {
        this.maxscore = maxscore;
    }

    /** 滑屏获得最小积分 */
    public int getMinscore() {
        return minscore;
    }

    public void setMinscore(int minscore) {
        this.minscore = minscore;
    }

    /** 当天可获得的最大积分 */
    public int getToadyscore() {
        return toadyscore;
    }

    public void setToadyscore(int toadyscore) {
        this.toadyscore = toadyscore;
    }

    /** 滑屏广告切换时间 */
    public long getScreenAdvertLoadTime() {
        return screenAdvertLoadTime;
    }

    public void setScreenAdvertLoadTime(long screenAdvertLoadTime) {
        this.screenAdvertLoadTime = screenAdvertLoadTime;
    }


    public List<VipLevel> getVipLevels() {
        return vipLevels;
    }

    public void setVipLevels(List<VipLevel> vipLevels) {
        this.vipLevels = vipLevels;
    }


    public ShareContent getHomeShareContent() {
        return homeShareContent;
    }

    public void setHomeShareContent(ShareContent homeShareContent) {
        this.homeShareContent = homeShareContent;
    }

    public ShareContent getShoppingCarShareContent() {
        return shoppingCarShareContent;
    }

    public void setShoppingCarShareContent(ShareContent shoppingCarShareContent) {
        this.shoppingCarShareContent = shoppingCarShareContent;
    }

    public ShareContent getGoodsShareContent() {
        return goodsShareContent;
    }

    public void setGoodsShareContent(ShareContent goodsShareContent) {
        this.goodsShareContent = goodsShareContent;
    }


    public int getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(int newVersion) {
        this.newVersion = newVersion;
    }

    public String getNewVersionMessage() {
        return newVersionMessage;
    }

    public void setNewVersionMessage(String newVersionMessage) {
        this.newVersionMessage = newVersionMessage;
    }


    public int getRedPaperProbability() {
        return redPaperProbability;
    }

    public void setRedPaperProbability(int redPaperProbability) {
        this.redPaperProbability = redPaperProbability;
    }


    public String getNewVersionDownload() {
        return newVersionDownload;
    }

    public void setNewVersionDownload(String newVersionDownload) {
        this.newVersionDownload = newVersionDownload;
    }
}
