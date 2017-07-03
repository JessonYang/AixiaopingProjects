package com.weslide.lovesmallscreen.view_yy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.views.AXPWebView;

/**
 * Created by YY on 2017/6/19.
 */
public class NormalPartnerFragment extends Fragment implements View.OnClickListener {

    private View fgView;
    private EditText name_edt, phone_edt, msg_edt;
    private ImageView user_applay_tag_iv;
    private Button join_btn;
    private AXPWebView axpWebview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_normal_partner,container,false);
        initView();
        initData();
        return fgView;
    }

    private void initData() {

    }

    private void initView() {
        join_btn = ((Button) fgView.findViewById(R.id.join_btn));
        axpWebview = ((AXPWebView) fgView.findViewById(R.id.webview));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.join_btn:

                break;
        }
    }
}
