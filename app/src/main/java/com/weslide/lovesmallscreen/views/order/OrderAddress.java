package com.weslide.lovesmallscreen.views.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/18.
 * 地址布局
 */
public class OrderAddress extends FrameLayout {
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_can_address)
    ImageView ivCanAddress;

    public OrderAddress(Context context) {
        super(context);
        initView();
    }

    public OrderAddress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OrderAddress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.order_view_address, this, true);
        ButterKnife.bind(this);
    }

    public void bindView(Order order, boolean isCanSelect) {

        if (isCanSelect) {
            ivCanAddress.setVisibility(View.VISIBLE);
        } else {
            ivCanAddress.setVisibility(View.INVISIBLE);
        }

        if (StringUtils.isEmpty(order.getUsername()) || StringUtils.isEmpty(order.getPhone()) || StringUtils.isEmpty(order.getAddress())) {
            tvUsername.setVisibility(View.INVISIBLE);
            tvPhone.setVisibility(View.INVISIBLE);
            tvAddress.setVisibility(View.INVISIBLE);
        } else {
            tvUsername.setVisibility(View.VISIBLE);
            tvPhone.setVisibility(View.VISIBLE);
            tvAddress.setVisibility(View.VISIBLE);

            tvUsername.setText(order.getUsername());
            tvPhone.setText(order.getPhone());
            tvAddress.setText(order.getAddress());
        }


    }
}
