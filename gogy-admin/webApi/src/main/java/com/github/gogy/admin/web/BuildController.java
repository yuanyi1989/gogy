package com.github.gogy.admin.web;

import com.github.gogy.admin.Constants;
import com.github.gogy.admin.application.model.Application;
import com.github.gogy.admin.application.repository.ApplicationRepository;
import com.github.gogy.admin.build.BuildFacade;
import com.github.gogy.admin.build.repository.BuildRepository;
import com.github.gogy.admin.web.message.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanyi
 * @date 2018/1/24
 */
@RestController
public class BuildController {

    @Autowired
    private BuildRepository buildRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private BuildFacade buildFacade;

    /**
     * 执行构建
     * @return
     */
    @RequestMapping(value = "/builds", method = RequestMethod.POST)
    public DefaultResponse build(@RequestParam("applicationKey") String applicationKey,
                                 @RequestParam("branch") String branch) {
        Application application = applicationRepository.findByKey(applicationKey);
        if (application == null) {
            return DefaultResponse.builder().status(Constants.Status.FAIL.getCode()).message("application not exist").build();
        }

        //提交构建任务
        buildFacade.build(application, branch);

        return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).message("start building").build();
    }
}
