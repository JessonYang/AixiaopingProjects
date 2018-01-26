package com.weslide.lovesmallscreen.exchange.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.exchange.adapter.ChooseGoodLvAdapter;
import com.weslide.lovesmallscreen.model_yy.javabean.ExchangeGoodDtModel;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseGoodsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.btn_sure)
    Button btn_sure;
    @BindView(R.id.good_lv)
    PullToRefreshListView goodLv;
    private ChooseGoodLvAdapter mAdapter;
    private List<ExchangeGoodDtModel> mGoodList = new ArrayList<>();
    private int pageIndex = 1;
    private int pageSize = 1;
    private int curPos = 0;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_goods);
        ButterKnife.bind(this);
        initData();
        askNetData();
    }

    private void initData() {
        intent = getIntent();
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        mAdapter = new ChooseGoodLvAdapter(this, mGoodList);
        goodLv.setAdapter(mAdapter);
        goodLv.setMode(PullToRefreshBase.Mode.BOTH);
        goodLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex = 1;
                mGoodList.clear();
                askNetData();
            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex++;
                if (pageIndex <= pageSize) {
                    askNetData();
                } else {
                    toolbar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChooseGoodsActivity.this, "没有更多数据了哦!", Toast.LENGTH_SHORT).show();
                            goodLv.onRefreshComplete();
                        }
                    }, 850);
                }
            }
        });
        goodLv.getRefreshableView().setOnItemClickListener(this);
    }

    private void askNetData() {
        Request<ExchangeGoodDtModel> request = new Request<>();
        ExchangeGoodDtModel model = new ExchangeGoodDtModel();
        model.setPageIndex(pageIndex);
        request.setData(model);
        RXUtils.request(this, request, "getMyChangeGoods", new SupportSubscriber<Response<DataList<ExchangeGoodDtModel>>>() {
            @Override
            public void onNext(Response<DataList<ExchangeGoodDtModel>> dataListResponse) {
                pageSize = dataListResponse.getData().getPageSize();
                mGoodList.addAll(dataListResponse.getData().getDataList());
                mAdapter.notifyDataSetChanged();
                goodLv.onRefreshComplete();
                if (mGoodList.size() == 0) {
                    btn_sure.setBackgroundResource(R.drawable.gray_rec_btn);
                    btn_sure.setEnabled(false);
                }else {
                    btn_sure.setBackgroundResource(R.drawable.btn_bg_yes_click);
                    btn_sure.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        curPos = i;
        mAdapter.changPos(i);
        Log.d("雨落无痕丶", "pos: " + i);
    }

    @OnClick(R.id.btn_sure)
    public void onClick() {
        intent.putExtra("goods",mGoodList.get(curPos));
        setResult(DealDetailActivity.ADD_GOOD_RESULT,intent);
        finish();
    }
}
