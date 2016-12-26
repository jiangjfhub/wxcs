package com.rib.wxcs.common;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils
{

    public JsonUtils()
    {
    }

    public static String toJson(Object obj, Gson gson)
    {
        return gson.toJson(obj);
    }

    public static String toJson(Object obj)
        throws Exception
    {
        return toJson(obj, gson);
    }

    public static String toJsonByExpose(Object obj)
        throws Exception
    {
        return toJson(obj, gsonByExpose);
    }

    public static Object fromJson(String json, Class clazz, Gson gson)
        throws Exception
    {
        if(json.startsWith("["))
            return gson.fromJson(json, getType(clazz));
        else
            return gson.fromJson(json, clazz);
    }

    public static Object fromJson(String json, Class clazz)
        throws Exception
    {
        return fromJson(json, clazz, gson);
    }

    public static Object fromJsonByExpose(String json, Class clazz)
        throws Exception
    {
        return fromJson(json, clazz, gsonByExpose);
    }

    private static Type getType(Class clazz)
        throws Exception
    {
        return com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, List.class, new Type[] {
            clazz
        });
    }

    public static String formatJson(String json)
    {
        Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        com.google.gson.JsonElement je = jp.parse(json);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }

    private static Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private static Gson gsonByExpose = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

}


/*
        DECOMPILATION REPORT

        Decompiled from: C:\Users\Matt\.m2\repository\com\rib\0.1.7\rib-0.1.7.jar
        Total time: 30 ms
        Jad reported messages/errors:
        Exit status: 0
        Caught exceptions:
*/