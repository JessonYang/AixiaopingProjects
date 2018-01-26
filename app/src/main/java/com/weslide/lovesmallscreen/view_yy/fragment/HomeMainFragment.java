package com.weslide.lovesmallscreen.view_yy.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.CityDistrictActivity_new;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.activitys.MsgHomeActivity;
import com.weslide.lovesmallscreen.activitys.mall.GoodsSearchActivity_New;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateMallMessage;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.presenter_yy.HomeMainFgPresenter;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.SerializableUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.view_yy.adapter.HomeMainRcAdapter;
import com.weslide.lovesmallscreen.view_yy.customview.DividerGridItemDecoration;
import com.weslide.lovesmallscreen.view_yy.viewinterface.IShowHomeMainFg;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by YY on 2017/11/27.
 */
public class HomeMainFragment extends BaseFragment implements IShowHomeMainFg, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    /**
     * 首页数据序列化存储文件名 Response<List<RecyclerViewModel>>
     */
    private static final String DATA_SERIALIZE_FILE_NAME = "axp_home_data";
    private View view, divider_view;
    private SuperRecyclerView mRclv;
    private LinearLayout ll_main_mall_title_background, msg_ll, scan_ll, share_ll;
    private RelativeLayout rl_main_mall_title_content, home_page_msg_rll;
    private TextView tv_title, unread_num_tv;
    private EditText et_search;
    private ImageView msg_iv, page_cashmall_index_location;
    private LoadingDialog loadingDialog;
    private HomeMainRcAdapter mAdapter;
    private View pwView;
    private boolean hasNewMsg;
    private String unreadCount;
    private PopupWindow popupWindow;
    private int dotStatus;
    private HomeMainFgPresenter homeMainFgPresenter;
    private DataList<RecyclerViewModel> mDataList;
    private int totalDy = 0;
    private Response mResponse;
    private GridLayoutManager rclvManager;
    public static String pid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_main, container, false);
        homeMainFgPresenter = new HomeMainFgPresenter(getActivity(), this);
        homeMainFgPresenter.initHomeMainFgView();
        homeMainFgPresenter.showHomeView();
        return view;
    }

    @Override
    public void initView() {
        mRclv = ((SuperRecyclerView) view.findViewById(R.id.home_main_rclv));
        ll_main_mall_title_background = ((LinearLayout) view.findViewById(R.id.ll_main_mall_title_background));
        rl_main_mall_title_content = ((RelativeLayout) view.findViewById(R.id.rl_main_mall_title_content));
        home_page_msg_rll = ((RelativeLayout) view.findViewById(R.id.home_page_msg_rll));
        tv_title = ((TextView) view.findViewById(R.id.tv_title));
        et_search = ((EditText) view.findViewById(R.id.et_search));
        msg_iv = ((ImageView) view.findViewById(R.id.home_page_msg_iv));
        page_cashmall_index_location = ((ImageView) view.findViewById(R.id.page_cashmall_index_location));
        divider_view = view.findViewById(R.id.divider_view);
        loadingDialog = new LoadingDialog(getActivity());
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        //读取序列化数据
        Object object = SerializableUtils.getObjectByCacheFile(getActivity(), DATA_SERIALIZE_FILE_NAME);
        if (object == null) {
            mDataList = new DataList<>();
            mDataList.setDataList(new ArrayList<>());
        } else {
            mDataList = (DataList<RecyclerViewModel>) object;
        }
        rl_main_mall_title_content.setOnClickListener(this);
        et_search.setOnClickListener(this);
        home_page_msg_rll.setOnClickListener(this);
        hasNewMsg = getContext().getSharedPreferences("newMsgInfo", Context.MODE_PRIVATE).getBoolean("hasNewMsg", false);
        unreadCount = getContext().getSharedPreferences("msg", Context.MODE_PRIVATE).getString("unreadCount", "");
        if (hasNewMsg) {
            msg_iv.setImageResource(R.drawable.sy_xiaoxitishidianbaise);
            dotStatus = 0;
        } else {
            msg_iv.setImageResource(R.drawable.sy_xialagengduo3);
            dotStatus = 1;
        }
        mAdapter = new HomeMainRcAdapter(getActivity(), mDataList);
        rclvManager = new GridLayoutManager(getActivity(), 3);
        mRclv.setLayoutManager(rclvManager);
        mRclv.setAdapter(mAdapter);
        rclvManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.isPtGood(position) ? 1 : 3;
            }
        });
        mRclv.addItemDecoration(new DividerGridItemDecoration(getActivity(), DensityUtils.dp2px(getActivity(), 3), R.color.main_view_bg));
