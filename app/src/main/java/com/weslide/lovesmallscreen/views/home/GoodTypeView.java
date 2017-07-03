package com.weslide.lovesmallscreen.views.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/10/21.
 */
public class GoodTypeView extends FrameLayout {
    @BindView(R.id.tv_all_classifi)
    TextView tvAllClassifi;
    @BindView(R.id.tv_sales_volume)
    TextView tvSalesVolume;
    @BindView(R.id.tv_value)
    TextView tvValue;

    public GoodTypeView(Context context) {
        super(context);
        initView();
    }

    public GoodTypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GoodTypeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_dressing_by_screening, this, false);
    }

    @OnClick({R.id.tv_all_classifi, R.id.tv_sales_volume, R.id.tv_value})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all_classifi:
                break;
            case R.id.tv_sales_volume:
                break;
            case R.id.tv_value:
                break;
        }
    }
}
