package com.weslide.lovesmallscreen.core;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.weslide.lovesmallscreen.ArchitectureAppliation;

/**
 * Created by xu on 2016/4/28.
 * 一个基础的Fragment
 */
public class BaseFragment extends Fragment {

    @Override
    public void startActivity(Intent intent) {
        getActivity().startActivity(intent);
    }

    public BaseActivity getSupportActivity(){
        return (BaseActivity) getActivity();
    }

    public ArchitectureAppliation getSupportApplication(){
        return  getSupportActivity().getSupportApplication();
    }
}
