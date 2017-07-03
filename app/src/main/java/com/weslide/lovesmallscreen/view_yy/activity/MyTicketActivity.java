package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.view_yy.fragment.MyTicketFragment;

/**
 * Created by YY on 2017/6/6.
 */
public class MyTicketActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        getSupportFragmentManager().beginTransaction().replace(R.id.replace_my_ticket_ll,new MyTicketFragment()).commit();
    }
}
