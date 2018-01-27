package com.github.gogy.admin.build.repository;

import com.github.gogy.admin.build.model.BuildRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author yuanyi
 * @date 2018/1/24
 */
public interface BuildRepository extends MongoRepository<BuildRecord, String> {

}
