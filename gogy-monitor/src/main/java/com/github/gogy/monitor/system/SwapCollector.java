package com.github.gogy.monitor.system;

import com.github.gogy.monitor.Collector;
import com.github.gogy.monitor.Metric;
import com.github.gogy.monitor.SigarProvider;
import org.hyperic.sigar.Swap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
@Component
public class SwapCollector implements Collector<SystemCollectEnv> {

    @Autowired
    private SigarProvider sigarProvider;

    @Override
    public Metric collect(SystemCollectEnv env) throws Exception {
        Metric metric = Metric.valueOf("system.swap");
        Swap swap = sigarProvider.getProxy().getSwap();
        //剩余
        metric.put("free", swap.getFree());
        //
        metric.put("pageIn", swap.getPageIn());
        metric.put("pageOut", swap.getPageOut());
        //总计
        metric.put("total", swap.getTotal());
        //已使用
        metric.put("used", swap.getUsed());
        return metric;
    }
}
