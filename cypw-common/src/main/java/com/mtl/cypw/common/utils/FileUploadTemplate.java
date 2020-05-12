package com.mtl.cypw.common.utils;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import cn.ucloud.ufile.util.MimeTypeUtil;
import com.mtl.cypw.common.enums.FileDirectoryEnum;
import com.mtl.cypw.common.utils.config.FileUploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tang.
 * @date 2019/12/10.
 */
@Slf4j
public class FileUploadTemplate {

    private FileUploadProperties fileUploadProperties;

    public FileUploadTemplate(FileUploadProperties properties) {
        fileUploadProperties = properties;
    }

//    public void createBucket() {
//        // Bucket相关API的授权器
//        BucketAuthorization bucket_authorizer = new UfileBucketLocalAuthorization(publicKey, privateKey);
//
//        String bucketName = "20191211";
//        String region = "cn-sh2";
//        try {
//            BucketResponse res = UfileClient.bucket(bucket_authorizer).createBucket(bucketName, region, BucketType.PUBLIC).execute();
//            log.info("res:{}", JSONObject.toJSONString(res));
//        } catch (UfileClientException e) {
//            log.error("创建bucket失败：", e.getMessage());
//        } catch (UfileServerException e) {
//            log.error("创建bucket失败：", e.getMessage());
//        }
//    }

    public String fileUpload(MultipartFile mFile) {
        return fileUpload(mFile, null);
    }

    public String fileUpload(MultipartFile mFile, FileDirectoryEnum fileDirectoryEnum) {
        File file = null;
        try {
            if (mFile.equals("") || mFile.getSize() <= 0) {
                return "";
            } else {
                InputStream ins = null;
                ins = mFile.getInputStream();
                file = new File(mFile.getOriginalFilename());
                FileUtil.inputStreamToFile(ins, file);
                ins.close();
            }
        } catch (Exception e) {
            log.error("文件上传失败：", e);
        }
        return fileUpload(file, fileDirectoryEnum);
    }

    public String fileUpload(File file) {
        return fileUpload(file, null);
    }

    public String fileUpload(File file, FileDirectoryEnum fileDirectoryEnum) {
        ObjectAuthorization object_authorizer = new UfileObjectLocalAuthorization(fileUploadProperties.getPublicKey(), fileUploadProperties.getPrivateKey());

        // 对象操作需要ObjectConfig来配置您的地区和域名后缀
        ObjectConfig config = new ObjectConfig(fileUploadProperties.getRegion(), fileUploadProperties.getProxySuffix());
        try {
            String fName = file.getName();
            String suffix = fName.substring(fName.lastIndexOf("."));
            String directory = "";
            if (fileDirectoryEnum != null) {
                directory = fileDirectoryEnum.name() + "/";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            directory = directory + sdf.format(new Date()) + "/";
            String fileName = directory + FileUtil.getFileName(suffix);
            PutObjectResultBean response = UfileClient.object(object_authorizer, config)
                    .putObject(file, MimeTypeUtil.getMimeType(file))
                    .nameAs(fileName)
                    .toBucket(fileUploadProperties.getBucketName())
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();
            return fileUploadProperties.getDomain() + fileName;
        } catch (UfileClientException e) {
            log.error("文件上传失败：", e);
        } catch (UfileServerException e) {
            log.error("文件上传失败：", e);
        }
        return null;
    }

//    public static String fileUpload(InputStream inputStream) {
//        File file = null;
//        try {
//            OutputStream os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[8192];
//            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            os.close();
//            inputStream.close();
//        } catch (Exception e) {
//            log.error("文件上传失败：", e);
//        }
//        return fileUpload(file);
//    }
//
//    public static String fileUpload(byte[] appendData) throws IOException {
//        File file = null;
//        try {
//            OutputStream output = new FileOutputStream(file);
//            BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
//            bufferedOutput.write(appendData);
//        } catch (Exception e) {
//            log.error("文件上传失败：", e);
//        }
//        return fileUpload(file);
//    }

}
