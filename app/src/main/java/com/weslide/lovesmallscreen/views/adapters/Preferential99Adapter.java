package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.BecomeVipActivity;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.Preferential99;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.views.Banner;
import com.weslide.lovesmallscreen.views.adapters.viewholder.BaseViewHolder;
import com.weslide.lovesmallscreen.views.preferential99.Preferential99FreeGoodsView;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/19.
 * 99特惠适配器
 */
public class Preferential99Adapter extends SuperRecyclerViewAdapter<RecyclerViewModel, BaseViewHolder> {

    public static final int VIEW_TYPE_BANNER = 1;
    public static final int VIEW_TYPE_FREE_GOODS = 2;
    public static final int VIEW_TYPE_BECOME_VIP = 3;
    public static final int VIEW_TYPE_FILTER_TYPE = 4;
    public static final int VIEW_TYPE_GOODS_ITEM = 5;

    public Preferential99Adapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
    }

    @Override
    public BaseViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {

        BaseViewHolder viewHolder = null;

        switch (viewType) {
            case VIEW_TYPE_BANNER:
                viewHolder = new Preferential99Banner(mContext, parent);
                break;
            case VIEW_TYPE_FREE_GOODS:
                viewHolder = new Preferential99FreeGoods(mContext, parent);
                break;
            case VIEW_TYPE_BECOME_VIP:
                viewHolder = new Preferential99BecomeView(mContext, parent);
                break;
            case VIEW_TYPE_FILTER_TYPE:
                viewHolder = new Preferential99FilterType(mContext, parent);
                break;
            case VIEW_TYPE_GOODS_ITEM:
                viewHolder = new Preferential99GoodsItem(mContext, parent);
                break;
        }

        return viewHolder;
    }

    /**
     * 判断是否是商品列表项
     */
    public boolean isGoods(int position) {
        return getItemViewType(position) == VIEW_TYPE_GOODS_ITEM;
    }

    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }

    @Override
    public void onSuperBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position));
    }

    /**
     * 头部banner
     */
    class Preferential99Banner extends BaseViewHolder<RecyclerViewModel> {

        @BindView(R.id.banner)
        Banner banner;

        public Preferential99Banner(Context context, ViewGroup parent) {
            super(context, parent, R.layout.preferential99_banner);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(RecyclerViewModel recyclerViewModel) {
            banner.setImageTexts(((Preferential99) recyclerViewModel.getData()).getHeadBanners());
        }
    }

    /**
     * 免单区商品
     */
    class Preferential99FreeGoods extends BaseViewHolder<RecyclerViewModel> {

        @BindView(R.id.layout_free_goods)
        Preferential99FreeGoodsView layoutFreeGoods;

        public Preferential99FreeGoods(Context context, ViewGroup parent) {
            super(context, parent, R.layout.preferential99_free_goods);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(RecyclerViewModel recyclerViewModel) {
            layoutFreeGoods.bindView(((Preferential99) recyclerViewModel.getData()).getFreeGoods());
        }
    }

    /**
     * 申请会员跳转
     */
    class Preferential99BecomeView extends BaseViewHolder<RecyclerViewModel> {

        @BindView(R.id.banner)
        Banner banner;

        public Preferential99BecomeView(Context context, ViewGroup parent) {
            super(context, parent, R.layout.preferential99_become_vip);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(RecyclerViewModel recyclerViewModel) {
            Preferential99 preferential99 = ((Preferential99) recyclerViewModel.getData());
            banner.setImageTexts(preferential99.getBecomeVipBanners());
            banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
                @Override
                public void onClick(ImageText data, int position) {
                    AppUtils.toActivity(getContext(), BecomeVipActivity.class);
                }
            });
        }
    }

    /**
     * 99特惠商品类型过滤
     */
    class Preferential99FilterType extends BaseViewHolder<RecyclerViewModel> {

        public Preferential99FilterType(Context context, ViewGroup parent) {
            super(context, parent, R.layout.preferential99_filter_type);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(RecyclerViewModel recyclerViewModel) {

        }

        @OnClick({R.id.tv_all_classifi, R.id.tv_sales_volume, R.id.tv_value})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_all_classifi:
                    break;
                case R.id.tv_sales_volume:
                    break;
                case R.id.tv_value:
                    break;
            }
        }
    }

    /**
     * 商品过滤
     */
    class Preferential99GoodsItem extends BaseViewHolder<RecyclerViewModel> {

        @BindView(R.id.iv_goods_image)
        SquareImageView ivGoodsImage;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_express_tactics)
        TextView tvExpressTactics;

        Goods mGoods;

        public Preferential99GoodsItem(Context context, ViewGroup parent) {
            super(context, parent, R.layout.preferential99_goods_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(RecyclerViewModel recyclerViewModel) {
            Goods goods = (Goods) recyclerViewModel.getData();
            mGoods = goods;

            Glide.with(getContext()).load(goods.getCoverPic()).into(ivGoodsImage);
            tvGoodsName.setText(goods.getName());
            tvGoodsPrice.setText(goods.getValue());
            tvExpressTactics.setText(goods.getExpressTactics());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.toGoods(getContext(), goods.getGoodsId());
                }
            });

        }


    }
}


