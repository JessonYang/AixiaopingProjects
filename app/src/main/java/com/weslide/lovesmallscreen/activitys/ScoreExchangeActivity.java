package com.weslide.lovesmallscreen.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.GoodsClassifiActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.fragments.ScoreExchangeListFragment;
import com.weslide.lovesmallscreen.models.Concentration;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.ScoreExchangeMall;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.SimpleAdpater;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/18.
 */
public class ScoreExchangeActivity extends BaseActivity {
    @BindView(R.id.gv_gride)
    public GridView gvGride;
    @BindView(R.id.stl_score_exchange_tab)
    SlidingTabLayout mTab;
    @BindView(R.id.vp_main_mall_search_shop_list)
    ViewPager mViewpager;
    List<BaseFragment> mFragments = new ArrayList<>();
    List<String> mTitles = new ArrayList<>();
    @BindView(R.id.tool_bar)
    Toolbar toolBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_exchange);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScoreExchangeActivity.this.finish();
            }
        });
        getScoreExchangeMall();
    }

    /**
     * 获取积分兑换商品选项卡及图片
     */
    private void getScoreExchangeMall() {
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        Request request = new Request();
        RXUtils.request(this, request, "scoreExchangeMall", new SupportSubscriber<Response<ScoreExchangeMall>>() {

            @Override
            public void onNext(Response<ScoreExchangeMall> response) {
                addFragment(response.getData().getScoreGoodsClassifys(),response.getData().getConcentration());
                setGrideviewAdapter(response.getData().getGoodsClassifys());
                loadingDialog.dismiss();
            }
        });
    }

    /**
     * 添加fragment
     */
    private void addFragment(List<ImageText> scoreGoodsClassify,Concentration concentration) {
        for(int i = 0 ;i < scoreGoodsClassify.size();i++){
            if(i==0){
                mFragments.add(ScoreExchangeListFragment.newInstance(Integer.parseInt(scoreGoodsClassify.get(i).getTypeId()),concentration));
            } else {
                mFragments.add(ScoreExchangeListFragment.newInstance(Integer.parseInt(scoreGoodsClassify.get(i).getTypeId())));
            }

            mTitles.add(scoreGoodsClassify.get(i).getName());
        }
        mViewpager.setAdapter(new ScoreExchangeFragmentPageAdapter(this.getSupportFragmentManager()));
        mTab.setViewPager(mViewpager);


    }

    class ScoreExchangeFragmentPageAdapter extends FragmentPagerAdapter {

        public ScoreExchangeFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }
    }

       private void setGrideviewAdapter(List<ImageText> goodsClassify){
         gvGride.setAdapter(new SimpleAdpater() {
            @Override
            public int getCount() {
                return goodsClassify.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView != null) {
                    return convertView;
                }
                View view = LayoutInflater.from(ScoreExchangeActivity.this).inflate(R.layout.home_view_goods_classify_item, parent, false);
                view.setOnClickListener(v -> {
                    //跳转至商品列表
                    Intent intent = new Intent(ScoreExchangeActivity.this, GoodsClassifiActivity.class);
                    GetGoodsListBean request = new GetGoodsListBean();
                    request.setTypeId(goodsClassify.get(position).getTypeId());
                    request.setMallTyle(Constants.MALL_SOCRE);
                    intent.putExtra(GoodsClassifiActivity.KEY_REQUEST, request);
                    intent.putExtra(GoodsClassifiActivity.KEY_TITLE, goodsClassify.get(position).getName());
                    ScoreExchangeActivity.this.startActivity(intent);

                });
                ((TextView) view.findViewById(R.id.tv_name)).setText(goodsClassify.get(position).getName());
                Glide.with(ScoreExchangeActivity.this).load(goodsClassify.get(position).getImage()).into((ImageView) view.findViewById(R.id.iv_icon));
                return view;
            }
        });
    }
}
