package com.weslide.lovesmallscreen.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

import net.aixiaoping.library.R;

import java.util.ArrayList;


/**
 * Created by xu on 2016/8/10.
 * 图片滑动展示
 */
public class ImageShowActivity extends BaseActivity {

    public static final String KEY_IMAGE_LIST_URI = "KEY_IMAGE_LIST_URI";

    public static final String KEY_IMAGE_LIST_RES = "KEY_IMAGE_RES";

    ArrayList<String> imageList;
    ConvenientBanner banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);

        banner = (ConvenientBanner) findViewById(R.id.banner);

        loadBundle();

        initView();
    }

    private void initView() {
        banner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, imageList)//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
    }

    private void loadBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageList = (ArrayList<String>) bundle.getSerializable(KEY_IMAGE_LIST_URI);
        }
    }

    class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(layoutParams);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(ImageShowActivity.this).load(data).into(imageView);
        }
    }
}
