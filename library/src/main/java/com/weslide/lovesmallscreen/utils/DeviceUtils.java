package com.weslide.lovesmallscreen.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.List;

/**
 * Created by xu on 2016/5/6.
 * 关于手机当前设备信息的工具类
 */
public class DeviceUtils {

    /**
     * 检查指定包名的应用是否在本设备中存在
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApplication(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 获取像素密度
     *
     * @param context
     * @return
     */
    public static int getDip(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /**
     * 获取设备码
     *
     * @param context
     * @return
     */
    public static String getMachineCode(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

}
