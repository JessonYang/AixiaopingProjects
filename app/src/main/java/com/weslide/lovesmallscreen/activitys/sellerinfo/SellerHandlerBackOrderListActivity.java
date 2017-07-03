package com.weslide.lovesmallscreen.activitys.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.sellerinfo.SellerHandlerBackOrderListFragment;

/**
 * Created by xu on 2016/7/25.
 * 商家处理用户退单界面列表
 */
public class SellerHandlerBackOrderListActivity extends BaseActivity {

    SellerHandlerBackOrderListFragment fragment = new SellerHandlerBackOrderListFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seller_handler_back_order_list);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
