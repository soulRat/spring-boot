package com.soul.rat.biz.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * oss链接util类
 *
 * @author 朱家兴
 * @date 2019-06-25 14:32
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring.oss")
public class OssClientConfig {

    /**
     * log日志
     */
    private static Logger logger = LoggerFactory.getLogger(OssClientConfig.class);
    /**
     * 阿里云API的内或外网域名
     */
    private String endPoint;
    /**
     * 阿里云API的密钥Access Key ID
     */
    private String accessKeyId;
    /**
     * 阿里云API的密钥Access Key Secret
     */
    private String accessKeySecret;
    /**
     * 阿里云API的bucket名称
     */
    private String bucketName;
    /**
     * 阿里云API的文件夹名称
     */
    private String fileDir;
    /**
     * 阿里云API的返回文件链接
     */
    private String baseUrl;

    /**
     * 获取阿里云OSS客户端对象
     */
    private OSS getOssClient() {
        return new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
    }

    public String getInfo() {
        return endPoint + accessKeyId + accessKeySecret + bucketName + fileDir + baseUrl;
    }


    public String addFile(String fileName, InputStream inputStream) {
        OSS ossClient = getOssClient();
        ossClient.putObject(bucketName, fileDir + fileName, inputStream);
        ossClient.shutdown();
        return getUrl(fileName);
    }

    public String getUrl(String fileName) {
        return baseUrl + fileDir + fileName;
    }

    public String putObjectRequest(String fileName, File file) {
        OSS ossClient = getOssClient();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileDir + fileName, file);
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        return getUrl(file.getName());
    }

    public void getObjectRequest(String fileName, File file) {
        OSS ossClient = getOssClient();
        ossClient.getObject(new GetObjectRequest(bucketName, fileDir + fileName), file);
        ossClient.shutdown();
    }

    /**
     * 文件上传
     *
     * @param classPathUrl classPathUrl
     * @param objectName   objectName
     * @author zhujx
     * @date 2020/8/31 10:50
     */
    public void upload(String classPathUrl, String objectName) throws IOException {
        //获取classPath目录下model文件夹
        Resource resource = new ClassPathResource(classPathUrl);
        InputStream inputStream = resource.getInputStream();
        uploadInputStream(inputStream, objectName);
    }

    /**
     * 流式上传
     *
     * @param inputStream inputStream
     * @param objectName  objectName
     * @author zhujx
     * @date 2020/8/31 10:48
     */
    public void uploadInputStream(InputStream inputStream, String objectName) throws IOException {
        OSS ossClient = getOssClient();
        // 上传文件流。
        ossClient.putObject(bucketName, fileDir + objectName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 下载文件流
     *
     * @param objectName objectName
     * @return java.io.InputStream
     * @author zhujx
     * @date 2020/8/31 10:51
     */
    public InputStream download(String objectName) {
        OSS ossClient = getOssClient();
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(bucketName, fileDir + objectName);
        return ossObject.getObjectContent();

    }

    /**
     * 文件是否存在
     *
     * @param objectName objectName
     * @return boolean
     * @author zhujx
     * @date 2020/8/31 10:52
     */
    public boolean doesObjectExist(String objectName) {
        OSS ossClient = getOssClient();
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        return ossClient.doesObjectExist(bucketName, fileDir + objectName);
    }

}