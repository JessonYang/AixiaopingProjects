package com.weslide.lovesmallscreen;

import android.content.Context;

import com.google.gson.Gson;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xu on 2016/6/2.
 * 用于对uri进行解析
 */
public class URIResolve {

    //网页URL，打开APP中的内置浏览器进行显示
    public static final String EXECUTE_HTTP = "http:";
    //网页URL，打开APP中的内置浏览器进行显示
    public static final String EXECUTE_HTTPS = "https:";
    //跳转至商品详情 示例goods:{“goodsId”:”12”}
    public static final String EXECUTE_GOODS = "goods:";
    //跳转至店铺首页 示例seller:{“sellerId”:”12”}
    public static final String EXECUTE_SELLER = "seller:";
    //跳转至商家兑换界面 示例 exchange:{ “orderId”:”12”, “code”:”652148”}  orderId 订单id  code 兑换码
    public static final String EXECUTE_EXCHANGE = "exchange:";

    /** 以下内容已基本淘汰 */
    //积分商城订单，商家扫描时跳转到该积分订单的详情界面，可点击确定兑换
    public static final String EXECUTE_SCORE_MALL_ORDER = "score_mall_order:";
    //促销商城订单，商家扫描后跳转到该现金订单的详情界面，可以点击确定兑换
    public static final String EXECUTE_CASH_MALL_ORDER = "cash_mall_order:";
    //代金券充值，用户扫描后跳转至代金券充值界面并自动填充兑换码，用户点击确定充值后充值
    public static final String EXECUTE_CASH_COUPON_RECHARGE = "cash_coupon_recharge:";

    static Gson gson = new Gson();

    /**
     * 将uri转换为意图
     * @param context
     * @param uri
     */
    public static void resolve(Context context, String uri){
        execute(context, uri);
    }


    /**
     * 用于处理所有URI
     * @param content
     */
    private static void execute(Context context ,String content){

        if(content == null || "".equals(content)){
            return;
        }

        content = content.trim();

        L.i("URI处理：" + content);

        if(content.startsWith(EXECUTE_HTTP) || content.startsWith(EXECUTE_HTTPS)){
            executeBrowser(context ,content);
            return;
        } else if(content.startsWith(EXECUTE_GOODS)){
            executeGoods(context, content);
        } else if(content.startsWith(EXECUTE_SELLER)){
            executeSeller(context, content);
        } else if(content.startsWith(EXECUTE_EXCHANGE)){
            executeExchange(context, content);
        } else if(content.startsWith(EXECUTE_SCORE_MALL_ORDER) ) {
            return;
        } else if(content.startsWith(EXECUTE_CASH_MALL_ORDER ) ) {
            return;
        } else if(content.startsWith(EXECUTE_CASH_COUPON_RECHARGE ) ) {
            return;
        } else {
        }

    }

    /**
     * 用于处理前缀为http:/https:的URI
     * 网页URL，打开APP中的内置浏览器进行显示
     * @param content
     */
    private static void executeBrowser(Context context ,String content){
        AppUtils.toBrowser(context, content);
    }

    private static void executeGoods(Context context ,String content){
        content = content.replace(EXECUTE_GOODS,"");

        Goods goods = gson.fromJson(content, Goods.class);
        AppUtils.toGoods(context, goods.getGoodsId());
    }

    private static void executeSeller(Context context ,String content){
        content = content.replace(EXECUTE_SELLER,"");

        Seller seller = gson.fromJson(content, Seller.class);
        AppUtils.toSeller(context, seller.getSellerId());
    }

    private static void executeExchange(Context context ,String content){
        content = content.replace(EXECUTE_EXCHANGE,"");
        Order order = gson.fromJson(content, Order.class);
        AppUtils.toGoodsExchange(context, order.getOrderId(), order.getExchangeCode());
    }


    /**
     * 创建积分兑换的URI
     * @param orderId 订单id
     * @param exchangeCode  兑换码
     * @return
     */
    public static String createExchangeURI(String orderId, String exchangeCode){
        Map<String,String> values = new HashMap<>();
        values.put("orderId",orderId);
        values.put("exchangeCode", exchangeCode);

        return EXECUTE_EXCHANGE + gson.toJson(values);
    }

}
