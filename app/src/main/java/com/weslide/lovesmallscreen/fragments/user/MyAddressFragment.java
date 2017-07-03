package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.user.MyAddressEditActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Address;
import com.weslide.lovesmallscreen.models.bean.AddressBean;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.adapters.MyAddressListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/7/1.
 * 地址列表
 */
public class MyAddressFragment extends BaseFragment {

    View mView;
    @BindView(R.id.iv_add_address)
    ImageView ivAddAddress;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    MyAddressListAdapter adapter;
    AddressBean mAddressListReqeust = new AddressBean();
    DataList<Address> adderss = new DataList<>();
    @BindView(R.id.lv_address_list)
    SuperRecyclerView lvAddressList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_my_address, container, false);

        ButterKnife.bind(this, mView);
        init();//初始化

        return mView;
    }

    private void init() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        adapter = new MyAddressListAdapter(getActivity(),MyAddressFragment.this, adderss);

        getAddressDetails();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        lvAddressList.setLayoutManager(manager);

        lvAddressList.setAdapter(adapter);

        lvAddressList.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getAddressDetails(), 2);

        lvAddressList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reLoadData();
            }
        });
        lvAddressList.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoadData();
            }
        });

    }

    public void reLoadData() {
        mAddressListReqeust.setPageIndex(0);
        getAddressDetails();
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoadData();
    }

    /**
     * 获取我的地址
     */
    private void getAddressDetails() {
        Request<AddressBean> request = new Request<>();
        mAddressListReqeust.setPageIndex(mAddressListReqeust.getPageIndex() + 1);
        request.setData(mAddressListReqeust);
        RXUtils.request(getActivity(), request, "getAddressList", new RecyclerViewSubscriber<Response<DataList<Address>>>(adapter, adderss){
            @Override
            public void onSuccess(Response<DataList<Address>> dataListResponse) {
                adapter.addDataListNotifyDataSetChanged(dataListResponse.getData());
            }
        });
    }

    /**
     * 删除地址
     * @param addressId
     */
    public void deleteAddress(String addressId){
        Request<Address> request = new Request<>();
        Address address = new Address();
        address.setAddressId(addressId);
        request.setData(address);
        RXUtils.request(getActivity(), request, "removeAddress", new SupportSubscriber<Response>() {

            @Override
            public void onNext(Response response) {
                T.showShort(getActivity(),response.getMessage());
                reLoadData();
            }
        });

    }

    /**
     * 设置默认地址
     * @param addressId
     */
    public void setDefaultAddress(String addressId){
        Request<Address> request = new Request<>();
        Address address = new Address();
        address.setAddressId(addressId);
        request.setData(address);
        RXUtils.request(getActivity(), request, "setDefaultAddress", new SupportSubscriber<Response>() {

            @Override
            public void onNext(Response response) {
                reLoadData();
            }
        });
    }

    @OnClick({R.id.iv_add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_address:
                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                AppUtils.toActivity(getActivity(), MyAddressEditActivity.class,bundle);
                break;
        }
    }
}
