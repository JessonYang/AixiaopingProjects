package com.weslide.lovesmallscreen.view_yy.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.view_yy.customview.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

/**
 * Created by YY on 2017/11/27.
 */
public class HomeBinnerHolder extends RecyclerView.ViewHolder {

    private Banner banner;
    private Context mContext;

    public HomeBinnerHolder(Context context,View itemView) {
        super(itemView);
        mContext = context;
        banner = (Banner) itemView.findViewById(R.id.home_fg_banner);
    }

    public void oprateView(List<ImageText> banner_imgs){
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(banner_imgs);
        banner.setDelayTime(2000);
        banner.startAutoPlay();
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                URIResolve.resolve(mContext, banner_imgs.get(position).getUri());
            }
        });
    }
}
