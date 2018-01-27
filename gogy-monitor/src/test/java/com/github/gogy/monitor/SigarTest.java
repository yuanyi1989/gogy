package com.github.gogy.monitor;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyi
 * @date 2018/1/26
 */
public class SigarTest {

    public static void main(String[] args) throws SigarException {
        List<SigarInfoEntity> cpuInfos = getNetInfos();
        cpuInfos.stream().forEach(info -> {
            System.out.println(info.getName()+" ==> "+info.getValue());
        });
    }

    private static Sigar sigar;

    public static Sigar getInstance() {
        if (null == sigar) {
            sigar = new Sigar();
        }
        return sigar;
    }



    public static List<SigarInfoEntity> getNetInfos() throws SigarException {
        List<SigarInfoEntity> netInfoList = new ArrayList<>();
        String nIfNames[] = getInstance().getNetInterfaceList();
        for (int i = 0; i < nIfNames.length; i++) {
            String name = nIfNames[i];
            NetInterfaceConfig nIfConfig = getInstance().getNetInterfaceConfig(
                    name);

            netInfoList.add(new SigarInfoEntity(name, "网络设备名" + i));
            netInfoList.add(new SigarInfoEntity(nIfConfig.getAddress(), "IP地址"
                    + i));
            netInfoList.add(new SigarInfoEntity(nIfConfig.getNetmask(), "子网掩码"
                    + i));

            if ((nIfConfig.getFlags() & 1L) <= 0L) {
                System.out.println("getNetInterfaceStat not exist");
                continue;
            }
            NetInterfaceStat nIfStat = getInstance().getNetInterfaceStat(name);
            netInfoList.add(new SigarInfoEntity(nIfStat.getRxPackets() + "",
                    "接收的总包裹数" + i));
            netInfoList.add(new SigarInfoEntity(nIfStat.getTxPackets() + "",
                    "发送的总包裹数" + i));
            netInfoList.add(new SigarInfoEntity(nIfStat.getRxBytes() + "",
                    "接收到的总字节数" + i));
            netInfoList.add(new SigarInfoEntity(nIfStat.getTxBytes() + "",
                    "发送的总字节数" + i));
            netInfoList.add(new SigarInfoEntity(nIfStat.getRxErrors() + "",
                    "接收到的错误包数" + i));
            netInfoList.add(new SigarInfoEntity(nIfStat.getTxErrors() + "",
                    "发送数据包时的错误数" + i));
            netInfoList.add(new SigarInfoEntity(nIfStat.getRxDropped() + "",
                    "接收时丢弃的包数" + i));
            netInfoList.add(new SigarInfoEntity(nIfStat.getTxDropped() + "",
                    "发送时丢弃的包数" + i));
        }
        return netInfoList;
    }
}
