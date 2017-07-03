package com.weslide.lovesmallscreen.activitys;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.CityAgencyAllOrderVpBaseFg;
import com.weslide.lovesmallscreen.fragments.OriginalCityAgencyAllOrderFragment;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/3/23.
 */
public class OriginalCityAgencyAllOrderActivity_New extends BaseActivity {

    private Button searchBtn;
    private RadioGroup rg;
    private ViewPager vp;
    private List<CityAgencyAllOrderVpBaseFg> list;
    private CustomToolbar toolbar;
    private EditText search_edt;
    private CityAgencyAllOrderVpBaseFg fg;
    private String orderType;

    private OriginalCityAgencyAllOrderFragment originalCityAgencyAllOrderFragment = new OriginalCityAgencyAllOrderFragment();
    private int isPartner;
    //    private RadioGroup all_order_rg;
    private LoadingDialog loadingDialog;
    private Button personal_order_tv;
    private ImageView back_iv;
    private ImageView sanjiao_iv;
    private View pwView;
    private PopupWindow popupWindow;
    private RelativeLayout sy_partner_order_rll, normal_partner_order_rll;
    private RelativeLayout toolbar_rll;
    private RelativeLayout personal_partner_order_rll;
    private LinearLayout screen_bg;
    private int currentItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_agency_all_order_new);
        isPartner = getIntent().getExtras().getInt("isPartner");
        orderType = isPartner+"";
        initView();
        initData();
        initListenner();
    }

    private void initListenner() {
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (isPartner == 3){
            personal_order_tv.setEnabled(false);
            sanjiao_iv.setVisibility(View.GONE);
        }else {
            personal_order_tv.setEnabled(true);
            sanjiao_iv.setVisibility(View.VISIBLE);
        }
        personal_order_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sanjiao_iv.setImageResource(R.drawable.icon_shangsanjiao);
                if (pwView == null) {
                    pwView = LayoutInflater.from(OriginalCityAgencyAllOrderActivity_New.this).inflate(R.layout.all_order_pw_item, null);
                    sy_partner_order_rll = ((RelativeLayout) pwView.findViewById(R.id.sy_partner_order_rll));
                    normal_partner_order_rll = ((RelativeLayout) pwView.findViewById(R.id.normal_partner_order_rll));
                    personal_partner_order_rll = ((RelativeLayout) pwView.findViewById(R.id.personal_partner_order_rll));
                }
                popupWindow = new PopupWindow(pwView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                sy_partner_order_rll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadingDialog.show();
                        orderType = "2";
                        personal_order_tv.setText("事业合伙人订单");
//                        initData();
                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":2");
                        fg.orderType = "2";
                        fg.upDateOrder("");

                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":3");
                        fg.orderType = "2";
                        fg.upDateOrder("");

                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":0");
                        fg.orderType = "2";
                        fg.upDateOrder("");

                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":1");
                        fg.orderType = "2";
                        fg.upDateOrder("");

                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                normal_partner_order_rll.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        loadingDialog.show();
                        orderType = "3";
                        personal_order_tv.setText("合伙人订单");
                        currentItem = vp.getCurrentItem();
//                        initData();
                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":2");
                        fg.orderType = "3";
                        fg.upDateOrder("");

                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":3");
                        fg.orderType = "3";
                        fg.upDateOrder("");

                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":0");
                        fg.orderType = "3";
                        fg.upDateOrder("");

                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":1");
                        fg.orderType = "3";
                        fg.upDateOrder("");
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });

                personal_partner_order_rll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadingDialog.show();
                        if (isPartner == 1) {
                            orderType = "1";
                        } else if (isPartner == 2) {
                            orderType = "2";
                        } else if (isPartner == 3) {
                            orderType = "3";
                        }
                        personal_order_tv.setText("个人订单");
