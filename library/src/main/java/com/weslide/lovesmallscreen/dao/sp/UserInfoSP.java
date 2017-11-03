package com.weslide.lovesmallscreen.dao.sp;

import android.content.Context;

import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xu on 2016/5/25.
 * 用户信息保存至
 */
public class UserInfoSP {

    /** 当前登录的用户信息 */
    public static final String FILE_USER_INFO = "FILE_USER_INFO";

    /** 当前登录的用户ID */
    public static final String KEY_USER_INFO_USER_ID = "KEY_USER_INFO_USER_ID";
    /** 当前登录的用户昵称 */
    public static final String KEY_USER_INFO_USERNAME = "KEY_USER_INFO_USERNAME";
    /** 当前登录的用户的真实姓名 */
    public static final String KEY_USER_INFO_REALNAME = "KEY_USER_INFO_REALNAME";
    /** 当前登录的用户的手机号码 */
    public static final String KEY_USER_INFO_PHONE = "KEY_USER_INFO_PHONE";
    /** 当前登录的用户的地址 */
    public static final String KEY_USER_INFO_ADDRESS = "KEY_USER_INFO_ADDRESS";
    /** 当前登录的用户的性别 */
    public static final String KEY_USER_INFO_SEX = "KEY_USER_INFO_SEX";
    /** 当前登录的用户的生日 */
    public static final String KEY_USER_INFO_BIRTHDAY = "KEY_USER_INFO_BIRTHDAY";
    /** 当前登录的用户的头像 */
    public static final String KEY_USER_INFO_HEADIMAGE = "KEY_USER_INFO_HEADIMAGE";
    /** 当前登录的用户的邀请码 */
    public static final String KEY_USER_INFO_INVITE_CODE = "KEY_USER_INFO_INVITE_CODE";
    /** 当前登录的用户的签名 */
    public static final String KEY_USER_INFO_SIGN = "KEY_USER_INFO_SIGN";
    /** 当前登录的用户的代金券（包含红包） */
    public static final String KEY_USER_INFO_CASHPOINT = "KEY_USER_INFO_CASHPOINT";
    /** 当前登录的用户的积分 */
    public static final String KEY_USER_INFO_SCORE = "KEY_USER_INFO_SCORE";
    /** 当前登录的用户的会员类型 */
    public static final String KEY_USER_INFO_VIP_TYPE = "KEY_USER_INFO_VIP_TYPE";
    /** 当前登录的用户的可用现金余额 */
    public static final String KEY_USER_INFO_AVAILABLE_MONEY = "KEY_USER_INFO_AVAILABLE_MONEY";
    /** 当前登录的用户的冻结现金余额 */
    public static final String KEY_USER_INFO_UNAVAILABLE_MONEY = "KEY_USER_INFO_UNAVAILABLE_MONEY";
    /** 当前登录的用户的免单券数量 */
    public static final String KEY_USER_INFO_FREE_COUNT = "KEY_USER_INFO_FREE_COUNT";
    /** 当前登录的用户的商家ID */
    public static final String KEY_USER_INFO_SELLER_ID = "KEY_USER_INFO_SELLER_ID";
    /** 当前登录的用户是否已绑定手机号码 */
    public static final String KEY_USER_INFO_IS_BINDING = "KEY_USER_INFO_IS_BINDING";
    /** 当前登录的用户绑定的手机号码 */
    public static final String KEY_USER_INFO_BINDING = "KEY_USER_INFO_BINDING";
    /** 当前登录的用户的融云token */
    public static final String KEY_USER_INFO_TOKEN = "KEY_USER_INFO_TOKEN";
    /** 当前登录的用户融云头像 */
    public static final String KEY_USER_INFO_USERHEAD = "KEY_USER_INFO_USERHEAD";

    /**
     * 清空
     * @param context
     */
    public static void clear(Context context){
        SPUtils.clear(context, FILE_USER_INFO);
    }

