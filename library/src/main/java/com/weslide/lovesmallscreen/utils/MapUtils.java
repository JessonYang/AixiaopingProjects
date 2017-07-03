package com.weslide.lovesmallscreen.utils;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.core.BaseActivity;

import net.aixiaoping.library.R;

import java.net.URISyntaxException;

/**
 * Created by xu on 2016/8/13.
 * 地图工具类
 */
public class MapUtils {
    // 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
    static CoordinateConverter converter = new CoordinateConverter();
    static {
        converter.from(CoordinateConverter.CoordType.COMMON);
    }

    /**
     * 启动导航
     * @param context
     * @param latLng
     */
    public static void showNav(final BaseActivity context, final LatLng latLng) {
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {

                LatLng currentLatlng = new LatLng(Double.parseDouble( ContextParameter.getCurrentLocation().getLatitude() ),
                        Double.parseDouble(ContextParameter.getCurrentLocation().getLongitude()));

                switch (getSelectedIndex()) {
                    case 0:
                        startBaidumapLine(context, gcj022bd(currentLatlng), gcj022bd(latLng));
                        break;
                    case 1:
                        startAMapLine(context, currentLatlng, gcj022bd(latLng));
                        break;
                }

                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder) builder).items(new String[]{"百度地图", "高德地图"}, 0)
                .title("请选择导航地图")
                .positiveAction("确定")
                .negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(context.getSupportFragmentManager(), null);
    }


    /**
     * 启动百度地图路线规划
     *
     * @param start
     * @param end
     */
    public static void startBaidumapLine(BaseActivity context , LatLng start, LatLng end) {

        // 构建 导航参数
//        NaviParaOption para = new NaviParaOption()
//                .startPoint(start).endPoint(end)
//                .startName("起点").endName("终点");
//
//        try {
//            BaiduMapNavigation.openBaiduMapNavi(para, this);
//        } catch (BaiduMapAppNotSupportNaviException e) {
//            e.printStackTrace();
//            showDialog();
//        }

        try {
            Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:" + start.latitude + "," + start.longitude + "|" +
                    "name:我家&destination=latlng:" + end.latitude + "," + end.longitude +
                    "|name:终点&mode=driving&region=&src=yourCompanyName|yourAppName#Intent;scheme=b" +
                    "dapp;package=com.baidu.BaiduMap;end");
            context.startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            showDialog(context);
        }

    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public static void showDialog(final BaseActivity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(context);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }


    public static LatLng gcj022bd(LatLng old) {
        // sourceLatLng待转换坐标
        converter.coord(old);
        return converter.convert();
    }

    /**
     * 启动本地高德地图路线规划
     *
     * @param start
     * @param end
     */
    public static void startAMapLine(BaseActivity context,LatLng start, LatLng end) {

        try {
            Intent intent = new Intent("android.intent.action.VIEW",
                    android.net.Uri.parse("androidamap://route?sourceApplication=softname&slat="
                            + start.latitude + "&slon=" + start.longitude + "&sname=起点&dlat="
                            + end.latitude + "&dlon=" + end.longitude + "&dname=终点&dev=0&m=0&t=1"));
            intent.setPackage("com.autonavi.minimap");
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            T.showShort(context, "未安装高德地图");
        }


    }

}
