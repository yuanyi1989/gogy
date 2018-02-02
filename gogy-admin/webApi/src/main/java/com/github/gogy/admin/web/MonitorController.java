package com.github.gogy.admin.web;

import com.github.gogy.admin.Constants;
import com.github.gogy.admin.resource.VMResourceService;
import com.github.gogy.admin.resource.model.VMResource;
import com.github.gogy.admin.web.message.DefaultResponse;
import com.github.gogy.admin.web.message.Metric;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author yuanyi
 * @date 2018/1/29
 */
@Slf4j
@RestController
public class MonitorController {

    @Autowired
    private VMResourceService resourceService;

    @RequestMapping(value = "/monitor/metrics", method = RequestMethod.POST)
    public DefaultResponse findOne(@RequestBody List<List<Metric>> metrics, HttpServletRequest request) {
        Optional<Metric> systemBaseMetric = metrics.stream().findFirst().get()
                .stream().filter(metric -> metric.getName().equals("system.base")).findFirst();

        String remoteAddress = request.getRemoteHost();
        if (!systemBaseMetric.isPresent()) {
            log.error("one client can not collect system base info, please check it; address => {}", remoteAddress);
            return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).build();
        }

        Metric metric = systemBaseMetric.get();
        Object serialNumber = metric.getValues().get("serialNumber");
        if (serialNumber == null) {
            log.error("one client can not collect system base info, please check it; address => {}", remoteAddress);
            return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).build();
        }

        VMResource resource;
        Optional<VMResource> one = resourceService.findOne((String) serialNumber);
        if (!one.isPresent()) {
            resource = VMResource.builder().serialNumber((String)serialNumber).env("other").status(1).build();
            resourceService.addVMResource(resource);
        } else {
            resource = one.get();
        }

        Map<String, Object> addressMetric = (Map)metric.getValues().get("addresses");
        if (addressMetric != null) {
            Map<String, Object> addresses = (Map) addressMetric.get("values");
            if (addresses != null) {
                List value = (List) addresses.get("value");
                value.add(remoteAddress);
                resource.setAddress(value);
            }
        }

        Object cpu = metric.getValues().get("cpu");
        if (cpu == null) {

        }

        resourceService.updateVMResource(resource);
        System.out.println(metrics);
        /*Application application = applicationRepository.findOne(id);
        if (Objects.isNull(application)) {
            return DefaultResponse.builder().message("Not Found").status(Constants.Status.NOT_FOUND.getCode()).build();
        }*/
        return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).build();
    }

}
