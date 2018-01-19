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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Before
    public void setUp() {
        applicationRepository.deleteAll();
    }

    @Test
    public void test() throws Exception {

        // 创建三个User，并验证User总数
        Application app1 = Application.builder()
                .id("id_1")
                .buildType("maven")
                .name("算法包")
                .key("popupserivce")
                .group("api")
                .state(0)
                .repository("http://git.mfexcel.com/sdkapi/popupservice.git")
                .build();

        Application app2 = Application.builder()
                .id("id_2")
                .buildType("maven")
                .name("企业资料")
                .key("publicNumService")
                .group("api")
                .state(0)
                .repository("http://git.mfexcel.com/sdkapi/publicNumService.git")
                .build();

        Application app3 = Application.builder()
                .id("id_3")
                .buildType("maven")
                .name("华为号码配置")
                .key("numberConfig")
                .group("api")
                .state(0)
                .repository("http://git.mfexcel.com/sdkapi/data/numberConfig.git")
                .build();

        applicationRepository.save(app1);
        applicationRepository.save(app2);
        applicationRepository.save(app3);
        Assert.assertEquals(3, applicationRepository.findAll().size());


    }

}
