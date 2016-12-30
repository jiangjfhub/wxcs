package com.rib.wxcs.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Main {

    public static void main(String[] args) throws IOException  {
        test2();
    }
    
    public static String convertStreamToString(InputStream is) {      
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
        StringBuilder sb = new StringBuilder();      
       
        String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {  
                sb.append(line + "\n");      
            }      
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        }      
        return sb.toString();      
    }  
    
    public  static void saveToFile(String fileName, InputStream in) throws IOException { 
        FileOutputStream fos = null;    
        BufferedInputStream bis = null;    
//              HttpURLConnection httpUrl = null;    
//              URL url = null;    
        int BUFFER_SIZE = 1024; 
        byte[] buf = new byte[BUFFER_SIZE];    
        int size = 0;    
//                  
//              //建立链接    
//              url = new URL(destUrl);    
//              httpUrl = (HttpURLConnection) url.openConnection();    
//              //连接指定的资源    
//              httpUrl.connect();    
//              //获取网络输入流    
        bis = new BufferedInputStream(in);    
//              //建立文件    
        fos = new FileOutputStream(fileName);    
//             
//              //保存文件    
        while ( (size = bis.read(buf)) != -1)     
          fos.write(buf, 0, size);    
//                  
        fos.close();    
        bis.close();    
//              httpUrl.disconnect();    
      }
    public static void test1() throws ClientProtocolException, IOException{
        String url = "https://www.seedmm.com";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response1 = httpClient.execute(httpGet);
        
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity = response1.getEntity();
            if (entity != null) {    
                InputStream instreams = entity.getContent();    
                //saveToFile("D://1.html", instreams);
                String str = convertStreamToString(instreams);  
                System.out.println("Do something");   
                FileWriter fw=new FileWriter("D:\\mm.txt");
                fw.write(str);
                fw.flush();
                fw.close();
                System.out.println(str);  
                
                // Do not need the rest    
                httpGet.abort();    
            }  
        } finally {
            httpGet.releaseConnection();
        }
    }
    
    public static void test2() {
        String url="http://www.baidu.com";
        String result="";//访问返回结果
        BufferedReader read=null;//读取访问结果
        String param="";
         
        try {
         //创建url
         URL realurl=new URL(url+"?"+param);
         //打开连接
         URLConnection connection=realurl.openConnection();
          // 设置通用的请求属性
                  connection.setRequestProperty("accept", "*/*");
                  connection.setRequestProperty("connection", "Keep-Alive");
                  connection.setRequestProperty("user-agent",
                          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                  //建立连接
                  connection.connect();
               // 获取所有响应头字段
                  Map<String, List<String>> map = connection.getHeaderFields();
                  InputStream instreams=connection.getInputStream();
                  String str = convertStreamToString(instreams); 
                  // 遍历所有的响应头字段，获取到cookies等
//                  for (String key : map.keySet()) {
//                      System.out.println(key + "--->" + map.get(key));
//                  }
//                  // 定义 BufferedReader输入流来读取URL的响应
//                  read = new BufferedReader(new InputStreamReader(
//                          connection.getInputStream(),"UTF-8"));
//                  String line;//循环读取
//                  while ((line = read.readLine()) != null) {
//                      result += line;
//                  }
                  System.out.print(str); 
        } catch (IOException e) {
         e.printStackTrace();
        }finally{
         if(read!=null){//关闭流
          try {
           read.close();
          } catch (IOException e) {
           e.printStackTrace();
          }
         }
        }
        
    }
}
