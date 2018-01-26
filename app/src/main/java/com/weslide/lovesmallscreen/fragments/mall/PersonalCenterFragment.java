package com.weslide.lovesmallscreen.fragments.mall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.activitys.MsgHomeActivity;
import com.weslide.lovesmallscreen.activitys.OriginalCityAgencyActivity;
import com.weslide.lovesmallscreen.activitys.ShareToEarnActivity;
import com.weslide.lovesmallscreen.activitys.mall.SellerListActivity_old;
import com.weslide.lovesmallscreen.activitys.order.BackOrderListActivity;
import com.weslide.lovesmallscreen.activitys.order.OrderActivity;
import com.weslide.lovesmallscreen.activitys.user.BindingContactsActivity;
import com.weslide.lovesmallscreen.activitys.user.ExemptActivity;
import com.weslide.lovesmallscreen.activitys.user.FeedbackActivity;
import com.weslide.lovesmallscreen.activitys.user.GoodsConcernActivity;
import com.weslide.lovesmallscreen.activitys.user.LockScreenActivity;
import com.weslide.lovesmallscreen.activitys.user.MyAddressActivity;
import com.weslide.lovesmallscreen.activitys.user.MyScoreActivity;
import com.weslide.lovesmallscreen.activitys.user.PersonInformationActivity;
import com.weslide.lovesmallscreen.activitys.user.RetrieveActivity;
import com.weslide.lovesmallscreen.activitys.user.SettingActivity;
import com.weslide.lovesmallscreen.activitys.withdrawals.CashActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.dao.sp.UserInfoSP;
import com.weslide.lovesmallscreen.model_yy.javabean.OrderStatusNum;
import com.weslide.lovesmallscreen.models.ImageAndText;
import com.weslide.lovesmallscreen.models.MonitorAccount;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.view_yy.activity.ApplayPartnerActivity;
import com.weslide.lovesmallscreen.view_yy.activity.InputStatusActivity;
import com.weslide.lovesmallscreen.view_yy.activity.MyTicketActivity;
import com.weslide.lovesmallscreen.view_yy.activity.OpenShopActivity;
import com.weslide.lovesmallscreen.view_yy.activity.TaoBaoActivity;
import com.weslide.lovesmallscreen.views.custom.SuperGridView;
import com.xmcamera.core.model.XmAccount;
import com.xmcamera.core.model.XmErrInfo;
import com.xmcamera.core.sys.XmSystem;
import com.xmcamera.core.sysInterface.IXmSystem;
import com.xmcamera.core.sysInterface.OnXmListener;
import com.xmcamera.core.sysInterface.OnXmSimpleListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/6/6.
 * 个人中心
 */
public class PersonalCenterFragment extends BaseFragment {
    View mView;
    @BindView(R.id.iv_head_portrait)
    ImageView mHeadPortrait;//头像
    @BindView(R.id.user_tag_iv)
    ImageView user_tag_iv;//头像
    @BindView(R.id.gv_layout)
    SuperGridView mGridView;
    @BindView(R.id.tv_user_name)
    TextView mUserName;
    @BindView(R.id.tv_vip_grade)
    TextView mUserVipGrade;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.tv_user_red_envelope_earnings)
    TextView mUserRedEvelope;
    @BindView(R.id.tv_user_cash_earnings)
    TextView mUserCash;
    @BindView(R.id.tv_user_exempt)
    TextView mUserExempt;
    @BindView(R.id.tv_my_invitation_code)
    TextView mUserInvitationCode;
    //积分显示
    @BindView(R.id.rl_score)
    RelativeLayout rlScre;
    //    @BindView(R.id.tv_my_fans)
