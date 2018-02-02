package com.github.gogy.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yuanyi
 * @date 2018/1/31
 */
@Data
@Configuration
@ConfigurationProperties(prefix="gogy")
public class GogyConfig {

    private List<Map<String, String>> env = new ArrayList<>();

}
