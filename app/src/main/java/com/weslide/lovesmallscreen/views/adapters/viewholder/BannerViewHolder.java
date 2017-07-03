package com.weslide.lovesmallscreen.views.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.views.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/14.
 *
 * 通用的BannerViewHolder
 */
public class BannerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.banner)
    public Banner banner;

    public BannerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
