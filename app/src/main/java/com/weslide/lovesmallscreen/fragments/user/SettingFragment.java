package com.weslide.lovesmallscreen.fragments.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.rey.material.widget.Switch;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.DownloadImageService;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.activitys.user.FeedbackActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.eventbus_message.DownloadImageMessage;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.FileUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Dong on 2016/6/20.
 * 设置
 */
public class SettingFragment extends BaseFragment {

    public long M = 1024 * 1024;

    View mView;
    @BindView(R.id.iv_clean_memorry)
    TextView ivCleanMemorry;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.switchView)
    com.rey.material.widget.Switch switchView;
    @BindView(R.id.switchView2)
    com.rey.material.widget.Switch switchView2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_setting, container, false);

        EventBus.getDefault().register(this);

        ButterKnife.bind(this, mView);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        switchView.setChecked(ContextParameter.getLocalConfig().isOpenPush());
        switchView2.setChecked(ContextParameter.getLocalConfig().isOpenVoice());
        switchView.setOnCheckedChangeListener((view, checked) -> {
            if (checked) {
                T.showShort(getActivity(), "接收推送消息");
                ContextParameter.getLocalConfig().setOpenPush(true);
                ContextParameter.setLocalConfig(ContextParameter.getLocalConfig());
            } else {
                T.showShort(getActivity(), "关闭推送消息");
                ContextParameter.getLocalConfig().setOpenPush(false);
                ContextParameter.setLocalConfig(ContextParameter.getLocalConfig());
            }
        });
        switchView2.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                if (checked) {
                    T.showShort(getActivity(), "开启声音");
                    ContextParameter.getLocalConfig().setOpenVoice(true);
                    ContextParameter.setLocalConfig(ContextParameter.getLocalConfig());
                }else {
                    T.showShort(getActivity(), "关闭声音");
                    ContextParameter.getLocalConfig().setOpenVoice(false);
                    ContextParameter.setLocalConfig(ContextParameter.getLocalConfig());
                }
            }
        });
        File file = new File(getActivity().getCacheDir() + "/" + DiskCache.Factory.DEFAULT_DISK_CACHE_DIR);
        long fileSize = FileUtils.getDirSize(file);
        long showValue = fileSize >= M ? fileSize / 1024 /1024 : fileSize / 1024;
        String showDanwei = fileSize >= M ? "M" : "K";
        ivCleanMemorry.setText(showValue + showDanwei);

        return mView;
    }

    @Subscribe
    public void onEvent(DownloadImageMessage message) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            T.showShort(getActivity(), "刷新成功");
            loadingDialog.dismiss();
        }
    }

    LoadingDialog loadingDialog;

    @OnClick({R.id.ll_clean_memorry, R.id.ll_feedback, R.id.ll_about_aixiaoping, R.id.ll_refresh_backound})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_clean_memorry:

                Observable.just(null).observeOn(Schedulers.computation())
                        .map(new Func1<Object, Object>() {
                            @Override
                            public Object call(Object o) {
                                Glide.get(getActivity()).clearDiskCache();
                                return null;
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Object o) {
                                File file = new File(getActivity().getCacheDir() + "/" + DiskCache.Factory.DEFAULT_DISK_CACHE_DIR);
                                long fileSize = FileUtils.getDirSize(file);
                                long showValue = fileSize >= M ? fileSize / 1024 /1024 : fileSize / 1024;
                                String showDanwei = fileSize >= M ? "M" : "K";
                                ivCleanMemorry.setText(showValue + showDanwei);
                                T.showShort(getActivity(), "缓存清除成功");
                            }
                        });


                break;
            case R.id.ll_feedback://意见反馈
                AppUtils.toActivity(getActivity(), FeedbackActivity.class);
                break;
            case R.id.ll_about_aixiaoping://关于爱小屏
                URIResolve.resolve(getActivity(), HTTP.URL_ABOUT + HTTP.formatJSONData(new Request()));
                break;
            case R.id.ll_refresh_backound://刷新锁屏
                Intent intent = new Intent(getActivity(), DownloadImageService.class);
                intent.putExtra(DownloadImageService.KEY_FRESHEN, true);
                getActivity().startService(intent);

                loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.show();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
