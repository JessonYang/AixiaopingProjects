package com.weslide.lovesmallscreen.fragments.mall;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.GoodsClassifiActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.GoodsType;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.ClassifiGoodsAdapter;
import com.weslide.lovesmallscreen.views.dialogs.SecondaryClassificationDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/23.
 * 分类商品Fragment
 */
public class GoodsClassifiFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_all_classifi)
    TextView tvAllClassifi;
    @BindView(R.id.list)
    SuperRecyclerView list;
    ClassifiGoodsAdapter mAdpater;
    //商品列表
    GoodsList mGoodsList = new GoodsList();

    /**
     * 请求参数对象
     * GetGoodsListBean
     */
    public static final String KEY_REQUEST = "KEY_REQUEST";
    GetGoodsListBean mGoodsListReqeust = new GetGoodsListBean();
    String title;
    @BindView(R.id.layout_classification)
    LinearLayout layoutClassification;
    @BindView(R.id.tv_sales_volume)
    TextView tvSalesVolume;
    @BindView(R.id.tv_value)
    TextView tvValue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_classifi, container, false);

        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(v -> getActivity().finish());

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        list.setLayoutManager(layoutManager);
        mAdpater = new ClassifiGoodsAdapter(getActivity(), mGoodsList);
        list.setAdapter(mAdpater);

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

        list.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> loadData(), 2);
        //刷新原有数据
        list.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                if (getActivity().getIntent().getExtras() != null) {
//                    Bundle bundle = getActivity().getIntent().getExtras();
//                    mGoodsListReqeust = (GetGoodsListBean) bundle.getSerializable(KEY_REQUEST);
//
//                    mGoodsListReqeust.setValue("0");
//                    tvValue.setText("价格");
//                    mGoodsListReqeust.setSalesVolume("0");
//                    tvSalesVolume.setText("销量");
//                }
                reLoadData();
            }
        });
        //数据请求失败后的数据重新载入
        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().getIntent().getExtras() != null) {
                    Bundle bundle = getActivity().getIntent().getExtras();
                    mGoodsListReqeust = (GetGoodsListBean) bundle.getSerializable(KEY_REQUEST);

                    mGoodsListReqeust.setValue("0");
                    tvValue.setText("价格");
                    mGoodsListReqeust.setSalesVolume("0");
                    tvSalesVolume.setText("销量");
                }
                reLoadData();
            }
        });
        loadBundle();
        loadData();

        toolBar.setTitle(title);

        return mView;
    }

    public void loadBundle() {
        if (getActivity().getIntent().getExtras() != null) {
            Bundle bundle = getActivity().getIntent().getExtras();
            mGoodsListReqeust = (GetGoodsListBean) bundle.getSerializable(KEY_REQUEST);
            title = bundle.getString(GoodsClassifiActivity.KEY_TITLE, "商品分类");
        }
    }

    public void reLoadData() {
        mGoodsListReqeust.setPageIndex(0);

//        list.showProgress();  //显示加载中
        loadData();
    }

    public void loadData() {
        Request<GetGoodsListBean> request = new Request<>();
        mGoodsListReqeust.setPageIndex(mGoodsListReqeust.getPageIndex() + 1);
        request.setData(mGoodsListReqeust);

        RXUtils.request(getActivity(), request, "getGoodsList", new RecyclerViewSubscriber<Response<GoodsList>>(mAdpater, mGoodsList) {

            @Override
            public void onSuccess(Response<GoodsList> goodsListResponse) {
                mAdpater.addDataListNotifyDataSetChanged(goodsListResponse.getData());
                if (mGoodsList.getTypes() == null) {
                    mGoodsList.setTypes(goodsListResponse.getData().getTypes());
                }
            }
        });
    }

    @OnClick({R.id.layout_all_classifi, R.id.layout_sales_volume, R.id.layout_value})
    public void onClick(View view) {
        if (mGoodsList == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.layout_all_classifi:

                if (mGoodsList.getTypes() == null || mGoodsList.getTypes().size() == 0) {
                    return;
                }

                int top = layoutClassification.getMeasuredHeight() + toolBar.getMeasuredHeight();

                SecondaryClassificationDialog dialog = new SecondaryClassificationDialog(getActivity(), mGoodsList.getTypes(), top);
                dialog.setOnClassificationSelectListener(new ClassificationSelectListener());
                dialog.show();
                break;
            case R.id.layout_sales_volume:

                mGoodsListReqeust.setValue("0");
                tvValue.setText("价格");

                if (mGoodsListReqeust.getSalesVolume().equals("0") || mGoodsListReqeust.getSalesVolume().equals("1")) {
                    mGoodsListReqeust.setSalesVolume("2");
                    tvSalesVolume.setText("从低到高");
                } else if (mGoodsListReqeust.getSalesVolume().equals("2")) {
                    mGoodsListReqeust.setSalesVolume("1");
                    tvSalesVolume.setText("从高到低");
                }
                reLoadData();
                break;
            case R.id.layout_value:

                mGoodsListReqeust.setSalesVolume("0");
                tvSalesVolume.setText("销量");

                if (mGoodsListReqeust.getValue().equals("1")) {
                    mGoodsListReqeust.setValue("2");
                    tvValue.setText("从低到高");
                } else if (mGoodsListReqeust.getValue().equals("0") || mGoodsListReqeust.getValue().equals("2")) {
                    mGoodsListReqeust.setValue("1");
                    tvValue.setText("从高到低");
                }
                reLoadData();
                break;
        }
    }

    class ClassificationSelectListener implements SecondaryClassificationDialog.OnClassificationSelectListener {
        @Override
        public void select(GoodsType type) {
            mGoodsListReqeust.setValue("0");
            tvValue.setText("价格");
            mGoodsListReqeust.setSalesVolume("0");
            tvSalesVolume.setText("销量");

            mGoodsListReqeust.setTypeId(type.getTypeId());

            toolBar.setTitle(type.getTypeName());
            reLoadData();
        }
    }
}
