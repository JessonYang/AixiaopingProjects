package com.weslide.lovesmallscreen.fragments.mall;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.SecondKill;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.HomeSecondKillGoodsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/21.
 * 首页秒杀
 */
public class HomeSecondKillGoodsFragment extends BaseFragment {

    /** 开始秒杀 */
    public boolean startSecondKill;

    View mView;
    SecondKill mSecondKill;
    @BindView(net.aixiaoping.library.R.id.list)
    RecyclerView list;
    HomeSecondKillGoodsAdapter mAdapter;
    GoodsList mGoodsList = new GoodsList();

    public static final HomeSecondKillGoodsFragment newInstance(SecondKill secondKill) {
        HomeSecondKillGoodsFragment fragment = new HomeSecondKillGoodsFragment();
        fragment.mSecondKill = secondKill;

        return fragment;
    }

    public void setStartSecondKill(boolean startSecondKill){
        this.startSecondKill = startSecondKill;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(net.aixiaoping.library.R.layout.fragment_home_second_kill, container, false);
        ButterKnife.bind(this, mView);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        list.setLayoutManager(layoutManager);

        //添加分割
        list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                int space = DensityUtils.dp2px(getActivity(), 4);
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;
                //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
                if (parent.getChildLayoutPosition(view) % 2 == 0) {
                    outRect.left = 0;
                } else {
                    outRect.left = 0;
                }
            }
        });

        mAdapter = new HomeSecondKillGoodsAdapter(getContext(), this, mGoodsList);
        list.setAdapter(mAdapter);
        loadData();


        return mView;
    }

    private void loadData() {

        if (mSecondKill == null) {
            return;
        }

        Request<GetGoodsListBean> reqeust = new Request<>();
        GetGoodsListBean goodsListRequest = new GetGoodsListBean();
        reqeust.setData(goodsListRequest);

        goodsListRequest.setMallTyle(Constants.MALL_SECOND_KILL);
        goodsListRequest.setSecondKillId(mSecondKill.getSecondKillId());
        goodsListRequest.setPageIndex(1);

        RXUtils.request(getActivity(), reqeust, "getGoodsList", new RecyclerViewSubscriber<Response<GoodsList>>(mAdapter, mGoodsList) {
            @Override
            public void onSuccess(Response<GoodsList> goodsListResponse) {
                mAdapter.addDataListNotifyDataSetChanged(goodsListResponse.getData());
            }
        });
    }

}
