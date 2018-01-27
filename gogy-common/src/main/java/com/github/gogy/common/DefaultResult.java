/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.common;

import lombok.Builder;

/**
 * @author yuanyi
 * @date 2018/1/22
 */
@Builder
public class DefaultResult implements Result {

    private boolean success;

    private String message;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
