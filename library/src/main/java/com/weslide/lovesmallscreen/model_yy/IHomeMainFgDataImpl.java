package com.weslide.lovesmallscreen.model_yy;

import android.content.Context;
import android.os.Handler;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.FeatureTypeModel;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.LivemoduleModel;
import com.weslide.lovesmallscreen.models.NewHomePageModel;
import com.weslide.lovesmallscreen.models.NfcpModel;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.SellerList;
import com.weslide.lovesmallscreen.models.SqgwModel;
import com.weslide.lovesmallscreen.models.TopClassifyModel;
import com.weslide.lovesmallscreen.models.TopLocalModel;
import com.weslide.lovesmallscreen.models.TopLocalProductModel;
import com.weslide.lovesmallscreen.models.bean.GetSellerListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.SerializableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/11/27.
 */
public class IHomeMainFgDataImpl implements IHomeMainFgData {

    /**
     * 首页数据序列化存储文件名 Response<List<RecyclerViewModel>>
     */
    private static final String DATA_SERIALIZE_FILE_NAME = "axp_home_data";

    private Context mContext;
    private DataList<RecyclerViewModel> mDataList = new DataList<>();
    private List<RecyclerViewModel> modelList = new ArrayList<>();
    private int sellerListPage = 1;
    private Handler mHandler = new Handler();

    @Override
    public void getHomeDataList(Context context, IMainFgListenner dataListIFgBaseObjListenner) {
        mContext = context;
        askNetData(context, dataListIFgBaseObjListenner);
    }

    private void askNetData(final Context context, final IMainFgListenner listenner) {
        modelList.clear();
        Request request = new Request();
        RXUtils.request(context, request, "getNewHomePage", new SupportSubscriber<Response<NewHomePageModel>>() {

            @Override
            public void onNext(Response<NewHomePageModel> newHomePageModelResponse) {
                if (newHomePageModelResponse != null && newHomePageModelResponse.getData() != null) {
                    handlerHomeResponse(newHomePageModelResponse, listenner);
                }
            }

            @Override
            public void onError(Throwable e) {
                listenner.onError(e.getMessage());
            }

            @Override
            public void onResponseError(Response response) {
                listenner.onError(response.getMessage());
            }
        });
    }

