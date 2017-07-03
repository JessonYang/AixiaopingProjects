package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.fragments.mall.ShoppingCartFragment;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.ShoppingCar;
import com.weslide.lovesmallscreen.models.ShoppingCarItem;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.utils.ViewUtils;
import com.weslide.lovesmallscreen.views.widget.AddAndSubtractView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/27.
 * 购物车数据适配器
 */
public class ShoppingCarAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    ShoppingCartFragment mShoppingCartFragment;

    /**
     * 购物车项标题
     */
    public static final int TYPE_SHOPPING_CAR_TITLE = 1;
    /**
     * 购物车项
     */
    public static final int TYPE_SHOPPING_CAR_ITEM = 2;


    public ShoppingCarAdapter(Context context, ShoppingCartFragment shoppingCartFragment, DataList<RecyclerViewModel> dataLis) {
        super(context, dataLis);
        mShoppingCartFragment = shoppingCartFragment;
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_SHOPPING_CAR_ITEM:
                viewHolder = new ShoppingCarItemViewHolder(mContext, mShoppingCartFragment, parent);
                break;
            case TYPE_SHOPPING_CAR_TITLE:
                viewHolder = new ShoppingCarTitleViewHolder(mContext, parent);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_SHOPPING_CAR_ITEM:
                ((ShoppingCarItemViewHolder) holder).bindView((ShoppingCarItem) mList.get(position).getData());
                break;
            case TYPE_SHOPPING_CAR_TITLE:
                ((ShoppingCarTitleViewHolder) holder).bindView((ShoppingCar) mList.get(position).getData());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }
}

/**
 * 购物车商品项
 */
class ShoppingCarItemViewHolder extends RecyclerView.ViewHolder {

    Context mContext;
    ShoppingCarItem mShoppingCarItem;
    ShoppingCartFragment mShoppingCartFragment;

    //http://stackoverflow.com/questions/27070220/recycleview-notifydatasetchanged-illegalstateexception
    @BindView(R.id.cb_select)
    CheckBox cbSelect;
    private boolean onBind;

    @BindView(R.id.iv_goods_image)
    ImageView ivGoodsImage;
    @BindView(R.id.asv_number)
    AddAndSubtractView asvNumber;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_stock)
    TextView tvStock;
    @BindView(R.id.tv_spec)
    TextView tvSpec;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_cost_price)
    TextView tvCostPrice;

    public ShoppingCarItemViewHolder(Context context, ShoppingCartFragment shoppingCartFragment, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.shopping_car_item_view, parent, false));
        mShoppingCartFragment = shoppingCartFragment;
        ButterKnife.bind(this, itemView);

        mContext = context;
    }

    boolean bindValue = false;

    public void bindView(ShoppingCarItem shoppingCarItem) {
        mShoppingCarItem = shoppingCarItem;

        Glide.with(mContext).load(mShoppingCarItem.getGoods().getCoverPic()).into(ivGoodsImage);

        //计算购物车项总价
        mShoppingCarItem.setSumPrice(Float.parseFloat(mShoppingCarItem.getGoods().getPrice()) * mShoppingCarItem.getNumber());

        bindValue = true;
        asvNumber.setMaxValue(mShoppingCarItem.getStock());
        asvNumber.setMinValue(1);
        bindValue = false;

        asvNumber.setValue(mShoppingCarItem.getNumber());
        asvNumber.setOnValueChangeListener(mOnValueChangeListener);

        tvGoodsName.setText(mShoppingCarItem.getGoods().getName());

        Utils.strikethrough(tvCostPrice);  //加横线
        tvCostPrice.setText(mShoppingCarItem.getGoods().getCostPriceString());

        tvValue.setText(mShoppingCarItem.getGoods().getValue());
        tvSpec.setText(mShoppingCarItem.getSpecString());
        tvStock.setText(mShoppingCarItem.getNumber() + "");
        onBind = true;
        cbSelect.setChecked(mShoppingCarItem.isSelected());
        onBind = false;
    }

    @OnClick(R.id.layout_shopping_car_item)
    public void onClick() {
    }

    @OnCheckedChanged({R.id.cb_select})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        /**
         * click checkbox -> onCheckedChanged() -> notifyDataSetChanged() -> onBindViewHolder() -> set checkbox -> onChecked...
         */
        if(!onBind){
            mShoppingCarItem.setSelected(isChecked);
            mShoppingCartFragment.handlerChecked(false);
        }


    }

    /**
     * 库存改变
     */
    private AddAndSubtractView.OnValueChangeListener mOnValueChangeListener = new AddAndSubtractView.OnValueChangeListener() {
        @Override
        public void change(int change) {
            if(!bindValue)
                mShoppingCartFragment.update(mShoppingCarItem, change);
        }
    };

}

/**
 * 购物车项标题
 */
class ShoppingCarTitleViewHolder extends RecyclerView.ViewHolder {

    Context mContext;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;

    ShoppingCar mShoppingCar;

    public ShoppingCarTitleViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.shopping_car_item_title_view, parent, false));
        ButterKnife.bind(this, itemView);

        mContext = context;
    }

    public void bindView(ShoppingCar shoppingCar) {
        mShoppingCar = shoppingCar;
        tvSellerName.setText(mShoppingCar.getSeller().getSellerName());
    }

    @OnClick(R.id.layout_seller_title)
    public void onClick() {

        AppUtils.toSeller(mContext, mShoppingCar.getSeller().getSellerId());
    }
}