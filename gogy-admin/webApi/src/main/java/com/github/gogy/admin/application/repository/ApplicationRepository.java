/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin.application.repository;

import com.github.gogy.admin.application.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author yuanyi
 * @date 2018/1/18
 */
public interface ApplicationRepository extends MongoRepository<Application, String> {

    /**
     * 通过应用的唯一关键字查找指定的应用
     * @param applicationKey
     * @return
     */
    Application findByKey(String applicationKey);

}
