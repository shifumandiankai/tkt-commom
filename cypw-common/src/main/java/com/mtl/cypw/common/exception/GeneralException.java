package com.mtl.cypw.common.exception;

import lombok.Getter;

/**
 * 通用异常
 * @author Johnathon.Yuan
 * @date 2019-11-13 16:48
 */
public class GeneralException extends RuntimeException {

    @Getter
    private int code;

    public GeneralException(int code) {
        super();
        this.code = code;
    }

    public GeneralException(String message, int code) {
        super(message);
        this.code = code;
    }

    public GeneralException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public GeneralException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
}
