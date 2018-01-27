package com.github.gogy.build.store;

import java.nio.file.Path;

/**
 * 构建存储接口
 * @author yuanyi
 * @date 2018/1/23
 */
public interface Store {

    /**
     * 存储文件
     * @param applicationKey
     * @param content
     * @param buildId
     * @param md5
     */
    boolean put(Path content, String applicationKey, String buildId, String md5);

    /**
     * 获取文件
     * @param applicationKey
     * @param buildId
     * @return
     */
    Path get(String applicationKey, String buildId);

}
