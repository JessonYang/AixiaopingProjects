package com.weslide.lovesmallscreen.view_yy.adapter.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.SqgwModel;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.view_yy.activity.SaveMoneyBrandActivity;
import com.weslide.lovesmallscreen.view_yy.activity.SaveMoneyHomeActivity;
import com.weslide.lovesmallscreen.view_yy.adapter.HomePprmAdapter;
import com.weslide.lovesmallscreen.view_yy.customview.RecyclerViewDivider;

/**
 * Created by YY on 2017/11/27.
 */
public class HomeSaveMoneyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView yhq_banner_iv, ppzq_iv, nine2nine_iv;
    private final RecyclerView hot_brand_rclv, hot_sold_rclv;
    private Context mContext;
    private SqgwModel saveMoney;

    public HomeSaveMoneyHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        yhq_banner_iv = ((ImageView) itemView.findViewById(R.id.yhq_banner_iv));
        ppzq_iv = ((ImageView) itemView.findViewById(R.id.ppzq_iv));
        nine2nine_iv = ((ImageView) itemView.findViewById(R.id.nine2nine_iv));
        hot_brand_rclv = ((RecyclerView) itemView.findViewById(R.id.hot_brand_rclv));
        hot_sold_rclv = ((RecyclerView) itemView.findViewById(R.id.hot_sold_rclv));
    }

    public void oprateView(SqgwModel sqgwModel) {
        saveMoney = sqgwModel;
        Glide.with(mContext).load(saveMoney.getYhj().getImage()).into(yhq_banner_iv);
        Glide.with(mContext).load(saveMoney.getNine2nine().getImage()).into(nine2nine_iv);
        Glide.with(mContext).load(saveMoney.getPpth().getImage()).into(ppzq_iv);
        nine2nine_iv.setOnClickListener(this);
        ppzq_iv.setOnClickListener(this);
        yhq_banner_iv.setOnClickListener(this);
        HomePprmAdapter pprmAdapter = new HomePprmAdapter(sqgwModel.getPprm(), mContext,0);
        hot_brand_rclv.setAdapter(pprmAdapter);
        hot_brand_rclv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        hot_brand_rclv.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#dddddd")));

        HomePprmAdapter bkrxAdapter = new HomePprmAdapter(sqgwModel.getBkrx(), mContext,1);
        hot_sold_rclv.setAdapter(bkrxAdapter);
        hot_sold_rclv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        hot_sold_rclv.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#dddddd")));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //省钱购物
            case R.id.yhq_banner_iv:
                Bundle saveMoneyBundle = new Bundle();
                saveMoneyBundle.putString("toolbarType", "省钱购物");
                saveMoneyBundle.putString("searchValue", "");
                saveMoneyBundle.putString("cid", "-1");
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, saveMoneyBundle);
                break;
            //品牌专区
            case R.id.ppzq_iv:
                AppUtils.toActivity(mContext, SaveMoneyBrandActivity.class);
                break;
            //九块九
            case R.id.nine2nine_iv:
                Bundle nineToNineBundle = new Bundle();
                nineToNineBundle.putString("toolbarType", "九块九");
                nineToNineBundle.putString("searchValue", "");
                nineToNineBundle.putString("cid", "-1");
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, nineToNineBundle);
                break;
            case R.id.clothing_ll:
                Bundle bundle3 = new Bundle();
                bundle3.putString("toolbarType", "省钱购物");
                bundle3.putString("searchValue", "");
                bundle3.putString("cid", saveMoney.getSqgwCategories().get(2).getCid());
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, bundle3);
                break;
        }
    }
}
