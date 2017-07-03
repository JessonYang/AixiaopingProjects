package com.weslide.lovesmallscreen.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.LiveFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/10/18.
 */
public class LiveActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_list);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change, new LiveFragment()).commit();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveActivity.this.finish();
            }
        });
    }
}
