package com.malinskiy.superrecyclerview.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.R;

/**
 * Created by xu on 2016/7/4.
 * 正在加载ViewHolder
 */
public class LoadingViewHolder extends DifferentSituationViewHolder {

    View view;
    ImageView ivLoading;

    public LoadingViewHolder(Context context, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_loading, parent, false);
        ivLoading = (ImageView) view.findViewById(R.id.iv_loading);

        Glide.with(context).load(R.drawable.gif_loading).into(ivLoading);
    }

    @Override
    public View getView() {
        return view;
    }
}
