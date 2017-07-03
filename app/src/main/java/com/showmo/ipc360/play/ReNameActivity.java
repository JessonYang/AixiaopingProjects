package com.showmo.ipc360.play;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.xmcamera.core.model.XmErrInfo;
import com.xmcamera.core.sys.XmSystem;
import com.xmcamera.core.sysInterface.IXmInfoManager;
import com.xmcamera.core.sysInterface.IXmSystem;
import com.xmcamera.core.sysInterface.OnXmSimpleListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/10/20.
 */
public class ReNameActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.edt_name)
    EditText edtName;
    IXmSystem xmSystem;
    IXmInfoManager manager;
    Intent in;
    int cameraId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advices_rename);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        in = getIntent();
        cameraId = in.getIntExtra("cameraId", 0);
        xmSystem = XmSystem.getInstance();
        manager = xmSystem.xmGetInfoManager(cameraId);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReNameActivity.this.finish();
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x001){
                T.showShort(ReNameActivity.this,"修改成功");
            }else if(msg.what == 0x002){
                T.showShort(ReNameActivity.this,"修改失败");
            }
        }
    };

    @OnClick(R.id.btn_sure)
    public void onClick() {
        String name = edtName.getText().toString();
        if(StringUtils.isBlank(name)){
            T.showShort(ReNameActivity.this,"请输入名称");
        }else{
            rename(name);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void rename(String name){
       manager.xmModifyDeviceName(name, new OnXmSimpleListener() {
           @Override
           public void onErr(XmErrInfo xmErrInfo) {
               handler.sendEmptyMessage(0x002);
           }

           @Override
           public void onSuc() {
               handler.sendEmptyMessage(0x001);
               ReNameActivity.this.finish();
           }
       });
    }
}
