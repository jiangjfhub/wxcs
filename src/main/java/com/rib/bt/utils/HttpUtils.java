package com.rib.bt.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

	public static String httpRequestByConnection(String url, String param) {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果
		try {
			// 创建url
			URL realurl = new URL(url + File.pathSeparator + param);
			// 打开连接
			URLConnection connection = realurl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;qa=0.8,en-US;q=0.5,en;q=0.3");
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 建立连接
			connection.connect();

			InputStream instreams = connection.getInputStream();
			result = convertStreamToString(instreams);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return result;
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

	public static void saveToFile(String str, String path) throws IOException {
		FileWriter fw = new FileWriter(path);
		fw.write(str);
		fw.flush();
		fw.close();
	}

	public static Document requstGetByJsoup(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).header("Accept", "*/*")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0").timeout(10000).get();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return doc;
	}
	
	public static Document requstPostByJsoup(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect("url").header("Accept", "*/*")
					.header("Accept-Language", "zh-CN,zh;qa=0.8,en-US;q=0.5,en;q=0.3")
					.userAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)").post();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return doc;
	}
}
