package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.models.bean.NewGoodDetailModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2018/1/23.
 */
public class ExchangeGoodDtModel extends BaseModel {

    //商品名称
    @SerializedName("goodsName")
    String goodsName;

    //商品单张图
    @SerializedName("coverPicOne")
    String coverPicOne;

    //商品轮播图
    @SerializedName("coverPic")
    List<String> coverPic;

    //商品规格
    @SerializedName("specifications")
    ArrayList<SpecificationModel> specifications;

    //商品是否有规格
    @SerializedName("hasSpecStr")
    boolean hasSpecStr;

    //商品库存
    @SerializedName("stock")
    int stock;

    //页码
    int pageIndex;

    //商品描述
    @SerializedName("changeDesc")
    String changeDesc;

    //商品所属人id
    @SerializedName("goodsUserId")
    int goodsUserId;

    //商品所属人头像
    @SerializedName("goodsUserHead")
    String goodsUserHead;

    //商品所属人地址
    @SerializedName("sellerAddress")
    String sellerAddress;

    //商品所属人名字
    @SerializedName("goodsUserName")
    String goodsUserName;

    //goodsOrder
    @SerializedName("goodsOrder")
    String goodsOrder;

    //交易方式
    @SerializedName("transportationType")
    String transportationType;

    //goodsId
    @SerializedName("goodsId")
    int goodsId;

    //mallType
    @SerializedName("mallType")
    int mallType;

    //浏览量
    @SerializedName("pageView")
    int pageView;

    //商品市场价
    @SerializedName("displayPrice")
    double displayPrice;

    //商品标签
    @SerializedName("want")
    List<String> want;

    //商品图文详情
    @SerializedName("goodsDetail")
    List<NewGoodDetailModel> goodsDetail;

    public String getCoverPicOne() {
        return coverPicOne;
    }

    public void setCoverPicOne(String coverPicOne) {
        this.coverPicOne = coverPicOne;
    }

    public boolean isHasSpecStr() {
        return hasSpecStr;
    }

    public void setHasSpecStr(boolean hasSpecStr) {
        this.hasSpecStr = hasSpecStr;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public List<String> getCoverPic() {
        return coverPic;
    }

    public ArrayList<SpecificationModel> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(ArrayList<SpecificationModel> specifications) {
        this.specifications = specifications;
    }

    public void setCoverPic(List<String> coverPic) {
        this.coverPic = coverPic;
    }

    public String getChangeDesc() {
        return changeDesc;
    }

    public void setChangeDesc(String changeDesc) {
        this.changeDesc = changeDesc;
    }

    public int getGoodsUserId() {
        return goodsUserId;
    }

    public void setGoodsUserId(int goodsUserId) {
        this.goodsUserId = goodsUserId;
    }

    public String getGoodsUserName() {
        return goodsUserName;
    }

    public void setGoodsUserName(String goodsUserName) {
        this.goodsUserName = goodsUserName;
    }

    public String getGoodsOrder() {
        return goodsOrder;
    }

    public void setGoodsOrder(String goodsOrder) {
        this.goodsOrder = goodsOrder;
    }

    public String getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(String transportationType) {
        this.transportationType = transportationType;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getMallType() {
        return mallType;
    }

    public void setMallType(int mallType) {
        this.mallType = mallType;
    }

    public int getPageView() {
        return pageView;
    }

    public void setPageView(int pageView) {
        this.pageView = pageView;
    }

    public double getDisplayPrice() {
        return displayPrice;
    }

    public void setDisplayPrice(double displayPrice) {
        this.displayPrice = displayPrice;
    }

    public List<String> getWant() {
        return want;
    }

    public void setWant(List<String> want) {
        this.want = want;
    }

    public List<NewGoodDetailModel> getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(List<NewGoodDetailModel> goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getGoodsUserHead() {
        return goodsUserHead;
    }

    public void setGoodsUserHead(String goodsUserHead) {
        this.goodsUserHead = goodsUserHead;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
