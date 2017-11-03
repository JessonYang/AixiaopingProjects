package com.weslide.lovesmallscreen.view_yy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.ShopcategoryInfo;
import com.weslide.lovesmallscreen.model_yy.javabean.UpdateStoreData;
import com.weslide.lovesmallscreen.model_yy.javabean.UpdateStoreInfo;
import com.weslide.lovesmallscreen.models.bean.UploadFileBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2017/2/13.
 */
public class InputInformationActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_info)
    EditText edtInfo;
    @BindView(R.id.tv_address)
    TextView tvAddress;
//    @BindView(R.id.tv_address)
//    EditText tvAddress;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_license)
    TextView tvLicense;
    UploadFileBean uploadFileBean;
    ShopcategoryInfo getCategoryItems;
    String address;
    @BindView(R.id.tv_connection)
    TextView tvConnection;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_input);
        ButterKnife.bind(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputInformationActivity.this.finish();
            }
        });
        getStoreVerifyStatus();
    }

    @OnClick({R.id.tv_guide, R.id.to_select_address, R.id.tv_type, R.id.tv_license, R.id.btn_commint})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_guide:
                break;
            case R.id.to_select_address:
                Intent intent1 = new Intent(InputInformationActivity.this, InputAddressActivity.class);
                startActivityForResult(intent1, 3);
                break;
            case R.id.tv_type:
                Intent intent2 = new Intent(InputInformationActivity.this, InputStoreTypeActivity.class);
                startActivityForResult(intent2, 5);
                break;
            case R.id.tv_license:
                Intent intent = new Intent(InputInformationActivity.this, UploadLicenseActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_commint:

                /*if (!StringUtils.isBlank(edtName.getText().toString()) &&
                        !StringUtils.isBlank(address) &&
                        !StringUtils.isBlank(edtPhone.getText().toString()) &&
                        !StringUtils.isBlank(uploadFileBean.getOppositeUrl()) &&
                        !StringUtils.isBlank(edtInfo.getText().toString()) &&
                        !StringUtils.isBlank(getCategoryItems.getCategoryId())) {
                    updateStoreInfo();
                } else {
                    T.showShort(InputInformationActivity.this, "请完善提交信息");
                }*/

                if (StringUtils.isBlank(edtInfo.getText().toString()) || StringUtils.isBlank(edtName.getText().toString()) || StringUtils.isBlank(edtPhone.getText().toString()) || StringUtils.isBlank(tvAddress.getText().toString()) || tvType.getText().toString().equals("您的店铺类型") || tvLicense.getText().toString().equals("上传营业执照")){
                    T.showShort(InputInformationActivity.this, "请完善提交信息");
                }else {
                    updateStoreInfo();
                }
                break;
        }
    }

    private void updateStoreInfo() {
        UpdateStoreData updateStoreData = new UpdateStoreData();
        Request<UpdateStoreData> request = new Request<>();
        updateStoreData.setName(edtName.getText().toString());
        if (address != null && address.length() > 0) {
            updateStoreData.setAddress(address);
        }else {
            updateStoreData.setAddress(tvAddress.getText().toString());
        }
        updateStoreData.setPhone(edtPhone.getText().toString());
        String oppositeUrl = uploadFileBean.getOppositeUrl();
        if (oppositeUrl != null && oppositeUrl.length() > 0) {
            updateStoreData.setBusinessLicencePic(oppositeUrl);
        } else {
            T.showShort(InputInformationActivity.this, "请完善提交信息");
            return;
        }
        updateStoreData.setSellerIdCard(edtInfo.getText().toString());
        updateStoreData.setShopcategoryId(getCategoryItems.getCategoryId());
        request.setData(updateStoreData);

        RXUtils.request(InputInformationActivity.this, request, "updateStoreInfo", new SupportSubscriber<Response<UpdateStoreInfo>>() {

            @Override
            public void onNext(Response<UpdateStoreInfo> updateStoreInfoResponse) {
                T.showShort(InputInformationActivity.this, updateStoreInfoResponse.getMessage());
                InputInformationActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == 1) {
                uploadFileBean = (UploadFileBean) data.getSerializableExtra("path");
                if (!StringUtils.isBlank(uploadFileBean.getAbsoluteUrl())) {
                    tvLicense.setText("上传成功");
                }

            }
        } else if (resultCode == 6) {
            if (requestCode == 5) {
                getCategoryItems = (ShopcategoryInfo) data.getSerializableExtra("type");
                tvType.setText(getCategoryItems.getCategoryName());
            }
        } else if (resultCode == 4) {
            if (requestCode == 3) {
                address = data.getStringExtra("address");
                tvAddress.setText(address);
            }
        }
    }

    private void getStoreVerifyStatus() {
        RXUtils.request(InputInformationActivity.this, new Request(), "storeVerifyStatus", new SupportSubscriber<Response<UpdateStoreInfo>>() {

            @Override
            public void onNext(Response<UpdateStoreInfo> updateStoreInfoResponse) {
                tvConnection.setText(updateStoreInfoResponse.getData().getTips());
            }
        });
    }
}
