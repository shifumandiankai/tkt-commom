package com.mtl.cypw.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-22 14:17
 */

@Getter
@AllArgsConstructor
public enum DeleteEnum {

    EXIST(0), DELETED(1);

    private int code;
}