    public void handlerHomeResponse(Response<NewHomePageModel> newHomePageModelResponse, IMainFgListenner listenner) {
        //轮播图
        List<ImageText> homeTopImages = newHomePageModelResponse.getData().getHomeTopImages();
        if (homeTopImages != null) {
            RecyclerViewModel binnerModel = new RecyclerViewModel();
            binnerModel.setItemType(Constants.HOME_BINNER_TYPE);
            binnerModel.setData(homeTopImages);
            modelList.add(binnerModel);
        }

        //周边店铺状态栏
        TopClassifyModel topClassify = newHomePageModelResponse.getData().getTopClassify();
        if (topClassify != null) {
            RecyclerViewModel nearStoreModel = new RecyclerViewModel();
            nearStoreModel.setItemType(Constants.HOME_NEAR_STORE_TYPE);
            nearStoreModel.setData(topClassify);
            modelList.add(nearStoreModel);
        }

        //省钱购物
        SqgwModel sqgw = newHomePageModelResponse.getData().getSqgw();
        if (sqgw != null) {
            RecyclerViewModel saveMoneyShoppingModel = new RecyclerViewModel();
            saveMoneyShoppingModel.setItemType(Constants.HOME_SAVE_MONEY_SHOPPING_TYPE);
            saveMoneyShoppingModel.setData(sqgw);
            modelList.add(saveMoneyShoppingModel);
        }

        //一县一品
        List<TopLocalModel> toplocal = newHomePageModelResponse.getData().getToplocal();
        if (toplocal != null) {
            TopLocalProductModel topLocalProductModel = new TopLocalProductModel();
            topLocalProductModel.setTopLocalModel(toplocal);
            topLocalProductModel.setTitleImg(newHomePageModelResponse.getData().getFeatureType().getYxypMinPicture().getImage());
            RecyclerViewModel topLocalModel = new RecyclerViewModel();
            topLocalModel.setItemType(Constants.HOME_TOP_LOCAL_TYPE);
            topLocalModel.setData(topLocalProductModel);
            modelList.add(topLocalModel);
        }

        //特产商城
        FeatureTypeModel featureType = newHomePageModelResponse.getData().getFeatureType();
        if (featureType != null) {
            RecyclerViewModel specialMallModel = new RecyclerViewModel();
            specialMallModel.setItemType(Constants.HOME_SPECIAL_MALL_TYPE);
            specialMallModel.setData(featureType);
            modelList.add(specialMallModel);
        }

        //直播
        LivemoduleModel livemodule = newHomePageModelResponse.getData().getLivemodule();
        if (livemodule != null && livemodule.getLives() != null && livemodule.getLives().size() > 0) {
            RecyclerViewModel liveModel = new RecyclerViewModel();
            liveModel.setItemType(Constants.HOME_LIVE_TYPE);
            liveModel.setData(livemodule);
            modelList.add(liveModel);
        }

        //底部展示图
        NfcpModel homeBottomBanner = newHomePageModelResponse.getData().getHomeBottomBanner();
        if (homeBottomBanner != null) {
            RecyclerViewModel bottomImgModel = new RecyclerViewModel();
            bottomImgModel.setItemType(Constants.HOME_BOTTOM_IMG_TYPE);
            bottomImgModel.setData(homeBottomBanner);
            modelList.add(bottomImgModel);
        }

        //处理商家店铺列表
        getHomeSellerList(listenner);
    }

    public void getHomeSellerList(final IMainFgListenner listenner) {
        Request<GetSellerListBean> request = new Request<>();
        GetSellerListBean getSellerListBean = new GetSellerListBean();
        getSellerListBean.setType("HOME");//首页的商家店铺列表类型
        getSellerListBean.setPageIndex(sellerListPage);
        request.setData(getSellerListBean);
        RXUtils.request(mContext, request, "getSellerListForNew", new SupportSubscriber() {

            @Override
            public void onNext(Object o) {
                final Response<SellerList> dataListResponse = (Response<SellerList>) o;
                if (dataListResponse.getData() != null && dataListResponse.getData().getDataList().size() > 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (Seller seller : dataListResponse.getData().getDataList()) {
                                RecyclerViewModel sellerListModel = new RecyclerViewModel();
                                sellerListModel.setItemType(Constants.HOME_NEAR_STORE_LIST_TYPE);
                                sellerListModel.setData(seller);
                                modelList.add(sellerListModel);
                            }
                            mDataList.setDataList(modelList);
                            mDataList.setPageIndex(dataListResponse.getData().getPageIndex());
                            mDataList.setPageSize(dataListResponse.getData().getPageSize());
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listenner.onSuccess(mDataList);
                                }
                            });
                            //序列化存储
                            SerializableUtils.serializableToCacheFile(mContext, mDataList, DATA_SERIALIZE_FILE_NAME);
                        }
                    }).start();
                } else {
                    mDataList.setDataList(modelList);
                    listenner.onSuccess(mDataList);
                }
            }

            @Override
            public void onResponseError(Response response) {
                mDataList.setDataList(modelList);
                listenner.onSuccess(mDataList);
            }

            @Override
            public void onError(Throwable e) {
                mDataList.setDataList(modelList);
                listenner.onSuccess(mDataList);
            }
        });
    }

    @Override
    public void refreshHomeData(IMainFgListenner listenner) {
        sellerListPage = 1;
        askNetData(mContext, listenner);
    }

    @Override
    public void loadMoreData(IMainFgListenner listenner) {
        sellerListPage++;
        getHomeSellerList(listenner);
    }

}
