package com.weslide.lovesmallscreen.core;

import android.content.Context;

import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

/**
 * Created by xu on 2016/7/15.
 * 显示正在加载
 */
public abstract class LoadingSubscriber<T> extends SupportSubscriber<T> {

    LoadingDialog dialog;
    Context context;


    public LoadingSubscriber(Context context){
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = new LoadingDialog(context);
        dialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        dialog.dismiss();
    }

}
