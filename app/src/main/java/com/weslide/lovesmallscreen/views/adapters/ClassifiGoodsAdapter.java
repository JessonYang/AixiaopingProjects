package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/23.
 * 分类商品显示适配器
 */
public class ClassifiGoodsAdapter extends SuperRecyclerViewAdapter<Goods, ClassifiGoodsAdapter.ClassifiGoodsAdapterViewHolder> {
    public ClassifiGoodsAdapter(Context context, GoodsList goodsList) {
        super(context, goodsList);
    }

    @Override
    public ClassifiGoodsAdapterViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassifiGoodsAdapterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_goods_grid, parent, false));
    }

    @Override
    public void onSuperBindViewHolder(ClassifiGoodsAdapterViewHolder holder, int position) {
        Goods goods = mList.get(position);

        Glide.with(mContext).load(goods.getCoverPic()).into(holder.ivGoodsImage);
        holder.tvGoodsCostPrice.setText("￥" + goods.getCostPrice());
        holder.tvGoodsName.setText(goods.getName());
        holder.tvGoodsPrice.setText(goods.getValue());

        //分类界面用到的是距离， 销量只有商品详情界面用到
        holder.tvLocaiont.setVisibility(View.VISIBLE);
        holder.tvSalesVolume.setVisibility(View.GONE);

        holder.tvLocaiont.setText(ContextParameter.getDistanceForCurrentLocationAddUnit(goods.getSeller().getLat(), goods.getSeller().getLng()));
        holder.expressTactics.setText(goods.getExpressTactics());

        holder.itemView.setOnClickListener(v -> AppUtils.toGoods(mContext, goods.getGoodsId()));
    }


    class ClassifiGoodsAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_goods_image)
        SquareImageView ivGoodsImage;
        @BindView(R.id.expressTactics)
        TextView expressTactics;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_costPrice)
        TextView tvGoodsCostPrice;
        @BindView(R.id.tv_sales_volume)
        TextView tvSalesVolume;
        @BindView(R.id.tv_locaiont)
        TextView tvLocaiont;

        public ClassifiGoodsAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            Utils.strikethrough(tvGoodsCostPrice);
        }
    }
}