//        mRclv.setRefreshListener(this);
        mRclv.setupMoreListener(this);
        //滑动监听(处理toolbar渐变)
        mRclv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                scrollChange(totalDy);
            }

        });
    }

    @Override
    public void showLoadingView() {
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissLoadingView() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showErrView() {
        dismissLoadingView();
    }

    @Override
    public void showView(DataList<RecyclerViewModel> dataList) {
        if (dataList.getPid() != null) {
            pid = dataList.getPid();
        }
        tv_title.setText(ContextParameter.getCurrentZone().getName());
        mDataList.getDataList().clear();
        mDataList.getDataList().addAll(dataList.getDataList());
        mDataList.setPageSize(dataList.getPageSize());
        mDataList.setPageIndex(dataList.getPageIndex());
        mAdapter.notifyDataSetChanged();
        dismissLoadingView();
    }

    /**
     * 更新view
     *
     * @param list
     */
    @Override
    public void refreshView(DataList<RecyclerViewModel> list) {
        tv_title.setText(ContextParameter.getCurrentZone().getName());
        mDataList.getDataList().clear();
        mDataList.getDataList().addAll(list.getDataList());
        mDataList.setPageSize(list.getPageSize());
        mDataList.setPageIndex(list.getPageIndex());
        mAdapter.notifyDataSetChanged();
        dismissLoadingView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //选择城市
            case R.id.rl_main_mall_title_content:
                AppUtils.toActivity(getActivity(), CityDistrictActivity_new.class);
                break;
            //搜索
            case R.id.et_search:
                Bundle bundle = new Bundle();
                bundle.putString("title", "");
                AppUtils.toActivity(getActivity(), GoodsSearchActivity_New.class, bundle);
                break;
            //消息
            case R.id.home_page_msg_rll:
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
                    dotStatus = 1;
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

    /**
     * 屏幕黑色半透明效果
     */
    private void changeScreenBg() {
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = (float) 0.6;
        getActivity().getWindow().setAttributes(params);
    }

    /**
     * 恢复屏幕颜色
     */
    private void restoreScreenBg() {
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = (float) 1;
        getActivity().getWindow().setAttributes(params);
    }

    /**
     * Event接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEvent(UpdateMallMessage event) {
        showLoadingView();
        homeMainFgPresenter.refreshHomeData();
        if (hasNewMsg) {
            msg_iv.setImageResource(R.drawable.sy_xiaoxitishidianbaise);
            dotStatus = 0;
        } else {
            msg_iv.setImageResource(R.drawable.sy_xialagengduo3);
            dotStatus = 1;
        }
    }

    @Override
    public void onRefresh() {
        //SuperRecyclerview下拉刷新
        homeMainFgPresenter.refreshHomeData();
    }

    /**
     * 滑动改变toolbar颜色
     */
    public void scrollChange(int t) {
        if (t > 20 && t / 2 < 255) {
            String transparency = "#" + (t / 2 < 16 ? "0" : "") + Integer.toHexString(t / 2) + "ffffff";
            divider_view.setVisibility(View.GONE);
            ll_main_mall_title_background.setBackgroundColor(Color.parseColor(transparency));
            tv_title.setTextColor(Color.parseColor("#333333"));
            page_cashmall_index_location.setImageResource(R.drawable.icon_baisexialajiantou);
            if (dotStatus == 0) {
                msg_iv.setImageResource(R.drawable.sy_xiaoxitishidianheise);
            } else {
                msg_iv.setImageResource(R.drawable.sy_xialagengduo3);
            }
            if (t > 350) {
                divider_view.setVisibility(View.VISIBLE);
            }
        } else if (t <= 20) {
            tv_title.setTextColor(Color.parseColor("#ffffff"));
            page_cashmall_index_location.setImageResource(R.drawable.icon_xialajiantoubaise);
            if (dotStatus == 0) {
                msg_iv.setImageResource(R.drawable.sy_xiaoxitishidianheise);
            } else {
                msg_iv.setImageResource(R.drawable.sy_xialagengduo3);
            }
            divider_view.setVisibility(View.GONE);
            ll_main_mall_title_background.setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            divider_view.setVisibility(View.VISIBLE);
            ll_main_mall_title_background.setBackgroundColor(getResources().getColor(R.color.main_color_white));
            tv_title.setTextColor(Color.parseColor("#333333"));
            page_cashmall_index_location.setImageResource(R.drawable.icon_baisexialajiantou);
            if (dotStatus == 0) {
                msg_iv.setImageResource(R.drawable.sy_xiaoxitishidianheise);
            } else {
                msg_iv.setImageResource(R.drawable.sy_xialagengduo3);
            }
        }
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        //SuperRecyclerView加载更多
        homeMainFgPresenter.loadMoreList();
    }
}
