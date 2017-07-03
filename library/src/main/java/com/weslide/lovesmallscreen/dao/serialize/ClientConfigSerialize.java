package com.weslide.lovesmallscreen.dao.serialize;

import android.content.Context;

import com.weslide.lovesmallscreen.models.config.ClientConfig;
import com.weslide.lovesmallscreen.utils.SerializableUtils;

/**
 * Created by xu on 2016/7/14.
 * 客户端配置数据序列化存储
 */
public class ClientConfigSerialize {

    public static final String SERIALIZE_NAME = "ClientConfigSerialize";

    public static void setClientConfig(Context context, ClientConfig config) {
        SerializableUtils.serializableToCacheFile(context, config, SERIALIZE_NAME);
    }

    public static ClientConfig getClientConfig(Context context) {

        Object value = SerializableUtils.getObjectByCacheFile(context, SERIALIZE_NAME);
        ClientConfig clientConfig = null;

        if(value == null){
            clientConfig = new ClientConfig();
        } else {
            clientConfig = (ClientConfig) value;
        }

        return clientConfig;
    }

}