    /**
     * 保存用户信息
     * @param userInfo
     */
    public static void setUserInfo(UserInfo userInfo){
        Map<String, String> values = new HashMap<>();
        values.put(KEY_USER_INFO_USER_ID, userInfo.getUserId());
        values.put(KEY_USER_INFO_USERNAME, userInfo.getUsername());
        values.put(KEY_USER_INFO_REALNAME, userInfo.getRealname());
        values.put(KEY_USER_INFO_PHONE, userInfo.getPhone());
        values.put(KEY_USER_INFO_ADDRESS, userInfo.getAddress());
        values.put(KEY_USER_INFO_SEX, userInfo.getSex());
        values.put(KEY_USER_INFO_BIRTHDAY, userInfo.getBirthday());
        values.put(KEY_USER_INFO_HEADIMAGE, userInfo.getHeadimage());
        values.put(KEY_USER_INFO_INVITE_CODE, userInfo.getInviteCode());
        values.put(KEY_USER_INFO_SIGN, userInfo.getSign());
        values.put(KEY_USER_INFO_CASHPOINT, userInfo.getCashpoint());
        values.put(KEY_USER_INFO_SCORE, userInfo.getScore());
        values.put(KEY_USER_INFO_VIP_TYPE, userInfo.getVipType());
        values.put(KEY_USER_INFO_AVAILABLE_MONEY, userInfo.getAvailableMoney());
        values.put(KEY_USER_INFO_UNAVAILABLE_MONEY, userInfo.getUnavailableMoney());
        values.put(KEY_USER_INFO_FREE_COUNT, userInfo.getFreeCount());
        values.put(KEY_USER_INFO_SELLER_ID, userInfo.getSellerId());
        values.put(KEY_USER_INFO_BINDING, userInfo.getBinding());
        values.put(KEY_USER_INFO_TOKEN, userInfo.getToken());
        values.put(KEY_USER_INFO_USERHEAD, userInfo.getUserHead());

        ContextParameter.setUserInfo(userInfo);

        SPUtils.clear(ArchitectureAppliation.getAppliation().getApplicationContext(), FILE_USER_INFO);
        SPUtils.puts(ArchitectureAppliation.getAppliation().getApplicationContext(), FILE_USER_INFO, values);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static UserInfo getUserInfo(){
        UserInfo userInfo = new UserInfo();

        Map<String, ?> values = SPUtils.getAll(ArchitectureAppliation.getAppliation().getApplicationContext(), FILE_USER_INFO);
        userInfo.setUserId(SPUtils.getValueByMapToString(values,KEY_USER_INFO_USER_ID, null));
        userInfo.setUsername(SPUtils.getValueByMapToString(values,KEY_USER_INFO_USERNAME, null));
        userInfo.setRealname(SPUtils.getValueByMapToString(values,KEY_USER_INFO_REALNAME, null));
        userInfo.setPhone(SPUtils.getValueByMapToString(values,KEY_USER_INFO_PHONE, null) );
        userInfo.setAddress(SPUtils.getValueByMapToString(values,KEY_USER_INFO_ADDRESS, null) );
        userInfo.setSex(SPUtils.getValueByMapToString(values,KEY_USER_INFO_SEX, null));
        userInfo.setBirthday(SPUtils.getValueByMapToString(values,KEY_USER_INFO_BIRTHDAY, null));
        userInfo.setHeadimage(SPUtils.getValueByMapToString(values,KEY_USER_INFO_HEADIMAGE, null));
        userInfo.setInviteCode(SPUtils.getValueByMapToString(values,KEY_USER_INFO_INVITE_CODE, null));
        userInfo.setSign(SPUtils.getValueByMapToString(values,KEY_USER_INFO_SIGN, null));
        userInfo.setCashpoint(SPUtils.getValueByMapToString(values,KEY_USER_INFO_CASHPOINT, null));
        userInfo.setScore(SPUtils.getValueByMapToString(values,KEY_USER_INFO_SCORE, null));
        userInfo.setVipType(SPUtils.getValueByMapToString(values,KEY_USER_INFO_VIP_TYPE, null));
        userInfo.setAvailableMoney(SPUtils.getValueByMapToString(values,KEY_USER_INFO_AVAILABLE_MONEY, null));
        userInfo.setUnavailableMoney(SPUtils.getValueByMapToString(values,KEY_USER_INFO_UNAVAILABLE_MONEY, null));
        userInfo.setFreeCount(SPUtils.getValueByMapToString(values,KEY_USER_INFO_FREE_COUNT, null));
        userInfo.setSellerId(SPUtils.getValueByMapToString(values,KEY_USER_INFO_SELLER_ID, null));
        userInfo.setBinding(SPUtils.getValueByMapToString(values,KEY_USER_INFO_BINDING, null));
        userInfo.setToken(SPUtils.getValueByMapToString(values,KEY_USER_INFO_TOKEN, null));
        userInfo.setUserHead(SPUtils.getValueByMapToString(values,KEY_USER_INFO_USERHEAD, null));

        return  userInfo;
    }
}
