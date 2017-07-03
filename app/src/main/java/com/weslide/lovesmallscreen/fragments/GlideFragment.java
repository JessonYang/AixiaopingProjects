package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.utils.AppUtils;

/**
 * Created by xu on 2016/8/1.
 * 引导页显示的Fragment
 */
public class GlideFragment extends BaseFragment {

    int imageRes;
    boolean click;

    public static GlideFragment newInstance(int imageRes) {
        GlideFragment glideFragment = new GlideFragment();
        glideFragment.imageRes = imageRes;

        return glideFragment;
    }

    public static GlideFragment newInstance(int imageRes, boolean click) {
        GlideFragment glideFragment = new GlideFragment();
        glideFragment.imageRes = imageRes;
        glideFragment.click = click;

        return glideFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(imageRes);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);

        if(click){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isShow",true);
                    AppUtils.toActivity(getActivity(), LoginOptionActivity.class,bundle);
                    getActivity().finish();
                }
            });
        }


        return imageView;
    }
}
