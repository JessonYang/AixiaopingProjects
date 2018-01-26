package com.weslide.lovesmallscreen.network;

import com.weslide.lovesmallscreen.managers.pay.PayModel;
import com.weslide.lovesmallscreen.model_yy.javabean.AchieveBean;
import com.weslide.lovesmallscreen.model_yy.javabean.ApplyPartnerModel;
import com.weslide.lovesmallscreen.model_yy.javabean.Brand;
import com.weslide.lovesmallscreen.model_yy.javabean.Cate;
import com.weslide.lovesmallscreen.model_yy.javabean.CouponsBean;
import com.weslide.lovesmallscreen.model_yy.javabean.ExchangeGoodDtModel;
import com.weslide.lovesmallscreen.model_yy.javabean.FeedbackTipsBean;
import com.weslide.lovesmallscreen.model_yy.javabean.GoodsModel;
import com.weslide.lovesmallscreen.model_yy.javabean.HomeTicketsModel;
import com.weslide.lovesmallscreen.model_yy.javabean.OrderStatusNum;
import com.weslide.lovesmallscreen.model_yy.javabean.PartnerIconListModel;
import com.weslide.lovesmallscreen.model_yy.javabean.RongUserInfo;
import com.weslide.lovesmallscreen.model_yy.javabean.Shopcate;
import com.weslide.lovesmallscreen.model_yy.javabean.TaoBaoUrlModel;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketGoodsDtModel;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketListModel;
import com.weslide.lovesmallscreen.model_yy.javabean.TypeListBean;
import com.weslide.lovesmallscreen.model_yy.javabean.UpdateStoreInfo;
import com.weslide.lovesmallscreen.models.Address;
import com.weslide.lovesmallscreen.models.AssetsMsgDtModel;
import com.weslide.lovesmallscreen.models.BackOrder;
import com.weslide.lovesmallscreen.models.BackOrderList;
import com.weslide.lovesmallscreen.models.CanPayBean;
import com.weslide.lovesmallscreen.models.CommentList;
import com.weslide.lovesmallscreen.models.DeleteMsgModel;
import com.weslide.lovesmallscreen.models.Exempt;
import com.weslide.lovesmallscreen.models.Fans;
import com.weslide.lovesmallscreen.models.FreeMall;
import com.weslide.lovesmallscreen.models.GlideAdvert;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.HeadquartersType;
import com.weslide.lovesmallscreen.models.Home;
import com.weslide.lovesmallscreen.models.HomePage;
import com.weslide.lovesmallscreen.models.Live;
import com.weslide.lovesmallscreen.models.Message;
import com.weslide.lovesmallscreen.models.MonitorAccount;
import com.weslide.lovesmallscreen.models.MsgHomeModel;
import com.weslide.lovesmallscreen.models.MyPartners;
import com.weslide.lovesmallscreen.models.MyScore;
import com.weslide.lovesmallscreen.models.NewHomePageModel;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.models.OrderList;
import com.weslide.lovesmallscreen.models.OrderMsgDtModel;
import com.weslide.lovesmallscreen.models.OrderMsgModel;
import com.weslide.lovesmallscreen.models.Preferential99;
import com.weslide.lovesmallscreen.models.RedPaper;
import com.weslide.lovesmallscreen.models.ScoreExchangeMall;
import com.weslide.lovesmallscreen.models.SecondKill;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.SellerInfo;
import com.weslide.lovesmallscreen.models.SellerList;
import com.weslide.lovesmallscreen.models.ShoppingCarList;
import com.weslide.lovesmallscreen.models.Show;
import com.weslide.lovesmallscreen.models.SpecialLocalProduct;
import com.weslide.lovesmallscreen.models.SystemMsgDtModel;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.models.Withdrawals;
import com.weslide.lovesmallscreen.models.ZoneList;
import com.weslide.lovesmallscreen.models.bean.ExchangeGoodListResModel;
import com.weslide.lovesmallscreen.models.bean.ExchangeReplyResModel;
import com.weslide.lovesmallscreen.models.bean.LeaderBean;
import com.weslide.lovesmallscreen.models.bean.MyChangeListResModel;
import com.weslide.lovesmallscreen.models.bean.Orders;
import com.weslide.lovesmallscreen.models.bean.OriginalCityAgencyBean;
import com.weslide.lovesmallscreen.models.bean.RechargeCardModel;
import com.weslide.lovesmallscreen.models.bean.TeamDataModel;
import com.weslide.lovesmallscreen.models.bean.UpdateScoreBean;
import com.weslide.lovesmallscreen.models.bean.UploadFileBean;
import com.weslide.lovesmallscreen.models.config.ClientConfig;
import com.weslide.lovesmallscreen.models.demo.IpInfo;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by xu on 2016/4/28.
 * <p>
 * 所有请求接口的定义
 */
