package com.weslide.lovesmallscreen.view_yy.adapter.viewholder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.ScoreExchangeActivity;
import com.weslide.lovesmallscreen.activitys.SecondKillActivity;
import com.weslide.lovesmallscreen.activitys.TaoKeActivity;
import com.weslide.lovesmallscreen.activitys.mall.SellerListActivity_new;
import com.weslide.lovesmallscreen.models.TopClassifyModel;
import com.weslide.lovesmallscreen.utils.AppUtils;

/**
 * Created by YY on 2017/11/27.
 */
public class HomeNearStoreHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView shop_around_iv,time_limite_iv,credit_exchange_iv,sign_up_attend_iv;
    private Context mContext;
    private TopClassifyModel topClassify;

    public HomeNearStoreHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        shop_around_iv = ((ImageView) itemView.findViewById(R.id.shop_around_iv));
        time_limite_iv = ((ImageView) itemView.findViewById(R.id.time_limite_iv));
        credit_exchange_iv = ((ImageView) itemView.findViewById(R.id.credit_exchange_iv));
        sign_up_attend_iv = ((ImageView) itemView.findViewById(R.id.sign_up_attend_iv));
    }

    public void oprateView(TopClassifyModel topClassifyModel){
        topClassify = topClassifyModel;
        Glide.with(mContext).load(topClassify.getZbdp().getImage()).into(shop_around_iv);
        Glide.with(mContext).load(topClassify.getXsms().getImage()).into(time_limite_iv);
        Glide.with(mContext).load(topClassify.getJfdh().getImage()).into(credit_exchange_iv);
        Glide.with(mContext).load(topClassify.getChbm().getImage()).into(sign_up_attend_iv);
        shop_around_iv.setOnClickListener(this);
        time_limite_iv.setOnClickListener(this);
        credit_exchange_iv.setOnClickListener(this);
        sign_up_attend_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shop_around_iv:
                AppUtils.toActivity(mContext, SellerListActivity_new.class);
                break;
            case R.id.time_limite_iv:
                AppUtils.toActivity(mContext, SecondKillActivity.class);
                break;
            case R.id.credit_exchange_iv:
                AppUtils.toActivity(mContext, ScoreExchangeActivity.class);
                break;
            case R.id.sign_up_attend_iv:
                String ChbmUri = topClassify.getChbm().getUri();
                Bundle ChbmBundle = new Bundle();
                ChbmBundle.putString(TaoKeActivity.KEY_LOAD_URL, ChbmUri);
                AppUtils.toActivity(mContext, TaoKeActivity.class, ChbmBundle);
                break;
        }
    }
}
