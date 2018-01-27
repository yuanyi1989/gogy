package com.github.gogy.monitor.system;

import com.github.gogy.monitor.Collector;
import com.github.gogy.monitor.Metric;
import com.github.gogy.monitor.SigarProvider;
import org.hyperic.sigar.NetStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
@Component
public class TcpCollector implements Collector<SystemCollectEnv> {

    @Autowired
    private SigarProvider sigarProvider;

    @Override
    public Metric collect(SystemCollectEnv env) throws Exception {
        Metric metric = Metric.valueOf("system.tcp");
        NetStat netStat = sigarProvider.getProxy().getNetStat();

        metric.put("ESTABLISHED", netStat.getTcpEstablished());
        metric.put("SYN_SENT", netStat.getTcpSynSent());
        metric.put("SYN_RECV", netStat.getTcpSynRecv());
        metric.put("FIN_WAIT1", netStat.getTcpFinWait1());
        metric.put("FIN_WAIT2", netStat.getTcpFinWait2());
        metric.put("TIME_WAIT", netStat.getTcpTimeWait());
        metric.put("CLOSE_WAIT", netStat.getTcpCloseWait());
        metric.put("LAST_ACK", netStat.getTcpLastAck());
        metric.put("LISTEN", netStat.getTcpListen());
        int nonEstablished = netStat.getTcpSynSent() + netStat.getTcpSynRecv() + netStat.getTcpFinWait1()
                + netStat.getTcpFinWait2() + netStat.getTcpTimeWait() + netStat.getTcpCloseWait()
                + netStat.getTcpLastAck() + netStat.getTcpListen();
        metric.put("NON_ESTABLISHED", nonEstablished);
        int total = netStat.getTcpEstablished() + nonEstablished;
        metric.put("TCP_TOTAL", total);
        return metric;
    }
}
