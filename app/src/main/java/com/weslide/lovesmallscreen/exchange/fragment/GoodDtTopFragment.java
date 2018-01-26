package com.weslide.lovesmallscreen.exchange.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.exchange.activity.ExchangeGoodDetailActivity;
import com.weslide.lovesmallscreen.exchange.adapter.ExchangeGoodDtAdapter;
import com.weslide.lovesmallscreen.model_yy.javabean.ExchangeGoodDtModel;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YY on 2018/1/23.
 */
public class GoodDtTopFragment extends BaseFragment {

    @BindView(R.id.rclv)
    SuperRecyclerView rclv;
    private View fgView;
    private ExchangeGoodDetailActivity mParentActivity;
    private String goodOrder;
    private DataList<RecyclerViewModel> mDataList = new DataList<>();
    private ExchangeGoodDtAdapter mAdapter;

    public static GoodDtTopFragment newInstance(ExchangeGoodDetailActivity activity) {
        GoodDtTopFragment goodDtTopFragment = new GoodDtTopFragment();
        goodDtTopFragment.mParentActivity = activity;
        return goodDtTopFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_good_dt_top, container, false);
        ButterKnife.bind(this, fgView);
        initData();
        askGoodDtData();
        return fgView;
    }

    private void initData() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            goodOrder = bundle.getString("goodOrder");
        }
        mAdapter = new ExchangeGoodDtAdapter(getActivity(),mDataList);
        rclv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rclv.setAdapter(mAdapter);
    }

    private void askGoodDtData() {
        Request<ExchangeGoodDtModel> request = new Request<>();
        ExchangeGoodDtModel model = new ExchangeGoodDtModel();
        model.setGoodsOrder(goodOrder);
        request.setData(model);
        RXUtils.request(getActivity(), request, "getGoodsDetail", new RecyclerViewSubscriber<Response<ExchangeGoodDtModel>>(mAdapter, mDataList) {
            @Override
            public void onSuccess(Response<ExchangeGoodDtModel> exchangeGoodDtModelResponse) {
                setGoodInfo(exchangeGoodDtModelResponse.getData());
                if (mParentActivity != null) {
                    mParentActivity.setExchangeGood(exchangeGoodDtModelResponse.getData());
                }
            }
        });
    }

    private void setGoodInfo(ExchangeGoodDtModel data) {
        mDataList.getDataList().addAll(handleModel(data));
        mDataList.setPageSize(1);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 解析处理成列表能显示的数据
     *
     * @return
     */
    private List<RecyclerViewModel> handleModel(ExchangeGoodDtModel goodDtModel) {
        List<RecyclerViewModel> list = new ArrayList<>();
        //轮播图
        RecyclerViewModel bannerRecyclerViewModel = new RecyclerViewModel();
        bannerRecyclerViewModel.setItemType(ExchangeGoodDtAdapter.BANNER_TYPE);
        bannerRecyclerViewModel.setData(goodDtModel);
        list.add(bannerRecyclerViewModel);

        //商品信息
        RecyclerViewModel infoRecyclerViewModel = new RecyclerViewModel();
        infoRecyclerViewModel.setItemType(ExchangeGoodDtAdapter.GOOD_INFO_TYPE);
        infoRecyclerViewModel.setData(goodDtModel);
        list.add(infoRecyclerViewModel);

        //想换标签
        RecyclerViewModel goodNoteRecyclerViewModel = new RecyclerViewModel();
        goodNoteRecyclerViewModel.setItemType(ExchangeGoodDtAdapter.NOTE_TYPE);
        goodNoteRecyclerViewModel.setData(goodDtModel);
        list.add(goodNoteRecyclerViewModel);

        //商品描述
        RecyclerViewModel goodDescRecyclerViewModel = new RecyclerViewModel();
        goodDescRecyclerViewModel.setItemType(ExchangeGoodDtAdapter.GOOD_DESCRIPTION_TYPE);
        goodDescRecyclerViewModel.setData(goodDtModel);
        list.add(goodDescRecyclerViewModel);

        //查看更多
        RecyclerViewModel lookMoreRecyclerViewModel = new RecyclerViewModel();
        lookMoreRecyclerViewModel.setItemType(ExchangeGoodDtAdapter.LOOK_MORE_TYPE);
        list.add(lookMoreRecyclerViewModel);
        return list;
    }

}
