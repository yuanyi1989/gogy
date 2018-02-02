package com.github.gogy.monitor;

import com.github.gogy.monitor.system.SystemBaseCollector;
import com.github.gogy.monitor.system.SystemCollectEnv;

/**
 * @author yuanyi
 * @date 2018/1/30
 */
public class SystemBaseCollecterTest {

    public static void main(String[] args) throws Exception {

        SigarProvider provider = new SigarProvider();
        SystemCollectEnv env = new SystemCollectEnv();
        env.setSigarProvider(provider);

        new SystemBaseCollector().collect(env);
    }

}
