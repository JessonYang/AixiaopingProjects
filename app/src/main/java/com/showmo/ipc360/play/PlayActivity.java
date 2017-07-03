package com.showmo.ipc360.play;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.administrator.showmosdkdemo.R;
import com.showmo.ipc360.beans.XmTimeRect;
import com.showmo.ipc360.util.spUtil;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.T;
import com.xmcamera.core.model.XmErrInfo;
import com.xmcamera.core.model.XmPermissonAction;
import com.xmcamera.core.model.XmRemoteFile;
import com.xmcamera.core.model.XmStreamMode;
import com.xmcamera.core.model.XmSysDataDef;
import com.xmcamera.core.sys.XmSystem;
import com.xmcamera.core.sysInterface.IXmPlaybackCameraCtrl;
import com.xmcamera.core.sysInterface.IXmRealplayCameraCtrl;
import com.xmcamera.core.sysInterface.IXmSystem;
import com.xmcamera.core.sysInterface.IXmTalkManager;
import com.xmcamera.core.sysInterface.OnStreamRateListener;
import com.xmcamera.core.sysInterface.OnXmBeginTalkListener;
import com.xmcamera.core.sysInterface.OnXmEndTalkListener;
import com.xmcamera.core.sysInterface.OnXmListener;
import com.xmcamera.core.sysInterface.OnXmMgrConnectStateChangeListener;
import com.xmcamera.core.sysInterface.OnXmSimpleListener;
import com.xmcamera.core.sysInterface.OnXmStartResultListener;
import com.xmcamera.core.sysInterface.OnXmTalkVolumListener;
import com.xmcamera.core.view.decoderView.XmGlView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/20.
 */
public class PlayActivity extends Activity implements View.OnClickListener, XmSysDataDef.XmPlaybackPosCallback {

    IXmSystem xmSystem;
    //实时播放
    IXmRealplayCameraCtrl realplayCameraCtrl;
    //回放
    IXmPlaybackCameraCtrl mPlaybackCtrl;
    //实时对讲
    IXmTalkManager talkManager;
    int cameraid;
    FrameLayout playContent;
    XmGlView glView;
    TextView show;
    String logtext = "LogCat:";

