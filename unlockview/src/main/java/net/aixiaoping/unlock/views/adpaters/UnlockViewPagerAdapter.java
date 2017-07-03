package net.aixiaoping.unlock.views.adpaters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.models.AdvertImg;

import java.util.List;

/**
 * Created by xu on 2016/5/16.
 * 滑屏广告中显示的viewPager
 */
public class UnlockViewPagerAdapter extends PagerAdapter {

    Context mContext;
    List<AdvertImg> mAdverts;

    public UnlockViewPagerAdapter(Context context, List<AdvertImg> adverts) {
        mContext = context;
        mAdverts = adverts;
    }

    @Override
    public int getCount() {
        //保证无数次循环
        return mAdverts.size() <= 1 ? mAdverts.size()
                : Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        position = position % mAdverts.size();
        AdvertImg advertImg = mAdverts.get(position);

        ImageView imageView = new ImageView(mContext);
        // 创建ImageView对象
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        Glide.with(mContext).load(advertImg.getImage()).dontAnimate().into(imageView);

        container.addView(imageView);
        return imageView;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