public interface API {

    /**
     * Demo中使用的接口
     *
     * @param ip 查询的ip
     * @return
     */
    @GET("service/getIpInfo.php")
    Call<Response<IpInfo>> getIpInfo(@Query("ip") String ip);

//    @POST("handler/askHandler.ashx")
//    @FormUrlEncoded
//    Call<Test> gettest(@Field("action") String action, @Field("voteId") String voteId, @Field("CompId") String CompId );


    /**---------------------------------------------------config START--------------------------------------------------------------------- */

    /**
     * 获取城市列表
     *
     * @param jsonData
     * @return
     */
    @POST("config/getZoneList")
    Call<Response<ZoneList>> getZoneList(@Query("data") String jsonData);

    /**
     * 获取客户端运行配置
     *
     * @param jsonData
     * @return
     */
    @POST("config/getClientConfig")
    Call<Response<ClientConfig>> getClientConfig(@Query("data") String jsonData);

    /**---------------------------------------------------config END--------------------------------------------------------------------- */


    /**---------------------------------------------------USER START--------------------------------------------------------------------- */

    /**
     * 登录
     *
     * @param jsonData
     * @return
     */
    @POST("users/login")
    //通过注解设置请求头
//    @Headers({
//            "Cache-Control: max-age=640000",
//            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko"
//    })
    Call<Response<UserInfo>> login(@Query("data") String jsonData);

    /**
     * 注册
     *
     * @param jsonData
     * @return
     */
    @POST("users/register")
    Call<Response<UserInfo>> register(@Query("data") String jsonData);


    /**
     * 上传文件
     *
     * @param userId
     * @param fileUse
     * @param uploadFile
     * @return
     */
    @Multipart
    @POST("users/uploadFilePic")
    Call<Response<UploadFileBean>> uploadFile(@Part("userId") String userId, @Part("fileUse") String fileUse,
                                              @Part MultipartBody.Part uploadFile);

    /**
     * 获取个人信息
     *
     * @param jsonData
     * @return
     */
    @POST("users/getBaseInfo")
    Call<Response<UserInfo>> getUserInfo(@Query("data") String jsonData);

    /**
     * 获取监控账号密码
     */
    @POST("users/getMonitorAccount")
    Call<Response<MonitorAccount>> getMonitorAccount(@Query("data") String jsonData);

    /**
     * 检查红包
     *
     * @param jsonData
     * @return checkAdminRedPaper
     */
    @POST("users/checkAdminRedPaper")
    Call<Response<RedPaper>> checkRedPaper(@Query("data") String jsonData);

    /**
     * 检查订单红包
     *
     * @param jsonData
     * @return checkOrderRedPaper
     */
    @POST("users/checkOrderRedPaper")
    Call<Response<RedPaper>> checkOrderRedPaper(@Query("data") String jsonData);

    /**
     * 拆红包
     */
    @POST("users/openAdminRedPaper")
    Call<Response<RedPaper>> openAdminRedPaper(@Query("data") String jsonData);

    /**
     * 获取验证码D
     *
     * @param jsonData
     * @return
     */
    @POST("users/sendCaptcha")
    Call<Response> sendCaptcha(@Query("data") String jsonData);

