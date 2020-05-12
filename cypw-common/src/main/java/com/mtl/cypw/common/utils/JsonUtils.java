package com.mtl.cypw.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-13 19:21
 */

public abstract class JsonUtils {

    /**
     * object to json
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  json to object
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  json to List<T>
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSONObject.parseArray(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

}