//                        initData();

                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":2");
                        fg.orderType = orderType;
                        fg.upDateOrder("");

                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":3");
                        fg.orderType = orderType;
                        fg.upDateOrder("");

                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":0");
                        fg.orderType = orderType;
                        fg.upDateOrder("");

                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":1");
                        fg.orderType = orderType;
                        fg.upDateOrder("");

                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });

                if (isPartner == 1) {
                    if (personal_order_tv.getText().toString().equals("个人订单")){
                        personal_partner_order_rll.setVisibility(View.GONE);
                        normal_partner_order_rll.setVisibility(View.VISIBLE);
                        sy_partner_order_rll.setVisibility(View.VISIBLE);
                    } else if (personal_order_tv.getText().toString().equals("合伙人订单")) {
                        personal_partner_order_rll.setVisibility(View.VISIBLE);
                        sy_partner_order_rll.setVisibility(View.VISIBLE);
                        normal_partner_order_rll.setVisibility(View.GONE);
                    } else if (personal_order_tv.getText().toString().equals("事业合伙人订单")) {
                        personal_partner_order_rll.setVisibility(View.VISIBLE);
                        sy_partner_order_rll.setVisibility(View.GONE);
                        normal_partner_order_rll.setVisibility(View.VISIBLE);
                    }
                    Log.d("雨落无痕丶", "onClick: yy==="+isPartner);
                } else if (isPartner == 2) {
                    Log.d("雨落无痕丶", "onClick: yy==="+isPartner);
                    sy_partner_order_rll.setVisibility(View.GONE);
                    if (personal_order_tv.getText().toString().equals("个人订单")){
                        personal_partner_order_rll.setVisibility(View.GONE);
                        normal_partner_order_rll.setVisibility(View.VISIBLE);
                    } else if (personal_order_tv.getText().toString().equals("合伙人订单")) {
                        personal_partner_order_rll.setVisibility(View.VISIBLE);
                        normal_partner_order_rll.setVisibility(View.GONE);
                    }
                }
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                popupWindow.setAnimationStyle(R.style.pwStyle);
//                popupWindow.showAtLocation(pwView, Gravity.TOP, 0, toolbar_rll.getMeasuredHeight());
                popupWindow.showAsDropDown(toolbar_rll);
                changeScreenBg();
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        restoreScreenBg();
                        sanjiao_iv.setImageResource(R.drawable.icon_xiasanjiao);
                    }
                });
            }
        });
        /*all_order_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.personal_order_rb:
                        loadingDialog.show();
                        orderType = "1";
                        initData();
                        break;
                    case R.id.partner_order_rb:
                        loadingDialog.show();
                        orderType = "2";
                        initData();
                        break;
                }
            }
        });*/

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.city_agency_all_order_today_rb:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.city_agency_all_order_yestoday_rb:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.city_agency_all_order_this_month_rb:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.city_agency_all_order_last_month_rb:
                        vp.setCurrentItem(3);
                        break;
                }
            }
        });
        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int radioButtonId = rg.getCheckedRadioButtonId();
                switch (radioButtonId) {
                    case R.id.city_agency_all_order_today_rb:
                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":0");
                        fg.upDateOrder(charSequence.toString());
                        break;
                    case R.id.city_agency_all_order_yestoday_rb:
                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":1");
                        fg.upDateOrder(charSequence.toString());
                        break;
                    case R.id.city_agency_all_order_this_month_rb:
                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":2");
                        fg.upDateOrder(charSequence.toString());
                        break;
                    case R.id.city_agency_all_order_last_month_rb:
                        fg = (CityAgencyAllOrderVpBaseFg) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":3");
                        fg.upDateOrder(charSequence.toString());
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        /*if (isPartner == 1) {
            all_order_rg.setVisibility(View.GONE);
            personal_order_tv.setVisibility(View.VISIBLE);
        }else if (isPartner == 2){
            all_order_rg.setVisibility(View.VISIBLE);
            personal_order_tv.setVisibility(View.GONE);
        }*/
    }

    private void initView() {
        searchBtn = ((Button) findViewById(R.id.original_all_order_search_btn));
        rg = ((RadioGroup) findViewById(R.id.city_agency_all_order_rg));
//        all_order_rg = ((RadioGroup) findViewById(R.id.all_order_rg));
        vp = ((ViewPager) findViewById(R.id.city_agency_all_order_vp));
        search_edt = ((EditText) findViewById(R.id.all_order_search_edt));
        personal_order_tv = ((Button) findViewById(R.id.personal_order_tv));
        sanjiao_iv = ((ImageView) findViewById(R.id.sanjiao_iv));
        back_iv = ((ImageView) findViewById(R.id.back_iv));
        toolbar_rll = ((RelativeLayout) findViewById(R.id.toolbar_rll));
        screen_bg = ((LinearLayout) findViewById(R.id.screen_bg));
        EventBus.getDefault().register(this);
    }

    private void initData() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        addFragments();
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        vp.setOffscreenPageLimit(3);
//        ((RadioButton) rg.getChildAt(currentItem)).setChecked(true);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) rg.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addFragments() {
        list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("time_num", String.valueOf(i + 1));
            bundle.putString("orderType", orderType);
            Log.d("雨落无痕丶", "addFragments: "+orderType);
            list.add(CityAgencyAllOrderVpBaseFg.getInstance(bundle));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void dismissDialog(String msg) {
        if (msg.equals("order")) {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }

    private void changeScreenBg() {
        screen_bg.setVisibility(View.VISIBLE);
        /*WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = (float) 0.6;
        this.getWindow().setAttributes(params);*/
    }

    private void restoreScreenBg() {
        screen_bg.setVisibility(View.GONE);
        /*WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = (float) 1;
        this.getWindow().setAttributes(params);*/
    }
}
