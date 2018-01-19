/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin.web;

import com.github.gogy.admin.Constants;
import com.github.gogy.admin.application.model.Application;
import com.github.gogy.admin.application.repository.ApplicationRepository;
import com.github.gogy.admin.web.message.DefaultResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author yuanyi
 * @date 2018/1/18
 */
@RestController
public class ApplicationController {

    @Resource
    private ApplicationRepository applicationRepository;

    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public DefaultResponse findAll(@RequestParam(required = false) String key) {
        Object allApplication = Objects.isNull(key) ? applicationRepository.findAll() : applicationRepository.findByKey(key);
        return DefaultResponse.builder().body(allApplication).status(Constants.Status.SUCCESS.getCode()).build();
    }

    @RequestMapping(value = "/applications/{id}", method = RequestMethod.GET)
    public DefaultResponse findOne(String id) {
        Application application = applicationRepository.findOne(id);
        if (Objects.isNull(application)) {
            return DefaultResponse.builder().message("Not Found").status(Constants.Status.NOT_FOUND.getCode()).build();
        }
        return DefaultResponse.builder().body(application).status(Constants.Status.SUCCESS.getCode()).build();
    }

    @RequestMapping(value = "/applications", method = RequestMethod.POST)
    public Application create(Application app) {
        Application save = applicationRepository.save(app);
        return save;
    }

    @RequestMapping(value = "/applications/{id}", method = RequestMethod.PUT)
    public Application update(@PathVariable("id") String id, Application app) {
        app.setId(id);
        return applicationRepository.save(app);

    }

    @RequestMapping(value = "/applications/{id}", method = RequestMethod.DELETE)
    public DefaultResponse delete(@PathVariable("id") String id) {
        applicationRepository.delete(id);
        return DefaultResponse.builder().message("SUCCESS").status(Constants.Status.SUCCESS.getCode()).build();
    }

}
