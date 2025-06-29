package com.mundane.mail.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class RedBookUtils {

    public static String getTagList(String tags) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(tags)) {
            JSONArray objects = JSONUtil.parseArray(tags);
            List<String> list = JSONUtil.toList(objects, String.class);
            for (String tag : list) {
                sb.append(tag).append(" ");
            }
        }
        return sb.toString();
    }
}
