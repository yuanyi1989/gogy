package com.github.gogy.admin.web;

import com.github.gogy.admin.Constants;
import com.github.gogy.admin.config.GogyConfig;
import com.github.gogy.admin.resource.VMResourceService;
import com.github.gogy.admin.resource.model.VMResource;
import com.github.gogy.admin.web.message.DefaultResponse;
import com.github.gogy.admin.web.message.EnvData;
import com.github.gogy.admin.web.message.Machine;
import com.github.gogy.admin.web.message.PageMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yuanyi
 * @date 2018/1/30
 */
@Slf4j
@RestController
public class VMResourceController {

    @Resource
    private GogyConfig config;
    @Resource
    private VMResourceService resourceService;

    @RequestMapping(value = "/resource/machines", method = RequestMethod.GET)
    public DefaultResponse findAll(@RequestParam String token, @RequestParam(required = false)String env,
                                   @RequestParam(required = false)String group,
                                   int pageNo, int pageSize) {
        PageMessage pageMessage = resourceService.listAll(env, group, pageNo, pageSize);
        return DefaultResponse.builder().body(pageMessage).status(Constants.Status.SUCCESS.getCode()).build();
    }

    @RequestMapping(value = "/resource/machines/groups", method = RequestMethod.GET)
    public DefaultResponse findAllGroups(@RequestParam String token, String env) {
        List<String> groups = resourceService.allGroups(env);
        return DefaultResponse.builder().body(groups).status(Constants.Status.SUCCESS.getCode()).build();
    }

    @RequestMapping(value = "/resource/machines/{id}", method = RequestMethod.GET)
    public DefaultResponse findOne(@PathVariable String id) {
        /*resourceService.
        Application application = resourceService.findOne(id);
        if (Objects.isNull(application)) {
            return DefaultResponse.builder().message("Not Found").status(Constants.Status.NOT_FOUND.getCode()).build();
        }*/
        return DefaultResponse.builder().body(null).status(Constants.Status.SUCCESS.getCode()).build();
    }

    @RequestMapping(value = "/resource/machines", method = RequestMethod.POST)
    public DefaultResponse create(VMResource resource) {
        resourceService.addVMResource(resource);
        return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).build();
    }

    @RequestMapping(value = "/resource/machines/{id}", method = RequestMethod.PUT)
    public DefaultResponse update(@PathVariable("id") String id, @RequestBody Machine machine) {
        Optional<VMResource> one = resourceService.findOne(machine.getSerialNumber());
        if (!one.isPresent()) {
            return DefaultResponse.builder().message("DATA NOT FOUND").status(Constants.Status.FAIL.getCode()).build();
        }
        VMResource vmResource = one.get();
        vmResource.setAlias(machine.getAlias());
        vmResource.setGroup(machine.getGroup());
        resourceService.updateVMResource(vmResource);
        return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).build();

    }

    @RequestMapping(value = "/resource/machines/{id}", method = RequestMethod.DELETE)
    public DefaultResponse delete(@PathVariable("id") String id) {
        /*resourceService.removeVMResource();
        resourceService.delete(id);*/
        return DefaultResponse.builder().message("SUCCESS").status(Constants.Status.SUCCESS.getCode()).build();
    }

    @RequestMapping(value = "/resource/machines/env", method = RequestMethod.GET)
    public DefaultResponse listAllEnv() {

        List<Map<String, String>> env = config.getEnv();
        List<EnvData> envDataList = env.stream().map(dataMap -> {
            EnvData data = new EnvData();
            data.setKey(dataMap.get("key"));
            data.setName(dataMap.get("name"));
            return data;
        }).collect(Collectors.toList());

        EnvData other = new EnvData();
        other.setKey("other");
        other.setName("其他");
        envDataList.add(other);

        envDataList.stream().forEach(envData -> {
            PageMessage pageMessage = resourceService.listAll(envData.getKey(), "", 0, Integer.MAX_VALUE);
            envData.setTotal(pageMessage.getTotal());
            List<VMResource> data = pageMessage.getData();
            long workingResource = data.stream().filter(resource -> resource.getStatus() == 1).count();
            envData.setWorking(workingResource);

            long downedResource = data.stream().filter(resource -> resource.getStatus() == -1).count();
            envData.setDown(downedResource);

            long unwatchResource = data.stream().filter(resource -> resource.getStatus() == 0).count();
            envData.setUnwatch(unwatchResource);
        });

        return DefaultResponse.builder().message("SUCCESS").body(envDataList).status(Constants.Status.SUCCESS.getCode()).build();
    }
}