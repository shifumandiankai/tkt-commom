package com.mtl.cypw.common.exception;

/**
 * HTTP 通信异常
 * @author Johnathon.Yuan
 * @date 2019-11-13 16:50
 */
public class RestException extends GeneralException {

    public RestException(int code) {
        super(code);
    }

    public RestException(String message, int code) {
        super(message, code);
    }

    public RestException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public RestException(Throwable cause, int code) {
        super(cause, code);
    }
}
