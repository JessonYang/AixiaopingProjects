package com.weslide.lovesmallscreen.views.dialogs;

import android.content.Context;
import android.os.Bundle;

import net.aixiaoping.library.R;

/**
 * Created by xu on 2016/5/27.
 */
public class LoadingDialog extends android.app.Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialog);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        setContentView(R.layout.dialog_loading);
    }
}
