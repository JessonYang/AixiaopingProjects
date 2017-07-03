package com.weslide.lovesmallscreen.fragments.goods;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.GoodsActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.Comment;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.GoodsAdapter;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/7.
 * 商品信息top
 */
public class GoodsInfoFragment extends BaseFragment {

    View mView;
    Goods goods;
    GoodsAdapter mAdapter;
    DataList<RecyclerViewModel> mDataList = new DataList<>();
    String goodsId;

    GoodsFragment mParentFragment;
    @BindView(R.id.list)
    SuperRecyclerView list;

    public static final GoodsInfoFragment newInstance(GoodsFragment parentFragment) {
        GoodsInfoFragment fragment = new GoodsInfoFragment();
        fragment.mParentFragment = parentFragment;

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_goods_info, container, false);

        ButterKnife.bind(this, mView);

        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new GoodsAdapter(getActivity(), mParentFragment, mDataList);
        list.setAdapter(mAdapter);
        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        loadBundler();
        initData();

        return mView;
    }

    private void loadBundler() {
        if (getActivity().getIntent().getExtras() != null) {
            goodsId = getActivity().getIntent().getExtras().getString(GoodsActivity.KEY_GOODS_ID);
        }

    }

    private void initData() {
        Goods _goods = new Goods();
        _goods.setGoodsId(goodsId);
        Request<Goods> request = new Request<>();
        request.setData(_goods);

        RXUtils.request(getActivity(), request, "getGoods", new RecyclerViewSubscriber<Response<Goods>>(mAdapter, mDataList) {

            @Override
            public void onSuccess(Response<Goods> goodsResponse) {
                goods = goodsResponse.getData();
                mParentFragment.setGoods(goods);
                mDataList.getDataList().addAll(handler());
                mDataList.setPageSize(1);  //如果pageSize是0 ，界面就会显示空
                mAdapter.notifyDataSetChanged();
            }
        });

    }


    /**
     * 解析处理成列表能显示的数据
     *
     * @return
     */
    private List<RecyclerViewModel> handler() {
        List<RecyclerViewModel> list = new ArrayList<>();

        RecyclerViewModel bannerRecyclerViewModel = new RecyclerViewModel();
        bannerRecyclerViewModel.setItemType(GoodsAdapter.TYPE_BANNER);
        bannerRecyclerViewModel.setData(goods);
        list.add(bannerRecyclerViewModel);

        RecyclerViewModel infoRecyclerViewModel = new RecyclerViewModel();
        infoRecyclerViewModel.setItemType(GoodsAdapter.TYPE_INFO);
        infoRecyclerViewModel.setData(goods);
        list.add(infoRecyclerViewModel);


        if (goods.getCommentList() != null && goods.getCommentList().getDataList() != null && goods.getCommentList().getDataList().size() > 0) {
            RecyclerViewModel commentTitleRecyclerViewModel = new RecyclerViewModel();
            commentTitleRecyclerViewModel.setItemType(GoodsAdapter.TYPE_COMMENT_TITLE);
            commentTitleRecyclerViewModel.setData(goods);
            list.add(commentTitleRecyclerViewModel);

            for (Comment comment : goods.getCommentList().getDataList()) {
                RecyclerViewModel commentItemRecyclerViewModel = new RecyclerViewModel();
                commentItemRecyclerViewModel.setItemType(GoodsAdapter.TYPE_COMMENT_ITEM);
                commentItemRecyclerViewModel.setData(comment);

                list.add(commentItemRecyclerViewModel);
            }

        }

        RecyclerViewModel loadDetailsRecyclerViewModel = new RecyclerViewModel();
        loadDetailsRecyclerViewModel.setItemType(GoodsAdapter.TYPE_LOAD_DETAIL);
        list.add(loadDetailsRecyclerViewModel);

        return list;
    }

}
