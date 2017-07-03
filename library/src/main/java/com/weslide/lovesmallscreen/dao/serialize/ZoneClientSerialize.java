package com.weslide.lovesmallscreen.dao.serialize;

import android.content.Context;

import com.weslide.lovesmallscreen.models.bean.GetAdverImgsBean;
import com.weslide.lovesmallscreen.models.bean.ZoneListBean;
import com.weslide.lovesmallscreen.utils.SerializableUtils;

/**
 * Created by xu on 2016/7/14.
 * 城市列表相关客户端数据序列化存储
 */
public class ZoneClientSerialize {

    public static final String SERIALIZE_NAME = "ZoneClientSerialize";

    public static void setClientConfig(Context context, ZoneListBean config) {
        SerializableUtils.serializableToCacheFile(context, config, SERIALIZE_NAME);
    }

    public static ZoneListBean getClientConfig(Context context) {

        Object value = SerializableUtils.getObjectByCacheFile(context, SERIALIZE_NAME);
        ZoneListBean clientConfig = null;

        if(value == null){
            clientConfig = new ZoneListBean();
        } else {
            clientConfig = (ZoneListBean) value;
        }

        return clientConfig;
    }

}
