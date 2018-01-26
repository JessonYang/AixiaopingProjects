package com.weslide.lovesmallscreen.view_yy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.CaroModel;
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
public class SaveMoneyHomeVpBaseFg extends BaseFragment {
    private View fgView;
    private PullToRefreshListView lv;
    private OriginalTaokeLvAdapter lvAdapter;
    private List<SaveMoneyGoodModel> lvList = new ArrayList<>();
    private String title;
    private String cid;
    private int page = 1;
    private int type = 0;
    private String searchValue;
    private List<String> bannerList = new ArrayList<>();
//    private Button reload_btn;
    private LinearLayout reload_ll;
//    private FragmentActivity activity;


    public static SaveMoneyHomeVpBaseFg getInstance(Bundle bundle) {
        SaveMoneyHomeVpBaseFg saveMoneyHomeVpBaseFg = new SaveMoneyHomeVpBaseFg();
        saveMoneyHomeVpBaseFg.setArguments(bundle);
        return saveMoneyHomeVpBaseFg;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        title = getArguments().getString("title", "");
        cid = getArguments().getString("cid");
        searchValue = getArguments().getString("searchValue");
//        activity = getActivity();
        fgView = inflater.inflate(R.layout.fragment_save_money_home_vp_base, container, false);
        initView();
        initData();
        return fgView;
    }

    private void initData() {
        /*reload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 0;
                loadData();
            }
        });*/
        type = 0;
        lvAdapter = new OriginalTaokeLvAdapter(getActivity(), lvList);
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
        if (type == 1) {
            dialog.show();
        }
        Request<GoodsModel> request = new Request<>();
        GoodsModel model = new GoodsModel();
        model.setPage_num(page+"");
        model.setPid(HomeMainFragment.pid);
        String methodName;
        if (!title.equals("省钱购物") && !title.equals("九块九") && !title.equals("")) {
            methodName = "getBrandGoods";
            model.setKeyWords(title);
        } else {
            methodName = "getSaveMoneyGoods";
            model.setKeyWords(searchValue);
            model.setTitle(title);
            model.setCid(cid);
        }
        request.setData(model);
        RXUtils.request(getActivity(), request, methodName, new SupportSubscriber<Response<GoodsModel>>() {

            @Override
            public void onNext(Response<GoodsModel> response) {
                if (methodName.equals("getSaveMoneyGoods")) {
                    SaveMoneyHomeFragment.shareContent = response.getData().getShareContent();
                    SaveMoneyHomeFragment.shareTitle = response.getData().getShareTitle();
                    SaveMoneyHomeFragment.shareTargetUrl = response.getData().getShareTargetUrl();
                    SaveMoneyHomeFragment.shareIconUrl = response.getData().getShareIconUrl();
                }
                if (type == 0) {
                    lvList.clear();
                }
                lvList.addAll(response.getData().getGoods());
                lvAdapter.notifyDataSetChanged();
                if (lvList == null || lvList.size() == 0) {
                    lv.setVisibility(View.GONE);
                    reload_ll.setVisibility(View.VISIBLE);
                }else {
                    lv.setVisibility(View.VISIBLE);
                    reload_ll.setVisibility(View.GONE);
                }
                if (SaveMoneyHomeFragment.loadingDialog != null && SaveMoneyHomeFragment.loadingDialog.isShowing()) {
                    SaveMoneyHomeFragment.loadingDialog.dismiss();
                }
                if (SaveMoneyHomeFragment2.loadingDialog != null && SaveMoneyHomeFragment2.loadingDialog.isShowing()) {
                    SaveMoneyHomeFragment2.loadingDialog.dismiss();
                }
                if (type == 1) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    lv.onRefreshComplete();
                }
                List<CaroModel> caro = response.getData().getCaro();
                if (caro != null && caro.size() > 0 && type == 0) {
                    addHeaderView(caro);
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

    private void initView() {
        lv = ((PullToRefreshListView) fgView.findViewById(R.id.pullv));
//        reload_btn = ((Button) fgView.findViewById(R.id.btn_empty_reload));
        reload_ll = ((LinearLayout) fgView.findViewById(R.id.reload_ll));
    }
}
