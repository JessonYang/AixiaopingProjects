package com.weslide.lovesmallscreen.fragments.mall;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.widget.VerticalSlide;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.CityDistrictActivity;
import com.weslide.lovesmallscreen.activitys.mall.GoodsSearchActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.Zone;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateMallMessage;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.ShareUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/10/18.
 */
public class HomeMallFragment extends BaseFragment {

    View mView;
    @BindView(R.id.first)
    FrameLayout first;
    @BindView(R.id.second)
    FrameLayout second;
    @BindView(R.id.dragLayout)
    VerticalSlide dragLayout;
    MallFragment fragment;
    SecondMallFragment secondMallFragment;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_go_top)
    ImageView ivGoTop;

    private int totalDy = 0;
    @BindView(R.id.ll_main_mall_title_background)
    LinearLayout llMainMallTitleBackground;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_home_mall, container, false);

        EventBus.getDefault().register(this);
        ButterKnife.bind(this, mView);

        Zone district = getSupportApplication().getDaoSession().getZoneDao()
                .loadDistrictByZoneName(ContextParameter.getCurrentLocation().getCity(), ContextParameter.getCurrentLocation().getDistrict());
        ContextParameter.setCurrentZone(district);

        init();
        return mView;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void init() {
        fragment = new MallFragment();
        secondMallFragment = new SecondMallFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.first, fragment);
        transaction.replace(R.id.second, secondMallFragment);
        transaction.commit();
        load();

        fragment.setTitleOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalDy += dy;
                scrollChange(totalDy);
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
        load();
    }

    public void load() {
        tvTitle.setText(ContextParameter.getCurrentZone().getName());

    }

    /**
     * 滑动
     */
    public void scrollChange(int y) {
        if (y > 20 && y / 2 < 255) {
            String transparency = "#" + (y / 2 < 16 ? "0" : "") + Integer.toHexString(y / 2) + "ff2d47";
            llMainMallTitleBackground.setBackgroundColor(Color.parseColor(transparency));
            ivGoTop.setVisibility(View.GONE);
        } else if (y <= 20) {
            if (llMainMallTitleBackground == null) {
                //backgroundDrawable = getResources().getDrawable(R.drawable.ic_main_mall_vip_title_background);
            }
            llMainMallTitleBackground.setBackgroundColor(Color.parseColor("#10000000"));
            ivGoTop.setVisibility(View.GONE);
        } else {
            llMainMallTitleBackground.setBackgroundColor(getResources().getColor(R.color.main_color_red));
            ivGoTop.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_go_top, R.id.rl_main_mall_title_content, R.id.et_search, R.id.tv_scan_qr, R.id.tv_share})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_go_top:
               dragLayout.goTop(new VerticalSlide.OnGoTopListener() {
                    @Override
                    public void goTop() {
                        fragment.goTop();
                        totalDy = 0;
                    }
                });
                break;
            case R.id.rl_main_mall_title_content:

                AppUtils.toActivity(getActivity(), CityDistrictActivity.class);
                break;
            case R.id.et_search:
                AppUtils.toActivity(getActivity(), GoodsSearchActivity.class);
                break;
            case R.id.tv_scan_qr:
                QRCodeUtil.scan(getActivity());
                break;
            case R.id.tv_share:
                ShareContent home = ContextParameter.getClientConfig().getHomeShareContent();
                ShareUtils.share(getActivity(), home.getTitle(),
                        home.getIconUrl(),
                        home.getTargetUrl(),
                        home.getContent());
                break;
        }


    }
    public void goToTop(){
        dragLayout.goTop(new VerticalSlide.OnGoTopListener() {
            @Override
            public void goTop() {
                fragment.goTop();
                totalDy = 0 ;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }
}
