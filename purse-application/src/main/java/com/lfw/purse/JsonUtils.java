package com.lfw.purse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JSON处理工具
 */
public class JsonUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private final static ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 将对象转换为字符串
     *
     * @param obj 对象
     * @return json字符串
     */
    public static String toJsonString(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("JSON string generating error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将JSON字符串转换为实际的对象
     *
     * @param json  对象的JSON字符串
     * @param clazz json字符串对应的真实对象类型
     * @param <T>
     * @return
     */
    public static <T> T parseJsonString(@Nullable String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            LOGGER.error("JSON string parsing error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }


    public static <T> List<T> parseListJsonString(@Nullable String json, Class<T> elementClass) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JavaType jt = MAPPER.getTypeFactory().constructParametricType(ArrayList.class, elementClass);
            return MAPPER.readValue(json, jt);
        } catch (Exception e) {
            LOGGER.error("JSON string parsing error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> T convertValue(Object obj, TypeReference<T> toValueTypeRef) {
        return Optional.ofNullable(obj).map(o -> {
            try {
                return MAPPER.convertValue(obj, toValueTypeRef);
            } catch (Exception e) {
                LOGGER.error("convertValue:: JSON string parsing errorL obj:{},clazz:{}", obj, toValueTypeRef, e.getStackTrace());
                throw new RuntimeException(e.getMessage());
            }
        }).orElse(null);
    }

    public static <T> T convertValue(Object obj, Class<T> toValueType) {
        return Optional.ofNullable(obj).map(o -> {
            try {
                return MAPPER.convertValue(obj, toValueType);
            } catch (Exception e) {
                LOGGER.error("convertValue:: JSON string parsing errorL obj:{},clazz:{}", obj, toValueType, e.getStackTrace());
                throw new RuntimeException(e.getMessage());
            }
        }).orElse(null);
    }
}
