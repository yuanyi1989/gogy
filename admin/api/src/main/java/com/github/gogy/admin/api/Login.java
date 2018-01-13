/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin.api;

import com.github.gogy.admin.api.dto.JSONWrapper;
import com.github.gogy.admin.api.dto.LoginDTO;
import com.github.gogy.admin.api.dto.UserInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanyi
 * @date 2018/1/13
 */
@RestController
public class Login {

    @RequestMapping("/login/login")
    public DefaultResponse login(@RequestBody LoginDTO loginInfo) {
        String name = loginInfo.getUsername();
        if (name.equals("root")) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername("heelo");
            JSONWrapper jsonWrapper = new JSONWrapper();
            jsonWrapper.put("userinfo", userInfo);
            return DefaultResponse.builder()
                    .status(Constants.STATUS_200)
                    .data(jsonWrapper.export()).build();
        } else if (name.equals("yuanyi")) {
            return DefaultResponse.builder().status(Constants.STATUS_404).msg("hello world").build();
        } else {
            return DefaultResponse.builder().status(Constants.STATUS_100).msg("账号不存在").build();
        }

    }

    @RequestMapping("/Article/selectArticle")
    public DefaultResponse article(@RequestParam int page, @RequestParam(name="page_size") int pageSize) {
        return DefaultResponse.builder().status(Constants.STATUS_404).msg("hello world").build();
    }


}
