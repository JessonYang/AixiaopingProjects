package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/13.
 * 积分商品适配器
 */
public class SellerScoreGoodsAdapter extends SuperRecyclerViewAdapter<Goods, SellerScoreGoodsViewHolder> {

    public SellerScoreGoodsAdapter(Context context, List<Goods> list) {
        super(context, list);
    }

    @Override
    public SellerScoreGoodsViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        return new SellerScoreGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_score_exchange_item, parent, false));
    }

    @Override
    public void onSuperBindViewHolder(SellerScoreGoodsViewHolder holder, int position) {
        Goods goods = mList.get(position);
        holder.tvGoodsScore.setText("积分：" + goods.getScore());
        holder.tvGoodsCostPrice.setText("原价：" + goods.getCostPrice());
        Glide.with(mContext).load(goods.getCoverPic()).into(holder.ivGoodsImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toGoods(mContext, goods.getGoodsId());
            }
        });
    }
}

class SellerScoreGoodsViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.iv_goods_image)
    ImageView ivGoodsImage;
    @BindView(R.id.tv_goods_score)
    TextView tvGoodsScore;
    @BindView(R.id.tv_goods_costPrice)
    TextView tvGoodsCostPrice;


    public SellerScoreGoodsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        tvGoodsCostPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
    }
}

