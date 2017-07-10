package com.weslide.lovesmallscreen.view_yy.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.DownloadImageService;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.activitys.CityDistrictActivity;
import com.weslide.lovesmallscreen.activitys.HomeActivity;
import com.weslide.lovesmallscreen.activitys.LiveDetails;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.activitys.MsgHomeActivity;
import com.weslide.lovesmallscreen.activitys.ScoreExchangeActivity;
import com.weslide.lovesmallscreen.activitys.SecondKillActivity;
import com.weslide.lovesmallscreen.activitys.TaoKeActivity;
import com.weslide.lovesmallscreen.activitys.mall.GoodsSearchActivity_New;
import com.weslide.lovesmallscreen.activitys.mall.SellerListActivity_new;
import com.weslide.lovesmallscreen.activitys.mall.SpecialLocalProductActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.FeatureTypeModel;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.LivemoduleModel;
import com.weslide.lovesmallscreen.models.LivesModel;
import com.weslide.lovesmallscreen.models.NewHomePageModel;
import com.weslide.lovesmallscreen.models.NfcpModel;
import com.weslide.lovesmallscreen.models.SellerList;
import com.weslide.lovesmallscreen.models.SqgwModel;
import com.weslide.lovesmallscreen.models.TopClassifyModel;
import com.weslide.lovesmallscreen.models.TopLocalModel;
import com.weslide.lovesmallscreen.models.Zone;
import com.weslide.lovesmallscreen.models.bean.GetSellerListBean;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateMallMessage;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.SerializableUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.view_yy.activity.SaveMoneyBrandActivity;
import com.weslide.lovesmallscreen.view_yy.activity.SaveMoneyHomeActivity;
import com.weslide.lovesmallscreen.view_yy.activity.TaoBaoActivity;
import com.weslide.lovesmallscreen.view_yy.adapter.CountyGoodRcLvAdapter;
import com.weslide.lovesmallscreen.view_yy.adapter.HomeSellerListLvAdapter;
import com.weslide.lovesmallscreen.view_yy.adapter.LiveGvAdapter;
import com.weslide.lovesmallscreen.view_yy.customview.GlideImageLoader;
import com.weslide.lovesmallscreen.view_yy.customview.MyScrollView;
import com.weslide.lovesmallscreen.view_yy.customview.NestedListView;
import com.weslide.lovesmallscreen.view_yy.customview.ObservableScrollView;
import com.weslide.lovesmallscreen.views.adapters.SellerListAdapter;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/3.
 * 商城界面
 */
public class HomePageFragment_New extends BaseFragment implements View.OnClickListener, MyScrollView.OnScrollListener, ObservableScrollView.OnObservableScrollViewListener {

    /**
     * 首页数据序列化存储文件名 Response<List<RecyclerViewModel>>
     */
    public static final String NEW_DATA_SERIALIZE_FILE_NAME = "NEW_MALL_HOME";

