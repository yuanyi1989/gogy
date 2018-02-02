package com.github.gogy.admin.resource;

import com.github.gogy.admin.resource.model.VMResource;
import com.github.gogy.admin.web.message.PageMessage;

import java.util.List;
import java.util.Optional;

/**
 * 服务器资源服务
 * @author yuanyi
 * @date 2018/1/30
 */
public interface VMResourceService {

    /**
     * 添加新服务器资源
     * @param resource
     */
    void addVMResource(VMResource resource);

    /**
     * 更新服务器资源
     * @param resource
     */
    void updateVMResource(VMResource resource);

    /**
     * 移除服务器资源
     * @param resource
     */
    void removeVMResource(VMResource resource);

    /**
     * 查找指定序列号的服务器
     * @param serialNumber
     * @return
     */
    Optional<VMResource> findOne(String serialNumber);

    /**
     * 查询出当前env下有哪些group
     * @param env
     * @return
     */
    public List<String> allGroups(String env);
    /**
     * 列出服务器资源
     * @param env 环境
     * @param group 分组
     * @param pageNo 分页-页数
     * @param pageSize 每页数量
     * @return
     */
    PageMessage listAll(String env, String group, int pageNo, int pageSize);

}
