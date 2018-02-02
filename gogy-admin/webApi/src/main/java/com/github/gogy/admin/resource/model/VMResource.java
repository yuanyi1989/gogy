package com.github.gogy.admin.resource.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author yuanyi
 * @date 2018/1/30
 */
@Builder
@Setter
@Getter
@Document
@ToString
public class VMResource {

    @Id
    private String id;

    private String serialNumber;

    /**
     * 别名
     */
    private String alias;

    private List<String> address;

    private CpuInfo cpuInfo;

    /**
     * 资源分组
     */
    private String group;

    /**
     * 环境 一个环境下包含多个分组
     */
    private String env;

    /**
     * 状态 0：未监控  1：正常  -1：异常
     */
    private int status;

}
