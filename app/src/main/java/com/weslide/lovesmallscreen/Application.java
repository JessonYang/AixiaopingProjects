package com.weslide.lovesmallscreen;

import android.content.Intent;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.igexin.sdk.PushManager;
import com.rey.material.app.ThemeManager;
import com.squareup.leakcanary.LeakCanary;
//import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.weslide.lovesmallscreen.managers.LocationService;

import net.aixiaoping.unlock.views.UnlockView;

/**
 * Created by xu on 2016/5/23.
 */
public class Application extends ArchitectureAppliation {

    public boolean alreadyInit = false;

    private UnlockView unlockView;
    public LocationService locationService;
    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
              //  T.showShort(Application.this,"初始化成功");
            }

            @Override
            public void onFailure(int i, String s) {
              //  T.showShort(Application.this,"初始化失败");
            }
        });

//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }



    /**
     * 初始化整个应用程序
     */
    public void initApplication(){

        if(!alreadyInit){
            super.init();

            // SDK初始化，第三方程序启动时，都要进行SDK初始化工作
            Log.d("GetuiSdkDemo", "initializing sdk...");
            PushManager.getInstance().initialize(this.getApplicationContext());
            locationService = new LocationService(getApplicationContext());
            PlatformConfig.setQQZone(Constants.QQ_APP_ID,Constants.QQ_APP_KEY);
            PlatformConfig.setWeixin(Constants.WEXIN_APP_ID,Constants.WEXIN_APP_APPSECRET);
            ThemeManager.init(this, 2, 0, null);

            setUnlockView(new UnlockView(this));

            Intent intent = new Intent(getApplicationContext(), AthanasiaService.class);
            startService(intent);

            //启动一个服务，长期在后台隔半个小时刷新一次
            Intent refushIntent = new Intent(this, RefushService.class);
            startService(refushIntent);

            alreadyInit = true;
        }
    }

    public UnlockView getUnlockView() {
        return unlockView;
    }

    public void setUnlockView(UnlockView unlockView) {
        this.unlockView = unlockView;
    }
}
