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
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/8.
 * 首页积分兑换数据适配器
 */
public class HomeScoreExchangeGoodsAdapter extends SuperRecyclerViewAdapter<Goods, HomeScoreExchangeGoodsViewHolder> {



    public HomeScoreExchangeGoodsAdapter(Context context, List<Goods> goodsList) {
        super(context, goodsList);
    }

    public HomeScoreExchangeGoodsAdapter(Context context, GoodsList goodsList) {
        super(context, goodsList);
    }

    @Override
    public HomeScoreExchangeGoodsViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeScoreExchangeGoodsViewHolder(LayoutInflater.from(mContext)
                .inflate(net.aixiaoping.library.R.layout.home_view_score_exchange_item, parent, false));
    }

    @Override
    public void onSuperBindViewHolder(HomeScoreExchangeGoodsViewHolder holder, int position) {
        Goods goods = mList.get(position);
        holder.mGoodsCostPrice.setText("原价：￥" + goods.getCostPrice());
        holder.mGoodsPrice.setText("积分：" + goods.getScore());
        Glide.with(mContext).load(goods.getCoverPic()).into(holder.mGoodsImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toGoods(mContext, goods.getGoodsId());
            }
        });
    }
}

class HomeScoreExchangeGoodsViewHolder extends RecyclerView.ViewHolder {

    @BindView(net.aixiaoping.library.R.id.iv_goods_image)
    ImageView mGoodsImage;
    @BindView(net.aixiaoping.library.R.id.tv_goods_score)
    TextView mGoodsPrice;
    @BindView(net.aixiaoping.library.R.id.tv_goods_costPrice)
    TextView mGoodsCostPrice;

    public HomeScoreExchangeGoodsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        Utils.strikethrough(mGoodsCostPrice);
    }
}
