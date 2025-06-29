package com.mundane.mail.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import java.util.List;

public class JsonUtils {
    public static <T> List<T> toList(String str, Class<T> elementType) {
        JSONArray jsonArray = JSONUtil.parseArray(str);
        List<T> list = JSONUtil.toList(jsonArray, elementType);
        return list;
    }
}
