package com.weslide.lovesmallscreen.view_yy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.AchieveBean;
import com.weslide.lovesmallscreen.model_yy.javabean.CouponsBean;
import com.weslide.lovesmallscreen.model_yy.javabean.RequestBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AxpDiscountTicketActivity extends BaseActivity {

    @BindView(R.id.user_face_iv)
    ImageView user_face_iv;

    @BindView(R.id.get_ticket_iv)
    ImageView get_ticket_iv;

    @BindView(R.id.good_icon)
    ImageView good_icon;

    @BindView(R.id.consume_way_iv)
    ImageView consume_way_iv;

    @BindView(R.id.store_name_tv)
    TextView store_name_tv;

    @BindView(R.id.ticket_price_tv)
    TextView ticket_price_tv;

    @BindView(R.id.limite_time_tv)
    TextView limite_time_tv;

    @BindView(R.id.good_desc)
    TextView good_desc;

    @BindView(R.id.consume_way_tv)
    TextView consume_way_tv;

    @BindView(R.id.current_price_tv)
    TextView current_price_tv;

    @BindView(R.id.sole_num_tv)
    TextView sole_num_tv;

    @BindView(R.id.after_price_tv)
    TextView after_price_tv;

    @BindView(R.id.action_detail_tv)
    TextView action_detail_tv;

    @BindView(R.id.axp_ticket_toolbar)
    CustomToolbar toolbar;

    private String ticketId;
    private String shareId;
    private String url;
    private String netShareId;
    private String netTicketid;
    private String shareUrl;
    private String shareIcon;
    private String shareTitle;
    private String shareContent;
    private String goodsMall;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_axp_discount_ticket);
        ButterKnife.bind(this);
        initBundle();
        initData();
    }

    private void initBundle() {
        Intent intent = getIntent();//在这个Activity里，我们可以通过getIntent()，来获取外部跳转传过来的信息。
        Bundle extras = intent.getExtras();
        String data = intent.getDataString();//接收到网页传过来的数据：sharetest://data/http://www.huxiu.com/
        if (data != null) {
            Log.d("雨落无痕丶", "initBundle: yyyyyy");
            String[] split = data.split("com.test/");//以data/切割data字符串
            url = split[1]; //就得到：http://www.huxiu.com/(这就是我们需要网页传给我们的数据)
            String[] strings = url.split("&");
            ticketId = strings[0];
            shareId = strings[1];
        } else {
            ticketId = extras.getString("ticketId");
            shareId = extras.getString("shareId");
        }
    }

    private void initData() {
        askNetData();
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {
                ShareUtils.share(AxpDiscountTicketActivity.this, shareTitle, shareIcon, shareUrl, shareContent);
            }
        });
    }

    private void askNetData() {
        Request<RequestBean> request = new Request<>();
        if (ticketId != null && shareId != null) {
            RequestBean bean = new RequestBean();
            bean.setTicketId(ticketId);
            bean.setShareId(shareId);
            request.setData(bean);
        }
        RXUtils.request(this, request, "getCoupons", new SupportSubscriber<Response<CouponsBean>>() {

            @Override
            public void onNext(Response<CouponsBean> couponsBeanResponse) {
                CouponsBean data = couponsBeanResponse.getData();
                store_name_tv.setText(data.getSeller_name());
                limite_time_tv.setText(data.getStartTime() + "-" + data.getStopTime());
                good_desc.setText(data.getGoods_name());
                current_price_tv.setText(data.getPrice());
                after_price_tv.setText(data.getLastprice());
                ticket_price_tv.setText(data.getTicketprice());
                sole_num_tv.setText(data.getSalesVolume() + "笔成交");
                Glide.with(AxpDiscountTicketActivity.this).load(data.getLogo()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(user_face_iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(AxpDiscountTicketActivity.this.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        user_face_iv.setImageDrawable(circularBitmapDrawable);
                    }
                });
                status = data.getStatus();
                if (status.equals("1")) {
                    get_ticket_iv.setImageResource(R.drawable.ljlq);
                    get_ticket_iv.setEnabled(true);
                } else if (data.getStatus().equals("2")) {
                    get_ticket_iv.setImageResource(R.drawable.qysx);
                } else if (data.getStatus().equals("3")) {
                    get_ticket_iv.setImageResource(R.drawable.qylw);
                } else if (data.getStatus().equals("4")) {
                    get_ticket_iv.setImageResource(R.drawable.qylq);
                } else if (data.getStatus().equals("5")) {
                    get_ticket_iv.setImageResource(R.drawable.qysy);
                }
                Glide.with(AxpDiscountTicketActivity.this).load(data.getGoodsImg()).into(good_icon);
                if (data.getTransportation().equals("1")) {
                    consume_way_iv.setImageResource(R.drawable.icon_sm_bydj);
                    consume_way_tv.setText("包邮到家");
                } else if (data.getTransportation().equals("2")) {
                    consume_way_iv.setImageResource(R.drawable.icon_sm_ddxf);
                    consume_way_tv.setText("到店消费");
                }
                List<String> activitydetails = data.getActivitydetails();
                StringBuilder builder = new StringBuilder();
                if (activitydetails != null && activitydetails.size() > 0) {
                    for (int i = 0; i < activitydetails.size(); i++) {
                        if (i != activitydetails.size() - 1) {
                            builder.append(activitydetails.get(i) + "\n");
                        } else {
                            builder.append(activitydetails.get(i));
                        }
                    }
                    action_detail_tv.setText(builder.toString());
                }
                netTicketid = data.getTicketid();
                netShareId = data.getShareId();
                goodsMall = data.getGoodsMall();
                shareContent = data.getShareContent();
                shareTitle = data.getShareTitle();
                shareIcon = data.getShareIcon();
                shareUrl = data.getShareUrl();
            }
        });
    }

    @OnClick({R.id.get_ticket_iv, R.id.to_good_dt_rll, R.id.good_item_rll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_ticket_iv:
                if (ContextParameter.isLogin()) {
                    if (status.equals("1")){
                        Request<AchieveBean> request = new Request<>();
                        AchieveBean bean = new AchieveBean();
                        bean.setTicketId(netTicketid);
                        bean.setShareId(netShareId);
                        bean.setUserId(ContextParameter.getUserInfo().getUserId());
                        request.setData(bean);
                        RXUtils.request(this, request, "achieve", new SupportSubscriber<Response<AchieveBean>>() {
                            @Override
                            public void onNext(Response<AchieveBean> achieveBeanResponse) {
                                AchieveBean data = achieveBeanResponse.getData();
                                if (data != null) {
                                    Toast.makeText(AxpDiscountTicketActivity.this, data.getContent(), Toast.LENGTH_SHORT).show();
                                    if (data.getStatus().equals("1")) {
                                        askNetData();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                SystemClock.sleep(1200);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        AppUtils.toGoods(AxpDiscountTicketActivity.this, goodsMall);
                                                    }
                                                });
                                            }
                                        }).start();
                                    }
                                }
                            }
                        });
                    }else {
                        AppUtils.toActivity(this, MyTicketActivity.class);
                    }
                }else {
                    AppUtils.toActivity(this, LoginOptionActivity.class);
                }
                break;
            case R.id.to_good_dt_rll:
            case R.id.good_item_rll:
                AppUtils.toGoods(this, goodsMall);
                break;
        }
    }
}
