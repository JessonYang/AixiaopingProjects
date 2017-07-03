package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.adapters.GoodsConcernAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/7/24.
 * 商品关注fragment
 */
public class GoodsConcernFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_goods_concern_details)
    SuperRecyclerView lvGoodsConcernDetails;
    GetGoodsListBean mGoodsListReqeust = new GetGoodsListBean();
    GoodsConcernAdapter mAdapter;
    DataList<Goods> mDataList = new DataList<>();
    DataList<Goods> dataList = new DataList<>();
    @BindView(R.id.btn_remove)
    Button btnRemove;
    boolean check = false;
    @BindView(R.id.iv_edit)
    TextView ivEdit;
    int state = 0;
    List<Goods> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_goods_concern, container, false);
        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mAdapter = new GoodsConcernAdapter(getActivity(), mDataList, GoodsConcernFragment.this);
        getGoodList();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        lvGoodsConcernDetails.setLayoutManager(manager);

        lvGoodsConcernDetails.setAdapter(mAdapter);

        lvGoodsConcernDetails.setRefreshListener(() -> getGoodList());
        lvGoodsConcernDetails.setDifferentSituationOptionListener(v -> getGoodList());
    }


    private void getGoodList() {
        mDataList.getDataList().clear();
        Request request = new Request<>();

        RXUtils.request(getActivity(), request, "getConcernGoodsList", new RecyclerViewSubscriber<Response<DataList<Goods>>>(mAdapter, mDataList) {

            @Override
            public void onSuccess(Response<DataList<Goods>> goodsResponse) {
                datas = goodsResponse.getData().getDataList();
                mAdapter.addDataListNotifyDataSetChanged(goodsResponse.getData());
            }
        });
    }

    /**
     * 遍历集合 以及删除不关注的商品
     * @param goods
     */
    public void goodsConcern(List<Goods> goods) {
        List<Goods> data = new ArrayList<>();
        for(int i = 0;i< goods.size();i++){
            if(goods.get(i).getSelect()==true){
                Goods  good = new Goods();
                good.setGoodsId(goods.get(i).getGoodsId());
                good.setConcern(goods.get(i).getConcern());
                data.add(good);
            }
        }
        if(data.size()==0){
            Toast.makeText(getContext(), "未选商品!", Toast.LENGTH_SHORT).show();
            return;
        }
        Request<DataList<Goods>> request = new Request<>();
        dataList.setDataList(data);
       request.setData(dataList);
        RXUtils.request(getActivity(), request, "goodsConcern", new SupportSubscriber<Response>() {

            @Override
            public void onNext(Response response) {
                T.showShort(getActivity(), response.getMessage());
                getGoodList();
            }
        });
    }

    @OnClick({R.id.iv_edit, R.id.btn_remove})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_edit:
                if(state == 0){
                    state =1;
                    ivEdit.setText("完成");
                 btnRemove.setVisibility(View.VISIBLE);
                 mAdapter.setState(state);
                    mAdapter.notifyDataSetChanged();
                }else{
                    state = 0;
                    ivEdit.setText("编辑");
                    btnRemove.setVisibility(View.GONE);
                    mAdapter.setState(state);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_remove:
                if(datas.size()!=0) {
                    goodsConcern(datas);
                }
                break;

        }
    }
}
