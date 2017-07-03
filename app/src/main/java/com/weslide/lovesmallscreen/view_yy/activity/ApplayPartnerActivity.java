package com.weslide.lovesmallscreen.view_yy.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.user.RetrieveActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.ApplyPartnerModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.view_yy.fragment.ApplayPartnerBaseFragment;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.util.List;

/**
 * Created by YY on 2017/6/18.
 */
public class ApplayPartnerActivity extends BaseActivity implements View.OnClickListener {
    private ImageView user_face_iv1, user_face_iv2, user_face_iv3, user_face_iv4, fans_icon, hhr_icon, syhhr_icon, yys_icon;
    private ImageView user_tag_iv, hhr_shengji_iv, syhhr_shengji_iv, yys_shengji_iv, back_iv, share_iv;
    private TextView user_name_tv, user_phone_tv, user_tag;

    public static int type;
    private FragmentTransaction transaction;
    private Button join_btn;
    private FrameLayout web_fl;
    private List<String> links;
    private ApplyPartnerModel data;
    public static LoadingDialog loadingDialog;
    public static ApplayPartnerActivity applayPartnerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applay_partner);
        type = getIntent().getExtras().getInt("type", 0);
        applayPartnerActivity = this;
        initView();
        initData();
    }

    private void initData() {
//        fans_shengji_iv.setOnClickListener(this);
        hhr_shengji_iv.setOnClickListener(this);
        syhhr_shengji_iv.setOnClickListener(this);
        yys_shengji_iv.setOnClickListener(this);
        fans_icon.setOnClickListener(this);
        hhr_icon.setOnClickListener(this);
        syhhr_icon.setOnClickListener(this);
        yys_icon.setOnClickListener(this);
        join_btn.setOnClickListener(this);
        back_iv.setOnClickListener(this);
        share_iv.setOnClickListener(this);
        if (type == 0) {
            join_btn.setText("我想了解合伙人");
            join_btn.setVisibility(View.VISIBLE);
        } else if (type == 1) {
            join_btn.setText("我想加入");
            join_btn.setVisibility(View.VISIBLE);
        } else {
            join_btn.setVisibility(View.GONE);
        }
        user_name_tv.setText(ContextParameter.getUserInfo().getUsername());
        user_phone_tv.setText(ContextParameter.getUserInfo().getPhone());
        if (ContextParameter.getUserInfo().getIspartenr().equals("0")) {
            user_tag.setText("粉丝");
            user_tag_iv.setImageResource(R.drawable.axpfs_icon);
        } else if (ContextParameter.getUserInfo().getIspartenr().equals("1")) {
            user_tag.setText("合伙人");
            user_tag_iv.setImageResource(R.drawable.syhhr_icon);
        } else if (ContextParameter.getUserInfo().getIspartenr().equals("2")) {
            user_tag.setText("事业合伙人");
            user_tag_iv.setImageResource(R.drawable.csdl_icon);
        } else if (ContextParameter.getUserInfo().getIspartenr().equals("3")) {
            user_tag.setText("城市运营商");
            user_tag_iv.setImageResource(R.drawable.csdl_icon);
        }
        Glide.with(this).load(ContextParameter.getUserInfo().getHeadimage()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(user_face_iv1) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(ApplayPartnerActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                user_face_iv1.setImageDrawable(circularBitmapDrawable);
            }
        });
        Glide.with(this).load(ContextParameter.getUserInfo().getHeadimage()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(user_face_iv2) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(ApplayPartnerActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                user_face_iv2.setImageDrawable(circularBitmapDrawable);
            }
        });
        Glide.with(this).load(ContextParameter.getUserInfo().getHeadimage()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(user_face_iv3) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(ApplayPartnerActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                user_face_iv3.setImageDrawable(circularBitmapDrawable);
            }
        });
        Glide.with(this).load(ContextParameter.getUserInfo().getHeadimage()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(user_face_iv4) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(ApplayPartnerActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                user_face_iv4.setImageDrawable(circularBitmapDrawable);
            }
        });
        showImage();
