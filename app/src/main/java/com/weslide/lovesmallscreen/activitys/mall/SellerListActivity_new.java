package com.weslide.lovesmallscreen.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.mall.SellerListFragment_new;

import java.util.ArrayList;

/**
 * Created by xu on 2016/7/23.
 * 周边店铺_新版
 */
public class SellerListActivity_new extends BaseActivity {

    /**
     * 默认选中
     * 0：全部店铺
     * 1：收藏店铺
     */
    public static final String KEY_SELECT = "KEY_SELECT";
    public static ArrayList sellerList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_list_new);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new SellerListFragment_new()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sellerList = null;
    }
}
