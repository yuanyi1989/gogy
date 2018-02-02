package com.github.gogy.admin.build;

import com.github.gogy.admin.application.model.Application;
import com.github.gogy.admin.build.model.BuildRecord;
import com.github.gogy.admin.build.repository.BuildRepository;
import com.github.gogy.admin.config.BuildConfiguration;
import com.github.gogy.build.BuildResult;
import com.github.gogy.build.BuildService;
import com.github.gogy.build.code.CodeRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author yuanyi
 * @date 2018/1/24
 */
@Component
public class BuildFacade {

    @Autowired
    private BuildService buildService;
    @Autowired
    private BuildRepository buildRepository;
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private CodeRepositoryService repositoryService;
    @Resource
    private BuildConfiguration config;

    @Async
    public Future<BuildResult> build(Application application, String branchName) {
        BuildRecord.BuildRecordBuilder buildRecordBuilder = BuildRecord.builder().applicationKey(application.getKey())
                .buildTime(LocalDateTime.now());

        BuildResult build = buildService.build(application.getKey(), application.getRepository(), application.getPackagePath(), true, branchName, false);
        buildRecordBuilder.success(build.isSuccess());
        buildRecordBuilder.buildVersion(build.getBuildId());
        buildRepository.insert(buildRecordBuilder.build());
        return new AsyncResult<>(build);
    }

    public List<String> allBranch(Application application) {
        return repositoryService.listBranches(application.getKey(), application.getRepository(), config.getCodeDataDir());
    }

    public List<String> allTag(Application application) {
        return repositoryService.listTags(application.getKey(), application.getRepository(), config.getCodeDataDir());
    }


    public List<BuildRecord> listAll(String applicationKey, int pageNo, int pageSize) {
        Criteria criteria = Criteria.where("applicationKey").is(applicationKey);
        List<BuildRecord> buildRecords = mongoTemplate.find(Query.query(criteria), BuildRecord.class);
        List<BuildRecord> allRecords = buildRecords.stream().skip(pageNo * pageSize).limit(pageSize).collect(Collectors.toList());
        return allRecords;
    }

}
