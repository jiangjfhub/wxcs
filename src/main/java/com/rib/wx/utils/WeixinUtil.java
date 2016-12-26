package com.rib.wx.utils;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rib.wx.entity.AccessToken;
import com.rib.wx.service.TokenThread;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class WeixinUtil {  
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
      
    /** 
     * 发起https请求并获取结果 
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（get、post） 
     * @param outputstr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)  
     */  
    @SuppressWarnings("deprecation")  
    public static JSONObject httpRequest(String requestUrl,String requestMethod,String outputstr){  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try{  
                        
            URL url=new URL(requestUrl);  
            HttpURLConnection  httpUrlConn = (HttpURLConnection ) url.openConnection();  
            httpUrlConn.setRequestMethod("GET"); // 必须是get方式请求  
            httpUrlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
              
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
              
            //设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
          
            if("GET".equalsIgnoreCase(requestMethod)){  
                httpUrlConn.connect();  
            }  
              
            //当有数据提交时  
            if(null != outputstr){  
                OutputStream outputStream =  httpUrlConn.getOutputStream();  
                //注意编码格式，防止中文乱码  
                outputStream.write(outputstr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
              
            // 将返回的输入流转换成字符串    
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader  = new InputStreamReader(inputStream,"UTF-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
              
            String str = null;  
            while((str = bufferedReader.readLine()) != null){  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            //释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        }catch(ConnectException e){  
            log.error("Weixin server connection timed out.");  
        }catch(Exception e){  
            log.error("https request error:{}",e);  
        }  
        return jsonObject;  
    }  
      
    // 获取access_token的接口地址（GET） 限200（次/天）    
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";  
      
      
    /** 
     * 获取accessToekn 
     * @param appid 凭证 
     * @param appsecret 密匙  
     * @return 
     */  
    public static AccessToken getAccessToken(String appid, String appsecret) {    
        AccessToken accessToken = null;    
        
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);    
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);    
        // 如果请求成功    
        if (null != jsonObject) {    
            try {    
                accessToken = new AccessToken();    
                accessToken.setToken(jsonObject.getString("access_token"));    
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));    
            } catch (JSONException e) {    
                accessToken = null;    
                // 获取token失败    
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));    
            }    
        }    
        return accessToken;    
    }    
      
      
    //获取JSAPI_Ticket   
    public static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";  
    /** 
     * 获取jsapi_ticket 
     * @param accessToken 
     * @return 
     */  
    public static String JSApiTIcket(String accessToken){  
        int result = 0;  
        String jsApiTicket = null;  
        //拼装创建菜单Url  
        String url =  jsapi_ticket_url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());  
        //调用接口获取jsapi_ticket  
        JSONObject jsonObject = httpRequest(url, "GET", null);  
        // 如果请求成功    
        if (null != jsonObject) {    
            try {    
                jsApiTicket = jsonObject.getString("ticket");  
            } catch (JSONException e) {    
                 if (0 != jsonObject.getInt("errcode")) {    
                    result = jsonObject.getInt("errcode");    
                    log.error("JSAPI_Ticket获取失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));    
                }    
            }    
        }    
        return jsApiTicket;  
    }  
      
      
  
    
    /** 
     * sha1加密 
     * @param str 
     * @return 
     */  
    public static String sha1Encrypt(String str){    
        String signature = null;  
        try  
        {  
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");  
            crypt.reset();  
            crypt.update(str.getBytes("UTF-8"));  
            signature = byteToHex(crypt.digest());  
        }  
        catch (NoSuchAlgorithmException e)  
        {  
            e.printStackTrace();  
        }  
        catch (UnsupportedEncodingException e)  
        {  
            e.printStackTrace();  
        }  
        return signature;  
    }    
      
    private static String byteToHex(final byte[] hash) {  
        Formatter formatter = new Formatter();  
        for (byte b : hash)  
        {  
            formatter.format("%02x", b);  
        }  
        String result = formatter.toString();  
        formatter.close();  
        return result;  
    }  
}  