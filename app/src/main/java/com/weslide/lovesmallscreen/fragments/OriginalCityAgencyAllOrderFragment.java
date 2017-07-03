package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/3/23.
 */
public class OriginalCityAgencyAllOrderFragment extends BaseFragment {


    private View city_agency_all_order_fg;
    private Button searchBtn;
    private RadioGroup rg;
    private ViewPager vp;
    private List<CityAgencyAllOrderVpBaseFg> list;
    private CustomToolbar toolbar;
    private EditText search_edt;
    private CityAgencyAllOrderVpBaseFg fg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        city_agency_all_order_fg = inflater.inflate(R.layout.fragment_city_agency_all_order, container, false);
        initView();

        return city_agency_all_order_fg;
    }

    private void initView() {
        searchBtn = ((Button) city_agency_all_order_fg.findViewById(R.id.original_all_order_search_btn));
        rg = ((RadioGroup) city_agency_all_order_fg.findViewById(R.id.city_agency_all_order_rg));
        vp = ((ViewPager) city_agency_all_order_fg.findViewById(R.id.city_agency_all_order_vp));
        toolbar = ((CustomToolbar) city_agency_all_order_fg.findViewById(R.id.all_order_toolbar));
        search_edt = ((EditText) city_agency_all_order_fg.findViewById(R.id.all_order_search_edt));
        addFragments();
        vp.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

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

        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                getActivity().finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });

        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int radioButtonId = rg.getCheckedRadioButtonId();
                switch (radioButtonId){
                    case R.id.city_agency_all_order_today_rb:
                        fg = (CityAgencyAllOrderVpBaseFg) getFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":0");
                        fg.upDateOrder(charSequence.toString());
                        break;
                    case R.id.city_agency_all_order_yestoday_rb:
                        fg = (CityAgencyAllOrderVpBaseFg) getFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":1");
                        fg.upDateOrder(charSequence.toString());
                        break;
                    case R.id.city_agency_all_order_this_month_rb:
                        fg = (CityAgencyAllOrderVpBaseFg) getFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":2");
                        fg.upDateOrder(charSequence.toString());
                        break;
                    case R.id.city_agency_all_order_last_month_rb:
                        fg = (CityAgencyAllOrderVpBaseFg) getFragmentManager().findFragmentByTag("android:switcher:" + R.id.city_agency_all_order_vp + ":3");
                        fg.upDateOrder(charSequence.toString());
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void addFragments() {
        list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("time_num",String.valueOf(i+1));
            list.add(CityAgencyAllOrderVpBaseFg.getInstance(bundle));
        }
    }
}
