package com.github.gogy.build;

import com.github.gogy.build.store.AliyunOSSStore;
import com.github.gogy.build.store.Store;
import com.github.gogy.build.util.MD5Util;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author yuanyi
 * @date 2018/1/23
 */
public class AliyunStoreTest {

    private Store store;

    @Before
    public void setUp() {
        store = new AliyunOSSStore(false, "/data/build/store/aliyun/",
                "oss-cn-shenzhen", "LTAIanGcIxH02A2g", "L2pUsEcHB8jDkAjlzBE0mPLE1izlaf");
    }

    @Test
    public void test() {
        Path path = Paths.get("D:\\gitRepository\\onlineteam\\sdkapi\\pubId\\target\\pubId-module-2.0.0-SNAPSHOT.jar");
        byte[] bytes = MD5Util.encodeToByte(path.toFile());
        String encode = Base64.encodeBase64String(bytes);
        boolean put = store.put(path, "pubid-test", "pubid-test-20180123121840", encode);
        System.out.println("put state ==> "+put);
    }

    @Test
    public void testGet() {
        Path path = store.get("pubid-test", "pubid-test-20180123121840");
        System.out.println(path.toString());
    }

}
