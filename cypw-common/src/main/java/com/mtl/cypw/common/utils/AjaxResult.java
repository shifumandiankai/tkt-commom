package com.mtl.cypw.common.utils;

import com.mtl.cypw.common.enums.ErrorCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-13 17:22
 */
@Data
public class AjaxResult<T> implements Serializable {

    private static final int SUCCESS_CODE = 200;
    /**
     * 返回代码
     * @see ErrorCode
     */
    private int code;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 返回记录
     */
    private T data;
    /**
     * 分页信息
     */
    private Paging paging;
    /**
     * 额外信息
     */
    private Map<Object, Object> attrMaps;

    public AjaxResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public AjaxResult(int code, String msg, T data, Paging paging) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.paging = paging;
    }

    public AjaxResult(int code, String msg, T data, Map<Object,Object> attrMaps) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.attrMaps = attrMaps;
    }

    public static <T> AjaxResult<T> createSuccessResult(T data) {
        return new AjaxResult<>(SUCCESS_CODE, StringUtils.EMPTY, data);
    }

    public static <T> AjaxResult<T> createSuccessResult(T data, Map<Object,Object> attrMaps) {
        return new AjaxResult<>(SUCCESS_CODE, StringUtils.EMPTY, data, attrMaps);
    }

    public static <T> AjaxResult<T> createSuccessResult(T data, Paging paging) {
        return new AjaxResult<>(SUCCESS_CODE, StringUtils.EMPTY, data, paging);
    }

    public static<T> AjaxResult<T> createFailedResult(T data, int code, String msg) {
        return new AjaxResult<>(code, msg, data);
    }

    public static<T> AjaxResult<T> createFailedResult(int code, String msg) {
        return new AjaxResult<>(code, msg, null);
    }

    public static<T> AjaxResult<T> createFailedResult(int code, String msg, Map<Object,Object> attrMaps) {
        return new AjaxResult<>(code, msg, null, attrMaps);
    }
}
