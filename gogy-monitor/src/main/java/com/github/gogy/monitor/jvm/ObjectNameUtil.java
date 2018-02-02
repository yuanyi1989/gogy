package com.github.gogy.monitor.jvm;

import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import java.io.IOException;
import java.util.*;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
@Slf4j
public class ObjectNameUtil {

    public static List<String> getNameList(MBeanServerConnection connection, String pattern) {
        ArrayList objectNameList = new ArrayList();
        Set mBeanSet;

        try {
            mBeanSet = connection.queryMBeans(null, null);
        } catch (IOException e) {
            log.error("JMXConnector do not connect", e);
            return Collections.emptyList();
        }

        Iterator iterator = mBeanSet.iterator();

        while(iterator.hasNext()) {
            ObjectInstance objectInstance = (ObjectInstance)iterator.next();
            ObjectName objectName = objectInstance.getObjectName();
            String name = objectName.getCanonicalName();
            if(name != null && name.contains(pattern)) {
                objectNameList.add(name);
            }
        }

        return objectNameList;
    }
}