    /**
     * 添加地址
     *
     * @param jsonData
     * @return
     */
    @POST("users/putAddress")
    Call<Response> putAddress(@Query("data") String jsonData);

    /**
     * 提交用户获得的积分
     *
     * @param jsonData
     * @return
     */
    @POST("users/updateScore")
    Call<Response<UpdateScoreBean>> updateScore(@Query("data") String jsonData);

    /**
     * 获取积分列表
     */
    @POST("users/getScoreList")
    Call<Response<DataList<MyScore>>> getScoreList(@Query("data") String jsonData);

    /**
     * 获取消息列表
     */
    @POST("users/getMessageList")
    Call<Response<DataList<Message>>> getMessageList(@Query("data") String jsonData);

    /**
     * 获取地址列表
     */
    @POST("users/getAddressList")
    Call<Response<DataList<Address>>> getAddressList(@Query("data") String jsonData);

    /**
     * 获取用户默认地址
     *
     * @param jsonData
     * @return
     */
    @POST("users/getDefaultAddress")
    Call<Response<Address>> getDefaultAddress(@Query("data") String jsonData);

    /**
     * 删除地址
     */
    @POST("users/removeAddress")
    Call<Response<Address>> removeAddress(@Query("data") String jsonData);

    /**
     * 设置默认地址
     */
    @POST("users/setDefaultAddress")
    Call<Response<Address>> setDefaultAddress(@Query("data") String jsonData);

    /**
     * 修改地址
     */
    @POST("users/updateAddress")
    Call<Response<Address>> updateAddress(@Query("data") String jsonData);

    /**
     * 获取粉丝列表(个人中心)
     */
    @POST("users/getFansList")
    Call<Response<DataList<Fans>>> getFansList(@Query("data") String jsonData);

    /**
     * 获取粉丝列表(融云会话列表二级页面)
     */
    @POST("users/getFansList2")
    Call<Response<DataList<Fans>>> getFansList2(@Query("data") String jsonData);

    /**
     * 意见反馈
     */
    @POST("users/feedback")
    Call<Response<Fans>> feedback(@Query("data") String jsonData);

    /**
     * 获取兑换券列表
     */
    @POST("users/getExemptList")
    Call<Response<DataList<Exempt>>> getExemptList(@Query("data") String jsonData);

    /**
     * 绑定联系人
     */
    @POST("users/bangdingContacts")
    Call<Response<LeaderBean>> bangdingContacts(@Query("data") String jsonData);

    /**
     * 个人资料修改
     */
    @POST("users/changeBaseInfo")
    Call<Response> changeBaseInfo(@Query("data") String jsonData);

    /**
     * 忘记密码及找回密码
     */
    @POST("users/forgetPassword")
    Call<Response> forgetPassword(@Query("data") String jsonData);

    /**
     * 修改密码
     */
    @POST("users/changePassword")
    Call<Response> changePassword(@Query("data") String jsonData);

    /**
     * 成为会员
     */
    @POST("users/becomeVip")
    Call<Response<PayModel>> becomeVip(@Query("data") String jsonData);

    /**
     * 获取商家信息
     */
    @POST("users/getSellerInfo")
    Call<Response<SellerInfo>> getSellerInfo(@Query("data") String jsonData);

    /**
     * 待发货
     */
    @POST("order/sellerConfirmReceipt")
    Call<Response> sellerConfirmReceipt(@Query("data") String jsonData);

    /**
     * 待兑换
     */
    @POST("order/sellerConfirmExchange")
    Call<Response> sellerConfirmExchange(@Query("data") String jsonData);

    /**
     * 积分兑换
     */
    @POST("mall/scoreExchangeMall")
    Call<Response<ScoreExchangeMall>> scoreExchangeMall(@Query("data") String jsonData);
    /**---------------------------------------------------USER END--------------------------------------------------------------------- */

    /**---------------------------------------------------ADVERTS START--------------------------------------------------------------------- */

