package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.Brand;
import com.weslide.lovesmallscreen.model_yy.javabean.BrandModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.view_yy.adapter.BrandGvAdapter;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/6/17.
 */
public class SaveMoneyBrandActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout back_iv;
    private GridView gv;
    private ImageView brand_iv;
    private BrandGvAdapter gvAdapter;
    private List<BrandModel> gvList = new ArrayList<>();
    private BrandModel brandModel;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_money_brand);
        initView();
        initData();
    }

    private void initData() {
        back_iv.setOnClickListener(this);
        brand_iv.setOnClickListener(this);
        gvAdapter = new BrandGvAdapter(this,gvList);
        gv.setAdapter(gvAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle nineToNineBundle = new Bundle();
                nineToNineBundle.putString("toolbarType",gvList.get(i).getName());
                nineToNineBundle.putString("searchValue","");
                nineToNineBundle.putString("cid",gvList.get(i).getBid());
                AppUtils.toActivity(SaveMoneyBrandActivity.this, SaveMoneyHomeActivity.class,nineToNineBundle);
            }
        });
        getNetData();
    }

    private void getNetData() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
        Request<Brand> request = new Request<>();
        RXUtils.request(this,request,"getBrand", new SupportSubscriber<Response<Brand>>() {

            @Override
            public void onNext(Response<Brand> brandResponse) {
                List<BrandModel> brand = brandResponse.getData().getBrand();
                gvList.clear();
                gvList.addAll(brand);
                gvList.remove(brand.size() - 1);
                Log.d("雨落无痕丶", "onNext: "+gvList.size());
                gvAdapter.notifyDataSetChanged();
                brandModel = brand.get(brand.size() - 1);
                Glide.with(SaveMoneyBrandActivity.this).load(brandModel.getLogo()).into(brand_iv);
                loadingDialog.dismiss();
            }
        });
    }

    private void initView() {
        back_iv = ((RelativeLayout) findViewById(R.id.back_iv));
        brand_iv = ((ImageView) findViewById(R.id.brand_iv));
        gv = ((GridView) findViewById(R.id.brand_gv));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.brand_iv:
                Bundle bundle = new Bundle();
                bundle.putString("toolbarType","品牌券");
                bundle.putString("searchValue","");
                bundle.putString("cid",brandModel.getBid());
                AppUtils.toActivity(SaveMoneyBrandActivity.this, SaveMoneyHomeActivity.class,bundle);
                break;
        }
    }
}
