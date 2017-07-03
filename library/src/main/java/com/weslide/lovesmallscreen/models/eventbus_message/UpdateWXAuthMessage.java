package com.weslide.lovesmallscreen.models.eventbus_message;

import com.tencent.mm.sdk.modelbase.BaseResp;

/**
 * Created by xu on 2016/8/18.
 * 微信登录授权
 */
public class UpdateWXAuthMessage extends EventMessage {

    private BaseResp resp;

    public UpdateWXAuthMessage(BaseResp resp){
        this.setResp(resp);
    }


    public BaseResp getResp() {
        return resp;
    }

    public void setResp(BaseResp resp) {
        this.resp = resp;
    }
}
