package com.weslide.lovesmallscreen.fragments.mall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.showmo.ipc360.IPC360Constans;
import com.showmo.ipc360.play.DeviceslistActivity;
import com.showmo.ipc360.util.spUtil;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.activitys.MsgHomeActivity;
import com.weslide.lovesmallscreen.activitys.OriginalCityAgencyActivity;
import com.weslide.lovesmallscreen.activitys.ShareToEarnActivity;
import com.weslide.lovesmallscreen.activitys.mall.SellerListActivity_old;
import com.weslide.lovesmallscreen.activitys.order.BackOrderListActivity;
import com.weslide.lovesmallscreen.activitys.order.OrderActivity;
import com.weslide.lovesmallscreen.activitys.user.FeedbackActivity;
import com.weslide.lovesmallscreen.activitys.user.PersonInformationActivity;
import com.weslide.lovesmallscreen.activitys.user.RetrieveActivity;
import com.weslide.lovesmallscreen.activitys.user.SettingActivity;
import com.weslide.lovesmallscreen.activitys.withdrawals.CashActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.dao.sp.UserInfoSP;
import com.weslide.lovesmallscreen.exchange.activity.MyExchangeActivity;
import com.weslide.lovesmallscreen.model_yy.javabean.OrderStatusNum;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.MonitorAccount;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.CustomDialogUtil;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.view_yy.activity.ApplayPartnerActivity;
import com.weslide.lovesmallscreen.view_yy.activity.InputStatusActivity;
import com.weslide.lovesmallscreen.view_yy.activity.MyTicketActivity;
import com.weslide.lovesmallscreen.view_yy.activity.OpenShopActivity;
import com.weslide.lovesmallscreen.view_yy.activity.TaoBaoActivity;
import com.weslide.lovesmallscreen.view_yy.customview.MyGridView;
import com.weslide.lovesmallscreen.view_yy.customview.MyScrollView;
import com.weslide.lovesmallscreen.views.adapters.PersonalCenterScoreGvAdapter;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;
import com.xmcamera.core.model.XmAccount;
import com.xmcamera.core.model.XmErrInfo;
import com.xmcamera.core.sys.XmSystem;
import com.xmcamera.core.sysInterface.IXmSystem;
import com.xmcamera.core.sysInterface.OnXmListener;
import com.xmcamera.core.sysInterface.OnXmSimpleListener;

import java.util.ArrayList;
import java.util.List;

import me.dkzwm.smoothrefreshlayout.SmoothRefreshLayout;
import me.dkzwm.smoothrefreshlayout.extra.footer.ClassicFooter;
import me.dkzwm.smoothrefreshlayout.extra.header.ClassicHeader;

/**
 * Created by Dong on 2016/6/6.
 * 个人中心
 */
public class PersonalCenterFragment_New extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, SmoothRefreshLayout.OnRefreshListener {

