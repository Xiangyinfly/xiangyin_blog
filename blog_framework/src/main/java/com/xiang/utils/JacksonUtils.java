package com.xiang.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtils {
    public static String toJsonString(Object o){
        String value = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            value = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
