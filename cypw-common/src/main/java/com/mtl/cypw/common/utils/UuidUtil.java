package com.mtl.cypw.common.utils;

import java.util.UUID;

/**
 * @author tang.
 * @date 2020/1/14.
 */
public class UuidUtil {

    public static String getUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}