    /**
     * 获取滑屏广告
     *
     * @param jsonData
     * @return
     */
    @POST("advers/getAdverImgs")
    Call<Response<GlideAdvert>> getAdverImgs(@Query("data") String jsonData);

    /**---------------------------------------------------ADVERTS END--------------------------------------------------------------------- */


    /**---------------------------------------------------MALL START--------------------------------------------------------------------- */
    /**
     * 请求首页数据
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getHome")
    Call<Response<Home>> getHome(@Query("data") String jsonData);

    @POST("mall/getHomeNew")
    Call<Response<HomePage>> getHomePage(@Query("data") String jsonData);

    @POST("mall/getHomeNew")
    Call<Response<NewHomePageModel>> getNewHomePage(@Query("data") String jsonData);

    /**
     * 请求首页直播列表数据
     */
    @POST("live/getTopLive")
    Call<Response<List<Live>>> getTopLive(@Query("data") String jsonData);

    /**
     * 请求更多直播列表数据
     */
    @POST("live/getLive")
    Call<Response<DataList<Live>>> getLive(@Query("data") String jsonData);

    /**
     * 直播详情
     */
    @POST("live/getLiveById")
    Call<Response<DataList<Live>>> getLiveById(@Query("data") String jsonData);

    /**
     * 是否有换货会
     */
    @POST("config/getIsShow")
    Call<Response<Show>> getIsShow(@Query("data") String jsonData);

    /**
     * 免单商城
     *
     * @param jsonData
     * @return
     */
    @POST("mall/freeMall")
    Call<Response<FreeMall>> freeMall(@Query("data") String jsonData);


    /**
     * 提交商家位置
     *
     * @param jsonData
     * @return
     */
    @POST("mall/postSellerAddress")
    Call<Response> postSellerAddress(@Query("data") String jsonData);

    /**
     * 99特惠商城
     *
     * @param jsonData
     * @return
     */
    @POST("mall/preferential99")
    Call<Response<Preferential99>> preferential99(@Query("data") String jsonData);

    /**
     * 特产速递
     *
     * @param jsonData
     * @return
     */
    @POST("mall/specialLocalProduct")
    Call<Response<SpecialLocalProduct>> specialLocalProduct(@Query("data") String jsonData);

    /**
     * 商品关注
     *
     * @param jsonData
     * @return
     */
    @POST("mall/goodsConcern")
    Call<Response> goodsConcern(@Query("data") String jsonData);

    /**
     * 关注商家
     *
     * @param jsonData
     * @return
     */
    @POST("mall/shopConcern")
    Call<Response> shopConcern(@Query("data") String jsonData);

    /**
     * 请求商家列表
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getSellerList")
    Call<Response<SellerList>> getSellerList(@Query("data") String jsonData);

    /**
     * 请求商家列表
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getSellerListForNew")
    Call<Response<SellerList>> getSellerListForNew(@Query("data") String jsonData);

    /**
     * 请求关注商家列表
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getConcernSellerList")
    Call<Response<SellerList>> getConcernSellerList(@Query("data") String jsonData);

    /**
     * 请求商品列表
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getGoodsList")
    Call<Response<GoodsList>> getGoodsList(@Query("data") String jsonData);

    /**
     * 请求本地特产分类
     */
    @POST("change/getGoodsType")
    Call<Response<HeadquartersType>> getGoodsType(@Query("data") String jsonData);

    /**
     * 请求商家信息
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getSeller")
    Call<Response<Seller>> getSeller(@Query("data") String jsonData);

    /**
     * 请求商品信息
     *
     * @param jsonData
     * @return
     */
    @POST("mall/getGoods")
    Call<Response<Goods>> getGoods(@Query("data") String jsonData);

    /**
     * 秒杀商城
     */
    @POST("mall/secondKillMall")
    Call<Response<DataList<SecondKill>>> secondKillMall(@Query("data") String jsonData);

    /**
     * 获取商品关注
     */
    @POST("mall/getConcernGoodsList")
    Call<Response<GoodsList>> getConcernGoodsList(@Query("data") String jsonData);

    /**---------------------------------------------------MALL END--------------------------------------------------------------------- */

