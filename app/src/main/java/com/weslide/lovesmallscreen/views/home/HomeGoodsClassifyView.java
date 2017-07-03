package com.weslide.lovesmallscreen.views.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.weslide.lovesmallscreen.models.ImageText;

import net.aixiaoping.library.R;

import java.util.List;

/**
 * Created by xu on 2016/6/3.
 * 商品分类
 */
public class HomeGoodsClassifyView extends FrameLayout {

    GridView mGrideView;
    List<ImageText> mImageTexts;

    public HomeGoodsClassifyView(Context context) {
        super(context);
        initView(context);
    }

    public HomeGoodsClassifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HomeGoodsClassifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.home_view_goods_classify, this, true);
        mGrideView = (GridView) findViewById(R.id.gv_gride);
    }

    public void setImageTexts(List<ImageText> imageTexts){
        mImageTexts = imageTexts;
        mGrideView.setAdapter(new HomeGoodsClassifyViewAdapter());
    }

    class HomeGoodsClassifyViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return LayoutInflater.from(getContext()).inflate(R.layout.home_view_goods_classify_item, parent, false);
        }
    }
}
