package com.weslide.lovesmallscreen.views.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.OrderDetail;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/29.
 * 订单明细视图
 */
public class OrderDetailView extends FrameLayout {

    @BindView(R.id.tv_detail_name)
    TextView tvDetailName;
    @BindView(R.id.tv_detail_option)
    TextView tvDetailOption;
    OrderDetail mDetail;

    public OrderDetailView(Context context) {
        super(context);
        initView();
    }

    public OrderDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OrderDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.order_view_order_detail, this, true);
        ButterKnife.bind(this);
    }

    public void bindView(OrderDetail detail){
        mDetail = detail;
        tvDetailName.setText(mDetail.getName());
        tvDetailOption.setText(mDetail.getPrice());
    }
}
