package com.github.gogy.monitor.jvm;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
@Data
public class JVMConfig {

    private static final String JMX_URL_PATTERN = "service:jmx:rmi:///jndi/rmi://HOST:PORT/jmxrmi";
    private String host;
    private Integer port;

    public String getJMXUrl() {
        if (!StringUtils.hasText(host)) {
            return null;
        }

        if (this.port == null || this.port.intValue() <= 0 || this.port.intValue() > Character.MAX_VALUE) {
            return null;
        }

        return JMX_URL_PATTERN.replace("HOST", this.host).replace("PORT", String.valueOf(this.port));
    }
}
