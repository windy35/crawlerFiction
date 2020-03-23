package com.wen.crawler.Tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatchStr {
    /**
     * patternStr为匹配的正则表达式，str为需要进行匹配的字符串
     */
    public static String getTargetStr(String patternStr,String str){
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(str);
        String rs="";
        while(matcher.find())
            rs+=matcher.group();
        return rs;
    }
}