//        loadingDialog = new LoadingDialog(this);
//        loadingDialog.show();
        RXUtils.request(this, new Request(), "applyPartner", new SupportSubscriber<Response<ApplyPartnerModel>>() {

            @Override
            public void onNext(Response<ApplyPartnerModel> applyPartnerModelResponse) {
                data = applyPartnerModelResponse.getData();
                links = data.getLinks();
                ApplayPartnerBaseFragment fragment = new ApplayPartnerBaseFragment();
                Bundle args = new Bundle();
                args.putString("url", links.get(type));
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.web_fl, fragment).commit();
            }
        });
    }

    private void showImage() {
        switch (type) {
            case 0:
                user_face_iv1.setVisibility(View.VISIBLE);
                user_face_iv2.setVisibility(View.GONE);
                hhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv3.setVisibility(View.GONE);
                syhhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv4.setVisibility(View.GONE);
                yys_shengji_iv.setVisibility(View.GONE);
                break;
            case 1:
                user_face_iv1.setVisibility(View.GONE);
                user_face_iv2.setVisibility(View.VISIBLE);
                hhr_shengji_iv.setVisibility(View.VISIBLE);
                user_face_iv3.setVisibility(View.GONE);
                syhhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv4.setVisibility(View.GONE);
                yys_shengji_iv.setVisibility(View.GONE);
                break;
            case 2:
                user_face_iv1.setVisibility(View.GONE);
                user_face_iv2.setVisibility(View.GONE);
                hhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv3.setVisibility(View.VISIBLE);
                syhhr_shengji_iv.setVisibility(View.VISIBLE);
                user_face_iv4.setVisibility(View.GONE);
                yys_shengji_iv.setVisibility(View.GONE);
                break;
            case 3:
                user_face_iv1.setVisibility(View.GONE);
//                fans_shengji_iv.setVisibility(View.GONE);
                user_face_iv2.setVisibility(View.GONE);
                hhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv3.setVisibility(View.GONE);
                syhhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv4.setVisibility(View.VISIBLE);
                yys_shengji_iv.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initView() {
        user_name_tv = ((TextView) findViewById(R.id.user_name_tv));
        user_phone_tv = ((TextView) findViewById(R.id.user_phone_tv));
        user_tag = ((TextView) findViewById(R.id.user_tag));
        back_iv = ((ImageView) findViewById(R.id.back_iv));
        share_iv = ((ImageView) findViewById(R.id.share_iv));
        user_tag_iv = ((ImageView) findViewById(R.id.user_tag_iv));
        user_face_iv1 = ((ImageView) findViewById(R.id.user_face_iv1));
        user_face_iv2 = ((ImageView) findViewById(R.id.user_face_iv2));
        user_face_iv3 = ((ImageView) findViewById(R.id.user_face_iv3));
        user_face_iv4 = ((ImageView) findViewById(R.id.user_face_iv4));
        hhr_shengji_iv = ((ImageView) findViewById(R.id.hhr_shengji_iv));
        syhhr_shengji_iv = ((ImageView) findViewById(R.id.syhhr_shengji_iv));
        yys_shengji_iv = ((ImageView) findViewById(R.id.yys_shengji_iv));
        yys_shengji_iv = ((ImageView) findViewById(R.id.yys_shengji_iv));
        fans_icon = ((ImageView) findViewById(R.id.fans_icon));
        hhr_icon = ((ImageView) findViewById(R.id.hhr_icon));
        syhhr_icon = ((ImageView) findViewById(R.id.syhhr_icon));
        yys_icon = ((ImageView) findViewById(R.id.yys_icon));
        join_btn = ((Button) findViewById(R.id.join_btn));
        web_fl = ((FrameLayout) findViewById(R.id.web_fl));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fans_icon:
                type = 0;
                join_btn.setText("我想了解合伙人");
                join_btn.setVisibility(View.VISIBLE);
                user_face_iv1.setVisibility(View.VISIBLE);
//                fans_shengji_iv.setVisibility(View.GONE);
                user_face_iv2.setVisibility(View.GONE);
                hhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv3.setVisibility(View.GONE);
                syhhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv4.setVisibility(View.GONE);
                yys_shengji_iv.setVisibility(View.GONE);
                if (links != null && links.size() > 0) {
                    ApplayPartnerBaseFragment fragment = new ApplayPartnerBaseFragment();
                    Bundle args = new Bundle();
                    args.putString("url", links.get(type));
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.web_fl, fragment).commit();
                }
                break;
            case R.id.hhr_icon:
            case R.id.hhr_shengji_iv:
                type = 1;
                if (ContextParameter.getUserInfo().getIspartenr() != null && ContextParameter.getUserInfo().getIspartenr().equals("1")) {
                    join_btn.setVisibility(View.GONE);
                    hhr_shengji_iv.setVisibility(View.GONE);
                } else {
                    join_btn.setText("我想加入");
                    join_btn.setVisibility(View.VISIBLE);
                    hhr_shengji_iv.setVisibility(View.VISIBLE);
                }
                user_face_iv1.setVisibility(View.GONE);
//                fans_shengji_iv.setVisibility(View.GONE);
                user_face_iv2.setVisibility(View.VISIBLE);
                user_face_iv3.setVisibility(View.GONE);
                syhhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv4.setVisibility(View.GONE);
                yys_shengji_iv.setVisibility(View.GONE);
                if (links != null && links.size() > 0) {
                    ApplayPartnerBaseFragment fragment = new ApplayPartnerBaseFragment();
                    Bundle args = new Bundle();
                    args.putString("url", links.get(type));
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.web_fl, fragment).commit();
                }
                break;
            case R.id.syhhr_icon:
            case R.id.syhhr_shengji_iv:
                type = 2;
                if (type == 0 || type == 1) {
                    join_btn.setVisibility(View.VISIBLE);
                } else {
                    join_btn.setVisibility(View.GONE);
                }
                user_face_iv1.setVisibility(View.GONE);
//                fans_shengji_iv.setVisibility(View.GONE);
                user_face_iv2.setVisibility(View.GONE);
                hhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv3.setVisibility(View.VISIBLE);
                syhhr_shengji_iv.setVisibility(View.VISIBLE);
                user_face_iv4.setVisibility(View.GONE);
                yys_shengji_iv.setVisibility(View.GONE);
                if (links != null && links.size() > 0) {
                    ApplayPartnerBaseFragment fragment = new ApplayPartnerBaseFragment();
                    Bundle args = new Bundle();
                    args.putString("url", links.get(type));
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.web_fl, fragment).commit();
                }
                break;
            case R.id.yys_icon:
            case R.id.yys_shengji_iv:
                type = 3;
                if (type == 0 || type == 1) {
                    join_btn.setVisibility(View.VISIBLE);
                } else {
                    join_btn.setVisibility(View.GONE);
                }
                user_face_iv1.setVisibility(View.GONE);
