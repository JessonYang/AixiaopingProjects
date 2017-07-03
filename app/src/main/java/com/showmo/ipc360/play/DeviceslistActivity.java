package com.showmo.ipc360.play;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.administrator.showmosdkdemo.R;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.showmo.ipc360.IPC360Constans;
import com.showmo.ipc360.adddevice.AddDeviceUserEnsure;
import com.showmo.ipc360.fragment.DevicesListFragment;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.utils.L;
import com.xmcamera.core.model.XmAccount;
import com.xmcamera.core.model.XmErrInfo;
import com.xmcamera.core.sys.XmSystem;
import com.xmcamera.core.sysInterface.IXmSystem;
import com.xmcamera.core.sysInterface.OnXmMgrConnectStateChangeListener;
import com.xmcamera.core.sysInterface.OnXmSimpleListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/29.
 * 设备列表
 */
public class DeviceslistActivity extends BaseActivity {

    @BindView(R.id.tl_my_tab)
    SegmentTabLayout tlTab;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    IXmSystem xmSystem;


    String[] mTiles = new String[]{"我的监控", "好友分享"};
    ArrayList<Fragment> mFragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceslist);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DevicesListFragment myDev = DevicesListFragment.getInstance(DevicesListFragment.Owner);
        DevicesListFragment shareDev = DevicesListFragment.getInstance(DevicesListFragment.Share);

        mFragments.add(myDev);
        mFragments.add(shareDev);

        tlTab.setTabData(mTiles, this, R.id.container, mFragments);
        loginMgr();

    }

    private void loginMgr() {

        xmSystem = XmSystem.getInstance();

        xmSystem.registerOnMgrConnectChangeListener(onXmMgrConnectStateChangeListener);

        xmSystem.xmMgrSignin(new OnXmSimpleListener() {
            @Override
            public void onErr(XmErrInfo info) {
//                Log.v("AAAAA", "MgrSignin fail!");
            }

            @Override
            public void onSuc() {
//                Log.v("AAAAA", "MgrSignin suc!");
            }
        });
    }

    OnXmMgrConnectStateChangeListener onXmMgrConnectStateChangeListener = new OnXmMgrConnectStateChangeListener() {
        @Override
        public void onChange(boolean connectState) {
            Log.v("AAAAA", "OnXmMgrConnectStateChangeListener onChange is " + connectState);
        }
    };


    @OnClick(R.id.btn_add_devices)
    public void onClick() {
        bt_binder();
    }

    private void bt_binder() {
        Intent in = new Intent(this, AddDeviceUserEnsure.class);
        startActivity(in);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        xmSystem.xmLogout();
        IPC360Constans.setUserInfo(null);

    }
}
