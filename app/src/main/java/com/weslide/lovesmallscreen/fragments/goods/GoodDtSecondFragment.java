package com.weslide.lovesmallscreen.fragments.goods;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YY on 2017/12/27.
 */
public class GoodDtSecondFragment extends BaseFragment {

    @BindView(R.id.good_detail_containner)
    LinearLayout goodDetailContainner;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_good_dt_second, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
