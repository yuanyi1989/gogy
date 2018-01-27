package com.github.gogy.admin.build;

import com.github.gogy.admin.application.model.Application;
import com.github.gogy.build.BuildResult;
import com.github.gogy.build.BuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @author yuanyi
 * @date 2018/1/24
 */
@Component
public class BuildFacade {

    @Autowired
    private BuildService buildService;

    @Async
    public Future<BuildResult> build(Application application, String branchName) {
        BuildResult build = buildService.build(application.getKey(), application.getRepository(), true, branchName, false);
        return new AsyncResult<>(build);
    }

}
