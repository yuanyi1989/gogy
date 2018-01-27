package com.github.gogy.monitor.system;

import com.github.gogy.monitor.Collector;
import com.github.gogy.monitor.Metric;
import com.github.gogy.monitor.SigarProvider;
import org.hyperic.sigar.CpuPerc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CPU指标收集器
 * @author yuanyi
 */
@Component
public class CpuCollector implements Collector<SystemCollectEnv> {

    @Autowired
    private SigarProvider sigarProvider;

    @Override
    public Metric collect(SystemCollectEnv env) throws Exception {
        Metric metric = Metric.valueOf("system.cpu");
        CpuPerc cpuPerc = sigarProvider.getProxy().getCpuPerc();
        //总计使用率
        metric.put("total", cpuPerc.getCombined());
        //空闲
        metric.put("idle", cpuPerc.getIdle());
        //硬件中断
        metric.put("irq", cpuPerc.getIrq());
        //软中断
        metric.put("soft_irq", cpuPerc.getSoftIrq());
        double nice = cpuPerc.getNice();
        double stolen = cpuPerc.getStolen();
        metric.put("other", nice+stolen);
        //内核态
        metric.put("sys", cpuPerc.getSys());
        //用户态
        metric.put("user", cpuPerc.getUser());
        //等待耗时，如IO
        metric.put("wait", cpuPerc.getWait());
        return metric;
    }
}
