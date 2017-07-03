package com.weslide.lovesmallscreen.dao.sp;

import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.models.config.LocalConfig;
import com.weslide.lovesmallscreen.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xu on 2016/5/30.
 * 存储本地配置
 */
public class LocalConfigSP {

    /**
     * 当前登录的用户信息
     */
    public static final String FILE_LOCAL_CONFIG = "FILE_LOCAL_CONFIG";
    /**
     * 滑屏图片请求时的时间
     */
    public static final String KEY_GLIDE_IMG_REQEUST_TIME = "KEY_GLIDE_IMG_REQEUST_TIME";
    /**
     * 　第一次运行
     */
    public static final String KEY_FIRST_LAUNCHER = "KEY_FIRST_LAUNCHER";
    /**
     * 第一次运行时的系统版本,暂时实现的功能是在版本更新后，能显示引导图
     */
    public static final String KEY_FIRST_LAUNCHER_VERSION = "KEY_FIRST_LAUNCHER_VERSION";
    /**
     * 打开锁屏广告
     */
    public static final String KEY_OPEN_UNLOCK = "KEY_OPEN_UNLOCK";

    /**
     * 打开推送
     */
    public static final String KEY_OPEN_PUSH = "KEY_OPEN_PUSH";
    private static String KEY_OPEN_VOICE = "KEY_OPEN_VOICE";

    public static void setLocalConfig(LocalConfig localConfig) {
        Map<String, Object> values = new HashMap<>();
        values.put(KEY_GLIDE_IMG_REQEUST_TIME, localConfig.getGlideImgRequestTime());
        values.put(KEY_FIRST_LAUNCHER, localConfig.isFirstLauncher());
        values.put(KEY_FIRST_LAUNCHER_VERSION, localConfig.getFirstLauncherVersion());
        values.put(KEY_OPEN_UNLOCK, localConfig.isOpenUnlock());
        values.put(KEY_OPEN_PUSH, localConfig.isOpenPush());
        values.put(KEY_OPEN_VOICE, localConfig.isOpenVoice());

        SPUtils.clear(ArchitectureAppliation.getAppliation(), FILE_LOCAL_CONFIG);
        SPUtils.puts(ArchitectureAppliation.getAppliation(), FILE_LOCAL_CONFIG, values);
    }

    public static LocalConfig getLocalConfig() {
        LocalConfig localConfig = new LocalConfig();
        Map<String, ?> values = SPUtils.getAll(ArchitectureAppliation.getAppliation(), FILE_LOCAL_CONFIG);
        localConfig.setGlideImgRequestTime(SPUtils.getValueByMapToLong(values, KEY_GLIDE_IMG_REQEUST_TIME, localConfig.getGlideImgRequestTime()));
        localConfig.setFirstLauncher(SPUtils.getValueByMapToBoolean(values, KEY_FIRST_LAUNCHER, localConfig.isFirstLauncher()));
        localConfig.setFirstLauncherVersion(SPUtils.getValueByMapToString(values, KEY_FIRST_LAUNCHER_VERSION, localConfig.getFirstLauncherVersion()));
        localConfig.setOpenUnlock(SPUtils.getValueByMapToBoolean(values, KEY_OPEN_UNLOCK, localConfig.isOpenUnlock()));
        localConfig.setOpenPush(SPUtils.getValueByMapToBoolean(values, KEY_OPEN_PUSH, localConfig.isOpenPush()));
        localConfig.setOpenVoice(SPUtils.getValueByMapToBoolean(values, KEY_OPEN_VOICE, localConfig.isOpenVoice()));

        return localConfig;
    }
}
