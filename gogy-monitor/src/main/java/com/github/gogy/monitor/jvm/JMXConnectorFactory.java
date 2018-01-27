package com.github.gogy.monitor.jvm;

import com.aliyun.tianji.cloudmonitor.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
@Slf4j
public class JMXConnectorFactory {

    private Map<String, JMXConnector> connectorCache = new HashMap<>();

    public JMXConnector connect(String jmxUrl) {
        if(StringUtil.isEmpty(jmxUrl)) {
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
