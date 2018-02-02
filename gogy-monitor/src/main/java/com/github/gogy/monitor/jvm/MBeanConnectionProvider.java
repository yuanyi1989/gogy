package com.github.gogy.monitor.jvm;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import java.io.IOException;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
@Slf4j
@Component
public class MBeanConnectionProvider {

    @Setter
    @Autowired
    private JVMConnectorFactory connectorFactory;


    public MBeanServerConnection connect(String jmxUrl) {
        if(StringUtils.isEmpty(jmxUrl)) {
            return null;
        }

        JMXConnector connector = connectorFactory.connect(jmxUrl);

        if (connector == null) {
            return null;
        }

        try {
            MBeanServerConnection mBeanServerConnection = connector.getMBeanServerConnection();
            return mBeanServerConnection;
        } catch (IOException e) {
            log.error("broken connector; can not create mBeanServerConnection; ", e);
            connectorFactory.removeBrokenConnector(jmxUrl);
            return null;
        }
    }

}
