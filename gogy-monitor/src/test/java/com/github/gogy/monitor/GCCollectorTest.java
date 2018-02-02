package com.github.gogy.monitor;

import com.github.gogy.monitor.jvm.JVMCollectEnv;
import com.github.gogy.monitor.jvm.config.JVMConfig;
import com.github.gogy.monitor.jvm.JVMConnectorFactory;
import com.github.gogy.monitor.jvm.MBeanConnectionProvider;
import com.github.gogy.monitor.jvm.collector.ThreadCollector;

import java.util.Map;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
public class GCCollectorTest {

    public static void main(String[] args) {

        JVMCollectEnv env = new JVMCollectEnv();

        JVMConfig config = new JVMConfig();
        config.setHost("192.168.101.14");
        config.setPort(1096);
        env.setConfig(config);

        MBeanConnectionProvider mBeanConnectionProvider = new MBeanConnectionProvider();
        JVMConnectorFactory jvmConnectorFactory = new JVMConnectorFactory();
        mBeanConnectionProvider.setConnectorFactory(jvmConnectorFactory);
        env.setProvider(mBeanConnectionProvider);

        ThreadCollector collector = new ThreadCollector();
        try {
            Metric metric = collector.collect(env);

            System.out.println("metric name ==> " + metric.getName());

            Map<String, Object> values = metric.getValues();
            values.entrySet().stream().forEach(entry -> {
                if (!(entry.getValue() instanceof Metric)) {
                    System.out.println("key :"+entry.getKey()+"; value:"+entry.getValue());
                } else {
                    System.out.println("key :"+entry.getKey());
                    Metric subMetric = (Metric)entry.getValue();
                    System.out.println("name =>"+ subMetric.getName()+"; value=>"+subMetric.getValues());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
