package com.weslide.lovesmallscreen.view_yy.adapter.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.TopLocalProductModel;
import com.weslide.lovesmallscreen.view_yy.adapter.CountyGoodRcLvAdapter;
import com.weslide.lovesmallscreen.view_yy.customview.RecyclerViewDivider;

/**
 * Created by YY on 2017/11/27.
 */
public class HomeTopLocalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final RecyclerView county_product_rclv;
    private Context mContext;
    private CountyGoodRcLvAdapter rclvAdapter;

    public HomeTopLocalHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        county_product_rclv = (RecyclerView) itemView.findViewById(R.id.county_product_rclv);
    }

    public void oprateView(TopLocalProductModel topLocalProductModel){
        rclvAdapter = new CountyGoodRcLvAdapter(topLocalProductModel.getTopLocalModel(), mContext);
        county_product_rclv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        county_product_rclv.addItemDecoration(new RecyclerViewDivider(mContext,LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#dddddd")));
        county_product_rclv.setAdapter(rclvAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
