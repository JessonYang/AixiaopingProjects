package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;
import com.weslide.lovesmallscreen.utils.StringUtils;

import java.util.List;

/**
 * Created by xu on 2016/6/4.
 * 商品
 */
public class Goods extends BaseModel {

    @SerializedName("goodsId")
    /** 商品id */
    private String goodsId;

    @SerializedName("mallType")
    /** mallType	所属商城模块	商城类型
     1:独立商城
     2:积分商城
     3:秒杀商城
     4:各地特产商城
     5:99特惠商城
     6:会员免单商城
     */
    private String mallType;

    @SerializedName("name")
    /** 商品名 */
    private String name;
    @SerializedName("price")
    /** 商品价格 */
    private String price;
    @SerializedName("score")
    /** 商品可以使用的积分 */
    private String score;
    @SerializedName("goodDetailUrl")
    /** 商品详情Url */
    private String goodDetailUrl;

    public String getGoodDetailUrl() {
        return goodDetailUrl;
    }

    public void setGoodDetailUrl(String goodDetailUrl) {
        this.goodDetailUrl = goodDetailUrl;
    }

    public String getPay_price() {
        return pay_price;
    }

    public void setPay_price(String pay_price) {
        this.pay_price = pay_price;
    }

    @SerializedName("pay_price")
    /** 退款金额 */
    private String pay_price;
    @SerializedName("cashpoint")
    /** 商品可使用的代金券 */
    private String cashpoint;
    @SerializedName("costPrice")
    /** 原价 */
    private String costPrice;
    @SerializedName("images")
    /** 商品介绍轮播图 */
    private List<ImageText> images;
    @SerializedName("videos")
    /** 商品介绍视频 */
    private List<VideoText> videos;
    @SerializedName("expressTactics")
    /** 配送策略
     * 包邮 、随单包邮 、单笔满固额包邮 、邮费代付 、到店消费 、上门自提 、周边配送
     */
    private String expressTactics;

    public String getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(String expressStatus) {
        this.expressStatus = expressStatus;
    }

    @SerializedName("expressStatus")
    private String expressStatus;
    @SerializedName("spec")
    /** 商品规格 */
    private List<Spec> spec;
    @SerializedName("salesVolume")
    /** 销量 */
    private int salesVolume;
    @SerializedName("specNotes")
    /** 不同规格的商品库存、价值记录 */
    private List<SpecNote> specNotes;
    @SerializedName("discount")
    /** 折扣 */
    private String discount;
    @SerializedName("seller")
    /** 商家信息 */
    private Seller seller;

    @SerializedName("commentList")
    /** 评论列表 */
    private CommentList commentList;

    @SerializedName("coverPic")
    /** 商品封面图片 */
    private String coverPic;

    @SerializedName("salesRatio")
    /** 售出比例 秒杀时使用 */
    private String salesRatio;
    @SerializedName("concern")
    /** 用户是否关注了该商品 */
    private Boolean concern;
    private Boolean select = false;

    public void setSelect(Boolean select) { this.select = select; }

    public Boolean getSelect() { return select; }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCashpoint() {
        return cashpoint;
    }

    public void setCashpoint(String cashpoint) {
        this.cashpoint = cashpoint;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public String getCostPriceString() {
        return "￥" + costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public List<ImageText> getImages() {
        return images;
    }

    public void setImages(List<ImageText> images) {
        this.images = images;
    }

    public List<VideoText> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoText> videos) {
        this.videos = videos;
    }

    public String getExpressTactics() {
        return expressTactics;
    }

    public void setExpressTactics(String expressTactics) {
        this.expressTactics = expressTactics;
    }

    public List<Spec> getSpec() {
        return spec;
    }

    public void setSpec(List<Spec> spec) {
        this.spec = spec;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public List<SpecNote> getSpecNote() {
        return getSpecNotes();
    }

    public void setSpecNote(List<SpecNote> stock) {
        this.setSpecNotes(stock);
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }


    public CommentList getCommentList() {
        return commentList;
    }

    public void setCommentList(CommentList commentList) {
        this.commentList = commentList;
    }


    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    /**
     * 获取该商品需要显示的价值
     * 用于区分现金和积分
     *
     * @return
     */
    public String getValue() {
        return getValue(getPrice(), getScore());
    }

    /**
     * 获取该商品需要显示的价值
     * 用于区分现金和积分
     *
     * @return
     */
    public static String getValue(String price, String score) {
        if (!StringUtils.isNumberEmpty(price)) {
            return "￥" + price;
        } else if (!StringUtils.isNumberEmpty(score)) {
            return (score + "积分");
        } else {
            return "免单";
        }
    }

    /**
     * 获取当前订单项的购买类型
     * 1.积分商品
     * 2.现金商品
     * 3.免单商品
     * 4.现金加红包商品
     *
     * @return
     */
    public int getType() {
        if (!StringUtils.isNumberEmpty(getScore())) { //积分商品
            return 1;
        } else if (!StringUtils.isNumberEmpty(getPrice())) { //现金商品
            if (!StringUtils.isNumberEmpty(getCashpoint())) { //现金红包商品
                return 4;
            }
            return 2;
        } else {  //免单商品
            return 3;
        }
    }

    public List<SpecNote> getSpecNotes() {
        return specNotes;
    }

    public void setSpecNotes(List<SpecNote> specNotes) {
        this.specNotes = specNotes;
    }

    public String getSalesRatio() {
        return salesRatio;
    }

    public void setSalesRatio(String salesRatio) {
        this.salesRatio = salesRatio;
    }

    public Boolean getConcern() {
        return concern == null ? false : concern;
    }

    public void setConcern(Boolean concern) {
        this.concern = concern;
    }

    public String getMallType() {
        return mallType;
    }

    public void setMallType(String mallType) {
        this.mallType = mallType;
    }
}
