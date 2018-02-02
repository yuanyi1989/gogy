package com.github.gogy.monitor.jvm.collector;

import com.github.gogy.monitor.Collector;
import com.github.gogy.monitor.Metric;
import com.github.gogy.monitor.jvm.JVMCollectEnv;
import com.github.gogy.monitor.jvm.JVMMetricHelper;
import com.github.gogy.monitor.jvm.ObjectNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.util.Iterator;
import java.util.List;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
@Slf4j
@Component
public class GCCollector implements Collector<JVMCollectEnv> {

    @Override
    public Metric collect(JVMCollectEnv env) throws Exception {
        Metric metric = Metric.valueOf("jvm.gc");
        MBeanServerConnection mBeanServerConnection = env.getProvider().connect(env.getConfig().getJMXUrl());

        if (mBeanServerConnection == null) {
            log.info("can not connect to mBean server, skip");
            return metric;
        }

        List<String> nameList = ObjectNameUtil.getNameList(mBeanServerConnection, "type=GarbageCollector");
        Iterator iterator = nameList.iterator();

        metric.put("port", String.valueOf(env.getConfig().getPort()));
        metric.put("host", env.getConfig().getHost());

        while(iterator.hasNext()) {
            String objectName = (String)iterator.next();
            Metric subMetric = Metric.valueOf(objectName);
            ObjectName gcName = new ObjectName(objectName);
            JVMMetricHelper helper = new JVMMetricHelper(mBeanServerConnection, gcName);
            String name = helper.getString("Name");
            Long collectionCount = helper.getLong("CollectionCount");
            Long collectionTime = helper.getLong("CollectionTime");
            subMetric.put("name", name);
            subMetric.put("collectionCount", collectionCount.longValue());
            subMetric.put("collectionTime", collectionTime.longValue());

            metric.put(name, subMetric);
            log.info("vm gc metric collect:" + name);

        }
        return metric;
    }
}
