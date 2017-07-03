package com.weslide.lovesmallscreen.view_yy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.CaroModel;
import com.weslide.lovesmallscreen.model_yy.javabean.CateModel;
import com.weslide.lovesmallscreen.model_yy.javabean.GoodsModel;
import com.weslide.lovesmallscreen.model_yy.javabean.SaveMoneyGoodModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.view_yy.adapter.OriginalTaokeLvAdapter;
import com.weslide.lovesmallscreen.view_yy.customview.GlideImageLoader;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/6/13.
 */
public class SaveMoneySearchFragment extends BaseFragment implements View.OnClickListener {
    private View fgView;
    private ImageView backIv;
    private ImageView shareIv;
    private EditText search_edt;
    private TabLayout tab;
    private ViewPager vp;
    private ArrayList<SaveMoneyHomeVpBaseFg> fgList;
    private List<CateModel> tabList;
    public static LoadingDialog loadingDialog;
    private TextView nineToNine_title;
    private String toolbarType;
    private RelativeLayout save_money_toolbar;
    private RelativeLayout nineToNine_toolbar;
    private String searchValue;
    private String cid;
    private PullToRefreshListView lv;
    private OriginalTaokeLvAdapter lvAdapter;
    private List<SaveMoneyGoodModel> lvList = new ArrayList<>();
    private int type;
    private int page = 1;
    private List<String> bannerList = new ArrayList<>();
    private String search;
    private String title;
    private LinearLayout reload_ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_save_money_search, container, false);
        initView();
        initData();
        return fgView;
    }

    private void initView() {
        backIv = ((ImageView) fgView.findViewById(R.id.back_iv2));
        nineToNine_title = ((TextView) fgView.findViewById(R.id.nineToNine_title));
        reload_ll = ((LinearLayout) fgView.findViewById(R.id.reload_ll));
        lv = ((PullToRefreshListView) fgView.findViewById(R.id.pullv));
    }

    private void initData() {
        backIv.setOnClickListener(this);
        search = getActivity().getIntent().getExtras().getString("search");
        cid = getActivity().getIntent().getExtras().getString("cid");
        title = getActivity().getIntent().getExtras().getString("title");
        loadingDialog = new LoadingDialog(getActivity());
        type = 0;
        lvAdapter = new OriginalTaokeLvAdapter(getSupportActivity(), lvList);
        lv.setAdapter(lvAdapter);
        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                type = 1;
                loadData();
            }
        });
        loadData();
    }

    private void loadData() {
        LoadingDialog dialog = new LoadingDialog(getSupportActivity());
        dialog.show();
        Request<GoodsModel> request = new Request<>();
        GoodsModel model = new GoodsModel();
        model.setPage_num(String.valueOf(page));
        model.setKeyWords(search);
        model.setPid(HomePageFragment_New.pid);
        model.setCid("-1");
        String methodName;
        if (title != null && !title.equals("省钱购物") && !title.equals("九块九") && !title.equals("")) {
            methodName = "getBrandGoods";
        }else {
            methodName = "getSaveMoneyGoods";
        }
        request.setData(model);
        RXUtils.request(getActivity(), request, methodName, new SupportSubscriber<Response<GoodsModel>>() {

            @Override
            public void onNext(Response<GoodsModel> response) {
                if (type == 0) {
                    lvList.clear();
                }
                lvList.addAll(response.getData().getGoods());
                lvAdapter.notifyDataSetChanged();
                if (SaveMoneyHomeFragment.loadingDialog != null && SaveMoneyHomeFragment.loadingDialog.isShowing()) {
                    SaveMoneyHomeFragment.loadingDialog.dismiss();
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (type == 1) {
                    lv.onRefreshComplete();
                }
                List<CaroModel> caro = response.getData().getCaro();
                if (caro != null && caro.size() > 0 && type == 0) {
                    addHeaderView(caro);
                }
                if (lvList == null || lvList.size() == 0) {
                    reload_ll.setVisibility(View.VISIBLE);
                    lv.setVisibility(View.GONE);
                }else {
                    reload_ll.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void addHeaderView(List<CaroModel> caro) {
        bannerList.clear();
        for (CaroModel caroModel : caro) {
            bannerList.add(caroModel.getPic_url());
        }
        View bannerView = LayoutInflater.from(getSupportActivity()).inflate(R.layout.new_home_page_top_banner, null);
        Banner banner = (Banner) bannerView.findViewById(R.id.new_home_banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(bannerList);
        banner.setDelayTime(3000);
        banner.startAutoPlay();
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                URIResolve.resolve(getActivity(), caro.get(position).getUri());
            }
        });
        lv.getRefreshableView().addHeaderView(bannerView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv2:
                getActivity().finish();
                break;
        }
    }
}
