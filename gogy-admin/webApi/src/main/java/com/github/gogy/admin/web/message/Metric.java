package com.github.gogy.admin.web.message;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 收集的指标信息
 * @author yuanyi
 * @date 2018/1/26
 */
public class Metric {

    /**指标名称或类型*/
    @Getter
    private String name;

    /**指标采集时间*/
    @Getter
    private long timestamp;

    /**采集项，采集内容*/
    @Getter
    private Map<String, Object> values = new HashMap<>();

    private Metric() {}

    public static Metric valueOf(String name, Object value) {
        Metric metric = new Metric();
        metric.name = name;
        metric.timestamp = System.currentTimeMillis();
        metric.values.put("value", value);
        return metric;
    }

    public static Metric valueOf(String name) {
        Metric metric = new Metric();
        metric.name = name;
        metric.timestamp = System.currentTimeMillis();
        return metric;
    }

    public void put(String key, Object value) {
        if (!StringUtils.hasText(key) || value == null) {
            throw new IllegalArgumentException("key and value can not be empty or null");
        }
        values.put(key, value);
    }

}
