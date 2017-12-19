package com.weslide.lovesmallscreen.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

/**
 * 会话界面
 */
public class ConversionActivity extends AppCompatActivity {

    private CustomToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        toolbar = ((CustomToolbar) findViewById(R.id.toolbar));
        toolbar.setTextViewTitle(getIntent().getData().getQueryParameter("title"));
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
    }
}
