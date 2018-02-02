package com.github.gogy.admin.resource.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuanyi
 * @date 2018/1/30
 */
@Data
@AllArgsConstructor
public class CpuInfo {
    private String model;
    private String vendor;
    private String totalCores;
    private String mhz;
    private String cacheSize;
}
