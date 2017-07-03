package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.io.File;

import retrofit2.http.Part;

/**
 * Created by xu on 2016/7/8.
 * 文件上传
 */
public class UploadFileBean extends BaseModel {

//    uploadFile	上传的文件	File
//    userId	上传该文件的用户
//    fileUse	文件用途	COMMENT_IMAGE：商品评论图片
//    USER_HEAD_SCULPTURE：用户头像
//    FEED_BACK:意见反馈

//    oppositeUrl	上传文件的相对路径
//    absoluteUrl	上传见的绝对路径

    //----------------数据

    private File file;
    private String userId;
    /** 文件用途
     * COMMENT_IMAGE：商品评论图片
     * USER_HEAD_SCULPTURE：用户头像
     * FEED_BACK:意见反馈
     * Constants 中有定义
     */
    private String fileUse;

    //---------------返回

    @SerializedName("oppositeUrl")
    private String oppositeUrl;
    @SerializedName("absoluteUrl")
    private String absoluteUrl;


    /** 上传的文件 */
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /** 上传的用户id */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** 文件用途
     * COMMENT_IMAGE：商品评论图片
     * USER_HEAD_SCULPTURE：用户头像
     * FEED_BACK:意见反馈
     * Constants 中有定义
     */
    public String getFileUse() {
        return fileUse;
    }

    /** 文件用途
     * COMMENT_IMAGE：商品评论图片
     * USER_HEAD_SCULPTURE：用户头像
     * FEED_BACK:意见反馈
     * Constants 中有定义
     */
    public void setFileUse(String fileUse) {
        this.fileUse = fileUse;
    }

    /** 上传文件的相对路径 */
    public String getOppositeUrl() {
        return oppositeUrl;
    }

    public void setOppositeUrl(String oppositeUrl) {
        this.oppositeUrl = oppositeUrl;
    }

    /** 上传见的绝对路径 */
    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }
}
