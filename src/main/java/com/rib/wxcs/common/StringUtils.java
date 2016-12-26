package com.rib.wxcs.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils
{

    public StringUtils()
    {
    }

    public static String escape(String src)
    {
        StringBuffer result = new StringBuffer();
        result.ensureCapacity(src.length() * 6);
        for(int i = 0; i < src.length(); i++)
        {
            char j = src.charAt(i);
            if(Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
            {
                result.append(j);
                continue;
            }
            if(j < '\u0100')
            {
                result.append("%");
                if(j < '\020')
                    result.append("0");
                result.append(Integer.toString(j, 16));
            } else
            {
                result.append("%u");
                result.append(Integer.toString(j, 16));
            }
        }

        return result.toString();
    }

    public static String unescape(String src)
    {
        StringBuffer result = new StringBuffer();
        result.ensureCapacity(src.length());
        int lastPos = 0;
        int pos = 0;
        while(lastPos < src.length()) 
        {
            pos = src.indexOf("%", lastPos);
            if(pos == lastPos)
            {
                if(src.charAt(pos + 1) == 'u')
                {
                    char ch = (char)Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    result.append(ch);
                    lastPos = pos + 6;
                } else
                {
                    char ch = (char)Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    result.append(ch);
                    lastPos = pos + 3;
                }
            } else
            if(pos == -1)
            {
                result.append(src.substring(lastPos));
                lastPos = src.length();
            } else
            {
                result.append(src.substring(lastPos, pos));
                lastPos = pos;
            }
        }
        return result.toString();
    }

    public static String replaceBlank(String str)
    {
        String dest = "";
        if(str != null)
        {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String truncate(String str, int length)
    {
        if(length <= str.getBytes().length)
        {
            for(int i = 0; i < length; i++)
            {
                String temp = str.substring(i, i + 1);
                if(temp.getBytes().length == 2)
                    length--;
            }

        } else
        {
            length = str.length();
        }
        return str.substring(0, length);
    }

    public static String filling(String src, int length, int mode, String chr)
    {
        StringBuffer result = new StringBuffer(src);
        if(mode == 1)
            result = (new StringBuffer(repeatChar(chr, length - result.length()))).append(result);
        else
        if(mode == 2)
            result.append(repeatChar(chr, length - result.length()));
        return result.toString();
    }

    public static String repeatChar(String chr, int length)
    {
        if(isBlank(chr))
            return "";
        StringBuffer result;
        for(result = new StringBuffer(chr); result.length() < length; result.append(chr));
        return result.toString();
    }

    public static final int FILLING_MODE_LEFT = 1;
    public static final int FILLING_MODE_RIGHT = 2;
}


/*
        DECOMPILATION REPORT

        Decompiled from: C:\Users\Matt\.m2\repository\com\rib\0.1.7\rib-0.1.7.jar
        Total time: 73 ms
        Jad reported messages/errors:
        Exit status: 0
        Caught exceptions:
*/
