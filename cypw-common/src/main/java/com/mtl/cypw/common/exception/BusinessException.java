package com.mtl.cypw.common.exception;

import lombok.Getter;

/**
 * 业务异常
 * @author Johnathon.Yuan
 * @date 2019-11-13 16:49
 */
public class BusinessException extends RuntimeException {

    @Getter
    private int code;

    public BusinessException(int code) {
        super();
        this.code = code;
    }

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
}
