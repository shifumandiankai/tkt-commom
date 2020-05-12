package com.mtl.cypw.common.exception;

/**
 * RPC 通信异常
 * @author Johnathon.Yuan
 * @date 2019-11-13 16:50
 */
public class RpcException extends GeneralException {

    public RpcException(int code) {
        super(code);
    }

    public RpcException(String message, int code) {
        super(message, code);
    }

    public RpcException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public RpcException(Throwable cause, int code) {
        super(cause, code);
    }
}
