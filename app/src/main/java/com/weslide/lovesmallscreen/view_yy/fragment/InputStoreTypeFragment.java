package com.weslide.lovesmallscreen.view_yy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.model_yy.javabean.Shopcate;
import com.weslide.lovesmallscreen.model_yy.javabean.ShopcategoryInfo;
import com.weslide.lovesmallscreen.view_yy.adapter.MyShopcateAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2017/2/28.
 */
public class InputStoreTypeFragment extends BaseFragment {

    View mView;
    @BindView(R.id.listview)
    ListView listview;
    AdapterView.OnItemClickListener onItemClickListener;
    public static final String TAG = "MyFragment";
    public MyShopcateAdapter adapter;
    List<ShopcategoryInfo> data = new ArrayList<>();
    Shopcate shopcate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_shopcategory, container, false);

        ButterKnife.bind(this, mView);
        shopcate = (Shopcate) getArguments().getSerializable(TAG);
        adapter = new MyShopcateAdapter(getActivity(),shopcate.getCategoryItems());
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onItemClickListener);
        return mView;
    }

    public void setLinsener(AdapterView.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
