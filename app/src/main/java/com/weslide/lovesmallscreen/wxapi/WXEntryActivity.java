package com.weslide.lovesmallscreen.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateWXAuthMessage;
import com.weslide.lovesmallscreen.utils.L;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ntop on 15/9/4.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        api.handleIntent(intent, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("WXEntryActivity被创建");

        api = WXAPIFactory.createWXAPI(ArchitectureAppliation.getAppliation(), Constants.WEXIN_APP_ID, true);
        // 将该app注册到微信
        api.registerApp(Constants.WEXIN_APP_ID);

        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

        EventBus.getDefault().post(new UpdateWXAuthMessage(baseResp));

        finish();

    }
}
