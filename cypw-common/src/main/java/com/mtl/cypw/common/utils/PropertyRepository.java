package com.mtl.cypw.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Johnathon.Yuan
 * @date 2019-11-21 14:22
 */
public class PropertyRepository {
    private static final String CONFIG_BASE_PATH = "/opt/cypw_configs/";
    private static final String COMMON_CONFIG_BASE_PATH = "/common/";
    public static final String CMB_PUBLIC_KEY_PATH = "/opt/cypw_configs/cmb/public.key";

    public PropertyRepository() {
    }

    public static void init(String fileName) throws FileNotFoundException, IOException {
        String fullName = CONFIG_BASE_PATH + fileName;
        System.getProperties().load(new FileInputStream(fullName));
    }

    public static void initPayment(String fileName) throws FileNotFoundException, IOException {
        String fullName = CONFIG_BASE_PATH + fileName;
        System.getProperties().load(new FileInputStream(fullName));
    }

    public static void initCommon(String fileName) throws FileNotFoundException, IOException {
        String fullName = COMMON_CONFIG_BASE_PATH + fileName;
        InputStream fis = PropertyRepository.class.getResourceAsStream(fullName);
        System.getProperties().load(fis);
    }

}
