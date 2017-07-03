package com.weslide.lovesmallscreen.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.ChangePasswordFragment;
import com.weslide.lovesmallscreen.fragments.ForgetMyPasswordFragment;
import com.weslide.lovesmallscreen.fragments.FreeRegisterFragment;
import com.weslide.lovesmallscreen.fragments.LoginFragment;
import com.weslide.lovesmallscreen.fragments.LoginLeadFragment;
import com.weslide.lovesmallscreen.fragments.RegisterFragment;

/**
 * Created by xu on 2016/6/1.
 * 包括
 * 登录
 * 注册
 * 第三方登录
 * 修改密码
 */
public class LoginOptionActivity extends BaseActivity {

    /** 进入界面时默认运行的Fragment */
    public static final String KEY_LAUNCHER_FRAGMENT = "launcher_fragment";
    /** 账号在其他地方登录 */
    public static final String KEY_OTHER_LOGIN = "other_login";

    private boolean isShow = false;

    String launcherFragment = FreeRegisterFragment.class.getName();
    LoginLeadFragment loginLeadFragment = new LoginLeadFragment();
    LoginFragment mLoginFragment = new LoginFragment();
    FreeRegisterFragment mFreeRegisterFragment = new FreeRegisterFragment();
    RegisterFragment mRegisterFragment = new RegisterFragment();
    ForgetMyPasswordFragment mRetrieveFragment = new ForgetMyPasswordFragment();
    ChangePasswordFragment mChangePasswordFragment = new ChangePasswordFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_option);

        init();
    }

    private void init(){

        if(getIntent().getExtras() != null){
            launcherFragment = getIntent().getExtras().getString(KEY_LAUNCHER_FRAGMENT, launcherFragment);
        }

        if(launcherFragment.equals(LoginFragment.class.getName())){
            login(false);
        } if(launcherFragment.equals(FreeRegisterFragment.class.getName())){
            freeRegisterFragment();
        } if(launcherFragment.equals(RegisterFragment.class.getName())) {
            register(false);
        }

        validateOtherLogin();
        unClockShow();
    }

    /**
     * 如果账号在其他地方登录则弹出提示
     */
    public void validateOtherLogin(){

        if(getIntent().getExtras() != null){
            boolean other = getIntent().getExtras().getBoolean(KEY_OTHER_LOGIN, false);
            if(other){
                boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
                SimpleDialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };


                builder.message(getResources().getString(net.aixiaoping.library.R.string.other_login)).title("下线通知")
                        .positiveAction("确定");

                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);
            }
        }
    }

    private void unClockShow(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isShow = bundle.getBoolean("isShow", false);
        }
        if(isShow == true){
            boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
            SimpleDialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
                @Override
                public void onPositiveActionClicked(DialogFragment fragment) {
                    super.onPositiveActionClicked(fragment);
                    ContextParameter.getLocalConfig().setOpenUnlock(true);
                    ContextParameter.setLocalConfig(ContextParameter.getLocalConfig());

                }

                @Override
                public void onNegativeActionClicked(DialogFragment fragment) {
                    super.onNegativeActionClicked(fragment);
                }
            };


            builder.message("是否打开锁屏,您还可以到个人中心的锁屏开关对锁屏进行设置哦~~")
                    .positiveAction("打开").negativeAction("关闭");

            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(getSupportFragmentManager(), null);
        }
    }

    /** 跳转至登录 */
    public void login(boolean addStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mLoginFragment);
        if(addStack){
            transaction.addToBackStack("mLoginFragment");
        }

        transaction.commit();
    }
    /** 跳转至忘记密码 */
    public void forgetPassWord(boolean addStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mRetrieveFragment);
        if(addStack){
            transaction.addToBackStack("mRetrieveFragment");
        }

        transaction.commit();
    }

    /** 跳转至修改密码密码 */
    public void changePassWord(boolean addStack,String phone,String captcha){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mChangePasswordFragment);
        if(addStack){
            transaction.addToBackStack("mChangePasswordFragment");
        }
        Bundle bundle = new Bundle();
        bundle.putString("phone",phone);
        bundle.putString("captcha",captcha);
        mChangePasswordFragment.setArguments(bundle);
        transaction.commit();
    }
    /** 跳转至注册引导界面 */
    public void freeRegisterFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, /*mFreeRegisterFragment*/loginLeadFragment);
        transaction.commit();
    }

    public void register(boolean addStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mRegisterFragment);
        if(addStack){
            transaction.addToBackStack("mRegisterFragment");
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        int num = getFragmentManager().getBackStackEntryCount();
        if(num == 0){
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
            return;
        }
//        Intent intent = new Intent(this, HomeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        mFreeRegisterFragment.onActivityResult(requestCode, resultCode, data);

        mLoginFragment.onActivityResult(requestCode, resultCode, data);

        mRegisterFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
