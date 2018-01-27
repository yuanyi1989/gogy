package com.github.gogy.build.maven;

import com.github.gogy.build.BuildResult;
import com.github.gogy.build.BuildService;
import com.github.gogy.build.code.CodeRepositoryService;
import com.github.gogy.build.store.Store;
import com.github.gogy.build.util.MD5Util;
import com.github.gogy.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * @author yuanyi
 * @date 2018/1/22
 */
@Slf4j
public class MavenBuildServiceImpl implements BuildService {

    private CodeRepositoryService codeRepositoryService;
    private Store store;
    private String codeLocalAddress;

    public MavenBuildServiceImpl(Store store, CodeRepositoryService codeRepositoryService, String codeLocalAddress) {
        this.codeRepositoryService = codeRepositoryService;
        this.codeLocalAddress = codeLocalAddress;
        this.store = store;
    }

    @Override
    public BuildResult build(String applicationKey, String codeRepository, boolean branch, String branchOrTagName, boolean withBuildFile) {
        //check代码
        log.info("start build application {}; code repository: {}; branch:{}", applicationKey, codeRepository, branchOrTagName);
        Result pullResult = codeRepositoryService.pull(applicationKey, codeRepository, codeLocalAddress);
        if (!pullResult.isSuccess()) {
            return BuildResult.builder().success(false).message("pull code fail").build();
        }
        //更新到指定的分支
        Result selectResult;
        if (branch) {
            selectResult = codeRepositoryService.selectBranch(branchOrTagName, applicationKey, codeLocalAddress);
        } else {
            selectResult = codeRepositoryService.selectTag(branchOrTagName, applicationKey, codeLocalAddress);
        }

        if (!selectResult.isSuccess()) {
            return BuildResult.builder().success(false).message("switch branch fail").build();
        }

        log.info("start package ……");
        String preCMD = "cd "+codeLocalAddress + File.separator + applicationKey;
        String packageCMD = "mvn clean package -DskipTests";

        BuildResult.BuildResultBuilder builder = BuildResult.builder().success(true);
        StringBuilder messageContent = new StringBuilder();
        try {
            Process exec = Runtime.getRuntime().exec((isWindows() ? "cmd /c " : "") + preCMD + " && " + packageCMD);
            BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
                messageContent.append(line).append("\n");
                if (line.contains("BUILD FAILURE")) {
                    builder.success(false);
                }
            }
            builder.message(messageContent.toString());
        } catch (IOException e) {
            log.error("package fail", e);
            builder.success(false);
            builder.message("can not connect to code repository");
        }
        BuildResult buildResult = builder.build();

        if (!buildResult.isSuccess()) {
            return buildResult;
        }

        String buildFilePath = codeLocalAddress + File.separator + applicationKey + "/target/build/build.zip";
        try {
            Files.deleteIfExists(Paths.get(buildFilePath));
        } catch (IOException e) {
            //如果打包文件已经存在，则删除
            log.error("can not clean maven workspace", e);
            buildResult.setSuccess(false);
            buildResult.setMessage("can not clean workspace, please do it yourself");
        }

        log.info("package finished...");
        log.info("start zip the file...");
        String zipPath = codeLocalAddress + File.separator + applicationKey + "/target/build";
        preCMD = "cd "+zipPath;
        String zipCMD = "zip -r build.zip ./*";

        try {
            //打包成zip
            Process exec = Runtime.getRuntime().exec((isWindows() ? "cmd /c " : "") + preCMD + " && " + zipCMD);

            BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream(), isWindows()? "gbk":"UTF-8"));
            //清空
            messageContent.delete(0, messageContent.length()-1);
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
                messageContent.append(line).append("\n");
            }

            if (!Files.exists(Paths.get(buildFilePath))) {
                //打包有异常
                buildResult.setMessage(messageContent.toString());
                buildResult.setSuccess(false);
                return buildResult;
            }

            buildResult.setBuildFile(Paths.get(buildFilePath).toFile());
        } catch (IOException e) {
            log.error("package build file error", e);
            buildResult.setMessage(messageContent.toString());
            buildResult.setSuccess(false);
            return buildResult;
        }

        buildResult.setBuildFile(Paths.get(buildFilePath).toFile());
        String md5 = Base64.encodeBase64String(MD5Util.encodeToByte(buildResult.getBuildFile()));

        buildResult.setMD5(md5);
        buildResult.setBuildId(generateBuildId(applicationKey));
        log.info("zip finished; buildId:{}; message:{}", buildResult.getBuildId(), buildResult.getMessage());

        //上传SSO
        log.info("start upload to aliyun oss...");
        boolean putResult = store.put(buildResult.getBuildFile().toPath(), applicationKey,
                buildResult.getBuildId(), buildResult.getMD5());

        if (!putResult) {
            buildResult.setSuccess(false);
            buildResult.setMessage("store build file fail");
            return buildResult;
        }

        try {
            Files.deleteIfExists(buildResult.getBuildFile().toPath());
        } catch (IOException e) {
            log.warn("delete build file error", e);
        }

        log.info("build success...");
        return buildResult;
    }

    private static final DateTimeFormatter BASIC_ISO_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendValue(ChronoField.YEAR, 4)
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .optionalStart()
                .toFormatter();

    private String generateBuildId(String applicationKey) {
        String now = applicationKey+"-"+LocalDateTime.now().format(BASIC_ISO_DATE_TIME);
        return now;
    }

    @Override
    public File getBuildFile(String applicationKey, String buildId) {
        Path path = store.get(applicationKey, buildId);
        return path.toFile();
    }

    private boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().startsWith("win");
    }
}
