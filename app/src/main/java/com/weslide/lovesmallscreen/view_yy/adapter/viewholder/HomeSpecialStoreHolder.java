package com.weslide.lovesmallscreen.view_yy.adapter.viewholder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.SpecialLocalProductActivity;
import com.weslide.lovesmallscreen.models.FeatureTypeModel;
import com.weslide.lovesmallscreen.utils.AppUtils;

/**
 * Created by YY on 2017/11/27.
 */
public class HomeSpecialStoreHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView nfcp_iv, county_banner_iv, to_friend_iv, eat_must_iv, dj_fruit_iv, tea_iv;
    private Context mContext;
    private FeatureTypeModel featureType;
    private String typeId;
    private final ImageView title_rll;

    public HomeSpecialStoreHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        county_banner_iv = ((ImageView) itemView.findViewById(R.id.county_banner_iv));
        nfcp_iv = ((ImageView) itemView.findViewById(R.id.nfcp_iv));
        title_rll = (ImageView) itemView.findViewById(R.id.title_rll);
        to_friend_iv = ((ImageView) itemView.findViewById(R.id.to_friend_iv));
        eat_must_iv = ((ImageView) itemView.findViewById(R.id.eat_must_iv));
        dj_fruit_iv = ((ImageView) itemView.findViewById(R.id.dj_fruit_iv));
        tea_iv = ((ImageView) itemView.findViewById(R.id.tea_iv));
    }

    public void oprateView(FeatureTypeModel featureTypeModel) {
        featureType = featureTypeModel;
        Glide.with(mContext).load(featureType.getYxypMaxPicture().getImage()).into(county_banner_iv);
        Glide.with(mContext).load(featureType.getNfcp().getImage()).into(nfcp_iv);
        Glide.with(mContext).load(featureType.getGift().getImage()).into(to_friend_iv);
        Glide.with(mContext).load(featureType.getCate().getImage()).into(eat_must_iv);
        Glide.with(mContext).load(featureType.getFruit().getImage()).into(dj_fruit_iv);
        Glide.with(mContext).load(featureType.getTea().getImage()).into(tea_iv);
        Glide.with(mContext).load(featureType.getTitleImg()).into(title_rll);
        county_banner_iv.setOnClickListener(this);
        nfcp_iv.setOnClickListener(this);
        to_friend_iv.setOnClickListener(this);
        eat_must_iv.setOnClickListener(this);
        dj_fruit_iv.setOnClickListener(this);
        tea_iv.setOnClickListener(this);
        title_rll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.county_banner_iv:
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class);
                break;
            case R.id.nfcp_iv:
                typeId = featureType.getNfcp().getTypeId();
                Bundle countyBundle1 = new Bundle();
                countyBundle1.putString("typeId", typeId);
                countyBundle1.putInt("where", 0);
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class, countyBundle1);
                break;
            case R.id.to_friend_iv:
                typeId = featureType.getGift().getTypeId();
                Bundle countyBundle2 = new Bundle();
                countyBundle2.putString("typeId", typeId);
                countyBundle2.putInt("where", 0);
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class, countyBundle2);
                break;
            case R.id.eat_must_iv:
                typeId = featureType.getCate().getTypeId();
                Bundle countyBundle3 = new Bundle();
                countyBundle3.putString("typeId", typeId);
                countyBundle3.putInt("where", 0);
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class, countyBundle3);
                break;
            case R.id.dj_fruit_iv:
                typeId = featureType.getFruit().getTypeId();
                Bundle countyBundle4 = new Bundle();
                countyBundle4.putString("typeId", typeId);
                countyBundle4.putInt("where", 0);
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class, countyBundle4);
                break;
            case R.id.tea_iv:
                typeId = featureType.getTea().getTypeId();
                Bundle countyBundle5 = new Bundle();
                countyBundle5.putString("typeId", typeId);
                countyBundle5.putInt("where", 0);
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class, countyBundle5);
                break;
            case R.id.title_rll:
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class);
                break;
        }
    }
}
