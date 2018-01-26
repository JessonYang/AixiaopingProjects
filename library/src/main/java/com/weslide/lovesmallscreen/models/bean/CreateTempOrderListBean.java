package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/6/29.
 * 创建临时订单的Bean
 */
public class CreateTempOrderListBean extends BaseModel {

    @SerializedName("type")
    /** 类型 1:购物车 2：立即购买 3：订单再付款 */
    private String type;

    @SerializedName("isTeam")
    /** 是否为拼团商品 */
    private boolean isTeam = false;

    public String getTeamOrderId() {
        return teamOrderId;
    }

    public void setTeamOrderId(String teamOrderId) {
        this.teamOrderId = teamOrderId;
    }

    public boolean isTeam() {
        return isTeam;
    }

    public void setTeam(boolean team) {
        isTeam = team;
    }

    @SerializedName("teamOrderId")
    /** 拼团商品id */

    private String teamOrderId;

    //    购物车临时订单生成参数
//    shoppingCarItemIds	购物车商品项id	[String,String]
//    购物车结算时传递
    @SerializedName("shoppingCarItemIds")
    /** 购物车商品项id */
    private List<String> shoppingCarItemIds;


    //            直接购买临时订单生产参数
//    goodsId	加入购物车的商品ID
//    number	购买数量
//    specs	规格项id	[String,String]
    @SerializedName("goodsId")
    /** 加入购物车的商品ID */
    private String goodsId;
    @SerializedName("number")
    /** 购买数量 */
    private Integer number;
    @SerializedName("specs")
    /** specs	规格项id	[String,String] */
    private List<String> specs;

//    重新付款参数
//    orderId	订单id
//    type	类型	3

    @SerializedName("orderId")
    /** 订单id */
    private String orderId;


    public List<String> getShoppingCarItemIds() {
        return shoppingCarItemIds;
    }

    public void setShoppingCarItemIds(List<String> shoppingCarItemIds) {
        this.shoppingCarItemIds = shoppingCarItemIds;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<String> getSpecs() {
        return specs;
    }

    public void setSpecs(List<String> specs) {
        this.specs = specs;
    }

    /** 类型 1:购物车 2：立即购买 3：订单再付款 */
    public String getType() {
        return type;
    }
    /** 类型 1:购物车 2：立即购买 3：订单再付款 */
    public void setType(String type) {
        this.type = type;

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
