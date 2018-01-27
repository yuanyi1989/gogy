package com.github.gogy.build.code.git;

import com.github.gogy.build.code.CodeRepositoryService;
import com.github.gogy.common.DefaultResult;
import com.github.gogy.common.Result;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author yuanyi
 * @date 2018/1/22
 */
@Slf4j
public class GitRepositoryServiceImpl implements CodeRepositoryService {

    private boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().startsWith("win");
    }

    @Override
    public Result clone(String applicationKey, String address, String localAddress) {
        log.info("start clone code to {}", localAddress);
        Path path = Paths.get(localAddress);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error("can not create local code directory", e);
                return DefaultResult.builder().success(false).message("can not create directory :"+localAddress).build();
            }
        }

        String cloneCMD = "git clone "+address+" "+applicationKey;
        String preCMD = (isWindows() ? "cmd /c " : "") + "cd "+localAddress;

        String command = preCMD + " && " + cloneCMD;

        Result result = executeCommand(command);
        log.info("clone finished; result:{}; message:{}", result.isSuccess(), result.getMessage());
        return result;
    }

    @Override
    public Result pull(String applicationKey, String address, String localAddress) {
        log.info("start pull code from repository……");
        String appLocalAddress = localAddress + File.separator + applicationKey;
        boolean exists = Files.exists(Paths.get(appLocalAddress));
        if (!exists) {
            Result cloneResult = clone(applicationKey, address, localAddress);
            if (!cloneResult.isSuccess()) {
                return cloneResult;
            }
        }

        String pullCMD = "git pull";
        String preCMD = (isWindows() ? "cmd /c " : "") + "cd "+appLocalAddress;

        String command = preCMD +" && " + pullCMD;
        Result result = executeCommand(command);
        log.info("pull code finished; result:{}; message:{}", result.isSuccess(), result.getMessage());
        return result;
    }

    @Override
    public Result selectBranch(String branchName, String applicationKey, String localAddress) {
        String appLocalAddress = localAddress + File.separator + applicationKey;
        String preCMD = (isWindows() ? "cmd /c " : "") + "cd "+appLocalAddress;
        String newBranchCMD = "git branch "+ branchName;

        /*不用关心执行结果 确保该分支存在即可*/
        executeCommand(preCMD + " && " + newBranchCMD);

        String selectBranchCMD = "git checkout "+branchName;
        return executeCommand(preCMD + " && " + selectBranchCMD);
    }

    @Override
    public Result selectTag(String tagName, String applicationKey, String localAddress) {
        String appLocalAddress = localAddress + File.separator + applicationKey;
        String preCMD = (isWindows() ? "cmd /c " : "") + "cd "+appLocalAddress;
        String newBranchCMD = "git tag "+ tagName;

        /*不用关心执行结果 确保该分支存在即可*/
        executeCommand(preCMD + " && " + newBranchCMD);

        String selectBranchCMD = "git checkout "+tagName;
        return executeCommand(preCMD + " && " + selectBranchCMD);
    }

    @Override
    public Result clean(String applicationKey, String localAddress) {
        DefaultResult.DefaultResultBuilder builder = DefaultResult.builder().success(true);
        String appLocalAddress = localAddress + File.separator + applicationKey;

        try {
            Files.walkFileTree(Paths.get(appLocalAddress), new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.deleteIfExists(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    Files.deleteIfExists(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
            builder.success(true);
        } catch (IOException e) {
            builder.success(false);
            builder.message("can not delete file; "+ e.getMessage());
            log.error("can not delete application workspace", e);
        }
        return builder.build();
    }

    private Result executeCommand(String command) {
        DefaultResult.DefaultResultBuilder builder = DefaultResult.builder().success(true);
        try {
            Process exec = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("fatal")) {
                    builder.success(false);
                    builder.message(line.substring(7));
                    break;
                }
            }
        } catch (IOException e) {
            log.error("clone fail", e);
            builder.success(false);
            builder.message("can not connect to code repository");
        }
        return builder.build();
    }
}
