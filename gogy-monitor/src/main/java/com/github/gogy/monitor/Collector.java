package com.github.gogy.monitor;

/**
 * 信息收集
 * @author yuanyi
 * @date 2018/1/26
 */
public interface Collector<C extends CollectEnv> {

    /**
     * 执行收集动作
     * @param env 收集行为所处的环境
     * @return
     */
    Metric collect(C env) throws Exception;

}
