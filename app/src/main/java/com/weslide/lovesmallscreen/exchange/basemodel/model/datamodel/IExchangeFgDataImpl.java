package com.weslide.lovesmallscreen.exchange.basemodel.model.datamodel;

import android.content.Context;

import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.exchange.basemodel.iinterface.IExchangeFgDataListenner;
import com.weslide.lovesmallscreen.models.bean.ExchangeGoodListResModel;
import com.weslide.lovesmallscreen.models.bean.ExchangeGoodModel;
import com.weslide.lovesmallscreen.models.bean.ExchangeReplyModel;
import com.weslide.lovesmallscreen.models.bean.ExchangeReplyResModel;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2018/1/20.
 */
public class IExchangeFgDataImpl implements IExchangeFgData {

    private int pageIndex = 1;
    private DataList<RecyclerViewModel> mDataList = new DataList<>();
    private List<RecyclerViewModel> mList = new ArrayList<>();
    private Context mContext;
    private ExchangeGoodListResModel goodListResData;
    private ExchangeReplyResModel replyResData;
    private final int BANNER_TYPE = 0;
    private final int GOOD_TYPE = 1;
    private final int REPLY_TYPE = 2;
    private final int MORE_TYPE = 3;
    private final int REPLY_TITLE_TYPE = 4;

    @Override
    public void askNetData(Context context, String typeId,String search,IExchangeFgDataListenner listener) {
        mContext = context;
        Request<ExchangeGoodListResModel> request = new Request<>();
        ExchangeGoodListResModel model = new ExchangeGoodListResModel();
        model.setTypeId(typeId);
        model.setSearch(search);
        model.setPageIndex(pageIndex);
        request.setData(model);
        RXUtils.request(context,request,"getChangeMallList", new SupportSubscriber<Response<ExchangeGoodListResModel>>() {

            @Override
            public void onNext(Response<ExchangeGoodListResModel> exchangeGoodListResModelResponse) {
                goodListResData = exchangeGoodListResModelResponse.getData();
                handleGoodListResData(listener);
            }
        });
    }

    @Override
    public void refreshData(String typeId, String search, IExchangeFgDataListenner listener) {
        pageIndex = 1;
        mList.clear();
        askNetData(mContext,typeId,search,listener);
    }

    @Override
    public void loadMoreData(IExchangeFgDataListenner listener) {
        pageIndex++;
        getReplyData(listener);
    }

    private void handleGoodListResData(IExchangeFgDataListenner listenner) {
        //轮播图
        if (goodListResData.getHomeTopImages() != null) {
            RecyclerViewModel topBannerModel = new RecyclerViewModel();
            topBannerModel.setItemType(BANNER_TYPE);
            topBannerModel.setData(goodListResData.getHomeTopImages());
            mList.add(topBannerModel);
        }
        //商品列表
        List<ExchangeGoodModel> changeList = goodListResData.getChangeList();
        if (changeList != null && changeList.size() > 0) {
            for (int i = 0; i < changeList.size(); i++) {
                RecyclerViewModel goodModel = new RecyclerViewModel();
                goodModel.setItemType(GOOD_TYPE);
                goodModel.setData(changeList.get(i));
                mList.add(goodModel);
            }
        }

        //查看更多商品
        RecyclerViewModel moreModel = new RecyclerViewModel();
        moreModel.setItemType(MORE_TYPE);
        mList.add(moreModel);

        //帖子标题(大家想换)
        RecyclerViewModel replyTitleModel = new RecyclerViewModel();
        replyTitleModel.setItemType(REPLY_TITLE_TYPE);
        replyTitleModel.setData(goodListResData.getWantImg());
        mList.add(replyTitleModel);

        getReplyData(listenner);
    }

    private void getReplyData(IExchangeFgDataListenner listenner) {
        Request<ExchangeReplyResModel> request = new Request<>();
        ExchangeReplyResModel model = new ExchangeReplyResModel();
        model.setPageIndex(pageIndex);
        request.setData(model);
        RXUtils.request(mContext,request,"getChangeReplys", new SupportSubscriber<Response<ExchangeReplyResModel>>() {

            @Override
            public void onNext(Response<ExchangeReplyResModel> exchangeReplyResModelResponse) {
                replyResData = exchangeReplyResModelResponse.getData();
                handleReplyResData(listenner);
            }
        });
    }

    private void handleReplyResData(IExchangeFgDataListenner listenner) {
        if (replyResData != null) {
            //帖子
            List<ExchangeReplyModel> noteList = replyResData.getNoteList();
            if (noteList != null) {
                for (ExchangeReplyModel reply : noteList) {
                    RecyclerViewModel replyModel = new RecyclerViewModel();
                    replyModel.setItemType(REPLY_TYPE);
                    replyModel.setData(reply);
                    mList.add(replyModel);
                }
            }
        }
        mDataList.setDataList(mList);
        mDataList.setPageIndex(replyResData.getPageIndex());
        mDataList.setPageSize(replyResData.getPageSize());
        listenner.onSuccess(mDataList,goodListResData.getClassifications());
    }
}
