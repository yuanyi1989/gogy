package com.github.gogy.build.store;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author yuanyi
 * @date 2018/1/23
 */
@Slf4j
public class LocalDiskStore implements Store {

    private String basePath = "/data/build/store/disk/";

    public LocalDiskStore(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public boolean put(Path content, String applicationKey, String buildId, String md5) {
        if (StringUtils.isEmpty(applicationKey) || !Files.exists(content)) {
            throw new IllegalArgumentException("content and applicationKey can not be empty");
        }

        try {
            Files.createDirectories(Paths.get(basePath + applicationKey));
            Path move = Files.move(content, Paths.get(basePath + applicationKey + "/" + buildId + ".zip"));
            if (!Files.exists(move)) {
                return false;
            }
        } catch (IOException e) {
            log.error("store build file error", e);
            return false;
        }
        return true;
    }

    @Override
    public Path get(String applicationKey, String buildId) {
        String realPath = basePath + applicationKey + "/" + buildId + ".zip";
        return Paths.get(realPath);
    }
}
