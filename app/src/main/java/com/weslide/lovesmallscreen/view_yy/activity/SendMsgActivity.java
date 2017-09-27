package com.weslide.lovesmallscreen.view_yy.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.ContactBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

import java.util.List;

public class SendMsgActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView constact_name_tv, constact_phome_tv;
    private EditText send_content_tv;
    private Button send_msg_btn;
    private ImageView constact_iv;
    private String name, phone;
    private CustomToolbar tool_bar;
    private List<ContactBean> phoneList;
    private SmsManager mSmsManager;
    private static final String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    private SmsBroadcastReceiver smsBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
        initView();
        initData();
    }

    private void initData() {
        registerReceiver(smsBroadcastReceiver, new IntentFilter(SENT_SMS_ACTION));
        phoneList = ((List<ContactBean>) getIntent().getSerializableExtra("phoneList"));
        if (phoneList == null || phoneList.size() == 0) {
            name = getIntent().getExtras().getString("name");
            phone = getIntent().getExtras().getString("phone");
            constact_name_tv.setText(name);
            constact_phome_tv.setVisibility(View.VISIBLE);
            constact_phome_tv.setText(phone);
        } else {
            constact_phome_tv.setVisibility(View.GONE);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < phoneList.size(); i++) {
                if (i != phoneList.size() - 1) {
                    builder.append(phoneList.get(i).getTitle()).append("、");
                } else {
                    builder.append(phoneList.get(i).getTitle());
                }
            }
            constact_name_tv.setText(builder.toString());
        }


        RXUtils.request(this, new Request(), "getDefaultMsg", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response response) {
                if (response.getMessage() != null) {
                    send_content_tv.setText(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("雨落无痕丶", "onError: " + e.toString());
            }

            @Override
            public void onResponseError(Response response) {
                Log.d("雨落无痕丶", "onResponseError: ==");
            }
        });
        send_msg_btn.setOnClickListener(this);
        constact_iv.setOnClickListener(this);
        tool_bar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });

    }

    private void initView() {
        constact_name_tv = ((TextView) findViewById(R.id.constact_name_tv));
        constact_phome_tv = ((TextView) findViewById(R.id.constact_phome_tv));
        send_content_tv = ((EditText) findViewById(R.id.send_content_tv));
        constact_iv = ((ImageView) findViewById(R.id.constact_iv));
        send_msg_btn = ((Button) findViewById(R.id.send_msg_btn));
        tool_bar = ((CustomToolbar) findViewById(R.id.send_msg_toolbar));
        mSmsManager = SmsManager.getDefault();
        smsBroadcastReceiver = new SmsBroadcastReceiver();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_msg_btn:
                String msg = send_content_tv.getText().toString();
                if (phoneList == null || phoneList.size() == 0) {
//                    doSendSMSTo(phone, msg);
//                    sendSMS(phone,msg);
                    // 创建一个PendingIntent对象
                    PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(SENT_SMS_ACTION), 0);
                    if (msg.length() > 70) {
                        // 拆分短信内容（手机短信长度限制）
                        List<String> divideContents = SmsManager.getDefault().divideMessage(msg);
                        for (String text : divideContents) {
                            SmsManager.getDefault().sendTextMessage(phone, null, text, pi, null);
                        }
                    } else {
                        // 发送短信
                        SmsManager.getDefault().sendTextMessage(phone, null, msg, pi, null);
                    }
                } else {
                    for (ContactBean bean : phoneList) {
                        PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(SENT_SMS_ACTION), 0);
                        if (msg.length() > 70) {
                            // 拆分短信内容（手机短信长度限制）
                            List<String> divideContents = SmsManager.getDefault().divideMessage(msg);
                            for (String text : divideContents) {
                                SmsManager.getDefault().sendTextMessage(bean.getPhoneNum(), null, text, pi, null);
                            }
                        } else {
                            // 发送短信
                            SmsManager.getDefault().sendTextMessage(bean.getPhoneNum(), null, msg, pi, null);
                        }
//                        mSmsManager.sendTextMessage(bean.getPhoneNum(), null, msg, pi, null);
                        Log.d("雨落无痕丶", "phone: "+bean.getPhoneNum());
                    }
                }
                break;

            case R.id.constact_iv:
                finish();
                break;
        }
    }

    /**
     * 调用系统发短信
     *
     * @param phoneNumber
     * @param message
     */
    public void doSendSMSTo(String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        }
    }

    /**
     * 直接调用短信接口发短信，不含发送报告和接受报告
     *
     * @param phoneNumber
     * @param message
     */
    public void sendSMS(String phoneNumber, String message) {
        // 获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        // 拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        }
    }

    private class SmsBroadcastReceiver extends BroadcastReceiver {

        private AlertDialog.Builder builder;
        private AlertDialog alertDialog_success, alertDialog_failure;

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    if (alertDialog_success == null) {
                        alertDialog_success = new AlertDialog.Builder(SendMsgActivity.this)
                                .setTitle("消息")
                                .setMessage("短信发送成功!")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SendMsgActivity.this.finish();
                                        ConstantListActivity.constantListActivity.finish();
                                    }
                                }).create();
                        alertDialog_success.show();
                    } else {
                        if (!alertDialog_success.isShowing()) {
                            alertDialog_success.show();
                        }
                    }
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    if (alertDialog_failure == null) {
                        alertDialog_failure = new AlertDialog.Builder(SendMsgActivity.this)
                                .setTitle("消息")
                                .setMessage("短信发送失败!")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).create();
                        alertDialog_failure.show();
                    } else {
                        if (!alertDialog_failure.isShowing()) {
                            alertDialog_failure.show();
                        }
                    }
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    Log.d("雨落无痕丶", "onReceive: off");
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    Log.d("雨落无痕丶", "onReceive: pdu");
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsBroadcastReceiver);
    }
}
