package com.weslide.lovesmallscreen.fragments.mall;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.SellerListSearchActivity;
import com.weslide.lovesmallscreen.activitys.mall.AmbitusSellerActivity;
import com.weslide.lovesmallscreen.activitys.mall.SellerListActivity_new;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.CityItems;
import com.weslide.lovesmallscreen.models.CityType;
import com.weslide.lovesmallscreen.models.GoodsType;
import com.weslide.lovesmallscreen.models.HeadquartersType;
import com.weslide.lovesmallscreen.models.SellerList;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.models.bean.GetSellerListBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.view_yy.customview.RecyclerViewDivider;
import com.weslide.lovesmallscreen.views.adapters.SellerListAdapter;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;
import com.weslide.lovesmallscreen.views.dialogs.SecondaryCityDialog_new;
import com.weslide.lovesmallscreen.views.dialogs.SecondaryTypeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/23.
 * 商家列表
 */
public class SellerListFragment_new extends BaseFragment {

    View mView;
    @BindView(R.id.list)
    SuperRecyclerView list;
    @BindView(R.id.seller_list_search_edt)
    EditText search_edt;
    @BindView(R.id.tool_bar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.near_line)
    View nearLine;
    @BindView(R.id.type_line)
    View typeLine;
    @BindView(R.id.comment_line)
    View commentLine;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.divider_fl)
    FrameLayout fl;
    @BindView(R.id.near_iv)
    ImageView nearIv;
    @BindView(R.id.type_iv)
    ImageView typeIv;
    @BindView(R.id.comment_iv)
    ImageView commentIv;
    @BindView(R.id.near_tv)
    TextView nearTv;
    @BindView(R.id.type_tv)
    TextView typeTv;
    @BindView(R.id.comment_tv)
    TextView commentTv;
    /*@BindView(R.id.near_rll)
    RelativeLayout nearRll;
    @BindView(R.id.type_rll)
    RelativeLayout typeRll;
    @BindView(R.id.comment_rll)
    RelativeLayout commentRll;*/
    SellerListAdapter mAdapter;
    SellerList mSellerList = new SellerList();
    GetSellerListBean getSellerListBean = new GetSellerListBean();
    private int top;
    private GetGoodsListBean mGoodsTypeReqeust = new GetGoodsListBean();
    private List<CityType> mDataType;
    private List<GoodsType> typeList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_seller_list_new, container, false);

        ButterKnife.bind(this, mView);

        Rect rect = new Rect();

        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        top = toolbar.getLayoutParams().height + ll.getLayoutParams().height + fl.getLayoutParams().height+rect.top;

//        top = DPUtils.dip2px(getActivity(),115);

//        Log.d("雨落无痕丶", "onCreateView:" + toolbar.getLayoutParams().height+","+ll.getLayoutParams().height+","+fl.getLayoutParams().height+","+rect.top);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);

        mAdapter = new SellerListAdapter(getActivity(), mSellerList);

        list.setAdapter(mAdapter);

        list.addItemDecoration(new RecyclerViewDivider(getActivity(),LinearLayoutManager.VERTICAL,1, Color.parseColor("#dddddd")));

        //刷新
        list.setRefreshListener(() -> {
            getSellerListBean.setPageIndex(0);
            loadData();
        });
        //加载更多
        list.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> loadData(), 2);
        //数据请求失败后的数据重新载入
        list.setDifferentSituationOptionListener(view -> {
            getSellerListBean.setPageIndex(0);
            loadData();
        });

        loadData();
