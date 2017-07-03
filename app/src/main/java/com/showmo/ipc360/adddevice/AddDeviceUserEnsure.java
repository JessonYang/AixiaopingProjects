package com.showmo.ipc360.adddevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.administrator.showmosdkdemo.R;
import com.showmo.ipc360.login.ResetDeviceActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;

/**
 * Created by Administrator on 2016/7/1.
 * 添加设备
 */
public class AddDeviceUserEnsure extends BaseActivity implements View.OnClickListener{


    Button btn_next,btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddev1);

        ((Toolbar) findViewById(R.id.tool_bar)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initview();

    }
    private void initview(){
        btn_next = (Button)findViewById(R.id.btn_next);
        btn_reset = (Button)findViewById(R.id.btn_reset);

        btn_next.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
    }

    private void btn_next(){
        Intent in = new Intent(this,AddDeviceSetNetworkActivity.class);
        startActivityForResult(in,100);
    }

    private void btn_reset(){
        Intent in = new Intent(this,ResetDeviceActivity.class);
        startActivity(in);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                btn_next();
                break;
            case R.id.btn_reset:
                btn_reset();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==101){
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
