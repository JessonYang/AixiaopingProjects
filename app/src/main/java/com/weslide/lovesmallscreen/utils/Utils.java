package com.weslide.lovesmallscreen.utils;

import android.graphics.Paint;
import android.widget.TextView;

/**
 * Created by xu on 2016/6/22.
 * 项目中常用的工具方法
 */
public class Utils {

    /**
     * 增加中划线
     * @param textView
     */
    public static void strikethrough(TextView textView){
        textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
    }

    private static long lastClickTime;

    /**
     * 预防用户重复点击
     * @return
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
