package com.weslide.lovesmallscreen.fragments.user;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Address;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.view_yy.activity.PermissionsActivity;
import com.weslide.lovesmallscreen.view_yy.util.PermissionsChecker;
import com.weslide.lovesmallscreen.views.choisCityView.MainSelectCityDialog;
import com.weslide.lovesmallscreen.views.choisCityView.SelectedCity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Dong on 2016/6/13.
 * 修改地址或者添加地址
 */
public class MyAddressEditFragment extends BaseFragment implements SelectedCity {
    View mView;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_phone_number)
    EditText edtPhoneNumber;
    @BindView(R.id.edt_address)
    TextView edtAddress;
    @BindView(R.id.edt_address_details)
    EditText edtAddressDetails;
    @BindView(R.id.btn_sure)
    ButtonRectangle btnSure;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_choise_city)
    ImageView ivChoiseCity;
//    @BindView(R.id.iv_location_city)
//    ImageView ivLocationCity;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.view_line2)
    View viewLine2;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    private String name, phone, mProvince, mCity, mDistrict, detailedAddress, addressId;
    private MainSelectCityDialog cityDialog;
    private String province = "";
    private String city = "";
    private String district = "";
    Address mAddress;
    Bundle bundle;
    private PermissionsChecker mPermissionsChecker;
    private final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_my_address_edit, container, false);

        ButterKnife.bind(this, mView);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        init();
        return mView;
    }

    private void init() {

        bundle = getArguments();
        if (bundle.getInt("type") == 1) {
            mAddress = new Address();
        } else if (bundle.getInt("type") == 2) {
            mAddress = (Address) bundle.getSerializable("mAddress");
            edtName.setText(mAddress.getName());
            edtPhoneNumber.setText(mAddress.getPhone());
            edtAddress.setText(mAddress.getProvince() + mAddress.getCity() + mAddress.getDistrict());
            edtAddressDetails.setText(mAddress.getDetailedAddress());
            mDistrict = mAddress.getDistrict();
            mProvince = mAddress.getProvince();
            mCity = mAddress.getCity();
            addressId = mAddress.getAddressId();
        } else {
            mAddress = new Address();
            llName.setVisibility(View.GONE);
            llPhone.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
            viewLine2.setVisibility(View.GONE);
            llLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 添加收货地址
     */
    private void postAddress() {
        name = edtName.getText().toString();
        phone = edtPhoneNumber.getText().toString();
        detailedAddress = edtAddressDetails.getText().toString();
        mAddress.setName(name);
        mAddress.setPhone(phone);
        mAddress.setProvince(mProvince);
        mAddress.setCity(mCity);
        mAddress.setDistrict(mDistrict);
        mAddress.setDetailedAddress(detailedAddress);
        Observable.just(mAddress)
                .filter((Address u) -> {
                    if (!NetworkUtils.isConnected(getActivity())) {
                        T.showShort(getActivity(), R.string.alert_no_network);
                        return false;
                    }
                    return true;
                }).filter(u -> validate(u))  //验证数据是否正确
                .map(userInfo1 -> {
                    return userInfo1;
                }).observeOn(Schedulers.computation())
                .map((Address u) -> {

                    try {
                        Request<Address> request = new Request<Address>();
                        request.setData(u);

                        Response<Address> response = HTTP.getAPI().putAddress(HTTP.formatJSONData(request)).execute().body();

                        return response;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<Address>>() {
            @Override
            public void call(Response<Address> userInfoResponse) {
                T.showShort(getActivity(), userInfoResponse.getMessage());
                getActivity().finish();
            }
        });

    }

    /**
     * 编辑地址
     */
    private void updateAddress() {
        name = edtName.getText().toString();
        phone = edtPhoneNumber.getText().toString();
        detailedAddress = edtAddressDetails.getText().toString();
        mAddress.setName(name);
        mAddress.setPhone(phone);
        mAddress.setProvince(mProvince);
        mAddress.setCity(mCity);
        mAddress.setDistrict(mDistrict);
        mAddress.setDetailedAddress(detailedAddress);
        mAddress.setAddressId(addressId);
        Request<Address> request = new Request<>();
        request.setData(mAddress);
        RXUtils.request(getActivity(), request, "updateAddress", new SupportSubscriber<Response>() {

            @Override
            public void onNext(Response response) {
                T.showShort(getActivity(), response.getMessage());
                getActivity().finish();
            }
        });


    }

    /**
     * 添加或修改个人地址
     */
    private void addUseInfoAddress() {
        detailedAddress = edtAddressDetails.getText().toString();
        mAddress.setDetailedAddress(mProvince + mCity + mDistrict + detailedAddress);
        Request<Address> request = new Request<>();
        request.setData(mAddress);
        RXUtils.request(getActivity(), request, "changeBaseInfo", new SupportSubscriber<Response>() {

            @Override
            public void onNext(Response response) {
                T.showShort(getActivity(), response.getMessage());
                getActivity().finish();
            }
        });
    }

    @OnClick({R.id.iv_choise_city, /*R.id.iv_location_city,*/ R.id.btn_sure,R.id.edt_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choise_city:
                mPermissionsChecker = new PermissionsChecker(getActivity());
                boolean lacksPermissions = mPermissionsChecker.lacksPermissions(PERMISSIONS);
                if (!lacksPermissions) {
                    mProvince = ContextParameter.getCurrentLocation().getProvince();
                    mCity = ContextParameter.getCurrentLocation().getCity();
                    mDistrict = ContextParameter.getCurrentLocation().getDistrict();
                    openDialogSelectCity();
                }else {
                    PermissionsActivity.startActivityForResult(getActivity(), 0, PERMISSIONS);
                }

                break;
            /*case R.id.iv_location_city:
                mProvince = ContextParameter.getCurrentLocation().getProvince();
                mCity = ContextParameter.getCurrentLocation().getCity();
                mDistrict = ContextParameter.getCurrentLocation().getDistrict();
                edtAddress.setText(mProvince + mCity + mDistrict);
                break;*/
            case R.id.btn_sure:
                if (bundle.getInt("type") == 3) {
                    addUseInfoAddress();
                } else {
                    if (mAddress.getAddressId() != null) {
                        updateAddress();
                    } else {
                        postAddress();
                    }
                }
                break;
            case R.id.edt_address:
                mProvince = ContextParameter.getCurrentLocation().getProvince();
                mCity = ContextParameter.getCurrentLocation().getCity();
                mDistrict = ContextParameter.getCurrentLocation().getDistrict();
                edtAddress.setText(mProvince + mCity + mDistrict);
                openDialogSelectCity();
                break;
        }
    }


    private void openDialogSelectCity() {
        if (cityDialog != null) {
            cityDialog.cancel();
            cityDialog = null;
        }
        cityDialog = new MainSelectCityDialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar, this, new String[]{this.province, this.city});
        cityDialog.locateArea(mProvince,mCity,mDistrict);
        cityDialog.show();
    }

    @Override
    public void selectedCity(String province, String city, String district, String zoneId) {
        this.province = province;
        this.city = city;
        this.district = district;
        mProvince = this.province;
        mCity = this.city;
        mDistrict = this.district;
        edtAddress.setText(province + city + district);

    }

    /**
     * 验证用户输入
     *
     * @param mAddress
     * @return
     */
    private boolean validate(Address mAddress) {

        if (StringUtils.isBlank(mAddress.getName())) {
            T.showShort(getActivity(), R.string.alert_name);
            return false;
        } else if (!StringUtils.validataPhoneNumber(mAddress.getPhone())) {
            T.showShort(getActivity(), R.string.alert_illegal_phone_number);
            return false;
        } else if (StringUtils.isBlank(mAddress.getProvince()) || StringUtils.isBlank(mAddress.getCity())) {
            T.showShort(getActivity(), R.string.hint_input_address_now);
            return false;
        } else if (StringUtils.isBlank(detailedAddress)) {
            T.showShort(getActivity(), R.string.address_details_hint);
            return false;
        }

        return true;
    }


}
