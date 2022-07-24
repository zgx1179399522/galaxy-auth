package com.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
public abstract class JsonUtils {


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String toJson(Object content) {
        return OBJECT_MAPPER.writeValueAsString(content);
    }

    @SneakyThrows
    public static <T> T fromJson(String json, Class<T> tClass) {
        return OBJECT_MAPPER.readValue(json, tClass);
    }
}
