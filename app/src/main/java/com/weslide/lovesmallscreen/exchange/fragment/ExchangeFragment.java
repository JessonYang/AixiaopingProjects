package com.weslide.lovesmallscreen.exchange.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.exchange.adapter.ExchangeHomeRclvAdapter;
import com.weslide.lovesmallscreen.exchange.basemodel.iinterface.IShowExchangeFg;
import com.weslide.lovesmallscreen.exchange.presenter.ExchangeHomePresenter;
import com.weslide.lovesmallscreen.models.GoodsType;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YY on 2018/1/19.
 */
public class ExchangeFragment extends BaseFragment implements IShowExchangeFg, TabLayout.OnTabSelectedListener, SwipeRefreshLayout.OnRefreshListener, OnMoreListener {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.rclv)
    SuperRecyclerView rclv;
    @BindView(R.id.exchange_tab)
    TabLayout mExchangeTab;
    private View fgView;
    private DataList<RecyclerViewModel> mDataList = new DataList<>();
    private ExchangeHomeRclvAdapter mAdapter;
    private GridLayoutManager gridLayoutManager;
    private LoadingDialog loadingDialog;
    private ExchangeHomePresenter presenter;
    private String typeId = "0";
    private String search = "";
    private List<GoodsType> mTypeList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_exchange, container, false);
        ButterKnife.bind(this, fgView);
        presenter = new ExchangeHomePresenter(this);
        presenter.showFgView(getActivity(),typeId,search);
        return fgView;
    }

    @Override
    public void initView() {
        loadingDialog = new LoadingDialog(getActivity());
        mDataList.setDataList(new ArrayList<>());
    }

    @Override
    public void initData() {
        mAdapter = new ExchangeHomeRclvAdapter(getActivity(), mDataList);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rclv.setLayoutManager(gridLayoutManager);
//        rclv.addItemDecoration(new DividerGridItemDecoration(getActivity(), DensityUtils.dp2px(getActivity(),10),R.color.main_color));
        rclv.setAdapter(mAdapter);
        rclv.setRefreshListener(this);
        rclv.setupMoreListener(this);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.isGood(position) ? 1 : gridLayoutManager.getSpanCount();
            }
        });
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {

            }

            @Override
            public void onRightImgClick() {

            }
        });
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showErrView() {

    }

    @Override
    public void showView(DataList<RecyclerViewModel> dataList, List<GoodsType> typeList) {
        if (typeList != null && typeList.size() > 0) {
            mTypeList = typeList;
            setTablayout(typeList);
        }
        mDataList.getDataList().clear();
        mDataList.getDataList().addAll(dataList.getDataList());
        mDataList.setPageIndex(dataList.getPageIndex());
        mDataList.setPageSize(dataList.getPageSize());
        mAdapter.notifyDataSetChanged();
    }

    private void setTablayout(List<GoodsType> typeList) {
        for (int i = 0; i < typeList.size() + 1; i++) {
            TabLayout.Tab newTab = mExchangeTab.newTab();
            if (i == 0) {
                newTab.setText("全部");
            }else {
                newTab.setText(typeList.get(i - 1).getTypeName());
            }
            mExchangeTab.addTab(newTab);
        }
        mExchangeTab.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int selectPos = tab.getPosition();
        if (selectPos != 0) {
            typeId = mTypeList.get(selectPos - 1).getTypeId();
        }else {
            typeId = "0";
        }
        presenter.refreshView(typeId,search);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onRefresh() {
        presenter.refreshView(typeId,search);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        presenter.loadMore();
    }
}
