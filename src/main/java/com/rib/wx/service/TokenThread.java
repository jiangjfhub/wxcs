package com.rib.wx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rib.wx.entity.AccessToken;
import com.rib.wx.utils.Configure;
import com.rib.wx.utils.WeixinUtil;

public class TokenThread implements Runnable {  
        private static Logger log = LoggerFactory.getLogger(TokenThread.class);  
          
        //第三方用户唯一凭证  
        public static String appid = Configure.getAppID();  
        //第三方用户唯一凭证密匙  
        public static String appsecret = Configure.getSecret();  
          
        public static AccessToken accessToken = null;  
        public static String jsapi_ticket=null;  
          
          
        public void run() {  
            // TODO Auto-generated method stub  
            while(true){  
                try{  
                    accessToken = WeixinUtil.getAccessToken(appid, appsecret);  
                    //获取JSAPI_Ticket  
                    jsapi_ticket = WeixinUtil.JSApiTIcket(accessToken.getToken());  
                      
                    if(null != accessToken){  
                        log.info("获取access_token成功，有效时长{}秒 token:{}",accessToken.getExpiresIn(),accessToken.getToken());  
                        log.info("获取jsapi_ticket成功， jsapi_ticket:{}",jsapi_ticket);  
                        //休眠700秒  
                        Thread.sleep((accessToken.getExpiresIn()-200)*1000);  
                    }  
                    else{  
                        //如果access_token未null，60秒后在获取  
                        Thread.sleep(60*1000);  
                    }  
                }catch(InterruptedException e){  
                    try{  
                        Thread.sleep(60*1000);  
                    }catch(InterruptedException e1){  
                        log.error("{}",e1);  
                    }  
                    log.error("{}",e);  
                }  
            }  
        }  
        public static void main(String[] args){  
            System.out.println(TokenThread.accessToken.getToken());  
        }  
    }  

