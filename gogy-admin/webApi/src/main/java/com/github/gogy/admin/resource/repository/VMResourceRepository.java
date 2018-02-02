package com.github.gogy.admin.resource.repository;

import com.github.gogy.admin.resource.model.VMResource;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author yuanyi
 * @date 2018/1/30
 */
public interface VMResourceRepository extends MongoRepository<VMResource, String> {
}