    View mView, mPartnerView, status_bar;
    private String verifyStatus;
    private TextView mUserName, mUserIdentityName, wait_pay_order_count, wait_confirm_order_count, wait_evaluate_order_count,
            back_order_count, mAllOrder, mEvaluatle, mAcknowledged, mWaitComment, mBackOrder, mIsPartner, mWaitReceive, wait_receive_count, open_store_tv;
    private ImageView mSetting, mMessage, mUserFace, mUserIdentityPic;
    private RelativeLayout mQuestion, mPartnerRll, mOpenStoreRll, mJingKongRll, mSuggestionRll, mShareEarnRll;
    private LinearLayout mProperty, mCollection, mExchange, mDiscount;
    private int mIdentityType;
    //粉丝类型
    private static int FANS = 0;
    //普通合伙人类型
    private static int PARTNER = 1;
    //事业合伙人类型
    private static int SY_PARTNER = 2;
    //事业合伙人标志
    private String SY_PARTNER_TAG = "2";
    //普通合伙人标志
    private String PARTNER_TAG = "1";
    private LoadingDialog loadingDialog;
    private String mUrl;
    private ProgressDialog dialog;
    private IXmSystem xmSystem;
    private spUtil sp;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Toast.makeText(getActivity(), "登录成功！", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 0x124) {
                Toast.makeText(getActivity(), "耐心等待一下下~", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };
    private MyGridView scoreGv;
    private PersonalCenterScoreGvAdapter mScoreAdapter;
    private List<Goods> mScoreList = new ArrayList<>();
    private int scoreListPage = 1;
    private SmoothRefreshLayout refreshLayout;
    private MyScrollView scrollview;
    private TextView mIsPartnerTag;
    private LinearLayout user_info_ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_new_personal_center, container, false);
        //初始化组件(为了性能不用butterknife等注解框架)
        initView();
        //初始化
        initData();
        if (ContextParameter.isLogin()) {
            getUserInfo(ContextParameter.getUserInfo().getUsername());
        } else {
            mUserName.setText("未登录");
            mUserIdentityName.setText("");
            mUserFace.setImageResource(R.drawable.icon_defult);
            mPartnerRll.setVisibility(View.GONE);
            mPartnerView.setVisibility(View.GONE);
            loadingDialog.dismiss();
        }
        getScoreListGood();
        updateOrderStatusNum();
        return mView;
    }

    private void initData() {
        loadingDialog.show();
//        int barHeight = StatusbarUtils.getStatusBarHeight(getActivity());
//        ViewGroup.LayoutParams params = status_bar.getLayoutParams();
//        params.height= barHeight;
//        status_bar.setLayoutParams(params);
        mSetting.setOnClickListener(this);
        mMessage.setOnClickListener(this);
        mQuestion.setOnClickListener(this);
        mAllOrder.setOnClickListener(this);
        mEvaluatle.setOnClickListener(this);
        mAcknowledged.setOnClickListener(this);
        mWaitComment.setOnClickListener(this);
        mWaitReceive.setOnClickListener(this);
        mBackOrder.setOnClickListener(this);
        mProperty.setOnClickListener(this);
        mExchange.setOnClickListener(this);
        mCollection.setOnClickListener(this);
        mDiscount.setOnClickListener(this);
        mPartnerRll.setOnClickListener(this);
        mOpenStoreRll.setOnClickListener(this);
        mJingKongRll.setOnClickListener(this);
        user_info_ll.setOnClickListener(this);
        mSuggestionRll.setOnClickListener(this);
        mShareEarnRll.setOnClickListener(this);
        refreshLayout.setMode(SmoothRefreshLayout.MODE_BOTH);
        refreshLayout.setHeaderView(new ClassicHeader(getActivity()));
        refreshLayout.setFooterView(new ClassicFooter(getActivity()));
        refreshLayout.setResistanceOfPullDown(2.2f);
        refreshLayout.setResistanceOfPullUp(2.2f);
        refreshLayout.setOnRefreshListener(this);
        mScoreAdapter = new PersonalCenterScoreGvAdapter(getActivity(), mScoreList);
        scoreGv.setAdapter(mScoreAdapter);
        scoreGv.setOnItemClickListener(this);
        xmSystem = XmSystem.getInstance();
        sp = new spUtil(getActivity());
        xmSystem.xmInit(getActivity(), "CN", new OnXmSimpleListener() {
            @Override
            public void onErr(XmErrInfo info) {
                Log.v("AAAAA", "init Fail");
            }

            @Override
            public void onSuc() {
                Log.v("AAAAA", "init Suc");
            }
        });

    }

    private void initView() {
        loadingDialog = new LoadingDialog(getActivity());
        scrollview = ((MyScrollView) mView.findViewById(R.id.scrollview));
        mSetting = ((ImageView) mView.findViewById(R.id.setting_iv));
        mMessage = ((ImageView) mView.findViewById(R.id.iv_message));
        mUserFace = ((ImageView) mView.findViewById(R.id.iv_head_portrait));
        mUserIdentityPic = ((ImageView) mView.findViewById(R.id.user_tag_iv));
        mQuestion = ((RelativeLayout) mView.findViewById(R.id.rl_question));
        mPartnerRll = ((RelativeLayout) mView.findViewById(R.id.to_partner_rll));
        mOpenStoreRll = ((RelativeLayout) mView.findViewById(R.id.to_open_store_rll));
        mJingKongRll = ((RelativeLayout) mView.findViewById(R.id.to_my_jiankong_rll));
        mSuggestionRll = ((RelativeLayout) mView.findViewById(R.id.to_suggestion_rll));
        mShareEarnRll = ((RelativeLayout) mView.findViewById(R.id.to_share_earn_rll));
        mUserName = ((TextView) mView.findViewById(R.id.tv_user_name));
        open_store_tv = ((TextView) mView.findViewById(R.id.open_store_tv));
        mUserIdentityName = ((TextView) mView.findViewById(R.id.tv_vip_grade));
        mAllOrder = ((TextView) mView.findViewById(R.id.tv_look_at_order));
        mEvaluatle = ((TextView) mView.findViewById(R.id.tv_to_evaluate));
        mAcknowledged = ((TextView) mView.findViewById(R.id.tv_acknowledged));
        mWaitComment = ((TextView) mView.findViewById(R.id.tv_have_evaluation));
        mWaitReceive = ((TextView) mView.findViewById(R.id.tv_wait_receive));
        mBackOrder = ((TextView) mView.findViewById(R.id.tv_off_the_stocks));
        wait_pay_order_count = ((TextView) mView.findViewById(R.id.wait_pay_order_count));
        wait_confirm_order_count = ((TextView) mView.findViewById(R.id.wait_confirm_order_count));
        wait_evaluate_order_count = ((TextView) mView.findViewById(R.id.wait_evaluate_order_count));
        wait_receive_count = ((TextView) mView.findViewById(R.id.wait_receive_count));
        back_order_count = ((TextView) mView.findViewById(R.id.back_order_count));
        mIsPartner = ((TextView) mView.findViewById(R.id.is_partner_tv));
        mIsPartnerTag = ((TextView) mView.findViewById(R.id.partner_desc_tag));
        mProperty = ((LinearLayout) mView.findViewById(R.id.my_property));
        mExchange = ((LinearLayout) mView.findViewById(R.id.my_exchange));
        user_info_ll = ((LinearLayout) mView.findViewById(R.id.user_info_ll));
        mCollection = ((LinearLayout) mView.findViewById(R.id.my_collection));
        mDiscount = ((LinearLayout) mView.findViewById(R.id.my_discount));
        mPartnerView = mView.findViewById(R.id.below_partner_view);
        scoreGv = ((MyGridView) mView.findViewById(R.id.score_gv));
        refreshLayout = ((SmoothRefreshLayout) mView.findViewById(R.id.refresh_layout));
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (ContextParameter.isLogin()) {
            getUserInfo(ContextParameter.getUserInfo().getUsername());
        } else {
            mUserName.setText("未登录");
            mUserIdentityName.setText("");
            mUserFace.setImageResource(R.drawable.icon_defult);
            mPartnerRll.setVisibility(View.GONE);
            mPartnerView.setVisibility(View.GONE);
        }
        updateOrderStatusNum();*/
    }

    /**
     * 获取个人中心数据
     */
    private void getUserInfo(String userId) {
        Request<UserInfo> request = new Request<UserInfo>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "getUserInfo", new SupportSubscriber<Response<UserInfo>>() {
            @Override
            public void onNext(Response<UserInfo> response) {
                UserInfoSP.setUserInfo(response.getData());
                verifyStatus = response.getData().getVerifyStatus();
                setDate();
            }
        });

    }

    /**
     * 更新订单数量状态
     */
    private void updateOrderStatusNum() {
        RXUtils.request(getActivity(), new Request(), "getOrderStatusNum", new SupportSubscriber<Response<OrderStatusNum>>() {
            @Override
            public void onNext(Response<OrderStatusNum> orderStatusNumResponse) {
                OrderStatusNum orderStatusNum = orderStatusNumResponse.getData();
                if (orderStatusNum.getPayment().equals("0")) {//待付款
                    wait_pay_order_count.setVisibility(View.GONE);
                } else {
                    wait_pay_order_count.setText(orderStatusNum.getPayment());
                    wait_pay_order_count.setVisibility(View.VISIBLE);
                }
                if (orderStatusNum.getShare().equals("0")) {//待分享
                    wait_confirm_order_count.setVisibility(View.GONE);
                } else {
                    wait_confirm_order_count.setText(orderStatusNum.getShare());
                    wait_confirm_order_count.setVisibility(View.VISIBLE);
                }
                if (orderStatusNum.getSendOutGoods().equals("0")) {//待发货
                    wait_evaluate_order_count.setVisibility(View.GONE);
                } else {
                    wait_evaluate_order_count.setText(orderStatusNum.getSendOutGoods());
                    wait_evaluate_order_count.setVisibility(View.VISIBLE);
                }
                if (orderStatusNum.getReceive().equals("0")) {//待收货
                    wait_receive_count.setVisibility(View.GONE);
                } else {
                    wait_receive_count.setText(orderStatusNum.getReceive());
                    wait_receive_count.setVisibility(View.VISIBLE);
                }
                if (orderStatusNum.getChargeback().equals("0")) {//退单售后
                    back_order_count.setVisibility(View.GONE);
                } else {
                    back_order_count.setText(orderStatusNum.getChargeback());
                    back_order_count.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    //获取积分商品列表
    private void getScoreListGood() {
        Request<GetGoodsListBean> request = new Request<>();
        GetGoodsListBean bean = new GetGoodsListBean();
        bean.setMallTyle(Constants.MALL_SOCRE);
        bean.setPageIndex(scoreListPage);
        request.setData(bean);
        RXUtils.request(getActivity(), request, "getGoodsList", new SupportSubscriber<Response<GoodsList>>() {
            @Override
            public void onNext(Response<GoodsList> goodsListResponse) {
                mScoreList.addAll(goodsListResponse.getData().getDataList());
                mScoreAdapter.notifyDataSetChanged();
                refreshLayout.refreshComplete();
            }
        });
    }

    /**
     * 给各控件设值
     */
    private void setDate() {
        mPartnerRll.setVisibility(View.VISIBLE);
        mPartnerView.setVisibility(View.VISIBLE);
        //设置用户昵称
        if (StringUtils.isBlank(ContextParameter.getUserInfo().getUsername())) {
            mUserName.setText("未设置");
        } else {
            mUserName.setText(ContextParameter.getUserInfo().getUsername());
        }
        //设置用户头像
        if (StringUtils.isBlank(ContextParameter.getUserInfo().getHeadimage())) {
            mUserFace.setImageResource(R.drawable.icon_defult);
        } else {
            //设置为圆形图
            Glide.with(getActivity()).load(ContextParameter.getUserInfo().getHeadimage()).asBitmap().placeholder(R.drawable.icon_defult)
                    .error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(mUserFace) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    mUserFace.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
        //用户身份昵称
        if (!StringUtils.isBlank(ContextParameter.getUserInfo().getTk_show())) {
            mUserIdentityName.setText(ContextParameter.getUserInfo().getTk_show());
        }
        if ("-1".equals(verifyStatus)) {
            open_store_tv.setText("我要开店");
        } else {
            open_store_tv.setText("我的店铺");
        }
        //用户身份
        if (StringUtils.isBlank(ContextParameter.getUserInfo().getIspartenr()) || ContextParameter.getUserInfo().getIspartenr().equals("0")) {
            mIsPartner.setText("我要赚钱");
            mIsPartnerTag.setText("成为合伙人推广赚佣金");
            mUserIdentityPic.setImageResource(R.drawable.axpfs_icon);
            mIdentityType = FANS;
        } else if (PARTNER_TAG.equals(ContextParameter.getUserInfo().getIspartenr())) {
            mIsPartner.setText("我要推广");
            mIsPartnerTag.setText("推广赚佣金");
            mUserIdentityPic.setImageResource(R.drawable.icon_syhhr);
            mIdentityType = PARTNER;
        } else if (SY_PARTNER_TAG.equals(ContextParameter.getUserInfo().getIspartenr())) {
            mIsPartner.setText("我要推广");
            mIsPartnerTag.setText("推广赚佣金");
            mUserIdentityPic.setImageResource(R.drawable.icon_csyys);
            mIdentityType = SY_PARTNER;
        }
        //二维码内容
        if (StringUtils.isBlank(ContextParameter.getUserInfo().getTdCode())) {
            if (StringUtils.isBlank(ContextParameter.getUserInfo().getInviteCode())) {
                mUrl = ContextParameter.getClientConfig().getDownload() + "?invitecode=" + "";
            } else {
                mUrl = ContextParameter.getClientConfig().getDownload() + "?invitecode=" + ContextParameter.getUserInfo().getInviteCode();
            }
        } else {
            mUrl = ContextParameter.getUserInfo().getTdCode();
        }
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private void getMonitorAccount() {
        RXUtils.request(getActivity(), new Request(), "getMonitorAccount", new SupportSubscriber<Response<MonitorAccount>>() {
            @Override
            public void onNext(Response<MonitorAccount> monitorAccountResponse) {
                login(monitorAccountResponse.getData().getName(), monitorAccountResponse.getData().getPwd());
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                if (response.getStatus() == -80) {
                    AppUtils.toActivity(getActivity(), RetrieveActivity.class);
                    return;
                }
                CustomDialogUtil.showNoticDialog(getActivity(), response.getMessage());
            }
        });
    }

    /**
     * 登录摄像头
     */
    private void login(String username, String psw) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(psw)) {
            Toast.makeText(getActivity(), "用户名或密码不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        showLoadingDialog();
        try {
            xmSystem.xmLogin(username, psw, new OnXmListener<XmAccount>() {
                @Override
                public void onSuc(XmAccount outinfo) {
                    dialog.dismiss();
                    sp.setUsername(username);
                    sp.setPwd(psw);
                    loginSuc(outinfo);
                }

                @Override
                public void onErr(XmErrInfo info) {
                    dialog.dismiss();
                    mHandler.sendEmptyMessage(0x124);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();
            mHandler.sendEmptyMessage(0x124);
        } finally {

        }
    }

    private void showLoadingDialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("请稍后...");
        dialog.show();
    }

    private void loginSuc(XmAccount info) {
        Intent in = new Intent(getActivity(), DeviceslistActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", info);
        in.putExtras(bundle);
        startActivity(in);
        IPC360Constans.setUserInfo(info);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //个人中心
            case R.id.user_info_ll:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), PersonInformationActivity.class);
                }
                break;
            //疑问
            case R.id.rl_question:
                Intent intent = new Intent(getActivity(), TaoBaoActivity.class);
                intent.putExtra("URL", ContextParameter.getUserInfo().getTk_uri());
                getActivity().startActivity(intent);
                break;
            //全部订单
            case R.id.tv_look_at_order:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), OrderActivity.class);
                }
                break;
            //待付款
            case R.id.tv_to_evaluate:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_PAY);
                    AppUtils.toActivity(getActivity(), OrderActivity.class, bundle);
                }
                break;
            //待分享
            case R.id.tv_acknowledged:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_SHARE);
                    AppUtils.toActivity(getActivity(), OrderActivity.class, bundle);
                }
                break;
            //待发货
            case R.id.tv_have_evaluation:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS);
                    AppUtils.toActivity(getActivity(), OrderActivity.class, bundle);
                }
                break;
            //待收货
            case R.id.tv_wait_receive:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_OF_GOODS);
                    AppUtils.toActivity(getActivity(), OrderActivity.class, bundle);
                }
                break;
            //退单售后
            case R.id.tv_off_the_stocks:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), BackOrderListActivity.class);
                }
                break;
            //我的钱包
            case R.id.my_property:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), CashActivity.class);
                }
                break;
            //我的换货
            case R.id.my_exchange:
