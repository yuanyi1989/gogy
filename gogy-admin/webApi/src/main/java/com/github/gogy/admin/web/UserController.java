/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin.web;

import com.github.gogy.admin.Constants;
import com.github.gogy.admin.web.message.DefaultResponse;
import com.github.gogy.admin.web.message.UserFormDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author yuanyi
 * @date 2018/1/16
 */
@RestController
public class UserController {

    /**
     * 用户登录，从应用的realm中查询用户信息
     * 账号以及密码都不可为空，当其一为空时，接口返回 {@link Constants.Status.FORBIDDEN }
     * @param userForm 用户登录信息，包含账号和密码， 密码为原文MD5后的摘要
     * @return 返回用户信息，如用户的权限code，以及后续请求接口所需的ticket
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public DefaultResponse login(UserFormDto userForm) {
        userForm.setTicket("sdfasdf1234251");
        userForm.setRoleCode("123456789");
        return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).body(userForm).message("登录成功").build();
    }

    /**
     * 根据ticket检测ticket是否有效
     * ticket无效时，返回未登录{@link Constants.Status.NOT_LOGIN}
     * @return
     */
    @RequestMapping(value = "/user/loggedIn", method = RequestMethod.POST)
    public DefaultResponse login(String ticket) {
        return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).message("登录成功").build();
    }

    /**
     * 通过ticket查询该用户的信息
     * @param ticket
     * @return
     */
    @RequestMapping(value = "/user/current")
    public DefaultResponse userInfo(String ticket) {

        return null;
    }


}
