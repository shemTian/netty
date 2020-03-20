package com.tsq.netty.study.util;

import com.google.gson.Gson;

/**
 * 这里添加类描述
 *
 * @author shemtian
 * @version 1.0.0
 * @Date 2020-03-20 15:50
 */
public class JsonUtil {
    private static Gson gson = new Gson();

    private JsonUtil() {
        //no instance
    }

    public static String toJson(Object message) {
        return gson.toJson(message);
    }

    public static <T> T fromJson(String message, Class<T> tClass) {
        return gson.fromJson(message, tClass);
    }
}
