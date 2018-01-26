package com.weslide.lovesmallscreen.model_yy.javabean;

/**
 * Created by YY on 2018/1/25.
 */
public class PostExchangeModel extends BaseModel {

    //换货方式(物物换/积分换)
    private int changeType;

    //积分数
    private int changeScore;

    //甲方用户id(甲方代表自己)
    private String inviteUserId;

    //甲方商品id
    private String inviteGoodsId;

    //甲方商品数量
    private int inviteGoodsNum;

    //乙方用户id
    private String acceptUserId;

    //乙方商品id
    private String acceptGoodsId;

    //乙方商品数量
    private int acceptGoodsNum;

    //甲方商品规格id
    private int inviteGoodStandardId;

    //乙方商品规格id
    private int acceptGoodStandardId;

    public int getChangeType() {
        return changeType;
    }

    public void setChangeType(int changeType) {
        this.changeType = changeType;
    }

    public int getChangeScore() {
        return changeScore;
    }

    public void setChangeScore(int changeScore) {
        this.changeScore = changeScore;
    }

    public String getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(String inviteUserId) {
        this.inviteUserId = inviteUserId;
    }

    public String getInviteGoodsId() {
        return inviteGoodsId;
    }

    public void setInviteGoodsId(String inviteGoodsId) {
        this.inviteGoodsId = inviteGoodsId;
    }

    public int getInviteGoodsNum() {
        return inviteGoodsNum;
    }

    public void setInviteGoodsNum(int inviteGoodsNum) {
        this.inviteGoodsNum = inviteGoodsNum;
    }

    public String getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public String getAcceptGoodsId() {
        return acceptGoodsId;
    }

    public void setAcceptGoodsId(String acceptGoodsId) {
        this.acceptGoodsId = acceptGoodsId;
    }

    public int getAcceptGoodsNum() {
        return acceptGoodsNum;
    }

    public void setAcceptGoodsNum(int acceptGoodsNum) {
        this.acceptGoodsNum = acceptGoodsNum;
    }

    public int getInviteGoodStandardId() {
        return inviteGoodStandardId;
    }

    public void setInviteGoodStandardId(int inviteGoodStandardId) {
        this.inviteGoodStandardId = inviteGoodStandardId;
    }

    public int getAcceptGoodStandardId() {
        return acceptGoodStandardId;
    }

    public void setAcceptGoodStandardId(int acceptGoodStandardId) {
        this.acceptGoodStandardId = acceptGoodStandardId;
    }
}
