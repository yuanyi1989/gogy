package com.github.gogy.admin.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 袁意
 * @date 2018/1/14
 */
@Slf4j
@Controller
public class DemoController {

    /**
     * 测试hello
     * @return
     */

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("name", "Dear");
        return "hello";
    }
    @ModelAttribute("globalName")
    public String testModel() {
        return "33hed22ddl1dfgo";
    }

    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public String header() {
        return "components/header";
    }

}
