package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by YY on 2017/11/21.
 */
public abstract class BaseRclvViewHolder extends RecyclerView.ViewHolder{

    public BaseRclvViewHolder(Context contextView , View itemView) {
        super(itemView);
    }

    public abstract <T extends View>T getView(int viewId);

    public abstract View getConvertView();

    public abstract void operateView(Object data);

    public abstract void bindViewClick(int ...viewIds);

}
