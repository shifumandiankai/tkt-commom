package com.mtl.cypw.common.exception;

/**
 * 验证服务异常
 * @author Johnathon.Yuan
 * @date 2019-11-13 16:47
 */
public class ValidationException extends GeneralException {

    public ValidationException(int code) {
        super(code);
    }

    public ValidationException(String message, int code) {
        super(message, code);
    }

    public ValidationException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public ValidationException(Throwable cause, int code) {
        super(cause, code);
    }

}
