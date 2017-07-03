package com.weslide.lovesmallscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.weslide.lovesmallscreen.activitys.UnlockActivity;
import com.weslide.lovesmallscreen.dao.sp.LocalConfigSP;
import com.weslide.lovesmallscreen.models.config.LocalConfig;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;

/**
 * Created by xu on 2016/8/4.
 * 用于控制锁屏的广播
 */
public class LockScreenReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        L.e("收到广播：" + action);

        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            L.i("屏幕熄灭" + ContextParameter.getLocalConfig().isOpenUnlock());

        } else if (Intent.ACTION_SCREEN_ON.equals(action)) {
            L.i("屏幕亮起");

            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int callState = telephonyManager.getCallState();
            if (callState == TelephonyManager.CALL_STATE_OFFHOOK || callState == TelephonyManager.CALL_STATE_RINGING) {
            } else {
                //因为UnlockActivity的运行模式为singleTop，所以不用顾虑会重复启动的问题
                if (ContextParameter.getLocalConfig().isOpenUnlock()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(UnlockActivity.KEY_IS_APP, false);
                    AppUtils.toActivity(context, UnlockActivity.class, bundle);
                }
            }

        }
    }
}
