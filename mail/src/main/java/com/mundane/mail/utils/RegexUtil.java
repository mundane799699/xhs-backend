package com.mundane.mail.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {


    public static String parseUrl(String text) {
        String regex = "https?://[\\w./-]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String url = matcher.group();
            return url;
        }
        return null;
    }

    public static String parseByRegex(String text, String regex) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String id = matcher.group();
            return id;
        }
        return null;
    }

    public static String matchTraceId(String url) {
        // 使用正则表达式匹配目标部分
        String pattern = "(?<=\\/)[^\\/]+(?=!)";
        Pattern targetPattern = Pattern.compile(pattern);
        Matcher matcher = targetPattern.matcher(url);

        // 查找匹配的目标部分
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}
