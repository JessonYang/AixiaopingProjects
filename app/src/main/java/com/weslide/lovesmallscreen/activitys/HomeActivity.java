package com.weslide.lovesmallscreen.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.DownloadImageService;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.dao.serialize.ZoneClientSerialize;
import com.weslide.lovesmallscreen.dao.sp.UserInfoSP;
import com.weslide.lovesmallscreen.fragments.mall.ExChangeBusFragment;
import com.weslide.lovesmallscreen.fragments.mall.PersonalCenterFragment;
import com.weslide.lovesmallscreen.fragments.mall.ShoppingCartFragment;
import com.weslide.lovesmallscreen.fragments.order.OrderFragment;
import com.weslide.lovesmallscreen.models.AcquireScore;
import com.weslide.lovesmallscreen.models.Show;
import com.weslide.lovesmallscreen.models.Zone;
import com.weslide.lovesmallscreen.models.ZoneList;
import com.weslide.lovesmallscreen.models.bean.UpdateScoreBean;
import com.weslide.lovesmallscreen.models.bean.ZoneListBean;
import com.weslide.lovesmallscreen.models.config.ClientConfig;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateMallMessage;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateOrderListMessage;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateShoppingCarMessage;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.network.StatusCode;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.view_yy.fragment.HomePageFragment_New;

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
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xu on 2016/5/25.
 * 商城界面
 */
public class HomeActivity extends BaseActivity {

    /**
     * 界面切换
     */
//    HomePageFragment mMallFragment;
    HomePageFragment_New mMallFragment;
    ShoppingCartFragment mShoppingCartFragment;
    OrderFragment mOrderFragment;
    PersonalCenterFragment mUserInfoFragment;
    ExChangeBusFragment exChangeBusFragment;
    public static Activity activity;

    public static final String KEY_SHOW = "KEY_SHOW";
    @BindView(R.id.iv_main_tab_mall)
    ImageView ivMainTabMall;
    @BindView(R.id.tv_main_tab_mall)
    TextView tvMainTabMall;
    @BindView(R.id.rl_main_tab_mall)
    RelativeLayout rlMainTabMall;
    @BindView(R.id.iv_main_tab_change)
    ImageView ivMainTabChange;
    @BindView(R.id.tv_main_tab_change)
    TextView tvMainTabChange;
    @BindView(R.id.rl_main_tab_change)
    RelativeLayout rlMainTabChange;
    @BindView(R.id.iv_main_tab_shopping_car)
    ImageView ivMainTabShoppingCar;
    @BindView(R.id.tv_main_tab_shopping_car)
    TextView tvMainTabShoppingCar;
    @BindView(R.id.rl_main_tab_shopping_car)
    RelativeLayout rlMainTabShoppingCar;
    @BindView(R.id.iv_main_tab_oder)
    ImageView ivMainTabOder;
    @BindView(R.id.tv_main_tab_oder)
    TextView tvMainTabOder;
    @BindView(R.id.rl_main_tab_oder)
    RelativeLayout rlMainTabOder;
    @BindView(R.id.iv_main_tab_person)
    ImageView ivMainTabPerson;
    @BindView(R.id.tv_main_tab_person)
    TextView tvMainTabPerson;
    @BindView(R.id.rl_main_tab_person)
    RelativeLayout rlMainTabPerson;
    @BindView(R.id.container)
    FrameLayout container;
    private String showChange;
    private boolean click = false;
    private int show = Constants.HOME_SHOW_MALL;

    /*  @BindView(R.id.htv_tab)
      HomeTabView mTabView;*/
    List<Integer> mIconUnselectId = new ArrayList<>();
    List<Integer> mIconSelectId = new ArrayList<>();
    List<String> title = new ArrayList<>();

