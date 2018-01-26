package com.weslide.lovesmallscreen.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.activitys.user.BindingContactsActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.view_yy.activity.ConstantListActivity;
import com.weslide.lovesmallscreen.view_yy.activity.FansConversationListActivity;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    private Button invate_friend_btn, send_constact_btn;
    private RelativeLayout to_fans_number_rll;
    private Bitmap logo;
    private View dcl_view;
    private CustomToolbar toolbar;
    private AlertDialog.Builder constactDialog;
    private Bitmap bitmap;
    private TextView tv_binding_contacts;
    private RelativeLayout rlBindingContacts;
    private TextView tvInviterName;
    private ImageView ivInviterImg;
    private LinearLayout llInviter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_share_to_earn, container, false);
        initView();
        initData();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindContactPersion();
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
        send_constact_btn.setOnClickListener(this);
        to_fans_number_rll.setOnClickListener(this);
        invitationCode.setOnClickListener(this);
        tv_binding_contacts.setOnClickListener(this);
        String targetUrl = ContextParameter.getClientConfig().getPersonalCenterShareContent().getTargetUrl();
        if (targetUrl == null) {
            targetUrl = "http://seller.aixiaoping.com/Share/Index/index";
        }
        bitmap = QRCodeUtil.createQRImage(targetUrl + "?img=" + ContextParameter.getUserInfo().getHeadimage() + "&name=" + ContextParameter.getUserInfo().getUsername()
                + "&phone=" + ContextParameter.getUserInfo().getPhone() + "&code=" + ContextParameter.getUserInfo().getInviteCode() + "&appVersion=" + AppUtils.getVersionCode(getActivity()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics()));
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
        tv_binding_contacts = ((TextView) mView.findViewById(R.id.tv_binding_contacts));
        tvInviterName = ((TextView) mView.findViewById(R.id.tv_inviter_name));
        invitationCode = ((ImageView) mView.findViewById(R.id.invitation_code));
        ivInviterImg = ((ImageView) mView.findViewById(R.id.iv_inviter_img));
        personalInvatationCode = ((TextView) mView.findViewById(R.id.personal_invatation_code));
        fansNum = ((TextView) mView.findViewById(R.id.fans_num));
        invate_friend_btn = ((Button) mView.findViewById(R.id.invate_friend_btn));
        send_constact_btn = ((Button) mView.findViewById(R.id.send_constact_btn));
        to_fans_number_rll = ((RelativeLayout) mView.findViewById(R.id.to_fans_number_rll));
        rlBindingContacts = ((RelativeLayout) mView.findViewById(R.id.rl_binding_contacts));
        llInviter = ((LinearLayout) mView.findViewById(R.id.ll_inviter));
        dcl_view = ((View) mView.findViewById(R.id.dcl_view));
        toolbar = ((CustomToolbar) mView.findViewById(R.id.toolbar));

    }

    private void bindContactPersion(){
        //是否绑定推荐人，如果绑定则显示绑定上级头像及昵称
        if (ContextParameter.getUserInfo().getBindingInviter() == true) {
            llInviter.setVisibility(View.VISIBLE);
            rlBindingContacts.setVisibility(View.GONE);
            if (StringUtils.isBlank(ContextParameter.getUserInfo().getInviterImg())) {
                ivInviterImg.setImageResource(R.drawable.icon_defult);
            } else {
                Glide.with(getActivity()).load(ContextParameter.getUserInfo().getInviterImg()).asBitmap().error(R.drawable.icon_defult).placeholder(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(ivInviterImg) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivInviterImg.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
            if (!StringUtils.isBlank(ContextParameter.getUserInfo().getInviterName())) {
                tvInviterName.setText(ContextParameter.getUserInfo().getInviterName());
            }
        } else {
            llInviter.setVisibility(View.GONE);
            rlBindingContacts.setVisibility(View.VISIBLE);

        }
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
                if (name == null) {
                    name = "";
                }
                if (img == null) {
                    img = "";
                }
                if (phone == null) {
                    phone = "";
                }
                if (code == null) {
                    code = "";
                }

//                String down = ContextParameter.getClientConfig().getDownload();
//                String score = ContextParameter.getUserInfo().getScore();
                String targetUrl = url + "?img=" + img + "&name=" + name + "&phone=" + phone + "&code=" + code + "&appVersion=" + appVersion;
//                Log.d("雨落无痕丶", "onClick: "+targetUrl);
                ShareUtils.share(getActivity(), home.getTitle(),
                        home.getIconUrl(),
                        targetUrl,
                        home.getContent());
                break;
            case R.id.send_constact_btn:
                constactDialog = new AlertDialog.Builder(getActivity());
                constactDialog.setTitle("提示").setMessage("是否允许访问系统通讯录?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AppUtils.toActivity(getActivity(), ConstantListActivity.class);
                            }
                        }).create().show();
                break;
            case R.id.to_fans_number_rll:
                if (ContextParameter.isLogin() == false) {
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class);
                } else {
                    //旧版我的粉丝列表
                    /*Bundle bundle = new Bundle();
                    bundle.putString("number", ContextParameter.getUserInfo().getFansNumber());
                    AppUtils.toActivity(getActivity(), MyFansActivity.class, bundle);*/

                    //改版我的粉丝列表页面
                    AppUtils.toActivity(getActivity(), FansConversationListActivity.class);
                }
                break;
            case R.id.invitation_code:
                new AlertDialog.Builder(getActivity()).setTitle("提示")
                        .setMessage("保存二维码到本地？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                saveImageToGallery(getActivity(), bitmap, "爱小屏二维码.jpg");
                            }
                        })
                        .create()
                        .show();
                break;
            //绑定联系人
            case R.id.tv_binding_contacts:
                AppUtils.toActivity(getActivity(), BindingContactsActivity.class);
                break;
        }
    }

    private void saveImageToGallery(Context context, Bitmap bitmap, String name) {
        File axp_picture = new File(Environment.getExternalStorageDirectory(), "axp_picture");
        if (!axp_picture.exists()) {
            axp_picture.mkdirs();
        }
        File file = new File(axp_picture, name);
        try {
            //保存图片到本地
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将文件插入图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), name, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
        Toast.makeText(ShareToEarnFragment.this.getActivity(), "图片保存成功!", Toast.LENGTH_SHORT).show();
    }
}
