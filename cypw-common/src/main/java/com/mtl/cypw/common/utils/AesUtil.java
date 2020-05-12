package com.mtl.cypw.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.mtl.cypw.common.utils.config.AesProperties;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.util.Arrays;

/**
 * @author tang.
 * @date 2019/10/18.
 */
@Slf4j
public class AesUtil {

    /**
     * AES 加密
     *
     * @param properties
     * @return
     * @throws Exception
     */
    public static String encrypt(AesProperties properties, String info) {
        if (!properties.checkParam()) {
            log.error("参数错误:{}", JSONObject.toJSONString(properties));
            return null;
        }
        try {
            SecretKeySpec keySpec = getKey(properties);
            Cipher cipher = Cipher.getInstance(properties.getModePadding());
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = info.getBytes(properties.getCharset());
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            IvParameterSpec iv = new IvParameterSpec(properties.getIv().getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encrypted = cipher.doFinal(plaintext);
            return HexUtil.bytesToHexString(encrypted);
        } catch (Throwable e) {
            log.error("AES加密失败，e:{}", e.getMessage());
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param properties
     * @return
     */
    public static String decrypt(AesProperties properties, String info) {
        try {
            byte[] byteResult = decrypt2Byte(properties, info);
            String result = new String(byteResult, properties.getCharset());
            return result;
        } catch (Throwable e) {
            log.error("AES解密失败，e:{}", e.getMessage());
        }
        return null;
    }

    public static String decrypt(AesProperties properties, byte[] info) {
        try {
            byte[] byteResult = decrypt2Byte(properties, info);
            String result = new String(byteResult, properties.getCharset());
            return result;
        } catch (Throwable e) {
            log.error("AES解密失败，e:{}", e.getMessage());
        }
        return null;
    }

    public static byte[] decrypt2Byte(AesProperties properties, String info) {
        try {
            if (!properties.checkParam()) {
                log.error("参数错误:{}", JSONObject.toJSONString(properties));
                return null;
            }
            SecretKeySpec keySpec = getKey(properties);
            Cipher cipher = Cipher.getInstance(properties.getModePadding());
            IvParameterSpec iv = new IvParameterSpec(properties.getIv().getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] encrypted = HexUtil.hexTobyte(info);
            byte[] original = flushZeroElements(cipher.doFinal(encrypted));
            return original;
        } catch (Throwable e) {
            log.error("AES解密失败，e:{}", e.getMessage());
        }
        return null;
    }

    public static byte[] decrypt2Byte(AesProperties properties, byte[] info) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(properties.getModePadding());
            AlgorithmParameters params = AlgorithmParameters.getInstance(properties.getMode());
            params.init(new IvParameterSpec(properties.getBiv()));
            cipher.init(Cipher.DECRYPT_MODE, getKeyForByte(properties), params);
            byte[] byteResult = flushZeroElements(cipher.doFinal(info));
            return byteResult;
        } catch (Exception e) {
            log.error("AES解密失败，e:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 清除AES加密前补位的内容
     *
     * @param source
     * @return
     */
    private static byte[] flushZeroElements(byte[] source) {
        int i = 0;
        for (byte b : source) {
            if (b == 0) {
                break;
            }
            i++;
        }
        byte[] result = Arrays.copyOf(source, i);
        return result;
    }

    /**
     * 针对长度不满16位的秘钥进行补0操作
     *
     * @param properties
     * @return
     */
    private static SecretKeySpec getKey(AesProperties properties) {
        byte[] arrBTmp = properties.getKey().getBytes();
        byte[] arrB = new byte[16];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec keySpec = new SecretKeySpec(arrB, properties.getMode());
        return keySpec;
    }

    private static SecretKeySpec getKeyForByte(AesProperties properties) {
        byte[] arrBTmp = properties.getBKey();
        byte[] arrB = new byte[16];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec keySpec = new SecretKeySpec(arrB, properties.getMode());
        return keySpec;
    }

//
//    public static void main(String[] args) throws Exception {
//        String str = "1CA028471095F90CCD8955F50B238CAD32A9AEFCCC9C0804994786E60E83BE9BD075FCF88CEC0719A1787D1F0C867D6F5648BF477C39625C8CA63C694DA28AA4C768A98550F6C150B9B56A8BCCB231639B15C5B9B77A4DFDF2757E2A1A21172F";
//        AesProperties properties = new AesProperties();
//        properties.setKey("3JEFX9]A4W5FdS{[*&");
//        properties.setIv("0807060504030201");
//        String s = decrypt(properties, str);
//        JSONObject jsonObject = JSON.parseObject(s);
//        String userCode = jsonObject.getString("usercode");
//        System.out.println("s:" + s);
//        System.out.println("userCode:" + userCode);
//    }
//
//    public static void main(String[] args) {
//        String encode = "n1a1fFvfQG1sP5gYFKiel47gqQ8bWoGghPehLJ07gslhA+4n5+x7FyIa4wBJhVFknxQ3XjMitixSyt41uuyzB9N11vm5ItxqXiyqYPpk03pcIH3FJZpCkyy+a2vXk+rUTxekQdz7U6UQOaj7d1JbWpqSWFNxN1YGYaYZ/RYeM1QmKfzAkh7AuMnfkGbW4vkfjRPlLY/tA8SR8+7qY+LtWA==";
//        AesProperties properties = new AesProperties();
//        properties.setBKey(Base64Util.decode("PZ1JP32cRyzAbxHWeAc5sQ=="));
////        properties.setIv("b5XpGjUe5iH8Gft69iFvGg==");
//        properties.setBiv(Base64Util.decode("b5XpGjUe5iH8Gft69iFvGg=="));
////        properties.setModePadding("AES/CBC/PKCS5Padding");
//        String info = AesUtil.decrypt(properties, Base64Util.decode(encode));
//        log.info("info:{}", info);
//    }
}
