package com.github.gogy.admin.web;

import com.github.gogy.admin.Constants;
import com.github.gogy.admin.application.model.Application;
import com.github.gogy.admin.application.repository.ApplicationRepository;
import com.github.gogy.admin.build.BuildFacade;
import com.github.gogy.admin.build.model.BuildRecord;
import com.github.gogy.admin.web.message.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author yuanyi
 * @date 2018/1/24
 */
@RestController
public class BuildController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private BuildFacade buildFacade;

    /**
     * 执行构建
     * @return
     */
    @RequestMapping(value = "/builds/{applicationKey}", method = RequestMethod.POST)
    public DefaultResponse build(@PathVariable("applicationKey") String applicationKey,
                                 @RequestParam("branch") String branch) {
        Application application = applicationRepository.findByKey(applicationKey);
        if (application == null) {
            return DefaultResponse.builder().status(Constants.Status.FAIL.getCode()).message("application not exist").build();
        }

        //提交构建任务
        buildFacade.build(application, branch);

        return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).message("start building").build();
    }

    @RequestMapping(value = "/builds/{applicationKey}", method = RequestMethod.GET)
    public DefaultResponse buildList(@PathVariable("applicationKey") String applicationKey,
                                     @PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize) {
        List<BuildRecord> buildRecords = buildFacade.listAll(applicationKey, pageNo, pageSize);
        return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).body(buildRecords).build();
    }

    @RequestMapping(value = "/builds/{applicationKey}/branches", method = RequestMethod.GET)
    public DefaultResponse getAllBranch(@PathVariable("applicationKey") String applicationKey) {
        Application application = applicationRepository.findByKey(applicationKey);
        List<String> branches = buildFacade.allBranch(application);
        return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).body(branches).build();
    }

    @RequestMapping(value = "/builds/{applicationKey}/tags", method = RequestMethod.GET)
    public DefaultResponse getAllTags(@PathVariable("applicationKey") String applicationKey) {
        Application application = applicationRepository.findByKey(applicationKey);
        List<String> tags = buildFacade.allTag(application);
        return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).body(tags).build();
    }
}
