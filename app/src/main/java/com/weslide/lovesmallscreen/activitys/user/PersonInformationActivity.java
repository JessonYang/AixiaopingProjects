package com.weslide.lovesmallscreen.activitys.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.HomeActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.user.PersonalInformationFragment;

/**
 * Created by Dong on 2016/6/29.
 * 个人资料
 */
public class PersonInformationActivity extends BaseActivity {

    PersonalInformationFragment fragment = new PersonalInformationFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
       getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
