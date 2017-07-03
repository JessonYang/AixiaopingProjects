package com.weslide.lovesmallscreen.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.VideoText;

/**
 * Created by xu on 2016/8/2.
 * banner里的图片及视频组成
 */
public class BannerImageVideoView extends AFrameLayout {

    private ImageView imageView;
    /** 需求改变， 现在要改为WebView */
//    private JCVideoPlayerStandard jcVideoPlayerStandard;
    private AXPWebView axpWebView;

    public BannerImageVideoView(Context context) {
        super(context);
        initView();
    }

    public BannerImageVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BannerImageVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        imageView = new ImageView(getContext());
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewGroup.LayoutParams imageViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

//        jcVideoPlayerStandard = new JCVideoPlayerStandard(getContext());
//        jcVideoPlayerStandard.setVisibility(View.GONE);
//        ViewGroup.LayoutParams jcVideoPlayerStandardParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addView(imageView, imageViewParams);
//        addView(jcVideoPlayerStandard, jcVideoPlayerStandardParams);
    }

    public void bindView(ImageText imageText) {
        if (imageText instanceof VideoText) {

            if(axpWebView == null){
                ViewGroup.LayoutParams axpWebViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                axpWebView = new AXPWebView(getContext());
                axpWebView.setVisibility(GONE);
                addView(axpWebView, axpWebViewParams);
            }

            VideoText videoText = (VideoText) imageText;
            imageView.setVisibility(View.GONE);
            axpWebView.setVisibility(View.VISIBLE);

            axpWebView.getWebView().loadUrl(videoText.getVideo());

//            String videoName = StringUtils.isEmpty(videoText.getName()) ? "视频" : videoText.getName();
//
//            jcVideoPlayerStandard.setUp(videoText.getVideo()
//                    , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, videoName);
//
//            if(!StringUtils.isEmpty(videoText.getImage())){
//                Glide.with(getContext()).load(videoText.getImage()).into(jcVideoPlayerStandard.thumbImageView);
//            }



        } else {
            imageView.setVisibility(View.VISIBLE);
            if(axpWebView != null){
                axpWebView.setVisibility(GONE);
            }
//            jcVideoPlayerStandard.setVisibility(View.GONE);

            //加载图片
            Glide.with(getContext()).load(imageText.getImage()).into(imageView);


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(axpWebView!= null){
            axpWebView.getWebView().destroy();
        }
    }

    public void setOnImageViewClickListener(OnClickListener listener) {
        imageView.setOnClickListener(listener);
    }
}
