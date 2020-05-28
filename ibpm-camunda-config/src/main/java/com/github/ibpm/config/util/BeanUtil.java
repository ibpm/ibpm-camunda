package com.github.ibpm.config.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.config.bean.SpringContextAware;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BeanUtil {

    public static Map<String, Object> bean2Map(Object obj) {
        if (obj == null) {
            return new HashMap<>();
        }
        try {
            return injectObjectMapper().readValue(obj instanceof String ? (String) obj : bean2JsonStr(obj), Map.class);
        } catch (IOException e) {
            throw new RTException(1400, e);
        }
    }

    public static <T> T map2Bean (Class<T> t, Map<String, Object> map) {
        try {
            return injectObjectMapper().readValue(bean2JsonStr(map), t);
        } catch (IOException e) {
            throw new RTException(1400, e);
        }
    }

    public static <T> T jsonStr2Bean (String jsonStr, Class<T> t) {
        try {
            return injectObjectMapper().readValue(jsonStr, t);
        } catch (IOException e) {
            throw new RTException(1400, e);
        }
    }

    public static String bean2JsonStr(Object obj) {
        try {
            return injectObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RTException(1400, e);
        }
    }

    private static ObjectMapper injectObjectMapper() {
        return SpringContextAware.getBean(ObjectMapper.class);
    }
}
