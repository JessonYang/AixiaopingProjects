package com.weslide.lovesmallscreen.managers.pay;


/**
 * 支付完成事件
 * @author xu
 *
 */
public interface PayListener {
	/**
	 * 支付成功
	 */
	public void onSuccess();
	
	/**
	 * 支付失败
	 */
	public void onDefeated();

	/**
	 * 支付取消
	 */
	public void onCancel();
}
