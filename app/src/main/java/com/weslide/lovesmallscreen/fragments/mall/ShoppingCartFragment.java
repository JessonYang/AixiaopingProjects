package com.weslide.lovesmallscreen.fragments.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.malinskiy.superrecyclerview.decoration.SpaceItemDecoration;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.SpecialLocalProductActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.ShoppingCar;
import com.weslide.lovesmallscreen.models.ShoppingCarItem;
import com.weslide.lovesmallscreen.models.ShoppingCarList;
import com.weslide.lovesmallscreen.models.bean.CreateTempOrderListBean;
import com.weslide.lovesmallscreen.models.bean.ShoppingCarBean;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateShoppingCarMessage;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.OrderUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.utils.UserUtils;
import com.weslide.lovesmallscreen.views.adapters.ShoppingCarAdapter;
import com.weslide.lovesmallscreen.views.dialogs.Dialog;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/3.
 * 购物车
 */
public class ShoppingCartFragment extends BaseFragment {

    public static final String TAG = "ShoppingCartFragment";

    View mView;
    DataList<RecyclerViewModel> mDataList = new DataList<>();
    List<RecyclerViewModel> mRecyclerViewModels;
    @BindView(R.id.layout_no_login)
    RelativeLayout layoutNoLogin;
    /**
     * 页面状态， 用于区分用户是否在编辑购物车
     */
    private int status = 0;

    /**
     * 处理联动
     * click checkbox -> onCheckedChanged() -> notifyDataSetChanged() -> onBindViewHolder() -> set checkbox -> onChecked...
     */
    private boolean handler;

    @BindView(R.id.tv_shopping_car_option)
    TextView tvShoppingCarOption;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.list)
    SuperRecyclerView list;
    ShoppingCarAdapter mAdapter;

    @BindView(R.id.cb_all_edit)
    CheckBox cbAllEdit;
    @BindView(R.id.layout_edit)
    RelativeLayout layoutEdit;
    @BindView(R.id.cb_all_buy)
    CheckBox cbAllBuy;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.layout_buy)
    RelativeLayout layoutBuy;
    @BindView(R.id.btn_to_sum)
    Button btnBuy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        mView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        ButterKnife.bind(this, mView);

        mRecyclerViewModels = mDataList.getDataList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);

        //添加项的间隔
        list.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(getActivity(), 12), ShoppingCarAdapter.TYPE_SHOPPING_CAR_TITLE));

        list.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //重新加载数据