    @BindView(R.id.new_home_banner)
    public Banner banner;
    View mView;
    int size = 0;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int totalDy = 0;
    @BindView(R.id.ll_main_mall_title_background)
    LinearLayout llMainMallTitleBackground;
    @BindView(R.id.no_data_ll)
    LinearLayout no_data_ll;
    @BindView(R.id.home_page_msg_iv)
    ImageView msg_iv;
    @BindView(R.id.shop_around_iv)
    ImageView shop_around_iv;
    @BindView(R.id.time_limite_iv)
    ImageView time_limite_iv;
    @BindView(R.id.credit_exchange_iv)
    ImageView credit_exchange_iv;
    @BindView(R.id.sign_up_attend_iv)
    ImageView sign_up_attend_iv;
    @BindView(R.id.save_money_title_rll)
    ImageView save_money_title_rll;
    @BindView(R.id.brand_zone_iv)
    ImageView brand_zone_iv;
    @BindView(R.id.nine_to_nine_baoyou_iv)
    ImageView nine_to_nine_baoyou_iv;
    //    @BindView(R.id.title_tv)
//    TextView title_tv;
//    @BindView(R.id.second_title_tv)
//    TextView second_title_tv;
//    @BindView(R.id.title_tv2)
//    TextView title_tv2;
//    @BindView(R.id.second_title_tv2)
//    TextView second_title_tv2;
    @BindView(R.id.sqgw_second_classify_iv)
    ImageView sqgw_second_classify_iv;
    @BindView(R.id.sqgw_second_classify_iv2)
    ImageView sqgw_second_classify_iv2;
    @BindView(R.id.yhq_banner_iv)
    ImageView yhq_banner_iv;
    @BindView(R.id.clothing_iv)
    ImageView clothing_iv;
    @BindView(R.id.clothing_tv)
    TextView clothing_tv;
    //    @BindView(R.id.clothing_second_tv)
//    TextView clothing_second_tv;
    @BindView(R.id.muying_iv)
    ImageView muying_iv;
    @BindView(R.id.muying_tv)
    TextView muying_tv;
    //    @BindView(R.id.muying_second_tv)
//    TextView muying_second_tv;
    @BindView(R.id.home_dress_iv)
    ImageView home_dress_iv;
    @BindView(R.id.home_dress_tv)
    TextView home_dress_tv;
    //    @BindView(R.id.home_dress_second_tv)
//    TextView home_dress_second_tv;
    @BindView(R.id.delicious_good_iv)
    ImageView delicious_good_iv;
    @BindView(R.id.delicious_good_tv)
    TextView delicious_good_tv;
    //    @BindView(R.id.delicious_good_second_tv)
//    TextView delicious_good_second_tv;
    @BindView(R.id.cosmetic_iv)
    ImageView cosmetic_iv;
    @BindView(R.id.cosmetic_tv)
    TextView cosmetic_tv;
    //    @BindView(R.id.cosmetic_second_tv)
//    TextView cosmetic_second_tv;
    @BindView(R.id.sports_iv)
    ImageView sports_iv;
    @BindView(R.id.sports_tv)
    TextView sports_tv;
    //    @BindView(R.id.sports_second_tv)
//    TextView sports_second_tv;
    @BindView(R.id.literary_form_iv)
    ImageView literary_form_iv;
    @BindView(R.id.literary_form_tv)
    TextView literary_form_tv;
    //    @BindView(R.id.literary_form_second_tv)
//    TextView literary_form_second_tv;
    @BindView(R.id.medical_health_iv)
    ImageView medical_health_iv;
    @BindView(R.id.medical_health_tv)
    TextView medical_health_tv;
    //    @BindView(R.id.medical_health_second_tv)
//    TextView medical_health_second_tv;
    @BindView(R.id.title_rll)
    ImageView title_rll;
    @BindView(R.id.county_product_rclv)
    RecyclerView county_product_rclv;
    @BindView(R.id.rexiaozhuanlan_iv)
    ImageView rexiaozhuanlan_iv;
    @BindView(R.id.county_banner_iv)
    ImageView county_banner_iv;
    @BindView(R.id.nfcp_iv)
    ImageView nfcp_iv;
    @BindView(R.id.to_friend_iv)
    ImageView to_friend_iv;
    @BindView(R.id.eat_must_iv)
    ImageView eat_must_iv;
    @BindView(R.id.dj_fruit_iv)
    ImageView dj_fruit_iv;
    @BindView(R.id.tea_iv)
    ImageView tea_iv;
    @BindView(R.id.exchange_iv)
    ImageView exchange_iv;
    @BindView(R.id.small_live_title)
    ImageView small_live_title;
    @BindView(R.id.page_cashmall_index_location)
    ImageView page_cashmall_index_location;
    @BindView(R.id.new_home_page_small_live_gv)
    GridView small_live_gv;
    @BindView(R.id.my_scroll_view)
    ObservableScrollView scroll_view;
    @BindView(R.id.small_live_view)
    View small_live_view;
    @BindView(R.id.zbdp_rll)
    RelativeLayout zbdp_rll;
    @BindView(R.id.divider_view)
    View divider_view;
    //    @BindView(R.id.list)
//    SuperRecyclerView list;
    @BindView(R.id.list)
    NestedListView list;

