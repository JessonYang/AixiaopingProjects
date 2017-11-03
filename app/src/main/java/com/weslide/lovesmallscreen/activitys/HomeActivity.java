package com.weslide.lovesmallscreen.activitys;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
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
import com.weslide.lovesmallscreen.view_yy.activity.AxpDiscountTicketActivity;
import com.weslide.lovesmallscreen.view_yy.adapter.ConversationListAdapterEx;
import com.weslide.lovesmallscreen.view_yy.fragment.HomePageFragment_New;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
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
    //主页
//    HomePageFragment mMallFragment;
    HomePageFragment_New mMallFragment;
    //购物车
    ShoppingCartFragment mShoppingCartFragment;
    //订单
    OrderFragment mOrderFragment;
    //个人中心
    PersonalCenterFragment mUserInfoFragment;
    //换货会
    ExChangeBusFragment exChangeBusFragment;
    //聊天
    ConversationListFragment mConversationListFg;
    public static Activity activity;
    private Fragment mFragment, mCurrentFragment;
    public static final String KEY_SHOW = "KEY_SHOW";
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.tab_rg)
    RadioGroup tab_rg;
    @BindView(R.id.main_rb)
    RadioButton main_rb;
    @BindView(R.id.exchange_rb)
    RadioButton exchange_rb;
    @BindView(R.id.shopping_car_rb)
    RadioButton shopping_car_rb;
    @BindView(R.id.chat_rb)
    RadioButton chat_rb;
    @BindView(R.id.personal_center_rb)
    RadioButton personal_center_rb;
    private String showChange;
    private boolean click = false;
    private int show = Constants.HOME_SHOW_MALL;
    List<Integer> mIconUnselectId = new ArrayList<>();
    List<Integer> mIconSelectId = new ArrayList<>();
    List<String> title = new ArrayList<>();
    int postion = 0;
    private SharedPreferences cityInfo;
    private ImageView cancleIv;
    private AlertDialog updateDialog;
    private Button updateBtn;
    private TextView update_content, version_tv;
    private ClipboardManager mClipboard;
    private boolean isDebug = false;
    private Conversation.ConversationType[] mConversationsTypes;
    private Fragment mChatFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activity = this;
        ButterKnife.bind(this);
        String city = ContextParameter.getCurrentLocation().getCity();
        Zone district = null;
        if (city == null) {
            city = "";
        }
        tab_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.main_rb:
                        postion = 1;
                        if (mMallFragment == null) {
                            mMallFragment = new HomePageFragment_New();
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        if (mMallFragment.isAdded()) {
                            transaction.hide(mCurrentFragment).show(mMallFragment).commit();
                        } else {
                            transaction.hide(mCurrentFragment).add(R.id.container, mMallFragment).commit();
                        }
                        mCurrentFragment = mMallFragment;
                        break;
                    case R.id.exchange_rb:
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
                    case R.id.shopping_car_rb:
                        postion = 2;
                        if (mShoppingCartFragment == null) {
                            mShoppingCartFragment = new ShoppingCartFragment();
                        }
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        if (mShoppingCartFragment.isAdded()) {
                            transaction1.hide(mCurrentFragment).show(mShoppingCartFragment).commit();
                        } else {
                            transaction1.hide(mCurrentFragment).add(R.id.container, mShoppingCartFragment).commit();
                        }
                        mCurrentFragment = mShoppingCartFragment;
                        break;
                    case R.id.chat_rb:
                        postion = 3;
                        if (mChatFragment == null) {
//                            mChatFragment = new ChatFragment();
                            mChatFragment = initConversationList();
                        }
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        if (mChatFragment.isAdded()) {
                            transaction2.hide(mCurrentFragment).show(mChatFragment).commit();
                        } else {
                            transaction2.hide(mCurrentFragment).add(R.id.container, mChatFragment).commit();
                        }
                        mCurrentFragment = mChatFragment;
                        break;
                    case R.id.personal_center_rb:
                        postion = 4;
                        if (mUserInfoFragment == null) {
                            mUserInfoFragment = new PersonalCenterFragment();
                        }
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        if (mUserInfoFragment.isAdded()) {
                            transaction3.hide(mCurrentFragment).show(mUserInfoFragment).commit();
                        } else {
                            transaction3.hide(mCurrentFragment).add(R.id.container, mUserInfoFragment).commit();
                        }
                        mCurrentFragment = mUserInfoFragment;
                        break;
                }
            }
        });
        mCurrentFragment = new HomePageFragment_New();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mCurrentFragment).commit();
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
        pasteToResult();
    }

    private void pasteToResult() {
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        }

        String resultString = "";
        // 检查剪贴板是否有内容
        if (!mClipboard.hasPrimaryClip()) {
            Log.d("雨落无痕丶", "pasteToResult: 剪贴板为空!");
        } else {
            ClipData clipData = mClipboard.getPrimaryClip();
            int count = clipData.getItemCount();
            for (int i = 0; i < count; ++i) {
                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item.coerceToText(this);
                resultString += str;
            }
            if (resultString != null && resultString.length() > 0 && resultString.startsWith("axp_2017_id:") && resultString.contains("&")) {
                String[] split = resultString.split(":");
                String str = split[1];
                String[] strings = str.split("&");
                Bundle bundle = new Bundle();
                bundle.putString("ticketId", strings[0]);
                bundle.putString("shareId", strings[1]);
                AppUtils.toActivity(this, AxpDiscountTicketActivity.class, bundle);
                mClipboard.setText(null);
            }
        }
    }

    private void init() {
        EventBus.getDefault().register(this);
//        loadChange();
        loadClientConfig();
        updateScore();
        loadGlideAdvert();
        updateZoneList();
//        rlMainTabMall.performClick();
    }

    private void loadChange() {
        RXUtils.request(HomeActivity.this, new Request(), "getIsShow", new SupportSubscriber<Response<Show>>() {

            @Override
            public void onNext(Response<Show> showResponse) {

                if (showResponse.getData().getShowChange().equals("1")) {
                    exchange_rb.setVisibility(View.GONE);

                } else if (showResponse.getData().getShowChange().equals("0")) {
                    exchange_rb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                exchange_rb.setVisibility(View.VISIBLE);
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
            shopping_car_rb.setChecked(true);
//            mTabView.getTabLayout().setCurrentTab(show);
//            rlMainTabShoppingCar.performClick();
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
                ContextParameter.setTimeExtra(clientConfig.getData().getServiceTime() - System.currentTimeMillis());
                ContextParameter.setClientConfig(clientConfig.getData());
                checkVersionUpdate();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("雨落无痕丶", "getClientConfig:onError: " + e.toString());
            }

            @Override
            public void onResponseError(Response response) {
                Log.d("雨落无痕丶", "onResponseError: fdsgfrgrf");
            }
        });

    }

    /**
     * 检查版本更新
     */
    public void checkVersionUpdate() {
        String hasNewVerson = ContextParameter.getClientConfig().getHasNewVerson();
        if (getSupportApplication().alertVersionUpdate) {
            //判断时间间隔
            long currentTime = System.currentTimeMillis();
            long intevalTime = currentTime - getSupportApplication().alertVersionUpdateTime;
            if (intevalTime < getSupportApplication().MAX_ALERT_VERSION_UPDATE_TIME) {
                return;
            }
        }
        if (hasNewVerson.equals("1")) {
            showUpdateDialog();
        }
    }

    private void showUpdateDialog() {
        View updateView = LayoutInflater.from(this).inflate(R.layout.update_tip_dialog, null);
        cancleIv = (ImageView) updateView.findViewById(R.id.cancle_iv);
        update_content = (TextView) updateView.findViewById(R.id.update_content);
        version_tv = (TextView) updateView.findViewById(R.id.version_tv);
        updateBtn = ((Button) updateView.findViewById(R.id.btn_update));
        version_tv.setText(ContextParameter.getClientConfig().getNewVersion());
        List<String> newVersonContents = ContextParameter.getClientConfig().getNewVersonContents();
        StringBuilder builder = new StringBuilder();
        if (newVersonContents != null && newVersonContents.size() > 0) {
            for (int i = 0; i < newVersonContents.size(); i++) {
                if (i != newVersonContents.size() - 1) {
                    builder.append(newVersonContents.get(i) + "\n");
                } else {
                    builder.append(newVersonContents.get(i));
                }
            }
        }
        update_content.setText(builder.toString());
//        update_content.setText(ContextParameter.getClientConfig().getNewVersonContents());
        cancleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDialog.dismiss();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportApplication().alertVersionUpdate = true;
                getSupportApplication().alertVersionUpdateTime = System.currentTimeMillis();
                Uri uri = Uri.parse(ContextParameter.getClientConfig().getNewVersionDownload());
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                updateDialog.dismiss();
            }
        });
        updateDialog = new AlertDialog.Builder(this, R.style.CustomDialog).setView(updateView).create();
        changeScreenBg();
        updateDialog.show();
        updateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                restoreScreenBg();
            }
        });
    }

    //初始化融云会话列表fg
    private Fragment initConversationList() {
        Log.d("雨落无痕丶", "initConversationList: ");
        if (mConversationListFg == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            listFragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
            Uri uri;
            if (isDebug) {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM,
                        Conversation.ConversationType.DISCUSSION
                };

            } else {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM
                };
            }
            listFragment.setUri(uri);
            mConversationListFg = listFragment;
            return listFragment;
        } else {
            return mConversationListFg;
        }
    }

    private void changeScreenBg() {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = (float) 0.5;
        this.getWindow().setAttributes(params);
    }

    private void restoreScreenBg() {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = (float) 1;
        this.getWindow().setAttributes(params);
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

                /*Zone district = getSupportApplication().getDaoSession().getZoneDao()
                        .loadDistrictByZoneName(ContextParameter.getCurrentLocation().getCity(), ContextParameter.getCurrentLocation().getDistrict());
                ContextParameter.setCurrentZone(district);*/

                ContextParameter.setHotCityList(zoneListResponse.getData().getHotDataList());
                ContextParameter.setAllCityList(zoneListResponse.getData().getDataList());
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
        edit.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ArchitectureAppliation.fixInputMethodManagerLeak(this);
    }

    private void click() {
        if (postion == 1) {
            main_rb.setChecked(true);
        } else if (postion == 2) {
            shopping_car_rb.setChecked(true);
        } else if (postion == 3) {
            chat_rb.setChecked(true);
        } else if (postion == 4) {
            personal_center_rb.setChecked(true);
        }
    }

    public void handlerZone() {
        Zone district = getSupportApplication().getDaoSession().getZoneDao()
                .loadDistrictByZoneName(ContextParameter.getCurrentLocation().getCity(), ContextParameter.getCurrentLocation().getDistrict());
        ContextParameter.setCurrentZone(district);
    }
}