//    TextView fans;
    @BindView(R.id.ll_exempt)
    LinearLayout llExempt;
    @BindView(R.id.tv_look_at_order)
    TextView tvLookAtOrder;
    @BindView(R.id.tv_to_evaluate)
    TextView tvToEvaluate;
    @BindView(R.id.tv_acknowledged)
    TextView tvAcknowledged;
    @BindView(R.id.tv_have_evaluation)
    TextView tvHaveEvaluation;
    @BindView(R.id.tv_off_the_stocks)
    TextView tvOffTheStocks;
    @BindView(R.id.iv_user_two_dimension_code)
    ImageView ivUserTwoDimensionCode;
    @BindView(R.id.rl_binding_contacts)
    RelativeLayout rlBindingContacts;
    @BindView(R.id.iv_inviter_img)
    ImageView ivInviterImg;
    @BindView(R.id.setting_iv)
    ImageView setting_iv;
    @BindView(R.id.tv_inviter_name)
    TextView tvInviterName;
    @BindView(R.id.wait_evaluate_order_count)
    TextView wait_evaluate_order_count;
    @BindView(R.id.wait_pay_order_count)
    TextView wait_pay_order_count;
    @BindView(R.id.wait_confirm_order_count)
    TextView wait_confirm_order_count;
    @BindView(R.id.back_order_count)
    TextView back_order_count;
    @BindView(R.id.ll_inviter)
    LinearLayout llInviter;
    private int[] drawint = new int[]{/*R.drawable.icon_jifen_wode_my,*/
            R.drawable.icon_dizi_wode_my,/* R.drawable.ixon_tixian_my,*/
            /*R.drawable.icon_ziliao_my,*/ R.drawable.icon_dianpu,
            R.drawable.icon_guangzhu_my,/*R.drawable.icon_shenri,*/
            R.drawable.icon_youhuiquan, /*R.drawable.icon_woshishangjia_my,*/
            R.drawable.icon_jiankong, /*R.drawable.icon_shezi_my,*/ R.drawable.icon_lianxiwomen,
            R.drawable.icon_woyaokaidian, R.drawable.icon_cshehuoren, R.drawable.icon_suoping_my};
    private String[] userString = new String[]{
            /*"我的积分",*/ "我的地址"/*,"我的提现"*/, /*"个人资料",*/
            "店铺收藏", "商品关注"/*,"送礼提醒"*/, "我的优惠券",
            /*"我是商家",*/ "我的监控", /*"我的设置",*/ "联系我们", "我要开店", "合伙人", "锁屏开关"};
    private List<ImageAndText> data;
    private Bitmap logo;
    private String mUrl;
    IXmSystem xmSystem;
    private String name, pwd;
    spUtil sp;
    private int JIANKONG = 1;
    private int HEHUOREN = 2;
    private String verifyStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_personal_center, container, false);

        ButterKnife.bind(this, mView);

        init();

        return mView;

    }

    private void init() {

        if (ContextParameter.getClientConfig().getHasNewVerson() != null && ContextParameter.getClientConfig().getHasNewVerson().equals("1")) {
            setting_iv.setImageResource(R.drawable.icon_heiseshezhidian);
        } else {
            setting_iv.setImageResource(R.drawable.icon_heiseshezhi);
        }
        data = new ArrayList<>();

        mGridView.setFocusable(false);
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
      /*  xmSystem.xmGetLoggerConfiger()
                .setFileName(Environment.getExternalStorageDirectory()+"/xmcamera_default_log.log")
                .registerOnLogListener(new OnLoggerListener() {
                    @Override
                    public void onLog(String str) {
                            Log.e("OnLog", str);
                    }
                });*/
    }

    @Override
    public void onResume() {
        super.onResume();
        addData();
        if (ContextParameter.isLogin() == true) {
            getUserInfo(ContextParameter.getUserInfo().getUsername());
//            Log.d("雨落无痕丶", "onResume: gggggg");
        } else {
//            remove();
            removeH();
//            Log.d("雨落无痕丶", "onResume: 移除");
        }
        setDate();
        updateOrderStatusNum();
    }

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
                if (orderStatusNum.getChargeback().equals("0")) {//退单售后
                    back_order_count.setVisibility(View.GONE);
                } else {
                    back_order_count.setText(orderStatusNum.getChargeback());
                    back_order_count.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getMonitorAccount(int type) {
        RXUtils.request(getActivity(), new Request(), "getMonitorAccount", new SupportSubscriber<Response<MonitorAccount>>() {

            @Override
            public void onNext(Response<MonitorAccount> monitorAccountResponse) {
                if (type == JIANKONG) {
                    login(monitorAccountResponse.getData().getName(), monitorAccountResponse.getData().getPwd());
                } else if (type == HEHUOREN) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 1);
                    AppUtils.toActivity(getActivity(), ApplayPartnerActivity.class, bundle);
                }
                // login("18178313317","123456");
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                if (response.getStatus() == -80) {
                    AppUtils.toActivity(getActivity(), RetrieveActivity.class);
                    return;
                }
                T.showShort(getActivity(), response.getMessage());
            }
        });

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
                if (response.getData().getIspartenr().equals("0")) {
                    user_tag_iv.setImageResource(R.drawable.axpfs_icon);
                } else if (response.getData().getIspartenr().equals("1")) {
                    user_tag_iv.setImageResource(R.drawable.syhhr_icon);
                } else if (response.getData().getIspartenr().equals("2")) {
                    user_tag_iv.setImageResource(R.drawable.icon_csyys);
                } else if (response.getData().getIspartenr().equals("3")) {
                    user_tag_iv.setImageResource(R.drawable.icon_csyys);
                }
                setDate();
                /*if (StringUtils.isBlank(ContextParameter.getUserInfo().getSellerId())) {
//                    remove();
                } else */
                /*if (StringUtils.isBlank(ContextParameter.getUserInfo().getIspartenr()) || ContextParameter.getUserInfo().getIspartenr().equals("0")) {
                    removeH();
                    Log.d("雨落无痕丶", "onNext: rm");
                } else {
                    addData();
                    Log.d("雨落无痕丶", "onNext: add");
                }*/
                addData();
            }
        });

    }

    /**
     * 给各控件设值
     */
    private void setDate() {
        if (ContextParameter.isLogin() == true) {

            rlScre.setVisibility(View.VISIBLE);

//            mUserIntegral.setText("积分 " + ContextParameter.getUserInfo().getScore());
            tv_score.setText(ContextParameter.getUserInfo().getScore());
            //设置用户昵称
            if (StringUtils.isBlank(ContextParameter.getUserInfo().getUsername())) {
                mUserName.setText("未设置");
            } else {
                mUserName.setText(ContextParameter.getUserInfo().getUsername());
            }
            //设置用户头像
            if (StringUtils.isBlank(ContextParameter.getUserInfo().getHeadimage())) {

                mHeadPortrait.setImageResource(R.drawable.icon_defult);

            } else {

                Glide.with(getActivity()).load(ContextParameter.getUserInfo().getHeadimage()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(mHeadPortrait) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mHeadPortrait.setImageDrawable(circularBitmapDrawable);
                        logo = resource;

                    }
                });
            }
            //粉丝
