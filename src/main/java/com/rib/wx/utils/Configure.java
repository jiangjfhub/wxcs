package com.rib.wx.utils;

public class Configure {
    
    //消息加解密密钥
    private static String key = PropertiesUtils.getProp("Key");

    //微信分配的公众号ID（开通公众号之后可以获取到）
    private static String appID = PropertiesUtils.getProp("APPID");
    
    //微信分配的公众号的appsecret （开通公众号之后可以获取到）
    private static String secret = PropertiesUtils.getProp("AppSecret");

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        Configure.key = key;
    }

    public static String getAppID() {
        return appID;
    }

    public static void setAppID(String appID) {
        Configure.appID = appID;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        Configure.secret = secret;
    }
}
