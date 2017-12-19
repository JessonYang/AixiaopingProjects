package com.weslide.lovesmallscreen.utils;

import android.content.Context;
import android.view.View;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.view_yy.customview.CustomDialog;


/**
 * Created by YY on 2017/12/12.
 */
public class DialogUtil {

    private static CustomDialog customDialog;

    public static CustomDialog showCustomDialog(Context context, int styleId, int viewId, int width, int height, String msg, int msgId, View.OnClickListener listener, int ... clickIds){
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .styleId(styleId)
                .view(viewId)
                .widthDp(width)
                .heightDp(height)
                .msg(msgId, msg);
        for (int i = 0; i < clickIds.length; i++) {
            builder.addViewClick(clickIds[i],listener);
        }
        CustomDialog dialog = builder.build();
        dialog.show();
        return dialog;
    }

    public static CustomDialog showCustomDialog(Context context, int styleId, int viewId, int width, int height, View.OnClickListener listener, int ... clickIds){
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .styleId(styleId)
                .view(viewId)
                .widthDp(width)
                .heightDp(height);
        for (int i = 0; i < clickIds.length; i++) {
            builder.addViewClick(clickIds[i],listener);
        }
        CustomDialog dialog = builder.build();
        dialog.show();
        return dialog;
    }

    /**
     * 提示dialog
     *
     * @param desc 提示信息
     */
    public static void showNoticDialog(Context context,String desc) {
        customDialog = showCustomDialog(context, R.style.customDialogStyle, R.layout.dialog_notice_item, 260, 130, desc, R.id.dialog_notice_msg, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_sure:
                        customDialog.dismiss();
                        break;
                }
            }
        }, R.id.btn_sure);
    }

}
