package com.weslide.lovesmallscreen.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.mall.GoodsClassifiFragment;

/**
 * Created by xu on 2016/6/23.
 * 商品分类Activity
 */
public class GoodsClassifiActivity extends BaseActivity {

    /**
     * 请求参数对象
     * GetGoodsListBean
     */
    public static final String KEY_REQUEST = "KEY_REQUEST";

    public static final String KEY_TITLE = "KEY_TITLE";

    GoodsClassifiFragment fragment = new GoodsClassifiFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_goods_classifi);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
