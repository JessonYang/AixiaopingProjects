package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.sellerinfo.ChargebacKauditActivity;
import com.weslide.lovesmallscreen.fragments.sellerinfo.ChargebacKauditFragment;
import com.weslide.lovesmallscreen.fragments.sellerinfo.SellerOrderListFragment;
import com.weslide.lovesmallscreen.models.BackOrder;
import com.weslide.lovesmallscreen.models.OrderItem;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.adapters.viewholder.BaseViewHolder;
import com.weslide.lovesmallscreen.views.order.GoodsItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/25.
 * 商家处理用户退单信息列表
 */
public class SellerHandlerBackOrderListAdapter extends SuperRecyclerViewAdapter<BackOrder, SellerHandlerBackOrderListViewHolder> {
    public SellerHandlerBackOrderListAdapter(Context context, DataList<BackOrder> dataList) {
        super(context, dataList);
    }

    @Override
    public SellerHandlerBackOrderListViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        return new SellerHandlerBackOrderListViewHolder(mContext, parent);
    }

    @Override
    public void onSuperBindViewHolder(SellerHandlerBackOrderListViewHolder holder, int position) {
        holder.bindView(mList.get(position));
    }
}

class SellerHandlerBackOrderListViewHolder extends BaseViewHolder<BackOrder> {

    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_line)
    View tvLine;
    @BindView(R.id.tv_order_order_number)
    TextView tvOrderOrderNumber;
    @BindView(R.id.layout_order_goods)
    LinearLayout layoutOrderGoods;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_reality_money)
    TextView tvRealityMoney;

    BackOrder mOrder;

    public SellerHandlerBackOrderListViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.seller_order_view_handler_back_order_list_item);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(BackOrder order) {
        mOrder = order;

        tvOrderDate.setText(order.getOrderDate());
        tvOrderOrderNumber.setText( order.getOrderNumber());
        tvOrderStatus.setText(order.getBackOrderStatus().getName());
        tvRealityMoney.setText(order.getRealityMoney());
        tvUserName.setText(order.getUser().getName());

        GoodsItemView itemView = new GoodsItemView(getContext());
        itemView.bindView(mOrder.getBackOrderItem());
        layoutOrderGoods.removeAllViews();
        layoutOrderGoods.addView(itemView);
    }

    @OnClick({R.id.btn_verify, R.id.btn_contact_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verify:
                Bundle bundle = new Bundle();
                bundle.putString(ChargebacKauditFragment.KEY_BACK_ORDER_ITEM_ID, mOrder.getBackOrderVerify().getBackOrderItemId());
                AppUtils.toActivity(getContext(), ChargebacKauditActivity.class, bundle);

                break;
            case R.id.btn_contact_user:
                if (StringUtils.isEmpty(mOrder.getUser().getPhone())) {
                    T.showShort(getContext(), "该用户还没有填写手机号码");
                    return;
                }
                AppUtils.toCallPhone(getContext(), mOrder.getSeller().getSellerPhone());
                break;
        }
    }
}
