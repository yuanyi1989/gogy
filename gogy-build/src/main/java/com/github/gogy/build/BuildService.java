package com.github.gogy.build;

import java.io.File;

/**
 * 执行构建以及获取构建后的文件
 * @author yuanyi
 * @date 2018/1/22
 */
public interface BuildService {

    /**
     * 构建应用
     * @param applicationKey 应用key，全局唯一
     * @param codeRepository 应用的代码仓库地址
     * @param branch 是否使用分支代码打包 true:使用分支  false：使用标签
     * @param branchOrTagName 分支或者标签的名称
     * @param withBuildFile 返回result时，是否包含构建成功的文件
     */
    BuildResult build(String applicationKey, String codeRepository, boolean branch, String branchOrTagName, boolean withBuildFile);


    /**
     * 通过指定的构建ID获取指定的构建应用结果
     * @param applicationKey 应用标识
     * @param buildId 构建ID
     * @return 返回构建后的结果，根据构建库的实现，返回值可能为null（如：时间久远后过期）
     */
    File getBuildFile(String applicationKey, String buildId);

}
