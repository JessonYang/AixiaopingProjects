package com.weslide.lovesmallscreen.view_yy.adapter.viewholder;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.PtListActivity;
import com.weslide.lovesmallscreen.models.NfcpModel;
import com.weslide.lovesmallscreen.models.bean.PtResModel;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.view_yy.customview.FoldTransformer;
import com.weslide.lovesmallscreen.view_yy.customview.RoundImgView;

import java.util.List;

/**
 * Created by YY on 2017/12/21.
 */
public class HomePtTopViewHoder extends RecyclerView.ViewHolder {

    private final ImageView pt_rll;
    private final ViewPager pt_banner;
    private final Context context;
    private final LinearLayout dotIndicater;
    private int count = 1;
    private int prePos = 0;

    public HomePtTopViewHoder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        pt_rll = ((ImageView) itemView.findViewById(R.id.pt_rll));
        pt_banner = ((ViewPager) itemView.findViewById(R.id.pt_banner));
        dotIndicater  = ((LinearLayout) itemView.findViewById(R.id.indicater_container));
    }

    public void oprateView(PtResModel model){
        Glide.with(context).load(model.getPtBgImageUrl()).into(pt_rll);
        pt_rll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toActivity(context, PtListActivity.class);
            }
        });
        initBanner(model.getPtImages());
    }

    private void initBanner(List<NfcpModel> list) {
        VpAdapter vpAdapter = new VpAdapter(list);
        pt_banner.setAdapter(vpAdapter);
        pt_banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotIndicater.getChildAt(prePos).setEnabled(false);
                dotIndicater.getChildAt(position).setEnabled(true);
                prePos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pt_banner.setPageTransformer(true,new FoldTransformer());
        if (count == 1) {//防止重复添加
            addIndicater(list);
        }
    }

    private void addIndicater(List<NfcpModel> list) {
        for (int i = 0; i < list.size(); i++) {
            View dotView = new View(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics()));
            params.leftMargin = 8;
            dotView.setLayoutParams(params);
            dotView.setBackgroundResource(R.drawable.dot_bg);
            if (i == 0) {
                dotView.setEnabled(true);
            }else dotView.setEnabled(false);
            dotIndicater.addView(dotView);
        }
        count++;
    }

    class VpAdapter extends PagerAdapter{

        private List<NfcpModel> list;

        public VpAdapter(List<NfcpModel> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.home_vp_item, container, false);
            RoundImgView vpIv = (RoundImgView) view.findViewById(R.id.vp_iv);
//            vpIv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(context).load(list.get(position).getImage()).into(vpIv);
            vpIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.toGoods(context,list.get(position).getGoodsId());
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
