package com.weslide.lovesmallscreen.view_yy.customview;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * ViewPager左右折叠动画
 * Created by YY on 2018/1/4.
 */
public class FoldTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        if (position < -1){
            ViewHelper.setPivotX(page,page.getMeasuredWidth() * 0.5f);
            ViewHelper.setPivotY(page,page.getMeasuredHeight() * 0.5f);
            ViewHelper.setScaleX(page,1);
        }else if (position <= 0){
            ViewHelper.setPivotX(page,page.getMeasuredWidth());
            ViewHelper.setPivotY(page,0);
            ViewHelper.setScaleX(page,1+position);
        }else if(position <= 1){
            ViewHelper.setPivotX(page,0);
            ViewHelper.setPivotY(page,0);
            ViewHelper.setScaleX(page,1 - position);
        }else {
            ViewHelper.setPivotX(page,page.getMeasuredWidth() * 0.5f);
            ViewHelper.setPivotY(page,page.getMeasuredHeight() * 0.5f);
            ViewHelper.setScaleX(page,1);
        }
    }
}
