package com.github.gogy.build;


import com.github.gogy.common.Result;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * @author yuanyi
 * @date 2018/1/22
 */
@Getter
@Setter
@Builder
public class BuildResult implements Result {

    /**
     * 是否构建成功
     */
    private boolean success;

    /**
     * 构建失败时，返回失败信息
     */
    private String message;

    /**
     * 构建成功时，返回构建ID
     */
    private String buildId;

    /**
     * 构建成功时，返回构建的文件
     */
    private File buildFile;

    /**
     * 构建完成后，文件的MD5值
     */
    private String MD5;

}
