package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gc.materialdesign.views.ButtonRectangle;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/7/1.
 * 添加修改名字
 */
public class NameEditFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.btn_sure)
    ButtonRectangle btnSure;
    private String name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_name_edit, container, false);

        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        return mView;
    }

    @OnClick(R.id.btn_sure)
    public void onClick() {
        name = edtName.getText().toString();
        if (!StringUtils.isBlank(name)) {
            if(name.length()>10){
                T.showShort(getContext(),"名称过长，不能超过十个字");
            }else {
                edtName();
            }
        }else{
            T.showShort(getContext(),"请输入姓名");
        }
    }
    /**
     * 选择生日
     * @param
     */
    private void edtName() {
        Request<UserInfo> request = new Request<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(name);
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "changeBaseInfo", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response response) {
                getActivity().finish();
            }
        });

    }
}
