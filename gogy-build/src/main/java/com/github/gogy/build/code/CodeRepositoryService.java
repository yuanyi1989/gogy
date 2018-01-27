package com.github.gogy.build.code;

import com.github.gogy.common.Result;

/**
 * @author yuanyi
 * @date 2018/1/22
 */
public interface CodeRepositoryService {

    /**
     * 下载代码
     * @param applicationKey 应用key
     * @param address 远端代码仓库的地址
     * @param localAddress 本地代码库的地址
     */
    Result clone(String applicationKey, String address, String localAddress);

    /**
     * 更新代码
     * @param applicationKey
     * @param address
     * @param localAddress
     * @return
     */
    Result pull(String applicationKey, String address, String localAddress);

    /**
     * 选择分支
     * @param branchName
     */
    Result selectBranch(String branchName, String applicationKey, String localAddress);

    /**
     * 选择标签
     * @param tagName
     */
    Result selectTag(String tagName, String applicationKey, String localAddress);


    /**
     * 清空本地文件
     * @param applicationKey
     * @return
     */
    Result clean(String applicationKey, String localAddress);

}
