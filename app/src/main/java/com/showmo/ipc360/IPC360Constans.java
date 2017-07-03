package com.showmo.ipc360;

import com.xmcamera.core.model.XmAccount;

/**
 * Created by xu on 2016/7/28.
 * 储存登录信息
 */
public class IPC360Constans {

    private static XmAccount userInfo;


    public static XmAccount getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(XmAccount userInfo) {
        IPC360Constans.userInfo = userInfo;
    }
}
