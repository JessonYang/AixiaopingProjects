package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.MyPartners;
import com.weslide.lovesmallscreen.views.adapters.OriginalMyPartnerLvAdapter;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

import java.util.ArrayList;

/**
 * Created by YY on 2017/3/23.
 */
public class OriginalMyPartnerFragment extends BaseFragment {

    private View originalMyPartnerFgView;
    private CustomToolbar customToolbar;
    private ListView lv;
    private ViewPager vp;
    private MyPartners data;
    private RadioGroup rg;
    private ArrayList<MyPartnerVpBaseFg> fragmentList;
    private OriginalMyPartnerLvAdapter mAdapter;
    private ImageView wuhehuoren_iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        originalMyPartnerFgView = inflater.inflate(R.layout.fragment_original_my_partner, container, false);
        /*initView();
        Request<MyPartners> request = new Request<>();
        MyPartners bean = new MyPartners();
        String userId = ContextParameter.getUserInfo().getUserId();
        bean.setUser_id(userId);
        request.setData(bean);
        RXUtils.request(getActivity(), request, "getPartnerList", new SupportSubscriber<Response<MyPartners>>() {

            @Override
            public void onNext(Response<MyPartners> response) {
                data = response.getData();
                if (data.getPartnersObs().size() == 0 || data.getPartnersObs() == null) {
                    lv.setVisibility(View.GONE);
                    wuhehuoren_iv.setVisibility(View.VISIBLE);
                } else {
                    lv.setVisibility(View.VISIBLE);
                    wuhehuoren_iv.setVisibility(View.GONE);
                }
                mAdapter = new OriginalMyPartnerLvAdapter(getActivity(), data.getPartnersObs());
                lv.setAdapter(mAdapter);
                addFragmentList();
                vp.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        return fragmentList.get(position);
                    }

                    @Override
                    public int getCount() {
                        return fragmentList.size();
                    }
                });

                vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        });*/
        return originalMyPartnerFgView;
    }

    /*private void initView() {
        customToolbar = ((CustomToolbar) originalMyPartnerFgView.findViewById(R.id.original_my_partner_toolbar));
        lv = ((ListView) originalMyPartnerFgView.findViewById(R.id.original_my_partner_lv));
        vp = ((ViewPager) originalMyPartnerFgView.findViewById(R.id.original_my_partner_vp));
        rg = ((RadioGroup) originalMyPartnerFgView.findViewById(R.id.original_my_partner_rg));
        wuhehuoren_iv = ((ImageView) originalMyPartnerFgView.findViewById(R.id.wuhehuoren_iv));
        customToolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                getActivity().finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.original_my_partner_today_rb:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.original_my_partner_yestoday_rb:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.original_my_partner_this_month_rb:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.original_my_partner_last_month_rb:
                        vp.setCurrentItem(3);
                        break;
                }
            }
        });
    }

    private void addFragmentList() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SummaryOb summaryOb = data.getSummaryObs().get(i);
            String personalOrder = summaryOb.getPersonalOrder();
            String partnerPredict = summaryOb.getPartnerPredict();
            String personalPredict = summaryOb.getPersonalPredict();
            String partnerOrder = summaryOb.getPartnerOrder();
            String totalPredict = summaryOb.getTotalPredict();
            String totalOrder = summaryOb.getTotalOrders();
            Bundle bundle = new Bundle();
            bundle.putString("personalOrder", personalOrder);
            bundle.putString("personalPredict", personalPredict);
            bundle.putString("partnerOrder", partnerOrder);
            bundle.putString("partnerPredict", partnerPredict);
            bundle.putString("totalPredict", totalPredict);
            bundle.putString("totalOrder", totalOrder);
            fragmentList.add(MyPartnerVpBaseFg.getInstance(bundle));
        }
    }*/
}
