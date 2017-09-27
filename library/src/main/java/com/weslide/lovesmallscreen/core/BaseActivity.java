package com.weslide.lovesmallscreen.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.utils.ShareUtils;

import net.aixiaoping.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2016/4/28.
 * 一个基础的Activity
 */
public class BaseActivity extends AppCompatActivity {

    List<APeriod> periodList = new ArrayList<>();

//    GestureDetector mGestureDetector;
//    public static final long CLICK_INTERVAL = 1200;

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.base_activity_slide_left_out, R.anim.base_activity_slide_right_out);
    }

    public void superFinish(){
        super.finish();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.base_activity_slide_right_in, R.anim.base_activity_slide_left_in);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全局禁用双击事件，预防用户重复点击
//        mGestureDetector = new GestureDetector(this ,onGestureListener); //使用派生自
//        mGestureDetector.setOnDoubleTapListener(onDoubleTapListener);


        //当前Activity压栈
        getSupportApplication().pushActivity(this);

        for (int i = 0; i < periodList.size(); i++) {
            periodList.get(i).onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (int i = 0; i < periodList.size(); i++) {
            periodList.get(i).onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < periodList.size(); i++) {
            periodList.get(i).onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0; i < periodList.size(); i++) {
            periodList.get(i).onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (int i = 0; i < periodList.size(); i++) {
            periodList.get(i).onStop();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        for (int i = 0; i < periodList.size(); i++) {
            periodList.get(i).onRestart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //当前Activity出栈
        getSupportApplication().removeActivity(this);

        for (int i = 0; i < periodList.size(); i++) {
            periodList.get(i).onDestroy();
        }

        ShareUtils.destory();
    }

    public ArchitectureAppliation getSupportApplication(){
        return (ArchitectureAppliation) getApplication();
    }

    /**
     * 将一个APeriod绑定到Activity的生命周期
     * @param period
     */
    public void addPeriod(APeriod period){
        periodList.add(period);
    }

    public void removePeriod(APeriod period){
        for (int i = 0; i < periodList.size(); i++) {
            if(periodList.get(i) == period){
                periodList.remove(i);
                return;
            }
        }
    }





//    GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener(){
//
//        @Override
//        public boolean onDown(MotionEvent motionEvent) {
//
//            return true;
//        }
//
//        @Override
//        public void onShowPress(MotionEvent motionEvent) {
//
//
//        }
//
//        @Override
//        public boolean onSingleTapUp(MotionEvent motionEvent) {
//
//            return true;
//        }
//
//        @Override
//        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//
//            return true;
//        }
//
//        @Override
//        public void onLongPress(MotionEvent motionEvent) {
//
//        }
//
//        @Override
//        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//
//            return true;
//        }
//    };
//
//    long lastClickTime;
//
//    GestureDetector.OnDoubleTapListener onDoubleTapListener = new GestureDetector.OnDoubleTapListener(){
//
//        @Override
//        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
//            long currentClickTime = System.currentTimeMillis();
//            long interval = currentClickTime - lastClickTime;
//
//            if(interval >= CLICK_INTERVAL){
//                lastClickTime = currentClickTime;
//                return true;
//            }
//            return false;
//        }
//
//        @Override
//        public boolean onDoubleTap(MotionEvent motionEvent) {
//            return false;
//        }
//
//        @Override
//        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
//            return false;
//        }
//    };


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if( mGestureDetector.onTouchEvent(ev) ){
//            return super.dispatchTouchEvent(ev);
//        } else {
//            return false;
//        }

        return super.dispatchTouchEvent(ev);
    }

}
