package com.mtl.cypw.common.exception;

/**
 * 数据权限异常
 * @author Johnathon.Yuan
 * @date 2019-11-13 16:48
 */
public class PermissionException extends GeneralException {

    public PermissionException(int code) {
        super(code);
    }

    public PermissionException(String message, int code) {
        super(message, code);
    }

    public PermissionException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public PermissionException(Throwable cause, int code) {
        super(cause, code);
    }
}
