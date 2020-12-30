package com.baizhi.aliyun;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.baizhi.util.aliyunUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class aliyunUploadDelFragment {

    /*
    upload方法
    fileName为上传文件的文件名  ：  自定义
    bucketName为aliyunOSS的库名：bucketName  ;   调用时请配置为:  "sunyf-yx"  形式
    folderName为aliyunOSS库下的文件夹名： buketName/img ；  调用时请配置为："img/"  形式
    file为前端传给后台的文件
     */
    public static void upload(String fileName, String bucketName, String folderName, MultipartFile file) throws IOException {


        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = aliyunUtil.getKeyAndSecret().get("endpoint");
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = aliyunUtil.getKeyAndSecret().get("AccessKeyID");
        String accessKeySecret = aliyunUtil.getKeyAndSecret().get("AccessKeySecret");

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传Byte数组。
        byte[] content = file.getBytes();

        ossClient.putObject(bucketName, folderName + fileName, new ByteArrayInputStream(content));

        // 关闭OSSClient。
        ossClient.shutdown();
    }


    /*
    del方法
    fileName为上传文件的文件名  ：  自定义
    bucketName为aliyunOSS的库名：bucketName  ;   调用时请配置为:  "sunyf-yx"  形式
    folderName为aliyunOSS库下的文件夹名： buketName/img ；  调用时请配置为："img/"  形式
    objectName为：folderName + fileName
     */
    public static void del(String fileName, String bucketName, String folderName) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = aliyunUtil.getKeyAndSecret().get("endpoint");
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = aliyunUtil.getKeyAndSecret().get("AccessKeyID");
        String accessKeySecret = aliyunUtil.getKeyAndSecret().get("AccessKeySecret");


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, folderName + fileName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }


    /*
    fragment方法
    fileName为上传文件的文件名  ：  自定义
    bucketName为aliyunOSS的库名：bucketName  ;   调用时请配置为:  "sunyf-yx"  形式
    folderName为aliyunOSS库下的文件夹名： buketName/img ；  调用时请配置为："img/"  形式
    objectName为：folderName + fileName
     */
    public static URL fragment(String bucketName, String folderName, String fileName) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = aliyunUtil.getKeyAndSecret().get("endpoint");
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = aliyunUtil.getKeyAndSecret().get("AccessKeyID");
        String accessKeySecret = aliyunUtil.getKeyAndSecret().get("AccessKeySecret");

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_1010,f_jpg,w_800,h_600";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, folderName + fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        //  System.out.println(signedUrl);
        // 关闭OSSClient。
        ossClient.shutdown();
        return signedUrl;
    }

    //上传网络流
    public static void internetUpload(String bucketName, String folderName, String fileName, URL url) throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = aliyunUtil.getKeyAndSecret().get("endpoint");
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = aliyunUtil.getKeyAndSecret().get("AccessKeyID");
        String accessKeySecret = aliyunUtil.getKeyAndSecret().get("AccessKeySecret");

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传网络流。
        InputStream inputStream = url.openStream();
        //System.out.println("上传网络流");
        ossClient.putObject(bucketName, folderName + fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
