package com.github.gogy.admin;

import com.github.gogy.admin.resource.VMResourceService;
import com.github.gogy.admin.resource.model.VMResource;
import com.github.gogy.admin.web.message.PageMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yuanyi
 * @date 2018/1/31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplication.class)
@SpringBootTest
public class VMResourceTest {

    @Resource
    private VMResourceService service;

    @Test
    public void testResource() {
        PageMessage pageMessage = service.listAll(null, null, 0, Integer.MAX_VALUE);
        List data = pageMessage.getData();
        data.stream().forEach(resource -> {
            VMResource vmResource = (VMResource) resource;
            System.out.println(vmResource.getSerialNumber());
            System.out.println(vmResource.getEnv());
        });
    }
}
