package com.github.gogy.monitor.http;

import com.github.gogy.common.DefaultResult;
import com.github.gogy.common.Result;
import com.github.gogy.monitor.Metric;
import com.github.gogy.monitor.jvm.JVMCollectEnv;
import com.github.gogy.monitor.jvm.MBeanConnectionProvider;
import com.github.gogy.monitor.jvm.collector.ThreadCollector;
import com.github.gogy.monitor.jvm.config.ConfigServiceImpl;
import com.github.gogy.monitor.jvm.config.JVMConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Collection;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
@Slf4j
@RestController
public class MonitorController {

    @Autowired
    private ThreadCollector threadCollector;

    @Autowired
    private MBeanConnectionProvider provider;

    @Autowired
    private ConfigServiceImpl configService;

    @RequestMapping(value = "/jvm/configs", method = RequestMethod.GET)
    public Collection<JVMConfig> getJvmConfig() {
        return configService.listAllConfig();
    }

    @RequestMapping(value = "/jvm/configs", method = RequestMethod.POST)
    public Result addJvmConfig(JVMConfig config) {
        if (!config.isAvailable()) {
            return DefaultResult.builder().message("config not available").build();
        }
        return configService.addNew(config);
    }

    @RequestMapping(value = "/jvm/configs", method = RequestMethod.PUT)
    public Result updateJvmConfig(JVMConfig config) {
        if (!config.isAvailable()) {
            return DefaultResult.builder().message("config not available").build();
        }
        return configService.update(config);
    }

    @RequestMapping(value = "/jvm/threads", method = RequestMethod.GET)
    public Metric jvmThreadInfo(@PathParam("host") String host, @PathParam("port") int port) {
        JVMConfig config = new JVMConfig();
        config.setPort(port);
        config.setHost(host);

        JVMCollectEnv env = new JVMCollectEnv();
        env.setConfig(config);
        env.setProvider(provider);

        try {
            return threadCollector.collectThreadDetail(env);
        } catch (Exception e) {
            log.error("can not collect thread info", e);
            Metric metric = Metric.valueOf("jvm.thread.detail");
            metric.put("value", "exception happen:"+e.getMessage());
            return metric;
        }
    }

}
