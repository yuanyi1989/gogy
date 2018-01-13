/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin.api;

import lombok.Builder;
import lombok.Getter;

/**
 * @author yuanyi
 * @date 2018/1/13
 */
@Getter
@Builder
public class DefaultResponse {

    private int status;
    private String msg;
    private Object data;

}
