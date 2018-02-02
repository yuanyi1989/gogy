package com.github.gogy.monitor.jvm.collector;

import com.github.gogy.monitor.Collector;
import com.github.gogy.monitor.Metric;
import com.github.gogy.monitor.jvm.JVMCollectEnv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.management.MBeanServerConnection;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
@Slf4j
@Component
public class ThreadCollector implements Collector<JVMCollectEnv> {

    @Override
    public Metric collect(JVMCollectEnv env) throws Exception {
        Metric metric = Metric.valueOf("jvm.thread");
        MBeanServerConnection mBeanServerConnection = env.getProvider().connect(env.getConfig().getJMXUrl());

        if (mBeanServerConnection == null) {
            log.info("can not connect to mBean server, skip");
            return metric;
        }
        metric.put("port", String.valueOf(env.getConfig().getPort()));
        metric.put("host", env.getConfig().getHost());

        ThreadMXBean threadBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection, "java.lang:type=Threading", ThreadMXBean.class);
        metric.put("count", (long)threadBean.getThreadCount());
        metric.put("peak_count", (long)threadBean.getPeakThreadCount());
        metric.put("deamon_count", (long)threadBean.getDaemonThreadCount());
        long[] deadlockedThreads = threadBean.findDeadlockedThreads();
        metric.put("deadLock_count", deadlockedThreads == null ? 0 : deadlockedThreads.length);

        return metric;
    }

    public Metric collectThreadDetail(JVMCollectEnv env) throws Exception {
        Metric metric = Metric.valueOf("jvm.thread.detail");
        MBeanServerConnection mBeanServerConnection = env.getProvider().connect(env.getConfig().getJMXUrl());

        if (mBeanServerConnection == null) {
            log.info("can not connect to mBean server, skip");
            return metric;
        }

        metric.put("port", String.valueOf(env.getConfig().getPort()));
        metric.put("host", env.getConfig().getHost());

        ThreadMXBean threadBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection, "java.lang:type=Threading", ThreadMXBean.class);

        ThreadInfo[] threadInfos = threadBean.dumpAllThreads(false, false);
        for (ThreadInfo info : threadInfos) {
            String threadName = info.getThreadName();
            Metric subMetric = Metric.valueOf(threadName);
            Thread.State threadState = info.getThreadState();
            long waitedCount = info.getWaitedCount();
            long waitedTime = info.getWaitedTime();
            StackTraceElement[] stackTrace = info.getStackTrace();

            subMetric.put("state", threadState.name());
            subMetric.put("waited_count", waitedCount);
            subMetric.put("waited_time", waitedTime);
            subMetric.put("stack_trace", stackTrace);

            metric.put(threadName, subMetric);
        }

        return metric;
    }

}
