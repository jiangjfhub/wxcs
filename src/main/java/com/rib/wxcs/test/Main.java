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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;

public class Main {

    public static void main(String[] args) throws IOException  {
        test6();
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
                FileWriter fw=new FileWriter("E:\\mm.txt");
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
        String url="https://btso.pw/search/abp-172";
        String result="";//访问返回结果
        BufferedReader read=null;//读取访问结果
        String param="";
         
        try {
         //创建url
         URL realurl=new URL(url+"?"+param);
         //打开连接
         URLConnection connection=realurl.openConnection();
          // 设置通用的请求属性
                  connection.setRequestProperty("Accept", "*/*");
                 connection.setRequestProperty("Accept-Language", "zh-CN,zh;qa=0.8,en-US;q=0.5,en;q=0.3");
                  connection.setRequestProperty("User-Agent",
                          "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
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
                  System.out.println("Do something");   
                  FileWriter fw=new FileWriter("E:\\mm1.txt");
                  fw.write(str);
                  fw.flush();
                  fw.close();
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
    public static void test3() throws IOException{
    	Document doc = Jsoup.connect("https://www.seedmm.com/abp-171").header("Accept", "*/*").header("Accept-Language", "zh-CN,zh;qa=0.8,en-US;q=0.5,en;q=0.3")
    			  .userAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)").get();// 设置 User-Agent 
    	FileWriter fw=new FileWriter("E:\\mm1.txt");
        fw.write(doc.toString());
        fw.flush();
        fw.close();
    	
    	Elements el=doc.getElementsByClass("col-md-3 info");
    	
    	Elements els =el.select("p");
    	els.get(1).select("span").remove();
    	String releaseDate = els.get(1).text();
    	String btCode =els.get(0).child(1).text();
    	Elements ei = doc.getElementsByClass("bigImage");
    	String img = ei.select("img").attr("src");
    	String btName = ei.select("img").attr("title");
    	String actorName = doc.getElementsByClass("star-name").text();
    	System.out.println(el.toString());
    }
    	
    	public static void test4() throws IOException{
    		Map<String,String> headers =new HashMap<String,String>();
    		headers.put("Connection", "keep-alive");
    		headers.put("Cookie", "__test; _ga=GA1.2.1913260682.1483423739; __PPU_SESSION_1_470916_false=1483705135345|1|1483705135345|1|1; _gat=1; AD_enterTime=1483705122; AD_adca_b_SM_T_728x90=1; AD_adst_b_SM_T_728x90=1; AD_jav_b_SM_T_728x90=0; AD_javu_b_SM_T_728x90=0; AD_wav_b_SM_T_728x90=0; AD_wwwp_b_SM_T_728x90=0; AD_clic_b_POPUNDER=2; __test; AD_javu_b_SM_B_728x90=1");
    	Document doc = Jsoup.connect("https://btso.pw/search/abp-178").header("Accept", "*/*").header("Accept-Language", "zh-CN,zh;qa=0.8,en-US;q=0.5,en;q=0.3")
    			  .userAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)").headers(headers).get();// 设置 User-Agent 
    	Elements els=doc.getElementsByClass("row");
    	List<String> urls = Lists.newArrayList();
    	for (Element element : els) {
			Pattern p = Pattern.compile("https://btso.pw/magnet/detail/hash/.[a-zA-Z0-9]*");
			Matcher m=p.matcher(element.toString());  
			while(m.find()){  
				urls.add(m.group());
				}  
		}
    	for (String url : urls) {
    		Document docu = Jsoup.connect(url).header("Accept", "*/*").header("Accept-Language", "zh-CN,zh;qa=0.8,en-US;q=0.5,en;q=0.3")
      			  .userAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)").get();
    		
    	 String magnetLink = docu.getElementById("magnetLink").text();
    	 String link_name = docu.select("h3").get(0).text();
    	 String href_link = url;
    	 String size = docu.getElementsByClass("col-md-10 col-sm-9 value").get(1).text();
    	 String convertDate = docu.getElementsByClass("col-md-10 col-sm-9 value").get(2).text();
    	 System.out.println(convertDate);
    	}
    	}
    	
    	
    	public static void test5() throws IOException{
    		String url="https://btso.pw/search/abp-172";
            String result="";//访问返回结果
            BufferedReader read=null;//读取访问结果
            String param="";
             
            try {
             //创建url
             URL realurl=new URL(url+"?"+param);
             //打开连接
             URLConnection connection=realurl.openConnection();
              // 设置通用的请求属性
                      connection.setRequestProperty("Accept", "*/*");
                     connection.setRequestProperty("Accept-Language", "zh-CN,zh;qa=0.8,en-US;q=0.5,en;q=0.3");
                      connection.setRequestProperty("User-Agent",
                              "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                      //建立连接
                      connection.connect();
                   // 获取所有响应头字段
                      Map<String, List<String>> map = connection.getHeaderFields();
                      InputStream instreams=connection.getInputStream();
                      String str = convertStreamToString(instreams); 
                      Document doc =Jsoup.parse(str);
                      Elements els=doc.getElementsByClass("row");
                  	List<String> urls = Lists.newArrayList();
                  	for (Element element : els) {
              			Pattern p = Pattern.compile("https://btso.pw/magnet/detail/hash/.[a-zA-Z0-9]*");
              			Matcher m=p.matcher(element.toString());  
              			while(m.find()){  
              				urls.add(m.group());
              				}  
              		}
                  	for (String url1 : urls) {
                  		Document docu = Jsoup.connect(url1).header("Accept", "*/*").header("Accept-Language", "zh-CN,zh;qa=0.8,en-US;q=0.5,en;q=0.3")
                    			  .userAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)").get();
                  		
                  	 String magnetLink = docu.getElementById("magnetLink").text();
                  	 String link_name = docu.select("h3").get(0).text();
                  	 String href_link = url1;
                  	 String size = docu.getElementsByClass("col-md-10 col-sm-9 value").get(1).text();
                  	 String convertDate = docu.getElementsByClass("col-md-10 col-sm-9 value").get(2).text();
                  	 System.out.println(convertDate);
                  	}
            }catch (IOException e) {
                e.printStackTrace();
            }
    	}
    	public static void test6() throws IOException{
        	Document doc = Jsoup.connect("https://www.seedmm.com/ajax/uncledatoolsbyajax.php?gid=25121613686&lang=zh&img=https://pics.javbus.info/cover/4d1s_b.jpg&uc=0&floor=721")
        			.header("Accept", "*/*")
        			.header("Accept-Language", "zh-CN,zh;qa=0.8,en-US;q=0.5,en;q=0.3")
        			.header("Accept-Encoding", "gzip, deflate, br")
//        			.header("Cookie","__cfduid=d789673894e50fbbe3e228dba10f3636a1483426435; HstCfa3242405=1483430184590; HstCla3242405=1483713742195; HstCmu3242405=1483430184590; HstPn3242405=1; HstPt3242405=21; HstCnv3242405=5; HstCns3242405=7; PHPSESSID=a6cr01jmdhugsn7slvl9tpq8l0")
//        			.header("Connection", "keep-alive") 
//        			.header("X-Requested-With","XMLHttpRequest")
        			.header("Referer","https://www.seedmm.com/abp-171")
        			.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0").get();// 设置 User-Agent 
        	FileWriter fw=new FileWriter("E:\\mm1.txt");
            fw.write(doc.toString());
            fw.flush();
            fw.close();
        	
        	Elements el=doc.getElementsByClass("col-md-3 info");
        	

        	System.out.println(el.toString());
        }
}