    spUtil sp;
    boolean isJustCreate = false;
    int playId;
    int playBackId;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.cb_shengyin)
    CheckBox cbShengyin;
    @BindView(R.id.iv_xiangji)
    ImageView ivXiangji;
    @BindView(R.id.cb_duijiang)
    CheckBox cbDuijiang;
    @BindView(R.id.cb_shexiang)
    CheckBox cbShexiang;
    @BindView(R.id.cb_huazhi)
    CheckBox cbHuazhi;
    LinearLayout.LayoutParams params;
    DisplayMetrics dm;
    TimePickerView pvTime;
    @BindView(R.id.btn)
    TextView btn;
    private List<XmRemoteFile> mRemoteFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                Long time = System.currentTimeMillis();
                L.e("结束时间" + time);
                onEnterNoSignZone(date.getTime(), time, 4 * 6 * 3600000);
            }
        });
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayActivity.this.finish();
            }
        });
        cameraid = getIntent().getExtras().getInt("cameraId");
        xmSystem = XmSystem.getInstance();

        realplayCameraCtrl = xmSystem.xmGetRealplayController();

        mPlaybackCtrl = xmSystem.xmGetPlaybackController();

        talkManager = xmSystem.xmGetTalkManager(cameraid);
        glView = new XmGlView(this, null);
        playContent = (FrameLayout) findViewById(R.id.glview);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width = dm.widthPixels - 10;
        params.height = dm.widthPixels - 10;
        playContent.setLayoutParams(params);
        playContent.addView((View) glView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        mHander.sendEmptyMessage(0x126);
        sp = new spUtil(this);

        isJustCreate = true;

        ivXiangji.setOnClickListener(this);

        //静音
        cbShengyin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == false) {
                    realplayCameraCtrl.openPlayAudio();
                } else {
                    realplayCameraCtrl.closePlayAudio();
                }
            }
        });
        //录像开关
        cbShexiang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == false) {//停止录像
                    if (sp.getisDemo()) {
                        mHander.sendEmptyMessage(0x127);
                        return;
                    }
                    if (!isPlay()) {
                        return;
                    }
                    StopRecord();

                } else {//开始录像
                    if (sp.getisDemo()) {
                        mHander.sendEmptyMessage(0x127);
                        return;
                    }
                    if (!isPlay()) {
                        return;
                    }
                    Record();
                }
            }
        });
        //对讲开关
        cbDuijiang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == false) {
                    stopTalk();
                } else {
                    talk();
                }
            }
        });
        cbHuazhi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == false) {
                    mPlaybackCtrl.xmStop(playBackId);
                    btn.setVisibility(View.GONE);
                    Play();
                } else {
                    realplayCameraCtrl.xmStop(playId);
                    btn.setVisibility(View.VISIBLE);
                    pvTime.show();

                }
            }
        });
        xmSystem.registerOnMgrConnectChangeListener(new OnXmMgrConnectStateChangeListener() {
            @Override
            public void onChange(boolean b) {

            }
        });

        realplayCameraCtrl.registerOnStreamRateListener(new OnStreamRateListener() {
            @Override
            public void onStreamRate(long l, long l1) {
                L.e("视频流：" + l);
            }
        });
        SwitchStream(XmStreamMode.ModeHd);
        Play();
        regitserListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Stop();
    }

    private void regitserListener() {
        mPlaybackCtrl.xmSetPlaybackCachePosListener(this);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            toolBar.setVisibility(View.GONE);
            params.width = dm.heightPixels - 10;
            params.height = dm.widthPixels - 10;
            playContent.setLayoutParams(params);
        }

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            toolBar.setVisibility(View.VISIBLE);
            params.width = dm.widthPixels - 10;
            params.height = dm.widthPixels - 10;
            playContent.setLayoutParams(params);
        }
    }

    /**
     * 视频播放
     */
    private void Play() {
        if (realplayCameraCtrl.isPlaying()) {
            mHander.sendEmptyMessage(0x128);
            return;
        }
        realplayCameraCtrl.xmStart(glView, cameraid, new OnXmStartResultListener() {

            @Override
            public void onStartSuc(boolean isLocalNet, int cameraId, int var3) {
                playId = var3;
                showTV("播放成功！  isLocalNet:" + isLocalNet);
            }

            @Override
            public void onStartErr(XmErrInfo errcode) {
                showTV("errId:" + errcode.errId + ",errCode:" + errcode.errCode + ",errdiscribe:" + errcode.discribe);
            }
        });
    }

    private void Stop() {
        if (realplayCameraCtrl.isPlaying()) {
            // realplayCameraCtrl.xmStop(playId);
            realplayCameraCtrl.xmStop(playId);
            showTV("停止播放！");
        }
    }

    private void SwitchStream(final XmStreamMode mode) {

        realplayCameraCtrl.xmSwitchStream(mode, new OnXmSimpleListener() {
            @Override
            public void onErr(XmErrInfo info) {
                showTV("切换失败！" + info.discribe);
            }

            @Override
            public void onSuc() {
                if (mode == XmStreamMode.ModeHd) {
                    showTV("切换到HD");
                } else if (mode == XmStreamMode.ModeFluency) {
                    showTV("切换到SD");
                } else if (mode == XmStreamMode.ModeAdapter) {
                    showTV("切换到AT");
                }
            }
        });
    }

    private void Capture() {
        final long time = System.currentTimeMillis();
        realplayCameraCtrl.xmCapture("/sdcard/zzj/", "p" + time + ".jpg", new OnXmListener<String>() {
            @Override
            public void onErr(XmErrInfo info) {
                showTV("截图失败");
            }

            @Override
            public void onSuc(String info) {
                showTV("截图成功1:" + "/sdcard/zzj/" + "p" + time + ".jpg");
                showJP("截图成功1:" + "/sdcard/zzj/" + "p" + time + ".jpg");
                realplayCameraCtrl.xmThumbnail("/sdcard/zzj", "thumb" + time + ".jpg", "p" + time + ".jpg", new OnXmListener<String>() {
                    @Override
                    public void onErr(XmErrInfo info) {
                        showTV("截图失败2");
                    }

                    @Override
                    public void onSuc(String info) {
                        showTV("截图成功2:" + "/sdcard/zzj/" + "thumb" + time + ".jpg");
                    }
                });
            }
        });
    }

    boolean isRecord = false;

    private void Record() {
        isRecord = true;
        long time = System.currentTimeMillis();
        boolean suc = realplayCameraCtrl.xmRecord("/sdcard/zzj", "v" + time + ".mp4");
        Toast.makeText(this, suc ? "开始录像" : "录像失败", Toast.LENGTH_LONG).show();
        showTV(suc ? "开始录像" : "录像失败");
    }

    private void StopRecord() {
        if (!isRecord) {
            return;
        }
        isRecord = false;
        String ss = realplayCameraCtrl.xmStopRecord();
        Toast.makeText(this, ss == null ? "录像失败" : "录像成功：" + ss, Toast.LENGTH_LONG).show();
        showTV(ss == null ? "录像失败" : "录像成功：" + ss);
    }

    private void btn_rebinder() {
        new AlertDialog.Builder(this).setTitle("温馨提示")
                .setMessage("您确定要删除此摄像机吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteDevice();
                    }
                }).setNegativeButton("取消", null).show();
    }

    private void DeleteDevice() {
        xmSystem.xmDeleteDevice(cameraid, xmSystem.xmFindDevice(cameraid).getmUuid(), new OnXmSimpleListener() {
            @Override
            public void onErr(XmErrInfo info) {
                mHander.sendEmptyMessage(0x124);
                showTV("删除失败！" + info.discribe);
            }

            @Override
            public void onSuc() {
                mHander.sendEmptyMessage(0x123);
            }
        });
    }

    Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Toast.makeText(PlayActivity.this, "删除成功！", Toast.LENGTH_LONG).show();
                setResult(101);
                finish();
            } else if (msg.what == 0x124) {
                Toast.makeText(PlayActivity.this, "删除失败！", Toast.LENGTH_LONG).show();
            } else if (msg.what == 0x125) {
                Toast.makeText(PlayActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
            } else if (msg.what == 0x126) {
            } else if (msg.what == 0x127) {
                Toast.makeText(PlayActivity.this, "你没有权限，请先注册登录~", Toast.LENGTH_LONG).show();
            } else if (msg.what == 0x128) {
                Toast.makeText(PlayActivity.this, "视频已经在播放中！", Toast.LENGTH_LONG).show();
            } else if (msg.what == 0x129) {
                Toast.makeText(PlayActivity.this, "截图成功:" + "/sdcard/zzj/", Toast.LENGTH_LONG).show();

            }
        }
    };

    private boolean isPlay() {
        if (!realplayCameraCtrl.isPlaying()) {
            showTV("视频未开启！");
        }
        return realplayCameraCtrl.isPlaying();
    }

    private void showTV(String ss) {
        logtext = logtext + "\n" + ss;
        mHander.sendEmptyMessage(0x126);
    }

    private void showJP(String ss) {
        logtext = logtext + "\n" + ss;
        mHander.sendEmptyMessage(0x129);
    }

    @OnClick({R.id.btn, R.id.iv_xiangji})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                if (mRemoteFiles.size() > 0) {
                    onPlayback(mRemoteFiles.get(0), 0);
                } else {
                    T.showShort(PlayActivity.this, "当前没有视频回放");
                }
                L.e("回放长度" + mRemoteFiles.size());
                break;
            case R.id.iv_xiangji:
                if (sp.getisDemo()) {
                    mHander.sendEmptyMessage(0x127);
                    break;
                }
                if (!isPlay()) {
                    break;
                }
                Capture();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Stop();
            mPlaybackCtrl.xmStop(playBackId);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 开始对讲
     */
    private void talk() {
        talkManager.xmBeginTalk(new OnXmBeginTalkListener() {
                                    @Override
                                    public void onIPCIsTalking() {
                                    }

                                    @Override
                                    public void onOpenTalkErr(XmErrInfo xmErrInfo) {
                                    }

                                    @Override
                                    public void onAlreadyTalking() {
                                    }

                                    @Override
                                    public void onNoRecordPermission() {
                                    }

                                    @Override
                                    public void onSuc() {
                                    }
                                },
                new OnXmTalkVolumListener() {
                    @Override
                    public void onVolumeChange(int i) {
                    }
                });
    }

    /**
     * 停止对讲
     */
    private void stopTalk() {
        talkManager.xmEndTalk(new OnXmEndTalkListener() {
            @Override
            public void onAlreadyClosed() {
            }

            @Override
            public void onTalkClosing() {
            }

            @Override
            public void onSuc() {
            }

            @Override
            public void onCloseTalkErr(XmErrInfo xmErrInfo) {
            }
        });
    }

    //回放
    public void onEnterNoSignZone(long timeBegin, long timeEnd, long MinSearchTime) {
        if (cameraid <= 0) {
            return;
        }

        if (realplayCameraCtrl.isPlaying()) {
            mHander.sendEmptyMessage(0x131);
            return;
        }

        final Time begin = new Time();
        final Time end = new Time();
        long beginms = timeBegin;
        long endms = timeEnd;
        if (timeBegin == 0) {
            beginms = timeEnd - MinSearchTime;
            endms = timeEnd;
        } else {
            if (timeEnd - timeBegin >= MinSearchTime) {
                beginms = timeBegin;
                endms = timeBegin + MinSearchTime;
            } else if (timeEnd - timeBegin < 60000) {//时间小于一分钟，不搜索
                return;
            }
        }
        begin.set(beginms);
        end.set(endms);
        beginSearchTask(begin, end);
    }

    private Time mLastSearchTaskBegin = null;
    private Time mLastSearchTaskEnd = null;

    private void handleSearchAfter(final Time begin, final Time end) {
        handleSearchAfter(begin, end, 1000);
    }

    private void handleSearchAfter(final Time begin, final Time end, long delay) {
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                beginSearchTask(begin, end);
            }
        }, delay);//如果查询失败，1秒后再进行查询。
    }

    private void beginSearchTask(final Time begin, final Time end) {
        xmSystem.xmGetRemoteDeviceListFromNet(cameraid, begin, end, new OnXmListener<List<XmRemoteFile>>() {
            @Override
            public void onErr(XmErrInfo info) {
                Log.v("AAAAA", "beginSearchTask Err!");
                if (info.errCode == XmErrInfo.ERR_NO_TASK_ALREADY_RUNNING) {
                    if (isJustCreate) {
                        isJustCreate = false;
                        handleSearchAfter(begin, end, 6000);
                        return;
                    }
                    mLastSearchTaskBegin = begin;
                    mLastSearchTaskEnd = end;
                    Log.v("AAAAATimeline", "ERR_NO_TASK_ALREADY_RUNNING");
                    return;
                }//如果查询任务正在运行，那么此次查询请求记录，待前面的查询完毕再进行查询
                //LogUtils.e("Timeline","ERR_ will search delay"+XmTimeLineFragment.this.hashCode());
                else if (info.errCode == XmErrInfo.ERR_NO_PERMISSION_DENIED) {
                    Time time = new Time();
                    time.setToNow();
                    return;
                }
                handleSearchAfter(begin, end);
                mHander.sendEmptyMessage(0x132);
            }

            @Override
            public void onSuc(List<XmRemoteFile> info) {
                List<XmTimeRect> validRects = new ArrayList<>();
                long earlyestBegintime = begin.toMillis(false);
                long lastestEndtime = end.toMillis(false);
                if (info != null) {
                    List<XmRemoteFile> validFiles = addRemoteFiles(info);
                    for (XmRemoteFile file : validFiles) {

                        validRects.add(new XmTimeRect(file.getStartTime().toMillis(false),
                                file.getEndTime().toMillis(false)));
                        if (earlyestBegintime > file.getStartTime().toMillis(false)) {
                            earlyestBegintime = file.getStartTime().toMillis(false);
                        }
                        if (lastestEndtime < file.getEndTime().toMillis(false)) {
                            lastestEndtime = file.getEndTime().toMillis(false);
                        }
                    }
                }
                if (mLastSearchTaskBegin != null && mLastSearchTaskEnd != null) {
                    handleSearchAfter(mLastSearchTaskBegin, mLastSearchTaskEnd);
                    mLastSearchTaskBegin = null;
                    mLastSearchTaskEnd = null;
                }
                mHander.sendEmptyMessage(0x132);
            }
        });
    }

    private List<XmRemoteFile> addRemoteFiles(List<XmRemoteFile> addfiles) {
        synchronized (mRemoteFiles) {
            mRemoteFiles.clear();
            List<XmRemoteFile> validFiles = new ArrayList<>();
            for (int i = 0; i < addfiles.size(); i++) {
                boolean valid = true;
                long saveLen = mRemoteFiles.size();
                for (int j = 0; j < saveLen; j++) {//如果文件时间有交叉，不更新
                    long curBegin = addfiles.get(i).getStartTime().toMillis(false);
                    long curEnd = addfiles.get(i).getEndTime().toMillis(false);
                    long saveBegin = mRemoteFiles.get(j).getStartTime().toMillis(false);
                    long saveEnd = mRemoteFiles.get(j).getEndTime().toMillis(false);
                    if (addfiles.get(i).getFileName().equals(mRemoteFiles.get(j).getFileName())) {//文件名相同,忽略
                        valid = false;
                    } else {
                        if (curBegin >= saveBegin && curBegin < saveEnd) {//
                            if (curBegin == saveBegin && curEnd > saveEnd) {
                                mRemoteFiles.get(j).setStartTime(addfiles.get(i).getStartTime());
                                mRemoteFiles.get(j).setEndTime(addfiles.get(i).getEndTime());
                                mRemoteFiles.get(j).setFileName(addfiles.get(i).getFileName());
                                mRemoteFiles.get(j).setFileSize(addfiles.get(i).getFileSize());
                            }
                            valid = true;
                        }
                        if (curEnd > saveBegin && curEnd <= saveEnd) {
                            valid = false;
                        }
                    }
                }
                if (valid) {
                    mRemoteFiles.add(addfiles.get(i));
                }
                if (valid) {
                    validFiles.add(addfiles.get(i));
                }
            }
            return validFiles;
        }
    }

    public void onPlayback(XmRemoteFile file, int pos) {

        if (!xmSystem.xmCheckPermisson(XmPermissonAction.Ctrl_Playback, cameraid)) {
            return;
        }
        realPlayback(file, pos);
    }

    private void realPlayback(final XmRemoteFile file, final int pos) {
        if (mPlaybackCtrl.isPlaying()) {
            mPlaybackCtrl.xmStop(playBackId);
        }
        mPlaybackCtrl.xmStartPlayback(glView, cameraid, file, pos, new OnXmListener<Integer>() {
            @Override
            public void onErr(XmErrInfo info) {
                showTV("回放失败！");

            }

            @Override
            public void onSuc(Integer playid) {
                showTV("回放成功！");
                playBackId = playid;

            }
        });
    }

    @Override
    public void onPlaybackPos(int i) {

    }

    /**
     * 把日期转换成秒
     */
    private long changeDate(String sDt) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date dt2 = null;
        try {
            dt2 = sdf.parse(sDt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//继续转换得到秒数的long型
        long begintime = dt2.getTime();
        return begintime;
    }


}
