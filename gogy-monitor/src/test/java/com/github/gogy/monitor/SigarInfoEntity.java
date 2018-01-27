package com.github.gogy.monitor;

import lombok.Data;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
@Data
public class SigarInfoEntity {
    private String value;
    private String name;

    public SigarInfoEntity(String value, String name) {
        super();
        this.value = value;
        this.name = name;
    }

    public SigarInfoEntity(){

    }
}
