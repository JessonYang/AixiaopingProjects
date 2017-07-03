package com.weslide.lovesmallscreen.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.goods.GoodsFragment;

/**
 * Created by xu on 2016/6/15.
 * 商品详情界面
 */
public class GoodsActivity extends BaseActivity {

    /**
     * 需要展示的商品id
     */
    public static final String KEY_GOODS_ID = "goods_id";

    /** 秒杀已开始 */
    public static final String KEY_SECOND_KILL_START = "KEY_SECOND_KILL_NO_START";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_goods);

        GoodsFragment goodsFragment = new GoodsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, goodsFragment).commit();
    }

}
