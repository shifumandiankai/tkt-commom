package com.mtl.cypw.common.utils.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tang.
 * @date 2020/1/10.
 */
@Data
public class AesProperties {

    /**
     * 秘钥
     */
    public String key;
    /**
     * 秘钥
     */
    public byte[] bKey;

    /**
     * 向量
     */
    private String iv;

    /**
     * 向量
     */
    private byte[] biv;

    /**
     * 加密模式及填充方式
     */
    private String modePadding = "AES/CBC/NoPadding";

    /**
     * 加密模式
     */
    private String mode = "AES";

    /**
     * 字符集
     */
    private String charset = "UTF-8";

    public Boolean checkParam() {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(iv)) {
            return false;
        }
        return true;
    }
}
