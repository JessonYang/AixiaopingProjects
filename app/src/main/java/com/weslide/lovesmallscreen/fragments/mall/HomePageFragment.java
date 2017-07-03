package com.weslide.lovesmallscreen.fragments.mall;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.DownloadImageService;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.CityDistrictActivity;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.activitys.MsgHomeActivity;
import com.weslide.lovesmallscreen.activitys.mall.GoodsSearchActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.HomePage;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.SellerList;
import com.weslide.lovesmallscreen.models.Zone;
import com.weslide.lovesmallscreen.models.bean.GetSellerListBean;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateMallMessage;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.network.StatusCode;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.SerializableUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.views.adapters.HomePageAdapter;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xu on 2016/6/3.
 * 商城界面
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener {

    /**
     * 首页数据序列化存储文件名 Response<List<RecyclerViewModel>>
     */
    public static final String DATA_SERIALIZE_FILE_NAME = "MALL_HOME";

    @BindView(R.id.list)
    public SuperRecyclerView mRecyclerView;
    HomePageAdapter mAdapter;
    DataList<RecyclerViewModel> mHomeDataList;

    GetSellerListBean getSellerListReqeust = new GetSellerListBean();
    RecyclerView.OnScrollListener mTitleScrollListener;
    View mView;
    int size = 0;
    GridLayoutManager layoutManager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int totalDy = 0;
    @BindView(R.id.ll_main_mall_title_background)
    LinearLayout llMainMallTitleBackground;

    @BindView(R.id.no_data_ll)
    LinearLayout no_data_ll;

    @BindView(R.id.home_page_msg_iv)
    ImageView msg_iv;

//    @BindView(R.id.home_page_msg_num_tv)
//    TextView home_page_msg_num_tv;

    private LoadingDialog loadingDialog;
    private boolean isErr = true;
    private int count = 0;
    private boolean isSuccess = false;
    private View pwView;
    private LinearLayout msg_ll;
    private LinearLayout scan_ll;
    private LinearLayout share_ll;
    private TextView unread_num_tv;
    private PopupWindow popupWindow;
    private boolean hasNewMsg;
    private String unreadCount;
    private String searchurl;

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mall, container, false);
        ButterKnife.bind(this, mView);

        EventBus.getDefault().register(this);

        hasNewMsg = getContext().getSharedPreferences("newMsgInfo", Context.MODE_PRIVATE).getBoolean("hasNewMsg", false);
        unreadCount = getContext().getSharedPreferences("msg", Context.MODE_PRIVATE).getString("unreadCount", "");
        if (hasNewMsg) {
//            home_page_msg_num_tv.setVisibility(View.VISIBLE);
//            home_page_msg_num_tv.setText(unreadCount);
            msg_iv.setImageResource(R.drawable.sy_xiaoxitishidian);
        } else {
//            home_page_msg_num_tv.setVisibility(View.GONE);
            msg_iv.setImageResource(R.drawable.sy_xialagengduo3);
        }
        Zone district = getSupportApplication().getDaoSession().getZoneDao()
                .loadDistrictByZoneName(ContextParameter.getCurrentLocation().getCity(), ContextParameter.getCurrentLocation().getDistrict());
        ContextParameter.setCurrentZone(district);

        //读取序列化数据
        Object object = SerializableUtils.getObjectByCacheFile(getActivity(), DATA_SERIALIZE_FILE_NAME);
        if (object == null) {
            mHomeDataList = new DataList<>();
            mHomeDataList.setDataList(new ArrayList<>());
        } else {
            mHomeDataList = (DataList<RecyclerViewModel>) object;
        }
        mAdapter = new HomePageAdapter(getActivity(), mHomeDataList, mRecyclerView);
        layoutManager = new GridLayoutManager(getActivity(), 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (mAdapter.getItemViewType(position) == 12) {
                    if (size == 1) {
                        return 4;
                    }
                    return 2;
                } else if (mAdapter.getItemViewType(position) == 14) {
                    return 1;
                }
                return layoutManager.getSpanCount();
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //加载更多
        mRecyclerView.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> {
            L.e("加载下一页");
            getSellerListReqeust.setPageIndex(getSellerListReqeust.getPageIndex() + 1);
            loadSellerList(getSellerListReqeust);
        }, 3);

        //滑动状态改变
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                scrollChange(totalDy);
            }
        });


        L.e(AppUtils.getSign(getActivity(), getActivity().getPackageName()));

        load();
        return mView;
    }


    /**
     * 滑动
     */
    public void scrollChange(int y) {
        if (y > 20 && y / 2 < 255) {
            String transparency = "#" + (y / 2 < 16 ? "0" : "") + Integer.toHexString(y / 2) + "ffffff";
            llMainMallTitleBackground.setBackgroundColor(Color.parseColor(transparency));
        } else if (y <= 20) {
            if (llMainMallTitleBackground == null) {
                //backgroundDrawable = getResources().getDrawable(R.drawable.ic_main_mall_vip_title_background);
            }
            llMainMallTitleBackground.setBackgroundColor(Color.parseColor("#10000000"));
        } else {
            llMainMallTitleBackground.setBackgroundColor(getResources().getColor(R.color.main_color_white));
        }
    }

    public void goTop() {
        layoutManager.scrollToPosition(0);
    }

    public void setTitleOnScrollListener(RecyclerView.OnScrollListener listener) {
        mTitleScrollListener = listener;
    }

    private void load() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        isErr = true;
        loadHomeData();
        tvTitle.setText(ContextParameter.getCurrentZone().getName());
    }

    /**
     * eventBus事件
     *
     * @param event
     */
    @Subscribe
    public void onEvent(UpdateMallMessage event) {
        load();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }

    /**
     * 加载首页数据
     */
    public void loadHomeData() {
//        Log.d("雨落无痕丶", "loadHomeData: sssssss");
        Observable.just(null)
                .filter(new Func1<Object, Boolean>() {  //判断网络
                    @Override
                    public Boolean call(Object o) {
                        return NetworkUtils.isConnected(getActivity());
                    }
                })
                .observeOn(Schedulers.computation())
                .map(new Func1<Object, Response<HomePage>>() {   //请求首页网络数据
                    @Override
                    public Response<HomePage> call(Object o) {

                        try {
                            Request request = new Request();
                            return HTTP.getAPI().getHomePage(HTTP.formatJSONData(request)).execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                })
                .filter(new Func1<Response<HomePage>, Boolean>() {  //验证数据
                    @Override
                    public Boolean call(Response<HomePage> homeResponse) {
                        return StatusCode.validateSuccess(getActivity(), homeResponse);
                    }
                })
                .map(new Func1<Response<HomePage>, DataList<RecyclerViewModel>>() {  //数据转换显示

                    @Override
                    public DataList<RecyclerViewModel> call(Response<HomePage> homeResponse) {
                        return handlerHomeData(homeResponse);
                    }
                })
                .map(new Func1<DataList<RecyclerViewModel>, DataList<RecyclerViewModel>>() {  //序列化存储
                    @Override
                    public DataList<RecyclerViewModel> call(DataList<RecyclerViewModel> listResponse) {
                        SerializableUtils.serializableToCacheFile(getActivity(), listResponse, DATA_SERIALIZE_FILE_NAME);
                        return listResponse;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataList<RecyclerViewModel>>() {
                    private Thread thread;  //数据更新

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d("雨落无痕丶", "onError: rrrrrrr");
                        if (thread == null) {
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (isErr) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                load();
//                                                Log.d("雨落无痕丶", "run: ===========");
                                            }
                                        });
                                        try {
                                            thread.sleep(2500);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        count++;
                                        if (count == 12) {
                                            isErr = false;
                                            if (loadingDialog != null && loadingDialog.isShowing()) {
                                                loadingDialog.dismiss();
                                            }
                                            count = 0;
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    no_data_ll.setVisibility(View.VISIBLE);
                                                    mRecyclerView.setVisibility(View.GONE);
                                                    no_data_ll.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            load();
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                            thread.start();
                        }
//                        Toast.makeText(HomePageFragment.this.getActivity(), "数据加载失败哦!", Toast.LENGTH_SHORT).show();
//                        Log.d("雨落无痕丶", "onError: " + e);
                        L.e("error", "error", e);
                    }

                    @Override
                    public void onNext(DataList<RecyclerViewModel> homeList) {
                        /*if (isSuccess) {
                            return;
                        }*/
                        if (mRecyclerView.getVisibility() == View.GONE) {
                            no_data_ll.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                        setHomeData(mHomeDataList, homeList);
                        getSellerListReqeust.setPageIndex(0);
                        mAdapter.notifyDataSetChanged();
                        getSellerListReqeust.setPageIndex(1);
                        loadSellerList(getSellerListReqeust);
                        loadingDialog.dismiss();
//                        Toast.makeText(HomePageFragment.this.getActivity(), "数据加载完毕!", Toast.LENGTH_SHORT).show();
                        isErr = false;
                        if (thread != null) {
                            thread.stop();
                        }
                        isSuccess = true;
                    }

                });
    }

    public void loadSellerList(final GetSellerListBean getSellerListRequest) {

        Request<GetSellerListBean> request = new Request<>();
        getSellerListRequest.setType("HOME");//首页的商家
        request.setData(getSellerListRequest);
        RXUtils.request(getActivity(), request, "getSellerListForNew", new SupportSubscriber() {

            @Override
            public void onNext(Object o) {
                Response<SellerList> dataListResponse = (Response<SellerList>) o;
                handlerSellerListData(dataListResponse.getData());
                if (getSellerListRequest.getPageIndex() == 1) {
                    //如果没有商品的情况下
                    if (dataListResponse.getData().getDataList() == null || dataListResponse.getData().getDataList().size() == 0) {
//                        removeSellerTitleRecyclerViewModel();
                        Toast.makeText(HomePageFragment.this.getActivity(), "亲!暂无商品哦", Toast.LENGTH_SHORT).show();
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 设置首页数据
     *
     * @param oldData
     * @param newData
     */
    public void setHomeData(DataList<RecyclerViewModel> oldData, DataList<RecyclerViewModel> newData) {
        setResponseData(oldData, newData);
        oldData.setPageSize(0);
        oldData.getDataList().clear();
        oldData.getDataList().addAll(newData.getDataList());
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
     * 将商家列表返回数据处理为RecyclerView能识别并分类的实体
     *
     * @param sellerList
     * @return
     */
    public void handlerSellerListData(SellerList sellerList) {

        List<RecyclerViewModel> recyclerViewModels = new ArrayList<>();

        setResponseData(mHomeDataList, sellerList);

        for (Seller seller : sellerList.getDataList()) {
            RecyclerViewModel sellerRecyclerViewModel = new RecyclerViewModel();
            sellerRecyclerViewModel.setItemType(HomePageAdapter.TYPE_SELLER);
            sellerRecyclerViewModel.setData(seller);
            recyclerViewModels.add(sellerRecyclerViewModel);
        }
        mHomeDataList.getDataList().addAll(recyclerViewModels);

    }

    /*@Override
    public void onResume() {
        super.onResume();
        Zone district = getSupportApplication().getDaoSession().getZoneDao()
                .loadDistrictByZoneName(ContextParameter.getCurrentLocation().getCity(), ContextParameter.getCurrentLocation().getDistrict());
        ContextParameter.setCurrentZone(district);
        load();
    }*/

    /**
     * 将首页返回数据处理为RecyclerView能识别并分类的实体
     *
     * @param home
     * @return
     */
    public DataList<RecyclerViewModel> handlerHomeData(Response<HomePage> home) {
        searchurl = home.getData().getSearchurl();
        DataList<RecyclerViewModel> responseRecyclerViewModel = new DataList<>();

        List<RecyclerViewModel> recyclerViewModels = new ArrayList<>();

        if (home.getData() == null) {
            Toast.makeText(HomePageFragment.this.getActivity(), "亲!没有数据哦", Toast.LENGTH_SHORT).show();
        }

        //头部轮播
        RecyclerViewModel bannerRecyclerViewModel = new RecyclerViewModel();
        bannerRecyclerViewModel.setItemType(HomePageAdapter.TYPE_HEAD_BANNERS);
        bannerRecyclerViewModel.setData(home.getData().getTopImages());
        recyclerViewModels.add(bannerRecyclerViewModel);
        //图文操作
        if (home.getData().getSaveMoney() != null) {
            RecyclerViewModel optionRecyclerViewModel = new RecyclerViewModel();
            optionRecyclerViewModel.setItemType(HomePageAdapter.TYPE_OPTION);
            optionRecyclerViewModel.setData(home.getData());
            recyclerViewModels.add(optionRecyclerViewModel);
        }

        if (home.getData().getCategories() != null && home.getData().getCategories().size() > 0) {
            for (int i = 0; i < home.getData().getCategories().size(); i++) {
                RecyclerViewModel taokeType = new RecyclerViewModel();
                taokeType.setItemType(HomePageAdapter.TYPE_TAOKE_TYPE);
                taokeType.setData(home.getData().getCategories().get(i));
                recyclerViewModels.add(taokeType);
            }
        }
        //图文操作
        RecyclerViewModel mallRecyclerViewModel = new RecyclerViewModel();
        mallRecyclerViewModel.setItemType(HomePageAdapter.TYPE_SHOPPINGMALL);
        mallRecyclerViewModel.setData(home.getData());
        recyclerViewModels.add(mallRecyclerViewModel);

        //书瑶换货会
        if (home.getData().getExchangeAll() != null) {
            RecyclerViewModel changeRecyclerViewModel = new RecyclerViewModel();
            changeRecyclerViewModel.setItemType(HomePageAdapter.TYPE_EXCHANGE);
            changeRecyclerViewModel.setData(home.getData());
            recyclerViewModels.add(changeRecyclerViewModel);
        }

        //活动来袭
        if (home.getData().getActivites() != null) {
            RecyclerViewModel activitiesRecyclerViewModel = new RecyclerViewModel();
            activitiesRecyclerViewModel.setItemType(HomePageAdapter.TYPE_ACTIVITIES_TYPE);
            activitiesRecyclerViewModel.setData(home.getData());
            recyclerViewModels.add(activitiesRecyclerViewModel);
        }

        if (home.getData().getLive().getHeader() != null) {
            //直播标题
            RecyclerViewModel liveTitleRecyclerViewModel = new RecyclerViewModel();
            liveTitleRecyclerViewModel.setItemType(HomePageAdapter.TYPE_LIVE_TITLE);
            liveTitleRecyclerViewModel.setData(home.getData());
            recyclerViewModels.add(liveTitleRecyclerViewModel);

        }

/*         if(home.getData().getTaokeTitle() != null){
             RecyclerViewModel taokeTitle = new RecyclerViewModel();
             taokeTitle.setItemType(HomePageAdapter.TYPE_TAOKE_TITLE);
             taokeTitle.setData(home.getData().getTaokeTitle());
             recyclerViewModels.add(taokeTitle);
         }*/
/*

        //图文操作
        RecyclerViewModel optionRecyclerViewModel = new RecyclerViewModel();
        optionRecyclerViewModel.setItemType(HomePageAdapter.TYPE_OPTION);
        optionRecyclerViewModel.setData(home.getData());
        recyclerViewModels.add(optionRecyclerViewModel);

        if(home.getData().getTaokeType() !=null && home.getData().getTaokeType().size() > 0){
            int size;
            if(home.getData().getTaokeType().size() > 4){
                size = 4;
            }else{
                size = home.getData().getTaokeType().size();
            }
            for(int i = 0; i < size; i++ ){
                RecyclerViewModel taokeType  = new RecyclerViewModel();
                taokeType.setItemType(HomePageAdapter.TYPE_TAOKE_TYPE);
                taokeType.setData(home.getData().getTaokeType().get(i));
                recyclerViewModels.add(taokeType);
            }
        }


        //秒杀
        if (home.getData().getSecondKills() != null && home.getData().getSecondKills().size() != 0) {
            RecyclerViewModel secondKillRecyclerViewModel = new RecyclerViewModel();
            secondKillRecyclerViewModel.setItemType(HomePageAdapter.TYPE_SECOND_KILL);
            secondKillRecyclerViewModel.setData(home.getData().getSecondKills());
            recyclerViewModels.add(secondKillRecyclerViewModel);
        }
        if(home.getData().getLive() != null && home.getData().getLive().size() != 0) {
            //直播标题
            RecyclerViewModel liveTitleRecyclerViewModel = new RecyclerViewModel();
            liveTitleRecyclerViewModel.setItemType(HomePageAdapter.TYPE_LIVE_TITLE);
            recyclerViewModels.add(liveTitleRecyclerViewModel);
        }

        //直播列表
        if (home.getData().getLive() != null && home.getData().getLive().size() != 0) {
            size = home.getData().getLive().size();
            for (Live live : home.getData().getLive()) {
                RecyclerViewModel liveRecyclerViewModel = new RecyclerViewModel();
                liveRecyclerViewModel.setItemType(HomePageAdapter.TYPE_LIVE_LIST);
                liveRecyclerViewModel.setData(live);
                recyclerViewModels.add(liveRecyclerViewModel);
            }
        }

        //积分标签分类
        if (home.getData().getScoreGoodsClassifys() != null && home.getData().getScoreGoodsClassifys().size() != 0) {
            RecyclerViewModel scoreGoodsClassifysRecyclerViewModel = new RecyclerViewModel();
            scoreGoodsClassifysRecyclerViewModel.setItemType(HomePageAdapter.TYPE_SCORE_GOODS_CLASSIFYS);
            scoreGoodsClassifysRecyclerViewModel.setData(home.getData().getScoreGoodsClassifys());
            recyclerViewModels.add(scoreGoodsClassifysRecyclerViewModel);
        }

*/

        //周边店铺标题
        RecyclerViewModel sellerTitleRecyclerViewModel = new RecyclerViewModel();
        sellerTitleRecyclerViewModel.setItemType(HomePageAdapter.TYPE_SELLER_TITLE);
        recyclerViewModels.add(sellerTitleRecyclerViewModel);

        responseRecyclerViewModel.setDataList(recyclerViewModels);
        return responseRecyclerViewModel;
    }

    public void removeSellerTitleRecyclerViewModel() {
        for (int i = 0; i < mAdapter.getData().getDataList().size(); i++) {
            if (mAdapter.getData().getDataList().get(i).getItemType() == HomePageAdapter.TYPE_SELLER_TITLE) {
                mAdapter.getData().getDataList().remove(i);
                return;
            }
        }
    }

    public void removeLiveTitleRecyclerViewModel() {
        for (int i = 0; i < mAdapter.getData().getDataList().size(); i++) {
            if (mAdapter.getData().getDataList().get(i).getItemType() == HomePageAdapter.TYPE_LIVE_TITLE) {
                mAdapter.getData().getDataList().remove(i);
                return;
            }
        }
    }

    @OnClick({R.id.rl_main_mall_title_content, R.id.et_search, R.id.home_page_msg_iv})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rl_main_mall_title_content:

                AppUtils.toActivity(getActivity(), CityDistrictActivity.class);
                break;
            case R.id.et_search:
                Bundle bundle = new Bundle();
                bundle.putString("searchUrl", searchurl);
                AppUtils.toActivity(getActivity(), GoodsSearchActivity.class, bundle);
                break;

            case R.id.home_page_msg_iv:
                if (pwView == null) {
                    pwView = LayoutInflater.from(getActivity()).inflate(R.layout.home_page_msg_pup_item, null);
                    msg_ll = ((LinearLayout) pwView.findViewById(R.id.home_page_msg_pup_item_msg));
                    scan_ll = ((LinearLayout) pwView.findViewById(R.id.home_page_msg_pup_item_scan));
                    share_ll = ((LinearLayout) pwView.findViewById(R.id.home_page_msg_pup_item_share));
                    unread_num_tv = ((TextView) pwView.findViewById(R.id.home_page_msg_pup_item_num_tv));
                }
                if (hasNewMsg && unreadCount != null && unreadCount.length() != 0) {
                    unread_num_tv.setVisibility(View.VISIBLE);
                    unread_num_tv.setText(unreadCount);
                } else {
                    unread_num_tv.setVisibility(View.GONE);
                }
                popupWindow = new PopupWindow(pwView, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 115, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics()));
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAsDropDown(msg_iv, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -94, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()));
                changeScreenBg();
                msg_ll.setOnClickListener(this);
                scan_ll.setOnClickListener(this);
                share_ll.setOnClickListener(this);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        restoreScreenBg();
                    }
                });
                break;
            case R.id.home_page_msg_pup_item_msg:
//                Toast.makeText(HomePageFragment.this.getActivity(), "消息", Toast.LENGTH_SHORT).show();
                if (ContextParameter.isLogin()) {
                    AppUtils.toActivity(getActivity(), MsgHomeActivity.class);
                    getContext().getSharedPreferences("newMsgInfo", Context.MODE_PRIVATE).edit().putBoolean("hasNewMsg", false).commit();
                    /*if (home_page_msg_num_tv.getVisibility() == View.VISIBLE) {
                        home_page_msg_num_tv.setVisibility(View.GONE);
                    }*/
                    msg_iv.setImageResource(R.drawable.sy_xialagengduo3);
                    if (unread_num_tv.getVisibility() == View.VISIBLE) {
                        unread_num_tv.setVisibility(View.GONE);
                    }
                } else {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                }
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.home_page_msg_pup_item_scan:
                QRCodeUtil.scan(getActivity());
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.home_page_msg_pup_item_share:
                ShareContent home = ContextParameter.getClientConfig().getHomeShareContent();
                String url = home.getTargetUrl();
                String username = ContextParameter.getUserInfo().getUsername();
                String userId = ContextParameter.getUserInfo().getUserId();
                String sellerId = ContextParameter.getUserInfo().getSellerId();
                String adminuserId = ContextParameter.getUserInfo().getAdminuserId();
                String headimage = ContextParameter.getUserInfo().getHeadimage();
                String phone = ContextParameter.getUserInfo().getPhone();
                String inviteCode = ContextParameter.getUserInfo().getInviteCode();
                if (username == null) {
                    username = "";
                }
                if (userId == null) {
                    userId = "";
                }
                if (sellerId == null) {
                    sellerId = "";
                }
                if (adminuserId == null) {
                    adminuserId = "";
                }
                if (headimage == null) {
                    headimage = "";
                }
                if (phone == null) {
                    phone = "";
                }
                if (inviteCode == null) {
                    inviteCode = "";
                }
                String targetUrl = url + "?userId=" + userId + "&appVersion=" + AppUtils.getVersionCode(getActivity()) + "&zoneId=" + ContextParameter.getCurrentZone().getZoneId() + "&sellerId=" + sellerId +
                        "&adminuserId=" + adminuserId + "&img=" + headimage + "&name=" + username + "&phone=" + phone + "&code=" + inviteCode;
                ShareUtils.share(getActivity(), home.getTitle(),
                        home.getIconUrl(),
                        targetUrl,
                        home.getContent());
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
        }

    }

    private void changeScreenBg() {
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = (float) 0.6;
        getActivity().getWindow().setAttributes(params);
    }

    private void restoreScreenBg() {
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = (float) 1;
        getActivity().getWindow().setAttributes(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*if (HomePageAdapter.mThread != null) {
            HomePageAdapter.mThread.stop();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent(getActivity(), DownloadImageService.class);
        intent.putExtra(DownloadImageService.KEY_FRESHEN, true);
        getActivity().startService(intent);
    }
}