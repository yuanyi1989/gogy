/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin.application.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author yuanyi
 * @date 2018/1/18
 */
@Builder
@Setter
@Getter
@Document
public class Application {

    @Id
    private String id;

    /**
     * 所属组
     */
    private String group;

    /**
     * 中文名称
     */
    private String name;

    /**
     * 唯一标识
     */
    private String key;

    /**
     * 代码仓库地址
     */
    private String repository;

    /**
     * 应用状态 0：未上线  1: 正常  -1：异常
     */
    private int state;

    /**
     * 描述
     */
    private String description;

    /**
     * 负责人
     */
    private String chargeMan;

    /**
     * 构建方式
     */
    private String buildType;

}
