package com.github.gogy.monitor.system;

import com.github.gogy.monitor.Collector;
import com.github.gogy.monitor.Metric;
import com.github.gogy.monitor.SigarProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
@Component
public class LoadAverageCollector implements Collector<SystemCollectEnv> {

    @Autowired
    SigarProvider sigarProvider;

    @Override
    public Metric collect(SystemCollectEnv env) throws Exception {
        Metric metric = Metric.valueOf("system.load");
        double[] loadAverage = sigarProvider.getProxy().getLoadAverage();
        metric.put("load_1", loadAverage[0]);
        metric.put("load_5", loadAverage[1]);
        metric.put("load_15", loadAverage[2]);
        return metric;
    }
}
