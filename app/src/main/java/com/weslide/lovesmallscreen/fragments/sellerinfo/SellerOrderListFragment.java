package com.weslide.lovesmallscreen.fragments.sellerinfo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.malinskiy.superrecyclerview.decoration.SpaceItemDecoration;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.sellerinfo.StayExchangeActivity;
import com.weslide.lovesmallscreen.activitys.sellerinfo.StayTakeOrderActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.LoadingSubscriber;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.models.OrderList;
import com.weslide.lovesmallscreen.models.bean.GetOrderListBean;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateOrderListMessage;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateSellerOrderListMessage;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.adapters.SellerOrderListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/14.
 * 商家订单列表切换选项卡的列表
 */
public class SellerOrderListFragment extends FrameLayout {

    String mStatus = Constants.ORDER_STATUS_WAIT_CONFIRM;  //默认为待确认
    GetOrderListBean mGetOrderListBean;

    SellerOrderListAdapter mAdapter;
    OrderList mOrderList = new OrderList();

    @BindView(R.id.list)
    SuperRecyclerView list;

    public SellerOrderListFragment(Context context, String status) {
        super(context);
        mStatus = status;
        initView();
    }

    public SellerOrderListFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SellerOrderListFragment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    private void initView() {

         LayoutInflater.from(getContext()).inflate(R.layout.fragment_order_list, this, true);

        ButterKnife.bind(this);

        mGetOrderListBean = new GetOrderListBean();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        //添加项的间隔
        list.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(getContext(), 8)));
        mAdapter = new SellerOrderListAdapter(getContext(), this, mOrderList, mStatus);
        list.setAdapter(mAdapter);

        //加载更多
        list.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                loadData();
            }
        });

        //下拉刷新
        list.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGetOrderListBean.setPageIndex(0);
                loadData();
            }
        });

        //重新加载
        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetOrderListBean.setPageIndex(0);
                loadData();
            }
        });
    }

    @Subscribe
    public void onEvent(UpdateSellerOrderListMessage message) {

        if (mStatus.equals(message.getStatus())) {
            mGetOrderListBean.setPageIndex(0);
            loadData();
        }
    }

    public void reloadDtaa(){
        mGetOrderListBean.setPageIndex(0);
        loadData();
    }

    public void loadData() {
        Request<GetOrderListBean> request = new Request();
        mGetOrderListBean.setPageIndex(mGetOrderListBean.getPageIndex() + 1);
        mGetOrderListBean.setStatus(mStatus);
        mGetOrderListBean.setSellerId(ContextParameter.getUserInfo().getSellerId());

        request.setData(mGetOrderListBean);
        RXUtils.request(getContext(), request, "getSellerOrderList", new RecyclerViewSubscriber<Response<OrderList>>(mAdapter, mOrderList) {

            @Override
            public void onSuccess(Response<OrderList> orderListResponse) {
                mAdapter.addDataListNotifyDataSetChanged(orderListResponse.getData());
            }
        });
    }


    public static void sellerConfirmOrder(Context context, Order order, String status) {


        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        com.rey.material.app.Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);
                Request request = new Request();
                Map<String, String> map = new HashMap<>();
                map.put("orderId", order.getOrderId());
                request.setData(map);

                RXUtils.request(context, request, "sellerConfirmOrder", new LoadingSubscriber<Response>(context) {
                    @Override
                    public void onNext(Response response) {
                        //发送列表数据更新消息
                        UpdateSellerOrderListMessage message = new UpdateSellerOrderListMessage();
                        message.setStatus(status);
                        EventBus.getDefault().post(message);
                    }
                });
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder) builder).message("您确认接受该订单吗？")
                .title("订单提示")
                .positiveAction("确定").negativeAction("再等等");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(((BaseActivity) context).getSupportFragmentManager(), null);


    }

    public static void toLogistics(Context context, Order order) {
        AppUtils.toExpress(context, order.getExpressName(), order.getExpressNumber());
    }

    public static void contactUser(Context context, Order order) {
        if (StringUtils.isEmpty(order.getUser().getPhone())) {
            T.showShort(context, "该用户还没有填写手机号码");
            return;
        }
        AppUtils.toCallPhone(context, order.getSeller().getSellerPhone());
    }

    public static void sendOutGoods(Context context, Order order) {
        Bundle bundle = new Bundle();
        bundle.putString(StayTakeOrderActivity.KEY_ORDER_ID, order.getOrderId());

        AppUtils.toActivity(context, StayTakeOrderActivity.class, bundle);

    }

    public static void exchange(Context context, Order order) {

        Bundle bundle = new Bundle();
        bundle.putString(StayExchangeActivity.KEY_ORDER_ID, order.getOrderId());

        AppUtils.toActivity(context, StayExchangeActivity.class, bundle);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

}
