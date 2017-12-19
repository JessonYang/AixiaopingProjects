package com.weslide.lovesmallscreen.view_yy.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.weslide.lovesmallscreen.utils.DensityUtils;


/**
 * Created by YY on 2017/12/11.
 */
public class CustomDialog extends Dialog {

    private Context mContext;
    private int mWidth,mHeight;
    private boolean mCancelTouchOut;
    private View mView;

    private CustomDialog(Builder builder) {
        super(builder.mContext);
        mContext = builder.mContext;
        mWidth = builder.mWidth;
        mHeight = builder.mHeight;
        mCancelTouchOut = builder.mCancelTouchOut;
        mView = builder.mView;
    }

    private CustomDialog(Builder builder, int themeResId) {
        super(builder.mContext, themeResId);
        mContext = builder.mContext;
        mWidth = builder.mWidth;
        mHeight = builder.mHeight;
        mCancelTouchOut = builder.mCancelTouchOut;
        mView = builder.mView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        setCanceledOnTouchOutside(mCancelTouchOut);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.height = mHeight;
        attributes.width = mWidth;
        attributes.gravity = Gravity.CENTER;
        window.setAttributes(attributes);
    }

    public String getText(int viewId){
        View view = mView.findViewById(viewId);
        if (view instanceof TextView) {
            return ((TextView) view).getText().toString();
        } else if (view instanceof EditText) {
            return ((EditText) view).getText().toString();
        }
        return null;
    }

    public static final class Builder{
        private Context mContext;
        private int mWidth,mHeight;
        private boolean mCancelTouchOut;
        private View mView;
        private int resStyleId = -1;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder view(int layoutId){
            mView =  LayoutInflater.from(mContext).inflate(layoutId,null);
            return this;
        }

        public Builder heightPx(int height){
            mHeight = height;
            return this;
        }

        public Builder widthPx(int width){
            mWidth = width;
            return this;
        }

        public Builder heightDp(int height){
            mHeight = DensityUtils.dp2px(mContext,height);
            return this;
        }

        public Builder widthDp(int width){
            mWidth = DensityUtils.dp2px(mContext,width);
            return this;
        }

        public Builder styleId(int styleId){
            resStyleId = styleId;
            return this;
        }

        public Builder cancelTouchOut(boolean cancelTouchOut) {
            mCancelTouchOut = cancelTouchOut;
            return this;
        }

        public Builder title(int viewId,String title){
            View viewById = mView.findViewById(viewId);
            if (viewById instanceof TextView) {
                ((TextView) viewById).setText(title);
            } else if (viewById instanceof EditText) {
                ((EditText) viewById).setText(title);
            }
            return this;
        }

        public Builder msg(int viewId,String msg){
            View viewById = mView.findViewById(viewId);
            if (viewById instanceof TextView) {
                ((TextView) viewById).setText(msg);
            } else if (viewById instanceof EditText) {
                ((EditText) viewById).setText(msg);
            }
            return this;
        }

        public Builder addViewClick(int viewId, View.OnClickListener listener){
            mView.findViewById(viewId).setOnClickListener(listener);
            return this;
        }

        public CustomDialog build(){
            if (resStyleId == -1) {
                return new CustomDialog(this);
            }else {
                return new CustomDialog(this,resStyleId);
            }
        }
    }

}
