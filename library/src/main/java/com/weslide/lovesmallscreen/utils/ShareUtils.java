package com.weslide.lovesmallscreen.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.tauth.Tencent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.ContextUtil;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.core.BaseActivity;

import net.aixiaoping.library.R;

import java.lang.reflect.Field;

/**
 * Created by xu on 2016/7/22.
 * 社会化分享
 */
public class ShareUtils {


//    private static IWXAPI mIWXAPI;
//
//    static {
//        mIWXAPI = WXAPIFactory.createWXAPI(ArchitectureAppliation.getAppliation(), Constants.WEXIN_APP_ID,
//                true);
//        mIWXAPI.registerApp(Constants.WEXIN_APP_ID);
//    }


    /**
     * 社会化分享
     *
     * @param title
     * @param iconUrl
     * @param targetUrl
     * @param content
     */
    public static void share(final Activity context, final String title, final String iconUrl, final String targetUrl, final String content) {
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        com.rey.material.app.Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {

            @Override
            protected com.rey.material.app.Dialog onBuild(Context context, int styleId) {
                return super.onBuild(context, styleId);
            }

            @Override
            protected void onBuildDone(com.rey.material.app.Dialog dialog) {

                dialog.findViewById(R.id.layout_share_dialog_mLvShareToQQFriend).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareToQQ(context, title, iconUrl, targetUrl, content);
                    }
                });

                dialog.findViewById(R.id.layout_share_dialog_mLvShareToQQZone).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareToQzone(context, title, iconUrl, targetUrl, content);
                    }
                });

                dialog.findViewById(R.id.layout_share_dialog_mLvShareToWechatFriend).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareToWexin(context, title, iconUrl, targetUrl, content);
                    }
                });

                dialog.findViewById(R.id.layout_share_dialog_mLvShareToFriendCircle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shareToWeXinFriendCircle(context, title, iconUrl, targetUrl, content);
                    }
                });
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.contentView(R.layout.dialog_axp_share);

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(((BaseActivity) context).getSupportFragmentManager(), null);
    }


    public static void shareToWexin(final Activity context, final String title, final String iconUrl, final String targetUrl, final String content) {

        if (!DeviceUtils.checkApplication(context, Constants.PACKAGE_WEIXIN)) {
            T.showShort(context, "请安装微信客户端");
            return;
        }

        UMImage image = new UMImage(context, iconUrl);

        new ShareAction(context)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(new DefaultShareResultListener())
                .withText(content)
                .withTargetUrl(targetUrl)
                .withMedia(image)
                .withTitle(title)
                .share();


//        Glide.with(context).load(iconUrl).into(new SimpleTarget<GlideDrawable>() {
//            @Override
//            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                Bitmap bitmap = BitmapUtils.drawableToBitamp(resource);
//                WXWebpageObject object = new WXWebpageObject();
//                object.webpageUrl = targetUrl;
//                WXMediaMessage msg = new WXMediaMessage();
//                msg.mediaObject = object;
//                bitmap = BitmapUtils.zoomImage(bitmap, 72, 72);
//                msg.thumbData = BitmapUtils.bmpToByteArray(bitmap);
//                // msg.description = spread + "\n" + "注册时填写我的邀请码:" + inviteCode;
//                msg.title = content;
//
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = String.valueOf(System.currentTimeMillis());
//                req.message = msg;
//                req.scene = SendMessageToWX.Req.WXSceneTimeline;
//                mIWXAPI.sendReq(req);
//            }
//
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//                T.showShort(context, "图片请求失败，请稍后再试！");
//            }
//        });
    }

    public static void shareToWeXinFriendCircle(final Activity context, final String title, final String iconUrl, final String targetUrl, final String content) {
        if (!DeviceUtils.checkApplication(context, Constants.PACKAGE_WEIXIN)) {
            T.showShort(context, "请安装微信客户端");
            return;
        }

        UMImage image = new UMImage(context, iconUrl);

        new ShareAction(context)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new DefaultShareResultListener())
                .withText(content)
                .withTargetUrl(targetUrl)
                .withMedia(image)
                .withTitle(title)
                .share();

