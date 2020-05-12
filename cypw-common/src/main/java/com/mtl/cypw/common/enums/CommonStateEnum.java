package com.mtl.cypw.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-22 15:29
 */
@Getter
@AllArgsConstructor
public enum CommonStateEnum {
    INVALID(0), VALID(1);
    private int code;
}
