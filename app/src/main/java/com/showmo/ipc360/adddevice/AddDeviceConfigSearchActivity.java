package com.showmo.ipc360.adddevice;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.showmosdkdemo.R;
import com.showmo.ipc360.base.BaseActivity;
import com.showmo.ipc360.util.spUtil;
import com.skyfishjy.library.RippleBackground;
import com.weslide.lovesmallscreen.utils.L;
import com.xmcamera.core.model.XmDevice;
import com.xmcamera.core.model.XmErrInfo;
import com.xmcamera.core.sys.XmSystem;
import com.xmcamera.core.sysInterface.IXmBinderManager;
import com.xmcamera.core.sysInterface.IXmSystem;
import com.xmcamera.core.sysInterface.OnXmBindListener;

/**
 * Created by Administrator on 2016/7/2.
 */
public class AddDeviceConfigSearchActivity extends BaseActivity implements View.OnClickListener{

    IXmSystem xmSystem;
    IXmBinderManager xmBinderManager;

    Button btn_next;

    String wifissid,wifipsw;
    spUtil sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddev4);

        ((Toolbar) findViewById(R.id.tool_bar)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        btn_next = (Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

        xmSystem = XmSystem.getInstance();
        xmBinderManager = xmSystem.xmGetBinderManager();
        xmBinderManager.setOnBindListener(xmBindListener);

        wifissid = getIntent().getExtras().getString("wifissid");
        wifipsw = getIntent().getExtras().getString("wifipsw");
        xmBinderManager.beginWork(getApplicationContext(), wifissid, wifipsw);

        sp = new spUtil(this);
    }

    OnXmBindListener xmBindListener = new OnXmBindListener() {
        @Override
        public void addedByOther(String uuid, String user) {
            xmBinderManager.exitAllWork();
            finish();
            L.e("====addedByOther=====");
        }
        @Override
        public void addedSuccess(XmDevice dev) {
            xmBinderManager.exitAllWork();
            mHandler.sendEmptyMessage(0x123);
            sp.setWifi(wifissid);
            sp.setWifiPsw(wifipsw);
            setResult(101);
            finish();
            L.e("====XmDevice=====");
        }
        @Override
        public void addedBySelf(String uuid, String user) {
            xmBinderManager.exitAllWork();
            finish();
            L.e("====addedBySelf=====");
        }
        @Override
        public void onDevConnectMgrErr(String uuid) {
            finish();
            L.e("====onDevConnectMgrErr=====");
        }

      /*  @Override
        public void addErr(String s, XmErrInfo xmErrInfo) {

        }*/

        @Override
        public void addErr(XmErrInfo xmErrInfo) {
           L.e("====addErr====="+xmErrInfo.errCode);
        }

    };

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x123){
                Toast.makeText(AddDeviceConfigSearchActivity.this,"添加成功！",Toast.LENGTH_LONG).show();
            }
            super.handleMessage(msg);
        }
    };

    private void btn_next(){
        btn_next.setVisibility(View.GONE);
        xmBinderManager.exitSendWork();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                btn_next();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        setResult(101);
        xmBinderManager.exitAllWork();
        xmBinderManager.setOnBindListener(null);
        super.onDestroy();
    }
}
