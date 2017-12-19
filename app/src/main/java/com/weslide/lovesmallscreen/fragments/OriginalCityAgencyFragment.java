package com.weslide.lovesmallscreen.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.OriginalCityAgencyAllOrderActivity_New;
import com.weslide.lovesmallscreen.activitys.OriginalMyPartnerActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.HomeTicketsModel;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketAllModel;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketTypesModel;
import com.weslide.lovesmallscreen.model_yy.javabean.TypeListBean;
import com.weslide.lovesmallscreen.models.CanPayBean;
import com.weslide.lovesmallscreen.models.GoodsType;
import com.weslide.lovesmallscreen.models.bean.OriginalCityAgencyBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ScreenUtils;
import com.weslide.lovesmallscreen.view_yy.activity.ApplayPartnerActivity;
import com.weslide.lovesmallscreen.view_yy.activity.TaoBaoActivity;
import com.weslide.lovesmallscreen.view_yy.activity.TicketGoodsDtActivity;
import com.weslide.lovesmallscreen.view_yy.adapter.OriginalAgenceVpAdapter;
import com.weslide.lovesmallscreen.view_yy.customview.MyScrollView;
import com.weslide.lovesmallscreen.view_yy.customview.NestedListView;
import com.weslide.lovesmallscreen.view_yy.customview.ViewPageInScrollView;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;
import com.weslide.lovesmallscreen.views.dialogs.SecondaryTypeDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.dkzwm.smoothrefreshlayout.SmoothRefreshLayout;
import me.dkzwm.smoothrefreshlayout.extra.footer.ClassicFooter;
import okhttp3.OkHttpClient;

/**
 * Created by YY on 2017/3/21.
 */
public class OriginalCityAgencyFragment extends BaseFragment implements View.OnClickListener, MyScrollView.OnScrollListener, View.OnTouchListener {

    private View originalCityAgencyFragmentView;
    private CustomToolbar toolbar;
    private TextView distinctProfit;
    private TextView canDeal;
    private Button go_pay_btn;
    private RadioGroup rg;
    private ViewPageInScrollView vp;
    private RelativeLayout myPartnerRll;
    private List<Fragment> fragmentList;
    private RelativeLayout allOrdersRll, hqkt_lv_rll;
    private OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build();
    private OriginalCityAgencyBean data;

