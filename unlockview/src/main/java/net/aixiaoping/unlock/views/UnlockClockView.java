package net.aixiaoping.unlock.views;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.weslide.lovesmallscreen.utils.DateUtils;

import net.aixiaoping.unlock.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/5/16.
 * 锁屏界面显示的时间控件
 */
public class UnlockClockView extends FrameLayout {

    Context mContext;
    TextView tvWeek;
    /**
     * YYYY年MM月HH日
     */
    TextView tvYTD;

    TextView tvTime;


    public UnlockClockView(Context context) {
        super(context);

        mContext = context;
        initView();
    }

    public UnlockClockView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        initView();
    }

    public UnlockClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        initView();
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            mHandler.sendEmptyMessageDelayed(1, 1000);
            updateDate();

            return false;
        }
    });

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_unlock_clock, this, true);

        tvWeek = (TextView) findViewById(R.id.tv_unlock_clock_week);
        tvYTD = (TextView) findViewById(R.id.tv_unlock_clock_YTD);
        tvTime = (TextView) findViewById(R.id.tv_unlock_clock_time);

        updateDate();
        onStart();

    }

    /**
     * 更新时间
     */
    private void updateDate() {
        Date date = new Date();

        String week = DateUtils.getWeekOfDate(date);
        String ytd = DateUtils.formatDate(date, "yyyy年MM月dd日");
        String time = DateUtils.formatDate(date, "HH:mm");

        tvWeek.setText(week);
        tvTime.setText(time);
        tvYTD.setText(ytd);
    }

    /**
     * 结束正在进行的任务
     * 以免造成内存泄露
     */
    public void onDestroy(){
        mHandler.removeMessages(1);
    }

    public void onStart(){
        mHandler.removeMessages(1);
        mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
