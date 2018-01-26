package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.order.OrderActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.bean.TeamDataModel;
import com.weslide.lovesmallscreen.models.bean.TeamResModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.CustomDialogUtil;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;
import com.weslide.lovesmallscreen.views.customview.CircleImageView;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PutTogetherActivity extends AppCompatActivity {

    @BindView(R.id.put_together_toolbar)
    CustomToolbar putTogetherToolbar;
    @BindView(R.id.host_icon)
    CircleImageView hostIcon;
    @BindView(R.id.question_icon)
    ImageView questionIcon;
    @BindView(R.id.host_name)
    TextView hostName;
    @BindView(R.id.send_right_now)
    TextView sendRightNow;
    @BindView(R.id.lack_num_tv)
    TextView lackNumTv;
    @BindView(R.id.invate_frend_tv)
    TextView invateFrendTv;
    @BindView(R.id.left_time_tv)
    TextView leftTimeTv;
    private String teamOrderId;
    private String shareTitle;
    private String shareContent;
    private String shareIcon;
    private String shareTargetUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_together);
        ButterKnife.bind(this);
        initBundle();
        initData();
    }

    private void initData() {
        putTogetherToolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                toOrderActivity();
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        Request<TeamResModel> request = new Request<>();
        TeamResModel model = new TeamResModel();
        model.setTeamOrderId(teamOrderId);
        request.setData(model);
        RXUtils.request(this, request, "teamShare", new SupportSubscriber<Response<TeamDataModel>>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onNext(Response<TeamDataModel> teamResModelResponse) {
                setData(teamResModelResponse.getData());
            }

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(PutTogetherActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onResponseError(Response response) {
                CustomDialogUtil.showNoticDialog(PutTogetherActivity.this, "" + response.getMessage());
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }
        });
    }

    private void toOrderActivity() {
        Bundle bundle = new Bundle();
        bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_SHARE);
        AppUtils.toActivity(PutTogetherActivity.this,OrderActivity.class,bundle);
    }

    private void setData(TeamDataModel teamDataModel) {
        Glide.with(this).load(teamDataModel.getUserHead()).into(hostIcon);
        long total_second = teamDataModel.getSurplusTime()/ 1000;
        long total_minute = total_second / 60;
        long total_hour = total_minute / 60;
        long second = total_second % 60;
        long minute = total_minute % 60;
        long hour = total_hour % 24;
        long day = total_hour / 24;
        leftTimeTv.setText(getTime(day,hour,minute,second));
        lackNumTv.setText(teamDataModel.getSurplusNum());
        shareTitle = teamDataModel.getShareTitle();
        shareContent = teamDataModel.getShareContent();
        shareIcon = teamDataModel.getShareIcon();
        shareTargetUrl = teamDataModel.getShareTargetUrl();
        new CountDownTimer(teamDataModel.getSurplusTime(), 1000) {
            @Override
            public void onTick(long l) {
                long total_second = l/ 1000;
                long total_minute = total_second / 60;
                long total_hour = total_minute / 60;
                long second = total_second % 60;
                long minute = total_minute % 60;
                long hour = total_hour % 24;
                long day = total_hour / 24;
                leftTimeTv.setText(getTime(day,hour,minute,second));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void initBundle() {
        if (getIntent().getExtras() != null) {
            teamOrderId = getIntent().getExtras().getString("teamOrderId");
        }
    }

    private String getTime(long day,long hour,long minute,long second){
        return  "剩余:" + String.format("%02d",day)+"天"+String.format("%02d",hour)+"时"+String.format("%02d",minute)+"分"+String.format("%02d",second)+"秒";
    }

    @OnClick({R.id.icon_wechat, R.id.icon_qq, R.id.icon_pengyouquan, R.id.icon_qq_zone})
    public void onClick(View view) {
        switch (view.getId()) {
            //微信分享
            case R.id.icon_wechat:
                ShareUtils.shareToWexin(this,shareTitle,shareIcon,shareTargetUrl,shareContent);
                break;
            //qq分享
            case R.id.icon_qq:
                ShareUtils.shareToQQ(this,shareTitle,shareIcon,shareTargetUrl,shareContent);
                break;
            //朋友圈分享
            case R.id.icon_pengyouquan:
                ShareUtils.shareToWeXinFriendCircle(this,shareTitle,shareIcon,shareTargetUrl,shareContent);
                break;
            //qq空间分享
            case R.id.icon_qq_zone:
                ShareUtils.shareToQzone(this,shareTitle,shareIcon,shareTargetUrl,shareContent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        toOrderActivity();
        super.onBackPressed();
    }
}