//            fans.setText(ContextParameter.getUserInfo().getFansNumber() + "粉丝>");

            mUserRedEvelope.setText(ContextParameter.getUserInfo().getCashpoint());
            mUserCash.setText(ContextParameter.getUserInfo().getAvailableMoney());
            mUserExempt.setText(ContextParameter.getUserInfo().getFreeCount());
            //会员类型
           /* if(!StringUtils.isBlank(ContextParameter.getUserInfo().getVipType())) {
                if (ContextParameter.getUserInfo().getVipType().equals("0")) {
                    mUserVipGrade.setText("未开通会员");
                } else {
                    mUserVipGrade.setText(ContextParameter.getUserInfo().getVipType() + "级会员");
                }
            }*/
            if (!StringUtils.isBlank(ContextParameter.getUserInfo().getTk_show())) {
                mUserVipGrade.setText(ContextParameter.getUserInfo().getTk_show());
            }
            mUserInvitationCode.setText(ContextParameter.getUserInfo().getInviteCode());
            //是否为商家
//            if (StringUtils.isBlank(ContextParameter.getUserInfo().getSellerId())) {
//                remove();
//            }
            if (StringUtils.isBlank(ContextParameter.getUserInfo().getIspartenr()) || ContextParameter.getUserInfo().getIspartenr().equals("0")) {
                removeH();
            }
            //是否绑定推荐人，如果绑定则显示绑定上级头像及昵称
            if (ContextParameter.getUserInfo().getBindingInviter() == true) {
                llInviter.setVisibility(View.VISIBLE);
                rlBindingContacts.setVisibility(View.GONE);
                if (StringUtils.isBlank(ContextParameter.getUserInfo().getInviterImg())) {
                    ivInviterImg.setImageResource(R.drawable.icon_defult);
                } else {
                    Glide.with(getActivity()).load(ContextParameter.getUserInfo().getInviterImg()).asBitmap().error(R.drawable.icon_defult).placeholder(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(ivInviterImg) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            ivInviterImg.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }
                if (!StringUtils.isBlank(ContextParameter.getUserInfo().getInviterName())) {
                    tvInviterName.setText(ContextParameter.getUserInfo().getInviterName());
                }
            } else {
                llInviter.setVisibility(View.GONE);
                rlBindingContacts.setVisibility(View.VISIBLE);

            }
            if (StringUtils.isBlank(ContextParameter.getUserInfo().getTdCode())) {
                if (StringUtils.isBlank(ContextParameter.getUserInfo().getInviteCode())) {
                    mUrl = ContextParameter.getClientConfig().getDownload() + "?invitecode=" + "";
                } else {
                    mUrl = ContextParameter.getClientConfig().getDownload() + "?invitecode=" + ContextParameter.getUserInfo().getInviteCode();
                }
            } else {
                mUrl = ContextParameter.getUserInfo().getTdCode();
            }

        } else {
//            rlScre.setVisibility(View.GONE);
            mUserName.setText("未登录");
            mUserVipGrade.setText("");
            mUserRedEvelope.setText("0");
            mUserCash.setText("0");
            mUserExempt.setText("0");
            mHeadPortrait.setImageResource(R.drawable.icon_defult);
            mUserInvitationCode.setText("");
//            remove();
            removeH();
//            fans.setText("");
            llInviter.setVisibility(View.GONE);
            rlBindingContacts.setVisibility(View.VISIBLE);
            ivInviterImg.setImageResource(R.drawable.icon_defult);
            mUrl = ContextParameter.getClientConfig().getDownload() + "?invitecode=" + "";

        }

    }

    @OnClick({R.id.tv_look_at_order, R.id.tv_to_evaluate, R.id.tv_acknowledged, R.id.tv_have_evaluation, R.id.tv_off_the_stocks,
            R.id.tv_user_name, R.id.iv_head_portrait, R.id.iv_message, R.id.iv_two_dimension_code,
            R.id.iv_user_two_dimension_code, R.id.to_share_earn_money, R.id.tv_binding_contacts, R.id.ll_exempt, R.id.question_iv,
            R.id.tv_must_see_strategy, R.id.ll_red_packet, R.id.ll_link_money, R.id.setting_iv, R.id.my_score, R.id.personal_info_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_score:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    //我的积分
                    AppUtils.toActivity(getActivity(), MyScoreActivity.class);
                }
                break;
            case R.id.personal_info_iv:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    //个人资料
                    AppUtils.toActivity(getActivity(), PersonInformationActivity.class);
                }
                break;
            case R.id.tv_look_at_order://查看全部订单
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), OrderActivity.class);
                }
                break;
            case R.id.tv_to_evaluate://待付款
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_PAY);
                    AppUtils.toActivity(getActivity(), OrderActivity.class, bundle);
                }
                break;
            case R.id.tv_acknowledged://待确认
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS);
                    AppUtils.toActivity(getActivity(), OrderActivity.class, bundle);
                }
                break;
            case R.id.tv_have_evaluation://待评价
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_COMMENT);
                    AppUtils.toActivity(getActivity(), OrderActivity.class, bundle);
                }
                break;
            case R.id.tv_off_the_stocks://已完成
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), BackOrderListActivity.class);
                }
                break;
            case R.id.iv_user_two_dimension_code://点击查看二维码
