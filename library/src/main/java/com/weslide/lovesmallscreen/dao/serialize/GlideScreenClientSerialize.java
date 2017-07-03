package com.weslide.lovesmallscreen.dao.serialize;

import android.content.Context;

import com.weslide.lovesmallscreen.models.bean.GetAdverImgsBean;
import com.weslide.lovesmallscreen.models.config.ClientConfig;
import com.weslide.lovesmallscreen.utils.SerializableUtils;

/**
 * Created by xu on 2016/7/14.
 * 滑屏广告客户端数据序列化存储
 */
public class GlideScreenClientSerialize {

    public static final String SERIALIZE_NAME = "GlideScreenClientSerialize";

    public static void setClientConfig(Context context, GetAdverImgsBean config) {
        SerializableUtils.serializableToCacheFile(context, config, SERIALIZE_NAME);
    }

    public static GetAdverImgsBean getClientConfig(Context context) {

        Object value = SerializableUtils.getObjectByCacheFile(context, SERIALIZE_NAME);
        GetAdverImgsBean clientConfig = null;

        if(value == null){
            clientConfig = new GetAdverImgsBean();
        } else {
            clientConfig = (GetAdverImgsBean) value;
        }

        return clientConfig;
    }

}