    int postion = 0;
    private SharedPreferences cityInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusbarUtils.enableTranslucentStatusbar(this);
        setContentView(R.layout.activity_home);
        activity = this;
        ButterKnife.bind(this);
        String city = ContextParameter.getCurrentLocation().getCity();
        Zone district = null;
        if (city == null) {
            city = "";
        }
        district = getSupportApplication().getDaoSession().getZoneDao()
                .loadDistrictByZoneName(city, ContextParameter.getCurrentLocation().getDistrict());
        ContextParameter.setCurrentZone(district);

//
//        cityInfo = getSharedPreferences("cityInfo", MODE_PRIVATE);
//        String currentCity = cityInfo.getString("currentCity", "黄埔区");

//        String s = LauncherActivity.location.getDistrict();
//        Log.d("雨落无痕丶", "onCreate: ttt"+currentCity+"========="+s);
        /*if (!currentCity.equals(s)){
            final String finalCity = city;
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("温馨提示")
                    .setMessage("当前定位到" + ContextParameter.getCurrentLocation().getDistrict() + ",是否切换到" + ContextParameter.getCurrentLocation().getDistrict())
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            init();
                        }
                    })
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (LauncherActivity.location != null) {
                                ContextParameter.setCurrentLocation(LauncherActivity.location);
                                handlerZone();
                                Log.d("雨落无痕丶", "onClick: dsfdsfs");
                            }

                            init();
                        }
                    })
                    .create();
            dialog.show();
        }*/

        init();

