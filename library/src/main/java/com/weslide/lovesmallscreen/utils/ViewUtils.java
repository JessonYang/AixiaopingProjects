package com.weslide.lovesmallscreen.utils;

import android.view.View;

/**
 * Created by xu on 2016/5/19.
 * View相关的工具类
 */
public class ViewUtils {

    public static int measureWidth(int pWidthMeasureSpec) {
        int result = 0;

        int widthMode = View.MeasureSpec.getMode(pWidthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(pWidthMeasureSpec);

        switch (widthMode) {
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
    }


    public static int measureHeight(int pHeightMeasureSpec) {
        int result = 0;

        int heightMode = View.MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(pHeightMeasureSpec);

        switch (heightMode) {
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.EXACTLY:
                result = heightSize;
                break;
        }

        return result;
    }

}
