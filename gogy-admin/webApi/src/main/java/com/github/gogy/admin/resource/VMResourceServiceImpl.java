package com.github.gogy.admin.resource;

import com.github.gogy.admin.resource.model.VMResource;
import com.github.gogy.admin.resource.repository.VMResourceRepository;
import com.github.gogy.admin.web.message.PageMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yuanyi
 * @date 2018/1/30
 */
@Slf4j
@Service
public class VMResourceServiceImpl implements VMResourceService {

    @Resource
    private VMResourceRepository resourceRepository;

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void addVMResource(VMResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("resource must not be null");
        }

        if (!StringUtils.hasText(resource.getSerialNumber()) && resource.getAddress().isEmpty()) {
            //不包含序列号 无效的资源
            throw new IllegalArgumentException("empty serialNumber or address! address => " + resource.toString());
        }

        resourceRepository.insert(resource);
    }

    @Override
    public void updateVMResource(VMResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("resource must not be null");
        }

        if (!StringUtils.hasText(resource.getSerialNumber())) {
            //不包含序列号 无效的资源
            throw new IllegalArgumentException("empty serialNumber! address => " + resource.toString());
        }

        resourceRepository.save(resource);
    }

    @Override
    public void removeVMResource(VMResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("resource must not be null");
        }

        if (!StringUtils.hasText(resource.getSerialNumber())) {
            //不包含序列号 无效的资源
            throw new IllegalArgumentException("empty serialNumber! address => " + resource.toString());
        }

        resourceRepository.delete(resource);
    }

    @Override
    public Optional<VMResource> findOne(String serialNumber) {
        Criteria criteria = Criteria.where("serialNumber").is(serialNumber);
        List<VMResource> vmResources = mongoTemplate.find(Query.query(criteria), VMResource.class);
        if (vmResources == null || vmResources.isEmpty()) {
            return Optional.empty();
        }

        if (vmResources.size() > 1) {
            log.error("duplicate serialNumber !!!! serialNumber ==> {}", serialNumber);
            throw new IllegalStateException("duplicate serialNumber");
        }

        return Optional.of(vmResources.stream().findFirst().get());
    }

    @Override
    public List<String> allGroups(String env) {
        Criteria criteria = Criteria.where("env").is(env);
        List<VMResource> vmResources = mongoTemplate.find(Query.query(criteria), VMResource.class);
        return vmResources.stream().map(resource -> resource.getGroup()).distinct().collect(Collectors.toList());
    }

    @Override
    public PageMessage listAll(String env, String group, int pageNo, int pageSize) {

        List<VMResource> resources;
        if (!StringUtils.hasText(env) && !StringUtils.hasText(group)) {
            resources = mongoTemplate.findAll(VMResource.class);
        } else {
            Criteria where = Criteria.where("serialNumber").exists(true);
            if (StringUtils.hasText(env)) {
                where.and("env").is(env);
            }
            if (StringUtils.hasText(group)) {
                where.and("group").is(group);
            }

            resources = mongoTemplate.find(Query.query(where), VMResource.class);
        }

        List<VMResource> data = resources.stream().skip(pageNo * pageSize).limit(pageSize).collect(Collectors.toList());
        return PageMessage.builder().total(resources.size()).currentPage(pageNo).pageSize(pageSize).data(data).build();
    }
}
