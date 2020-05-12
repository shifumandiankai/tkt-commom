package com.mtl.cypw.common.utils.config;

import lombok.Data;

/**
 * @author tang.
 * @date 2020/3/16.
 */
@Data
public class FileUploadProperties {

    private String publicKey;

    private String privateKey;

    private String bucketName;

    private String region;

    private String proxySuffix;

    private String domain;
}
