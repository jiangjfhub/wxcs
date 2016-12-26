package com.rib.wxcs.geetest;

/**
 * GeetestWeb配置文件
 * 
 *
 */
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
	private static final String geetest_id = "5172a877f3367ee2577e078d832b9925";
	private static final String geetest_key = "0e8002afb8f40d43f514903fc7a22476";

	public static final String getGeetest_id() {
		return geetest_id;
	}

	public static final String getGeetest_key() {
		return geetest_key;
	}

}
