package com.github.gogy.admin.web.message;

import lombok.Data;

/**
 * @author yuanyi
 * @date 2018/1/31
 */
@Data
public class EnvData {

    private String key;
    private String name;
    private long total;
    private long working;
    private long down;
    private long unwatch;

}
