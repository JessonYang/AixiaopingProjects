package com.weslide.lovesmallscreen.activitys.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.mall.GoodsCommentListFragment;

/**
 * Created by Dong on 2016/8/16.
 *  评论列表
 */
public class GoodsCommentListActivity extends BaseActivity{
    GoodsCommentListFragment fragment = new GoodsCommentListFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bundle.getString("goodsId");
        fragment.setArguments(bundle);
    }
}