//                loadData();

                //跳转各地特产
                AppUtils.toActivity(getActivity(), SpecialLocalProductActivity.class);

                //跳转省钱购物
                /*Bundle saveMoneyBundle = new Bundle();
                saveMoneyBundle.putString("toolbarType", "省钱购物");
                saveMoneyBundle.putString("searchValue", "");
                saveMoneyBundle.putString("cid", "-1");
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, saveMoneyBundle);*/
            }
        });

        mAdapter = new ShoppingCarAdapter(getActivity(), ShoppingCartFragment.this, mDataList);
        list.setSupeRecyclerView_parent_type(1);
        list.setAdapter(mAdapter);


        if (ContextParameter.isLogin()) {
            loadData();
        }
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!ContextParameter.isLogin()) {
            layoutNoLogin.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        } else {

            layoutNoLogin.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void onEvent(UpdateShoppingCarMessage message) {

        if (!ContextParameter.isLogin()) {
            return;
        }

        loadData();
    }


    /**
     * 加载购物车列表数据
     */
    public void loadData() {


        Request request = new Request();

        RXUtils.request(getActivity(), request, "getShoppingCarList", new RecyclerViewSubscriber<Response<ShoppingCarList>>(mAdapter, mDataList) {
            @Override
            public void onSuccess(Response<ShoppingCarList> shoppingCarListResponse) {
                mRecyclerViewModels.clear();
                mRecyclerViewModels.addAll(handller(shoppingCarListResponse.getData()));
                mAdapter.notifyDataSetChanged();

                //数据更新后需要重新计算价格之类的东西(默认不全选)
//                handlerChecked(false);

                //默认商品全选
                for (RecyclerViewModel recyclerViewModel : mRecyclerViewModels) {
                    if (recyclerViewModel.getData() instanceof ShoppingCarItem) {
                        ShoppingCarItem item = (ShoppingCarItem) recyclerViewModel.getData();
                        item.setSumPrice(Float.parseFloat(item.getGoods().getPrice()) * item.getNumber());
                    }
                }
                cbAllBuy.setChecked(true);
                handlerChecked(true);
            }
        });
    }

    /**
     * 显示编辑
     */
    private void showEdit() {
        layoutBuy.setVisibility(View.GONE);
        layoutEdit.setVisibility(View.VISIBLE);

    }

    /**
     * 显示购买
     */
    private void showBuy() {
        layoutEdit.setVisibility(View.GONE);
        layoutBuy.setVisibility(View.VISIBLE);
    }

    /**
     * 购买
     */
    private void buy() {

        if (mRecyclerViewModels == null) {
            return;
        }
        List<String> ids = new ArrayList<>();

        //计算当前选中的商品数量
        for (RecyclerViewModel recyclerViewModel : mRecyclerViewModels) {
            if (recyclerViewModel.getData() instanceof ShoppingCarItem) {
                ShoppingCarItem item = (ShoppingCarItem) recyclerViewModel.getData();
                if (item.isSelected()) {
                    ids.add(item.getShoppingCarItemId());
                }
            }
        }

        if (ids.size() == 0) {
            T.showShort(getActivity(), "还是先选一些商品吧~");
        } else {
            CreateTempOrderListBean bean = new CreateTempOrderListBean();
            bean.setShoppingCarItemIds(ids);
            bean.setType("1");
            OrderUtils.createTempOrderList(getActivity(), bean, Constants.TYPE_OF_NORMAL);
        }


    }

    /**
     * 分享
     */
    private void share() {

        if (mRecyclerViewModels == null) {
            return;
        }

        ShareContent home = ContextParameter.getClientConfig().getShoppingCarShareContent();
        ShareUtils.share(getActivity(), home.getTitle(),
                home.getIconUrl(),
                home.getTargetUrl() + "?appVersion=" + AppUtils.getVersionCode(getActivity()) + "&img=" + ContextParameter.getUserInfo().getHeadimage() + "&name=" + ContextParameter.getUserInfo().getName() + "&phone=" + ContextParameter.getUserInfo().getPhone() + "&code=" + ContextParameter.getUserInfo().getInviteCode(),
                home.getContent());

    }

    /**
     * 购买数量更新
     *
     * @param item
     * @param number
     */
    public void update(ShoppingCarItem item, int number) {
        if (mRecyclerViewModels == null) {
            return;
        }

        Request<ShoppingCarBean> request = new Request<>();
        ShoppingCarBean shoppingCarBean = new ShoppingCarBean();
        shoppingCarBean.setNumber(number);
        shoppingCarBean.setShoppingCarItemId(item.getShoppingCarItemId());

        request.setData(shoppingCarBean);
        RXUtils.request(getActivity(), request, "updateShoppingCar", new SupportSubscriber() {

            LoadingDialog loadingDialog = null;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(Object o) {
                Response response = (Response) o;
                L.i(TAG, "update-" + response.getMessage());

                item.setNumber(number);

                //计算购物车项总价
                item.setSumPrice(Float.parseFloat(item.getGoods().getPrice()) * item.getNumber());
                sum();
            }
        });


    }

    /**
     * 移除购物车项
     */
    public void remove() {

        if (mRecyclerViewModels == null) {
            return;
        }

        List<String> ids = new ArrayList<>();

        //计算当前选中的商品数量
        for (RecyclerViewModel recyclerViewModel : mRecyclerViewModels) {
            if (recyclerViewModel.getData() instanceof ShoppingCarItem) {
                ShoppingCarItem item = (ShoppingCarItem) recyclerViewModel.getData();
                if (item.isSelected()) {
                    ids.add(item.getShoppingCarItemId());
                }
            }
        }

        if (ids.size() == 0) {
            T.showShort(getActivity(), "还商品被选中哦~");
        } else {
            Dialog dialog = new Dialog(getActivity(), "购物车提示", "您确定移除购物车中的" + ids.size() + "项商品吗？");
            dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Request<ShoppingCarBean> request = new Request<>();
                    ShoppingCarBean shoppingCarBean = new ShoppingCarBean();
                    shoppingCarBean.setShoppingCarItemIds(ids);

                    request.setData(shoppingCarBean);

                    RXUtils.request(getActivity(), request, "removeShoppingCar", new SupportSubscriber() {
                        @Override
                        public void onNext(Object o) {
                            Response response = (Response) o;
                            L.i(TAG, "remove-" + response.getMessage());
                            T.showShort(getActivity(), response.getMessage());
                            Log.d("雨落无痕丶", "message: "+response.getMessage());
                            loadData();
                        }
                    });
                }
            });

            dialog.addCancelButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    L.i(TAG, "remove-用户取消操作。");
                }
            });

            dialog.show();

        }

    }

    /**
     * 处理选中项联动
     *
     * @param isAll 是否是选中全部
     */
    public void handlerChecked(boolean isAll) {
        if (mRecyclerViewModels == null || mRecyclerViewModels.size() == 0) {
            cbAllBuy.setChecked(false);
            cbAllEdit.setChecked(false);

            sum();
            btnBuy.setText("去结算（" + 0 + "）");
            return;
        }

        if (isAll) {
            boolean checked;
            //判断当前的操作状态
            if (status == 0) {
                //购买操作
                checked = cbAllBuy.isChecked();
            } else {
                //编辑操作
                checked = cbAllEdit.isChecked();
            }

            //判断是全选状态还是取消全选
            if (checked) {
                //全选
                for (RecyclerViewModel recyclerViewModel : mRecyclerViewModels) {
                    if (recyclerViewModel.getData() instanceof ShoppingCarItem) {
                        ShoppingCarItem item = (ShoppingCarItem) recyclerViewModel.getData();
                        item.setSelected(true);
                    }
                }
                cbAllBuy.setChecked(true);
                cbAllEdit.setChecked(true);
            } else {
                //取消全选
                for (RecyclerViewModel recyclerViewModel : mRecyclerViewModels) {
                    if (recyclerViewModel.getData() instanceof ShoppingCarItem) {
                        ShoppingCarItem item = (ShoppingCarItem) recyclerViewModel.getData();
                        item.setSelected(false);
                    }
                }

                cbAllBuy.setChecked(false);
                cbAllEdit.setChecked(false);
            }


        } else {
            //判断是否全部选中
            boolean all = true;
            for (RecyclerViewModel recyclerViewModel : mRecyclerViewModels) {
                if (recyclerViewModel.getData() instanceof ShoppingCarItem) {
                    ShoppingCarItem item = (ShoppingCarItem) recyclerViewModel.getData();
                    if (!item.isSelected()) {
                        all = false;
                        break;
                    }
                }
            }

            handler = true;
            if (all) {
                cbAllBuy.setChecked(true);
                cbAllEdit.setChecked(true);
            } else {
                cbAllBuy.setChecked(false);
                cbAllEdit.setChecked(false);
            }
            handler = false;
        }

        sum();

        //计算当前选中的商品数量
        int count = 0;
        for (RecyclerViewModel recyclerViewModel : mRecyclerViewModels) {
            if (recyclerViewModel.getData() instanceof ShoppingCarItem) {
                ShoppingCarItem item = (ShoppingCarItem) recyclerViewModel.getData();
                if (item.isSelected()) {
                    count++;
                }
            }
        }

        //MaterialDesignLibrary 出现的问题， 怀疑已经过时了， 但是没有时间去找新的，只能通过反射来设值了
//        java.lang.reflect.Field field = ReflectionUtils.getDeclaredField(btnBuy, "textButton");
//        field.setAccessible(true);
//        ReflectionUtils.methodInvoke()
//        ReflectionUtils.setFieldValue(btnBuy, "textButton", "去结算（" + count + "）");
        btnBuy.setText("去结算（" + count + "）");

        mAdapter.notifyDataSetChanged();
    }

    /**
     * 计算总价格
     */
    public void sum() {

        if (mRecyclerViewModels == null) {
            return;
        }

        float sum = 0;

        for (RecyclerViewModel recyclerViewModel : mRecyclerViewModels) {
            if (recyclerViewModel.getData() instanceof ShoppingCarItem) {
                ShoppingCarItem item = (ShoppingCarItem) recyclerViewModel.getData();
                if (item.isSelected()) {
                    sum += item.getSumPrice();
//                    Log.d("雨落无痕丶", "sum: "+sum);
                }
            }
        }

        tvPrice.setText("￥" + sum);
    }


    /**
     * 将实体数据处理为recyclerview能识别的数据列表
     *
     * @param shoppingCarList
     * @return
     */
    private List<RecyclerViewModel> handller(ShoppingCarList shoppingCarList) {

        List<RecyclerViewModel> recyclerViewModels = new ArrayList<>();

        for (ShoppingCar shoppingCar : shoppingCarList.getDataList()) {

            RecyclerViewModel shoppingCarRecyclerViewModel = new RecyclerViewModel();
            shoppingCarRecyclerViewModel.setItemType(ShoppingCarAdapter.TYPE_SHOPPING_CAR_TITLE);
            shoppingCarRecyclerViewModel.setData(shoppingCar);
            recyclerViewModels.add(shoppingCarRecyclerViewModel);

            for (ShoppingCarItem shoppingCarItem : shoppingCar.getShoppingCarItems()) {
                RecyclerViewModel shoppingCarItemRecyclerViewModel = new RecyclerViewModel();
                shoppingCarItemRecyclerViewModel.setItemType(ShoppingCarAdapter.TYPE_SHOPPING_CAR_ITEM);
                shoppingCarItemRecyclerViewModel.setData(shoppingCarItem);
                recyclerViewModels.add(shoppingCarItemRecyclerViewModel);
            }
        }

        return recyclerViewModels;

    }


    @OnCheckedChanged({R.id.cb_all_edit, R.id.cb_all_buy})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (!handler) {
            handlerChecked(true);
        }

    }

    @OnClick(R.id.tv_shopping_car_option)
    public void onClick() {

        if (status == 0) {
            status = 1;

            tvShoppingCarOption.setText("完成");
            showEdit();

        } else {
            status = 0;

            tvShoppingCarOption.setText("编辑");
            showBuy();
        }

    }

    @OnClick({R.id.btn_share, R.id.btn_remove, R.id.btn_to_sum, R.id.btn_to_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_share:
                share();
                break;
            case R.id.btn_remove:
                remove();
                break;
            case R.id.btn_to_sum:
                buy();
                break;
            case R.id.btn_to_login:
                UserUtils.login(getActivity());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
