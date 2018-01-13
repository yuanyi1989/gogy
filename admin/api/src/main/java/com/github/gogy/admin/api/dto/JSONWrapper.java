/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin.api.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanyi
 * @date 2018/1/13
 */
public class JSONWrapper {

    private Map<String, Object> data = new HashMap<>();

    public void put(String key, Object data) {
        this.data.put(key, data);
    }

    public Map<String, Object> export() {
        return data;
    }

}
