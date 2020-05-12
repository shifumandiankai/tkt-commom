package com.mtl.cypw.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-13 15:16
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * success
     */
    SUCCESS(200, "success"),
    /**
     * timeout
     */
    TIMEOUT(10001, "请求超时"),
    /**
     * restricted
     */
    RESTRICTED(10002, "业务规则约束限制"),
    /**
     * unsupported
     */
    UNSUPPORTED(10003, "不支持的业务请求");

    private int code;

    private String msg;

}
