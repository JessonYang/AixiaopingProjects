package com.showmo.ipc360.adddevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.administrator.showmosdkdemo.R;
import com.showmo.ipc360.base.BaseActivity;

/**
 * Created by Administrator on 2016/7/1.
 */
public class AddDeviceUserTipClose extends BaseActivity implements View.OnClickListener{

    Button btn_next;
    String wifissid,wifipsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddev3);

        ((Toolbar) findViewById(R.id.tool_bar)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_next = (Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

        wifissid = getIntent().getExtras().getString("wifissid");
        wifipsw = getIntent().getExtras().getString("wifipsw");
    }



    private void btn_next(){
        Intent in = new Intent(this,AddDeviceConfigSearchActivity.class);
        in.putExtra("wifissid",wifissid);
        in.putExtra("wifipsw",wifipsw);
        startActivityForResult(in,100);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==101){
            setResult(101);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