//        Glide.with(context).load(iconUrl).into(new SimpleTarget<GlideDrawable>() {
//            @Override
//            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                Bitmap bitmap = BitmapUtils.drawableToBitamp(resource);
//                WXWebpageObject object = new WXWebpageObject();
//                object.webpageUrl = targetUrl;
//                WXMediaMessage msg = new WXMediaMessage();
//                msg.mediaObject = object;
//                bitmap = BitmapUtils.zoomImage(bitmap, 72, 72);
//                msg.thumbData = BitmapUtils.bmpToByteArray(bitmap);
//                // msg.description = spread + "\n" + "注册时填写我的邀请码:" + inviteCode;
//                msg.title = content;
//
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = String.valueOf(System.currentTimeMillis());
//                req.message = msg;
//                req.scene = SendMessageToWX.Req.WXSceneTimeline;
//                mIWXAPI.sendReq(req);
//            }
//
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//                T.showShort(context, "图片请求失败，请稍后再试！");
//            }
//        });
    }

    public static void shareToQQ(final Activity context, final String title, final String iconUrl, final String targetUrl, final String content) {
        if (!DeviceUtils.checkApplication(context, Constants.PACKAGE_QQ)) {
            T.showShort(context, "请安装QQ客户端");
            return;
        }

        UMImage image = new UMImage(context, iconUrl);

        new ShareAction(context)
                .setPlatform(SHARE_MEDIA.QQ)
                .setCallback(new DefaultShareResultListener())
                .withText(content)
                .withTargetUrl(targetUrl)
                .withMedia(image)
                .withTitle(title)
                .share();


//        Tencent mTencent = Tencent
//                .createInstance(Constants.QQ_APP_ID, context);
//        final Bundle params = new Bundle();
//        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
//                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
//        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);
//        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, iconUrl);
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, context.getResources().getString(R.string.app_name));
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
//                QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
//
//        if (mTencent == null) {
//            Log.i("MyInvitationFragment", "mTencent >>> null");
//        } else {
//            mTencent.shareToQQ(context, params, new IUiListener() {
//
//                @Override
//                public void onError(UiError arg0) {
//                    Log.i("share", "error >>> " + arg0.errorMessage);
//                }
//
//                @Override
//                public void onComplete(Object arg0) {
//                    Log.i("share", "onComplete");
//                }
//
//                @Override
//                public void onCancel() {
//                    Log.i("share", "cancel ");
//                }
//            });
//        }

    }

    public static void shareToQzone(final Activity context, final String title, final String iconUrl, final String targetUrl, final String content) {
        if (!DeviceUtils.checkApplication(context, Constants.PACKAGE_QQ)) {
            T.showShort(context, "请安装QQ客户端");
            return;
        }

        UMImage image = new UMImage(context, iconUrl);

        new ShareAction(context)
                .setPlatform(SHARE_MEDIA.QZONE)
                .setCallback(new DefaultShareResultListener())
                .withText(content)
                .withTargetUrl(targetUrl)
                .withMedia(image)
                .withTitle(title)
                .share();


//        Tencent mTencent = Tencent
//                .createInstance(Constants.QQ_APP_ID, context);
//        Bundle params = new Bundle();
//        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
//                QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE);
//        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);// 必填
//        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, content);// 选填
//        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);// 必填
//
//        ArrayList<String> mQzoneImageList = new ArrayList<String>();
//        mQzoneImageList.add(iconUrl);
//
//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, mQzoneImageList);
//        mTencent.shareToQzone(context, params, new IUiListener() {
//
//            @Override
//            public void onError(UiError arg0) {
//                Log.i("share", "error >>> " + arg0.errorMessage);
//            }
//
//            @Override
//            public void onComplete(Object arg0) {
//                Log.i("share", "onComplete");
//            }
//
//            @Override
//            public void onCancel() {
//                Log.i("share", "cancel ");
//            }
//        });
    }

    public static void destory(){

        ContextUtil.setContext(null);

        //利用反射处理腾讯友盟内存泄露
        Field tencentField = ReflectionUtils.getDeclaredField(Tencent.class, "sInstance");
        Field tencentListenerFiedld = ReflectionUtils.getDeclaredField(UIListenerManager.class, "mInstance");
        Field UMShareAPIApifield = ReflectionUtils.getDeclaredField(UMShareAPI.class, "b");
        tencentField.setAccessible(true);
        UMShareAPIApifield.setAccessible(true);
        tencentListenerFiedld.setAccessible(true);
        try {
            tencentField.set(null, null);
            UMShareAPIApifield.set(null, null);
            tencentListenerFiedld.set(null, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static class DefaultShareResultListener implements UMShareListener {



        @Override
        public void onResult(SHARE_MEDIA share_media) {
            L.i("分享成功");

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            L.i("分享错误");

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            L.i("分享取消");

        }
    }
}