//        loadType();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        search_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toActivity(getActivity(), SellerListSearchActivity.class);
            }
        });

        return mView;
    }

    public void loadData() {
        Request request = new Request();
        getSellerListBean.setPageIndex(getSellerListBean.getPageIndex() + 1);
        getSellerListBean.setType("LIST");//列表类型
        request.setData(getSellerListBean);
        RXUtils.request(getActivity(), request, "getSellerList", new RecyclerViewSubscriber<Response<SellerList>>(mAdapter, mSellerList) {
            @Override
            public void onSuccess(Response<SellerList> sellerListResponse) {
                SellerListActivity_new.sellerList = (ArrayList) sellerListResponse.getData().getDataList();
                mDataType = (ArrayList) sellerListResponse.getData().getCityList();
                typeList = (ArrayList) sellerListResponse.getData().getTypeList();
                mAdapter.addDataListNotifyDataSetChanged(sellerListResponse.getData());
            }
        });
    }

    public void changeCityData(String cityId) {
        LoadingDialog loadDialog = new LoadingDialog(getActivity());
        loadDialog.show();
        Request request = new Request();
        getSellerListBean.setPageIndex(1);
        getSellerListBean.setType("LIST");//列表类型
        getSellerListBean.setCityId(cityId);
        request.setData(getSellerListBean);
        RXUtils.request(getActivity(), request, "getSellerList", new RecyclerViewSubscriber<Response<SellerList>>(mAdapter, mSellerList) {
            @Override
            public void onSuccess(Response<SellerList> sellerListResponse) {
                SellerListActivity_new.sellerList = (ArrayList) sellerListResponse.getData().getDataList();
                mAdapter.addDataListNotifyDataSetChanged(sellerListResponse.getData());
                loadDialog.dismiss();
            }
        });
    }

    public void changeTypeData(String shopcategoryId) {
        LoadingDialog loadDialog = new LoadingDialog(getActivity());
        loadDialog.show();
        Request request = new Request();
        getSellerListBean.setPageIndex(1);
        getSellerListBean.setType("LIST");//列表类型
        getSellerListBean.setShopcategoryId(shopcategoryId);
        request.setData(getSellerListBean);
        RXUtils.request(getActivity(), request, "getSellerList", new RecyclerViewSubscriber<Response<SellerList>>(mAdapter, mSellerList) {
            @Override
            public void onSuccess(Response<SellerList> sellerListResponse) {
                SellerListActivity_new.sellerList = (ArrayList) sellerListResponse.getData().getDataList();
                mAdapter.addDataListNotifyDataSetChanged(sellerListResponse.getData());
                loadDialog.dismiss();
            }
        });
    }

    private void loadType() {
        mGoodsTypeReqeust.setType("91");
        mGoodsTypeReqeust.setPageIndex(1);
        Request<GetGoodsListBean> request = new Request();
        request.setData(mGoodsTypeReqeust);
        RXUtils.request(getActivity(), request, "getGoodsType", new SupportSubscriber<Response<HeadquartersType>>() {

            @Override
            public void onNext(Response<HeadquartersType> listResponse) {
                mDataType = listResponse.getData().getCityList();
                typeList = listResponse.getData().getTypeList();
            }
        });
    }

    @OnClick({R.id.btn_sellers_map, R.id.near_rll, R.id.type_rll, R.id.comment_rll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sellers_map:
                if (SellerListActivity_new.sellerList == null || SellerListActivity_new.sellerList.size() == 0) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(AmbitusSellerActivity.KEY_SELLER_LIST, SellerListActivity_new.sellerList);
                AppUtils.toActivity(getActivity(), AmbitusSellerActivity.class, bundle);
                break;

            case R.id.near_rll:
                nearIv.setImageResource(R.drawable.icon_xiasanjiao_red);
                typeIv.setImageResource(R.drawable.icon_xiasanjiao);
                commentIv.setImageResource(R.drawable.icon_xiasanjiao);
                nearLine.setVisibility(View.VISIBLE);
                typeLine.setVisibility(View.INVISIBLE);
                commentLine.setVisibility(View.INVISIBLE);
                nearTv.setTextColor(Color.parseColor("#ff2d47"));
                typeTv.setTextColor(Color.parseColor("#333333"));
                commentTv.setTextColor(Color.parseColor("#333333"));
                SecondaryCityDialog_new dialog_new = new SecondaryCityDialog_new(getActivity(), mDataType, top);
                dialog_new.setOnClassificationSelectListener(new SecondaryCityDialog_new.OnClassificationSelectListener() {
                    @Override
                    public void select(CityItems type) {
                        changeCityData(type.getCityId());
                    }
                });
                dialog_new.show();
                dialog_new.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        nearLine.setVisibility(View.INVISIBLE);
                        nearTv.setTextColor(Color.parseColor("#333333"));
                        nearIv.setImageResource(R.drawable.icon_xiasanjiao);
                    }
                });
                break;

            case R.id.type_rll:
                nearIv.setImageResource(R.drawable.icon_xiasanjiao);
                typeIv.setImageResource(R.drawable.icon_xiasanjiao_red);
                commentIv.setImageResource(R.drawable.icon_xiasanjiao);
                nearLine.setVisibility(View.INVISIBLE);
                typeLine.setVisibility(View.VISIBLE);
                commentLine.setVisibility(View.INVISIBLE);
                nearTv.setTextColor(Color.parseColor("#333333"));
                typeTv.setTextColor(Color.parseColor("#ff2d47"));
                commentTv.setTextColor(Color.parseColor("#333333"));
                SecondaryTypeDialog typeDialog = new SecondaryTypeDialog(getActivity(), typeList, top);
                typeDialog.setOnClassificationSelectListener(new SecondaryTypeDialog.OnClassificationSelectListener() {
                    @Override
                    public void select(GoodsType type) {
                        changeTypeData(type.getTypeId());
                    }
                });
                typeDialog.show();
                typeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        typeLine.setVisibility(View.INVISIBLE);
                        typeTv.setTextColor(Color.parseColor("#333333"));
                        typeIv.setImageResource(R.drawable.icon_xiasanjiao);
                    }
                });
                break;

            case R.id.comment_rll:

                break;
        }
    }

}
