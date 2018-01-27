package com.github.gogy.admin.config;

import com.github.gogy.build.BuildService;
import com.github.gogy.build.code.CodeRepositoryService;
import com.github.gogy.build.code.git.GitRepositoryServiceImpl;
import com.github.gogy.build.maven.MavenBuildServiceImpl;
import com.github.gogy.build.store.LocalDiskStore;
import com.github.gogy.build.store.Store;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuanyi
 * @date 2018/1/24
 */
@Configuration
@ConfigurationProperties(prefix = "gogy.build")
public class BuildConfiguration {

    private String codeDataDir;
    private String buildStoreDir;

    @Bean
    public CodeRepositoryService exportCodeRepositoryService() {
        return new GitRepositoryServiceImpl();
    }

    @Bean
    public Store exportStore() {
        return new LocalDiskStore(buildStoreDir);
    }

    @Bean
    public BuildService exportBuildService(Store store, CodeRepositoryService codeRepositoryService) {
        return new MavenBuildServiceImpl(store, codeRepositoryService, codeDataDir);
    }


}
