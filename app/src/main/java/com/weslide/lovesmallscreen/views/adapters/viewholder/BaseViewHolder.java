package com.weslide.lovesmallscreen.views.adapters.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by xu on 2016/7/15.
 * 基础的ViewHolder
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private Context context;

    public BaseViewHolder(Context context, ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId, parent, false));
        this.setContext(context);
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract void bindView(T t);
}

