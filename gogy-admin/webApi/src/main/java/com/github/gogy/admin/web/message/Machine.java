package com.github.gogy.admin.web.message;

import lombok.Data;

/**
 * @author yuanyi
 * @date 2018/2/1
 */
@Data
public class Machine {

    private String id;


    private String serialNumber;

    /**
     * 别名
     */
    private String alias;

    /**
     * 资源分组
     */
    private String group;


}
