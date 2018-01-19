/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin;

import com.github.gogy.admin.application.model.Application;
import com.github.gogy.admin.application.repository.ApplicationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yuanyi
 * @date 2018/1/18
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplication.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Resource
    private MongoTemplate mongoTemplate;

    @Before
    public void setUp() {
        //applicationRepository.deleteAll();
    }

    @Test
    public void testFetch() {

        List<Application> applications = mongoTemplate.find(Query.query(Criteria.where("type").is("api")), Application.class);

        applications.stream().forEach(application -> System.out.println(application.getName()));
    }

    @Test
    public void test() throws Exception {

        List<Application> all = applicationRepository.findAll();
        // 创建三个User，并验证User总数
        Application app1 = Application.builder()
                .id("id_1")
                .buildType("maven")
                .name("算法包")
                .key("popupserivce")
                .group("V0")
                .state(-1)
                .type("api")
                .chargeMan("袁意")
                .repository("http://git.mfexcel.com/sdkapi/popupservice.git")
                .build();
        Example<Application> ex = Example.of(app1, ExampleMatcher.matchingAny());
        Application app2 = Application.builder()
                .id("id_2")
                .buildType("maven")
                .name("企业资料")
                .key("publicNumService")
                .group("V0")
                .state(0)
                .type("api")
                .chargeMan("刘洋飞")
                .repository("http://git.mfexcel.com/sdkapi/publicNumService.git")
                .build();

        Application app3 = Application.builder()
                .id("id_3")
                .buildType("maven")
                .name("华为号码配置")
                .key("numberConfig")
                .group("V2")
                .state(1)
                .type("api")
                .chargeMan("梁艳萍")
                .repository("http://git.mfexcel.com/sdkapi/data/numberConfig.git")
                .build();

        Application app4 = Application.builder()
                .id("id_4")
                .buildType("maven")
                .name("陌电")
                .key("phone")
                .group("V2")
                .state(1)
                .type("api")
                .chargeMan("刘涛")
                .repository("http://git.mfexcel.com/sdkapi/data/numberConfig.git")
                .build();

        Application app5 = Application.builder()
                .id("id_5")
                .buildType("maven")
                .name("服务规则")
                .key("serviceRule")
                .group("V2")
                .state(1)
                .type("api")
                .chargeMan("钟正涛")
                .repository("http://git.mfexcel.com/sdkapi/data/numberConfig.git")
                .build();

        Application app6 = Application.builder()
                .id("id_6")
                .buildType("maven")
                .name("V2算法包")
                .key("version")
                .group("V2")
                .state(1)
                .type("api")
                .chargeMan("刘涛")
                .repository("http://git.mfexcel.com/sdkapi/data/numberConfig.git")
                .build();

        applicationRepository.save(app1);
        applicationRepository.save(app2);
        applicationRepository.save(app3);
        applicationRepository.save(app4);
        applicationRepository.save(app5);
        applicationRepository.save(app6);
        Assert.assertEquals(6, applicationRepository.findAll().size());


    }

}
