package com.mtl.cypw.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author tang.
 * @date 2019/10/12.
 */
public class HexUtil {

    /**
     * 将传入的byte[]数组转换成十六机制数的字符串
     *
     * @param src 要转换的byte数组
     * @return 返回十六进制的字符串
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            //将一个byte的二进制数转换成十六进制字符
            String hv = Integer.toHexString(v);
            //如果二进制数转换成十六进制数高位为0，则加入'0'字符
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 将16进制字符串转换为byte数组
     *
     * @param strHex
     * @return
     */
    public static byte[] hexTobyte(String strHex) {
        if (StringUtils.isEmpty(strHex)) {
            return null;
        }
        int count = strHex.length();
        if (count % 2 == 1) {
            return null;
        }
        byte[] b = new byte[count / 2];
        for (int i = 0 ; i!=count/2;i++){
            b[i]= (byte) Integer.parseInt(strHex.substring(i*2,i*2+2),16);
        }
        return b;
    }
}
