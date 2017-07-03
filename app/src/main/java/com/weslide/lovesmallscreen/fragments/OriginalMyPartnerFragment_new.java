package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.PartnerDtEventBusBean;
import com.weslide.lovesmallscreen.models.MyPartners;
import com.weslide.lovesmallscreen.models.PartnersOb;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.OriginalMyPartnerLvAdapter;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/3/23.
 */
public class OriginalMyPartnerFragment_new extends BaseFragment implements View.OnClickListener {

    private View originalMyPartnerFgView;
    private RelativeLayout customToolbar;
    private com.handmark.pulltorefresh.library.PullToRefreshListView lv;
    private ViewPager vp;
    private MyPartners data;
    private RadioGroup rg;
    private ArrayList<MyPartnerVpBaseFg> fragmentList;
    private OriginalMyPartnerLvAdapter mAdapter;
    private ImageView wuhehuoren_iv;
    private ProgressBar my_partner_progress;
    private TextView has_partner_num, search_tv;
    private TextView all_partner_num;
    private TextView has_partner_num_tv;
    private TextView left_partner_num_tv;
    private Button add_partner_btn;
    private List<PartnersOb> mList = new ArrayList<>();
    private OriginalMyPartnerLvAdapter mLvAdapter;
    private LoadingDialog loadingDialog;
    private int page = 1;
    private static int GETDATATYPE = 0;
    private ImageView back_iv;
    private TextView partner_num_tv;
    private EditText search_edt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        originalMyPartnerFgView = inflater.inflate(R.layout.fragment_original_my_partner_new2, container, false);
        initView();
        initData();
//        getLvData();
        return originalMyPartnerFgView;
    }

    private void getLvData(String search) {
        if (loadingDialog != null) {
            loadingDialog.show();
        }
        Request<MyPartners> request = new Request<>();
        MyPartners bean = new MyPartners();
        String userId = ContextParameter.getUserInfo().getUserId();
        bean.setUser_id(userId);
        bean.setSeach(search);
        bean.setPageIndex(page);
        request.setData(bean);
        RXUtils.request(getActivity(), request, "getPartnerList", new SupportSubscriber<Response<MyPartners>>() {

            @Override
            public void onNext(Response<MyPartners> response) {
                data = response.getData();
//                my_partner_progress.setMax(Integer.valueOf(data.getPartnerPlace()));
//                my_partner_progress.setProgress(Integer.valueOf(data.getPartnerCount()));
//                has_partner_num.setText(data.getPartnerCount());
//                all_partner_num.setText(data.getPartnerPlace());
//                has_partner_num_tv.setText(data.getPartnerCount());
                partner_num_tv.setText(data.getPartnerCount() + "人");
//                left_partner_num_tv.setText(data.getPartnerVacanc());
                if (GETDATATYPE == 0) {
                    mList.clear();
                }
                mList.addAll(data.getPartnersObs());
                mLvAdapter.notifyDataSetChanged();
                if (GETDATATYPE == 1) {
                    lv.onRefreshComplete();
                    if (data.getPartnersObs().size() == 0){
                        Toast.makeText(OriginalMyPartnerFragment_new.this.getActivity(), "亲!没有数据了哦", Toast.LENGTH_SHORT).show();
                    }
                }
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }

        });
    }

    private void initData() {
        search_tv.setOnClickListener(this);
        search_edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    String value = search_edt.getText().toString();
                    getLvData(value);
                }
                return false;
            }
        });
        GETDATATYPE = 0;
        loadingDialog = new LoadingDialog(getActivity());
        mLvAdapter = new OriginalMyPartnerLvAdapter(getActivity(), mList);
        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lv.setAdapter(mLvAdapter);
        add_partner_btn.setOnClickListener(this);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                GETDATATYPE = 1;
                getLvData("");
            }
        });
    }

    private void initView() {
        customToolbar = ((RelativeLayout) originalMyPartnerFgView.findViewById(R.id.original_my_partner_toolbar));
        lv = ((com.handmark.pulltorefresh.library.PullToRefreshListView) originalMyPartnerFgView.findViewById(R.id.original_my_partner_lv));
        search_edt = ((EditText) originalMyPartnerFgView.findViewById(R.id.search_edt));
        has_partner_num = ((TextView) originalMyPartnerFgView.findViewById(R.id.has_partner_num));
        search_tv = ((TextView) originalMyPartnerFgView.findViewById(R.id.search_tv));
        all_partner_num = ((TextView) originalMyPartnerFgView.findViewById(R.id.all_partner_num));
        has_partner_num_tv = ((TextView) originalMyPartnerFgView.findViewById(R.id.has_partner_num_tv));
        partner_num_tv = ((TextView) originalMyPartnerFgView.findViewById(R.id.partner_num_tv));
        left_partner_num_tv = ((TextView) originalMyPartnerFgView.findViewById(R.id.left_partner_num_tv));
        add_partner_btn = ((Button) originalMyPartnerFgView.findViewById(R.id.add_partner_btn));
        wuhehuoren_iv = ((ImageView) originalMyPartnerFgView.findViewById(R.id.wuhehuoren_iv));
        back_iv = ((ImageView) originalMyPartnerFgView.findViewById(R.id.back_iv));
        my_partner_progress = ((ProgressBar) originalMyPartnerFgView.findViewById(R.id.my_partner_progress));
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_partner_btn:

                break;
            case R.id.search_tv:
                String search = search_edt.getText().toString();
                getLvData(search);
                break;
        }
    }

    @Subscribe
    public void refreshLvData(PartnerDtEventBusBean busBean) {
        GETDATATYPE = 0;
        getLvData("");
    }

    @Override
    public void onResume() {
        super.onResume();
        GETDATATYPE = 0;
        page = 1;
        getLvData("");
//        Log.d("雨落无痕丶", "onResume: yy");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
