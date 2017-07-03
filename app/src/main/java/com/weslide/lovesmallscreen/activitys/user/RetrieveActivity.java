package com.weslide.lovesmallscreen.activitys.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.user.RetrieveFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/1.
 * 发送验证码
 */
public class RetrieveActivity extends BaseActivity {
    RetrieveFragment fragment = new RetrieveFragment();
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change, fragment).commit();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrieveActivity.this.finish();
            }
        });
        Bundle bundle = new Bundle();
        bundle.putInt("key",1);
        fragment.setArguments(bundle);
    }
}
