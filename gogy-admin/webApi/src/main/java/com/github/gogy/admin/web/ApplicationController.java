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
import com.github.gogy.admin.web.message.PageMessage;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yuanyi
 * @date 2018/1/18
 */
@RestController
public class ApplicationController {

    @Resource
    private ApplicationRepository applicationRepository;

    @Resource
    private MongoTemplate mongoTemplate;


    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public DefaultResponse findAll(@RequestParam String token, @RequestParam(required = false)String key, String type, int pageSize, int currentPage) {
        List<Application> applications =  mongoTemplate.find(Query.query(Criteria.where("type").is(type)), Application.class);
        applications = applications.stream().filter(app -> app.getKey().contains(key)).collect(Collectors.toList());
        List<Application> data = applications.stream().skip(currentPage * pageSize).limit(pageSize).collect(Collectors.toList());
        PageMessage retValue = PageMessage.builder().total(applications.size()).currentPage(currentPage).pageSize(pageSize).data(data).build();
        return DefaultResponse.builder().body(retValue).status(Constants.Status.SUCCESS.getCode()).build();
    }

    @RequestMapping(value = "/applications/{id}", method = RequestMethod.GET)
    public DefaultResponse findOne(@PathVariable String id) {
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
