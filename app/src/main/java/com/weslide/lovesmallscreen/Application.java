package com.weslide.lovesmallscreen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.igexin.sdk.PushManager;
import com.rey.material.app.ThemeManager;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.weslide.lovesmallscreen.activitys.CrashHandler;
import com.weslide.lovesmallscreen.managers.LocationService;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.view_yy.model.CustomizeMessage;
import com.weslide.lovesmallscreen.view_yy.model.CustomizeMessageItemProvider;

import net.aixiaoping.unlock.views.UnlockView;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.RichContentMessage;

//import com.umeng.analytics.MobclickAgent;

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
        //友盟数据统计场景设置
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        //初始化融云
        RongIM.init(this, Constants.RONGIM_APP_KEY);
        RongIM.registerMessageType(CustomizeMessage.class);
        RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider());
        RongIM.getInstance().setReadReceiptConversationTypeList(Conversation.ConversationType.PRIVATE);
        initListenner();
    }

    private void initListenner() {
        //会话界面操作监听
        RongIM.setConversationBehaviorListener(new RongIM.ConversationBehaviorListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                return false;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                if (message.getContent() instanceof RichContentMessage) {
                    AppUtils.toGoods(context, ((RichContentMessage) message.getContent()).getExtra());
                    return true;
                }
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });
    }


    /**
     * 初始化整个应用程序
     */
    public void initApplication() {

        if (!alreadyInit) {
            super.init();

            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());

            // SDK初始化，第三方程序启动时，都要进行SDK初始化工作
            Log.d("GetuiSdkDemo", "initializing sdk...");
            PushManager.getInstance().initialize(this.getApplicationContext());
            locationService = new LocationService(getApplicationContext());
            PlatformConfig.setQQZone(Constants.QQ_APP_ID, Constants.QQ_APP_KEY);
            PlatformConfig.setWeixin(Constants.WEXIN_APP_ID, Constants.WEXIN_APP_APPSECRET);
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
