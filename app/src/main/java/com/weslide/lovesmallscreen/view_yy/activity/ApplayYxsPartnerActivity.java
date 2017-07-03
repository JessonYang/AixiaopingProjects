package com.weslide.lovesmallscreen.view_yy.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.rey.material.widget.Button;
import com.showmo.ipc360.base.BaseActivity;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;

/**
 * Created by YY on 2017/6/18.
 */
public class ApplayYxsPartnerActivity extends BaseActivity implements View.OnClickListener {
    private ImageView user_face_iv;
    private ImageView shengji_iv, user_applay_tag_iv;
    private TextView user_name_tv, user_phone_tv;
    private EditText name_edt, phone_edt, msg_edt;
    private Button commit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applay_yxs_partner);
        initView();
        initData();
    }

    private void initData() {
        shengji_iv.setOnClickListener(this);
        commit_btn.setOnClickListener(this);
        user_name_tv.setText(ContextParameter.getUserInfo().getUsername());
        user_phone_tv.setText(ContextParameter.getUserInfo().getPhone());
        Glide.with(this).load(ContextParameter.getUserInfo().getHeadimage()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(user_face_iv) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(ApplayYxsPartnerActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                user_face_iv.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    private void initView() {
        user_face_iv = ((ImageView) findViewById(R.id.user_face_iv));
        shengji_iv = ((ImageView) findViewById(R.id.shengji_iv));
        user_applay_tag_iv = ((ImageView) findViewById(R.id.user_applay_tag_iv));
        user_name_tv = ((TextView) findViewById(R.id.user_name_tv));
        user_phone_tv = ((TextView) findViewById(R.id.user_phone_tv));
        name_edt = ((EditText) findViewById(R.id.name_edt));
        phone_edt = ((EditText) findViewById(R.id.phone_edt));
        msg_edt = ((EditText) findViewById(R.id.msg_edt));
        commit_btn = ((Button) findViewById(R.id.commit_btn));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shengji_iv:

                break;
            case R.id.commit_btn:

                break;
        }
    }
}