    SellerList mSellerList = new SellerList();
    GetSellerListBean getSellerListBean = new GetSellerListBean();
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
    private List<String> banner_imgs = new ArrayList<>();
    private FeatureTypeModel featureType;
    private String storesTotalPage;
    private TopClassifyModel topClassify;
    private List<TopLocalModel> toplocal;
    private LivemoduleModel liveModul;
    private SqgwModel saveMoney;
    private List<ImageText> homeTopImages;
    private NfcpModel exchangeModel;
    private ArrayList<TopLocalModel> rclvList = new ArrayList<>();
    private CountyGoodRcLvAdapter rclvAdapter;
    private String typeId;
    private Response<NewHomePageModel> mResponse;
    private LiveGvAdapter liveGvAdapter;
    private List<LivesModel> gvList = new ArrayList<>();
    private int mHeight;
    private SellerListAdapter mAdapter;
    private HomeSellerListLvAdapter homeSellerListLvAdapter;
    public static String pid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mall_new, container, false);
        ButterKnife.bind(this, mView);

        EventBus.getDefault().register(this);

        hasNewMsg = getContext().getSharedPreferences("newMsgInfo", Context.MODE_PRIVATE).getBoolean("hasNewMsg", false);
        unreadCount = getContext().getSharedPreferences("msg", Context.MODE_PRIVATE).getString("unreadCount", "");
        if (hasNewMsg) {
            msg_iv.setImageResource(R.drawable.sy_xiaoxitishidianbaise);
        } else {
            msg_iv.setImageResource(R.drawable.sy_xialagengduo3);
        }
        Zone district = getSupportApplication().getDaoSession().getZoneDao()
                .loadDistrictByZoneName(ContextParameter.getCurrentLocation().getCity(), ContextParameter.getCurrentLocation().getDistrict());
        ContextParameter.setCurrentZone(district);
//        StatusbarUtils.enableTranslucentStatusbar(getActivity());
//        llMainMallTitleBackground.setBackgroundColor(Color.argb(0, 48, 63, 159));
        llMainMallTitleBackground.setBackgroundResource(R.drawable.dclbj);
        rclvAdapter = new CountyGoodRcLvAdapter(rclvList, getActivity());
        county_product_rclv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        county_product_rclv.setAdapter(rclvAdapter);
        liveGvAdapter = new LiveGvAdapter(getActivity(), gvList);
        small_live_gv.setAdapter(liveGvAdapter);
        small_live_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("liveId", gvList.get(i).getLiveid());
                AppUtils.toActivity(getActivity(), LiveDetails.class, bundle);
            }
        });
        //滑动状态改变
        ViewTreeObserver viewTreeObserver = banner.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                banner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = banner.getHeight() / 2;//这里取的高度应该为图片的高度-标题栏
                //注册滑动监听
                scroll_view.setOnObservableScrollViewListener(HomePageFragment_New.this);
            }
        });

        //读取序列化数据
        Object object = SerializableUtils.getObjectByCacheFile(getActivity(), NEW_DATA_SERIALIZE_FILE_NAME);
        if (object != null) {
            mResponse = (Response<NewHomePageModel>) object;
            if (mResponse.getData() != null) {
                initData(mResponse);
                showData();
            }
        }

        load();
        loadSellerList();
        loadData();
        return mView;
    }

    private void askNetData() {
        Request request = new Request();
        RXUtils.request(getActivity(), request, "getNewHomePage", new SupportSubscriber<Response<NewHomePageModel>>() {

            @Override
            public void onNext(Response<NewHomePageModel> newHomePageModelResponse) {
                if (newHomePageModelResponse != null && newHomePageModelResponse.getData() != null) {
                    pid = newHomePageModelResponse.getData().getPid();
                    mResponse = newHomePageModelResponse;
                    initData(mResponse);
                    showData();
                    SerializableUtils.serializableToCacheFile(getActivity(), newHomePageModelResponse, NEW_DATA_SERIALIZE_FILE_NAME);
                }
                /*if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }*/
//                Log.d("雨落无痕丶", "onNext: sssssssssss");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("雨落无痕丶", "onError: rrrrrr:"+e.getMessage());
                loadingDialog.dismiss();
            }

            @Override
            public void onResponseError(Response response) {
                Log.d("雨落无痕丶", "onResponseError: eeeee"+response.getMessage());
                loadingDialog.dismiss();
            }
        });
    }

    private void showData() {
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(banner_imgs);
        banner.setDelayTime(2000);
        banner.startAutoPlay();
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                URIResolve.resolve(getActivity(), homeTopImages.get(position).getUri());
            }
        });
        Glide.with(getActivity()).load(topClassify.getZbdp().getImage()).into(shop_around_iv);
        Glide.with(getActivity()).load(topClassify.getXsms().getImage()).into(time_limite_iv);
        Glide.with(getActivity()).load(topClassify.getJfdh().getImage()).into(credit_exchange_iv);
        Glide.with(getActivity()).load(topClassify.getChbm().getImage()).into(sign_up_attend_iv);
        Glide.with(getActivity()).load(saveMoney.getSqgwTp().getImage()).placeholder(R.drawable.sqgw_title).into(save_money_title_rll);
        Glide.with(getActivity()).load(saveMoney.getPpth().getImage()).into(brand_zone_iv);
        Glide.with(getActivity()).load(saveMoney.getNine2nine().getImage()).into(nine_to_nine_baoyou_iv);