    /**---------------------------------------------------ORDER START--------------------------------------------------------------------- */

    /**
     * 请求评论列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getCommentList")
    Call<Response<CommentList>> getCommentList(@Query("data") String jsonData);

    /**
     * 请求购物车数据列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getShoppingCarList")
    Call<Response<ShoppingCarList>> getShoppingCarList(@Query("data") String jsonData);

    /**
     * 移除购物车数据
     *
     * @param jsonData
     * @return
     */
    @POST("order/removeShoppingCar")
    Call<Response> removeShoppingCar(@Query("data") String jsonData);

    /**
     * 更新购物车数据
     *
     * @param jsonData
     * @return
     */
    @POST("order/updateShoppingCar")
    Call<Response> updateShoppingCar(@Query("data") String jsonData);

    /**
     * 向购物车添加商品
     *
     * @param jsonData
     * @return
     */
    @POST("order/putShoppingCar")
    Call<Response> putShoppingCar(@Query("data") String jsonData);


    /**
     * 生成临时订单
     *
     * @param jsonData
     * @return
     */
    @POST("order/createTempOrderList")
    Call<Response<OrderList>> createTempOrderList(@Query("data") String jsonData);

    /**
     * 提交订单
     *
     * @param jsonData
     * @return
     */
    @POST("order/confirmOrder")
    Call<Response> confirmOrder(@Query("data") String jsonData);

    /**
     * 获取订单列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getOrderList")
    Call<Response<OrderList>> getOrderList(@Query("data") String jsonData);

    /**
     * 获取退单列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getBackOrderList")
    Call<Response<BackOrderList>> getBackOrderList(@Query("data") String jsonData);


    /**
     * 获取退单详情
     *
     * @param jsonData
     * @return
     */
    @POST("order/getBackOrder")
    Call<Response<BackOrder>> getBackOrder(@Query("data") String jsonData);

    /**
     * 获取订单信息
     *
     * @param jsonData
     * @return
     */
    @POST("order/getOrder")
    Call<Response<Order>> getOrder(@Query("data") String jsonData);


    /**
     * 取消订单
     *
     * @param jsonData
     * @return
     */
    @POST("order/cancelOrder")
    Call<Response> cancelOrder(@Query("data") String jsonData);

    /**
     * 确认收货
     *
     * @param jsonData
     * @return
     */
    @POST("order/confirmReceipt")
    Call<Response> confirmReceipt(@Query("data") String jsonData);

    /**
     * 提交评论
     *
     * @param jsonData
     * @return
     */
    @POST("order/putComment")
    Call<Response> putComment(@Query("data") String jsonData);

    /**
     * 订单支付
     *
     * @param jsonData
     * @return
     */
    @POST("order/payOrders")
    Call<Response<PayModel>> payOrders(@Query("data") String jsonData);

    /**
     * 获取商家退单列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getSellerBackOrderList")
    Call<Response<BackOrderList>> getSellerBackOrderList(@Query("data") String jsonData);

    /**
     * 获取商家订单列表
     *
     * @param jsonData
     * @return
     */
    @POST("order/getSellerOrderList")
    Call<Response<OrderList>> getSellerOrderList(@Query("data") String jsonData);

    /**
     * 申请退单
     *
     * @param jsonData
     * @return
     */
    @POST("order/applyBackOrder")
    Call<Response> applyBackOrder(@Query("data") String jsonData);

    /**
     * 商家确认订单
     *
     * @param jsonData
     * @return
     */
    @POST("order/sellerConfirmOrder")
    Call<Response> sellerConfirmOrder(@Query("data") String jsonData);


    /**
     * 商家退单审核
     *
     * @param jsonData
     * @return
     */
    @POST("order/sellerBackOrderVerify")
    Call<Response> sellerBackOrderVerify(@Query("data") String jsonData);

    /**---------------------------------------------------WITHDRAWALS--------------------------------------------------------------------- */

    /**
     * 现金首页
     */
    @POST("users/getUsersM")
    Call<Response<DataList<Withdrawals>>> getUsersM(@Query("data") String jsonData);

