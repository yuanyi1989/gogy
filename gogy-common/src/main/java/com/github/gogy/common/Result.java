/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.common;

/**
 * @author yuanyi
 * @date 2018/1/22
 */
public interface Result {

    /**
     * 是否成功
     * @return
     */
    boolean isSuccess();

    /**
     * 响应的message
     * @return
     */
    String getMessage();

}
