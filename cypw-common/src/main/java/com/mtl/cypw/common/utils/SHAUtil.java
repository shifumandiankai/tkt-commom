package com.mtl.cypw.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author tang.
 * @date 2019/10/12.
 */
public class SHAUtil {

    public static final String KEY_SHA_256 = "SHA-256";
    public static final String KEY_SHA_1 = "SHA";

    public static byte[] SHA256(final String strText) {
        return SHA(strText, KEY_SHA_256);
    }

    public static byte[] SHA1(final String strText) {
        return SHA(strText, KEY_SHA_1);
    }

    public static byte[] SHA(final String strText, final String KEY_SHA) {
        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                // SHA 加密开始
                MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
                messageDigest.update(strText.getBytes());
                return messageDigest.digest();
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        } else {
            return null;
        }
    }
}
