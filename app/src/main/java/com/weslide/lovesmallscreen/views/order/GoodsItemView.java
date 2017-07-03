package com.weslide.lovesmallscreen.views.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.OrderItem;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/29.
 * 订单商品项
 */
public class GoodsItemView extends FrameLayout {
    @BindView(R.id.cb_select)
    CheckBox cbSelect;
    @BindView(R.id.iv_goods_image)
    ImageView ivGoodsImage;
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

    OrderItem mOrderItem;
    @BindView(R.id.iv_to_goods)
    ImageView ivToGoods;

    public GoodsItemView(Context context) {
        super(context);
        initView();
    }

    public GoodsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GoodsItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.order_view_order_goods_item, this, true);
        ButterKnife.bind(this);
    }

    public void bindView(OrderItem orderItem, boolean showToGoods) {
        mOrderItem = orderItem;

        Glide.with(getContext()).load(mOrderItem.getGoods().getCoverPic()).into(ivGoodsImage);

        tvGoodsName.setText(mOrderItem.getGoods().getName());

        Utils.strikethrough(tvCostPrice);  //加横线
        tvCostPrice.setText(mOrderItem.getGoods().getCostPriceString());

        tvValue.setText(mOrderItem.getGoods().getValue());
        tvSpec.setText(mOrderItem.getSpecString());
        tvStock.setText("x" + mOrderItem.getNumber());

        if (showToGoods) {
            ivToGoods.setVisibility(View.VISIBLE);
        } else {
            ivToGoods.setVisibility(View.INVISIBLE);
        }

    }

    public void bindView(OrderItem orderItem) {
        bindView(orderItem, false);
    }


    public void showCheckBox() {

        cbSelect.setVisibility(View.VISIBLE);
        cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mOrderItem.setSelect(b);
            }
        });
    }

    public void hideCheckBox() {
        cbSelect.setVisibility(View.GONE);
    }
}
