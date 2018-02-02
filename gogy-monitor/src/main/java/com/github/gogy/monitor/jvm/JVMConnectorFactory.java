package com.github.gogy.monitor.jvm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
@Slf4j
@Component
public class JVMConnectorFactory {

    private Map<String, JMXConnector> connectorCache = new HashMap<>();

    public void removeBrokenConnector(String jmxUrl) {
        connectorCache.remove(jmxUrl);
    }

    public JMXConnector connect(String jmxUrl) {
        if(StringUtils.isEmpty(jmxUrl)) {
            return null;
        }

        if (connectorCache.containsKey(jmxUrl)) {
            return connectorCache.get(jmxUrl);
        }

        log.debug("create connection to JMX; jmxUrl={}", jmxUrl);

        try {
            JMXServiceURL e = new JMXServiceURL(jmxUrl);
            JMXConnector jmxConnector = javax.management.remote.JMXConnectorFactory.connect(e);
            connectorCache.put(jmxUrl, jmxConnector);
            return jmxConnector;
        } catch (Exception e) {
            //monitor为本机监控，不应该出现异常
            log.error("can not create JMXConnector; ", e);
            return null;
        }
    }
}
