package com.showmo.ipc360.adddevice;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.showmosdkdemo.R;
import com.showmo.ipc360.base.BaseActivity;
import com.showmo.ipc360.util.spUtil;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;

/**
 * Created by Administrator on 2016/7/1.
 */
public class AddDeviceSetNetworkActivity extends BaseActivity implements View.OnClickListener {

    TextView tvWifo;
    EditText et_wifipsw;
    View btn_next;

    spUtil sp;
    String wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddev2);

        ((Toolbar) findViewById(R.id.tool_bar)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

        tvWifo = (TextView) findViewById(R.id.tv_wifi);
        et_wifipsw = (EditText) findViewById(R.id.et_wifipsw);

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();

        sp = new spUtil(this);

        if (!StringUtils.isEmpty(ssid)) {
            wifi = ssid.replace("\"", "");
            sp.setWifi(wifi);
        } else {
            T.showLong(this, "请链接WIFI后重试");
            finish();
        }


        tvWifo.setText("WIFI：" + sp.getWifi());
        et_wifipsw.setText(sp.getWifiPsw());
    }

    private void btn_next() {
        String psw = et_wifipsw.getText().toString();
     /*   if (wifi.equals("") || psw.equals("")) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_LONG).show();
            return;
        }*/
        Intent in = new Intent(this, AddDeviceUserTipClose.class);
        in.putExtra("wifissid", wifi);
        in.putExtra("wifipsw", psw);
        startActivityForResult(in, 100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                btn_next();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 101) {
            setResult(101);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
