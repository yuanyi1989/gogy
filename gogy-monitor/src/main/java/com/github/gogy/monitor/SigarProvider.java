package com.github.gogy.monitor;

import lombok.Getter;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarProxyCache;
import org.springframework.stereotype.Component;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
@Component
public class SigarProvider {

    @Getter
    private Sigar sigar = new Sigar();
    @Getter
    private SigarProxy proxy;

    public SigarProvider() {
        this.proxy = SigarProxyCache.newInstance(this.sigar, 100);
    }

}
