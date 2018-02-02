package com.github.gogy.monitor.system;

import com.github.gogy.monitor.Collector;
import com.github.gogy.monitor.Metric;
import com.github.gogy.monitor.SigarProvider;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 主机的基本信息收集，如主机指纹，IP, CPU大小， 内存大小，硬盘容量
 * @author yuanyi
 * @date 2018/1/29
 */
@Slf4j
@Component
public class SystemBaseCollector implements Collector<SystemCollectEnv> {

    private static String linux_id_command = "dmidecode -t 1";

    @Override
    public Metric collect(SystemCollectEnv env) throws Exception {
        if (isWindows()) {
            Metric metric = Metric.valueOf("system.base");
            metric.put("value", "platform not supported");
            return metric;
        }

        boolean permissionDenied = false;
        String serialNumber = null;
        Process exec = Runtime.getRuntime().exec(linux_id_command);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.debug("system base info ==> {}", line);
                if (line.contains("Permission denied")) {
                    permissionDenied = true;
                }
                if (line.contains("Serial Number")) {
                    serialNumber = line.split(":")[1].substring(1);
                    log.info("get system serial number => {}", serialNumber);
                }
            }
        }
        if (permissionDenied) {
            log.warn("permission denied when execute dmidecode command; need a root account");
            Metric metric = Metric.valueOf("system.base");
            metric.put("value", "permission denied");
            return metric;
        }

        Metric metric = Metric.valueOf("system.base");
        metric.put("serialNumber", serialNumber);

        extractAddress(env, metric);
        extractCpu(env, metric);

        return metric;
    }

    private void extractCpu(SystemCollectEnv env, Metric metric) throws SigarException {
        Metric subMetric = Metric.valueOf("cpu");
        CpuInfo cpuInfo = env.getSigarProvider().getProxy().getCpuInfoList()[0];
        subMetric.put("model", cpuInfo.getModel());
        subMetric.put("vendor", cpuInfo.getVendor());
        subMetric.put("total_cores", cpuInfo.getTotalCores());
        subMetric.put("mhz", cpuInfo.getMhz());
        subMetric.put("cache_size", cpuInfo.getCacheSize());
        metric.put("cpu", subMetric);
    }

    private void extractAddress(SystemCollectEnv env, Metric metric) throws SigarException {
        List<String> addresses = new ArrayList<>();
        String[] netInterfaceList = env.getSigarProvider().getProxy().getNetInterfaceList();
        for (int i = 0; i < netInterfaceList.length; i++) {
            String name = netInterfaceList[i];
            String address = this.getAddress(env.getSigarProvider(), name);
            if (NetFlags.isAnyAddress(address) || NetFlags.isLoopback(address)) {
                continue;
            }
            addresses.add(address);
        }
        metric.put("addresses", Metric.valueOf("addresses", addresses));
    }

    private String getAddress(SigarProvider sigarProvider, String name) {
        try {
            return sigarProvider.getProxy().getNetInterfaceConfig(name).getAddress();
        } catch (SigarException var3) {
            //ignore, may never happen
            return null;
        }
    }

    private boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().startsWith("win");
    }
}
