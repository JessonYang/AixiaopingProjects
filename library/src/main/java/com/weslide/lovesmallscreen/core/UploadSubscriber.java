package com.weslide.lovesmallscreen.core;

import com.weslide.lovesmallscreen.models.bean.UploadFileBean;
import com.weslide.lovesmallscreen.network.Response;

import java.util.List;

/**
 * Created by xu on 2016/7/8.
 * 图片上传Subscriber
 */
public abstract class UploadSubscriber extends SupportSubscriber<List<Response<UploadFileBean>>> {

}
