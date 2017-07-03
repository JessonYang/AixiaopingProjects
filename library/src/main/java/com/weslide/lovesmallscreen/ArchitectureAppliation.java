package com.weslide.lovesmallscreen;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.view.inputmethod.InputMethodManager;

import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.tencent.bugly.crashreport.CrashReport;

import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.DaoMaster;
import com.weslide.lovesmallscreen.core.DaoSession;
import com.weslide.lovesmallscreen.managers.LocationManager;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.T;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by xu on 2016/4/26.
 * 程序架构的Application
 */
public class ArchitectureAppliation extends MultiDexApplication {


    //版本更新
    /** 是否已经提示了版本更新 */
    public boolean alertVersionUpdate = false;
    /** 提示更新的时间 */
    public long alertVersionUpdateTime = 0;
    /** 提示用户更新的最大时间间隔 */
    public static final long MAX_ALERT_VERSION_UPDATE_TIME = 1000 * 60 * 60 * 12;


    /**
     * 用于存储所有启动的Activity栈
     * 控制Activity的生存周期
     * 适当的时间点将对应的Activity关闭
     */
    private Stack<Activity> activityStack = new Stack<>();

    private static ArchitectureAppliation mAppliation;
    private static DaoSession daoSession;

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static void setDaoSession(DaoSession daoSession) {
        ArchitectureAppliation.daoSession = daoSession;
    }

    @Override
    public void onCreate() {
        mAppliation = this;

        super.onCreate();
    }

    protected void init() {

        initDataBase();
        //初始化Bugly
        CrashReport.initCrashReport(this, Constants.BUGLY_APP_ID, false);

        //初始化定位
        LocationManager.initialise(this);

        //百度地图初始化
        SDKInitializer.initialize(this);
    }


    @Override
    public void onTerminate() {
        Glide.get(this).clearMemory();
        // 程序终止的时候执行
        L.e("程序终止的时候执行");
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        L.e("低内存的时候执行");
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    /**
     * 初始化数据库
     */
    private void initDataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "aixiaoping-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        setDaoSession(daoMaster.newSession());
    }

    public static ArchitectureAppliation getAppliation() {
        return mAppliation;
    }


    /**
     * 分割 Dex 支持
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 指定Activity出栈，并关闭
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
        L.e(activity.getClass().getName() + "出栈");
        if (!activity.isFinishing()) {
            activity.finish();
        }
    }

    /**
     * 多个Activity出栈
     *
     * @param activitys
     */
    public void removeActivitys(Class... activitys) {
        List<Activity> removeActivitys = new ArrayList<>();
        for (int i = 0; i < activityStack.size(); i++) {
            for (int j = 0; j < activitys.length; j++) {
                if (activityStack.get(i).getClass().getName().equals(activitys[j].getName())) {
                    removeActivitys.add(activityStack.get(i));
                }
            }
        }

        for (int i = 0; i < removeActivitys.size(); i++) {
            removeActivity(removeActivitys.get(i));
        }
    }

    /**
     * 移除所有activity
     */
    public void removeAllActivitys() {
        for (int i = 0; i < activityStack.size(); i++) {
            activityStack.get(i).finish();
        }
        activityStack.clear();
    }

    /**
     * 留下唯一的Activity，预防内存泄露利器
     *
     * @param cla
     */
    public void onlyActivity(Class<? extends BaseActivity> cla) {
        List<Activity> removeActivitys = new ArrayList<>();
        for (int i = 0; i < activityStack.size(); i++) {
            if (!activityStack.get(i).getClass().getName().equals(cla.getName())) {
                removeActivitys.add(activityStack.get(i));
            }
        }

        for (int i = 0; i < removeActivitys.size(); i++) {
            removeActivity(removeActivitys.get(i));
        }
    }

    /**
     * 压栈
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        activityStack.push(activity);
        L.e(activity.getClass().getName() + "入栈");
    }


    /**
     * 用于存储所有启动的Activity，
     */
    public Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public void setActivityStack(Stack<Activity> activityStack) {
        this.activityStack = activityStack;
    }

    /**
     * InputMethodManager内存泄露现象及解决
     * http://www.tuicool.com/articles/ZRZzUfY
     * @param context
     */
    public static void fixInputMethodManagerLeak(Context context) {
        if (context == null) {
            return;
        }
        try {
            // 对 mCurRootView mServedView mNextServedView 进行置空...
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null) {
                return;
            }// author:sodino mail:sodino@qq.com

            Object obj_get = null;
            Field f_mCurRootView = imm.getClass().getDeclaredField("mCurRootView");
            Field f_mServedView = imm.getClass().getDeclaredField("mServedView");
            Field f_mNextServedView = imm.getClass().getDeclaredField("mNextServedView");

            if (f_mCurRootView.isAccessible() == false) {
                f_mCurRootView.setAccessible(true);
            }
            obj_get = f_mCurRootView.get(imm);
            if (obj_get != null) { // 不为null则置为空
                f_mCurRootView.set(imm, null);
            }

            if (f_mServedView.isAccessible() == false) {
                f_mServedView.setAccessible(true);
            }
            obj_get = f_mServedView.get(imm);
            if (obj_get != null) { // 不为null则置为空
                f_mServedView.set(imm, null);
            }

            if (f_mNextServedView.isAccessible() == false) {
                f_mNextServedView.setAccessible(true);
            }
            obj_get = f_mNextServedView.get(imm);
            if (obj_get != null) { // 不为null则置为空
                f_mNextServedView.set(imm, null);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}
