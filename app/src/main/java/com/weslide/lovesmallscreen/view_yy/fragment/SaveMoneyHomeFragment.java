package com.weslide.lovesmallscreen.view_yy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.GoodsSearchActivity_New;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.Cate;
import com.weslide.lovesmallscreen.model_yy.javabean.CateModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/6/13.
 */
public class SaveMoneyHomeFragment extends BaseFragment implements View.OnClickListener {
    private View fgView;
    private RelativeLayout backIv;
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
    private ImageView search_iv;
    private int position = 0;
    public static String shareIconUrl;
    public static String shareTargetUrl;
    public static String shareTitle;
    public static String shareContent;
    private ImageView backIv2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_save_money_home, container, false);
        initView();
        initData();
        return fgView;
    }

    private void initView() {
        backIv = ((RelativeLayout) fgView.findViewById(R.id.back_iv));
        backIv2 = ((ImageView) fgView.findViewById(R.id.back_iv2));
        nineToNine_title = ((TextView) fgView.findViewById(R.id.nineToNine_title));
        shareIv = ((ImageView) fgView.findViewById(R.id.share_iv));
        search_iv = ((ImageView) fgView.findViewById(R.id.search_iv));
        search_edt = ((EditText) fgView.findViewById(R.id.search_edt));
        tab = ((TabLayout) fgView.findViewById(R.id.tab));
        vp = ((ViewPager) fgView.findViewById(R.id.vp));
        nineToNine_toolbar = ((RelativeLayout) fgView.findViewById(R.id.nineToNine_toolbar));
        save_money_toolbar = ((RelativeLayout) fgView.findViewById(R.id.save_money_toolbar));
    }

    private void initData() {
        backIv.setOnClickListener(this);
        backIv2.setOnClickListener(this);
        shareIv.setOnClickListener(this);
        search_edt.setOnClickListener(this);
        search_iv.setOnClickListener(this);
        loadingDialog = new LoadingDialog(getActivity());
        toolbarType = getActivity().getIntent().getExtras().getString("toolbarType");
        searchValue = getActivity().getIntent().getExtras().getString("searchValue");
        cid = getActivity().getIntent().getExtras().getString("cid");
        if (toolbarType.equals("省钱购物")) {
            save_money_toolbar.setVisibility(View.VISIBLE);
            nineToNine_toolbar.setVisibility(View.GONE);
            toolbarType = "";
        } else {
            save_money_toolbar.setVisibility(View.VISIBLE);
            nineToNine_toolbar.setVisibility(View.GONE);
            nineToNine_title.setText(toolbarType);
        }

        getNetData();
    }

    private void getNetData() {
        if (loadingDialog != null) {
            loadingDialog.show();
        }
        Request<Cate> request = new Request<>();
        RXUtils.request(getActivity(), request, "getCate", new SupportSubscriber<Response<Cate>>() {

            @Override
            public void onNext(Response<Cate> response) {
                Log.d("雨落无痕丶", "onNext: ssssssssssssssssssssssss");
                tabList = response.getData().getCate();
                addFragmentList();
//                shareContent = response.getData().getShareContent();
//                shareIconUrl = response.getData().getShareIconUrl();
//                shareTargetUrl = response.getData().getShareTargetUrl();
//                shareTitle = response.getData().getShareTitle();
            }
        });
    }

    private void addFragmentList() {
        fgList = new ArrayList<>();
        for (int i = 0; i < tabList.size(); i++) {
            if (cid.equals(tabList.get(i).getCid())) {
//                Log.d("雨落无痕丶", "addFragmentList: " + cid);
                position = i;
            }
            Bundle bundle = new Bundle();
            bundle.putString("title", toolbarType);
            bundle.putString("cid", tabList.get(i).getCid());
            bundle.putString("searchValue", searchValue);
            fgList.add(SaveMoneyHomeVpBaseFg.getInstance(bundle));
        }

        vp.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgList.get(position);
            }

            @Override
            public int getCount() {
                return fgList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabList.get(position).getTitle();
            }
        });
        tab.setupWithViewPager(vp);
        tab.getTabAt(position).select();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv2:
            case R.id.back_iv:
//                Log.d("雨落无痕丶", "onClick: yyyyy");
                getActivity().finish();
                break;
            case R.id.share_iv:
                if (shareTitle != null && shareIconUrl != null && shareTargetUrl != null && shareContent != null) {
                    ShareUtils.share(getActivity(), shareTitle, shareIconUrl, shareTargetUrl, shareContent);
                }else {
                    Toast.makeText(SaveMoneyHomeFragment.this.getActivity(), "分享内容出错!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.search_iv:
            case R.id.search_edt:
                Bundle bundle = new Bundle();
                bundle.putString("title", toolbarType);
                AppUtils.toActivity(getActivity(), GoodsSearchActivity_New.class, bundle);
                break;
        }
    }
}
