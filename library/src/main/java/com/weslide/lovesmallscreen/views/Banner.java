package com.weslide.lovesmallscreen.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.utils.StringUtils;

import net.aixiaoping.library.R;

import java.util.List;

/**
 * Created by xu on 2016/6/3.
 * 轮播及横幅
 */
public class Banner extends FrameLayout {

    private ConvenientBanner convenientBanner;
    List<ImageText> mImageTexts;
    private OnBannerClickListener onBannerClickListener;

    public Banner(Context context) {
        super(context);
        initView(context);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setImageTexts(List<ImageText> imageTexts) {
        mImageTexts = imageTexts;

        show();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_banner, this, true);
        setConvenientBanner((ConvenientBanner) findViewById(R.id.convenientBanner));

    }

    private void show() {
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        getConvenientBanner().setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, mImageTexts);
        convenientBanner.setCanLoop(true);
//        convenientBanner.startTurning(8000);
        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
//        convenientBanner.setManualPageable(false);//设置不能手动影响
    }

    public OnBannerClickListener getOnBannerClickListener() {
        return onBannerClickListener;
    }

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    public ConvenientBanner getConvenientBanner() {
        return convenientBanner;
    }

    public void setConvenientBanner(ConvenientBanner convenientBanner) {
        this.convenientBanner = convenientBanner;
    }

    public class LocalImageHolderView implements Holder<ImageText> {

        BannerImageVideoView view;

        @Override
        public View createView(Context context) {
            view = new BannerImageVideoView(getContext());
            return view;
        }

        @Override
        public void UpdateUI(final Context context, final int position, final ImageText data) {

            view.bindView(data);

            if (onBannerClickListener == null) {
                if (!StringUtils.isBlank(data.getUri())) {
                    //URI解析
                    view.setOnImageViewClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            URIResolve.resolve(context, data.getUri());
                        }
                    });
                }

            } else {

                view.setOnImageViewClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBannerClickListener.onClick(data, position);
                    }
                });

            }


        }
    }

    /**
     * banner的点击事件
     */
    public interface OnBannerClickListener {
        void onClick(ImageText data, int position);
    }


}
