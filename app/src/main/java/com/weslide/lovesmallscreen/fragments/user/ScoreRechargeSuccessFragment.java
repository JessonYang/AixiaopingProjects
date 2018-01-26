package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.user.MyScoreActivity;
import com.weslide.lovesmallscreen.activitys.user.ScoreRechargeOffLineActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YY on 2018/1/9.
 */
public class ScoreRechargeSuccessFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.score_num_tv)
    TextView scoreNumTv;
    @BindView(R.id.desc_type)
    TextView desc_type;
    private View fgView;
    private String score = "获得0.0积分";
    private String title = "积分充值";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_score_recharge_success, container, false);
        ButterKnife.bind(this, fgView);
        initData();
        return fgView;
    }

    private void initData() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            score = bundle.getString("score");
            title = bundle.getString("title");
        }
        toolbar.setTextViewTitle(title);
        if (title.equals("积分充值")) {
            scoreNumTv.setText("获得" + score + "积分");
            desc_type.setText("充值成功");
        } else if (title.equals("赠送积分")) {
            scoreNumTv.setText(score + "积分");
            desc_type.setText("赠送成功");
        } else if (title.equals("收取积分")) {
            scoreNumTv.setText("获得" + score + "积分");
            desc_type.setText("收到赠送积分");
        }
        dateTv.setText(simpleDateFormat.format(new Date(System.currentTimeMillis())));
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                handleFinish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
    }

    @OnClick(R.id.btn_sure)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                handleFinish();
                break;
        }
    }

    private void handleFinish() {
        if (!"收取积分".equals(title)) {
            getSupportApplication().removeActivitys(ScoreRechargeOffLineActivity.class);
            getActivity().finish();
        } else {
            AppUtils.toActivity(getActivity(), MyScoreActivity.class);
            getActivity().finish();
        }
    }
}
