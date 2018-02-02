package com.github.gogy.monitor.jvm.config;

import com.github.gogy.common.DefaultResult;
import com.github.gogy.common.Result;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
@Component
public class ConfigServiceImpl {

    private Map<String, JVMConfig> configStore = new HashMap<>();

    public Collection<JVMConfig> listAllConfig() {
        return configStore.values();
    }

    public Result addNew(JVMConfig config) {
        if (configStore.containsKey(config.getApplicationKey())) {
            return DefaultResult.builder().success(false).message("vm already exist").build();
        }

        configStore.put(config.getApplicationKey(), config);
        return DefaultResult.builder().success(true).build();
    }

    public Result update(JVMConfig config) {
        if (!configStore.containsKey(config.getApplicationKey())) {
            return DefaultResult.builder().success(false).message("vm not exist").build();
        }

        configStore.put(config.getApplicationKey(), config);
        return DefaultResult.builder().success(true).build();
    }

    public void remove(String applicationKey) {
        configStore.remove(applicationKey);
    }
}
