package com.mtl.cypw.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @author tang.
 * @date 2020/1/15.
 */
@Slf4j
public class DesUtil {

    /**
     * DES加密
     *
     * @param key
     * @param info
     * @return
     */
    public static String encrypt(String key, String info) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(info)) {
            log.error("DES加密参数异常，key:{},info:{}", key, info);
            return null;
        }
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secureKey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, random);
            byte[] cipherBytes = cipher.doFinal(info.getBytes());
            return HexUtil.bytesToHexString(cipherBytes);
        } catch (Throwable e) {
            log.error("DES加密失败，key:{},info:{}", key, info);
            log.error("e:{}", e.getMessage());
        }
        return null;
    }

    /**
     * DES解密
     *
     * @param key
     * @param info
     * @return
     */
    public static String decrypt(String key, String info) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(info)) {
            log.error("DES解密参数异常，key:{},info:{}", key, info);
            return null;
        }
        try {
            byte[] bytes = HexUtil.hexTobyte(info);
            return decrypt(key, bytes);
        } catch (Throwable e) {
            log.error("DES解密失败，key:{},info:{}", key, info);
            log.error("e:{}", e.getMessage());
        }
        return null;
    }

    /**
     * DES解密
     *
     * @param key
     * @param info
     * @return
     */
    public static String decrypt(String key, byte[] info) {
        if (StringUtils.isEmpty(key) || info == null) {
            log.error("DES解密参数异常，key:{},info:{}", key, info);
            return null;
        }
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secureKey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secureKey, random);
            return new String(cipher.doFinal(info));
        } catch (Throwable e) {
            log.error("DES解密失败，key:{},info:{}", key, info);
            log.error("e:{}", e.getMessage());
        }
        return null;
    }
}
