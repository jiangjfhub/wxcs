/**
 * @（#）:PropertiesUtils.java
 * @description: 属性文件获取工具类
 * @author: dengrongxi 2016年3月3日
 * @version: Version 1.0
 */
package com.rib.wx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
    private static Properties prop = new Properties();
    static {
        InputStream propIn = PropertiesUtils.class.getResourceAsStream("/config.properties");
        try {
            if(propIn != null) {
                prop.load(propIn);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 根据key获取配置的value字符串值
     * @param key 文件中配置的key
     * @return 获取到的值，如果没有则返回null
     */
    public static String getProp(String key) {
        String value = prop.getProperty(key);
        
        if (value != null) {
            return value.trim();
        }
        return value;
    }

    /**
     * 根据key获取配置的value数字值
     * @param key 文件中配置的key
     * @return 获取到的值，如果没有或者转换出错则返回null
     */
    public static Integer getPropAsInteger(String key) {
        String value = prop.getProperty(key);
        
        if (value != null) {
            try {
                Integer val = Integer.valueOf(value.trim());
                return val;
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                return null;
            }
        }
        return null;
    }
}
