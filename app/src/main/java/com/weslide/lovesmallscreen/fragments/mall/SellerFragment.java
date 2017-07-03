package com.weslide.lovesmallscreen.fragments.mall;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.SellerActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.views.adapters.SellerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/12.
 * 周边店铺进店详情界面
 */
public class SellerFragment extends BaseFragment {


    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.list)
    SuperRecyclerView list;
    SellerAdapter mAdapter;
    String mSellerId = "-1";
    int pageIndex = 0;
    private String sellerName;
    DataList<RecyclerViewModel> mHomeData = new DataList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_seller, container, false);


        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        initBundle();
        initRecycler();
        initSellerData();
        return mView;
    }

    private void initBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mSellerId = bundle.getString(SellerActivity.SELLER_ID, "-1");
        }
    }

    private void initRecycler() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果是商品占用一格，如果不是占用一行
                return mAdapter.isGoods(position) ? 1 : layoutManager.getSpanCount();
            }
        });
        list.setLayoutManager(layoutManager);
        mAdapter = new SellerAdapter(getActivity(), mHomeData);
        list.setAdapter(mAdapter);
        //设置加载更多事件
        list.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                L.e("正在加载更多");
                loadSellerGoodsData();
            }
        }, 2);

        //添加分割
        list.addItemDecoration(new RecyclerView.ItemDecoration() {

            int goodsIndex = 0;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


                int position = parent.getChildLayoutPosition(view);
                int itemType = mAdapter.getItemViewType(position);

                if (itemType == SellerAdapter.TYPE_SELLER_GOODS) {
                    int space = DensityUtils.dp2px(getActivity(), 4);

                    outRect.bottom = space;
                    //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
                    if (goodsIndex % 2 == 0) {
                        outRect.left = space;
                        outRect.right = space / 2;
                    } else {
                        outRect.right = space;
                        outRect.left = space / 2;
                    }

                    goodsIndex++;
                }


            }
        });

        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSellerData();
            }
        });
    }

    /**
     * 获取网络数据
     */
    private void initSellerData() {

        Seller seller = new Seller();
        seller.setSellerId(mSellerId);
        Request<Seller> sellerRequest = new Request<Seller>();
        sellerRequest.setData(seller);

        RXUtils.request(getActivity(), sellerRequest, "getSeller", new SupportSubscriber() {


            @Override
            public void onNext(Object o) {
                Response<Seller> response = (Response<Seller>) o;

                handlerSellerData(response);
                mAdapter.notifyDataSetChanged();
                loadSellerGoodsData();
                sellerName = response.getData().getSellerName();
            }
        });
    }

    /**
     * 分页加载商家的商品
     */
    private void loadSellerGoodsData() {
        GetGoodsListBean goodsReqeust = new GetGoodsListBean();
        goodsReqeust.setPageIndex(++pageIndex);
        goodsReqeust.setSellerId(mSellerId);
        Request<GetGoodsListBean> request = new Request<>();
        request.setData(goodsReqeust);

        RXUtils.request(getActivity(), request, "getGoodsList", new SupportSubscriber() {
            @Override
            public void onNext(Object o) {
                Response<GoodsList> response = (Response<GoodsList>) o;
                DataList<RecyclerViewModel> dataList = handlerSellerGoodsListData(response);
                addSellerData(mHomeData, dataList);
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    /**
     * 设置通用的响应数据
     *
     * @param oldData
     * @param newData
     */
    private void setResponseData(DataList oldData, DataList newData) {
        oldData.setPageIndex(newData.getPageIndex());
        oldData.setPageItemCount(newData.getPageItemCount());
        oldData.setPageSize(newData.getPageSize());
    }

    /**
     * 添加商家详情数据
     *
     * @param oldData
     * @param newData
     */
    public void addSellerData(DataList<RecyclerViewModel> oldData, DataList<RecyclerViewModel> newData) {
        setResponseData(oldData, newData);
        oldData.getDataList().addAll(newData.getDataList());
    }

    /**
     * 将请求数据转换为RecyclerView能识别的数据集合
     *
     * @param response
     * @return
     */
    public void handlerSellerData(Response<Seller> response) {

        mHomeData.setPageIndex(0);
        mHomeData.setPageSize(2);


        //头部轮播
        RecyclerViewModel imagesRecyclerViewModel = new RecyclerViewModel();
        imagesRecyclerViewModel.setItemType(SellerAdapter.TYPE_SELLER_IMAGES);
        List<ImageText> imageTexts = new ArrayList<>();
        if (response.getData().getVideos() != null)
            imageTexts.addAll(response.getData().getVideos());
        if (response.getData().getImages() != null)
            imageTexts.addAll(response.getData().getImages());
        imagesRecyclerViewModel.setData(imageTexts);
        mHomeData.getDataList().add(imagesRecyclerViewModel);

        //商家信息
        RecyclerViewModel baseInfoRecyclerViewModel = new RecyclerViewModel();
        baseInfoRecyclerViewModel.setItemType(SellerAdapter.TYPE_SELLER_INFO);
        baseInfoRecyclerViewModel.setData(response.getData());
        mHomeData.getDataList().add(baseInfoRecyclerViewModel);

        //积分商品列表
        if (response.getData().getRecommendScoreGoodsList() != null && response.getData().getRecommendScoreGoodsList().size() != 0) {
            RecyclerViewModel socreGoodsRecyclerViewModel = new RecyclerViewModel();
            socreGoodsRecyclerViewModel.setItemType(SellerAdapter.TYPE_SELLER_SCORE_GOODS);
            socreGoodsRecyclerViewModel.setData(response.getData().getRecommendScoreGoodsList());
            mHomeData.getDataList().add(socreGoodsRecyclerViewModel);
        }


        //横幅广告
        if (response.getData().getBanner() != null && response.getData().getBanner().size() != 0) {
            RecyclerViewModel bannerRecyclerViewModel = new RecyclerViewModel();
            bannerRecyclerViewModel.setItemType(SellerAdapter.TYPE_SELLER_BANNER);
            bannerRecyclerViewModel.setData(response.getData().getBanner());
            mHomeData.getDataList().add(bannerRecyclerViewModel);

        }

    }

    /**
     * 将请求数据转换为RecyclerView能识别的数据集合
     *
     * @param response
     * @return
     */
    public DataList<RecyclerViewModel> handlerSellerGoodsListData(Response<GoodsList> response) {

        DataList<RecyclerViewModel> responseRecyclerViewModel = new DataList<>();
        List<RecyclerViewModel> recyclerViewModels = new ArrayList<>();

        setResponseData(responseRecyclerViewModel, response.getData());

        for (Goods goods : response.getData().getDataList()) {
            RecyclerViewModel sellerRecyclerViewModel = new RecyclerViewModel();
            sellerRecyclerViewModel.setItemType(SellerAdapter.TYPE_SELLER_GOODS);
            sellerRecyclerViewModel.setData(goods);
            recyclerViewModels.add(sellerRecyclerViewModel);
        }

        responseRecyclerViewModel.setDataList(recyclerViewModels);

        return responseRecyclerViewModel;
    }

    //分享
    @OnClick(R.id.iv_share)
    public void onClick() {
        ShareContent seller = ContextParameter.getClientConfig().getSellerShareContent();
        ShareUtils.share(getActivity(), sellerName,
                seller.getIconUrl(),
                seller.getTargetUrl() + "?userId=" + ContextParameter.getUserInfo().getUserId() + "&appVersion=" + AppUtils.getVersionCode(getActivity()) + "&zoneId=" + ContextParameter.getCurrentZone().getZoneId() +
                        "&sellerId=" + mSellerId + "&img=" + ContextParameter.getUserInfo().getHeadimage() + "&name=" + ContextParameter.getUserInfo().getName() + "&phone=" + ContextParameter.getUserInfo().getPhone() + "&code=" + ContextParameter.getUserInfo().getInviteCode(),
                seller.getContent());
        L.e("===标题==" + seller.getTitle());
        L.e("===内容==" + seller.getContent());
    }
}