//                AppUtils.toActivity(getActivity(), MyScoreActivity.class);//我的积分
                AppUtils.toActivity(getActivity(), MyExchangeActivity.class);
                break;
            //我的收藏
            case R.id.my_collection:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("KEY_SELECT", 1);
                    //店铺收藏
                    AppUtils.toActivity(getActivity(), SellerListActivity_old.class, bundle);
                }
                break;
            //我的优惠券
            case R.id.my_discount:
                if (ContextParameter.isLogin() == true) {
                    AppUtils.toActivity(getActivity(), MyTicketActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                }
                break;
            //我是合伙人
            case R.id.to_partner_rll:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    if (ContextParameter.getUserInfo().getIspartenr().equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 0);
                        AppUtils.toActivity(getActivity(), ApplayPartnerActivity.class, bundle);
                    } else {
                        AppUtils.toActivity(getActivity(), OriginalCityAgencyActivity.class);
                    }
                }
                break;
            //我要开店
            case R.id.to_open_store_rll:
                if (ContextParameter.isLogin() == true) {
                    if (verifyStatus.equals("-1")) {
                        AppUtils.toActivity(getActivity(), OpenShopActivity.class);
                    } else {
                        AppUtils.toActivity(getActivity(), InputStatusActivity.class);
                    }
                } else {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                }
                break;
            //我的监控
            case R.id.to_my_jiankong_rll:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    getMonitorAccount();
                }
                break;
            //意见反馈
            case R.id.to_suggestion_rll:
                if (ContextParameter.isLogin()) {
                    AppUtils.toActivity(getActivity(), FeedbackActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                }
                break;
            //分享赚钱
            case R.id.to_share_earn_rll:
                if (!ContextParameter.isLogin()) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Intent i = new Intent(getActivity(), ShareToEarnActivity.class);
                    i.putExtra("invatationCodeUrl", mUrl);
                    startActivity(i);
                }
                break;
            //设置
            case R.id.setting_iv:
                AppUtils.toActivity(getActivity(), SettingActivity.class);
                break;
            //消息
            case R.id.iv_message:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), MsgHomeActivity.class);
                }
                break;
        }
    }

    //积分商品gv item点击事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AppUtils.toGoods(getActivity(), mScoreList.get(i).getGoodsId());
    }

    //SmoothRefreshLayout的刷新监听
    @Override
    public void onRefreshBegin(boolean isRefresh) {
        if (isRefresh) {//下拉刷新
            scoreListPage = 1;
            mScoreList.clear();
            getScoreListGood();
        } else {//上拉加载
            scoreListPage++;
            getScoreListGood();
        }
    }

    //SmoothRefreshLayout的刷新完成监听
    @Override
    public void onRefreshComplete() {
    }
}
