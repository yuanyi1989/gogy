package com.github.gogy.monitor.jvm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.io.IOException;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
@Slf4j
public class JVMMetricHelper {

    private MBeanServerConnection connection;
    private ObjectName objectName;

    public JVMMetricHelper(MBeanServerConnection connection, ObjectName name) {
        this.connection = connection;
        this.objectName = name;
    }

    public String getString(String name) {
        return (String)this.getAttribute(name, "", String.class);
    }

    public String[] getStringArray(String name) throws Exception {
        return !StringUtils.hasText(name) ? null : this.getAttribute(name, new String[0], String[].class);
    }

    public Integer getInteger(String name) throws Exception {
        return this.getAttribute(name, Integer.valueOf(0), Integer.class);
    }

    public Long getLong(String name) throws Exception {
        return this.getAttribute(name, Long.valueOf(0L), Long.class);
    }

    public Double getDouble(String name) throws Exception {
        return this.getAttribute(name, Double.valueOf(0.0D), Double.class);
    }

    private <T> T getAttribute(String name, T defaultValue, Class<T> cls) {
        try {
            if(StringUtils.isEmpty(name)) {
                return defaultValue;
            } else {
                Object obj = this.connection.getAttribute(this.objectName, name);
                if(cls.isInstance(obj)) {
                    return (T)obj;
                } else {
                    log.warn("attribute type error:" + this.objectName + "/" + name + ", expect:" + cls + ", actual:" + obj.getClass().getName());
                    return defaultValue;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.warn("attribute exception:" + this.objectName + "/" + name + "e:" + e.getClass() + ", m:" + e.getMessage());
            return defaultValue;
        }
    }

}
