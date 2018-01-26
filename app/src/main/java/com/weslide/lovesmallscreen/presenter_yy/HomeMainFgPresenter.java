package com.weslide.lovesmallscreen.presenter_yy;

import android.content.Context;

import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.model_yy.IHomeMainFgData;
import com.weslide.lovesmallscreen.model_yy.IHomeMainFgDataImpl;
import com.weslide.lovesmallscreen.model_yy.IMainFgListenner;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.CustomDialogUtil;
import com.weslide.lovesmallscreen.view_yy.viewinterface.IShowHomeMainFg;

/**
 * Created by YY on 2017/5/5.
 */
public class HomeMainFgPresenter {
    private IShowHomeMainFg iShowHomeMainFg;
    private IHomeMainFgData iHomeMainFgData;
    private Context context;

    public HomeMainFgPresenter(Context context,IShowHomeMainFg iShowHomeMainFg) {
        this.context = context;
        this.iShowHomeMainFg = iShowHomeMainFg;
        this.iHomeMainFgData = new IHomeMainFgDataImpl();
    }

    public void initHomeMainFgView() {
        iShowHomeMainFg.initView();
        iShowHomeMainFg.showLoadingView();
        iShowHomeMainFg.initData();
    }

    public void showHomeView(){
        iHomeMainFgData.getHomeDataList(context, new IMainFgListenner() {
            @Override
            public void onSuccess(DataList<RecyclerViewModel> dataList) {
                iShowHomeMainFg.showView(dataList);
            }

            @Override
            public void onError(String errMsg) {
                iShowHomeMainFg.dismissLoadingView();
                CustomDialogUtil.showNoticDialog(context,errMsg);
            }
        });
    }

    public void refreshHomeData(){
        iHomeMainFgData.refreshHomeData(new IMainFgListenner() {
            @Override
            public void onSuccess(DataList<RecyclerViewModel> dataList) {
                iShowHomeMainFg.refreshView(dataList);
            }

            @Override
            public void onError(String errMsg) {
                iShowHomeMainFg.dismissLoadingView();
            }
        });
    }

    public void loadMoreList(){
        iHomeMainFgData.loadMoreData(new IMainFgListenner() {
            @Override
            public void onSuccess(DataList<RecyclerViewModel> dataList) {
                iShowHomeMainFg.refreshView(dataList);
            }

            @Override
            public void onError(String errMsg) {
                iShowHomeMainFg.dismissLoadingView();
            }
        });
    }
}