    /**
     * 未支付
     */
    @POST("users/getwithdrawalsInfo")
    Call<Response<DataList<Withdrawals>>> getwithdrawalsInfo(@Query("data") String jsonData);

    /**
     * 已支付
     */
    @POST("users/getwithdrawalsInfoForPay")
    Call<Response<DataList<Withdrawals>>> getwithdrawalsInfoForPay(@Query("data") String jsonData);

    /**
     * 下一步提现
     */
    @POST("users/applyWithdrawals")
    Call<Response<Withdrawals>> applyWithdrawals(@Query("data") String jsonData);

    /**
     * 提现申请
     */
    @POST("users/withdrawals")
    Call<Response<Withdrawals>> withdrawals(@Query("data") String jsonData);

    /**
     *
     * 城市代理上部分数据
     * @return
     */
    @POST("taoke/getPartnerInfos")
    Call<Response<OriginalCityAgencyBean>> getPartnerInfo(@Query("data") String jsonData);

    /**
     * 城市代理订单详情
     */
    @POST("taoke/getPartnerOrders")
    Call<Response<Orders>> getPartnerOrder(@Query("data") String jsonData);

    /**
     * 我的合伙人详情
     */
    @POST("taoke/getPartnerLists")
    Call<Response<MyPartners>> getPartnerList(@Query("data") String jsonData);

    /**
     * 可结算
     */
    @POST("taoke/settlement")
    Call<Response<CanPayBean>> settlement(@Query("data") String jsonData);

    @POST("message/getMessageList")
    Call<Response<OrderMsgModel>> getMsgList(@Query("data") String jsonData);

    @POST("message/getMessageHome")
    Call<Response<MsgHomeModel>> getMsgTypes(@Query("data") String jsonData);

    @POST("message/msgDetail")
    Call<Response<SystemMsgDtModel>> getMsgDetailSystem(@Query("data") String jsonData);

    @POST("message/msgDetail")
    Call<Response<AssetsMsgDtModel>> getMsgDetailAssets(@Query("data") String jsonData);

    @POST("message/msgDetail")
    Call<Response<OrderMsgDtModel>> getMsgDetailOrder(@Query("data") String jsonData);

    @POST("message/delMessage")
    Call<Response<DeleteMsgModel>> deleteMsg(@Query("data") String jsonData);

    @POST("taoke/ticketGoodsDetail")
    Call<Response<TicketGoodsDtModel>> getTicketGoodsDetail(@Query("data") String jsonData);

    @POST("taoke/homeTickets")
    Call<Response<HomeTicketsModel>> getHomeTickets(@Query("data") String jsonData);

    @POST("taoke/partnerRemark")
    Call<Response> getPartnerRemark(@Query("data") String jsonData);

    @POST("taoke/partnerDelete")
    Call<Response> getPartnerDelete(@Query("data") String jsonData);

    @POST("taoke/ticketList")
    Call<Response<TicketListModel>> getTicketList(@Query("data") String jsonData);

    @POST("taoke/partnerList")
    Call<Response<PartnerIconListModel>> partnerList(@Query("data") String jsonData);

    @POST("taoke/cate")
    Call<Response<Cate>> getCate(@Query("data") String jsonData);

    @POST("taoke/getGoods")
    Call<Response<GoodsModel>> getSaveMoneyGoods(@Query("data") String jsonData);

    @POST("taoke/brand")
    Call<Response<Brand>> getBrand(@Query("data") String jsonData);

    @POST("taoke/getBrandGoods")
    Call<Response<GoodsModel>> getBrandGoods(@Query("data") String jsonData);

    @POST("users/ApplyPartner")
    Call<Response<ApplyPartnerModel>> applyPartner(@Query("data") String jsonData);

    @POST("users/getCheckPhone")
    Call<Response> getCheckPhone(@Query("data") String jsonData);

    @POST("users/getDefaultMsg")
    Call<Response> getDefaultMsg(@Query("data") String jsonData);

