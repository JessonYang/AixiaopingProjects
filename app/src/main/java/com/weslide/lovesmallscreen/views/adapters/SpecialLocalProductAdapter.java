package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.CityItems;
import com.weslide.lovesmallscreen.models.CityType;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.SpecialLocalProduct;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.views.Banner;
import com.weslide.lovesmallscreen.views.adapters.viewholder.BaseViewHolder;
import com.weslide.lovesmallscreen.views.dialogs.SecondaryCityDialog;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/19.
 * 特产速递适配器
 */
public class SpecialLocalProductAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, BaseViewHolder> {

    public static final int VIEW_TYPE_BANNER = 1;
    public static final int VIEW_TYPE_CITY = 2;
    public static final int VIEW_TYPE_FILTER_TYPE = 4;
    public static final int VIEW_TYPE_GOODS_ITEM = 5;
    int h,s;
    private long lastClickTime = 0;

    public SpecialLocalProductAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
    }

    @Override
    public BaseViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_BANNER:
                viewHolder = new SpecialLocalProductBannerViewHolder(mContext, parent);
                break;
            case VIEW_TYPE_CITY:
                viewHolder = new CitySelectViewHolder(mContext, parent);
                break;
            case VIEW_TYPE_FILTER_TYPE:
                viewHolder = new SpecialLocalProductFilterType(mContext, parent);
                break;
            case VIEW_TYPE_GOODS_ITEM:
                viewHolder = new SpecialLocalProductGoodsItem(mContext, parent);
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
//        return mList.get(position).getItemType();
    }


    @Override
    public void onSuperBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position));
    }

    /**
     * banner
     */
    class SpecialLocalProductBannerViewHolder extends BaseViewHolder<RecyclerViewModel> {

        @BindView(R.id.banner)
        Banner banner;

        public SpecialLocalProductBannerViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.preferential99_banner);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(RecyclerViewModel recyclerViewModel) {
            banner.setImageTexts(((SpecialLocalProduct) recyclerViewModel.getData()).getHeadBanners());
            h = banner.getLayoutParams().height;
        }
    }

    /**
     * 选择分类
     */
    class CitySelectViewHolder extends BaseViewHolder<RecyclerViewModel> {

        @BindView(R.id.tv_all_classifi)
        TextView tvAllClassifi;
        @BindView(R.id.layout_all_classifi)
        FrameLayout layoutAllClassifi;
        @BindView(R.id.tv_sales_volume)
        TextView tvSalesVolume;
        @BindView(R.id.layout_sales_volume)
        FrameLayout layoutSalesVolume;
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.layout_value)
        FrameLayout layoutValue;
        @BindView(R.id.layout_classification)
        LinearLayout layoutClassification;
        List<CityType> types;
        public CitySelectViewHolder(Context context, ViewGroup parent) {
            super(context, parent, R.layout.view_dressing_by_screening);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(RecyclerViewModel recyclerViewModel) {
            types = (List<CityType>) recyclerViewModel.getData();
            s = layoutClassification.getLayoutParams().height;
        }

        @OnClick({R.id.layout_all_classifi, R.id.layout_sales_volume, R.id.layout_value})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.layout_all_classifi:
                    SecondaryCityDialog dialog = new SecondaryCityDialog(mContext, types, 240);
                    dialog.setOnClassificationSelectListener(new ClassificationSelectListener());
                    dialog.show();
                    break;
                case R.id.layout_sales_volume:
                    /*long currentTime = Calendar.getInstance().getTimeInMillis();
                    if (currentTime - lastClickTime > SpecialLocalProductFragment.MIN_CLICK_DELAY_TIME) {
                        EventBus.getDefault().post(new UpdateMallMessage());
                        lastClickTime = currentTime;
                        tvValue.setText("价格");

                        if (SpecialLocalProductFragment.mGoodsListReqeust.getSalesVolume().equals("0") || SpecialLocalProductFragment.mGoodsListReqeust.getSalesVolume().equals("1")) {
                            SpecialLocalProductFragment.mGoodsListReqeust.setSalesVolume("2");
                            tvSalesVolume.setText("从低到高");
                        } else if (SpecialLocalProductFragment.mGoodsListReqeust.getSalesVolume().equals("2")) {
                            SpecialLocalProductFragment.mGoodsListReqeust.setSalesVolume("1");
                            tvSalesVolume.setText("从高到低");
                        }
                    }*/
                    break;
                case R.id.layout_value:
                    break;
            }
        }
    }

    class ClassificationSelectListener implements SecondaryCityDialog.OnClassificationSelectListener {
        @Override
        public void select(CityItems type) {
            EventBus.getDefault().post(type);
        }
    }

    /**
     * 商品类型过滤
     */
    class SpecialLocalProductFilterType extends BaseViewHolder<RecyclerViewModel> {

        public SpecialLocalProductFilterType(Context context, ViewGroup parent) {
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
    class SpecialLocalProductGoodsItem extends BaseViewHolder<RecyclerViewModel> {

        @BindView(R.id.iv_goods_image)
        SquareImageView ivGoodsImage;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_express_tactics)
        TextView tvExpressTactics;

        Goods mGoods;

        public SpecialLocalProductGoodsItem(Context context, ViewGroup parent) {
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
   public int getHigh(){
       return h+s;
   }

}
