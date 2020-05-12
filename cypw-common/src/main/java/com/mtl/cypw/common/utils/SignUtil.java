package com.mtl.cypw.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * @author tang.
 * @date 2019/10/14.
 */
public class SignUtil {

    /**
     * 签名算法，招商支付签名
     */
    public static String getSignByCmb(Map<String, Object> map, String key) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            list.add(entry.getKey() + "=" + entry.getValue() + "&");
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += key;
        return HexUtil.bytesToHexString(SHAUtil.SHA256(result));
    }

    /**
     * 签名算法，招商回调验签
     */
    public static boolean checkCmbSign(String cmbResult, String pubKey) throws Exception {
        Map<String, Object> map = JSONObject.parseObject(cmbResult);
        String sign = (String) map.get("sign");
        Map<String, Object> noticeDataMap = (Map<String, Object>) map.get("noticeData");
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : noticeDataMap.entrySet()) {
            list.add(entry.getKey() + "=" + entry.getValue() + "&");
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.substring(0, sb.length() - 1).toString();
        return RSAUtil.verifyWhenSha1Sign(result, RSAUtil.getPublicKey(pubKey), sign);
    }

    /**
     * 签名算法（微信统一下单时使用）
     *
     * @param o 要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSignByWechat(Object o, String key) throws IllegalAccessException {
        ArrayList<String> list = new ArrayList<String>();
        Field[] fields = FieldUtils.getAllFields(o.getClass());
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                list.add(f.getName() + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        return MD5Util.md5(result).toUpperCase();
    }


    /**
     * 签名（微信端唤起支付界面时必要参数）
     *
     * @param map
     * @param key 密钥
     * @return
     */
    public static String getSignByWechat(Map<String, Object> map, String key) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        return MD5Util.md5(result).toUpperCase();
    }

    /**
     * 微信回调数据验签
     *
     * @param responseString
     * @param key
     * @return
     * @throws Exception
     */
    public static boolean checkIsSignValidForWechat(String responseString, String key) throws Exception {
        Map<String, Object> map = null;
        try {
            map = XMLParser.getMapFromXML(responseString);
        } catch (Exception e) {
            System.out.println("xml转化成map异常 " + e.getMessage());
        }
        String wxSign = (String) map.get("sign");
        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String sign = getSignByWechat(map, key);
        if (map.containsKey("sign")) {
            return sign.equals(wxSign);
        } else {
            throw new Exception("微信返回数据没有签名");
        }
    }

}