//        title_tv.setText(saveMoney.getSqgwCategories().get(0).getTitle());
//        second_title_tv.setText(saveMoney.getSqgwCategories().get(0).getSubTitle());
        Glide.with(getActivity()).load(saveMoney.getSqgwCategories().get(0).getImage()).into(sqgw_second_classify_iv);
//        title_tv2.setText(saveMoney.getSqgwCategories().get(1).getTitle());
//        second_title_tv2.setText(saveMoney.getSqgwCategories().get(1).getSubTitle());
        Glide.with(getActivity()).load(saveMoney.getSqgwCategories().get(1).getImage()).into(sqgw_second_classify_iv2);
        Glide.with(getActivity()).load(saveMoney.getYhj().getImage()).into(yhq_banner_iv);
        Glide.with(getActivity()).load(saveMoney.getSqgwCategories().get(2).getImage()).into(clothing_iv);
        clothing_tv.setText(saveMoney.getSqgwCategories().get(2).getTitle());
//        clothing_second_tv.setText(saveMoney.getSqgwCategories().get(2).getSubTitle());
        Glide.with(getActivity()).load(saveMoney.getSqgwCategories().get(3).getImage()).into(muying_iv);
        muying_tv.setText(saveMoney.getSqgwCategories().get(3).getTitle());
//        muying_second_tv.setText(saveMoney.getSqgwCategories().get(3).getSubTitle());
        Glide.with(getActivity()).load(saveMoney.getSqgwCategories().get(4).getImage()).into(home_dress_iv);
        home_dress_tv.setText(saveMoney.getSqgwCategories().get(4).getTitle());
//        home_dress_second_tv.setText(saveMoney.getSqgwCategories().get(4).getSubTitle());
        Glide.with(getActivity()).load(saveMoney.getSqgwCategories().get(5).getImage()).into(delicious_good_iv);
        delicious_good_tv.setText(saveMoney.getSqgwCategories().get(5).getTitle());
//        delicious_good_second_tv.setText(saveMoney.getSqgwCategories().get(5).getSubTitle());
        Glide.with(getActivity()).load(saveMoney.getSqgwCategories().get(6).getImage()).into(cosmetic_iv);
        cosmetic_tv.setText(saveMoney.getSqgwCategories().get(6).getTitle());
//        cosmetic_second_tv.setText(saveMoney.getSqgwCategories().get(6).getSubTitle());
        Glide.with(getActivity()).load(saveMoney.getSqgwCategories().get(7).getImage()).into(sports_iv);
        sports_tv.setText(saveMoney.getSqgwCategories().get(7).getTitle());
//        sports_second_tv.setText(saveMoney.getSqgwCategories().get(7).getSubTitle());
        Glide.with(getActivity()).load(saveMoney.getSqgwCategories().get(8).getImage()).into(literary_form_iv);
        literary_form_tv.setText(saveMoney.getSqgwCategories().get(8).getTitle());
//        literary_form_second_tv.setText(saveMoney.getSqgwCategories().get(8).getSubTitle());
        Glide.with(getActivity()).load(saveMoney.getSqgwCategories().get(9).getImage()).into(medical_health_iv);
        medical_health_tv.setText(saveMoney.getSqgwCategories().get(9).getTitle());