//                fans_shengji_iv.setVisibility(View.GONE);
                user_face_iv2.setVisibility(View.GONE);
                hhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv3.setVisibility(View.GONE);
                syhhr_shengji_iv.setVisibility(View.GONE);
                user_face_iv4.setVisibility(View.VISIBLE);
                yys_shengji_iv.setVisibility(View.VISIBLE);
                if (links != null && links.size() > 0) {
                    ApplayPartnerBaseFragment fragment = new ApplayPartnerBaseFragment();
                    Bundle args = new Bundle();
                    args.putString("url", links.get(type));
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.web_fl, fragment).commit();
                }
                break;
            case R.id.join_btn:
                if (type == 1) {
                    getMonitorAccount();
//                    AppUtils.toActivity(this,ApplayPartnerPayActivity.class);
                } else if (type == 0) {
                    type = 1;
                    user_face_iv1.setVisibility(View.GONE);
//                    fans_shengji_iv.setVisibility(View.GONE);
                    user_face_iv2.setVisibility(View.VISIBLE);
//                    hhr_shengji_iv.setVisibility(View.VISIBLE);
                    user_face_iv3.setVisibility(View.GONE);
                    syhhr_shengji_iv.setVisibility(View.GONE);
                    user_face_iv4.setVisibility(View.GONE);
                    yys_shengji_iv.setVisibility(View.GONE);
                    if (links != null && links.size() > 0) {
                        ApplayPartnerBaseFragment fragment = new ApplayPartnerBaseFragment();
                        Bundle args = new Bundle();
                        args.putString("url", links.get(type));
                        fragment.setArguments(args);
                        getSupportFragmentManager().beginTransaction().replace(R.id.web_fl, fragment).commit();
                    }
                    if (!ContextParameter.getUserInfo().getIspartenr().equals("0")) {
                        hhr_shengji_iv.setVisibility(View.GONE);
                        join_btn.setText("我想加入");
                        join_btn.setVisibility(View.GONE);
                    } else {
                        hhr_shengji_iv.setVisibility(View.VISIBLE);
                        join_btn.setText("我想加入");
                        join_btn.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.share_iv:
                ShareUtils.share(this, data.getShareTitle(), data.getShareIconUrl(), data.getShareTargetUrl(), data.getShareContent());
                break;
        }
    }

    private void getMonitorAccount() {
        RXUtils.request(this, new Request(), "getCheckPhone", new SupportSubscriber<Response>() {

            @Override
            public void onNext(Response monitorAccountResponse) {
                if (monitorAccountResponse.getStatus() == 1) {
                    AppUtils.toActivity(ApplayPartnerActivity.this, ApplayPartnerPayActivity.class);
                }
                // login("18178313317","123456");
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                if (response.getStatus() == -80) {
                    new AlertDialog.Builder(ApplayPartnerActivity.this).setTitle("提示").setMessage(response.getMessage()).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AppUtils.toActivity(ApplayPartnerActivity.this, RetrieveActivity.class);
                        }
                    }).create().show();
                    return;
                }
//                Log.d("雨落无痕丶", "onResponseError:yy "+response.getStatus());
                T.showShort(ApplayPartnerActivity.this, response.getMessage());
            }
        });

    }
}