    //银联支付成功后通知我方服务器
    @POST("order/synchroYiLianNotify")
    Call<Response> synchroYiLianNotify(@Query("data") String jsonData);

    @POST("users/feedbackTips")
    Call<Response<FeedbackTipsBean>> feedbackTips(@Query("data") String jsonData);

    @POST("taoke/commodityType")
    Call<Response<TypeListBean>> commodityType(@Query("data") String jsonData);

    @POST("taoke/coupons")
    Call<Response<CouponsBean>> getCoupons(@Query("data") String jsonData);

    @POST("taoke/achieve")
    Call<Response<AchieveBean>> achieve(@Query("data") String jsonData);

    /**
     * 提交开店资料
     */
    @POST("sellers/updateStoreInfo")
    Call<Response<UpdateStoreInfo>> updateStoreInfo(@Query("data") String jsonData);

    /**
     * 提交资料状态审核
     */
    @POST("sellers/storeVerifyStatus")
    Call<Response<UpdateStoreInfo>> storeVerifyStatus(@Query("data") String jsonData);

    /**
     * 选择店铺类型
     */
    @POST("sellers/getShopcategoryInfo")
    Call<Response<List<Shopcate>>> getShopcategoryInfo(@Query("data") String jsonData);

    /**
     * 提交资料状态审核
     */
    @POST("sellers/returnCheckstatus")
    Call<Response> returnCheckstatus(@Query("data") String jsonData);


    /**
     * 获取跳转淘宝链接
     */
    @POST("taoke/findGoodsUrl")
    Call<Response<TaoBaoUrlModel>> findGoodsUrl(@Query("data") String jsonData);

    /**
     * 删除优惠券
     */
    @POST("taoke/delcoupon")
    Call<Response> delcoupon(@Query("data") String jsonData);

    /**
     * 上传崩溃日志到服务器
     */
    @POST("users/uploadLog")
    Call<Response> upLoadLog(@Query("data") String jsonData);

    /**
     * 获取融云聊天列表userinfo
     */
    @POST("users/getUserInfo")
    Call<Response<RongUserInfo>> getRongUserInfo(@Query("data") String jsonData);

    /**
     * 获取订单状态对应订单数
     */
    @POST("users/getOrderStatusNum")
    Call<Response<OrderStatusNum>> getOrderStatusNum(@Query("data") String jsonData);

    /**
     * 获取购买商品成功后拼团页面数据
     */
    @POST("order/teamShare")
    Call<Response<TeamDataModel>> teamShare(@Query("data") String jsonData);

    /**
     * 接收通过qr赠送积分
     */
    @POST("integral/receiveQRScore")
    Call<Response> receiveQRScore(@Query("data") String jsonData);

    /**
     * 实卡充值
     */
    @POST("integral/recharge")
    Call<Response<RechargeCardModel>> recharge(@Query("data") String jsonData);

    /**
     * 换货会首页商品列表
     */
    @POST("change/getChangeMallList")
    Call<Response<ExchangeGoodListResModel>> getChangeMallList(@Query("data") String jsonData);

    /**
     * 换货会首页帖子列表
     */
    @POST("change/getChangeReplys")
    Call<Response<ExchangeReplyResModel>> getChangeReplys(@Query("data") String jsonData);

    /**
     * 我的换货检测列表个数
     */
    @POST("change/myChangeList")
    Call<Response<MyChangeListResModel>> myChangeList(@Query("data") String jsonData);

    /**
     * 换货会商品详情
     */
    @POST("change/getGoodsDetail")
    Call<Response<ExchangeGoodDtModel>> getGoodsDetail(@Query("data") String jsonData);

    /**
     * 换货会交易明细选择商品
     */
    @POST("change/getMyChangeGoods")
    Call<Response<ExchangeGoodDtModel>> getMyChangeGoods(@Query("data") String jsonData);

    /**
     * 换货会发起换货
     */
    @POST("change/putChangeOrder")
    Call<Response> putChangeOrder(@Query("data") String jsonData);

}
