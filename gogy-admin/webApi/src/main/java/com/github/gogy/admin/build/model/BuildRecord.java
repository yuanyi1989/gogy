package com.github.gogy.admin.build.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author yuanyi
 * @date 2018/1/24
 */
@Builder
@Setter
@Getter
@Document
public class BuildRecord {

    @Id
    private String id;

    /**
     * 构建版本号 构建失败时，无版本号
     */
    private String buildVersion;

    /**
     * 应用key
     */
    private String applicationKey;

    /**
     * 构建时间
     */
    private LocalDateTime buildTime;

}
