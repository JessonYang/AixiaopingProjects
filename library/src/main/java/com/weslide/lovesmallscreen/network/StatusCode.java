package com.weslide.lovesmallscreen.network;

import android.content.Context;

import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.T;

import net.aixiaoping.library.R;

/**
 * Created by xu on 2016/5/25.
 * 后台返回的请求状态码
 */
public class StatusCode {

    /**
     * 成功
     */
    public static final int SUCCESS = 0x01;

    /**
     * 失败
     */
    public static final int FAIL = -0x01;

    /**
     * 验证是否请求成功
     *
     * @param response
     * @return
     */
    public static boolean validateSuccess(Context context, Response response) {
        if (response != null) {
            if (response.getStatus() == SUCCESS) {
                return true;
            } else {
                L.e("请求错误，错误码：" + response.getStatus() + ", 错误内容：" + response.getMessage());
            }
        } else {
            T.showShort(context, R.string.error_data_load);
        }

        return false;
    }

    /**
     * 验证是否请求成功,如果为成功，Toast错误信息
     *
     * @param response
     * @return
     */
    public static boolean validateSuccessIfFailToast(Context context, Response response) {
        if (response != null) {
            if (response.getStatus() == SUCCESS) {
                return true;
            } else {
                L.e("请求错误，错误码：" + response.getStatus() + ", 错误内容：" + response.getMessage());
                T.showShort(context, response.getMessage());
            }
        } else {
//            T.showShort(context, R.string.error_data_load);
        }
        return false;
    }

}
