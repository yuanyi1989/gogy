package com.github.gogy.build.store;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author yuanyi
 * @date 2018/1/22
 */
@Slf4j
public class AliyunOSSStore implements Store {

    private static final String default_endpoint_suffix = ".aliyuncs.com";
    private String basePath = "/data/build/store/aliyun/";

    private OSSClient client;

    public AliyunOSSStore(boolean isOnline, String localStorePath, String endpoint, String accessKeyId, String accessKeySecret) {
        // http://oss-cn-shenzhen-internal.aliyuncs.com
        endpoint = "http://" + endpoint+ (isOnline ? "-internal" : "") + default_endpoint_suffix;
        this.basePath = localStorePath;
        // 创建ClientConfiguration实例
        ClientConfiguration conf = new ClientConfiguration();
        // 设置OSSClient使用的最大连接数，默认1024
        conf.setMaxConnections(200);
        // 设置请求超时时间，默认50秒
        conf.setSocketTimeout(10000);
        // 设置失败请求重试次数，默认3次
        conf.setMaxErrorRetry(3);
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
    }


    @Override
    public boolean put(Path content, String applicationKey, String buildId, String md5) {
        if (StringUtils.isEmpty(applicationKey) || !Files.exists(content)) {
            throw new IllegalArgumentException("content and applicationKey can not be empty");
        }

        String bucketName = parseBucketName(applicationKey, buildId);
        boolean bucketExist = client.doesBucketExist(bucketName);
        if (!bucketExist) {
            client.createBucket(bucketName);
        }

        ObjectMetadata meta = new ObjectMetadata();
        // 设置上传文件长度
        meta.setContentLength(content.toFile().length());
        meta.setContentMD5(md5);
        PutObjectResult putObjectResult = client.putObject(bucketName, buildId, content.toFile(), meta);

        String eTag = putObjectResult.getETag();

        return StringUtils.isNotBlank(eTag);
    }

    @Override
    public Path get(String applicationKey, String buildId) {
        String bucketName = parseBucketName(applicationKey, buildId);
        OSSObject object = client.getObject(bucketName, buildId);
        String path = basePath + applicationKey;

        if (!Files.exists(Paths.get(path))) {
            try {
                Files.createDirectories(Paths.get(path));
            } catch (IOException e) {
                throw new IllegalStateException("create temp store path fail", e);
            }
        }

        Path destination = Paths.get(path, buildId);
        try {
            Files.deleteIfExists(destination);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        try (InputStream objectContent = object.getObjectContent()) {

            byte[] b = new byte[1024];
            while((objectContent.read(b)) != -1){
                Files.write(destination, b, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            log.error("download build file error", e);
            throw new IllegalStateException(e);
        }
        return destination;
    }

    private String parseBucketName(String applicationKey, String buildId) {
        return "xybuild-"+applicationKey;
    }

}
