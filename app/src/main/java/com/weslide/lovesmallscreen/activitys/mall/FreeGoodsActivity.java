package com.weslide.lovesmallscreen.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.mall.FreeGoodsFragment;

/**
 * Created by xu on 2016/7/13.
 * 免单商品列表
 */
public class FreeGoodsActivity extends BaseActivity {

    FreeGoodsFragment mFragment = new FreeGoodsFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_free_goods);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();
    }
}
