package com.malinskiy.superrecyclerview.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.R;

/**
 * Created by xu on 2016/7/2.
 * 没有网络时显示
 */
public class NoNetworkViewHolder extends DifferentSituationViewHolder {

    public View view;
    public ImageView ivImage;
    public TextView tvText;
    private View.OnClickListener onClickListener;

    public NoNetworkViewHolder(Context context, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_no_network, parent, false);

        ivImage = (ImageView) view.findViewById(R.id.iv_network_image);
        tvText = (TextView) view.findViewById(R.id.tv_network_text);

    }


    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

        view.findViewById(R.id.btn_network_reload).setOnClickListener(getOnClickListener());
    }

    @Override
    public View getView() {
        return view;
    }
}
