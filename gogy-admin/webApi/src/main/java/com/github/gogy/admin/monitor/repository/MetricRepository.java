package com.github.gogy.admin.monitor.repository;

import com.github.gogy.admin.web.message.Metric;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author yuanyi
 * @date 2018/1/30
 */
public interface MetricRepository extends MongoRepository<Metric, String> {
}
