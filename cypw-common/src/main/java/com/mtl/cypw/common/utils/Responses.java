package com.mtl.cypw.common.utils;

import com.mtl.cypw.common.enums.ResponseCode;
import com.mtl.cypw.common.dto.info.Response;
import com.mtl.cypw.common.enums.ErrorCode;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-13 17:21
 */
public abstract class Responses {

    public static <T> Response<T> of(T data) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMsg(ResponseCode.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }

    public static <T> Response<T> of(T data, String msg) {
        Response<T> response = of(data);
        response.setMsg(msg);
        return response;
    }

    public static <T> Response<T> fail(ErrorCode errorCode) {
        if (errorCode.getCode() == ResponseCode.SUCCESS.getCode()) {
            throw new IllegalArgumentException("Invalid code.");
        }
        Response<T> response = new Response<>();
        response.setCode(errorCode.getCode());
        response.setMsg(errorCode.getMsg());
        return response;
    }

    public static <T> Response<T> fail(ResponseCode responseCode) {
        if (responseCode.getCode() == ResponseCode.SUCCESS.getCode()) {
            throw new IllegalArgumentException("Invalid code.");
        }
        Response<T> response = new Response<>();
        response.setCode(responseCode.getCode());
        response.setMsg(responseCode.getMsg());
        return response;
    }

    public static <T> Response<T> restrictedRequest() {
        return fail(ResponseCode.RESTRICTED);
    }

    public static <T> Response<T> unsupportedRequest() {
        return fail(ResponseCode.UNSUPPORTED);
    }

    public static <T> Response<T> requestTimeout() {
        return fail(ResponseCode.TIMEOUT);
    }
}
