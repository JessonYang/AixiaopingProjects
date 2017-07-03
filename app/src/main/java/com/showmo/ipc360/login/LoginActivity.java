package com.showmo.ipc360.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.showmosdkdemo.R;
import com.showmo.ipc360.IPC360Constans;
import com.showmo.ipc360.base.BaseActivity;
import com.showmo.ipc360.play.DeviceslistActivity;
import com.showmo.ipc360.util.spUtil;
import com.xmcamera.core.model.XmAccount;
import com.xmcamera.core.model.XmErrInfo;
import com.xmcamera.core.sys.XmSystem;
import com.xmcamera.core.sysInterface.IXmSystem;
import com.xmcamera.core.sysInterface.OnXmListener;
import com.xmcamera.core.sysInterface.OnXmSimpleListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/28.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    IXmSystem xmSystem;

    EditText et_username, et_psw;
    Button bt_login, bt_logindemo, bt_register;

    spUtil sp;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (IPC360Constans.getUserInfo() != null) {
            loginSuc(IPC360Constans.getUserInfo());
        }

        initview();

        init();
    }

    private void initview() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_psw = (EditText) findViewById(R.id.et_psw);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_logindemo = (Button) findViewById(R.id.bt_fast);
        bt_register = (Button) findViewById(R.id.bt_register);

        bt_login.setOnClickListener(this);
        bt_logindemo.setOnClickListener(this);
        bt_register.setOnClickListener(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sp = new spUtil(this);
        et_username.setText(sp.getUsername());
        et_psw.setText(sp.getPwd());
    }

    private void init() {
        xmSystem = XmSystem.getInstance();


        xmSystem.xmInit(this, "CN", new OnXmSimpleListener() {
            @Override
            public void onErr(XmErrInfo info) {
                Log.v("AAAAA", "init Fail");
            }

            @Override
            public void onSuc() {
                Log.v("AAAAA", "init Suc");
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
            } else if (msg.what == 0x124) {
                Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_LONG).show();
            }
            super.handleMessage(msg);
        }
    };

    ProgressDialog dialog;

    private void showLoadingDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("请稍后...");
        dialog.show();
    }

    private void closeLoadingDialog() {
        dialog.dismiss();
    }

    private void login() {
        String username = et_username.getText().toString();
        String psw = et_psw.getText().toString();
        if (username.equals("") || psw.equals("")) {
            Toast.makeText(this, "用户名或密码不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        showLoadingDialog();
        try {
            xmSystem.xmLogin(username, psw, new OnXmListener<XmAccount>() {
                @Override
                public void onSuc(XmAccount outinfo) {
                    closeLoadingDialog();
                    mHandler.sendEmptyMessage(0x123);
                    sp.setUsername(et_username.getText().toString());
                    sp.setPwd(psw);
                    loginSuc(outinfo);
                }

                @Override
                public void onErr(XmErrInfo info) {
                    closeLoadingDialog();
                    mHandler.sendEmptyMessage(0x124);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            closeLoadingDialog();
            mHandler.sendEmptyMessage(0x124);
        } finally {

        }
    }

    private void login_demo() {
        showLoadingDialog();
        try {
            xmSystem.xmLoginDemo(new OnXmListener<XmAccount>() {
                @Override
                public void onErr(XmErrInfo info) {
                    closeLoadingDialog();
                    mHandler.sendEmptyMessage(0x124);
                }

                @Override
                public void onSuc(XmAccount info) {
                    closeLoadingDialog();
                    mHandler.sendEmptyMessage(0x123);
                    loginSuc(info);
                }
            });
        } catch (Exception e) {
            closeLoadingDialog();
            e.printStackTrace();
            mHandler.sendEmptyMessage(0x124);
        } finally {

        }
    }

    private void loginSuc(XmAccount info) {
        Intent in = new Intent(this, DeviceslistActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", info);
        in.putExtras(bundle);
        startActivity(in);
        IPC360Constans.setUserInfo(info);
        finish();
    }

    private void bt_register() {
        Intent in = new Intent(this, RegisterActivity.class);
        startActivity(in);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.bt_fast:
                login_demo();
                break;
            case R.id.bt_register:
                bt_register();
                break;
        }
    }

}
