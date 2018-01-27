/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.build;

import com.github.gogy.build.code.CodeRepositoryService;
import com.github.gogy.build.code.git.GitRepositoryServiceImpl;
import com.github.gogy.build.maven.MavenBuildServiceImpl;
import com.github.gogy.build.store.LocalDiskStore;
import com.github.gogy.build.store.Store;
import com.github.gogy.common.Result;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author yuanyi
 * @date 2018/1/22
 */
public class MavenCommandTest {

    private String localAddress = "D:\\gitCmdTest\\onlineteam\\sdkapi";
    private String remoteAddress = "http://git.mfexcel.com/sdkapi/location.git";
    private String applicationKey = "location-git";
    private CodeRepositoryService service = new GitRepositoryServiceImpl();

    private BuildService buildService;

    private Store store;

    @Before
    public void setUp() {
        store = new LocalDiskStore("/data/build/");
        /*store = new AliyunOSSStore(false, "/data/build/store/aliyun/",
                "oss-cn-shenzhen", "LTAIanGcIxH02A2g", "L2pUsEcHB8jDkAjlzBE0mPLE1izlaf");
*/
        buildService = new MavenBuildServiceImpl(store, service, localAddress);
    }

    @Test
    public void testMaven() {

       /* Path name = Paths.get("D:\\gitCmdTest\\onlineteam\\sdkapi\\location-test\\target\\build\\location-module-2.0.0-SNAPSHOT.jar").getFileName();
        System.out.println(name);
*/

        BuildResult master = buildService.build("location-test",
                "http://git.mfexcel.com/sdkapi/location.git", true, "master", false);

        if (!master.isSuccess()) {
            System.out.println(master.getMessage());
            return;
        }
        File buildFile = buildService.getBuildFile("location-test", master.getBuildId());

        System.out.println(buildFile.toString());
    }

    @Test
    public void testGit() throws IOException {

        /*Result clean = service.clean(applicationKey, localAddress);
        System.out.println("clean ==> "+clean.isSuccess()+"; message ==> "+clean.getMessage());*/


        Result pullResult = service.pull(applicationKey, remoteAddress, localAddress);
        if (!pullResult.isSuccess()) {
            System.out.println("pull ==> "+pullResult.getMessage());
            return;
        }

        Result branchResult = service.selectBranch("master", applicationKey, localAddress);
        if (!branchResult.isSuccess()) {
            System.out.println("branch ==> "+pullResult.getMessage());
            return;
        }

        System.out.println("execute success");
    }

}
