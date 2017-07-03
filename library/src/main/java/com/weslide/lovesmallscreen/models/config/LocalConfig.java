package com.weslide.lovesmallscreen.models.config;

/**
 * Created by xu on 2016/5/30.
 * 保存一些本地数据
 */
public class LocalConfig {
    private long glideImgRequestTime;
    private boolean firstLauncher = true;
    private String firstLauncherVersion = "0";
    private boolean openUnlock = false;
    private boolean openPush = true;

    public boolean isOpenVoice() {
        return openVoice;
    }

    public void setOpenVoice(boolean openVoice) {
        this.openVoice = openVoice;
    }

    private boolean openVoice = true;


    /**　第一次运行 */
    public boolean isFirstLauncher() {
        return firstLauncher;
    }

    public void setFirstLauncher(boolean firstLauncher) {
        this.firstLauncher = firstLauncher;
    }

    /** 第一次运行时的系统版本,暂时实现的功能是在版本更新后，能显示引导图 */
    public String getFirstLauncherVersion() {
        return firstLauncherVersion;
    }

    public void setFirstLauncherVersion(String firstLauncherVersion) {
        this.firstLauncherVersion = firstLauncherVersion;
    }

    /** 滑屏图片请求时的时间 */
    public long getGlideImgRequestTime() {
        return glideImgRequestTime;
    }

    public void setGlideImgRequestTime(long glideImgRequestTime) {
        this.glideImgRequestTime = glideImgRequestTime;
    }


    public boolean isOpenUnlock() {
        return openUnlock;
    }

    public void setOpenUnlock(boolean openUnlock) {
        this.openUnlock = openUnlock;
    }


   /** 打开推送 */
    public boolean isOpenPush() {
        return openPush;
    }

    public void setOpenPush(boolean openPush) {
        this.openPush = openPush;
    }
}
