package com.github.gogy.monitor.system;

import com.github.gogy.monitor.Collector;
import com.github.gogy.monitor.Metric;
import com.github.gogy.monitor.SigarProvider;
import org.hyperic.sigar.Mem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
@Component
public class MemeryCollector implements Collector<SystemCollectEnv> {

    @Autowired
    private SigarProvider sigarProvider;

    @Override
    public Metric collect(SystemCollectEnv env) throws Exception {
        Metric metric = Metric.valueOf("system.memery");
        Mem mem = sigarProvider.getProxy().getMem();
        //ram ??
        metric.put("ram", mem.getRam());
        //共计内存
        metric.put("total", mem.getTotal());
        //剩余
        metric.put("free", mem.getFree());
        //使用
        metric.put("used", mem.getUsed());
        //实际剩余
        metric.put("actual_free", mem.getActualFree());
        //实际使用
        metric.put("actual_used", mem.getActualUsed());
        //空闲比例
        metric.put("free_percent", mem.getFreePercent());
        //使用比例
        metric.put("used_percent", mem.getUsedPercent());
        return metric;
    }
}
