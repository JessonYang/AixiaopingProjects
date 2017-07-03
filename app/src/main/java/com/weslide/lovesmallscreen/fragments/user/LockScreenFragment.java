package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gc.materialdesign.views.Switch;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/6/20.
 * 锁屏
 */
public class LockScreenFragment extends BaseFragment {
    View mView;

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.switchView)
    com.rey.material.widget.Switch switchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_lock_screen, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {

        if(ContextParameter.getLocalConfig().isOpenUnlock()){
            tvState.setText("关闭锁屏");
            switchView.setChecked(true);
        } else {
            tvState.setText("打开锁屏");
            switchView.setChecked(false);
        }

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        switchView.setOnCheckedChangeListener(new com.rey.material.widget.Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(com.rey.material.widget.Switch view, boolean checked) {
                if (checked) {
                    tvState.setText("关闭锁屏");
                    T.showShort(getActivity(), "打开锁屏");

                    ContextParameter.getLocalConfig().setOpenUnlock(true);
                    ContextParameter.setLocalConfig(ContextParameter.getLocalConfig());
                } else {
                    tvState.setText("打开锁屏");
                    T.showShort(getActivity(), "关闭锁屏");
                    ContextParameter.getLocalConfig().setOpenUnlock(false);
                    ContextParameter.setLocalConfig(ContextParameter.getLocalConfig());
                }
            }
        });

    }
}