    private AlertDialog canPayDialog;
    private TextView yesBtn;
    private TextView noBtn;
    private String userId;
    private TabLayout mTab_top;
    private String[] title = new String[]{"今日", "昨日", "本月", "上月"};
    //    private List<String> tabTitleList = new ArrayList<>();
//    private ImageView no_status_iv;
//    private LinearLayout ddzh_ll;
//    private TextView ddzt_tv;
    private String canPayMsg;
    private String direction;
    private TextView yestoday_personal_predict;
    private TextView this_month_personal_predict;
    private TextView last_month_personal_predict;
    private TextView today_partner_predict;
    private TextView yestoday_partner_predict;
    private TextView this_month_partner_predict;
    private TextView last_month_partner_predict;
    private TextView today_personal_predict;
    private TextView today_personal_order;
    private TextView this_month_personal_order;
    private TextView today_partner_order;
    private TextView yestoday_partner_order;
    private TextView this_month_partner_order;
    private TextView last_month_partner_order;
    private TextView yestoday_personal_order;
    private TextView last_month_personal_order;
    private LoadingDialog loadingDialog;
    private TextView income_banlance_tv;
    private TextView partner_promotion_num;
    private TextView has_distribute_num;
    private TextView no_distribute_num;
    private List<TicketTypesModel> ticketTypes;
    private List<TicketAllModel> lvList = new ArrayList<>();
    //    private ListView hqkt_lv;
    private NestedListView hqkt_lv;
    private OriginalAgenceVpAdapter mLvAdapter;
    private int mLvPage = 1;
    private String typeId = "";
    private LinearLayout myPartnerRll_data;
    private LinearLayout partner_order_ll;
    private LinearLayout partner_predict_ll, hqkt_ll;
    private TextView personal_order_tag;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (ticketTypes != null && ticketTypes.size() > 0) {
                    initTabLayout();
                }
            }
        }
    };
    private int isPartner;
    private TabLayout mTab;
    private MyScrollView myScrollView;
    private com.rey.material.widget.Button reload_btn;
    private LinearLayout empty_ll;
    private TextView partner_type_tv, partner_promotion_tag, has_distribute_tag, no_distribute_tag;
    private LinearLayout partner_detail_ll;
    private TextView partner_order_tag, partner_predict_tag;
    private Button to_applay_partner_btn;
    private ImageView predict_profit_help, income_help, can_deal_help;
    private LinearLayout type_ll, type_ll2;
    private RelativeLayout near_rll, type_rll, comment_rll;
    private ImageView nearIv, typeIv, commentIv, nearIv3, typeIv3, commentIv3;
    private TextView nearTv, typeTv, commentTv, nearTv3, typeTv3, commentTv3;
    private List<GoodsType> typeList;
    private int top;
    private PopupWindow hqkt_type_pw;
    private View hqkt_type_pw_view;
    private TextView near_type_tv;
    private String areaType = "0";
    private String sortType = "0";
    private LinearLayout screen_bg_ll;
    private String typeListId = "";
    private LinearLayout parent_layout, type_ll3;
    private String totalPage = "1";
    private boolean isClearList = true;
    private SmoothRefreshLayout smooth_refresh;
    private PopupWindow comment_pw;
    private View comment_pw_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        originalCityAgencyFragmentView = inflater.inflate(R.layout.fragment_original_city_agency, container, false);
        initView();
        userId = ContextParameter.getUserInfo().getUserId();
        mLvAdapter = new OriginalAgenceVpAdapter(getActivity(), lvList);
        hqkt_lv.setAdapter(mLvAdapter);
        initData();
        hqkt_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("ticketId", lvList.get(i).getTicketId());
                AppUtils.toActivity(getActivity(), TicketGoodsDtActivity.class, bundle);
            }
        });
        myScrollView.setOnScrollListener(this);
        hqkt_lv.setOnTouchListener(this);
        smooth_refresh.setMode(SmoothRefreshLayout.MODE_LOAD_MORE);
        smooth_refresh.setFooterView(new ClassicFooter(getActivity()));
        smooth_refresh.setOnRefreshListener(new SmoothRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefreshBegin(boolean isRefresh) {
                mLvPage++;
                isClearList = false;
                changeHqktLv(typeListId);
            }

            @Override
            public void onRefreshComplete() {

            }
        });
        //当布局的状态或者控件的可见性发生改变回调的接口
        originalCityAgencyFragmentView.findViewById(R.id.parent_layout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                //这一步很重要，使得上面的tab布局和下面的tab布局重合
                onScroll(myScrollView, myScrollView.getScrollY());

            }
        });
        return originalCityAgencyFragmentView;
    }

    private void initData() {
        hqkt_ll.setVisibility(View.VISIBLE);
        hqkt_lv_rll.setVisibility(View.VISIBLE);
        Request<OriginalCityAgencyBean> request = new Request<>();
        OriginalCityAgencyBean bean = new OriginalCityAgencyBean();
        bean.setUser_id(userId);
        request.setData(bean);
        RXUtils.request(getActivity(), request, "getPartnerInfo", new SupportSubscriber<Response<OriginalCityAgencyBean>>() {

            @Override
            public void onNext(Response<OriginalCityAgencyBean> originalCityAgencyBeanResponse) {
                data = originalCityAgencyBeanResponse.getData();
//                typeList = data.getTypeList();
                direction = data.getDirection();
                List<TicketAllModel> ticketAll = data.getTicketAll();
                if (data.getTicketTypes() != null && data.getTicketTypes().size() > 0) {
                    OriginalCityAgencyFragment.this.ticketTypes = data.getTicketTypes();
                    if (type_ll.getVisibility() == View.GONE) {
                        type_ll.setVisibility(View.VISIBLE);
                        type_ll2.setVisibility(View.VISIBLE);
                    }
                    if (hqkt_lv.getVisibility() == View.GONE) {
                        hqkt_lv.setVisibility(View.VISIBLE);
                    }
                } else {
                    type_ll.setVisibility(View.GONE);
                    type_ll2.setVisibility(View.GONE);
                    hqkt_lv.setVisibility(View.GONE);
                }
                lvList.clear();
                lvList.addAll(ticketAll);
                /*int count = mLvAdapter.getCount();
                int totalHeight = 0;
                for (int i = 0; i < count; i++) {
                    View itemView = mLvAdapter.getView(i, null, hqkt_lv);
                    itemView.measure(0, 0);
                    totalHeight += itemView.getMeasuredHeight();
                }
                ViewGroup.LayoutParams params = hqkt_lv.getLayoutParams();
                params.height = totalHeight + (count - 1) * hqkt_lv.getDividerHeight();
                hqkt_lv.requestLayout();*/
                mLvAdapter.notifyDataSetChanged();
                myScrollView.smoothScrollTo(0, 0);
//                myScrollView.smoothScrollTo(0, type_ll.getTop());
                isPartner = data.getIsPartner();
                if (isPartner == 3) {
                    myPartnerRll.setVisibility(View.GONE);
                    myPartnerRll_data.setVisibility(View.GONE);
                    partner_predict_ll.setVisibility(View.GONE);
                    partner_order_ll.setVisibility(View.GONE);
                    personal_order_tag.setText("订单数量");
                    to_applay_partner_btn.setVisibility(View.VISIBLE);
                    to_applay_partner_btn.setText("申请事业合伙人");
                    toolbar.setTextViewTitle("合伙人");
                } else if (isPartner == 1) {
                    myPartnerRll.setVisibility(View.VISIBLE);
                    myPartnerRll_data.setVisibility(View.VISIBLE);
                    partner_predict_ll.setVisibility(View.VISIBLE);
                    partner_order_ll.setVisibility(View.VISIBLE);
                    partner_type_tv.setText("我的合伙人");
                    partner_detail_ll.setVisibility(View.GONE);
                    myPartnerRll.setClickable(false);
                    personal_order_tag.setText("个人订单");
                    partner_predict_tag.setText("合伙人预估");
                    partner_order_tag.setText("合伙人订单");
                    partner_promotion_tag.setText("合伙人总数");
                    has_distribute_tag.setText("事业合伙人");
                    no_distribute_tag.setText("普通合伙人");
                    to_applay_partner_btn.setVisibility(View.GONE);
                    toolbar.setTextViewTitle("城市运营商");
                } else if (isPartner == 2) {
                    myPartnerRll.setVisibility(View.VISIBLE);
                    myPartnerRll_data.setVisibility(View.VISIBLE);
                    partner_predict_ll.setVisibility(View.VISIBLE);
                    partner_order_ll.setVisibility(View.VISIBLE);
                    partner_type_tv.setText("我的合伙人");
                    partner_detail_ll.setVisibility(View.VISIBLE);
                    myPartnerRll.setClickable(true);
                    personal_order_tag.setText("个人订单");
                    partner_predict_tag.setText("普通合伙人");
                    partner_order_tag.setText("普通合伙人");
                    partner_promotion_tag.setText("合伙人总数");
                    has_distribute_tag.setText("今日合伙人");
                    no_distribute_tag.setText("本周合伙人");
                    to_applay_partner_btn.setVisibility(View.GONE);
//                    to_applay_partner_btn.setText("申请城市运营商");
                    toolbar.setTextViewTitle("事业合伙人");
                }
                distinctProfit.setText(data.getProfit().getExpectedProfit());
                String candeal = data.getProfit().getSettlementProfit();
                canDeal.setText(candeal);
                income_banlance_tv.setText(data.getProfit().getRealProfit());
                if (candeal.equals("0.00")) {
                    go_pay_btn.setClickable(false);
                    go_pay_btn.setBackground(getResources().getDrawable(R.drawable.button_bg_no_click));
                } else {
                    go_pay_btn.setClickable(true);
                    go_pay_btn.setBackground(getResources().getDrawable(R.drawable.button_bg_yes_click));
                }

                partner_promotion_num.setText(data.getPartnerInfo().getPartnerPlace());
                has_distribute_num.setText(data.getPartnerInfo().getAssignedPlace());
                no_distribute_num.setText(data.getPartnerInfo().getUnassignPlace());

                today_personal_order.setText(data.getOrderDetail().getPersonalToday());
                today_partner_predict.setText(data.getProfitDetail().getPartnerToday());
                today_personal_predict.setText(data.getProfitDetail().getPersonalToday());
                today_partner_order.setText(data.getOrderDetail().getPartnerToday());

                yestoday_personal_order.setText(data.getOrderDetail().getPersonalLastday());
                yestoday_partner_predict.setText(data.getProfitDetail().getPartnerLastday());
                yestoday_personal_predict.setText(data.getProfitDetail().getPersonalLastday());
                yestoday_partner_order.setText(data.getOrderDetail().getPartnerLastday());

                this_month_personal_order.setText(data.getOrderDetail().getPersonalThisMonth());
                this_month_partner_predict.setText(data.getProfitDetail().getPartnerThisMonth());
                this_month_personal_predict.setText(data.getProfitDetail().getPersonalThisMonth());
                this_month_partner_order.setText(data.getOrderDetail().getPartnerThisMonth());

                last_month_personal_order.setText(data.getOrderDetail().getPersonalLastMonth());
                last_month_partner_predict.setText(data.getProfitDetail().getPartnerLastMonth());
                last_month_personal_predict.setText(data.getProfitDetail().getPersonalLastMonth());
                last_month_partner_order.setText(data.getOrderDetail().getPartnerLastMonth());
//                addFragmentList();
                /*vp.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        return fragmentList.get(position);
                    }

                    @Override
                    public int getCount() {
                        return fragmentList.size();
                    }

                    @Override
                    public CharSequence getPageTitle(int position) {
                        return ticketTypes.get(position).getTypeName();
                    }
                });*/

//                mTab_top.setupWithViewPager(vp);
                loadingDialog.dismiss();
            }

            @Override
            public void onCompleted() {
//                mHandler.sendEmptyMessage(0);
            }
        });

        getTypeListNet();
    }

    private void getTypeListNet() {
        Request request1 = new Request();
        TypeListBean typeListBean = new TypeListBean();
        typeListBean.setAreaType(areaType);
        request1.setData(typeListBean);
        RXUtils.request(getActivity(), request1, "commodityType", new SupportSubscriber<Response<TypeListBean>>() {
            @Override
            public void onNext(Response<TypeListBean> typeListBeanResponse) {
                typeList = typeListBeanResponse.getData().getTypeList();
            }
        });
    }

    private void initTabLayout() {
        for (int i = 0; i < ticketTypes.size(); i++) {
            TabLayout.Tab tab = mTab_top.newTab();
            String typeName = ticketTypes.get(i).getTypeName();
            tab.setText(typeName);
            mTab_top.addTab(tab);
        }

        mTab_top.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                typeId = ticketTypes.get(tab.getPosition()).getTypeId();
                mLvPage = 1;
                isClearList = true;
                changeHqktLv(typeId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void changeHqktLv(String typeId) {
        loadingDialog.show();
        Request<HomeTicketsModel> request = new Request<>();
        HomeTicketsModel homeTicketsModel = new HomeTicketsModel();
        homeTicketsModel.setTypeId(typeId);
        homeTicketsModel.setAreaType(areaType);
        homeTicketsModel.setSortType(sortType);
        homeTicketsModel.setPage(String.valueOf(mLvPage));
        request.setData(homeTicketsModel);
        RXUtils.request(getActivity(), request, "getHomeTickets", new SupportSubscriber<Response<HomeTicketsModel>>() {

            @Override
            public void onNext(Response<HomeTicketsModel> homeTicketsModelResponse) {
                totalPage = homeTicketsModelResponse.getData().getTotalPage();
                if (isClearList) {
                    lvList.clear();
                }
                lvList.addAll(homeTicketsModelResponse.getData().getTickets());
                if (homeTicketsModelResponse.getData().getTickets() == null || homeTicketsModelResponse.getData().getTickets().size() == 0) {
                    Toast.makeText(OriginalCityAgencyFragment.this.getActivity(), "没有更多数据了!", Toast.LENGTH_SHORT).show();
                }
                if (lvList.size() == 0) {
                    hqkt_lv.setVisibility(View.GONE);
                    empty_ll.setVisibility(View.VISIBLE);
                } else {
                    hqkt_lv.setVisibility(View.VISIBLE);
                    empty_ll.setVisibility(View.GONE);
                }
                mLvAdapter.notifyDataSetChanged();
                smooth_refresh.refreshComplete();
                loadingDialog.dismiss();
            }
        });
    }

    private void initView() {
        to_applay_partner_btn = ((Button) originalCityAgencyFragmentView.findViewById(R.id.to_applay_partner_btn));
        toolbar = (CustomToolbar) originalCityAgencyFragmentView.findViewById(R.id.original_city_agency_toolbar);
        myScrollView = (MyScrollView) originalCityAgencyFragmentView.findViewById(R.id.original_agency_scollview);
        distinctProfit = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.distinct_profit_tv));
        partner_type_tv = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.partner_type_tv));
        partner_order_tag = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.partner_order_tag));
        partner_predict_tag = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.partner_predict_tag));
        partner_promotion_tag = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.partner_promotion_tag));
        has_distribute_tag = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.has_distribute_tag));
        no_distribute_tag = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.no_distribute_tag));
        canDeal = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.can_deal_tv));
        predict_profit_help = ((ImageView) originalCityAgencyFragmentView.findViewById(R.id.predict_profit_help));
        income_help = ((ImageView) originalCityAgencyFragmentView.findViewById(R.id.income_help));
        can_deal_help = ((ImageView) originalCityAgencyFragmentView.findViewById(R.id.can_deal_help));
        empty_ll = ((LinearLayout) originalCityAgencyFragmentView.findViewById(R.id.empty_ll));
        partner_detail_ll = ((LinearLayout) originalCityAgencyFragmentView.findViewById(R.id.partner_detail_ll));
        go_pay_btn = ((Button) originalCityAgencyFragmentView.findViewById(R.id.original_city_agency_go_pay));
        smooth_refresh = ((SmoothRefreshLayout) originalCityAgencyFragmentView.findViewById(R.id.smooth_refresh));

        parent_layout = ((LinearLayout) originalCityAgencyFragmentView.findViewById(R.id.parent_layout));
        type_ll3 = ((LinearLayout) originalCityAgencyFragmentView.findViewById(R.id.type_ll3));
        screen_bg_ll = ((LinearLayout) originalCityAgencyFragmentView.findViewById(R.id.screen_bg_ll));
        near_rll = ((RelativeLayout) originalCityAgencyFragmentView.findViewById(R.id.near_rll));
        type_rll = ((RelativeLayout) originalCityAgencyFragmentView.findViewById(R.id.type_rll));
        comment_rll = ((RelativeLayout) originalCityAgencyFragmentView.findViewById(R.id.comment_rll));
        type_ll = ((LinearLayout) originalCityAgencyFragmentView.findViewById(R.id.ll));
        type_ll2 = ((LinearLayout) originalCityAgencyFragmentView.findViewById(R.id.ll2));
        nearIv = ((ImageView) originalCityAgencyFragmentView.findViewById(R.id.near_iv));
        nearIv3 = ((ImageView) originalCityAgencyFragmentView.findViewById(R.id.near_iv3));
        typeIv = ((ImageView) originalCityAgencyFragmentView.findViewById(R.id.type_iv));
        typeIv3 = ((ImageView) originalCityAgencyFragmentView.findViewById(R.id.type_iv3));
        commentIv = ((ImageView) originalCityAgencyFragmentView.findViewById(R.id.comment_iv));
        commentIv3 = ((ImageView) originalCityAgencyFragmentView.findViewById(R.id.comment_iv3));
        nearTv = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.near_tv));
        nearTv3 = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.near_tv3));
        typeTv = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.type_tv));
        typeTv3 = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.type_tv3));
        commentTv = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.comment_tv));
        commentTv3 = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.comment_tv3));