//        medical_health_second_tv.setText(saveMoney.getSqgwCategories().get(9).getSubTitle());

        Glide.with(getActivity()).load(featureType.getYxypMinPicture().getImage()).into(title_rll);
        Glide.with(getActivity()).load(featureType.getYxypMaxPicture().getImage()).into(county_banner_iv);
        Glide.with(getActivity()).load(featureType.getNfcp().getImage()).into(nfcp_iv);
        Glide.with(getActivity()).load(featureType.getGift().getImage()).into(to_friend_iv);
        Glide.with(getActivity()).load(featureType.getCate().getImage()).into(eat_must_iv);
        Glide.with(getActivity()).load(featureType.getFruit().getImage()).into(dj_fruit_iv);
        Glide.with(getActivity()).load(featureType.getTea().getImage()).into(tea_iv);
        if (liveModul.getLives() == null) {
            small_live_view.setVisibility(View.GONE);
        } else {
            if (liveModul.getLives() != null && liveModul.getLives().size() > 0) {
                small_live_view.setVisibility(View.VISIBLE);
//                Glide.with(getActivity()).load(liveModul.getLiveHeader().getImage()).into(small_live_title);
                gvList.clear();
                gvList.addAll(liveModul.getLives());
                liveGvAdapter.notifyDataSetChanged();
            }
        }
        Glide.with(getActivity()).load(exchangeModel.getImage()).into(exchange_iv);
        rclvList.clear();
        rclvList.addAll(toplocal);
        rclvAdapter.notifyDataSetChanged();
    }

    private void initData(Response<NewHomePageModel> newHomePageModelResponse) {
        homeTopImages = newHomePageModelResponse.getData().getHomeTopImages();
        featureType = newHomePageModelResponse.getData().getFeatureType();
        liveModul = newHomePageModelResponse.getData().getLivemodule();
        saveMoney = newHomePageModelResponse.getData().getSqgw();
        searchurl = newHomePageModelResponse.getData().getSearchurl();
        storesTotalPage = newHomePageModelResponse.getData().getStoresTotalPage();
        topClassify = newHomePageModelResponse.getData().getTopClassify();
        toplocal = newHomePageModelResponse.getData().getToplocal();
        exchangeModel = newHomePageModelResponse.getData().getHomeBottomBanner();
        banner_imgs.clear();
        for (ImageText topImage : homeTopImages) {
            banner_imgs.add(topImage.getImage());
        }
    }

    /**
     * 滑动
     */
    public void scrollChange(ScrollView scrollView, int t) {
        if (t <= 0) {
            //顶部图处于最顶部，标题栏透明
            llMainMallTitleBackground.setBackgroundColor(Color.argb(0, 48, 63, 159));
        } else if (t > 0 && t < mHeight) {
            //滑动过程中，渐变
            float scale = (float) t / mHeight;//算出滑动距离比例
            float alpha = (255 * scale);//得到透明度
            llMainMallTitleBackground.setBackgroundColor(Color.argb((int) alpha, 48, 63, 159));
        } else {
            //过顶部图区域，标题栏定色
            llMainMallTitleBackground.setBackgroundColor(Color.argb(255, 48, 63, 159));
        }

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
        loadData();
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
        askNetData();
    }

    @OnClick({R.id.rl_main_mall_title_content, R.id.et_search, R.id.home_page_msg_iv, R.id.shop_around_iv, R.id.sign_up_attend_iv, R.id.time_limite_iv, R.id.credit_exchange_iv, R.id.save_money_title_rll, R.id.brand_zone_iv,
            R.id.nine_to_nine_baoyou_iv, R.id.sqgw_media_rll, R.id.sqgw_shoes_rll, R.id.yhq_banner_iv, R.id.clothing_ll, R.id.muying_ll, R.id.home_dress_ll, R.id.delicious_good_ll, R.id.medical_health_ll, R.id.wtcp_ll, R.id.sports_ll,
            R.id.title_rll, R.id.hzp_ll, R.id.to_friend_iv, R.id.nfcp_iv, R.id.county_banner_iv, R.id.eat_must_iv, R.id.dj_fruit_iv, R.id.tea_iv, R.id.small_live_title, R.id.exchange_iv,R.id.zbdp_rll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_main_mall_title_content:
                AppUtils.toActivity(getActivity(), CityDistrictActivity.class);
                break;

            case R.id.et_search:
                Bundle bundle = new Bundle();
//                bundle.putString("searchUrl", searchurl);
                bundle.putString("title", "");
                AppUtils.toActivity(getActivity(), GoodsSearchActivity_New.class, bundle);
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
                if (ContextParameter.isLogin()) {
                    AppUtils.toActivity(getActivity(), MsgHomeActivity.class);
                    getContext().getSharedPreferences("newMsgInfo", Context.MODE_PRIVATE).edit().putBoolean("hasNewMsg", false).commit();
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

            case R.id.county_banner_iv:
            case R.id.title_rll:
                AppUtils.toActivity(getActivity(), SpecialLocalProductActivity.class);
                break;

            case R.id.exchange_iv:
                String exchangeUri = exchangeModel.getUri();
                if (exchangeUri != null && exchangeUri != "") {
                    if (ContextParameter.isLogin()) {
                        Intent intent = new Intent(getActivity(), TaoBaoActivity.class);
                        intent.putExtra("URL", exchangeUri);
                        getActivity().startActivity(intent);
                        Log.d("雨落无痕丶", "onClick: url:" + exchangeUri);
                    } else {
                        AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                        if (HomeActivity.activity != null) {
                            HomeActivity.activity.finish();
                        }
                    }
                }
                break;

            case R.id.yhq_banner_iv:
                if (saveMoney.getYhj().getUri() != null && saveMoney.getYhj().getUri().length()>0 && saveMoney.getYhj().getUri().startsWith("http")) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(TaoKeActivity.KEY_LOAD_URL,saveMoney.getYhj().getUri());
                    AppUtils.toActivity(getActivity(),TaoKeActivity.class,bundle1);
                }else {
                    Bundle saveMoneyBundle = new Bundle();
                    saveMoneyBundle.putString("toolbarType", "省钱购物");
                    saveMoneyBundle.putString("searchValue", "");
                    saveMoneyBundle.putString("cid", "-1");
                    AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, saveMoneyBundle);
                }
                break;
            case R.id.save_money_title_rll:
                Bundle saveMoneyBundle = new Bundle();
                saveMoneyBundle.putString("toolbarType", "省钱购物");
                saveMoneyBundle.putString("searchValue", "");
                saveMoneyBundle.putString("cid", "-1");
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, saveMoneyBundle);
                break;

            case R.id.nine_to_nine_baoyou_iv:
                Bundle nineToNineBundle = new Bundle();
                nineToNineBundle.putString("toolbarType", "九块九");
                nineToNineBundle.putString("searchValue", "");
                nineToNineBundle.putString("cid", "-1");
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, nineToNineBundle);
                break;

            case R.id.brand_zone_iv:
                AppUtils.toActivity(getActivity(), SaveMoneyBrandActivity.class);
                break;

            case R.id.sqgw_media_rll:
                Bundle bundle1 = new Bundle();
                bundle1.putString("toolbarType", "省钱购物");
                bundle1.putString("searchValue", "");
                bundle1.putString("cid", saveMoney.getSqgwCategories().get(0).getCid());
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, bundle1);
                break;

            case R.id.sqgw_shoes_rll:
                Bundle bundle2 = new Bundle();
                bundle2.putString("toolbarType", "省钱购物");
                bundle2.putString("searchValue", "");
                bundle2.putString("cid", saveMoney.getSqgwCategories().get(1).getCid());
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, bundle2);
                break;

            case R.id.clothing_ll:
                Bundle bundle3 = new Bundle();
                bundle3.putString("toolbarType", "省钱购物");
                bundle3.putString("searchValue", "");
                bundle3.putString("cid", saveMoney.getSqgwCategories().get(2).getCid());
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, bundle3);
                break;

            case R.id.muying_ll:
                Bundle bundle4 = new Bundle();
                bundle4.putString("toolbarType", "省钱购物");
                bundle4.putString("searchValue", "");
                bundle4.putString("cid", saveMoney.getSqgwCategories().get(3).getCid());
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, bundle4);
                break;

            case R.id.home_dress_ll:
                Bundle bundle5 = new Bundle();
                bundle5.putString("toolbarType", "省钱购物");
                bundle5.putString("searchValue", "");
                bundle5.putString("cid", saveMoney.getSqgwCategories().get(4).getCid());
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, bundle5);
                break;

            case R.id.delicious_good_ll:
                Bundle bundle6 = new Bundle();
                bundle6.putString("toolbarType", "省钱购物");
                bundle6.putString("searchValue", "");
                bundle6.putString("cid", saveMoney.getSqgwCategories().get(5).getCid());
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, bundle6);
                break;

            case R.id.hzp_ll:
                Bundle bundle7 = new Bundle();
                bundle7.putString("toolbarType", "省钱购物");
                bundle7.putString("searchValue", "");
                bundle7.putString("cid", saveMoney.getSqgwCategories().get(6).getCid());
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, bundle7);
                break;

            case R.id.sports_ll:
                Bundle bundle8 = new Bundle();
                bundle8.putString("toolbarType", "省钱购物");
                bundle8.putString("searchValue", "");
                bundle8.putString("cid", saveMoney.getSqgwCategories().get(7).getCid());
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, bundle8);
                break;

            case R.id.wtcp_ll:
                Bundle bundle9 = new Bundle();
                bundle9.putString("toolbarType", "省钱购物");
                bundle9.putString("searchValue", "");
                bundle9.putString("cid", saveMoney.getSqgwCategories().get(8).getCid());
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, bundle9);
                break;

            case R.id.medical_health_ll:
                Bundle bundle10 = new Bundle();
                bundle10.putString("toolbarType", "省钱购物");
                bundle10.putString("searchValue", "");
                bundle10.putString("cid", saveMoney.getSqgwCategories().get(9).getCid());
                AppUtils.toActivity(getActivity(), SaveMoneyHomeActivity.class, bundle10);
                break;

            case R.id.nfcp_iv:
                typeId = featureType.getNfcp().getTypeId();
                Bundle countyBundle1 = new Bundle();
                countyBundle1.putString("typeId", typeId);
                countyBundle1.putInt("where", 0);
                AppUtils.toActivity(getActivity(), SpecialLocalProductActivity.class, countyBundle1);
                break;
            case R.id.to_friend_iv:
                typeId = featureType.getGift().getTypeId();
                Bundle countyBundle2 = new Bundle();
                countyBundle2.putString("typeId", typeId);
                countyBundle2.putInt("where", 0);
                AppUtils.toActivity(getActivity(), SpecialLocalProductActivity.class, countyBundle2);
                break;
            case R.id.eat_must_iv:
                typeId = featureType.getCate().getTypeId();
                Bundle countyBundle3 = new Bundle();
                countyBundle3.putString("typeId", typeId);
                countyBundle3.putInt("where", 0);
                AppUtils.toActivity(getActivity(), SpecialLocalProductActivity.class, countyBundle3);
                break;
            case R.id.dj_fruit_iv:
                typeId = featureType.getFruit().getTypeId();
                Bundle countyBundle4 = new Bundle();
                countyBundle4.putString("typeId", typeId);
                countyBundle4.putInt("where", 0);
                AppUtils.toActivity(getActivity(), SpecialLocalProductActivity.class, countyBundle4);
                break;
            case R.id.tea_iv:
                typeId = featureType.getTea().getTypeId();
                Bundle countyBundle5 = new Bundle();
                countyBundle5.putString("typeId", typeId);
                countyBundle5.putInt("where", 0);
                AppUtils.toActivity(getActivity(), SpecialLocalProductActivity.class, countyBundle5);
                break;

            case R.id.zbdp_rll:
            case R.id.shop_around_iv:
                AppUtils.toActivity(getActivity(), SellerListActivity_new.class);
                break;

            case R.id.time_limite_iv:
                AppUtils.toActivity(getActivity(), SecondKillActivity.class);
                break;

            case R.id.credit_exchange_iv:
                AppUtils.toActivity(getActivity(), ScoreExchangeActivity.class);
                break;

            case R.id.sign_up_attend_iv:
                String ChbmUri = topClassify.getChbm().getUri();
                Bundle ChbmBundle = new Bundle();
                ChbmBundle.putString(TaoKeActivity.KEY_LOAD_URL, ChbmUri);
                AppUtils.toActivity(getActivity(), TaoKeActivity.class, ChbmBundle);
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
    }

    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent(getActivity(), DownloadImageService.class);
        intent.putExtra(DownloadImageService.KEY_FRESHEN, true);
        getActivity().startService(intent);
    }

    @Override
    public void onScroll(ScrollView scrollView, int scrollY) {
        totalDy += scrollY;
        scrollChange(scrollView, totalDy);
    }

    @Override
    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
        if (t > 20 && t / 2 < 255) {
            String transparency = "#" + (t / 2 < 16 ? "0" : "") + Integer.toHexString(t / 2) + "ffffff";
            divider_view.setVisibility(View.GONE);
            llMainMallTitleBackground.setBackgroundColor(Color.parseColor(transparency));
            tvTitle.setTextColor(Color.parseColor("#333333"));
            page_cashmall_index_location.setImageResource(R.drawable.icon_baisexialajiantou);
            if (hasNewMsg) {
                msg_iv.setImageResource(R.drawable.sy_xiaoxitishidianheise);
            } else {
                msg_iv.setImageResource(R.drawable.sy_xialagengduo3);
            }
            if (t>350){
                divider_view.setVisibility(View.VISIBLE);
            }
        } else if (t <= 20) {
            tvTitle.setTextColor(Color.parseColor("#ffffff"));
            page_cashmall_index_location.setImageResource(R.drawable.icon_xialajiantoubaise);
            if (hasNewMsg) {
                msg_iv.setImageResource(R.drawable.sy_xiaoxitishidianbaise);
            } else {
                msg_iv.setImageResource(R.drawable.sy_xialagengduo3);
            }
            divider_view.setVisibility(View.GONE);
            llMainMallTitleBackground.setBackgroundColor(Color.parseColor("#00000000"));
//            llMainMallTitleBackground.setAlpha(0);
        } else {
            divider_view.setVisibility(View.VISIBLE);
            llMainMallTitleBackground.setBackgroundColor(getResources().getColor(R.color.main_color_white));
            tvTitle.setTextColor(Color.parseColor("#333333"));
            page_cashmall_index_location.setImageResource(R.drawable.icon_baisexialajiantou);
            if (hasNewMsg) {
                msg_iv.setImageResource(R.drawable.sy_xiaoxitishidianheise);
            } else {
                msg_iv.setImageResource(R.drawable.sy_xialagengduo3);
            }
        }
    }

    public void loadSellerList() {
        homeSellerListLvAdapter = new HomeSellerListLvAdapter(getActivity(), mSellerList.getDataList());
        list.setAdapter(homeSellerListLvAdapter);
    }

    public void loadData() {
        Request<GetSellerListBean> request = new Request<>();
        getSellerListBean.setType("HOME");//首页的商家
        getSellerListBean.setPageIndex(1);
        request.setData(getSellerListBean);
        RXUtils.request(getActivity(), request, "getSellerListForNew", new SupportSubscriber() {

            @Override
            public void onNext(Object o) {
                Response<SellerList> dataListResponse = (Response<SellerList>) o;
                mSellerList.getDataList().clear();
                mSellerList.getDataList().addAll(dataListResponse.getData().getDataList());
                if (getSellerListBean.getPageIndex() == 1) {
                    //如果没有商品的情况下
                    if (dataListResponse.getData().getDataList() == null || dataListResponse.getData().getDataList().size() == 0) {
                        zbdp_rll.setVisibility(View.GONE);
                        Toast.makeText(HomePageFragment_New.this.getActivity(), "亲!暂无商品哦", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                /*int count = homeSellerListLvAdapter.getCount();
                int totalHeight = 0;
                for (int i = 0; i < count; i++) {
                    View itemView = homeSellerListLvAdapter.getView(i, null, list);
                    itemView.measure(0, 0);
                    totalHeight += itemView.getMeasuredHeight();
                }
                ViewGroup.LayoutParams params = list.getLayoutParams();
                params.height = totalHeight + (count - 1) * list.getDividerHeight();
                list.requestLayout();*/
                homeSellerListLvAdapter.notifyDataSetChanged();
                scroll_view.smoothScrollTo(0, 0);
                zbdp_rll.setVisibility(View.VISIBLE);
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }
        });
    }

}
