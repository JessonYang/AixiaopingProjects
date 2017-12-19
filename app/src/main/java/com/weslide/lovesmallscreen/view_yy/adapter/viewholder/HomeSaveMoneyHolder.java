package com.weslide.lovesmallscreen.view_yy.adapter.viewholder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.SqgwModel;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.view_yy.activity.SaveMoneyBrandActivity;
import com.weslide.lovesmallscreen.view_yy.activity.SaveMoneyHomeActivity;

/**
 * Created by YY on 2017/11/27.
 */
public class HomeSaveMoneyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView save_money_title_rll, brand_zone_iv, nine_to_nine_baoyou_iv, sqgw_second_classify_iv, sqgw_second_classify_iv2, yhq_banner_iv, clothing_iv, muying_iv, home_dress_iv, delicious_good_iv, cosmetic_iv, sports_iv, literary_form_iv, medical_health_iv;
    private final TextView clothing_tv, muying_tv, home_dress_tv, delicious_good_tv, cosmetic_tv, sports_tv, literary_form_tv, medical_health_tv;
    private final LinearLayout clothing_ll, muying_ll, home_dress_ll, delicious_good_ll, hzp_ll, sports_ll, wtcp_ll, medical_health_ll;
    private Context mContext;
    private SqgwModel saveMoney;

    public HomeSaveMoneyHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        save_money_title_rll = ((ImageView) itemView.findViewById(R.id.save_money_title_rll));
        brand_zone_iv = ((ImageView) itemView.findViewById(R.id.brand_zone_iv));
        nine_to_nine_baoyou_iv = ((ImageView) itemView.findViewById(R.id.nine_to_nine_baoyou_iv));
        sqgw_second_classify_iv = ((ImageView) itemView.findViewById(R.id.sqgw_second_classify_iv));
        sqgw_second_classify_iv2 = ((ImageView) itemView.findViewById(R.id.sqgw_second_classify_iv2));
        yhq_banner_iv = ((ImageView) itemView.findViewById(R.id.yhq_banner_iv));
        clothing_iv = ((ImageView) itemView.findViewById(R.id.clothing_iv));
        muying_iv = ((ImageView) itemView.findViewById(R.id.muying_iv));
        home_dress_iv = ((ImageView) itemView.findViewById(R.id.home_dress_iv));
        delicious_good_iv = ((ImageView) itemView.findViewById(R.id.delicious_good_iv));
        cosmetic_iv = ((ImageView) itemView.findViewById(R.id.cosmetic_iv));
        sports_iv = ((ImageView) itemView.findViewById(R.id.sports_iv));
        literary_form_iv = ((ImageView) itemView.findViewById(R.id.literary_form_iv));
        medical_health_iv = ((ImageView) itemView.findViewById(R.id.medical_health_iv));
        clothing_tv = ((TextView) itemView.findViewById(R.id.clothing_tv));
        muying_tv = ((TextView) itemView.findViewById(R.id.muying_tv));
        home_dress_tv = ((TextView) itemView.findViewById(R.id.home_dress_tv));
        delicious_good_tv = ((TextView) itemView.findViewById(R.id.delicious_good_tv));
        cosmetic_tv = ((TextView) itemView.findViewById(R.id.cosmetic_tv));
        sports_tv = ((TextView) itemView.findViewById(R.id.sports_tv));
        literary_form_tv = ((TextView) itemView.findViewById(R.id.literary_form_tv));
        medical_health_tv = ((TextView) itemView.findViewById(R.id.medical_health_tv));
        clothing_ll = ((LinearLayout) itemView.findViewById(R.id.clothing_ll));
        muying_ll = ((LinearLayout) itemView.findViewById(R.id.muying_ll));
        home_dress_ll = ((LinearLayout) itemView.findViewById(R.id.home_dress_ll));
        delicious_good_ll = ((LinearLayout) itemView.findViewById(R.id.delicious_good_ll));
        hzp_ll = ((LinearLayout) itemView.findViewById(R.id.hzp_ll));
        sports_ll = ((LinearLayout) itemView.findViewById(R.id.sports_ll));
        wtcp_ll = ((LinearLayout) itemView.findViewById(R.id.wtcp_ll));
        medical_health_ll = ((LinearLayout) itemView.findViewById(R.id.medical_health_ll));
    }

    public void oprateView(SqgwModel sqgwModel) {
        saveMoney = sqgwModel;
        Glide.with(mContext).load(saveMoney.getSqgwTp().getImage()).placeholder(R.drawable.sqgw_title).into(save_money_title_rll);
        Glide.with(mContext).load(saveMoney.getPpth().getImage()).override(100, 100).into(brand_zone_iv);
        Glide.with(mContext).load(saveMoney.getNine2nine().getImage()).override(100, 100).into(nine_to_nine_baoyou_iv);
        Glide.with(mContext).load(saveMoney.getSqgwCategories().get(0).getImage()).into(sqgw_second_classify_iv);
        Glide.with(mContext).load(saveMoney.getSqgwCategories().get(1).getImage()).into(sqgw_second_classify_iv2);
        Glide.with(mContext).load(saveMoney.getYhj().getImage()).into(yhq_banner_iv);
        Glide.with(mContext).load(saveMoney.getSqgwCategories().get(2).getImage()).into(clothing_iv);
        clothing_tv.setText(saveMoney.getSqgwCategories().get(2).getTitle());
        Glide.with(mContext).load(saveMoney.getSqgwCategories().get(3).getImage()).into(muying_iv);
        muying_tv.setText(saveMoney.getSqgwCategories().get(3).getTitle());
        Glide.with(mContext).load(saveMoney.getSqgwCategories().get(4).getImage()).into(home_dress_iv);
        home_dress_tv.setText(saveMoney.getSqgwCategories().get(4).getTitle());
        Glide.with(mContext).load(saveMoney.getSqgwCategories().get(5).getImage()).into(delicious_good_iv);
        delicious_good_tv.setText(saveMoney.getSqgwCategories().get(5).getTitle());
        Glide.with(mContext).load(saveMoney.getSqgwCategories().get(6).getImage()).into(cosmetic_iv);
        cosmetic_tv.setText(saveMoney.getSqgwCategories().get(6).getTitle());
        Glide.with(mContext).load(saveMoney.getSqgwCategories().get(7).getImage()).into(sports_iv);
        sports_tv.setText(saveMoney.getSqgwCategories().get(7).getTitle());
        Glide.with(mContext).load(saveMoney.getSqgwCategories().get(8).getImage()).into(literary_form_iv);
        literary_form_tv.setText(saveMoney.getSqgwCategories().get(8).getTitle());
        Glide.with(mContext).load(saveMoney.getSqgwCategories().get(9).getImage()).into(medical_health_iv);
        medical_health_tv.setText(saveMoney.getSqgwCategories().get(9).getTitle());
        save_money_title_rll.setOnClickListener(this);
        brand_zone_iv.setOnClickListener(this);
        nine_to_nine_baoyou_iv.setOnClickListener(this);
        sqgw_second_classify_iv.setOnClickListener(this);
        sqgw_second_classify_iv2.setOnClickListener(this);
        clothing_ll.setOnClickListener(this);
        muying_ll.setOnClickListener(this);
        home_dress_ll.setOnClickListener(this);
        delicious_good_ll.setOnClickListener(this);
        hzp_ll.setOnClickListener(this);
        sports_ll.setOnClickListener(this);
        wtcp_ll.setOnClickListener(this);
        medical_health_ll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_money_title_rll:
                Bundle saveMoneyBundle = new Bundle();
                saveMoneyBundle.putString("toolbarType", "省钱购物");
                saveMoneyBundle.putString("searchValue", "");
                saveMoneyBundle.putString("cid", "-1");
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, saveMoneyBundle);
                break;
            case R.id.brand_zone_iv:
                AppUtils.toActivity(mContext, SaveMoneyBrandActivity.class);
                break;
            case R.id.nine_to_nine_baoyou_iv:
                Bundle nineToNineBundle = new Bundle();
                nineToNineBundle.putString("toolbarType", "九块九");
                nineToNineBundle.putString("searchValue", "");
                nineToNineBundle.putString("cid", "-1");
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, nineToNineBundle);
                break;
            case R.id.sqgw_second_classify_iv:
                Bundle bundle1 = new Bundle();
                bundle1.putString("toolbarType", "省钱购物");
                bundle1.putString("searchValue", "");
                bundle1.putString("cid", saveMoney.getSqgwCategories().get(0).getCid());
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, bundle1);
                break;
            case R.id.sqgw_second_classify_iv2:
                Bundle bundle2 = new Bundle();
                bundle2.putString("toolbarType", "省钱购物");
                bundle2.putString("searchValue", "");
                bundle2.putString("cid", saveMoney.getSqgwCategories().get(1).getCid());
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, bundle2);
                break;
            case R.id.clothing_ll:
                Bundle bundle3 = new Bundle();
                bundle3.putString("toolbarType", "省钱购物");
                bundle3.putString("searchValue", "");
                bundle3.putString("cid", saveMoney.getSqgwCategories().get(2).getCid());
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, bundle3);
                break;
            case R.id.muying_ll:
                Bundle bundle4 = new Bundle();
                bundle4.putString("toolbarType", "省钱购物");
                bundle4.putString("searchValue", "");
                bundle4.putString("cid", saveMoney.getSqgwCategories().get(3).getCid());
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, bundle4);
                break;
            case R.id.home_dress_ll:
                Bundle bundle5 = new Bundle();
                bundle5.putString("toolbarType", "省钱购物");
                bundle5.putString("searchValue", "");
                bundle5.putString("cid", saveMoney.getSqgwCategories().get(4).getCid());
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, bundle5);
                break;
            case R.id.delicious_good_ll:
                Bundle bundle6 = new Bundle();
                bundle6.putString("toolbarType", "省钱购物");
                bundle6.putString("searchValue", "");
                bundle6.putString("cid", saveMoney.getSqgwCategories().get(5).getCid());
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, bundle6);
                break;
            case R.id.hzp_ll:
                Bundle bundle7 = new Bundle();
                bundle7.putString("toolbarType", "省钱购物");
                bundle7.putString("searchValue", "");
                bundle7.putString("cid", saveMoney.getSqgwCategories().get(6).getCid());
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, bundle7);
                break;
            case R.id.sports_ll:
                Bundle bundle8 = new Bundle();
                bundle8.putString("toolbarType", "省钱购物");
                bundle8.putString("searchValue", "");
                bundle8.putString("cid", saveMoney.getSqgwCategories().get(7).getCid());
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, bundle8);
                break;
            case R.id.wtcp_ll:
                Bundle bundle9 = new Bundle();
                bundle9.putString("toolbarType", "省钱购物");
                bundle9.putString("searchValue", "");
                bundle9.putString("cid", saveMoney.getSqgwCategories().get(8).getCid());
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, bundle9);
                break;
            case R.id.medical_health_ll:
                Bundle bundle10 = new Bundle();
                bundle10.putString("toolbarType", "省钱购物");
                bundle10.putString("searchValue", "");
                bundle10.putString("cid", saveMoney.getSqgwCategories().get(9).getCid());
                AppUtils.toActivity(mContext, SaveMoneyHomeActivity.class, bundle10);
                break;
        }
    }
}
