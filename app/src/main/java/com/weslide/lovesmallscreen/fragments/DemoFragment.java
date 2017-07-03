package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.demo.IpInfo;
import com.weslide.lovesmallscreen.network.API;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.views.adapters.DemoListAdapter;

import com.weslide.lovesmallscreen.utils.KeyBoardUtils;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.T;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xu on 2016/5/5.
 * Demo中使用的Fragment
 * 所有的UI展示必须在Fragment中实现
 * 包括ActionBar
 * 这样的好处在于结构分离，解耦，便于维护
 * 但是不推荐Fragment嵌套Fragment，除非使用ViewPager等必要情况时
 */
public class DemoFragment extends BaseFragment {

    @BindView(R.id.et_input_ip)
    EditText etInputIp;
    @BindView(R.id.btn_show)
    Button btnShow;
    @BindView(R.id.tv_network_result)
    TextView tvIpResutl;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;


    @BindView(R.id.list)
    RecyclerView mList;
    RecyclerView.LayoutManager mRecyclerLayoutManager;
    DemoListAdapter mListAdapter;

    List<IpInfo> mInfoList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_main, container, false);

        ButterKnife.bind(this, view);

        //设置刷新时动画的颜色，可以设置4个
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        //设置下拉事件
        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            Observable.just(1)
                    .observeOn(Schedulers.computation())       //切换线程池中执行
                    .map(new Func1<Integer, List<IpInfo>>() {  //加载数据库中的数据
                        @Override
                        public List<IpInfo> call(Integer v) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            List<IpInfo> infos = ArchitectureAppliation.getDaoSession().getIpInfoDao().loadAll();
                            return infos == null ? new ArrayList<IpInfo>() : infos;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())  //切换主线程
                    .subscribe(new Action1<List<IpInfo>>() {    //提示数据更新，并设置下拉加载结束
                        @Override
                        public void call(List<IpInfo> ipInfos) {
                            mInfoList.clear();
                            mInfoList.addAll(ipInfos);
                            mListAdapter.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
        });

        mRecyclerLayoutManager = new LinearLayoutManager(getActivity());

        mList.setLayoutManager(mRecyclerLayoutManager);
        //初始化所有数据
        mInfoList.addAll(ArchitectureAppliation.getDaoSession().getIpInfoDao().loadAll());
        mListAdapter = new DemoListAdapter(getActivity(), mInfoList);
        mList.setAdapter(mListAdapter);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(null)
                        .filter((Object o) -> {
                            if(!NetworkUtils.isConnected(getActivity())){
                                T.showShort(getActivity(), "还是先把网络连一下吧");
                                return false;
                            }
                            return  true;
                        })  //有网络的情况下继续执行
                        .observeOn(Schedulers.computation())       //切换线程池中执行
                        .map(new Func1<Object, Response<IpInfo>>() {  //加载网络数据
                            @Override
                            public Response<IpInfo> call(Object o) {
                                Retrofit retrofit = HTTP.getRetrofit("http://ip.taobao.com");
                                API service = retrofit.create(API.class);

                                Call<Response<IpInfo>> ipao = service.getIpInfo(etInputIp.getText().toString());
                                try {
                                    return ipao.execute().body();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }
                        })
                        .filter((Response<IpInfo> ipInfoResponse) -> ipInfoResponse != null) //数据返回不为空时继续执行
                        .map(new Func1<Response<IpInfo>, Response<IpInfo>>() {
                            @Override
                            public Response<IpInfo> call(Response<IpInfo> ipInfoResponse) {
                                ArchitectureAppliation.getDaoSession().getIpInfoDao().insert(ipInfoResponse.getData());
                                return ipInfoResponse;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Response<IpInfo>>() {
                            @Override
                            public void call(Response<IpInfo> ipInfoResponse) {
                                KeyBoardUtils.closeKeybord(etInputIp, getActivity()); //隐藏软键盘
                                etInputIp.setText("");
                                tvIpResutl.setText(ipInfoResponse.getData().getIp());

                            }
                        });
            }
        });
        return view;
    }


}
