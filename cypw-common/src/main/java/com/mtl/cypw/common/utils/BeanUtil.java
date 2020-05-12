package com.mtl.cypw.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tang.
 * @date 2019/10/14.
 */
@Slf4j
public class BeanUtil {

    private static final String JAVAP = "java.";
    private static final String JAVADATESTR = "java.util.Date";

    public static Map<String, Object> converterMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value == null ? "" : value);
                }
            }
        } catch (Exception e) {
            log.error("converterMap error,{}", e.getMessage());
        }
        return map;
    }

    public static Map<String, String> converterMapToString(Object obj) {
        return converterMapToString(null, obj, new String[]{});
    }

    /**
     * 利用递归调用将Object中的值全部进行获取
     *
     * @param timeFormatStr 格式化时间字符串默认<strong>yyyy-MM-dd HH:mm:ss</strong>
     * @param obj           对象
     * @param excludeFields 排除的属性
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, String> converterMapToString(String timeFormatStr, Object obj, String... excludeFields) {
        Map<String, String> map = new HashMap<>();
        try {
            if (excludeFields.length != 0) {
                List<String> list = Arrays.asList(excludeFields);
                objectTransfer(timeFormatStr, obj, map, list);
            } else {
                objectTransfer(timeFormatStr, obj, map, null);
            }
        } catch (Exception e) {
            log.error("converterMapToString error,{}", e.getMessage());
        }
        return map;
    }

    /**
     * 递归调用函数
     *
     * @param obj           对象
     * @param map           map
     * @param excludeFields 对应参数
     * @return
     * @throws IllegalAccessException
     */
    private static Map<String, String> objectTransfer(String timeFormatStr, Object obj, Map<String, String> map, List<String> excludeFields) throws IllegalAccessException {
        boolean isExclude = false;
        //默认字符串
        String formatStr = "YYYY-MM-dd HH:mm:ss";
        //设置格式化字符串
        if (timeFormatStr != null && !timeFormatStr.isEmpty()) {
            formatStr = timeFormatStr;
        }
        if (excludeFields != null) {
            isExclude = true;
        }
        Class<?> clazz = obj.getClass();
        //获取值
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            //判断是不是需要跳过某个属性
            if (isExclude && excludeFields.contains(fieldName)) {
                continue;
            }
            //设置属性可以被访问
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value == null) {
                continue;
            }
            Class<?> valueClass = value.getClass();
            if (valueClass.isPrimitive()) {
                map.put(fieldName, value.toString());
            } else if (valueClass.getName().contains(JAVAP)) {
                if (valueClass.getName().equals(JAVADATESTR)) {
                    //格式化Date类型
                    SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
                    Date date = (Date) value;
                    String dataStr = sdf.format(date);
                    map.put(fieldName, dataStr);
                } else {
                    map.put(fieldName, value.toString());
                }
            } else {
                objectTransfer(timeFormatStr, value, map, excludeFields);
            }
        }
        return map;
    }
}