       /* Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //读取当前应该显示哪一项
            show = bundle.getInt(KEY_SHOW, Constants.HOME_SHOW_MALL);
//            mTabView.getTabLayout().setCurrentTab(show);
            rlMainTabShoppingCar.performClick();
            EventBus.getDefault().post(new UpdateShoppingCarMessage());
            EventBus.getDefault().post(new UpdateOrderListMessage(Constants.ORDER_STATUS_WAIT_PAY));
            Log.d("雨落无痕丶", "onCreate: fff"+show);
        }*/
        L.e("onCreate");
    }

    private void init() {
        EventBus.getDefault().register(this);
//        loadChange();
        loadClientConfig();
        updateScore();
        loadGlideAdvert();
        updateZoneList();
        rlMainTabMall.performClick();
    }

    private void loadChange() {
        RXUtils.request(HomeActivity.this, new Request(), "getIsShow", new SupportSubscriber<Response<Show>>() {

            @Override
            public void onNext(Response<Show> showResponse) {

                if (showResponse.getData().getShowChange().equals("1")) {
                    rlMainTabChange.setVisibility(View.GONE);

                } else if (showResponse.getData().getShowChange().equals("0")) {
                    rlMainTabChange.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                rlMainTabChange.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * eventBus事件
     *
     * @param event
     */
    @Subscribe
    public void onEvent(UpdateMallMessage event) {
        loadChange();
    }

    @Override
    protected void onResume() {
        super.onResume();

        L.e("onResume");

        getSupportApplication().onlyActivity(HomeActivity.class);
        if (click == true) {
            click();
            click = false;
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        L.e("onNewIntent-----------------");

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            //读取当前应该显示哪一项
            show = bundle.getInt(KEY_SHOW, Constants.HOME_SHOW_MALL);
//            mTabView.getTabLayout().setCurrentTab(show);
            rlMainTabShoppingCar.performClick();
        }

        EventBus.getDefault().post(new UpdateShoppingCarMessage());
        EventBus.getDefault().post(new UpdateOrderListMessage(Constants.ORDER_STATUS_WAIT_PAY));
    }

    /**
     * 初始化客户端配置
     */
    public void loadClientConfig() {

        RXUtils.request(this, new Request(), "getClientConfig", new SupportSubscriber<Response<ClientConfig>>() {
            @Override
            public void onNext(Response<ClientConfig> clientConfig) {
                ContextParameter.setClientConfig(clientConfig.getData());

                checkVersionUpdate();
            }
        });

    }

    /**
     * 检查版本更新
     */
    public void checkVersionUpdate() {

        int currentVersion = AppUtils.getVersionCode(this);
        int newVersion = ContextParameter.getClientConfig().getNewVersion();

        if (getSupportApplication().alertVersionUpdate) {
            //判断时间间隔
            long currentTime = System.currentTimeMillis();
            long intevalTime = currentTime - getSupportApplication().alertVersionUpdateTime;

            if (intevalTime < getSupportApplication().MAX_ALERT_VERSION_UPDATE_TIME) {
                return;
            }

        }


        if (newVersion > currentVersion) {

            boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
            SimpleDialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
                @Override
                public void onPositiveActionClicked(DialogFragment fragment) {
                    super.onPositiveActionClicked(fragment);
                    getSupportApplication().alertVersionUpdate = true;
                    getSupportApplication().alertVersionUpdateTime = System.currentTimeMillis();
                    Uri uri = Uri.parse(ContextParameter.getClientConfig().getNewVersionDownload());
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                }

                @Override
                public void onNegativeActionClicked(DialogFragment fragment) {
                    super.onNegativeActionClicked(fragment);
                }
            };

            builder.message(ContextParameter.getClientConfig().getNewVersionMessage()).title("更新提示")
                    .positiveAction("立即更新")
                    .negativeAction("再等等");

            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(getSupportFragmentManager(), null);

            /*getSupportApplication().alertVersionUpdate = true;
            getSupportApplication().alertVersionUpdateTime = System.currentTimeMillis();*/

        }
    }

    /**
     * 向服务器提交积分
     */
    public void updateScore() {

        if (!ContextParameter.isLogin()) {
            return;
        }

        Observable.just(null)
                .filter(o -> NetworkUtils.isConnected(HomeActivity.this))  //检测网络
                .observeOn(Schedulers.computation())                       //子线程切换
                .filter(o -> { //判断用户是否已经登录,未登录情况下，将所有获取积分的记录全部删除
                    if (ContextParameter.isLogin()) {
                        return true;
                    } else {
                        //将所有删除积分数据删除
                        ArchitectureAppliation.getDaoSession().getAcquireScoreDao().deleteAll();
                        return false;
                    }
                })
                .map(new Func1<Object, UpdateScoreBean>() {             //读取数据库是否有更新数据
                    @Override
                    public UpdateScoreBean call(Object o) {
                        List<AcquireScore> acquireScoreList = ArchitectureAppliation.getDaoSession().getAcquireScoreDao().loadAll();
                        UpdateScoreBean reqeust = new UpdateScoreBean();
                        reqeust.setAcquireScores(acquireScoreList);
                        return reqeust;
                    }
                })
                .filter(updateScoreReqeust -> updateScoreReqeust.getAcquireScores() != null && updateScoreReqeust.getAcquireScores().size() != 0)
                .map(new Func1<UpdateScoreBean, Response<UpdateScoreBean>>() {
                    @Override
                    public Response<UpdateScoreBean> call(UpdateScoreBean updateScoreReqeust) {

                        Request<UpdateScoreBean> reqeust = new Request<UpdateScoreBean>();
                        reqeust.setData(updateScoreReqeust);
                        try {
                            Response<UpdateScoreBean> response = HTTP.getAPI().updateScore(HTTP.formatJSONData(reqeust)).execute().body();
                            return response;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                })
                .filter(new Func1<Response<UpdateScoreBean>, Boolean>() {
                    @Override
                    public Boolean call(Response<UpdateScoreBean> updateScoreResponseResponse) {
                        //判断是否积分达到上限
                        if (updateScoreResponseResponse != null && updateScoreResponseResponse.getStatus() == -65) {
                            //将所有删除积分数据删除
                            ArchitectureAppliation.getDaoSession().getAcquireScoreDao().deleteAll();
                        }
                        return StatusCode.validateSuccess(HomeActivity.this, updateScoreResponseResponse);
                    }
                })
                .subscribe(new Subscriber<Response<UpdateScoreBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("error", "error", e);
                    }

                    @Override
                    public void onNext(Response<UpdateScoreBean> updateScoreResponseResponse) {
                        ArchitectureAppliation.getDaoSession().getAcquireScoreDao().deleteAll();
                        ContextParameter.getUserInfo().setScore(updateScoreResponseResponse.getData().getTotalScore());
                        UserInfoSP.setUserInfo(ContextParameter.getUserInfo());

                        L.e("当前积分：" + ContextParameter.getUserInfo().getScore());
                    }
                });

    }


    /**
     * 加载滑屏图片
     */
    public void loadGlideAdvert() {

        Intent intent = new Intent(HomeActivity.this, DownloadImageService.class);
        startService(intent);

    }

    /**
     * 更新城市列表
     */
    private void updateZoneList() {

        Request request = new Request();
        ZoneListBean bean = new ZoneListBean();
        bean.setVersion(ZoneClientSerialize.getClientConfig(this).getVersion());
        request.setData(bean);

        RXUtils.request(this, request, "getZoneList", new SupportSubscriber<Response<ZoneList>>() {

            @Override
            public void onNoNetwork() {
                L.e("无网络状态，更新城市列表失败！");
            }

            @Override
            public void handlerResponse(Response<ZoneList> zoneListResponse) {  //数据请求成功后处理
                if (zoneListResponse.getData() == null || zoneListResponse.getData().getDataList().size() == 0) { //判断后台是否有返回数据
                    return;
                }
                //将数据持久化
                getSupportApplication().getDaoSession().getZoneDao().deleteAll();
                getSupportApplication().getDaoSession().getZoneDao().insertInTx(zoneListResponse.getData().getDataList());

                //记录版本号
                ZoneListBean config = new ZoneListBean();
                config.setVersion(zoneListResponse.getData().getVersion());
                ZoneClientSerialize.setClientConfig(ArchitectureAppliation.getAppliation(), config);
            }

            @Override
            public void onNext(Response<ZoneList> zoneListResponse) {

                Zone district = getSupportApplication().getDaoSession().getZoneDao()
                        .loadDistrictByZoneName(ContextParameter.getCurrentLocation().getCity(), ContextParameter.getCurrentLocation().getDistrict());
                ContextParameter.setCurrentZone(district);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Log.d("QRActivity", "Cancelled scan");
            } else {
                Log.d("QRActivity", "Scanned : " + result.getContents());
                URIResolve.resolve(this, result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);

        }

    }

    long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出爱小屏", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                superFinish();
                Glide.get(this).clearMemory();
//                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();

        cityInfo = getSharedPreferences("cityInfo", MODE_PRIVATE);
        SharedPreferences.Editor edit = cityInfo.edit();
        edit.putString("zoneId", ContextParameter.getCurrentZone().getZoneId());
        edit.putString("zoneName", ContextParameter.getCurrentZone().getName());
        edit.putString("zoneLevel", ContextParameter.getCurrentZone().getLevel());


        /*edit.putString("pool1", ContextParameter.getCurrentZone().getLevel());
        edit.putString("pool2", ContextParameter.getCurrentZone().getLevel());
        edit.putString("pool3", ContextParameter.getCurrentZone().getLevel());
        edit.putString("pool4", ContextParameter.getCurrentZone().getLevel());*/
        edit.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ArchitectureAppliation.fixInputMethodManagerLeak(this);

//        Log.d("雨落无痕丶", "onDestroy: dddd"+ContextParameter.getCurrentZone().getName());
    }

    @OnClick({R.id.rl_main_tab_mall, R.id.rl_main_tab_change, R.id.rl_main_tab_shopping_car, R.id.rl_main_tab_oder, R.id.rl_main_tab_person})
    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (view.getId()) {
            case R.id.rl_main_tab_mall:
                postion = 1;
                select();
                tvMainTabMall.setSelected(true);
                ivMainTabMall.setSelected(true);
                if (mMallFragment == null) {
//                    mMallFragment = new HomePageFragment();
                    mMallFragment = new HomePageFragment_New();
                    transaction.replace(R.id.container, mMallFragment);
                } else {
                    transaction.show(mMallFragment);
                    // mMallFragment.goToTop();
                }
                break;
            case R.id.rl_main_tab_change:
               /* tvMainTabChange.setSelected(true);
                ivMainTabChange.setSelected(true);
                if(exChangeBusFragment==null){
                    exChangeBusFragment = new ExChangeBusFragment();
                    transaction.add(R.id.container,exChangeBusFragment);
                }else{
                    transaction.show(exChangeBusFragment);
                }*/
                // AppUtils.toActivity(HomeActivity.this,ExChangeActivity.class);
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(HomeActivity.this, LoginOptionActivity.class);
                } else {
                    String URL = HTTP.URL_EXCHENGE_GOODS + "zone_id=" + ContextParameter.getCurrentZone().getZoneId() + "&user_id=" + ContextParameter.getUserInfo().getUserId();
                    L.e("换货会链接", URL);
                    //URIResolve.resolve(this,URL);
                    Bundle bundle = new Bundle();
                    bundle.putString(ChangeGoodsWebActivity.KEY_LOAD_URL, URL);
                    AppUtils.toActivity(HomeActivity.this, ChangeGoodsWebActivity.class, bundle);
                }
                click = true;
                break;
            case R.id.rl_main_tab_shopping_car:
                postion = 2;
                select();
                tvMainTabShoppingCar.setSelected(true);
                ivMainTabShoppingCar.setSelected(true);
                if (mShoppingCartFragment == null) {
                    mShoppingCartFragment = new ShoppingCartFragment();
                    transaction.add(R.id.container, mShoppingCartFragment);
                } else {
                    transaction.show(mShoppingCartFragment);
                }
                break;
            case R.id.rl_main_tab_oder:
                postion = 3;
                select();
                tvMainTabOder.setSelected(true);
                ivMainTabOder.setSelected(true);
                if (mOrderFragment == null) {
                    mOrderFragment = new OrderFragment();
                    transaction.add(R.id.container, mOrderFragment);
                } else {
                    transaction.show(mOrderFragment);
                }
                break;
            case R.id.rl_main_tab_person:
                postion = 4;
                select();
                tvMainTabPerson.setSelected(true);
                ivMainTabPerson.setSelected(true);
                if (mUserInfoFragment == null) {
                    mUserInfoFragment = new PersonalCenterFragment();
                    transaction.add(R.id.container, mUserInfoFragment);
                } else {
                    transaction.show(mUserInfoFragment);
                }
                break;

        }
        transaction.commit();
    }


    private void click() {
        if (postion == 1) {
            rlMainTabMall.performClick();
        } else if (postion == 2) {
            rlMainTabShoppingCar.performClick();
        } else if (postion == 3) {
            rlMainTabOder.performClick();
        } else if (postion == 4) {
            rlMainTabPerson.performClick();
        }
    }

    private void select() {
        tvMainTabChange.setSelected(false);
        ivMainTabChange.setSelected(false);

        tvMainTabMall.setSelected(false);
        ivMainTabMall.setSelected(false);

        tvMainTabShoppingCar.setSelected(false);
        ivMainTabShoppingCar.setSelected(false);

        tvMainTabOder.setSelected(false);
        ivMainTabOder.setSelected(false);

        tvMainTabPerson.setSelected(false);
        ivMainTabPerson.setSelected(false);
    }

    public void hideAllFragment(FragmentTransaction transaction) {
        if (mMallFragment != null) {
            transaction.hide(mMallFragment);
        }
        if (mShoppingCartFragment != null) {
            transaction.hide(mShoppingCartFragment);
        }
        if (mOrderFragment != null) {
            transaction.hide(mOrderFragment);
        }
        if (mUserInfoFragment != null) {
            transaction.hide(mUserInfoFragment);
        }
    }

    public void handlerZone() {
        Zone district = getSupportApplication().getDaoSession().getZoneDao()
                .loadDistrictByZoneName(ContextParameter.getCurrentLocation().getCity(), ContextParameter.getCurrentLocation().getDistrict());
        ContextParameter.setCurrentZone(district);
    }
}
