package com.weslide.lovesmallscreen.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;

/**
 * Created by xu on 2016/7/25.
 * 商家处理用户退单详情
 */
public class SellerHandlerBackOrderDetailFragment extends BaseFragment {

    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_seller_handler_back_order_detail, container, false);

        return mView;
    }
}
