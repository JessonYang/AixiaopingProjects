package com.weslide.lovesmallscreen.view_yy.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.SpecialLocalProductActivity;
import com.weslide.lovesmallscreen.models.TopLocalProductModel;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.view_yy.adapter.CountyGoodRcLvAdapter;

/**
 * Created by YY on 2017/11/27.
 */
public class HomeTopLocalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView title_rll;
    private final RecyclerView county_product_rclv;
    private Context mContext;
    private CountyGoodRcLvAdapter rclvAdapter;

    public HomeTopLocalHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        title_rll = (ImageView) itemView.findViewById(R.id.title_rll);
        county_product_rclv = (RecyclerView) itemView.findViewById(R.id.county_product_rclv);
    }

    public void oprateView(TopLocalProductModel topLocalProductModel){
        Glide.with(mContext).load(topLocalProductModel.getTitleImg()).into(title_rll);
        rclvAdapter = new CountyGoodRcLvAdapter(topLocalProductModel.getTopLocalModel(), mContext);
        county_product_rclv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        county_product_rclv.setAdapter(rclvAdapter);
        title_rll.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_rll:
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class);
                break;
        }
    }
}
