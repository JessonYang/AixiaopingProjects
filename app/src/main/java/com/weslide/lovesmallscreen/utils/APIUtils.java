package com.weslide.lovesmallscreen.utils;

import android.content.Context;

import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.network.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xu on 2016/7/20.
 * api一些常用的操作
 */
public class APIUtils {

    /**
     * 关注或取消关注商品
     *
     * @param context
     * @param goodsId
     * @param concern
     * @param subscriber
     */
    public static void concernGoods(Context context, String goodsId, boolean concern, SupportSubscriber subscriber) {
        Goods goods = new Goods();
        goods.setGoodsId(goodsId);
        goods.setConcern(concern);

        List<Goods> goodsList = new ArrayList<>(1);
        goodsList.add(goods);

        concernGoodsList(context, goodsList, subscriber);


    }

    /**
     * 关注或取消关注商品列表
     *
     * @param context
     * @param goodsList
     * @param subscriber
     */
    public static void concernGoodsList(Context context, List<Goods> goodsList, SupportSubscriber subscriber) {
        Request request = new Request();

        Map<String, List<Goods>> values = new HashMap<>();
        values.put("dataList", goodsList);

        request.setData(values);

        RXUtils.request(context, request, "goodsConcern", subscriber);

    }

    /**
     * 关注或取消关注商家
     * @param context
     * @param sellerId
     * @param concern
     * @param subscriber
     */
    public static void concernSeller(Context context, String sellerId, boolean concern, SupportSubscriber subscriber) {
        Seller seller = new Seller();
        seller.setSellerId(sellerId);
        seller.setConcern(concern);

        List<Seller> sellerList = new ArrayList<>(1);
        sellerList.add(seller);

        concernSellerList(context, sellerList, subscriber);
    }

    /**
     * 关注或取消关注商家列表
     * @param context
     * @param sellerList
     * @param subscriber
     */
    public static void concernSellerList(Context context, List<Seller> sellerList, SupportSubscriber subscriber) {
        Request request = new Request();

        Map<String, List<Seller>> values = new HashMap<>();
        values.put("dataList", sellerList);

        request.setData(values);

        RXUtils.request(context, request, "shopConcern", subscriber);

    }

}
