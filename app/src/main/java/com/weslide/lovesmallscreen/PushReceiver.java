package com.weslide.lovesmallscreen;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.weslide.lovesmallscreen.activitys.MsgHomeActivity;
import com.weslide.lovesmallscreen.models.Message;
import com.weslide.lovesmallscreen.models.RefreshPushMsg;
import com.weslide.lovesmallscreen.models.push.OtherLogin;
import com.weslide.lovesmallscreen.models.push.SystemMsg;
import com.weslide.lovesmallscreen.network.Push;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.UserUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;

/**
 * Created by xu on 2016/5/6.
 * 用于接收推送广播
 */
public class PushReceiver extends BroadcastReceiver {

    /**
     * 单点登录
     */
    public static final int CODE_OTHER_LOGIN = 1000;
    /**
     * 消息中心
     */
    public static final int CODE_MESSAGE = 2000;

    /**
     * 系统消息
     */
    public static final int CODE_SYSTEM_MSG = 3000;

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();
    Bitmap largeIcon = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");

                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

                if (payload != null) {
                    String data = new String(payload);

                    if (StringUtils.isBlank(data)) return;

                    L.e("透传推送：" + data);

                    try {
                        Gson gson = new Gson();
                        Push push = gson.fromJson(data, Push.class);
                        if (push.getTaskCode() == CODE_MESSAGE) {

                            /*if (!ContextParameter.getLocalConfig().isOpenPush()) {
                                L.e("用户已手动关闭消息推送");
                                return;
                            }
                            if (ContextParameter.isLogin() == false) {
                                return;
                            }

                            Push<Message> messagePush = new Push<>();
                            //对泛型做支持
                            Type objectType = new TypeToken<Push<Message>>() {
                            }.getType();
                            messagePush = gson.fromJson(data, objectType);
                            EventBus.getDefault().post(new RefreshPushMsg());
                            sendMessage(context, messagePush);*/
                        } else if (push.getTaskCode() == CODE_OTHER_LOGIN) {
                            Push<OtherLogin> otherLoginPush = new Push<OtherLogin>();
                            //对泛型做支持
                            Type objectType = new TypeToken<Push<OtherLogin>>() {
                            }.getType();
                            otherLoginPush = gson.fromJson(data, objectType);
                            otherLogin(context, otherLoginPush);
                        } else if (push.getTaskCode() == CODE_SYSTEM_MSG) {
                            Push<SystemMsg> systemMsgPush = new Push<SystemMsg>();
                            //对泛型做支持
                            Type objectType = new TypeToken<Push<SystemMsg>>() {
                            }.getType();
                            systemMsgPush = gson.fromJson(data, objectType);
                            context.getSharedPreferences("newMsgInfo", Context.MODE_PRIVATE).edit().putBoolean("hasNewMsg", true).commit();
                            EventBus.getDefault().post(new RefreshPushMsg());
                            saveMsg(context, systemMsgPush);
                            sendMessage(systemMsgPush, context);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        L.e("test", "异常", e);
                    }

                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
//                Log.d("雨落无痕丶", "onReceive: cid:" + cid);
                ContextParameter.setChannelId(cid);
                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 *
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }

    /**
     * 单点登录
     *
     * @param context
     * @param otherLoginPush
     */
    public void otherLogin(Context context, Push<OtherLogin> otherLoginPush) {
        if (otherLoginPush.getData().getUserId().equals(ContextParameter.getUserInfo().getUserId())) {
            if (!otherLoginPush.getData().getChannelId().equals(ContextParameter.getChannelId())) {
                UserUtils.otherLogin(context);
            }
        }
    }

    /**
     * 生成系统通知栏
     */
    private void sendMessage(Context context, Push<Message> messagePush) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        Intent intent = new Intent(context, MyMessageActivity.class);
        Intent intent = new Intent(context, MsgHomeActivity.class);

        boolean isAboveLollipop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        builder.setSmallIcon(isAboveLollipop ? R.drawable.icon_white : R.drawable.icon_app)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_app))
                .setContentTitle(messagePush.getData().getTitle())//大标题
                .setContentText(messagePush.getData().getDetail())//内容
//                .setTicker("爱小屏新消息哦~~")//提醒
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));
//                .setFullScreenIntent(PendingIntent.getActivity(context, 0, intent, 0), true);
        Notification n = builder.getNotification();
        n.tickerText = "爱小屏新消息哦~~";
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        if (ContextParameter.getLocalConfig().isOpenVoice()) {
            n.defaults = Notification.DEFAULT_SOUND;
        } else {
            n.sound = null;
        }
        nm.notify(0, n);
    }

    private void sendMessage(Push<SystemMsg> messagePush, Context context) {
        Log.d("雨落无痕丶", "sendMessage: yy");
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        Intent intent = new Intent(context, MyMessageActivity.class);
        Intent intent = new Intent(context, MsgHomeActivity.class);
        boolean isAboveLollipop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        builder.setSmallIcon(isAboveLollipop ? R.drawable.icon_white : R.drawable.icon_app)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_app))
                .setContentTitle("爱小屏消息")//大标题
                .setContentText(messagePush.getData().getMessage())//内容
                .setTicker("爱小屏新消息哦~~")//提醒
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, 0))
                .setFullScreenIntent(PendingIntent.getActivity(context, 0, intent, 0), true);
        Notification n = builder.getNotification();
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        n.tickerText = "爱小屏新消息哦~~";
        if (ContextParameter.getLocalConfig().isOpenVoice()) {
            n.defaults = Notification.DEFAULT_SOUND;
        } else {
            n.sound = null;
        }
        nm.notify(0, n);
    }

    private void saveMsg(Context context, Push<SystemMsg> systemMsgPush) {
        SharedPreferences.Editor edit = context.getSharedPreferences("msg", Context.MODE_PRIVATE).edit();
        edit.putString("unreadCount", systemMsgPush.getData().getMsg_unread_count());
        edit.commit();
    }

}
