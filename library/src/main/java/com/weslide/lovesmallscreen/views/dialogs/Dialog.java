package com.weslide.lovesmallscreen.views.dialogs;

import android.content.Context;
import android.os.Bundle;

import net.aixiaoping.library.R;

/**
 * Created by xu on 2016/6/2.
 * 提示框
 */
public class Dialog extends com.gc.materialdesign.widgets.Dialog {
    public Dialog(Context context, String title, String message) {
        super(context, title, message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getButtonAccept().setText(getContext().getResources().getString(R.string.btn_accept));
        if( getButtonCancel() != null)getButtonCancel().setText(getContext().getResources().getString(R.string.btn_cancel));
        getMessageTextView().setTextColor(getContext().getResources().getColor(R.color.font_content_color));
        getTitleTextView().setTextColor(getContext().getResources().getColor(R.color.font_title_color));
    }
}
