package com.weslide.lovesmallscreen.exchange.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.bean.MyChangeListResModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyExchangeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.my_reply_count)
    TextView myReplyCount;
    @BindView(R.id.my_start_count)
    TextView myStartCount;
    @BindView(R.id.invite_me_count)
    TextView inviteMeCount;
    @BindView(R.id.my_deal_count)
    TextView myDealCount;
    @BindView(R.id.download_seller_qr)
    ImageView downloadSellerQr;
    private String downSellerUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exchange);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        Bitmap qrImage = QRCodeUtil.createQRImage(downSellerUrl, DensityUtils.dp2px(this, 90), DensityUtils.dp2px(this, 90));
        downloadSellerQr.setImageBitmap(qrImage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNewsCount();
    }

    private void checkNewsCount() {
        RXUtils.request(this, new Request(), "myChangeList", new SupportSubscriber<Response<MyChangeListResModel>>() {
            @Override
            public void onNext(Response<MyChangeListResModel> response) {
                if (response.getStatus() == 1) {
                    setCount(response.getData());
                }
            }
        });
    }

    private void setCount(MyChangeListResModel data) {
        int acceptCount = data.getAcceptCount();
        int inviteCount = data.getInviteCount();
        int noteCount = data.getNoteCount();
        int trading = data.getTrading();
        //我的帖子数量
        if (noteCount > 0) {
            myReplyCount.setVisibility(View.VISIBLE);
            myReplyCount.setText("(" + noteCount + ")");
        }
        //我发起的数量
        if (inviteCount > 0) {
            myStartCount.setVisibility(View.VISIBLE);
            myStartCount.setText("(" + inviteCount + ")");
        }
        //邀约我的数量
        if (acceptCount > 0) {
            inviteMeCount.setVisibility(View.VISIBLE);
            inviteMeCount.setText("(" + acceptCount + ")");
        }
        //我交易的数量
        if (trading > 0) {
            myDealCount.setVisibility(View.VISIBLE);
            myDealCount.setText("(" + trading + ")");
        }
    }

    @OnClick({R.id.my_reply_ll, R.id.my_start_ll, R.id.invite_me_ll, R.id.my_deal_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            //我的帖子
            case R.id.my_reply_ll:

                break;
            //我发起的
            case R.id.my_start_ll:

                break;
            //邀请我的
            case R.id.invite_me_ll:

                break;
            //我交易的
            case R.id.my_deal_ll:

                break;
        }
    }
}
