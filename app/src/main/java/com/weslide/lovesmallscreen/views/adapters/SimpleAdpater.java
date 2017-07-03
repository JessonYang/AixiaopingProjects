package com.weslide.lovesmallscreen.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by xu on 2016/6/3.
 * 一个简化的Adapter
 */
public abstract class SimpleAdpater extends BaseAdapter {

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
