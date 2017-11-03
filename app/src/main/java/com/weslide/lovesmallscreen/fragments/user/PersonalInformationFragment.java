package com.weslide.lovesmallscreen.fragments.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.ThemeManager;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.user.ChangePasswordActivity;
import com.weslide.lovesmallscreen.activitys.user.ChioseSexActivity;
import com.weslide.lovesmallscreen.activitys.user.MyAddressEditActivity;
import com.weslide.lovesmallscreen.activitys.user.NameEditActivity;
import com.weslide.lovesmallscreen.activitys.user.RetrieveActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.core.UploadSubscriber;
import com.weslide.lovesmallscreen.dao.sp.UserInfoSP;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.models.bean.UploadFileBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.utils.UserUtils;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by Dong on 2016/6/20.
 * 个人资料
 */
public class PersonalInformationFragment extends BaseFragment {
    View mView;
    //头像
    @BindView(R.id.iv_head_portrait)
    ImageView ivHeadPortrait;
    //昵称
    @BindView(R.id.tv_name)
    TextView tvName;
    //电话
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    //地址
    @BindView(R.id.tv_address)
    TextView tvAddress;
    //性别
    @BindView(R.id.tv_sex)
    TextView tvSex;
    //生日
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    //退出登录
    @BindView(R.id.btn_logout)
    ButtonRectangle btnLogout;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_headimg_hint)
    TextView tvHeadimg;
    MultiImageSelector selector = MultiImageSelector.create(getActivity());
    private ArrayList<String> mSelectPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_personal_information, container, false);

        ButterKnife.bind(this, mView);


        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return mView;
    }

    @OnClick({R.id.iv_head_portrait, R.id.ll_name, R.id.ll_phone, R.id.ll_address, R.id.ll_sex, R.id.ll_birthday, R.id.btn_logout,R.id.ll_change_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head_portrait:
                selector.single();
                selector.start(getActivity(), 2);
                break;
            case R.id.ll_name:
                AppUtils.toActivity(getActivity(), NameEditActivity.class);
                break;
            case R.id.ll_phone:
                //一旦填写了手机资料后便不能修改
                /*if(StringUtils.isBlank(ContextParameter.getUserInfo().getPhone())) {
                    AppUtils.toActivity(getActivity(), RetrieveActivity.class);
                }else{
                    T.showShort(getActivity(),"您已绑定了手机号码");
            }*/

                //填写资料后还可以进行修改
                AppUtils.toActivity(getActivity(), RetrieveActivity.class);
                break;
            case R.id.ll_address:
                Bundle bundle = new Bundle();
                bundle.putInt("type",3);
                AppUtils.toActivity(getActivity(), MyAddressEditActivity.class,bundle);
                break;
            case R.id.ll_sex:
                AppUtils.toActivity(getActivity(), ChioseSexActivity.class);
                break;
            case R.id.ll_change_password:
                AppUtils.toActivity(getActivity(), ChangePasswordActivity.class);

                break;
            case R.id.ll_birthday:
                choiseUserBirthday();
                break;
            case R.id.btn_logout:
                UserUtils.logout(getActivity());
                getActivity().finish();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        getUserInfo(ContextParameter.getUserInfo().getUserId());
        setData();
    }
    /**
     * 获取个人中心数据
     */
    private void getUserInfo(String userId) {
        Request<UserInfo> request = new Request<UserInfo>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "getUserInfo", new SupportSubscriber<Response<UserInfo>>() {
            @Override
            public void onNext(Response<UserInfo> response) {
                UserInfoSP.setUserInfo(response.getData());
                setData();
            }
        });
    }

    private void setData() {
        if(StringUtils.isBlank(ContextParameter.getUserInfo().getUsername())){
            tvName.setText("未设置");
        }else {
            tvName.setText(ContextParameter.getUserInfo().getUsername());
        }
        if(StringUtils.isBlank(ContextParameter.getUserInfo().getPhone())){
            tvPhone.setText("还没绑定号码呢");
        }else {
            tvPhone.setText(ContextParameter.getUserInfo().getPhone());
        }
        if(StringUtils.isBlank(ContextParameter.getUserInfo().getAddress())){
            tvAddress.setText("请完善你的地址");
        }else{
            tvAddress.setText(ContextParameter.getUserInfo().getAddress());
        }
        if(StringUtils.isBlank(ContextParameter.getUserInfo().getBirthday())){
            tvBirthday.setText("告诉我你的生日吧，有惊喜哦");
        }else{
            tvBirthday.setText(ContextParameter.getUserInfo().getBirthday());
        }
        if(StringUtils.isBlank(ContextParameter.getUserInfo().getSex())){
            tvSex.setText("保密");
        }else{
            tvSex.setText(ContextParameter.getUserInfo().getSex());
        }
        if (StringUtils.isBlank(ContextParameter.getUserInfo().getHeadimage())){
            ivHeadPortrait.setImageResource(R.drawable.icon_defult);
        }else{
            Glide.with(getActivity()).load(ContextParameter.getUserInfo().getHeadimage()).asBitmap()
                    .error(R.drawable.icon_defult).placeholder(R.drawable.icon_defult)
                    .centerCrop().into(new BitmapImageViewTarget(ivHeadPortrait) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    ivHeadPortrait.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == -1) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                inputHeadImg();
            }
        }
    }

    /**
     * 选择生日
     */
    private void choiseUserBirthday() {
        Dialog.Builder builder = null;
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        Calendar cal = Calendar.getInstance();
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        int mMonth = cal.get(Calendar.MONTH);
        int mYear = cal.get(Calendar.YEAR);
        int mMinDay = mDay;
        int mMinMonth = mMonth;
        int mMinYear = mYear - 116;
        int mMaxDay = mDay;
        int mMaxMonth = mMonth;
        int mMaxYear = mYear + 34;

        builder = new DatePickerDialog.Builder(isLightTheme ? R.style.Material_App_Dialog_DatePicker_Light :
                R.style.Material_App_Dialog_DatePicker, mMinDay, mMinMonth, mMinYear, mMaxDay, mMaxMonth, mMaxYear, mDay, mMonth, mYear) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                int year = dialog.getYear();
                int month = dialog.getMonth();
                int day = dialog.getDay();
                choiseBirthday(date);
                /*Toast.makeText(getActivity(), "Date is " + date, Toast.LENGTH_SHORT).show();*/
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
               /* Toast.makeText(getActivity(), "Cancelled" , Toast.LENGTH_SHORT).show();*/
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("确定")
                .negativeAction("关闭");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);

    }

    /**
     * 上传生日
     * @param birthday
     */
    private void choiseBirthday(String birthday) {
        Request<UserInfo> request = new Request<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setBirthday(birthday);
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "changeBaseInfo", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response response) {
                getUserInfo(ContextParameter.getUserInfo().getUserId());
            }
        });

    }
    private void inputHeadImg(){

        List<UploadFileBean> uploadFileBeen = new ArrayList<>();
        if (mSelectPath != null && mSelectPath.size() > 0) {

            for (int i = 0; i < mSelectPath.size(); i++) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(mSelectPath.get(i)));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_FEED_BACK);

                uploadFileBeen.add(bean);
            }
        }
        RXUtils.uploadImages(getActivity(), uploadFileBeen, new UploadSubscriber() {

            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(List<Response<UploadFileBean>> responses) {
              postHeadImg(responses.get(0).getData().getOppositeUrl(),responses.get(0).getData().getAbsoluteUrl());

            }
        });
    }
    /**
     * 上传头像
     */
    private void postHeadImg(String path,String url){
        Request<UserInfo> request = new Request<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setHeadimage(path);
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "changeBaseInfo", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response response) {
                T.showShort(getActivity(),response.getMessage());
                Glide.with(getActivity()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivHeadPortrait) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivHeadPortrait.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
        });

    }

}
