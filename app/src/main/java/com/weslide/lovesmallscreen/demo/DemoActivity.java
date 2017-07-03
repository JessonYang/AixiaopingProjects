package com.weslide.lovesmallscreen.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.DemoFragment;
import com.weslide.lovesmallscreen.utils.L;


/**
 * Created by xu on 2016/5/4.
 * <p>
 * 用于熟悉项目架构使用方法的参考代码
 * <p>
 * 其实rxjava的好处在于流程清晰
 * 平时开发android程序的时候因为要在主线程和子线程中切换试代码变得跳来跳去，代码变得可读性不高
 */
public class DemoActivity extends BaseActivity {

    DemoFragment demoFragment = new DemoFragment();
    //    FreeRegisterFragment demoFragment = new FreeRegisterFragment();
    //    LoginFragment demoFragment = new LoginFragment();
//   RegisterFragment demoFragment = new RegisterFragment();
    //  LoginFragment demoFragment = new LoginFragment();
    private String[] mTitles = {"首页", "首页", "首页", "首页", "消息"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);


        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.e("toolbar");
            }
        });

//        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
//        jcVideoPlayerStandard.setUp("http://player.youku.com/embed/XMTY4MjA5MjU4MA=="
//                , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "嫂子闭眼睛");
//        jcVideoPlayerStandard.thumbImageView.setThumbInCustomProject("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");

//        findViewById(R.id.btn_empty_reload).setOnImageViewClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                L.e("执行了点击事件");
//            }
//        });

//        setContentView(R.layout.fragment_order_list);
//        SegmentTabLayout tabLayout_1 = (SegmentTabLayout) findViewById(R.id.tl_1);
//        tabLayout_1.setTabData(mTitles);



    }


    @Override
    public void onBackPressed() {
        //预防全屏时，用户点击返回后直接退出了activity
//        if (JCVideoPlayer.backPress()) {
//            return;
//        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JCVideoPlayer.releaseAllVideos();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
