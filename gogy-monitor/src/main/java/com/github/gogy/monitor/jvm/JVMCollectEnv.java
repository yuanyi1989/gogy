package com.github.gogy.monitor.jvm;

import com.github.gogy.monitor.CollectEnv;
import com.github.gogy.monitor.jvm.config.JVMConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
public class JVMCollectEnv implements CollectEnv {

    @Getter
    @Setter
    private MBeanConnectionProvider provider;

    @Getter
    @Setter
    private JVMConfig config;

}
