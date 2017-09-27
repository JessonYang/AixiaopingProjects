package com.weslide.lovesmallscreen.views.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.models.OrderDetail;
import com.weslide.lovesmallscreen.models.OrderItem;
import com.weslide.lovesmallscreen.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/29.
 * 订单视图
 */
public class OrderView extends FrameLayout {

    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.tv_order_status)
    TextView tvExpressTactics;
    @BindView(R.id.layout_order_goods)
    LinearLayout layoutOrderGoods;
    @BindView(R.id.tv_order_total_money)
    TextView tvOrderTotalMoney;
    @BindView(R.id.layout_order_details)
    LinearLayout layoutOrderDetails;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_reality_money)
    TextView tvRealityMoney;

    Order mOrder;
    Context mContext;

    public OrderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public OrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public OrderView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.order_view_order, this, true);
        ButterKnife.bind(this);

    }

    /**
     * 绑定数据
     * @param order
     */
    public void bindView(Order order, boolean canSelect){
        mOrder = order;

        tvSellerName.setText(mOrder.getSeller().getSellerName());
     //   L.e("======红包======"+mOrder.getExpressTactics());
        tvOrderTotalMoney.setText(mOrder.getMoney());
        tvOrderDate.setText(mOrder.getOrderDate());
        tvRealityMoney.setText(mOrder.getRealityMoney());

        if(mOrder.getOrderItems() != null) {
            //是否包邮
            tvExpressTactics.setText(mOrder.getOrderItems().get(0).getGoods().getExpressTactics());
            for (OrderItem orderItem : mOrder.getOrderItems()) {
                GoodsItemView itemView = new GoodsItemView(getContext());
                itemView.bindView(orderItem);
                if(canSelect && orderItem.isBack() || mOrder.getStatus().getStatusId().equals(Constants.ORDER_STATUS_WAIT_COMMENT)){
                    //显示多选框
                    itemView.showCheckBox();
                }
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String goodsId = orderItem.getGoods().getGoodsId();
                        if (goodsId != null && goodsId.length() > 0) {
                            AppUtils.toGoods(mContext, goodsId);
                        }
                    }
                });
                layoutOrderGoods.addView(itemView);
            }
        }

        if(mOrder.getDetails() != null){
            for (OrderDetail detail : mOrder.getDetails()) {
                if(detail.getName().equals("运费") || detail.getName().equals("优惠券抵扣")) {
                    OrderDetailView detailView = new OrderDetailView(getContext());
                    detailView.bindView(detail);
                    layoutOrderDetails.addView(detailView);
                }
            }
        }

    }

    /**
     * 绑定数据
     * @param order
     */
    public void bindView(Order order){
        bindView(order, false);
    }

    @OnClick({})
    public void onClick(View v){
        switch (v.getId()) {

        }
    }
}
