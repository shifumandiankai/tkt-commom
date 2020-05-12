package com.mtl.cypw.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

/**
 * 基于commons-code包
 *
 * @author tang.
 * @date 2020/1/15.
 */
@Slf4j
public class Base64Util {
    /**
     * Base64 编码
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(str.getBytes("UTF-8"));
        } catch (Throwable e) {
            log.error("Base64 编码失败，str:{}", str);
            log.error("e:{}", e.getMessage());
        }
        return null;
    }

    /**
     * Base64解码
     *
     * @param str
     * @return
     */
    public static String decodeToString(String str) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(str.getBytes());
            return new String(bytes, "UTF-8");
        } catch (Throwable e) {
            log.error("Base64 解码失败，str:{}", str);
            log.error("e:{}", e.getMessage());
        }
        return null;
    }

    /**
     * Base64解码
     *
     * @param str
     * @return
     */
    public static byte[] decode(String str) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            return decoder.decode(str.getBytes());
        } catch (Throwable e) {
            log.error("Base64 解码失败，str:{}", str);
            log.error("e:{}", e.getMessage());
        }
        return null;
    }


    public static void main(String[] args) {
        String str = "u9su4Mi92g8b2l1GjBEllZQBpjFxRbKYY4MNjgS7qyQBNWCWEZXEbOzsEYjkBr+snon1a091EAl4DQUJovlVtDiLMEfqnjg93w9X8rlqAjKq2M7Sz/RThQ5MNSIFoQWGOcFTbIIxXL6DB2z9t7cYdxj64ST/WuYyUt4xfXuZLzmu2/UF3fp38yzTmYL5t+YDJzKcjUdk9n/BXqxXj4z60B11hfZ2FfLF/MDv289MbyPP6j5Q2o6aOmwRDHYFRdiUXi+rzfzseuEoa1Hk2IyHG7C1pDWJ1efpgGT+bV/WHa/4MNYlu0IvmiD2uifVd1nteGqXHTCvEt14ADrTnnCtsLuPr1lP/4PQsIPqpiZz/yYvV9fDX4nlU/NjLd3BhOv37T7itZ/mjyOJ+eemiOrcOCCsAKOqP+DmwbteJcgkWRtuxrdRKxXLX4M+Xo4Fl7aWdxNaGK1cAbT/r+w3N4c3CfShSfLHmQ2xCWZeHiCRd4KbfmIsyEVS9s40vLIQAZ+QJ08MnvsRbG8NyZr5mY85fptU4K8Osb3oJn/VF7Ou3wRGAY06ckgSIpapCi4wfx8M/1oP+y+BMAQPH2JuG4gHVtiHMTeHmItnIEElqAm863k9z+T/cnICKZQfFZ4+OYTGOsJUC2xmlYw7oRh3fpbrGOfcKBcrGSI0SWa2AQeJ0z60y4xPyaHQZ7XmYVzNotzJ";
        String info = DesUtil.decrypt("cmbtest1", decode(str));
        log.info("info:{}", info);
    }
}
