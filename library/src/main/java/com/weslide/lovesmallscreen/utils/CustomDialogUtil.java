package com.weslide.lovesmallscreen.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import com.weslide.lovesmallscreen.models.bean.TeamOrderModel;
import com.weslide.lovesmallscreen.views.dialogs.CustomDialog;

import net.aixiaoping.library.R;

import java.util.List;


/**
 * Created by YY on 2017/12/12.
 */
public class CustomDialogUtil {

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

    public static CustomDialog showCustomDialog(Context context, int styleId, int viewId, int width, int height,String title,int titleId, String msg, int msgId, View.OnClickListener listener, int ... clickIds){
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .styleId(styleId)
                .view(viewId)
                .widthDp(width)
                .heightDp(height)
                .msg(msgId, msg)
                .title(titleId,title);
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

    public static CustomDialog showCustomDialog(Context context, int styleId, int viewId, int width, int height, BaseAdapter adapter, int listId, List<TeamOrderModel> list, View.OnClickListener listener, int ... clickIds){
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .styleId(styleId)
                .view(viewId)
                .widthDp(width)
                .heightDp(height)
                .teamlistAdapter(adapter,listId,list);
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
        customDialog = showCustomDialog(context, R.style.customDialogStyle, R.layout.notice_dialog, 260, 162, desc, R.id.dialog_notice, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = view.getId();
                if (i == R.id.sure) {
                    customDialog.dismiss();
                }
            }
        }, R.id.sure);
    }

    /**
     * 提示dialog
     *
     * @param desc 提示信息并结束页面
     */
    public static void showNoticDialog(Context context, String desc, final Activity activity) {
        customDialog = showCustomDialog(context, R.style.customDialogStyle, R.layout.notice_dialog, 260, 162, desc, R.id.dialog_notice, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = view.getId();
                if (i == R.id.sure) {
                    customDialog.dismiss();
                    activity.finish();
                }
            }
        }, R.id.sure);
    }

}
