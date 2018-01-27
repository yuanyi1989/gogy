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
public interface StateResult extends Result {

    int DEFAULT_SUCCESS = 0;

    /**
     * 状态值
     * @return
     */
    int getState();

    /**
     * 是否成功
     * @return
     */
    @Override
    default boolean isSuccess() {
        return getState() == DEFAULT_SUCCESS;
    }

}
