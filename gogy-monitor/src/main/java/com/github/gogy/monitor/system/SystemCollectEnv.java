package com.github.gogy.monitor.system;

import com.github.gogy.monitor.CollectEnv;
import com.github.gogy.monitor.SigarProvider;
import lombok.Getter;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
public class SystemCollectEnv implements CollectEnv {

    @Getter
    private SigarProvider sigarProvider;

}