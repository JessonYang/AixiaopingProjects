package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.fragments.mall.HomeSecondKillGoodsFragment;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/22.
 * 首页秒杀商品适配器
 */
public class HomeSecondKillGoodsAdapter extends SuperRecyclerViewAdapter<Goods, HomeSecondKillGoodsViewHolder> {

    HomeSecondKillGoodsFragment parentFragment;

    public HomeSecondKillGoodsAdapter(Context context, HomeSecondKillGoodsFragment parent, GoodsList list) {
        super(context, list);
        parentFragment = parent;
    }

    @Override
    public HomeSecondKillGoodsViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeSecondKillGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_second_kill_item, parent, false));
    }

    @Override
    public void onSuperBindViewHolder(HomeSecondKillGoodsViewHolder holder, int position) {
        Goods goods = mList.get(position);
        Glide.with(mContext).load(goods.getCoverPic()).into(holder.ivImage);
        holder.tvAddress.setText(ContextParameter.getDistanceForCurrentLocationAddUnit(goods.getSeller()));
        holder.tvCostPrice.setText("￥" + goods.getCostPrice());
        holder.tvName.setText(goods.getName());
        holder.tvValue.setText("￥" + goods.getPrice());
        holder.tvDiscount.setText(goods.getDiscount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.toGoods(mContext, goods.getGoodsId(), parentFragment.startSecondKill);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size() > 2 ? 2 : mList.size();
    }
}

/**
 * Created by xu on 2016/6/22.
 * 首页秒杀商品ViewHolder
 */
class HomeSecondKillGoodsViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.iv_image)
    SquareImageView ivImage;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_cost_price)
    TextView tvCostPrice;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    public HomeSecondKillGoodsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        Utils.strikethrough(tvCostPrice);
    }
}
