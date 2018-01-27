package com.github.gogy.monitor.system;

import com.github.gogy.monitor.Collector;
import com.github.gogy.monitor.Metric;
import com.github.gogy.monitor.SigarProvider;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
@Slf4j
@Component
public class NetCollector implements Collector<SystemCollectEnv> {

    @Autowired
    private SigarProvider sigarProvider;

    @Override
    public Metric collect(SystemCollectEnv env) throws Exception {
        Map<String, Metric> netMetrics = new HashMap<>(5);
        Metric metric = Metric.valueOf("system.net", netMetrics);
        String[] netInterfaceList = sigarProvider.getProxy().getNetInterfaceList();
        for (int i = 0; i < netInterfaceList.length; i++) {
            String name = netInterfaceList[i];
            String address = this.getAddress(name);
            if(NetFlags.isAnyAddress(address) || NetFlags.isLoopback(address)) {
                continue;
            }

            Metric namedMetric = Metric.valueOf(name, netMetrics);
            NetInterfaceStat netInterfaceStat = sigarProvider.getProxy().getNetInterfaceStat(name);

            namedMetric.put("rx_bytes", netInterfaceStat.getRxBytes());
            namedMetric.put("rx_dropped", netInterfaceStat.getRxDropped());
            namedMetric.put("rx_errors", netInterfaceStat.getRxErrors());
            namedMetric.put("rx_frame", netInterfaceStat.getRxFrame());
            namedMetric.put("rx_overruns", netInterfaceStat.getRxOverruns());
            namedMetric.put("rx_packets", netInterfaceStat.getRxPackets());

            namedMetric.put("speed", netInterfaceStat.getSpeed());

            namedMetric.put("tx_bytes", netInterfaceStat.getTxBytes());
            namedMetric.put("tx_bytes", netInterfaceStat.getTxCarrier());
            namedMetric.put("tx_bytes", netInterfaceStat.getTxCollisions());
            namedMetric.put("tx_bytes", netInterfaceStat.getTxDropped());
            namedMetric.put("tx_bytes", netInterfaceStat.getTxErrors());
            namedMetric.put("tx_bytes", netInterfaceStat.getTxOverruns());
            namedMetric.put("tx_bytes", netInterfaceStat.getTxPackets());

            netMetrics.put(name, namedMetric);
        }
        return metric;
    }

    private String getAddress(String name) {
        try {
            return sigarProvider.getProxy().getNetInterfaceConfig(name).getAddress();
        } catch (SigarException var3) {
            //ignore, may never happen
            return null;
        }
    }
}
