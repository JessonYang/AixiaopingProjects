package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.views.adapters.viewholder.BannerViewHolder;
import com.weslide.lovesmallscreen.views.seller.SellerBaseInfoView;
import com.weslide.lovesmallscreen.views.seller.SellerScoreGoodsViwe;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/13.
 * 店铺详情适配器
 */

public class SellerAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    /**
     * 商家头部介绍图片、包括视频
     */
    public static final int TYPE_SELLER_IMAGES = 0;
    /**
     * 商家信息
     */
    public static final int TYPE_SELLER_INFO = 1;
    /**
     * 商家积分商品列表
     */
    public static final int TYPE_SELLER_SCORE_GOODS = 2;
    /**
     * 商家中部商品介绍横幅
     */
    public static final int TYPE_SELLER_BANNER = 3;
    /**
     * 商家商品列表
     */
    public static final int TYPE_SELLER_GOODS = 4;
    private String mDistance;


    public SellerAdapter(Context context, DataList<RecyclerViewModel> list) {
        super(context, list);
    }

    /**
     * 判断是否是商品列表项
     */
    public boolean isGoods(int position) {
        return getItemViewType(position) == TYPE_SELLER_GOODS;
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_SELLER_IMAGES:
                viewHolder = new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.seller_view_images, parent, false));
                break;
            case TYPE_SELLER_INFO:
                viewHolder = new SellerBaseInfoViewHodler(new SellerBaseInfoView(mContext));
                break;
            case TYPE_SELLER_SCORE_GOODS:
                viewHolder = new SellerScoreGoodsViewHodler(new SellerScoreGoodsViwe(mContext));
                break;
            case TYPE_SELLER_BANNER:
                viewHolder = new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.seller_view_banner, parent, false));
                break;
            case TYPE_SELLER_GOODS:
                viewHolder = new SellerGoodsViewHodler(LayoutInflater.from(mContext).inflate(R.layout.view_goods_grid, parent, false));
                break;
        }

        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_SELLER_IMAGES:
                ((BannerViewHolder) holder).banner.setImageTexts((List<ImageText>) mList.get(position).getData());
                break;
            case TYPE_SELLER_INFO:
                ((SellerBaseInfoView) ((SellerBaseInfoViewHodler) holder).itemView).show((Seller) mList.get(position).getData());
                mDistance = ContextParameter.getDistanceForCurrentLocationAddUnit((Seller) mList.get(position).getData());
                break;
            case TYPE_SELLER_SCORE_GOODS:
                ((SellerScoreGoodsViwe) holder.itemView).bindView((List<Goods>) mList.get(position).getData());
                break;
            case TYPE_SELLER_BANNER:
                ((BannerViewHolder) holder).banner.setImageTexts((List<ImageText>) mList.get(position).getData());
                break;
            case TYPE_SELLER_GOODS:
                Goods goods = (Goods) mList.get(position).getData();
                SellerGoodsViewHodler sellerGoodsViewHodler = (SellerGoodsViewHodler) holder;

                sellerGoodsViewHodler.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppUtils.toGoods(mContext, goods.getGoodsId());
                    }
                });
                Glide.with(mContext).load(goods.getCoverPic()).into(sellerGoodsViewHodler.ivGoodsImage);
                sellerGoodsViewHodler.tvGoodsCostPrice.setText("￥" + goods.getCostPrice());
                sellerGoodsViewHodler.tvGoodsName.setText(goods.getName());
                sellerGoodsViewHodler.tvGoodsPrice.setText("￥" + goods.getPrice());
                if (mDistance != null) {
                    sellerGoodsViewHodler.tvSalesVolume.setText("距离" + mDistance);
                }
//                sellerGoodsViewHodler.tvSalesVolume.setText("已售" + goods.getSalesVolume() + "份");
                sellerGoodsViewHodler.expressTactics.setText(goods.getExpressTactics());

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }
}

/**
 * 商家信息
 */
class SellerBaseInfoViewHodler extends RecyclerView.ViewHolder {

    public SellerBaseInfoViewHodler(View itemView) {
        super(itemView);
    }
}

/**
 * 商家积分商品列表
 */
class SellerScoreGoodsViewHodler extends RecyclerView.ViewHolder {

    public SellerScoreGoodsViewHodler(View itemView) {
        super(itemView);
    }
}

/**
 * 商家商品列表
 */
class SellerGoodsViewHodler extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_goods_image)
    SquareImageView ivGoodsImage;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_costPrice)
    TextView tvGoodsCostPrice;
    @BindView(R.id.tv_sales_volume)
    TextView tvSalesVolume;
    @BindView(R.id.expressTactics)
    TextView expressTactics;

    public SellerGoodsViewHodler(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        tvGoodsCostPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
    }
}