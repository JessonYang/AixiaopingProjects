package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/6/3.
 * 图文
 * 任何图片或图片文字的组合都使用该对象存储
 */
public class ImageText extends BaseModel {

    private Long id;

    @SerializedName("image")
    /** 图片链接 */
    private String image;
    @SerializedName("uri")
    /** 图片的点击意义 */
    private String uri;
    @SerializedName("name")
    /** 对于图片的描述 */
    private String name;
    @SerializedName("goodsOrder")
    /** 对于图片的描述 */
    private String goodsOrder;
    @SerializedName("typeId")
    /** 分类id 自定义值，视情况而定 */
    private String typeId;
    @SerializedName("sellerId")
    /** 商家id 自定义值，视情况而定 */
    private String sellerId;

    /**是否选择*/
    private boolean select = false;

    public boolean isSelect() { return select; }

    public void setSelect(boolean select) { this.select = select; }

    /** 数据库存储id */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getGoodsOrder() {
        return goodsOrder;
    }

    public void setGoodsOrder(String goodsOrder) {
        this.goodsOrder = goodsOrder;
    }
}
