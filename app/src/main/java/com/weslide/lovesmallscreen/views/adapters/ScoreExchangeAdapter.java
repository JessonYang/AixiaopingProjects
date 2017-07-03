package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.flyco.tablayout.SlidingTabLayout;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.GoodsClassifiActivity;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.Banner;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/6/25.
 */
public class ScoreExchangeAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    /**
     * 商品分类
     */
    public static final int TYPE_GOODS_CLASSIFYS = 1;


    /**
     * 精选选项标签
     */
    public static final int TYPE_CHIOCE_NESS = 2;

    /**
     * 轮播图
     */
    public static final int TYPE_BANNER = 3;

    /**
     * 精选推荐
     */
    public static final int TYPE_COMMENDATION = 4;
    /**
     * 积分兑换商品
     */

    public static final int TYPE_SCORE_GOODS = 5;

    public ScoreExchangeAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
    }

    /**
     * 判断是否是商品列表项
     */
    public boolean isGoods(int position) {
        return getItemViewType(position) == TYPE_SCORE_GOODS;
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_GOODS_CLASSIFYS:

                viewHolder = new HomeGoodsClassifyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_goods_classify, parent, false));

                break;
            case TYPE_CHIOCE_NESS:

                viewHolder = new ScoreExchangeChoiseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.score_view_exchange, parent, false));

                break;
            case TYPE_BANNER:

                viewHolder = new ScoreExchangeBannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_head_banner, parent, false));

                break;
            case TYPE_COMMENDATION:

                viewHolder = new ScoreExchangeRecommendViewHolder(LayoutInflater.from(mContext).inflate(R.layout.score_view_recommend, parent, false));

                break;
            case TYPE_SCORE_GOODS:

                viewHolder = new ScoreExchangeGoodListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_goods_grid, parent, false));

                break;
        }

        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_GOODS_CLASSIFYS:
                List<ImageText> imageTexts = (List<ImageText>) mList.get(position).getData();
                ((HomeGoodsClassifyViewHolder) holder).bindView(imageTexts);
                break;
            case TYPE_CHIOCE_NESS:


                break;
            case TYPE_BANNER:

                ((HomeHeadBannerViewHolder) holder).banner.setImageTexts((List<ImageText>) mList.get(position).getData());

                break;
            case TYPE_COMMENDATION:


                break;
            case TYPE_SCORE_GOODS:
                Goods goods = (Goods) mList.get(position).getData();
                SellerGoodsViewHodler sellerGoodsViewHodler = (SellerGoodsViewHodler) holder;

                sellerGoodsViewHodler.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.showShort(mContext, "显示的商品ID:" + goods.getGoodsId());
                    }
                });
                Glide.with(mContext).load(goods.getImages().get(0).getImage()).into(sellerGoodsViewHodler.ivGoodsImage);
                sellerGoodsViewHodler.tvGoodsCostPrice.setText("￥" + goods.getCostPrice());
                sellerGoodsViewHodler.tvGoodsName.setText(goods.getName());
                sellerGoodsViewHodler.tvGoodsPrice.setText("￥" + goods.getPrice());
                sellerGoodsViewHodler.tvSalesVolume.setText(goods.getSalesVolume());
                break;

        }


    }

    @Override
    public int getItemViewType(int position) {

        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;

    }


    /**
     * 积分兑换轮播图BannerViewHolder
     */
    class ScoreExchangeBannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner_home_banner)
        Banner banner;

        public ScoreExchangeBannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 积分兑换商品选项卡
     */
    class ScoreExchangeChoiseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.stl_score_exchange_tab)
        SlidingTabLayout slidingTabLayout;

        public ScoreExchangeChoiseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 积分兑换推荐
     */
    class ScoreExchangeRecommendViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gv_score_exchane_recommend)
        GridView gvRecommend;

        public ScoreExchangeRecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 积分兑换商品列表
     */
    class ScoreExchangeGoodListViewHolder extends RecyclerView.ViewHolder {
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

        public ScoreExchangeGoodListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvGoodsCostPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        }
    }
}
