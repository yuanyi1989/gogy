package com.github.gogy.monitor.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.gogy.monitor.Collector;
import com.github.gogy.monitor.Metric;
import com.github.gogy.monitor.SigarProvider;
import com.github.gogy.monitor.jvm.JVMCollectEnv;
import com.github.gogy.monitor.jvm.MBeanConnectionProvider;
import com.github.gogy.monitor.jvm.config.ConfigServiceImpl;
import com.github.gogy.monitor.jvm.config.JVMConfig;
import com.github.gogy.monitor.system.SystemCollectEnv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
@Slf4j
@Component
public class CollectExecutor implements BeanPostProcessor {

    @Value("${gogy.server.address}")
    private String gogyServer;

    private List<Collector> systemCollectorList = new ArrayList<>();
    private List<Collector> jvmCollectorList = new ArrayList<>();

    @Autowired
    private ConfigServiceImpl configService;
    @Autowired
    private SigarProvider sigarProvider;
    @Autowired
    private MBeanConnectionProvider mBeanConnectionProvider;

    private Lock lock = new ReentrantLock();

    private volatile List<List<Metric>> metricLocalCache = new ArrayList<>();

    /**
     * 每分钟上传一次数据
     */
    @Scheduled(fixedDelay = 60*1000)
    public void scheduleSendData() {
        lock.lock();
        try {
            byte[] post = HttpHelper.post(gogyServer, JSON.toJSONBytes(metricLocalCache, SerializerFeature.NotWriteRootClassName));
            metricLocalCache = new ArrayList<>();
            log.info("post metrics to server; result => {}", new String(post));
        } catch (Exception e) {
            //上传失败，数据仍保存在本地，等待下次上传
            log.error("can not connect to server", e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 每5秒收集一次数据
     */
    @Scheduled(fixedDelay = 5*1000)
    public void schedule() {
        lock.lock();
        try {
            List<Metric> allMetrics = new ArrayList<>();

            SystemCollectEnv systemCollectEnv = new SystemCollectEnv();
            systemCollectEnv.setSigarProvider(sigarProvider);
            List<Metric> systemMetrics = systemCollectorList.stream().map(collector -> {
                try {
                    return collector.collect(systemCollectEnv);
                } catch (Exception e) {
                    log.error("can not collect system metric", e);
                    return null;
                }
            }).filter(metric -> metric != null).collect(Collectors.toList());
            allMetrics.addAll(systemMetrics);


            Collection<JVMConfig> jvmConfigs = configService.listAllConfig();


            jvmConfigs.stream().forEach(config -> {
                List<Metric> jvmMetrics = jvmCollectorList.stream().map(collector -> {
                    JVMCollectEnv jvmCollectEnv = new JVMCollectEnv();
                    jvmCollectEnv.setProvider(mBeanConnectionProvider);
                    jvmCollectEnv.setConfig(config);

                    try {
                        return collector.collect(jvmCollectEnv);
                    } catch (Exception e) {
                        log.error("can not collect jvm metric!", e);
                        return null;
                    }
                }).filter(metric -> metric != null).collect(Collectors.toList());
                allMetrics.addAll(jvmMetrics);
            });

            metricLocalCache.add(allMetrics);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (o instanceof Collector) {
            Collector collector = (Collector)o;
            Type genericSuperclass = collector.getClass().getGenericInterfaces()[0];
            ParameterizedType p = (ParameterizedType) genericSuperclass;
            Class parameterClass = (Class) p.getActualTypeArguments()[0];
            if (parameterClass.isAssignableFrom(SystemCollectEnv.class)) {
                systemCollectorList.add(collector);
            } else if (parameterClass.isAssignableFrom(JVMCollectEnv.class)) {
                jvmCollectorList.add(collector);
            } else {

            }
        }
        return o;
    }
}
