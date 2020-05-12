package com.mtl.cypw.common.utils;

import com.mtl.cypw.common.enums.ResponseCode;
import com.mtl.cypw.common.dto.info.PagedResponse;
import com.mtl.cypw.common.enums.ErrorCode;

import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-13 17:21
 */
public abstract class PagedResponses {

    public static <T> PagedResponse<T> of(List<T> records, int totalHits, boolean hasNext) {
        PagedResponse<T> response = new PagedResponse<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMsg(ResponseCode.SUCCESS.getMsg());
        response.setData(records);
        response.setTotalHits(totalHits);
        response.setHasNext(hasNext);
        return response;
    }

    public static <T> PagedResponse<T> of(List<T> records, int totalHits) {
        return of(records, totalHits, false);
    }

    public static <T> PagedResponse<T> fail(ErrorCode errorCode) {
        if (errorCode.getCode() == ResponseCode.SUCCESS.getCode()) {
            throw new IllegalArgumentException("Invalid Code.");
        }
        PagedResponse<T> response = new PagedResponse<>();
        response.setCode(errorCode.getCode());
        response.setMsg(errorCode.getMsg());
        return response;
    }

    public static <T> PagedResponse<T> fail(ResponseCode responseCode) {
        if (responseCode.getCode() == ResponseCode.SUCCESS.getCode()) {
            throw new IllegalArgumentException("Invalid Code.");
        }
        PagedResponse<T> response = new PagedResponse<>();
        response.setCode(responseCode.getCode());
        response.setCode(responseCode.getCode());
        return response;
    }

    public static <T> PagedResponse<T> restrictedRequest() {
        return fail(ResponseCode.RESTRICTED);
    }

    public static <T> PagedResponse<T> unsupportedRequest() {
        return fail(ResponseCode.UNSUPPORTED);
    }

    public static <T> PagedResponse<T> requestTimeout() {
        return fail(ResponseCode.TIMEOUT);
    }
}
