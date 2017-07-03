package com.weslide.lovesmallscreen.view_yy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gc.materialdesign.views.ButtonRectangle;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketGoodsDtModel;
import com.weslide.lovesmallscreen.presenter_yy.TicketGoodsDtFgPresenter;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.view_yy.viewinterface.IShowTicketGoodsDtFg;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

/**
 * Created by YY on 2017/6/1.
 */
public class TicketGoodsDtFragment extends BaseFragment implements IShowTicketGoodsDtFg, View.OnClickListener {

    private View mFgView;
    private LoadingDialog loadingDialog;
    private ImageView headerGoodIv;
    private ImageView consumeStateIv;
    private TextView goodDescTv;
    private TextView afterTicketPriceTv;
    private TextView originalPriceTv;
    private TextView ticketPrice;
    private ImageView goodDtIv;
    private TextView predictProfitTv;
    private TextView leftTicketTv;
    private TextView useTimeTv;
    private TextView consumeStateTv;
    private TextView salesNumTv;
    private TextView noUseTimeTv;
    private ButtonRectangle btn_share_good;
    private TicketGoodsDtModel mTicketGoodsDtModel;
    private String ticketId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFgView = inflater.inflate(R.layout.fragment_ticket_good_dt, container, false);
        ticketId = getActivity().getIntent().getStringExtra("ticketId");
        TicketGoodsDtFgPresenter presenter = new TicketGoodsDtFgPresenter(this);
        presenter.showTicketGoodDtFgView(getActivity(), ticketId);
        return mFgView;
    }

    //初始化view
    @Override
    public void initView() {
        loadingDialog = new LoadingDialog(getActivity());
        headerGoodIv = ((ImageView) mFgView.findViewById(R.id.ticket_good_dt_banner_iv));
        consumeStateIv = ((ImageView) mFgView.findViewById(R.id.ticket_good_dt_consume_state_iv));
        goodDtIv = ((ImageView) mFgView.findViewById(R.id.ticket_goods_detail_iv));
        goodDescTv = ((TextView) mFgView.findViewById(R.id.ticket_good_dt_desc_tv));
        afterTicketPriceTv = ((TextView) mFgView.findViewById(R.id.after_ticket_price));
        originalPriceTv = ((TextView) mFgView.findViewById(R.id.original_price_tv));
        ticketPrice = ((TextView) mFgView.findViewById(R.id.ticket_price));
        predictProfitTv = ((TextView) mFgView.findViewById(R.id.ticket_good_dt_predict_profit_tv));
        leftTicketTv = ((TextView) mFgView.findViewById(R.id.ticket_good_dt_left_ticket_tv));
        useTimeTv = ((TextView) mFgView.findViewById(R.id.ticket_good_dt_use_time_tv));
        consumeStateTv = ((TextView) mFgView.findViewById(R.id.ticket_good_dt_consume_state_tv));
        salesNumTv = ((TextView) mFgView.findViewById(R.id.ticket_good_dt_slaes_num_tv));
        noUseTimeTv = ((TextView) mFgView.findViewById(R.id.ticket_good_dt_noUse_time_tv));
        btn_share_good = ((ButtonRectangle) mFgView.findViewById(R.id.ticket_good_dt_btn_share));
    }

    //准备工作，初始化数据
    @Override
    public void initData() {
        goodDtIv.setOnClickListener(this);
        btn_share_good.setOnClickListener(this);
    }

    //加载数据之前显示的LoadingView
    @Override
    public void showLoadingView() {
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissLoadingView() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    //请求网络数据失败
    @Override
    public void showErrView() {

    }

    //适配数据
    @Override
    public void showViewData(TicketGoodsDtModel ticketGoodsDtModel) {
        mTicketGoodsDtModel = ticketGoodsDtModel;
        Glide.with(getActivity()).load(ticketGoodsDtModel.getGoodsIcon()).into(headerGoodIv);
        goodDescTv.setText(ticketGoodsDtModel.getGoodsName());
        afterTicketPriceTv.setText(ticketGoodsDtModel.getGoodsPriceAfterTicket());
        originalPriceTv.setText("原价￥" + ticketGoodsDtModel.getGoodsOrieignPrice());
        ticketPrice.setText(ticketGoodsDtModel.getTicketFaceValue());
        predictProfitTv.setText("￥" + ticketGoodsDtModel.getProfit());
        leftTicketTv.setText(ticketGoodsDtModel.getTicketVacancy());
        useTimeTv.setText(ticketGoodsDtModel.getDaysInUse());
        salesNumTv.setText(ticketGoodsDtModel.getGoodsSold());
        noUseTimeTv.setText(ticketGoodsDtModel.getExpiryDate());
        String consumeTack = ticketGoodsDtModel.getConsumeTack();
        if (consumeTack.equals("0")) {
            consumeStateIv.setImageResource(R.drawable.icon_sm_ddxf);
            consumeStateTv.setText("到店消费");
        } else if (consumeTack.equals("1")) {
            consumeStateIv.setImageResource(R.drawable.icon_sm_bydj);
            consumeStateTv.setText("包邮到家");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ticket_goods_detail_iv:  //商品详情点击监听
                AppUtils.toGoods(getContext(), mTicketGoodsDtModel.getGoodsId());
                break;
            case R.id.ticket_good_dt_btn_share:  //分享推广点击监听
                ShareUtils.share(getActivity(), mTicketGoodsDtModel.getShareTitle(), mTicketGoodsDtModel.getShareIconUrl(), mTicketGoodsDtModel.getShareTargetUrl(), mTicketGoodsDtModel.getShareContent());
                break;
        }
    }
}
