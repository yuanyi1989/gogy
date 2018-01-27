package com.github.gogy.admin.web;

import com.github.gogy.admin.Constants;
import com.github.gogy.admin.web.message.DefaultResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author yuanyi
 * @date 2018/1/24
 */
@Controller
public class WebsocketEndpoint {

    @MessageMapping("/test")
    @SendTo("/topic/resend")
    public DefaultResponse greeting(String message) {
        return DefaultResponse.builder().status(Constants.Status.SUCCESS.getCode()).message("hello, "+message).build();
    }

}