//                showTwoDimensionCode(mUrl);
                showTwoDimensionCode(ContextParameter.getClientConfig().getPersonalCenterShareContent().getTargetUrl() + "?img=" + ContextParameter.getUserInfo().getHeadimage() + "&name=" + ContextParameter.getUserInfo().getUsername()
                        + "&phone=" + ContextParameter.getUserInfo().getPhone() + "&code=" + ContextParameter.getUserInfo().getInviteCode() + "&appVersion=" + AppUtils.getVersionCode(getActivity()));
                break;
            case R.id.to_share_earn_money:
                if (!ContextParameter.isLogin()) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Intent intent = new Intent(getActivity(), ShareToEarnActivity.class);
                    intent.putExtra("invatationCodeUrl", mUrl);
                    startActivity(intent);
                }
                break;
            /*case R.id.tv_my_fans://查看粉丝
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("number", ContextParameter.getUserInfo().getFansNumber());
                    AppUtils.toActivity(getActivity(), MyFansActivity.class, bundle);

                }
                break;*/
            case R.id.tv_binding_contacts://绑定联系人
                if (ContextParameter.isLogin() == false) {

                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);

                } else {

                    AppUtils.toActivity(getActivity(), BindingContactsActivity.class);

                }

                break;

            case R.id.tv_must_see_strategy://必看攻略
                URIResolve.resolve(getActivity(), HTTP.URL_SEE_STRATEGY + HTTP.formatJSONData(new Request()));

                break;
            case R.id.ll_red_packet:
                if (ContextParameter.isLogin() == false) {

                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);

                } else {

                    URIResolve.resolve(getActivity(), HTTP.URL_RED_PAPER_LIST + HTTP.formatJSONData(new Request()));

                }
                break;
            case R.id.ll_link_money:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), CashActivity.class);
                   /* URIResolve.resolve(getActivity(), HTTP.URL_MONEY_LINK_LIST + HTTP.formatJSONData(new Request()));
                    L.e("999999999999999999999",HTTP.URL_MONEY_LINK_LIST + HTTP.formatJSONData(new Request()));*/
                }
                break;

            case R.id.tv_user_name:

                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), PersonInformationActivity.class);
                }
