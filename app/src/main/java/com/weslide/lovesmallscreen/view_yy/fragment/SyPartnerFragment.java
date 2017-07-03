package com.weslide.lovesmallscreen.view_yy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rey.material.widget.Button;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.view_yy.activity.ApplayPartnerActivity;

/**
 * Created by YY on 2017/6/19.
 */
public class SyPartnerFragment extends BaseFragment implements View.OnClickListener {

    private View fgView;
    private EditText name_edt, phone_edt, msg_edt;
    private Button commit_btn;
    private ImageView user_applay_tag_iv;
    private TextView user_applay_tag_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_sy_partner,container,false);
        initView();
        initData();
        return fgView;
    }

    private void initData() {
        commit_btn.setOnClickListener(this);
        if (ApplayPartnerActivity.type == 3) {
            user_applay_tag_tv.setText("升级事业合伙人基本信息");
            user_applay_tag_iv.setImageResource(R.drawable.icon_syhhr);
        } else if (ApplayPartnerActivity.type == 3) {
            user_applay_tag_tv.setText("升级城市运营商基本信息");
            user_applay_tag_iv.setImageResource(R.drawable.grzx_csyyshz);
        }
    }

    private void initView() {
        user_applay_tag_iv = ((ImageView) fgView.findViewById(R.id.user_applay_tag_iv));
        user_applay_tag_tv = ((TextView) fgView.findViewById(R.id.user_applay_tag_tv));
        name_edt = ((EditText) fgView.findViewById(R.id.name_edt));
        phone_edt = ((EditText) fgView.findViewById(R.id.phone_edt));
        msg_edt = ((EditText) fgView.findViewById(R.id.msg_edt));
        commit_btn = ((Button) fgView.findViewById(R.id.commit_btn));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit_btn:

                break;
        }
    }
}
