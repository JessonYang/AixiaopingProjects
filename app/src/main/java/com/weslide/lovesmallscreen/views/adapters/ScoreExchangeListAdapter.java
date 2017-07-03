package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.umeng.socialize.utils.Log;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.activitys.mall.BecomeVipActivity;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.views.Banner;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/19.
 */

public class ScoreExchangeListAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder>{

    Goods mGoods;
    ImageText headBanner;
    ImageText selection;
    Context mcontxt;
    public static final int HEAD_BANNER = 1;
    public static final int TYPE_SELECTION = 2;
    public static final int TYPE_GOODS_ITEM = 3;
    public ScoreExchangeListAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
        mcontxt = context;
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
       switch (viewType){
            case HEAD_BANNER:

                viewHolder = new HeadBannerViewHolder(mcontxt,parent);

                break;

           case TYPE_SELECTION:

               viewHolder = new SelectionViewHolder(mcontxt,parent);

              break;

           case TYPE_GOODS_ITEM:
             viewHolder = new ClassifiGoodsAdapterViewHolder(mcontxt,parent);
              break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }
    /**
     * 判断是否是商品列表项
     */
    public int isGoods(int position) {
        return getItemViewType(position);
    }
    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case HEAD_BANNER:     //头部轮播图
                ((HeadBannerViewHolder)holder).bindView((List<ImageText>) mList.get(position).getData());
                break;
            case TYPE_SELECTION://中部四个选项卡
                ((SelectionViewHolder)holder).bindView((ImageText) mList.get(position).getData());
                break;
            case TYPE_GOODS_ITEM://商品列表
                ((ClassifiGoodsAdapterViewHolder)holder).bindView((Goods) mList.get(position).getData());
                break;

        }
    }
    /**
     * 首页头部BannerViewHolder
     */
    class HeadBannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner banner;
        Context mContxt;
        public HeadBannerViewHolder(Context context,ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.free_goods_banner, parent, false));

            mContxt = context;
            ButterKnife.bind(this,itemView);
        }
        public void bindView(List<ImageText> imageText){
            banner.setImageTexts(imageText);
            /*banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
                @Override
                public void onClick(ImageText data, int position) {
                    URIResolve.resolve(mContxt,imageText.get);
                }
            });*/

        }
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
        Context context;

        public ClassifiGoodsAdapterViewHolder(Context context , ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.view_goods_grid, parent, false));
            ButterKnife.bind(this, itemView);
            this.context = context;
        }
        public void bindView(Goods goods) {
           Glide.with(context).load(goods.getCoverPic()).into(ivGoodsImage);
            tvGoodsName.setText(goods.getName());
            tvGoodsPrice.setText("积分 "+goods.getScore());
            tvSalesVolume.setText("已换"+goods.getSalesVolume()+"件");
            tvGoodsCostPrice.setText("价值：￥"+goods.getCostPrice());
            expressTactics.setText(goods.getExpressTactics());
            Utils.strikethrough(tvGoodsCostPrice);
            itemView.setOnClickListener(view -> AppUtils.toGoods(context, goods.getGoodsId()));

        }
    }
    class SelectionViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_goods_icon)
        ImageView icon;
        Context context;
        public SelectionViewHolder(Context context,ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.item_delection, parent, false));
            ButterKnife.bind(this, itemView);
            this.context = context;
        }
        public void bindView(ImageText imageTexts){

            Glide.with(context).load(imageTexts.getImage()).into(icon);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    URIResolve.resolve(context,imageTexts.getUri());
                }
            });
        }

    }
}