//                getActivity().finish();
                break;

            case R.id.iv_head_portrait:

                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);

                } else {
                    AppUtils.toActivity(getActivity(), PersonInformationActivity.class);
                }
                break;

            case R.id.iv_message:

                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
//                    AppUtils.toActivity(getActivity(), MyMessageActivity.class);
                    AppUtils.toActivity(getActivity(), MsgHomeActivity.class);
                }
                break;

            case R.id.iv_two_dimension_code:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    QRCodeUtil.scan(getActivity());
                }
                break;
            case R.id.ll_exempt:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    AppUtils.toActivity(getActivity(), ExemptActivity.class);
                }
                break;
            case R.id.setting_iv:
                AppUtils.toActivity(getActivity(), SettingActivity.class);
                break;
            case R.id.question_iv:
                Intent intent = new Intent(getActivity(), TaoBaoActivity.class);
                intent.putExtra("URL", ContextParameter.getUserInfo().getTk_uri());
                getActivity().startActivity(intent);
                break;
        }
    }

    /**
     * 适配器
     */
    private BaseAdapter userBaseAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final int Localposition = position;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_user_personal_center, parent, false);
            }
            ImageView imageview = (ImageView) convertView
                    .findViewById(R.id.imageview_item_user_center);
            TextView textView = (TextView) convertView
                    .findViewById(R.id.textview_item_grid_user_center);
            imageview.setImageResource(data.get(position).getImageId()); // 图片
            textView.setText(data.get(position).getText());

            RelativeLayout lItem = (RelativeLayout) convertView
                    .findViewById(R.id.ll_item);// 线性布局
            lItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    switch (data.get(position).getId()) {
                        /*case 0:
                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                //我的积分
                                AppUtils.toActivity(getActivity(), MyScoreActivity.class);
                            }

                            break;*/
                        case 0:
                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                //我的地址
                                AppUtils.toActivity(getActivity(), MyAddressActivity.class);
                            }
                            break;
                    /*    case 2:

                            if(ContextParameter.isLogin()==false){
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            }else {
                                AppUtils.toActivity(getActivity(), MyDataVerificationActivity.class);
                            }

                            break;*/
                        case 8:
                            /*if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                //个人资料(暂时屏蔽)
                                AppUtils.toActivity(getActivity(), PersonInformationActivity.class);
                            }*/

                            //锁屏设置
                            AppUtils.toActivity(getActivity(), LockScreenActivity.class);
                            break;
                        case 1:

                            /*if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putInt("KEY_SELECT", 1);
                                //店铺收藏
                                AppUtils.toActivity(getActivity(), SellerListActivity_old.class, bundle);
                            }*/

                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putInt("KEY_SELECT", 1);
                                //店铺收藏
                                AppUtils.toActivity(getActivity(), SellerListActivity_old.class, bundle);
                            }

                            break;
                        case 2:

                            /*if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                //商品关注
                                AppUtils.toActivity(getActivity(), GoodsConcernActivity.class);
                            }*/

                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                //商品关注
                                AppUtils.toActivity(getActivity(), GoodsConcernActivity.class);
                            }
                            break;
                       /* case 5:

                            if(ContextParameter.isLogin()==false){
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            }else {
                                AppUtils.toActivity(getActivity(), MyScoreActivity.class);
                            }

                            break;*/
                        case 3:
                            //优惠券
                            if (ContextParameter.isLogin() == true) {
                                AppUtils.toActivity(getActivity(), MyTicketActivity.class);
                            } else {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            }

                            break;
                        case 4:
                            /*if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                            //我是商家
                                AppUtils.toActivity(getActivity(), MyStoreActivity.class);

                            }

                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                //我的监控
                                getMonitorAccount(JIANKONG);
                            }*/

                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                //我的监控
                                getMonitorAccount(JIANKONG);
                            }

                            break;
                        case 5:
                            //联系我们
                            if (ContextParameter.isLogin()) {
                                AppUtils.toActivity(getActivity(), FeedbackActivity.class);
                            } else {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            }
                            break;
                        case 6:
                            //我要开店
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
                        case 7:

                            /*if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                //城市合伙人
                                Bundle bundle = new Bundle();
                                bundle.putString(TaoKeActivity.KEY_LOAD_URL, ContextParameter.getUserInfo().getTk_uri());
                                AppUtils.toActivity(getActivity(), TaoKeActivity.class, bundle);
                            }*/

                            if (ContextParameter.isLogin() == false) {
                                AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                            } else {
                                //合伙人(原生)
                                if (ContextParameter.getUserInfo().getIspartenr().equals("0")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("type", 0);
                                    AppUtils.toActivity(getActivity(), ApplayPartnerActivity.class, bundle);
                                } else {
                                    AppUtils.toActivity(getActivity(), OriginalCityAgencyActivity.class);
                                }
                            }
                            break;
                    }
                }
            });
            return convertView;
        }

    };

    /**
     * 删除data数据
     */
    private void remove() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == 6) {
                //删除我的监控或我是商家
                data.remove(data.get(6));
                break;
            }
        }
        mGridView.setAdapter(userBaseAdapter);
    }

    /**
     * 删除data数据
     */
    private void removeH() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == 7) {
                data.remove(data.get(data.size() - 2));
                userBaseAdapter.notifyDataSetChanged();
                break;
            }
        }
