package com.weslide.lovesmallscreen.view_yy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.models.Location;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.views.choisCityView.MainSelectCityDialog;
import com.weslide.lovesmallscreen.views.choisCityView.SelectedCity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2017/3/1.
 */
public class InputChioseAddressActivity extends BaseActivity implements SelectedCity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.edt_address)
    TextView edtAddress;
    @BindView(R.id.edt_address_details)
    EditText edtAddressDetails;
    Intent intent;
    Location location = new Location();
    private MainSelectCityDialog cityDialog;
    private String province = "";
    private String city = "";
    private String district = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_address_edit);
        ButterKnife.bind(this);
        intent = getIntent();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!StringUtils.isBlank(province)&&!StringUtils.isBlank(city)&&!StringUtils.isBlank(district)&&!StringUtils.isBlank(edtAddressDetails.getText().toString())) {
                    setData();
                    setResult(2, intent);
                    InputChioseAddressActivity.this.finish();
                }else {
                    InputChioseAddressActivity.this.finish();
                }
            }
        });
    }

    @OnClick({R.id.iv_choise_city,R.id.btn_sure})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_choise_city:
                openDialogSelectCity();
                break;
            case R.id.btn_sure:
                if(!StringUtils.isBlank(province)&&!StringUtils.isBlank(city)&&!StringUtils.isBlank(district)&&!StringUtils.isBlank(edtAddressDetails.getText().toString())) {
                    setData();
                    setResult(2, intent);
                    InputChioseAddressActivity.this.finish();
                }else {
                    InputChioseAddressActivity.this.finish();
                }
                break;
        }
    }


    private void openDialogSelectCity() {
        if (cityDialog != null) {
            cityDialog.cancel();
            cityDialog = null;
        }
        cityDialog = new MainSelectCityDialog(InputChioseAddressActivity.this, android.R.style.Theme_Translucent_NoTitleBar, this,
                new String[]{this.province, this.city});
        cityDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(!StringUtils.isBlank(province)&&!StringUtils.isBlank(city)&&!StringUtils.isBlank(district)&&!StringUtils.isBlank(edtAddressDetails.getText().toString())) {
            setData();
            setResult(2, intent);
            InputChioseAddressActivity.this.finish();
        }else {
            InputChioseAddressActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void selectedCity(String province, String city, String district, String zoneId) {
        edtAddress.setText(province+city+district);
        this.province = province;
        this.city = city;
        this.district = district;

    }

    private void setData(){
        location.setStreet(edtAddressDetails.getText().toString());
        location.setProvince(province);
        location.setCity(city);
        location.setDistrict(district);
        intent.putExtra("Location",location);
    }
}
