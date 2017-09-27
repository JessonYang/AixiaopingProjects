package com.weslide.lovesmallscreen.view_yy.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.TaoKeActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.UpdateStoreInfo;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2017/3/1.
 */
public class InputStatusActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_connection)
    TextView tvConnection;
    @BindView(R.id.tv_remind)
    TextView tvRemind;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.seller_psw_tv)
    TextView seller_psw_tv;
    @BindView(R.id.seller_account_tv)
    TextView seller_account_tv;
    int verifyStatus;
    @BindView(R.id.ll_start)
    ImageView llStart;
    @BindView(R.id.ll_pass)
    ImageView llPass;
    @BindView(R.id.ll_defeated)
    ImageView llDefeated;
    @BindView(R.id.seller_account_ll)
    LinearLayout seller_account_ll;
    @BindView(R.id.seller_psw_ll)
    LinearLayout seller_psw_ll;
    @BindView(R.id.download_app_ll)
    LinearLayout download_app_ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_input_type);
        ButterKnife.bind(this);
        getStoreVerifyStatus();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStatusActivity.this.finish();
            }
        });
    }

    private void getStoreVerifyStatus() {
        RXUtils.request(InputStatusActivity.this, new Request(), "storeVerifyStatus", new SupportSubscriber<Response<UpdateStoreInfo>>() {

            @Override
            public void onNext(Response<UpdateStoreInfo> updateStoreInfoResponse) {
                Log.d("雨落无痕丶", "onNext: sds");
                setData(updateStoreInfoResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.d("雨落无痕丶", "onError: "+e.toString());
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                Log.d("雨落无痕丶", "onResponseError: sfdsf");
            }
        });
    }

    private void setData(UpdateStoreInfo spdateStoreInfo) {
        tvConnection.setText(spdateStoreInfo.getTips());
        tvMessage.setText(spdateStoreInfo.getMessage());
        tvRemind.setText(spdateStoreInfo.getRemind());
        verifyStatus = spdateStoreInfo.getVerifyStatus();
        Log.d("雨落无痕丶", "setData: 55");
        if (verifyStatus == 1 || verifyStatus == 2){
            Log.d("雨落无痕丶", "setData: sss");
            llStart.setVisibility(View.GONE);
            llDefeated.setVisibility(View.GONE);
            llPass.setVisibility(View.VISIBLE);
            seller_account_ll.setVisibility(View.VISIBLE);
            seller_psw_ll.setVisibility(View.VISIBLE);
            download_app_ll.setVisibility(View.VISIBLE);
            seller_account_tv.setText(spdateStoreInfo.getSellerAccount());
            seller_psw_tv.setText(spdateStoreInfo.getSellerPassword());
        }else if(verifyStatus == -2){
            llStart.setVisibility(View.GONE);
            llDefeated.setVisibility(View.VISIBLE);
            llPass.setVisibility(View.GONE);
            seller_account_ll.setVisibility(View.GONE);
            seller_psw_ll.setVisibility(View.GONE);
            download_app_ll.setVisibility(View.GONE);
        }else if(verifyStatus == 0){
            llStart.setVisibility(View.VISIBLE);
            llDefeated.setVisibility(View.GONE);
            llPass.setVisibility(View.GONE);
            seller_account_ll.setVisibility(View.GONE);
            seller_psw_ll.setVisibility(View.GONE);
            download_app_ll.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.btn_commint,R.id.download_app_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commint:
                if (verifyStatus == 2 || verifyStatus == -2) {
                    returnCheckstatus();
                } else {
                    this.finish();
                }
                break;
            case R.id.download_app_tv:
                if (!isHasApp("com.axp.axpseller")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(TaoKeActivity.KEY_LOAD_URL, "http://a.app.qq.com/o/simple.jsp?pkgname=com.axp.axpseller");
                    AppUtils.toActivity(this, TaoKeActivity.class, bundle);
                }else {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.axp.axpseller");
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 审核通过确认
     */
    private void returnCheckstatus() {
        RXUtils.request(InputStatusActivity.this, new Request(), "returnCheckstatus", new SupportSubscriber() {
            @Override
            public void onNext(Object o) {
                InputStatusActivity.this.finish();
            }
        });
    }

    private boolean isHasApp(String packageName){
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }
}