//        mTab_top = ((TabLayout) originalCityAgencyFragmentView.findViewById(R.id.hqkt_tab_top));
//        mTab = ((TabLayout) originalCityAgencyFragmentView.findViewById(R.id.hqkt_tab));
//        vp = ((ViewPageInScrollView) originalCityAgencyFragmentView.findViewById(R.id.hqkt_vp));
        hqkt_lv = ((NestedListView) originalCityAgencyFragmentView.findViewById(R.id.hqkt_lv));
//        hqkt_lv = ((ListView) originalCityAgencyFragmentView.findViewById(R.id.hqkt_lv));
        reload_btn = ((com.rey.material.widget.Button) originalCityAgencyFragmentView.findViewById(R.id.btn_empty_reload));
        myPartnerRll = ((RelativeLayout) originalCityAgencyFragmentView.findViewById(R.id.city_agenty_mypartner_rll));
        myPartnerRll_data = ((LinearLayout) originalCityAgencyFragmentView.findViewById(R.id.city_agenty_mypartner_rll_data));
        partner_order_ll = ((LinearLayout) originalCityAgencyFragmentView.findViewById(R.id.partner_order_ll));
        partner_predict_ll = ((LinearLayout) originalCityAgencyFragmentView.findViewById(R.id.partner_predict_ll));
        hqkt_ll = ((LinearLayout) originalCityAgencyFragmentView.findViewById(R.id.hqkt_ll));
        personal_order_tag = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.personal_order_tag));
        allOrdersRll = ((RelativeLayout) originalCityAgencyFragmentView.findViewById(R.id.city_agenty_all_orders_rll));
        hqkt_lv_rll = ((RelativeLayout) originalCityAgencyFragmentView.findViewById(R.id.hqkt_lv_rll));

        income_banlance_tv = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.income_banlance_tv));
        partner_promotion_num = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.partner_promotion_num));
        has_distribute_num = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.has_distribute_num));
        no_distribute_num = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.no_distribute_num));
        today_personal_predict = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.today_personal_predict));
        yestoday_personal_predict = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.yestoday_personal_predict));
        this_month_personal_predict = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.this_month_personal_predict));
        last_month_personal_predict = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.last_month_personal_predict));
        today_partner_predict = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.today_partner_predict));
        yestoday_partner_predict = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.yestoday_partner_predict));
        this_month_partner_predict = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.this_month_partner_predict));
        last_month_partner_predict = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.last_month_partner_predict));

        today_personal_order = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.today_personal_order));
        yestoday_personal_order = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.yestoday_personal_order));
        this_month_personal_order = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.this_month_personal_order));
        last_month_personal_order = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.last_month_personal_order));
        today_partner_order = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.today_partner_order));
        yestoday_partner_order = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.yestoday_partner_order));
        this_month_partner_order = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.this_month_partner_order));
        last_month_partner_order = ((TextView) originalCityAgencyFragmentView.findViewById(R.id.last_month_partner_order));

        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
        myPartnerRll.setOnClickListener(this);
        allOrdersRll.setOnClickListener(this);
        go_pay_btn.setOnClickListener(this);
        reload_btn.setOnClickListener(this);
        to_applay_partner_btn.setOnClickListener(this);
        predict_profit_help.setOnClickListener(this);
        income_help.setOnClickListener(this);
        can_deal_help.setOnClickListener(this);
        near_rll.setOnClickListener(this);
        type_rll.setOnClickListener(this);
        comment_rll.setOnClickListener(this);

        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                getActivity().finish();
            }

            @Override
            public void onRightImgClick() {
                /*Bundle bundle = new Bundle();
                bundle.putString("url", direction);
                AppUtils.toActivity(getActivity(), OriginalCityAgencyHelpActivity.class, bundle);*/
            }
        });

    }

    private void addFragmentList() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < ticketTypes.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("isAgency", data.getIsPartner());
            bundle.putString("typeId", ticketTypes.get(i).getTypeId());
            fragmentList.add(OriginalCityAgencyVpBaseFg.getInstance(bundle));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.city_agenty_mypartner_rll:
                AppUtils.toActivity(getActivity(), OriginalMyPartnerActivity.class);
                break;
            case R.id.city_agenty_all_orders_rll:
                Bundle bundle = new Bundle();
                bundle.putInt("isPartner", isPartner);
                AppUtils.toActivity(getActivity(), OriginalCityAgencyAllOrderActivity_New.class, bundle);
                break;
            case R.id.original_city_agency_go_pay:
                if (canPayDialog == null) {
                    View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.can_pay_dialog, null, false);
                    canPayDialog = new AlertDialog.Builder(getActivity())
                            .setView(dialogView)
                            .create();
                    yesBtn = ((TextView) dialogView.findViewById(R.id.yes_btn));
                    noBtn = ((TextView) dialogView.findViewById(R.id.no_btn));
                    yesBtn.setOnClickListener(this);
                    noBtn.setOnClickListener(this);
                }
                canPayDialog.show();
                break;
            case R.id.yes_btn:
                Request<CanPayBean> request = new Request<>();
                RXUtils.request(getActivity(), request, "settlement", new SupportSubscriber<Response<CanPayBean>>() {

                    @Override
                    public void onNext(Response<CanPayBean> canPayBeanResponse) {
                        canPayMsg = canPayBeanResponse.getMessage();
                        Toast.makeText(OriginalCityAgencyFragment.this.getActivity(), canPayMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCompleted() {
                        initData();
                        Log.d("雨落无痕丶", "onCompleted:rrr ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseError(Response response) {
                        Toast.makeText(OriginalCityAgencyFragment.this.getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                canPayDialog.dismiss();
                break;
            case R.id.no_btn:
                if (canPayDialog != null) {
                    canPayDialog.dismiss();
                }
                break;
            case R.id.btn_empty_reload:
                mLvPage = 1;
                isClearList = true;
                changeHqktLv(typeId);
                break;
            case R.id.to_applay_partner_btn:
                Bundle applayBundle = new Bundle();
                if (to_applay_partner_btn.getText().toString().equals("申请事业合伙人")) {
                    applayBundle.putInt("type", 2);
                } else if (to_applay_partner_btn.getText().toString().equals("申请城市运营商")) {
                    applayBundle.putInt("type", 3);
                }
                AppUtils.toActivity(getActivity(), ApplayPartnerActivity.class, applayBundle);
                break;
            case R.id.predict_profit_help:
                Intent intent = new Intent(getActivity(), TaoBaoActivity.class);
                intent.putExtra("URL", data.getProfitDirection());
                getActivity().startActivity(intent);
                break;
            case R.id.income_help:
                Intent intent1 = new Intent(getActivity(), TaoBaoActivity.class);
                intent1.putExtra("URL", data.getRealProfitDirection());
                getActivity().startActivity(intent1);
                break;
            case R.id.can_deal_help:
                Intent intent2 = new Intent(getActivity(), TaoBaoActivity.class);
                intent2.putExtra("URL", data.getSettleDirection());
                getActivity().startActivity(intent2);
                break;
            case R.id.type_rll:
                typeTv.setTextColor(Color.parseColor("#ff4460"));
                typeTv3.setTextColor(Color.parseColor("#ff4460"));
                typeIv.setImageResource(R.drawable.icon_shangsanjiao);
                typeIv3.setImageResource(R.drawable.icon_shangsanjiao);
                type_ll3.setVisibility(View.VISIBLE);
                /*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, -type_ll.getTop(), 0, 0);
                myScrollView.setLayoutParams(layoutParams);
                myScrollView.smoothScrollTo(0, 0);
                hqkt_lv.getParent().requestDisallowInterceptTouchEvent(true);
                hqkt_lv.smoothScrollToPosition(0);*/
                myScrollView.smoothScrollTo(0, type_ll.getTop());
                Rect rect = new Rect();
                getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//                top = (int) (type_ll.getY());
                top = toolbar.getLayoutParams().height + type_ll.getLayoutParams().height;
                screen_bg_ll.setVisibility(View.VISIBLE);
                nearIv.setImageResource(R.drawable.icon_xiasanjiao);
                typeIv.setImageResource(R.drawable.icon_xiasanjiao_red);
//                commentIv.setImageResource(R.drawable.icon_xiasanjiao);
                nearTv.setTextColor(Color.parseColor("#333333"));
                typeTv.setTextColor(Color.parseColor("#ff2d47"));
                commentTv.setTextColor(Color.parseColor("#333333"));
                SecondaryTypeDialog typeDialog = new SecondaryTypeDialog(getActivity(), typeList, top);
                typeDialog.setOnClassificationSelectListener(new SecondaryTypeDialog.OnClassificationSelectListener() {

                    @Override
                    public void select(GoodsType type) {
//                        changeTypeData(type.getTypeId());
                        mLvPage = 1;
                        isClearList = true;
                        typeListId = type.getTypeId();
                        typeTv.setText(type.getTypeName());
                        changeHqktLv(typeListId);
                    }
                });
                typeDialog.show();
                typeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        typeTv.setTextColor(Color.parseColor("#333333"));
                        typeTv3.setTextColor(Color.parseColor("#333333"));
                        typeIv.setImageResource(R.drawable.icon_xiasanjiao);
                        typeIv3.setImageResource(R.drawable.icon_xiasanjiao);
                        type_ll3.setVisibility(View.GONE);
                        screen_bg_ll.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.near_rll:
                nearTv.setTextColor(Color.parseColor("#ff4460"));
                nearTv3.setTextColor(Color.parseColor("#ff4460"));
                nearIv.setImageResource(R.drawable.icon_shangsanjiao);
                nearIv3.setImageResource(R.drawable.icon_shangsanjiao);
                type_ll3.setVisibility(View.VISIBLE);
              /*  RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, -type_ll.getTop(), 0, 0);
                myScrollView.setLayoutParams(params);
                myScrollView.smoothScrollTo(0, 0);
                hqkt_lv.smoothScrollToPosition(0);*/
                myScrollView.smoothScrollTo(0, type_ll.getTop());
                if (hqkt_type_pw == null || hqkt_type_pw_view == null) {
                    hqkt_type_pw_view = LayoutInflater.from(getActivity()).inflate(R.layout.hqkt_type_pw_item, null);
                    near_type_tv = ((TextView) hqkt_type_pw_view.findViewById(R.id.near_type_tv));
                    near_type_tv.setOnClickListener(this);
                    hqkt_type_pw = new PopupWindow(hqkt_type_pw_view, WindowManager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
//                changeScreenBg();
                screen_bg_ll.setVisibility(View.VISIBLE);
                hqkt_type_pw.setOutsideTouchable(true);
                hqkt_type_pw.setFocusable(true);
                hqkt_type_pw.setBackgroundDrawable(new BitmapDrawable());
                hqkt_type_pw.showAsDropDown(type_ll3);
                hqkt_type_pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        restoreScreenBg();
                        screen_bg_ll.setVisibility(View.GONE);
                        nearIv.setImageResource(R.drawable.icon_xiasanjiao);
                        nearIv3.setImageResource(R.drawable.icon_xiasanjiao);
                        type_ll3.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.comment_rll:
                commentTv.setTextColor(Color.parseColor("#ff4460"));
                commentTv3.setTextColor(Color.parseColor("#ff4460"));
                commentIv.setImageResource(R.drawable.icon_shangsanjiao);
                commentIv3.setImageResource(R.drawable.icon_shangsanjiao);
                type_ll3.setVisibility(View.VISIBLE);
                myScrollView.smoothScrollTo(0, type_ll.getTop());
                if (comment_pw == null || comment_pw_view == null) {
                    comment_pw_view = LayoutInflater.from(getActivity()).inflate(R.layout.comment_pw_item, null);
                    ((LinearLayout) comment_pw_view.findViewById(R.id.high_commission)).setOnClickListener(this);
                    ((LinearLayout) comment_pw_view.findViewById(R.id.low_commission)).setOnClickListener(this);
                    ((LinearLayout) comment_pw_view.findViewById(R.id.activity_extend)).setOnClickListener(this);
                    ((LinearLayout) comment_pw_view.findViewById(R.id.nomal_extend)).setOnClickListener(this);
                    ((LinearLayout) comment_pw_view.findViewById(R.id.all_type)).setOnClickListener(this);
                    comment_pw = new PopupWindow(comment_pw_view, WindowManager.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                screen_bg_ll.setVisibility(View.VISIBLE);
                comment_pw.setOutsideTouchable(true);
                comment_pw.setFocusable(true);
                comment_pw.setBackgroundDrawable(new BitmapDrawable());
                int xoff = -(ScreenUtils.getScreenWidth(getActivity()) - comment_pw.getWidth());
                comment_pw.showAsDropDown(type_ll3, -xoff, 0);
                comment_pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        screen_bg_ll.setVisibility(View.GONE);
                        commentIv.setImageResource(R.drawable.icon_xiasanjiao);
                        commentIv3.setImageResource(R.drawable.icon_xiasanjiao);
                        type_ll3.setVisibility(View.GONE);
                        commentTv.setTextColor(Color.parseColor("#333333"));
                        commentTv3.setTextColor(Color.parseColor("#333333"));
                    }
                });

                /*myScrollView.smoothScrollTo(0, type_ll.getTop());
                if (sortType.equals("0")) {
                    sortType = "1";
                    commentIv.setImageResource(R.drawable.icon_didaogao);
                } else if (sortType.equals("1")) {
                    sortType = "-1";
                    commentIv.setImageResource(R.drawable.icon_yongjin);
                } else if (sortType.equals("-1")) {
                    sortType = "0";
                    commentIv.setImageResource(R.drawable.icon_gaodaodi);
                }
                mLvPage = 1;
                isClearList = true;
                changeHqktLv(typeListId);*/
                break;
            case R.id.near_type_tv:
                String string = near_type_tv.getText().toString();
                if (string != null) {
                    typeListId = "183";
                    typeTv.setText("分类");
                    if (string.equals("周边产品")) {
                        if (!ContextParameter.isLocation()) {
                            new AlertDialog.Builder(getActivity()).setTitle("提示")
                                    .setMessage("周边产品需要应用开启手机定位功能才能正常使用！")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .create()
                                    .show();
                        }
                        areaType = "1";
                        getTypeListNet();
                        mLvPage = 1;
                        isClearList = true;
                        changeHqktLv(typeListId);
                        hqkt_type_pw.dismiss();
                        nearTv.setText("周边产品");
                        nearTv.setTextColor(Color.parseColor("#ff2d47"));
                        near_type_tv.setText("全国特产");
                    } else if (string.equals("全国特产")) {
                        areaType = "2";
                        getTypeListNet();
                        mLvPage = 1;
                        isClearList = true;
                        changeHqktLv(typeListId);
                        hqkt_type_pw.dismiss();
                        nearTv.setText("全国特产");
                        nearTv.setTextColor(Color.parseColor("#ff2d47"));
                        near_type_tv.setText("周边产品");
                    }
                }
                break;
            case R.id.all_type:
                commentTv.setText("全选");
                commentTv.setTextColor(getResources().getColor(R.color.main_color_red));
                commentTv3.setText("全选");
                commentTv3.setTextColor(getResources().getColor(R.color.main_color_red));
                sortType = "0";
                mLvPage = 1;
                isClearList = true;
                changeHqktLv(typeListId);
                comment_pw.dismiss();
                break;
            case R.id.high_commission:
                commentTv.setText("高佣金");
                commentTv.setTextColor(getResources().getColor(R.color.main_color_red));
                commentTv3.setText("高佣金");
                commentTv3.setTextColor(getResources().getColor(R.color.main_color_red));
                sortType = "-1";
                mLvPage = 1;
                isClearList = true;
                changeHqktLv(typeListId);
                comment_pw.dismiss();
                break;
            case R.id.low_commission:
                commentTv.setText("低佣金");
                commentTv.setTextColor(getResources().getColor(R.color.main_color_red));
                commentTv3.setText("低佣金");
                commentTv3.setTextColor(getResources().getColor(R.color.main_color_red));
                sortType = "1";
                mLvPage = 1;
                isClearList = true;
                changeHqktLv(typeListId);
                comment_pw.dismiss();
                break;
            case R.id.activity_extend:
                commentTv.setText("活动推广");
                commentTv.setTextColor(getResources().getColor(R.color.main_color_red));
                commentTv3.setText("活动推广");
                commentTv3.setTextColor(getResources().getColor(R.color.main_color_red));
                sortType = "3";
                mLvPage = 1;
                isClearList = true;
                changeHqktLv(typeListId);
                comment_pw.dismiss();
                break;
            case R.id.nomal_extend:
                commentTv.setText("普通推广");
                commentTv.setTextColor(getResources().getColor(R.color.main_color_red));
                commentTv3.setText("普通推广");
                commentTv3.setTextColor(getResources().getColor(R.color.main_color_red));
                sortType = "4";
                mLvPage = 1;
                isClearList = true;
                changeHqktLv(typeListId);
                comment_pw.dismiss();
                break;
        }
    }

    @Override
    public void onScroll(ScrollView scrollView, int scrollY) {
        int mTab2ParentTop = Math.max(scrollY, type_ll2.getTop());
//        mTab_top.layout(0, mTab2ParentTop, mTab_top.getWidth(), mTab2ParentTop + mTab_top.getHeight());
        type_ll.layout(0, mTab2ParentTop, type_ll.getWidth(), mTab2ParentTop + type_ll.getHeight());
    }

    private void changeScreenBg() {
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = (float) 0.5;
        getActivity().getWindow().setAttributes(params);
    }

    private void restoreScreenBg() {
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = (float) 1;
        getActivity().getWindow().setAttributes(params);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 0);
                myScrollView.setLayoutParams(layoutParams);
                break;
        }
        return false;
    }
}
