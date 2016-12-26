/**
 * @（#）:ControllerUtils.java
 * @description: 
 * @author: kefei 2015年2月24日
 * @version: Version 1.0
 */
package com.rib.wxcs.utils;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.rib.wxcs.common.JsonUtils;


public class ControllerUtils {

    public static final String RESPONSE_CODE_SUCCESS = "SUCCESS";
    public static final String RESPONSE_CODE_FAILED = "FAILED";

    /**
     * JSON内容输出到mmgrid
     * 
     * @param printWriter
     * @param obj
     * @param logger
     * @param byExpose
     */
    public static void outputGrid(PrintWriter printWriter, long totalCount, Object obj,
            Logger logger, boolean byExpose) {
        try {
            StringBuffer content = new StringBuffer();
            content.append("{\"totalCount\": ");
            content.append(totalCount);
            content.append(", \"items\": ");
            if (byExpose) {
                content.append(JsonUtils.toJsonByExpose(obj));
            } else {
                content.append(JsonUtils.toJson(obj));
            }
            content.append("}");
            output(printWriter, content.toString(), logger);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 将对象输出为JSON内容
     * 
     * @param printWriter
     * @param obj
     * @param logger
     * @param byExpose
     */
    public static void output(PrintWriter printWriter, Object obj, Logger logger, boolean byExpose) {
        try {
            if (byExpose) {
                output(printWriter, JsonUtils.toJsonByExpose(obj), logger);
            } else {
                output(printWriter, JsonUtils.toJson(obj), logger);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 输出文本内容
     * 
     * @param printWriter
     * @param result
     * @param logger
     */
    public static void output(PrintWriter printWriter, String result, Logger logger) {
        try {
            printWriter.write(result);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 创建ajax返回对象
     * 
     * @param returnCode
     * @param returnMessage
     * @return
     */
    public static Map<String, Object> createReturnObject(String returnCode, String returnMessage) {
        return createReturnObject(returnCode, returnMessage, null);
    }

    /**
     * 创建ajax返回对象
     * 
     * @param returnCode
     * @param returnMessage
     * @param returnParam
     * @return
     */
    public static Map<String, Object> createReturnObject(String returnCode, String returnMessage,
            Object returnParam) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("CODE", returnCode);
        result.put("MESSAGE", returnMessage);
        if (returnParam != null) {
            result.put("RETURN_PARAM", returnParam);
        }
        return result;
    }

}
