package com.weslide.lovesmallscreen.view_yy.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.SystemMsgDtModel;
import com.weslide.lovesmallscreen.models.SystemMsgDtObModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ScreenUtils;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ActionDtActivity extends AppCompatActivity {

    private CustomToolbar toolBar;
    private ImageView pic;
    private TextView desc, title, time;
    private LoadingDialog loadingDialog;
    private String msgId, typeId;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_dt);
        initView();
        initData();
    }

    private void initData() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        msgId = getIntent().getExtras().getString("msgId");
        typeId = getIntent().getExtras().getString("typeId");
        toolBar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });

        getActionDtData(msgId, typeId);
    }

    private void initView() {
        toolBar = ((CustomToolbar) findViewById(R.id.my_toolbar));
        pic = ((ImageView) findViewById(R.id.pic_iv));
        desc = ((TextView) findViewById(R.id.desc_tv));
        title = ((TextView) findViewById(R.id.title_tv));
        time = ((TextView) findViewById(R.id.time_tv));

    }

    private void getActionDtData(String msgId, String typeId) {
        Request<SystemMsgDtModel> request = new Request<>();
        SystemMsgDtModel model = new SystemMsgDtModel();
        model.setMsgId(msgId);
        model.setTypeId(typeId);
        model.setUserId(ContextParameter.getUserInfo().getUserId());
        request.setData(model);
        RXUtils.request(this, request, "getMsgDetailSystem", new SupportSubscriber<Response<SystemMsgDtModel>>() {
            @Override
            public void onNext(Response<SystemMsgDtModel> systemMsgDtModelResponse) {
                SystemMsgDtObModel systemMsgDtObModel = systemMsgDtModelResponse.getData().getSystemMsgDtObModel();
                toolBar.setTextViewTitle(systemMsgDtObModel.getNav_title());
                title.setText(systemMsgDtObModel.getTitle());
                time.setText(systemMsgDtObModel.getTime());
                StringBuilder stringBuilder = new StringBuilder();
                List<String> list = systemMsgDtObModel.getContent();
                for (String s : list) {
                    stringBuilder.append(s + "\n");
                }
                desc.setText(stringBuilder.toString());
                String picture = systemMsgDtObModel.getPicture();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (picture != null && picture.length() > 0) {
                            bitmap = returnBitMap(picture);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int bHeight = bitmap.getHeight();
                                    int bWidth = bitmap.getWidth();
                                    int screenWidth = ScreenUtils.getScreenWidth(ActionDtActivity.this);
                                    int height = screenWidth * bHeight / bWidth;
                                    ViewGroup.LayoutParams params = pic.getLayoutParams();
                                    params.height = height;
                                    pic.setLayoutParams(params);
                                    Glide.with(ActionDtActivity.this).load(picture).into(pic);
                                    loadingDialog.dismiss();
                                }
                            });
                        }else loadingDialog.dismiss();
                    }
                }).start();
                /*int bHeight = bitmap.getHeight();
                int bWidth = bitmap.getWidth();
                int screenWidth = ScreenUtils.getScreenWidth(ActionDtActivity.this);
                int height = screenWidth * bHeight / bWidth;
                ViewGroup.LayoutParams params = pic.getLayoutParams();
                params.height = height;
                pic.setLayoutParams(params);
                Glide.with(ActionDtActivity.this).load(picture).into(pic);
                loadingDialog.dismiss();*/
            }
        });
    }

    public Bitmap returnBitMap(final String url) {

        Bitmap bitmap = null;
        URL imageurl = null;
        try {
            imageurl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = null;
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
