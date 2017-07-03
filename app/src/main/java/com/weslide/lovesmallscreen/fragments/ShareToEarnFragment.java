package com.weslide.lovesmallscreen.fragments;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.activitys.user.MyFansActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

/**
 * Created by YY on 2017/4/17.
 */
public class ShareToEarnFragment extends BaseFragment implements View.OnClickListener {
    private View mView;
    private ImageView userFace;
    private TextView userName;
    private TextView userPhone;
    private ImageView invitationCode;
    private TextView personalInvatationCode;
    private TextView fansNum;
    private Button invate_friend_btn;
    private RelativeLayout to_fans_number_rll;
    private Bitmap logo;
    private View dcl_view;
    private CustomToolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_share_to_earn, container, false);
        initView();
        initData();
        return mView;
    }

    private void initData() {
        Glide.with(getActivity()).load(ContextParameter.getUserInfo().getHeadimage()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(userFace) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                userFace.setImageDrawable(circularBitmapDrawable);
                logo = resource;
            }
        });
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                getActivity().finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        userName.setText(ContextParameter.getUserInfo().getUsername());
        userPhone.setText(ContextParameter.getUserInfo().getPhone());
        personalInvatationCode.setText(ContextParameter.getUserInfo().getInviteCode());
        fansNum.setText(ContextParameter.getUserInfo().getFansNumber());
        invate_friend_btn.setOnClickListener(this);
        to_fans_number_rll.setOnClickListener(this);
        Bitmap bitmap = QRCodeUtil.createQRImage(ContextParameter.getClientConfig().getPersonalCenterShareContent().getTargetUrl()+ "?img="+ContextParameter.getUserInfo().getHeadimage() + "&name="+ContextParameter.getUserInfo().getUsername()
                +"&phone="+ContextParameter.getUserInfo().getPhone()+"&code="+ContextParameter.getUserInfo().getInviteCode()+"&appVersion="+AppUtils.getVersionCode(getActivity()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,120,getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,120,getResources().getDisplayMetrics()));
        invitationCode.setImageBitmap(bitmap);
        int barHeight = getStatusBarHeight();
        dcl_view.getLayoutParams().height = barHeight;
    }

    private int getStatusBarHeight() {
        Rect rect = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top == 0 ? 60 : rect.top;
    }

    private void initView() {
        userFace = ((ImageView) mView.findViewById(R.id.user_face));
        userName = ((TextView) mView.findViewById(R.id.user_name));
        userPhone = ((TextView) mView.findViewById(R.id.user_phone));
        invitationCode = ((ImageView) mView.findViewById(R.id.invitation_code));
        personalInvatationCode = ((TextView) mView.findViewById(R.id.personal_invatation_code));
        fansNum = ((TextView) mView.findViewById(R.id.fans_num));
        invate_friend_btn = ((Button) mView.findViewById(R.id.invate_friend_btn));
        to_fans_number_rll = ((RelativeLayout) mView.findViewById(R.id.to_fans_number_rll));
        dcl_view = ((View) mView.findViewById(R.id.dcl_view));
        toolbar = ((CustomToolbar) mView.findViewById(R.id.toolbar));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.invate_friend_btn:
                ShareContent home = ContextParameter.getClientConfig().getPersonalCenterShareContent();
                String url = home.getTargetUrl();
                String img = ContextParameter.getUserInfo().getHeadimage();
                String name = ContextParameter.getUserInfo().getUsername();
                String phone = ContextParameter.getUserInfo().getPhone();
                String code = ContextParameter.getUserInfo().getInviteCode();
                String appVersion = String.valueOf(AppUtils.getVersionCode(getActivity()));
                if (name == null){
                    name = "";
                }
                if (img == null){
                    img = "";
                }
                if (phone == null){
                    phone = "";
                }
                if (code == null){
                    code = "";
                }

//                String down = ContextParameter.getClientConfig().getDownload();
//                String score = ContextParameter.getUserInfo().getScore();
                String targetUrl =url+ "?img="+img + "&name="+name+"&phone="+phone+"&code="+code+"&appVersion="+appVersion;
//                Log.d("雨落无痕丶", "onClick: "+targetUrl);
                ShareUtils.share(getActivity(), home.getTitle(),
                        home.getIconUrl(),
                        targetUrl,
                        home.getContent());
                break;
            case R.id.to_fans_number_rll:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("number", ContextParameter.getUserInfo().getFansNumber());
                    AppUtils.toActivity(getActivity(), MyFansActivity.class, bundle);
                }
                break;
        }
    }
}
