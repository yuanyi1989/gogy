package com.github.gogy.admin.deploy;

import com.github.gogy.admin.build.model.BuildRecord;

/**
 * @author yuanyi
 * @date 2018/2/3
 */
public interface DeployService {

    void deploy(BuildRecord record);

}
