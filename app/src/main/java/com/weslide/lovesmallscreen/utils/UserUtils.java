package com.weslide.lovesmallscreen.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.umeng.analytics.MobclickAgent;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.HomeActivity;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.dao.sp.UserInfoSP;
import com.weslide.lovesmallscreen.models.UserInfo;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;

/**
 * Created by xu on 2016/6/2.
 * 用户的各种操作
 */
public class UserUtils {

    /**
     * 登出
     * 退出当前账号
     *
     * @param context
     */
    public static void logout(Context context) {
        //退出友盟统计
        MobclickAgent.onProfileSignOff();
        //断开融云连接
        RongIM.getInstance().disconnect();
        Bundle bundle = new Bundle();
//        bundle.putString(LoginOptionActivity.KEY_LAUNCHER_FRAGMENT, LoginFragment.class.getName());
        AppUtils.toActivity(context, LoginOptionActivity.class, Intent.ACTION_VIEW, Intent.FLAG_ACTIVITY_CLEAR_TOP, bundle);
        UserInfoSP.clear(context);
        ContextParameter.setUserInfo(new UserInfo());
        if (HomeActivity.activity != null) {
            HomeActivity.activity.finish();
            Log.d("雨落无痕丶", "logout: 登出");
        }
    }

    /**
     * 账号在其他设备中登录
     * 单点登录
     *
     * @param context
     */
    public static void otherLogin(Context context) {
        Bundle bundle = new Bundle();
//        bundle.putString(LoginOptionActivity.KEY_LAUNCHER_FRAGMENT, LoginFragment.class.getName());
        bundle.putBoolean(LoginOptionActivity.KEY_OTHER_LOGIN, true);
        AppUtils.toActivity(context, LoginOptionActivity.class, Intent.ACTION_VIEW, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK, bundle);
        UserInfoSP.clear(context);
        ContextParameter.setUserInfo(new UserInfo());
    }

    /**
     * 处理登录，没登录的情况下，跳转到登录
     *
     * @param context
     */
    public static boolean handlerLogin(FragmentActivity context) {

        if (!ContextParameter.isLogin()) {

            boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
            SimpleDialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
                @Override
                public void onPositiveActionClicked(DialogFragment fragment) {
                    super.onPositiveActionClicked(fragment);

                    login(context);
                }

                @Override
                public void onNegativeActionClicked(DialogFragment fragment) {
                    super.onNegativeActionClicked(fragment);
                }
            };


            builder.message("进行下面的操作需要登录哦~").title("登录提示")
                    .positiveAction("去登录")
                    .negativeAction("再等等");

            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(context.getSupportFragmentManager(), null);


        } else {
            return true;
        }

        return false;

    }

    /**
     * 登录
     */
    public static void login(Context context) {
        Bundle bundle = new Bundle();
//        bundle.putString(LoginOptionActivity.KEY_LAUNCHER_FRAGMENT, LoginFragment.class.getName());
//        bundle.putString(LoginOptionActivity.KEY_LAUNCHER_FRAGMENT, LoginFragment.class.getName());
        AppUtils.toActivity(context, LoginOptionActivity.class, Intent.ACTION_VIEW, Intent.FLAG_ACTIVITY_NEW_TASK, bundle);
        UserInfoSP.clear(context);
    }

    //跳转淘宝
    public static void toTaobao(String url,Context context) {
        //提供给三方传递配置参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        AlibcBasePage alibcBasePage = new AlibcPage(url);
        //设置页面打开方式
        AlibcShowParams showParams = null;
        if (isPkgInstalled("com.taobao.taobao",context) == false) {
            showParams = new AlibcShowParams(OpenType.Auto, false);
        } else {
            showParams = new AlibcShowParams(OpenType.Native, false);
        }
        //使用百川sdk提供默认的Activity打开detail
        AlibcTrade.show(HomeActivity.activity, alibcBasePage, showParams, null, exParams,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                    }
                });
    }

    //检验手机是否安装了某个应用
    private static boolean isPkgInstalled(String pkgName,Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

}