//        mGridView.setAdapter(userBaseAdapter);
    }

    /**
     * 添加data数据
     */
    private void addData() {
        data.clear();
        YY:
        for (int i = 0; i < drawint.length; i++) {
            ImageAndText it = new ImageAndText();
            if (i == drawint.length - 2) {
                if (ContextParameter.getUserInfo().getIspartenr() != null) {
                    if (ContextParameter.getUserInfo().getIspartenr().equals("0")) {
                        it.setText("我想赚钱");
                        it.setImageId(R.drawable.icon_wxzq);
                    } else if (ContextParameter.getUserInfo().getIspartenr().equals("1")) {
                        it.setText("合伙人");
                        it.setImageId(R.drawable.icon_pthhr);
                    } else if (ContextParameter.getUserInfo().getIspartenr().equals("2")) {
                        it.setText("事业合伙人");
                        it.setImageId(R.drawable.syhhr);
                    } else if (ContextParameter.getUserInfo().getIspartenr().equals("3")) {
                        it.setText("城市运营商");
                        it.setImageId(R.drawable.csyys);
                    }
                    it.setId(i);
                    data.add(it);
//                    break YY;
                }
            }
            if (i != drawint.length - 2) {
                it.setText(userString[i]);
                it.setImageId(drawint[i]);
                it.setId(i);
                data.add(it);
            }
        }
        mGridView.setAdapter(userBaseAdapter);
    }

    /**
     * 点击出现二维码
     */
    private void showTwoDimensionCode(String content) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LayoutInflater inflater2 = getActivity().getLayoutInflater();
        //得到界面视图
        View currean_View = inflater.inflate(R.layout.fragment_personal_center, null);
        //得到要弹出的界面视图
        View view = inflater2.inflate(R.layout.view_two_dimension_code_show, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_two_dimension_code);
        Bitmap bitmap = QRCodeUtil.createQRImage(content, 300, 300);
        imageView.setImageBitmap(bitmap);
        WindowManager windowManager = getActivity().getWindowManager();
        int width = windowManager.getDefaultDisplay().getWidth();
        int heigth = windowManager.getDefaultDisplay().getHeight();
        Log.i("width", width + "");
        Log.i("height", heigth + "");
        PopupWindow popupWindow = new PopupWindow(view, (int) (width * 0.8), (int) (heigth * 0.5));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //显示在屏幕中央
        popupWindow.showAtLocation(currean_View, Gravity.CENTER, 0, 40);
        //popupWindow弹出后屏幕半透明
        BackgroudAlpha((float) 0.5);
        //弹出窗口关闭事件
        popupWindow.setOnDismissListener(new popupwindowdismisslistener());
    }

    //设置屏幕背景透明度
    private void BackgroudAlpha(float alpha) {
        // TODO Auto-generated method stub
        WindowManager.LayoutParams l = getActivity().getWindow().getAttributes();
        l.alpha = alpha;
        getActivity().getWindow().setAttributes(l);
    }

    //点击其他部分popwindow消失时，屏幕恢复透明度
    class popupwindowdismisslistener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            BackgroudAlpha((float) 1);
        }
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
                    closeLoadingDialog();

                    //  mHandler.sendEmptyMessage(0x123);
                    sp.setUsername(username);
                    sp.setPwd(psw);
                    loginSuc(outinfo);
                }

                @Override
                public void onErr(XmErrInfo info) {
                    closeLoadingDialog();
                    mHandler.sendEmptyMessage(0x124);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            closeLoadingDialog();
            mHandler.sendEmptyMessage(0x124);
        } finally {

        }
    }

    ProgressDialog dialog;

    private void showLoadingDialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("请稍后...");
        dialog.show();
    }

    private void closeLoadingDialog() {
        dialog.dismiss();
    }

    Handler mHandler = new Handler() {
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

    private void loginSuc(XmAccount info) {
        Intent in = new Intent(getActivity(), DeviceslistActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", info);
        in.putExtras(bundle);
        startActivity(in);
        IPC360Constans.setUserInfo(info);
    }
}
