package com.weslide.lovesmallscreen.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.config.ClientConfig;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/8/3.
 */
public class RegisterSuccessfulActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_invite_code)
    TextView tvInviteCode;
    @BindView(R.id.img_two)
    ImageView imgTwo;
    String URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_successful);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterSuccessfulActivity.this.finish();
            }
        });
        loadClientConfig();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String inviteCode = bundle.getString("inviteCode");
        tvInviteCode.setText(inviteCode);
        URL = ContextParameter.getClientConfig().getDownload() + "?invitecode=" +inviteCode;
        Bitmap bitmap = QRCodeUtil.createQRImage(ContextParameter.getClientConfig().getDownload() + "?invitecode=" +inviteCode,200,200);
        imgTwo.setImageBitmap(bitmap);
    }

    @OnClick({R.id.tv_invite_friends, R.id.tv_obtain, R.id.tv_rebate, R.id.btn_come, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_invite_friends:
                ShareContent home = ContextParameter.getClientConfig().getHomeShareContent();
                ShareUtils.share(this, home.getTitle(),
                        home.getIconUrl(),
                        URL,
                        home.getContent());
                break;
            case R.id.tv_obtain://怎么获得积分
                URIResolve.resolve(this, HTTP.URL_HOW_GET_SCORE+ HTTP.formatJSONData(new Request()));
                break;
            case R.id.tv_rebate://返利提现
                URIResolve.resolve(this,HTTP.URL_REBATE_CAN_CASH+ HTTP.formatJSONData(new Request()));
                break;
/*            case R.id.tv_how_to_send:
                URIResolve.resolve(this,"http://www.baidu.com");
                break;*/
            case R.id.btn_come:
                AppUtils.toActivity(this,HomeActivity.class);
                break;
            case R.id.back:
                this.finish();
                break;

        }
    }
    /**
     * 初始化客户端配置
     */
    public void loadClientConfig(){

        RXUtils.request(this, new Request(), "getClientConfig", new SupportSubscriber<Response<ClientConfig>>() {
            @Override
            public void onNext(Response<ClientConfig> clientConfig) {
                ContextParameter.setClientConfig(clientConfig.getData());

                checkVersionUpdate();
            }
        });

    }
    /**
     * 检查版本更新
     */
    public void checkVersionUpdate() {

        int currentVersion = AppUtils.getVersionCode(this);
        int newVersion = ContextParameter.getClientConfig().getNewVersion();

        if (getSupportApplication().alertVersionUpdate) {
            //判断时间间隔
            long currentTime = System.currentTimeMillis();
            long intevalTime = currentTime - getSupportApplication().alertVersionUpdateTime;

            if (intevalTime < getSupportApplication().MAX_ALERT_VERSION_UPDATE_TIME) {
                return;
            }

        }
    }
}
